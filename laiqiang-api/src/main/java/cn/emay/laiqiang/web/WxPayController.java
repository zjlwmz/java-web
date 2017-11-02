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
 * @Title ΢��֧���ص��ӿ�
 * @author zjlwm
 * @date 2016-12-13 ����9:36:10
 * 
 */
@Controller
@RequestMapping(value = "/laiqiang/app/wx/pay/")
public class WxPayController extends BaseController {

	private static Logger logger = Logger.getLogger(WxPayController.class.getName());

	// �ظ�֪ͨ����
	private static ExpireKey expireKey = new DefaultExpireKey();

	/**
	 * mch key ΢��֧��keyֵ
	 */
	@Value("#{configProperties['wx.pay.mch_key']}")
	private String key;

	/**
	 * ���׶���service�ӿ�
	 */
	@Autowired
	private LqTransactionOrderService lqTransactionOrderService;

	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private LaiqiangService laiqiangService;

	/**
	 * ����app��Աservic�ӿ�
	 */
	@Autowired
	private LqMemberService lqMemberService;

	/**
	 * ��ֵ�����ӿڵ��ô�����־��¼
	 */
	@Autowired
	private LqTransactionErrorLogService lqTransactionErrorLogService;

	/**
	 * http://lqapp.tunnel.qydev.com/laiqiang/app/wx/pay/notify ֧������ص�����
	 * 
	 * @return
	 */
	@RequestMapping(value = "notify")
	@ResponseBody
	public void notify(HttpServletResponse response, HttpServletRequest request) {
		// xml�������
		try {
			// ��ȡ��������
			String xmlData = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
			if (StringUtils.isBlank(xmlData)) {
				return;
			}
			logger.info("notify:" + xmlData);
			// ��XMLתΪMAP,ȷ�������ֶζ�����ǩ����֤
			Map<String, String> mapData = XMLConverUtil.convertToMap(xmlData);
			// ת�����ݶ���
			MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, xmlData);

			// �Ѵ��� ȥ��
			if (expireKey.exists(payNotify.getTransaction_id())) {
				return;
			}

			// ǩ����֤����֤�ɹ�
			if (SignatureUtil.validateSign(mapData, key)) {
				expireKey.add(payNotify.getTransaction_id());
				// ����״̬��

				String return_code = payNotify.getReturn_code();// requestMap.get("return_code");
				if (return_code.equals("SUCCESS")) {
					// ֧��״̬
					String result_code = payNotify.getResult_code();// requestMap.get("result_code");
					if (result_code.equals("SUCCESS")) {
						// �̻�������
						String out_trade_no = payNotify.getOut_trade_no();// requestMap.get("out_trade_no");
						// ΢�Ŷ�����
						String transaction_id = payNotify.getTransaction_id();
						// �����ܽ��
						// int total_fee=payNotify.getTotal_fee();
						// �ֽ�֧�����
						int cash_fee = payNotify.getCash_fee();
						LqTransactionOrder lqTransactionOrder = lqTransactionOrderService.findLqTransactionOrderByOrderNo(out_trade_no);
						if (null != lqTransactionOrder && lqTransactionOrder.getStatus().intValue() == TransactionOrderStatus.NotPaid) {
							double cash_feeD = cash_fee / 100d;
							Long memberId = lqTransactionOrder.getMemberId();
							long transactionTypeId = lqTransactionOrder.getTransactionTypeId();
							// ��֤����Ƿ�һ��
							if (lqTransactionOrder.getAmount().doubleValue() == cash_feeD) {

								lqTransactionOrder.setStatus(TransactionOrderStatus.AlreadyPaid);
								lqTransactionOrder.setFinishOn(DateUtils.getDateTime());// ���ʱ��
								lqTransactionOrder.setPaymentId(PaymentType.wechat);
								lqTransactionOrder.setThirdpartyOrderId(transaction_id);
								lqTransactionOrderService.update(lqTransactionOrder);

								LqMemberBO lqMemberBO = lqMemberService.getByMemberId(memberId);

								// ��������
								if (TransactionType.PurchaseFlow == transactionTypeId) {
									/**
									 * ������ֵ
									 */
									Map<String, String> params = new HashMap<String, String>();
									params.put("unionid", lqMemberBO.getUnionid());
									params.put("phone", lqTransactionOrder.getPhoneNumber());
									params.put("flowpackage", lqTransactionOrder.getPackageType());
									params.put("buytype", "0");// ���ѳ�ֵ
									params.put("paytype", "3");// ���ѳ�ֵ

									Map<String, String> chargeResult = laiqiangService.flowrecharge(params);
									if (chargeResult.get("status").equals("OK")) {
										String flowOrderNo = chargeResult.get("data");
										lqTransactionOrder.setFlowOrderNo(flowOrderNo);
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.success);// ���óɹ�
									} else {
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.fail);// ����ʧ��
										// ������Ϣ����
										String errorMessage = chargeResult.get("data");
										LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
										lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
										lqTransactionErrorLog.setDescribe("������ֵ�ӿڵ���ʧ��");
										lqTransactionErrorLog.setErrorMessage(errorMessage);
										lqTransactionErrorLog.setOrderNo(out_trade_no);
										lqTransactionErrorLog.setMemberId(memberId);
										lqTransactionErrorLog.setAmount(lqTransactionOrder.getAmount());
										lqTransactionErrorLog.setTransactionTypeId(transactionTypeId);
										lqTransactionErrorLogService.insert(lqTransactionErrorLog);
									}

									lqTransactionOrderService.update(lqTransactionOrder);
								}
								// ���򻰷�
								else if (TransactionType.BuyCalls == transactionTypeId) {
									/**
									 * ���ѳ�ֵ
									 */
									Map<String, String> params = new HashMap<String, String>();
									params.put("unionid", lqMemberBO.getUnionid());
									params.put("phone", lqTransactionOrder.getPhoneNumber());
									params.put("recharge", lqTransactionOrder.getPackageType());
									params.put("money", lqTransactionOrder.getAmount().toString());// ����

									Map<String, String> rechargeResult = laiqiangService.recharge(params);

									if (rechargeResult.get("status").equals("OK")) {
										String flowOrderNo = rechargeResult.get("data");
										lqTransactionOrder.setFlowOrderNo(flowOrderNo);
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.success);// ���óɹ�
									} else {
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.fail);// ����ʧ��
										// ������Ϣ����
										String errorMessage = rechargeResult.get("data");
										LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
										lqTransactionErrorLog.setDescribe("���ѳ�ֵ�ӿڵ���ʧ��");
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
								// ����
								else if (TransactionType.ShotPay == transactionTypeId) {
									Map<String, String> paymentoutParams = new HashMap<String, String>();
									paymentoutParams.put("wxorderid", lqTransactionOrder.getFlowOrderNo());
									laiqiangService.paymentout(paymentoutParams);
								} else {
									// ��������δ֪�쳣
									// ������Ϣ����
									String errorMessage = JSON.toJSONString(payNotify);
									LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
									lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
									lqTransactionErrorLog.setDescribe("��������δ֪�쳣");
									lqTransactionErrorLog.setErrorMessage(errorMessage);
									lqTransactionErrorLog.setOrderNo(out_trade_no);
									lqTransactionErrorLog.setMemberId(memberId);
									lqTransactionErrorLog.setAmount(lqTransactionOrder.getAmount());
									lqTransactionErrorLog.setTransactionTypeId(transactionTypeId);
									lqTransactionErrorLogService.insert(lqTransactionErrorLog);
								}

							} else {
								// ֧������붩����һ��
								String errorMessage = JSON.toJSONString(payNotify);
								LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
								lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
								lqTransactionErrorLog.setDescribe("�쳣΢��֧���ص���֧������붩����һ��");
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
							lqTransactionErrorLog.setDescribe("�����Ų�����");
							lqTransactionErrorLog.setErrorMessage(errorMessage);
							lqTransactionErrorLog.setOrderNo(out_trade_no);
							lqTransactionErrorLogService.insert(lqTransactionErrorLog);
						}
					} else {
						String errorMessage = JSON.toJSONString(payNotify);
						LqTransactionErrorLog lqTransactionErrorLog = new LqTransactionErrorLog();
						lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
						lqTransactionErrorLog.setDescribe("����֧��ʧ��");
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
			logger.error("΢��֧������ص�����", e);
		}

	}
}
