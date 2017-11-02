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
 * @Title �����ӿ�
 * @author zjlwm
 * @date 2016-12-11 ����5:23:22
 */
@Controller
@RequestMapping(value = "/laiqiang/app/order/")
public class LqOrderController extends BaseController{

	private static Logger logger = Logger.getLogger(LqOrderController.class.getName());
	
	
	
	/**
	 * �̻�֧������
	 */
	@Value("#{configProperties['wx.pay.mch_key']}")
	private String mchKey;
	
	
	/**
	 * Ӧ��ID
	 * ΢�ſ���ƽ̨���ͨ����Ӧ��APPID
	 */
	@Value("#{configProperties['wx.pay.appid']}")
	private String appid;
	
	
	/**
	 * �̻���
	 * ΢��֧��������̻���
	 */
	@Value("#{configProperties['wx.pay.mch_id']}")
	private String mch_id;
	
	
	/**
	 * �ӿڵ�ַ
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	/**
	 * ��Աservice�ӿ�
	 */
	@Autowired
	private MemberService memberService;
	
	
	
	/**
	 * �������service�ӿ�
	 */
	@Autowired
	private LqOrderNoService lqOrderNoService;
	
	
	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private LaiqiangService laiqiangService;
	
	
	/**
	 * ���׶���service�ӿ�
	 */
	@Autowired
	private LqTransactionOrderService lqTransactionOrderService;
	
	
	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private CardBoService cardBoService;
	
	
	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private RechargelogService rechargelogService;
	
	
	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private LqParamService lqParamService;
	
	
	/**
	 * APP
	 * ΢���µ�
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
					 * APP�û�ʶ��ID
					 */
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					/**
					 * ��Աid
					 */
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					String body=postData.get("body");
					if(StringUtils.isBlank(body)){
						result.setSuccess(false);
						result.setMessage("body��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					String orderNo=postData.get("orderNo");
					if(StringUtils.isBlank(orderNo)){
						result.setSuccess(false);
						result.setMessage("orderNo��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					LqTransactionOrder lqTransactionOrder=lqTransactionOrderService.findLqTransactionOrderByOrderNo(orderNo);
					if(null==lqTransactionOrder){
						result.setSuccess(false);
						result.setMessage("�ö���������");
						return FastJsonUtils.toJSONString(result);
					}
					Integer status=lqTransactionOrder.getStatus();
					
					if(status.intValue()!=TransactionOrderStatus.NotPaid){
						result.setSuccess(false);
						result.setMessage("�ö������ܽ���֧��");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					Double moneyD=DoubleUtil.mul(lqTransactionOrder.getAmount(), 100);
					int  total_fee=moneyD.intValue();
					
					Unifiedorder unifiedorder=new Unifiedorder();
					unifiedorder.setAppid(appid);
					unifiedorder.setMch_id(mch_id);
					unifiedorder.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
					
					
					unifiedorder.setBody(body);
					
					
					//�̻�������
					unifiedorder.setOut_trade_no(orderNo);
					//�ܽ��
					unifiedorder.setTotal_fee(String.valueOf(total_fee));
					
					//�ն�IP
					unifiedorder.setSpbill_create_ip(ip);
					
					String notify_url=domain+"laiqiang/app/wx/pay/notify";
					//֪ͨ��ַ
					unifiedorder.setNotify_url(notify_url);
					//��������
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
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
			
		}catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * ׬Ǯ����
	 * ������������������
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
					 * APP�û�ʶ��ID
					 */
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					/**
					 * ��Աid
					 */
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					//0:��������������  ; 1:�ֽ��������� ; 2 :�����ֵ��
					String type=postData.get("type");
					if(StringUtils.isBlank(type)){
						result.setSuccess(false);
						result.setMessage("type��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * �绰����
					 */
					String phone=postData.get("phone");
					if(StringUtils.isBlank(phone)){
						result.setSuccess(false);
						result.setMessage("phone��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * �ײ�����
					 */
					String packageType=postData.get("package");
					if(StringUtils.isBlank(packageType)){
						result.setSuccess(false);
						result.setMessage("package��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * �������
					 */
					Double price=null;
					int lqtype1=-1;//1����������ȯ��2�����Ѵ���ȯ��3�����˻�����ֵ����ȯ
					String codeid=postData.get("codeid");
					if(StringUtils.isNotBlank(codeid)){
						/**
						 * ��֤�û�Ա�Ƿ��иÿ���
						 */
						boolean cardResult=cardBoService.isCardExit(memberBO.getId(), codeid);
						if(!cardResult){
							result.setSuccess(false);
							result.setMessage("�ÿ�������");
							return FastJsonUtils.toJSONString(result);
						}else{
							CardBo cardBo=cardBoService.get(codeid);
							if(null!=cardBo){
								Integer islose=cardBo.getIslose();
								if(null!=islose && islose.intValue()==1){//����ʹ��
									price=cardBo.getPrice();
									lqtype1=cardBo.getLqtype1();
								}else{
									result.setSuccess(false);
									result.setMessage("�������ڻ���ʹ��");
									return FastJsonUtils.toJSONString(result);
								}
							}else{
								result.setSuccess(false);
								result.setMessage("�������ڻ���ʹ��");
								return FastJsonUtils.toJSONString(result);
							}
						}
						
						
						if(price==null){
							result.setSuccess(false);
							result.setMessage("��������");
							return FastJsonUtils.toJSONString(result);
						}
						
						if(price.doubleValue()==0){
							result.setSuccess(false);
							result.setMessage("��������");
							return FastJsonUtils.toJSONString(result);
						}
						
					}
					
					
					
					//�����������������
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
						lqParamsMap.put("buytype", "1");//��ֵ���ͣ�0:���ѳ�ֵ��1�û�����ֵ
						lqParamsMap.put("paytype", "0");
						Map<String,String> resultMap=laiqiangService.flowrecharge(lqParamsMap);
						String status=resultMap.get("status");
						if(status.equals("OK")){
							//�˺���������ֵ
							result.setSuccess(true);
						}else{
							String message=resultMap.get("data");
							result.setSuccess(false);
							result.setMessage(message);
							return FastJsonUtils.toJSONString(result);
						}
					}
					
					//�ֽ���������
					else if(type.equals("1")){
						Map<String,String>flowMap=lqParamService.buyflowRechargestatus();
						if(flowMap.get("status").equals("ERROR")){
							result.setMessage(flowMap.get("mesage"));
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						
						//��ֵ���
						String amount=postData.get("amount");
						if(StringUtils.isBlank(amount)){
							result.setSuccess(false);
							result.setMessage("amount��������Ϊ��");
							return FastJsonUtils.toJSONString(result);
						}
						
						double amountDouble=Double.parseDouble(amount);
						
						/**
						 * ��ѯ�����������ͼ۸�
						 */
						Map<String,String>flowmoneyMap=laiqiangService.getflowmoney(phone, Integer.parseInt(packageType));
						if(flowmoneyMap.get("status").equals("OK")){
							String priceStr=flowmoneyMap.get("price");
							double flowPrice=Double.parseDouble(priceStr);
							double priceNew=DoubleUtil.div(flowPrice, 100d);
							if(priceNew!=amountDouble){
								result.setSuccess(false);
								result.setMessage("�Ƿ�����");
								return FastJsonUtils.toJSONString(result);
							}
						}else{
							result.setSuccess(false);
							result.setMessage("�ֽ���������ʧ�ܣ��Ժ�����");
							return FastJsonUtils.toJSONString(result);
						}
						
						
						
						
						
						/**
						 * ���׶���
						 */
						LqTransactionOrder lqTransactionOrderBO=new LqTransactionOrder();
						String orderNo=lqOrderNoService.getOrderNo();
						lqTransactionOrderBO.setOrderNo(orderNo);
						lqTransactionOrderBO.setAmount(amountDouble);
						lqTransactionOrderBO.setRewardType("2");//�ֽ�
						lqTransactionOrderBO.setStatus(0);//δ֧��
						lqTransactionOrderBO.setCreatedTime(DateUtils.getDateTime());
						lqTransactionOrderBO.setTransactionTypeId(TransactionType.PurchaseFlow);//��������
						lqTransactionOrderBO.setMemberId(memberBO.getId());
						lqTransactionOrderBO.setPhoneNumber(phone);//��ֵ�ֻ�����
						lqTransactionOrderBO.setPackageType(packageType);//��ֵ����
						if(lqtype1==1){//��������
							//�ۼ����ۿ���
							if(price!=null && price.doubleValue()>0){
								lqTransactionOrderBO.setCardcodeId(Long.parseLong(codeid));//��ȯid
								amountDouble=amountDouble-price;
								lqTransactionOrderBO.setAmount(amountDouble);
								
								/**
								 * ����ʹ������
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
							result.setMessage("��������ʧ��");
							return FastJsonUtils.toJSONString(result);
						}
						
					}
					//���򻰷Ѱ�
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
							result.setMessage("amount��������Ϊ��");
							return FastJsonUtils.toJSONString(result);
						}
						Double amountDouble=Double.parseDouble(amount);
						
						
						
						/**
						 * ��ѯ���򻰷��ײͼ۸�
						 */
						Map<String,String>flowmoneyMap=laiqiangService.getChargepackagemoney(phone, Integer.parseInt(packageType));
						if(flowmoneyMap.get("status").equals("OK")){
							String priceStr=flowmoneyMap.get("price");
							double flowPrice=Double.parseDouble(priceStr);
							double priceNew=DoubleUtil.div(flowPrice, 100d);
							if(priceNew!=amountDouble){
								result.setSuccess(false);
								result.setMessage("�Ƿ�����");
								return FastJsonUtils.toJSONString(result);
							}
						}else{
							result.setSuccess(false);
							result.setMessage("�ֽ���������ʧ�ܣ��Ժ�����");
							return FastJsonUtils.toJSONString(result);
						}
						
						
						
						
						/**
						 * ���׶���
						 */
						LqTransactionOrder lqTransactionOrderBO=new LqTransactionOrder();
						String orderNo=lqOrderNoService.getOrderNo();
						lqTransactionOrderBO.setOrderNo(orderNo);
						lqTransactionOrderBO.setAmount(amountDouble);
						lqTransactionOrderBO.setRewardType("2");//�ֽ�
						lqTransactionOrderBO.setStatus(0);//δ֧��
						lqTransactionOrderBO.setCreatedTime(DateUtils.getDateTime());
						lqTransactionOrderBO.setTransactionTypeId(TransactionType.BuyCalls);//���򻰷�
						lqTransactionOrderBO.setMemberId(memberBO.getId());
						lqTransactionOrderBO.setPhoneNumber(phone);//��ֵ�ֻ�����
						lqTransactionOrderBO.setPackageType(packageType);//��ֵ���Ѱ�
						
						if(lqtype1==2){//���ѿ���
							//�ۼ����ۿ���
							if(StringUtils.isNotBlank(codeid)){
								lqTransactionOrderBO.setCardcodeId(Long.parseLong(codeid));//��ȯid
								if(null!=price && price.intValue()>0){
									amountDouble=amountDouble-price;
									lqTransactionOrderBO.setAmount(amountDouble);
								}
							}
							
							/**
							 * ����ʹ������
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
							result.setMessage("��������ʧ��");
							return FastJsonUtils.toJSONString(result);
						}
					}else{
						result.setSuccess(false);
						result.setMessage("�������Ͳ�ʶ��");
						return FastJsonUtils.toJSONString(result);
					}

					result.setData(params);
					result.setSuccess(true);
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}

	
	
	
	
	
	
	/**
	 * ����������1:���Ѷ���
	 * ���׶�����ѯ
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
					 * APP�û�ʶ��ID
					 */
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					String currentPageStr=postData.get("currentPage");
					if(StringUtils.isBlank(currentPageStr)){
						result.setSuccess(false);
						result.setMessage("currentPage��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					String pageSizeStr=postData.get("pageSize");
					if(StringUtils.isBlank(pageSizeStr)){
						result.setSuccess(false);
						result.setMessage("pageSize��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					Long currentPage=Long.parseLong(currentPageStr);
					Long pageSize=Long.parseLong(pageSizeStr);
					long start=(currentPage-1)*pageSize;
					long end=currentPage*pageSize-1;
					
					//0:����������1:���Ѷ���
					String type=postData.get("type");
					if(StringUtils.isBlank(type)){
						result.setSuccess(false);
						result.setMessage("type��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * ��Աid
					 */
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}
					
					Long memberId=memberBO.getId();
					List<Rechargelog> rechargelogList=null;
					DecimalFormat df = new DecimalFormat("##0.0#");
					double totalMoeny=0.0d;
					//��������
					if(type.equals("0")){
//						Long moneyTotal=rechargelogService.findRechargelogTotalMoney(memberId);
						//ʣ���������
						totalMoeny=memberBO.getCardbalance()*1d;
						rechargelogList=rechargelogService.findRechargelogList(memberId,start, end);
					}
					//���Ѷ���
					else if(type.equals("1")){
						Long moneyTotal=rechargelogService.findWxrechargeTotalMoney(memberId);
						totalMoeny=moneyTotal/100.0;
						rechargelogList=rechargelogService.findWxrechargeList(memberId,start, end);
					}else{
						result.setSuccess(false);
						result.setMessage("type����ֵ����");
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
						if(type.equals("0")){//����
							if(null!=failflow && null!=packageType){
								if(failflow.intValue()==packageType.intValue()){
									if(retype.equals("1") || retype.equals("3") || retype.equals("4") | retype.equals("5")){
										rechargelog.setRstatus("���˿�");
									}
								}
							}
							
						}else if(type.equals("1")){//����
							if(status==2){
								rechargelog.setRstatus("���˿�");
							}
						}
					}
					
					
					//��ֵ���
					params.put("totalMoeny", df.format(totalMoeny));
					
					params.put("rechargelogList", rechargelogList);
					
					result.setData(params);
					result.setSuccess(true);
					
				}else{
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			}else{
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		}catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	
	/**
	 * 
	 */
}
