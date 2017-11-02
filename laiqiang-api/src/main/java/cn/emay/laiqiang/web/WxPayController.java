package cn.emay.laiqiang.web;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import weixin.popular.bean.paymch.MchBaseResult;
import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;
import cn.emay.laiqiang.bo.LqMemberBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.entity.LqTransactionErrorLog;
import cn.emay.laiqiang.entity.LqTransactionOrder;
import cn.emay.laiqiang.service.LaiqiangService;
import cn.emay.laiqiang.service.LqMemberService;
import cn.emay.laiqiang.service.LqTransactionErrorLogService;
import cn.emay.laiqiang.service.LqTransactionOrderService;
import cn.emay.laiqiang.support.InterfaceReturnStatus;
import cn.emay.laiqiang.support.PaymentType;
import cn.emay.laiqiang.support.TransactionOrderStatus;
import cn.emay.laiqiang.support.TransactionType;

/**
 * 
 * @Title 微信支付回调接口
 * @author zjlwm
 * @date 2016-12-13 上午9:36:10
 * 
 */
@Controller
@RequestMapping(value = "/laiqiang/app/wx/pay/")
public class WxPayController extends BaseController {

	private static Logger logger = Logger.getLogger(WxPayController.class.getName());

	// 重复通知过滤
	private static ExpireKey expireKey = new DefaultExpireKey();

	/**
	 * mch key 微信支付key值
	 */
	@Value("#{configProperties['wx.pay.mch_key']}")
	private String key;

	/**
	 * 交易订单service接口
	 */
	@Autowired
	private LqTransactionOrderService lqTransactionOrderService;

	/**
	 * 来抢service接口
	 */
	@Autowired
	private LaiqiangService laiqiangService;

	/**
	 * 来抢app会员servic接口
	 */
	@Autowired
	private LqMemberService lqMemberService;

	/**
	 * 充值流量接口调用错误日志记录
	 */
	@Autowired
	private LqTransactionErrorLogService lqTransactionErrorLogService;

	/**
	 * http://lqapp.tunnel.qydev.com/laiqiang/app/wx/pay/notify 支付结果回调处理
	 * 
	 * @return
	 */
	@RequestMapping(value = "notify")
	@ResponseBody
	public void notify(HttpServletResponse response, HttpServletRequest request) {
		// xml请求解析
		try {
			// 获取请求数据
			String xmlData = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
			if (StringUtils.isBlank(xmlData)) {
				return;
			}
			logger.info("notify:" + xmlData);
			// 将XML转为MAP,确保所有字段都参与签名验证
			Map<String, String> mapData = XMLConverUtil.convertToMap(xmlData);
			// 转换数据对象
			MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, xmlData);

			// 已处理 去重
			if (expireKey.exists(payNotify.getTransaction_id())) {
				return;
			}

			// 签名验证、验证成功
			if (SignatureUtil.validateSign(mapData, key)) {
				expireKey.add(payNotify.getTransaction_id());
				// 返回状态码

				String return_code = payNotify.getReturn_code();// requestMap.get("return_code");
				if (return_code.equals("SUCCESS")) {
					// 支付状态
					String result_code = payNotify.getResult_code();// requestMap.get("result_code");
					if (result_code.equals("SUCCESS")) {
						// 商户订单号
						String out_trade_no = payNotify.getOut_trade_no();// requestMap.get("out_trade_no");
						// 微信订单号
						String transaction_id = payNotify.getTransaction_id();
						// 订单总金额
						// int total_fee=payNotify.getTotal_fee();
						// 现金支付金额
						int cash_fee = payNotify.getCash_fee();
						LqTransactionOrder lqTransactionOrder = lqTransactionOrderService.findLqTransactionOrderByOrderNo(out_trade_no);
						if (null != lqTransactionOrder && lqTransactionOrder.getStatus().intValue() == TransactionOrderStatus.NotPaid) {
							double cash_feeD = cash_fee / 100d;
							Long memberId = lqTransactionOrder.getMemberId();
							long transactionTypeId = lqTransactionOrder.getTransactionTypeId();
							// 验证金额是否一致
							if (lqTransactionOrder.getAmount().doubleValue() == cash_feeD) {

								lqTransactionOrder.setStatus(TransactionOrderStatus.AlreadyPaid);
								lqTransactionOrder.setFinishOn(DateUtils.getDateTime());// 完成时间
								lqTransactionOrder.setPaymentId(PaymentType.wechat);
								lqTransactionOrder.setThirdpartyOrderId(transaction_id);
								lqTransactionOrderService.update(lqTransactionOrder);

								LqMemberBO lqMemberBO = lqMemberService.getByMemberId(memberId);

								// 购买流量
								if (TransactionType.PurchaseFlow == transactionTypeId) {
									/**
									 * 流量充值
									 */
									Map<String, String> params = new HashMap<String, String>();
									params.put("unionid", lqMemberBO.getUnionid());
									params.put("phone", lqTransactionOrder.getPhoneNumber());
									params.put("flowpackage", lqTransactionOrder.getPackageType());
									params.put("buytype", "0");// 付费充值
									params.put("paytype", "3");// 付费充值

									Map<String, String> chargeResult = laiqiangService.flowrecharge(params);
									if (chargeResult.get("status").equals("OK")) {
										String flowOrderNo = chargeResult.get("data");
										lqTransactionOrder.setFlowOrderNo(flowOrderNo);
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.success);// 调用成功
									} else {
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.fail);// 调用失败
										// 错误信息保存
										String errorMessage = chargeResult.get("data");
										LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
										lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
										lqTransactionErrorLog.setDescribe("流量充值接口调用失败");
										lqTransactionErrorLog.setErrorMessage(errorMessage);
										lqTransactionErrorLog.setOrderNo(out_trade_no);
										lqTransactionErrorLog.setMemberId(memberId);
										lqTransactionErrorLog.setAmount(lqTransactionOrder.getAmount());
										lqTransactionErrorLog.setTransactionTypeId(transactionTypeId);
										lqTransactionErrorLogService.insert(lqTransactionErrorLog);
									}

									lqTransactionOrderService.update(lqTransactionOrder);
								}
								// 购买话费
								else if (TransactionType.BuyCalls == transactionTypeId) {
									/**
									 * 话费充值
									 */
									Map<String, String> params = new HashMap<String, String>();
									params.put("unionid", lqMemberBO.getUnionid());
									params.put("phone", lqTransactionOrder.getPhoneNumber());
									params.put("recharge", lqTransactionOrder.getPackageType());
									params.put("money", lqTransactionOrder.getAmount().toString());// 付费

									Map<String, String> rechargeResult = laiqiangService.recharge(params);

									if (rechargeResult.get("status").equals("OK")) {
										String flowOrderNo = rechargeResult.get("data");
										lqTransactionOrder.setFlowOrderNo(flowOrderNo);
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.success);// 调用成功
									} else {
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.fail);// 调用失败
										// 错误信息保存
										String errorMessage = rechargeResult.get("data");
										LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
										lqTransactionErrorLog.setDescribe("话费充值接口调用失败");
										lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
										lqTransactionErrorLog.setErrorMessage(errorMessage);
										lqTransactionErrorLog.setMemberId(memberId);
										lqTransactionErrorLog.setOrderNo(out_trade_no);
										lqTransactionErrorLog.setAmount(lqTransactionOrder.getAmount());
										lqTransactionErrorLog.setTransactionTypeId(transactionTypeId);
										lqTransactionErrorLogService.insert(lqTransactionErrorLog);
									}

									lqTransactionOrderService.update(lqTransactionOrder);

								}
								// 抢拍
								else if (TransactionType.ShotPay == transactionTypeId) {
									Map<String, String> paymentoutParams = new HashMap<String, String>();
									paymentoutParams.put("wxorderid", lqTransactionOrder.getFlowOrderNo());
									laiqiangService.paymentout(paymentoutParams);
								} else {
									// 交易类型未知异常
									// 错误信息保存
									String errorMessage = JSON.toJSONString(payNotify);
									LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
									lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
									lqTransactionErrorLog.setDescribe("交易类型未知异常");
									lqTransactionErrorLog.setErrorMessage(errorMessage);
									lqTransactionErrorLog.setOrderNo(out_trade_no);
									lqTransactionErrorLog.setMemberId(memberId);
									lqTransactionErrorLog.setAmount(lqTransactionOrder.getAmount());
									lqTransactionErrorLog.setTransactionTypeId(transactionTypeId);
									lqTransactionErrorLogService.insert(lqTransactionErrorLog);
								}

							} else {
								// 支付金额与订单金额不一致
								String errorMessage = JSON.toJSONString(payNotify);
								LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
								lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
								lqTransactionErrorLog.setDescribe("异常微信支付回调，支付金额与订单金额不一致");
								lqTransactionErrorLog.setErrorMessage(errorMessage);
								lqTransactionErrorLog.setOrderNo(out_trade_no);
								lqTransactionErrorLog.setMemberId(memberId);
								lqTransactionErrorLog.setAmount(lqTransactionOrder.getAmount());
								lqTransactionErrorLog.setTransactionTypeId(transactionTypeId);
								lqTransactionErrorLogService.insert(lqTransactionErrorLog);
							}

						} else {
							String errorMessage = JSON.toJSONString(payNotify);
							LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
							lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
							lqTransactionErrorLog.setDescribe("订单号不存在");
							lqTransactionErrorLog.setErrorMessage(errorMessage);
							lqTransactionErrorLog.setOrderNo(out_trade_no);
							lqTransactionErrorLogService.insert(lqTransactionErrorLog);
						}
					} else {
						String errorMessage = JSON.toJSONString(payNotify);
						LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
						lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
						lqTransactionErrorLog.setDescribe("订单支付失败");
						lqTransactionErrorLog.setErrorMessage(errorMessage);
						lqTransactionErrorLogService.insert(lqTransactionErrorLog);
					}
				} else {
					String return_msg = payNotify.getReturn_msg();// requestMap.get("return_msg");
					logger.error(return_msg);
				}

				MchBaseResult baseResult = new MchBaseResult();
				baseResult.setReturn_code("SUCCESS");
				baseResult.setReturn_msg("OK");
				response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
			} else {
				MchBaseResult baseResult = new MchBaseResult();
				baseResult.setReturn_code("FAIL");
				baseResult.setReturn_msg("ERROR");
				response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
			}

		} catch (Exception e) {
			logger.error("微信支付结果回调处理", e);
		}

	}
}
