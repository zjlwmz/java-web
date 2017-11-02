package cn.emay.laiqiang.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
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
import cn.emay.laiqiang.bo.Balancepackage;
import cn.emay.laiqiang.bo.CardBo;
import cn.emay.laiqiang.bo.LqMemberBO;
import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.bo.Wxorder;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.response.wxlq.CallsPackage;
import cn.emay.laiqiang.common.response.wxlq.FlowPacket;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.entity.LqTransactionOrder;
import cn.emay.laiqiang.entity.LqVerifyCode;
import cn.emay.laiqiang.service.CardBoService;
import cn.emay.laiqiang.service.LaiqiangService;
import cn.emay.laiqiang.service.LqMemberService;
import cn.emay.laiqiang.service.LqOrderNoService;
import cn.emay.laiqiang.service.LqTransactionOrderService;
import cn.emay.laiqiang.service.LqVerifyCodeService;
import cn.emay.laiqiang.service.MemberService;
import cn.emay.laiqiang.support.PaymentType;
import cn.emay.laiqiang.support.TransactionType;
import cn.emay.laiqiang.support.VerifyCodeType;

import com.alibaba.fastjson.JSON;

import example.SingletonClient;

/**
 * ����app�ӿ�
 * 
 * @author lenovo
 * 
 */
@Controller
@RequestMapping(value = "/laiqiang/app/service/")
public class LaiqiangController extends BaseController{

	private static Logger logger = Logger.getLogger(LaiqiangController.class.getName());

	
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
	 * ����app service�ӿ�
	 */
	@Autowired
	private LaiqiangService laiqiangService;
	
	
	/**
	 * ��Աservice�ӿ�
	 */
	@Autowired
	private MemberService memberService;
	
	
	/**
	 * app��Աservice�ӿ�
	 */
	@Autowired
	private LqMemberService lqMemberService;
	
	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private CardBoService cardBoService;
	
	
	
	/**
	 * ������֤��service�ӿ�
	 */
	@Autowired
	private LqVerifyCodeService lqVerifyCodeService;
	
	
	
	/**
	 * �������service�ӿ�
	 */
	@Autowired
	private LqOrderNoService lqOrderNoService;
	
	
	
	/**
	 * ���׶���service�ӿ�
	 */
	@Autowired
	private LqTransactionOrderService lqTransactionOrderService;
	
	
	
	/**
	 * �������������Ѱ��б�
	 * ��ֵ���б�
	 * @return
	 */
	@RequestMapping(value="findRechargeBag")
	@ResponseBody
	public String findRechargeBag(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.info("checkphoneflow:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					
					/**
					 * phone
					 */
					String phone=postData.get("phone");
					if(StringUtils.isBlank(phone)){
						result.setSuccess(false);
						result.setMessage("�������ֻ���");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * �绰����Ӫ��
					 */
					String catName=laiqiangService.getPhoneType(phone);
					params.put("catName", catName);
					if(StringUtils.isBlank(catName)){
						result.setSuccess(false);
						result.setMessage("�ֻ��Ŵ���");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * ������
					 */
					Map<String,Object> flowPacketMap=laiqiangService.checkphoneflow(phone);
					if(flowPacketMap.get("status").toString().equals("ERROR")){
						result.setSuccess(false);
						result.setMessage(flowPacketMap.get("data").toString());
						return FastJsonUtils.toJSONString(result);
					}
					String flowPacketData=flowPacketMap.get("data").toString();
					
					List<FlowPacket> flowPacketList=JSON.parseArray(flowPacketData, FlowPacket.class);
					for(FlowPacket flowPacket:flowPacketList){
						if(null!=flowPacket){
							String price=flowPacket.getPrice();
							double priced=Double.parseDouble(price)/100d; 
							flowPacket.setPrice(priced+"");
							
							
							//originalprice
							String originalprice=flowPacket.getOriginalprice();
							double originalpriced=Double.parseDouble(originalprice)/100d; 
							flowPacket.setOriginalprice(originalpriced+"");
							
						}
					}
					params.put("flowPacketList", flowPacketList);
					
					
					/**
					 * ���Ѱ�
					 */
					Map<String,Object> callsPackageMap=laiqiangService.checkphonerecharege(phone);
					if(callsPackageMap.get("status").toString().equals("ERROR")){
						result.setSuccess(false);
						result.setMessage(callsPackageMap.get("data").toString());
						return FastJsonUtils.toJSONString(result);
					}
					String callsPackageData=callsPackageMap.get("data").toString();
					List<CallsPackage> callsPackageList=JSON.parseArray(callsPackageData, CallsPackage.class);
					for(CallsPackage callsPackage : callsPackageList){
						if(null!=callsPackage){
							String price=callsPackage.getPrice();
							double priced=Double.parseDouble(price)/100d; 
							callsPackage.setPrice(priced+"");
							
							String originalprice=callsPackage.getOriginalprice();
							double originalpriced=Double.parseDouble(originalprice)/100d; 
							callsPackage.setOriginalprice(originalpriced+"");
							
						}
					}
					params.put("callsPackageList", callsPackageList);
					
					
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
	 * �绰����
	 * @return
	 */
	@RequestMapping(value="getPhoneType")
	@ResponseBody
	public String getPhoneType(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.info("checkphoneflow:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String phone=postData.get("phone");
					if(StringUtils.isBlank(phone)){
						result.setSuccess(false);
						result.setMessage("phone��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					String catName=laiqiangService.getPhoneType(phone);
					params.put("catName", catName);
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
	 * �ҵĿ���
	 */
	@RequestMapping(value="findCardBoList")
	@ResponseBody
	public String findCardBoList(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.info("findCardBoList:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}
					
					//-1���С�0δ��ʼ��1����ʹ�á� 2��ʧЧ��
					String type=postData.get("type");
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("type��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					List<CardBo> cardBoList=cardBoService.findCardBoByMemberId(memberBO.getId(),type);
					params.put("cardBoList", cardBoList);
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
	 * M��ֵ�һ��ӿ�
	 */
	@RequestMapping(value="exchangemcode")
	@ResponseBody
	public String exchangemcode(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		logger.info("sendSms:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}
					
					LqMemberBO lqMemberBO=lqMemberService.getByMemberId(memberBO.getId());
					
					String phoneNumber=postData.get("phoneNumber");
					if(StringUtils.isBlank(phoneNumber)){
						result.setSuccess(false);
						result.setMessage("phoneNumber��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * M��ֵ
					 */
					String mcode=postData.get("mcode");
					if(StringUtils.isBlank(phoneNumber)){
						result.setSuccess(false);
						result.setMessage("mcode��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					Map<String,String>changecodeparams=new HashMap<String, String>();
					changecodeparams.put("unionid", lqMemberBO.getUnionid());
					changecodeparams.put("phone", phoneNumber);
					changecodeparams.put("mcode", mcode);
					Map<String,String>changecodeResult=laiqiangService.exchangemcode(changecodeparams);
					if(changecodeResult.get("status").equals("OK")){
						result.setSuccess(true);
						return FastJsonUtils.toJSONString(result);
					}else{
						result.setSuccess(false);
						changecodeResult.get("status");
						result.setMessage(changecodeResult.get("data"));
						return FastJsonUtils.toJSONString(result);
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
	 * ����������֤��
	 */
	@RequestMapping(value="sendSms")
	@ResponseBody
	public String sendSms(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		logger.info("sendSms:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}
					
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String phoneNumber=postData.get("phoneNumber");
					if(StringUtils.isBlank(phoneNumber)){
						result.setSuccess(false);
						result.setMessage("phoneNumber��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					String type=postData.get("type");
					if(StringUtils.isBlank(phoneNumber)){
						result.setSuccess(false);
						result.setMessage("type��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					//��֤��
					LqVerifyCode lqVerifyCode=new LqVerifyCode();
					lqVerifyCode.setCreatedTime(DateUtils.getDateTime());
					lqVerifyCode.setPhoneNumber(phoneNumber);
					lqVerifyCode.setMemberId(memberBO.getId());
					String verifyCode=RandomStringUtils.randomNumeric(6);
					lqVerifyCode.setType(Short.parseShort(type));
					String value="";
					try{
						value = SingletonClient.getClient().sendVoice(new String[] { phoneNumber }, verifyCode, "", "GBK", 5, System.currentTimeMillis());
					}catch (Exception e) {
						logger.error("������֤�뷢�ͣ�", e);
					}
					if(StringUtils.isNotBlank(value)){
						lqVerifyCode.setVerifyCode(verifyCode);
						lqVerifyCode.setCode(value);
						lqVerifyCodeService.insert(lqVerifyCode);
						result.setSuccess(true);
					}else{
						result.setMessage("��֤�뷢��ʧ��");
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
	 * ������֤����֤
	 */
	@RequestMapping(value="validateSMS")
	@ResponseBody
	public String validateSMS(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		logger.info("validateSMS:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}
					
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String phoneNumber=postData.get("phoneNumber");
					if(StringUtils.isBlank(phoneNumber)){
						result.setSuccess(false);
						result.setMessage("phoneNumber��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					String verifyCode=postData.get("verifyCode");
					if(StringUtils.isBlank(verifyCode)){
						result.setSuccess(false);
						result.setMessage("verifyCode��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					boolean smsresult=lqVerifyCodeService.validateSMS(phoneNumber, VerifyCodeType.paymentPassword, verifyCode);
					if(smsresult){
						result.setSuccess(true);
					}else{
						result.setMessage("��֤�����");
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
	 * �����Ͱ�
	 */
	@RequestMapping(value="findBalancepackagelist")
	@ResponseBody
	public String findBalancepackagelist(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					List<Balancepackage>balancepackageList=laiqiangService.findBalancepackagelist();
					params.put("balancepackageList", balancepackageList);
					result.setSuccess(true);
					result.setData(params);
					
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
	
	
	
//	/**
//	 * ���Ķ�����ѯ
//	 */
//	@RequestMapping(value="findQpOrder")
//	@ResponseBody
//	public String findQpOrder(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
//		ResponeResultModel result = new ResponeResultModel();
//		Map<String, Object> params = new HashMap<String, Object>();
//		try {
//			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
//			if (null != postParams) {
//				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
//				if (validateMap.get("status").equals("OK")) {
//					@SuppressWarnings("unchecked")
//					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
//					
//					String uuid=postParams.getUuid();
//					if(StringUtils.isBlank(uuid)){
//						result.setSuccess(false);
//						result.setMessage("uuid��������Ϊ��");
//						return FastJsonUtils.toJSONString(result);
//					}
//					
//					MemberBO memberBO=memberService.getMember(uuid);
//					if(null==memberBO){
//						result.setSuccess(false);
//						result.setMessage("uuid����ֵ����");
//						return FastJsonUtils.toJSONString(result);
//					}
//					
//					
//					String orderId=postData.get("orderId");
//					if(StringUtils.isBlank(orderId)){
//						result.setSuccess(false);
//						result.setMessage("orderId��������Ϊ��");
//						return FastJsonUtils.toJSONString(result);
//					}
//					
//					Wxorder wxorder= laiqiangService.findQpOrder(orderId);
//					params.put("wxorder", wxorder);
//					result.setData(params);
//					result.setSuccess(true);
//				}else{
//					result.setMessage(validateMap.get("message").toString());
//					result.setSuccess(false);
//				}
//			}else{
//				result.setMessage("������ʽ����");
//				result.setSuccess(false);
//			}
//		}catch (Exception e) {
//			logger.error("�����쳣", e);
//			result.setSuccess(false);
//			result.setMessage("�����쳣");
//		}
//		return FastJsonUtils.toJSONString(result);
//	}
	

	
	
	
	
	/**
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
					
					

					
					String orderId=postData.get("orderId");
					if(StringUtils.isBlank(orderId)){
						result.setSuccess(false);
						result.setMessage("orderId��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * ��������
					 */
					Wxorder wxorder=laiqiangService.findQpOrder(orderId);
					if(null==wxorder.getOrderid()){
						result.setSuccess(false);
						result.setMessage("�ö���������");
						return FastJsonUtils.toJSONString(result);
					}
					
					Integer status=wxorder.getStatus();
					
					if(status.intValue()!=0){//0δ֧��
						result.setSuccess(false);
						result.setMessage("�ö������ܽ���֧��");
						return FastJsonUtils.toJSONString(result);
					}
					
					//�ܽ��
					int  total_fee=wxorder.getMoney();
					
					/**
					 * ���׶���
					 */
					LqTransactionOrder lqTransactionOrderBO=new LqTransactionOrder();
					//String orderNo=lqOrderNoService.getOrderNo();
					String orderNo=orderId;
					lqTransactionOrderBO.setOrderNo(orderNo);
					Double amountDouble=total_fee*1d/100d;
					lqTransactionOrderBO.setAmount(amountDouble);
					lqTransactionOrderBO.setRewardType("1");//
					lqTransactionOrderBO.setStatus(0);//δ֧��
					lqTransactionOrderBO.setCreatedTime(DateUtils.getDateTime());
					lqTransactionOrderBO.setFlowOrderNo(orderId);
					lqTransactionOrderBO.setTransactionTypeId(TransactionType.ShotPay);
					
					lqTransactionOrderBO.setPaymentId(PaymentType.wechat);
					
					
					lqTransactionOrderBO.setMemberId(memberBO.getId());
					int transtionOrderresult=lqTransactionOrderService.insert(lqTransactionOrderBO);
					
					
					if(transtionOrderresult>0){
						Unifiedorder unifiedorder=new Unifiedorder();
						unifiedorder.setAppid(appid);
						unifiedorder.setMch_id(mch_id);
						unifiedorder.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
						
						unifiedorder.setBody("����");
						
						//�̻�������
						unifiedorder.setOut_trade_no(orderNo);
						//�ܽ��
						unifiedorder.setTotal_fee(String.valueOf(total_fee));
						String ip=getIpAddr(req);
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
						result.setSuccess(false);
						result.setMessage("��������ʧ��");
					}
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
}
