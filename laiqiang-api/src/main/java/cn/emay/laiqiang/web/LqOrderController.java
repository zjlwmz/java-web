package cn.emay.laiqiang.web;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.MchPayApp;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.util.PayUtil;
import cn.emay.laiqiang.bo.CardBo;
import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.DoubleUtil;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.entity.LqTransactionOrder;
import cn.emay.laiqiang.entity.Rechargelog;
import cn.emay.laiqiang.service.CardBoService;
import cn.emay.laiqiang.service.LaiqiangService;
import cn.emay.laiqiang.service.LqOrderNoService;
import cn.emay.laiqiang.service.LqParamService;
import cn.emay.laiqiang.service.LqTransactionOrderService;
import cn.emay.laiqiang.service.MemberService;
import cn.emay.laiqiang.service.RechargelogService;
import cn.emay.laiqiang.support.TransactionOrderStatus;
import cn.emay.laiqiang.support.TransactionType;


/**
 * @Title 订单接口
 * @author zjlwm
 * @date 2016-12-11 下午5:23:22
 */
@Controller
@RequestMapping(value = "/laiqiang/app/order/")
public class LqOrderController extends BaseController{

	private static Logger logger = Logger.getLogger(LqOrderController.class.getName());
	
	
	
	/**
	 * 商户支付密码
	 */
	@Value("#{configProperties['wx.pay.mch_key']}")
	private String mchKey;
	
	
	/**
	 * 应用ID
	 * 微信开放平台审核通过的应用APPID
	 */
	@Value("#{configProperties['wx.pay.appid']}")
	private String appid;
	
	
	/**
	 * 商户号
	 * 微信支付分配的商户号
	 */
	@Value("#{configProperties['wx.pay.mch_id']}")
	private String mch_id;
	
	
	/**
	 * 接口地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	/**
	 * 会员service接口
	 */
	@Autowired
	private MemberService memberService;
	
	
	
	/**
	 * 订单编号service接口
	 */
	@Autowired
	private LqOrderNoService lqOrderNoService;
	
	
	/**
	 * 来抢service接口
	 */
	@Autowired
	private LaiqiangService laiqiangService;
	
	
	/**
	 * 交易订单service接口
	 */
	@Autowired
	private LqTransactionOrderService lqTransactionOrderService;
	
	
	/**
	 * 卡卷service接口
	 */
	@Autowired
	private CardBoService cardBoService;
	
	
	/**
	 * 订单service接口
	 */
	@Autowired
	private RechargelogService rechargelogService;
	
	
	/**
	 * 参数service接口
	 */
	@Autowired
	private LqParamService lqParamService;
	
	
	/**
	 * APP
	 * 微信下单
	 */
	@RequestMapping(value="wxPlaceAnOrder")
	@ResponseBody
	public String wxPlaceAnOrder(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try{
			String ip=getIpAddr(req);
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					
					/**
					 * APP用户识别ID
					 */
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					/**
					 * 会员id
					 */
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid参数值错误");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					String body=postData.get("body");
					if(StringUtils.isBlank(body)){
						result.setSuccess(false);
						result.setMessage("body参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					String orderNo=postData.get("orderNo");
					if(StringUtils.isBlank(orderNo)){
						result.setSuccess(false);
						result.setMessage("orderNo参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					LqTransactionOrder lqTransactionOrder=lqTransactionOrderService.findLqTransactionOrderByOrderNo(orderNo);
					if(null==lqTransactionOrder){
						result.setSuccess(false);
						result.setMessage("该订单不存在");
						return FastJsonUtils.toJSONString(result);
					}
					Integer status=lqTransactionOrder.getStatus();
					
					if(status.intValue()!=TransactionOrderStatus.NotPaid){
						result.setSuccess(false);
						result.setMessage("该订单不能进行支付");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					Double moneyD=DoubleUtil.mul(lqTransactionOrder.getAmount(), 100);
					int  total_fee=moneyD.intValue();
					
					Unifiedorder unifiedorder=new Unifiedorder();
					unifiedorder.setAppid(appid);
					unifiedorder.setMch_id(mch_id);
					unifiedorder.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
					
					
					unifiedorder.setBody(body);
					
					
					//商户订单号
					unifiedorder.setOut_trade_no(orderNo);
					//总金额
					unifiedorder.setTotal_fee(String.valueOf(total_fee));
					
					//终端IP
					unifiedorder.setSpbill_create_ip(ip);
					
					String notify_url=domain+"laiqiang/app/wx/pay/notify";
					//通知地址
					unifiedorder.setNotify_url(notify_url);
					//交易类型
					unifiedorder.setTrade_type("APP");
					
					UnifiedorderResult unifiedorderResult =PayMchAPI.payUnifiedorder(unifiedorder, mchKey);
					String return_code=unifiedorderResult.getReturn_code();
					if(return_code.equals("SUCCESS")){
						String result_code=unifiedorderResult.getResult_code();
						if(result_code.equals("SUCCESS")){
							String prepay_id=unifiedorderResult.getPrepay_id();
							MchPayApp mchPayApp = PayUtil.generateMchAppData(prepay_id, appid, mch_id, mchKey);
							Map<String,Object>payApp=new HashMap<String, Object>();
							payApp.put("appId", appid);
							payApp.put("partnerId", mchPayApp.getPartnerid());
							payApp.put("prepayId", mchPayApp.getPrepayid());
							payApp.put("packageValue", mchPayApp.getPackage_());
							payApp.put("nonceStr", mchPayApp.getNoncestr());
							payApp.put("timeStamp", mchPayApp.getTimestamp());
							payApp.put("sign", mchPayApp.getSign());
							
							params.put("payApp", payApp);
							result.setData(params);
							result.setSuccess(true);
						}else{
							String error_msg=unifiedorderResult.getErr_code_des();
							result.setMessage(error_msg);
							result.setSuccess(false);
						}
					}else{
						String return_msg=unifiedorderResult.getReturn_msg();
						result.setMessage(return_msg);
						result.setSuccess(false);
					}
				}else{
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			}else{
				result.setMessage("参数格式错误！");
				result.setSuccess(false);
			}
			
		}catch (Exception e) {
			logger.error("数据异常", e);
			result.setSuccess(false);
			result.setMessage("数据异常");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 赚钱任务
	 * 创建订单、订单保存
	 */
	@RequestMapping(value="createOrder")
	@ResponseBody
	public String createOrder(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.error("createOrder:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					/**
					 * APP用户识别ID
					 */
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					/**
					 * 会员id
					 */
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid参数值错误");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					//0:流量余额购买流量包  ; 1:现金购买流量包 ; 2 :购买充值包
					String type=postData.get("type");
					if(StringUtils.isBlank(type)){
						result.setSuccess(false);
						result.setMessage("type参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * 电话号码
					 */
					String phone=postData.get("phone");
					if(StringUtils.isBlank(phone)){
						result.setSuccess(false);
						result.setMessage("phone参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * 套餐类型
					 */
					String packageType=postData.get("package");
					if(StringUtils.isBlank(packageType)){
						result.setSuccess(false);
						result.setMessage("package参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * 卡卷代码
					 */
					Double price=null;
					int lqtype1=-1;//1：流量代金券；2：话费代金券；3：给账户余额充值代金券
					String codeid=postData.get("codeid");
					if(StringUtils.isNotBlank(codeid)){
						/**
						 * 验证该会员是否有该卡卷
						 */
						boolean cardResult=cardBoService.isCardExit(memberBO.getId(), codeid);
						if(!cardResult){
							result.setSuccess(false);
							result.setMessage("该卡卷不存在");
							return FastJsonUtils.toJSONString(result);
						}else{
							CardBo cardBo=cardBoService.get(codeid);
							if(null!=cardBo){
								Integer islose=cardBo.getIslose();
								if(null!=islose && islose.intValue()==1){//可以使用
									price=cardBo.getPrice();
									lqtype1=cardBo.getLqtype1();
								}else{
									result.setSuccess(false);
									result.setMessage("卡卷不存在或已使用");
									return FastJsonUtils.toJSONString(result);
								}
							}else{
								result.setSuccess(false);
								result.setMessage("卡卷不存在或已使用");
								return FastJsonUtils.toJSONString(result);
							}
						}
						
						
						if(price==null){
							result.setSuccess(false);
							result.setMessage("卡卷不存在");
							return FastJsonUtils.toJSONString(result);
						}
						
						if(price.doubleValue()==0){
							result.setSuccess(false);
							result.setMessage("卡卷不存在");
							return FastJsonUtils.toJSONString(result);
						}
						
					}
					
					
					
					//余额流量购买流量包
					if(type.equals("0")){
						Map<String,String>flowMap=lqParamService.buyflowRechargestatus();
						if(flowMap.get("status").equals("ERROR")){
							result.setMessage(flowMap.get("mesage"));
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						Map<String,String>lqParamsMap=new HashMap<String, String>();
						lqParamsMap.put("unionid", memberBO.getUnionid());
						lqParamsMap.put("phone", phone);
						lqParamsMap.put("flowpackage", packageType);
						lqParamsMap.put("buytype", "1");//充值类型：0:付费充值，1用户余额充值
						lqParamsMap.put("paytype", "0");
						Map<String,String> resultMap=laiqiangService.flowrecharge(lqParamsMap);
						String status=resultMap.get("status");
						if(status.equals("OK")){
							//账号流量余额充值
							result.setSuccess(true);
						}else{
							String message=resultMap.get("data");
							result.setSuccess(false);
							result.setMessage(message);
							return FastJsonUtils.toJSONString(result);
						}
					}
					
					//现金购买流量包
					else if(type.equals("1")){
						Map<String,String>flowMap=lqParamService.buyflowRechargestatus();
						if(flowMap.get("status").equals("ERROR")){
							result.setMessage(flowMap.get("mesage"));
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						
						//充值金额
						String amount=postData.get("amount");
						if(StringUtils.isBlank(amount)){
							result.setSuccess(false);
							result.setMessage("amount参数不能为空");
							return FastJsonUtils.toJSONString(result);
						}
						
						double amountDouble=Double.parseDouble(amount);
						
						/**
						 * 查询购买套流量餐价格
						 */
						Map<String,String>flowmoneyMap=laiqiangService.getflowmoney(phone, Integer.parseInt(packageType));
						if(flowmoneyMap.get("status").equals("OK")){
							String priceStr=flowmoneyMap.get("price");
							double flowPrice=Double.parseDouble(priceStr);
							double priceNew=DoubleUtil.div(flowPrice, 100d);
							if(priceNew!=amountDouble){
								result.setSuccess(false);
								result.setMessage("非法请求");
								return FastJsonUtils.toJSONString(result);
							}
						}else{
							result.setSuccess(false);
							result.setMessage("现金购买流量包失败；稍后再试");
							return FastJsonUtils.toJSONString(result);
						}
						
						
						
						
						
						/**
						 * 交易订单
						 */
						LqTransactionOrder lqTransactionOrderBO=new LqTransactionOrder();
						String orderNo=lqOrderNoService.getOrderNo();
						lqTransactionOrderBO.setOrderNo(orderNo);
						lqTransactionOrderBO.setAmount(amountDouble);
						lqTransactionOrderBO.setRewardType("2");//现金
						lqTransactionOrderBO.setStatus(0);//未支付
						lqTransactionOrderBO.setCreatedTime(DateUtils.getDateTime());
						lqTransactionOrderBO.setTransactionTypeId(TransactionType.PurchaseFlow);//购买流量
						lqTransactionOrderBO.setMemberId(memberBO.getId());
						lqTransactionOrderBO.setPhoneNumber(phone);//充值手机号码
						lqTransactionOrderBO.setPackageType(packageType);//充值流量
						if(lqtype1==1){//流量卡卷
							//扣减代扣卡卷
							if(price!=null && price.doubleValue()>0){
								lqTransactionOrderBO.setCardcodeId(Long.parseLong(codeid));//卡券id
								amountDouble=amountDouble-price;
								lqTransactionOrderBO.setAmount(amountDouble);
								
								/**
								 * 卡卷使用设置
								 */
								cardBoService.userCard(codeid);
							}
						}
						
						int transtionOrderresult=lqTransactionOrderService.insert(lqTransactionOrderBO);
						if(transtionOrderresult>0){
							params.put("orderNo", orderNo);
							result.setData(params);
							result.setSuccess(true);
						}else{
							result.setSuccess(false);
							result.setMessage("订单创建失败");
							return FastJsonUtils.toJSONString(result);
						}
						
					}
					//购买话费包
					else if(type.equals("2")){
						Map<String,String>flowMap=lqParamService.buyphoneRechargetatus();
						if(flowMap.get("status").equals("ERROR")){
							result.setMessage(flowMap.get("mesage"));
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						
						
						String amount=postData.get("amount");
						if(StringUtils.isBlank(amount)){
							result.setSuccess(false);
							result.setMessage("amount参数不能为空");
							return FastJsonUtils.toJSONString(result);
						}
						Double amountDouble=Double.parseDouble(amount);
						
						
						
						/**
						 * 查询购买话费套餐价格
						 */
						Map<String,String>flowmoneyMap=laiqiangService.getChargepackagemoney(phone, Integer.parseInt(packageType));
						if(flowmoneyMap.get("status").equals("OK")){
							String priceStr=flowmoneyMap.get("price");
							double flowPrice=Double.parseDouble(priceStr);
							double priceNew=DoubleUtil.div(flowPrice, 100d);
							if(priceNew!=amountDouble){
								result.setSuccess(false);
								result.setMessage("非法请求");
								return FastJsonUtils.toJSONString(result);
							}
						}else{
							result.setSuccess(false);
							result.setMessage("现金购买流量包失败；稍后再试");
							return FastJsonUtils.toJSONString(result);
						}
						
						
						
						
						/**
						 * 交易订单
						 */
						LqTransactionOrder lqTransactionOrderBO=new LqTransactionOrder();
						String orderNo=lqOrderNoService.getOrderNo();
						lqTransactionOrderBO.setOrderNo(orderNo);
						lqTransactionOrderBO.setAmount(amountDouble);
						lqTransactionOrderBO.setRewardType("2");//现金
						lqTransactionOrderBO.setStatus(0);//未支付
						lqTransactionOrderBO.setCreatedTime(DateUtils.getDateTime());
						lqTransactionOrderBO.setTransactionTypeId(TransactionType.BuyCalls);//购买话费
						lqTransactionOrderBO.setMemberId(memberBO.getId());
						lqTransactionOrderBO.setPhoneNumber(phone);//充值手机号码
						lqTransactionOrderBO.setPackageType(packageType);//充值话费包
						
						if(lqtype1==2){//话费卡卷
							//扣减代扣卡卷
							if(StringUtils.isNotBlank(codeid)){
								lqTransactionOrderBO.setCardcodeId(Long.parseLong(codeid));//卡券id
								if(null!=price && price.intValue()>0){
									amountDouble=amountDouble-price;
									lqTransactionOrderBO.setAmount(amountDouble);
								}
							}
							
							/**
							 * 卡卷使用设置
							 */
							cardBoService.userCard(codeid);
						}
						
						int transtionOrderresult=lqTransactionOrderService.insert(lqTransactionOrderBO);
						if(transtionOrderresult>0){
							params.put("orderNo", orderNo);
							result.setData(params);
							result.setSuccess(true);
						}else{
							result.setSuccess(false);
							result.setMessage("订单创建失败");
							return FastJsonUtils.toJSONString(result);
						}
					}else{
						result.setSuccess(false);
						result.setMessage("订单类型不识别");
						return FastJsonUtils.toJSONString(result);
					}

					result.setData(params);
					result.setSuccess(true);
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("参数格式错误！");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("数据异常", e);
			result.setSuccess(false);
			result.setMessage("数据异常");
		}
		return FastJsonUtils.toJSONString(result);
	}

	
	
	
	
	
	
	/**
	 * 流量订单、1:话费订单
	 * 交易订单查询
	 */
	@RequestMapping(value="findOrderList")
	@ResponseBody
	public String findOrderList(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.error("createOrder:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					
					/**
					 * APP用户识别ID
					 */
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					String currentPageStr=postData.get("currentPage");
					if(StringUtils.isBlank(currentPageStr)){
						result.setSuccess(false);
						result.setMessage("currentPage参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					String pageSizeStr=postData.get("pageSize");
					if(StringUtils.isBlank(pageSizeStr)){
						result.setSuccess(false);
						result.setMessage("pageSize参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					Long currentPage=Long.parseLong(currentPageStr);
					Long pageSize=Long.parseLong(pageSizeStr);
					long start=(currentPage-1)*pageSize;
					long end=currentPage*pageSize-1;
					
					//0:流量订单、1:话费订单
					String type=postData.get("type");
					if(StringUtils.isBlank(type)){
						result.setSuccess(false);
						result.setMessage("type参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * 会员id
					 */
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid参数值错误");
						return FastJsonUtils.toJSONString(result);
					}
					
					Long memberId=memberBO.getId();
					List<Rechargelog> rechargelogList=null;
					DecimalFormat df = new DecimalFormat("##0.0#");
					double totalMoeny=0.0d;
					//流量订单
					if(type.equals("0")){
//						Long moneyTotal=rechargelogService.findRechargelogTotalMoney(memberId);
						//剩余流量余额
						totalMoeny=memberBO.getCardbalance()*1d;
						rechargelogList=rechargelogService.findRechargelogList(memberId,start, end);
					}
					//话费订单
					else if(type.equals("1")){
						Long moneyTotal=rechargelogService.findWxrechargeTotalMoney(memberId);
						totalMoeny=moneyTotal/100.0;
						rechargelogList=rechargelogService.findWxrechargeList(memberId,start, end);
					}else{
						result.setSuccess(false);
						result.setMessage("type参数值错误");
						return FastJsonUtils.toJSONString(result);
					}
					
					for(Rechargelog rechargelog:rechargelogList){
						Integer status=rechargelog.getStatus();
						String retype=rechargelog.getType();
						Integer failflow=rechargelog.getFailflow();
						Integer packageType=rechargelog.getPackageType();
						String money=rechargelog.getMoney();
						if(StringUtils.isNotBlank(money)){
							double md=Double.parseDouble(money);
							rechargelog.setMoney(df.format(md));
						}
						if(type.equals("0")){//流量
							if(null!=failflow && null!=packageType){
								if(failflow.intValue()==packageType.intValue()){
									if(retype.equals("1") || retype.equals("3") || retype.equals("4") | retype.equals("5")){
										rechargelog.setRstatus("已退款");
									}
								}
							}
							
						}else if(type.equals("1")){//话费
							if(status==2){
								rechargelog.setRstatus("已退款");
							}
						}
					}
					
					
					//充值金额
					params.put("totalMoeny", df.format(totalMoeny));
					
					params.put("rechargelogList", rechargelogList);
					
					result.setData(params);
					result.setSuccess(true);
					
				}else{
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			}else{
				result.setMessage("参数格式错误！");
				result.setSuccess(false);
			}
		}catch (Exception e) {
			logger.error("数据异常", e);
			result.setSuccess(false);
			result.setMessage("数据异常");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	
	/**
	 * 
	 */
}
