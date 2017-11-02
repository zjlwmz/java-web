package cn.emay.laiqiang.web;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.emay.laiqiang.bo.LqInstallLogBO;
import cn.emay.laiqiang.bo.LqMemberBO;
import cn.emay.laiqiang.bo.LqMemberTaskBO;
import cn.emay.laiqiang.bo.LqParamBO;
import cn.emay.laiqiang.bo.LqTaskBO;
import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.bo.MemberaddressBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.DoubleUtil;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.FormatUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.InviteFriendsDTO;
import cn.emay.laiqiang.dto.MemberDTO;
import cn.emay.laiqiang.dto.RegisterWxUserDTO;
import cn.emay.laiqiang.entity.LqAccount;
import cn.emay.laiqiang.entity.LqAccountSeq;
import cn.emay.laiqiang.entity.LqInviteLog;
import cn.emay.laiqiang.entity.LqTransactionErrorLog;
import cn.emay.laiqiang.entity.LqTransactionOrder;
import cn.emay.laiqiang.entity.Memberflowlog;
import cn.emay.laiqiang.service.LaiqiangService;
import cn.emay.laiqiang.service.LqAccountSeqService;
import cn.emay.laiqiang.service.LqAccountService;
import cn.emay.laiqiang.service.LqActivityService;
import cn.emay.laiqiang.service.LqDrawCommissionLogService;
import cn.emay.laiqiang.service.LqInstallLogService;
import cn.emay.laiqiang.service.LqInvitationCodeService;
import cn.emay.laiqiang.service.LqInviteLogService;
import cn.emay.laiqiang.service.LqMemberService;
import cn.emay.laiqiang.service.LqMemberTaskService;
import cn.emay.laiqiang.service.LqParamService;
import cn.emay.laiqiang.service.LqSysInfoService;
import cn.emay.laiqiang.service.LqTaskService;
import cn.emay.laiqiang.service.LqTransactionErrorLogService;
import cn.emay.laiqiang.service.LqTransactionOrderService;
import cn.emay.laiqiang.service.LqVerifyCodeService;
import cn.emay.laiqiang.service.MemberService;
import cn.emay.laiqiang.service.MemberaddressService;
import cn.emay.laiqiang.service.MemberflowlogService;
import cn.emay.laiqiang.service.TransactionTypeService;
import cn.emay.laiqiang.support.CanDrawCommission;
import cn.emay.laiqiang.support.Direction;
import cn.emay.laiqiang.support.InterfaceReturnStatus;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.support.ParamName;
import cn.emay.laiqiang.support.ParamType;
import cn.emay.laiqiang.support.PaymentType;
import cn.emay.laiqiang.support.RewardType;
import cn.emay.laiqiang.support.TransactionOrderStatus;
import cn.emay.laiqiang.support.TransactionType;
import cn.emay.laiqiang.support.VerifyCodeType;

import com.alibaba.fastjson.JSON;


/**
 * ��Ա��Ϣ�ӿ�
 * 
 * @author zjlwm
 * @date 2016-11-25
 */
@Controller
@RequestMapping(value = "/laiqiang/app/member/")
public class MemberController extends BaseController {

	private static Logger logger = Logger.getLogger(MemberController.class.getName());

	
	/**
	 * ��Ա��Ϣ�ӿ�
	 */
	@Autowired
	private MemberService memberService;
	
	
	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private LaiqiangService laiqiangService;
	
	
	/**
	 * ��Ա�ջ���ַservice�ӿ�
	 */
	@Autowired
	private MemberaddressService memberaddressService;

	/**
	 * װ����־service�ӿ�
	 */
	@Autowired
	private LqInstallLogService installLogService;

	
	/**
	 * ������service�ӿ�
	 */
	@Autowired
	private LqInvitationCodeService lqInvitationCodeService;
	
	/**
	 * app�û�service�ӿ�
	 */
	@Autowired
	private LqMemberService lqMemberService;
	
	
	
	/**
	 * ��Ա�˺�service�ӿ�
	 */
	@Autowired
	private LqAccountService lqAccountService;
	
	
	
	/**
	 * ���׶���service�ӿ�
	 */
	@Autowired
	private LqTransactionOrderService lqTransactionOrderService;
	
	
	
	/**
	 * ������֤��service�ӿ�
	 */
	@Autowired
	private LqVerifyCodeService lqVerifyCodeService;
	
	
	
	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private LqTaskService lqTaskService;
	
	
	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private LqParamService lqParamService;
	
	
	
	/**
	 * ������־service�ӿ�
	 */
	@Autowired
	private LqInviteLogService lqInviteLogService;
	
	/**
	 * �˺�������־service�ӿ�
	 */
	@Autowired
	private MemberflowlogService memberflowlogService;
	
	
	/**
	 * �˻��ʽ���־service�ӿ�
	 */
	@Autowired
	private LqAccountSeqService lqAccountSeqService;
	
	
	/**
	 * �������service�ӿ�
	 */
	@Autowired
	private LqDrawCommissionLogService lqDrawCommissionLogService;
	
	
	
	
	/**
	 * ϵͳ��Ϣservice�ӿ�
	 */
	@Autowired
	private LqSysInfoService lqSysInfoService;
	
	
	
	/**
	 * ��Ա׬Ǯ����service�ӿ�
	 */
	@Autowired
	private LqMemberTaskService lqMemberTaskService;
	
	
	
	
	/**
	 * ��ֵ�����ӿڵ��ô�����־��¼
	 */
	@Autowired
	private LqTransactionErrorLogService lqTransactionErrorLogService;
	
	
	
	
	/**
	 * ��������
	 */
	@Autowired
	private TransactionTypeService transactionTypeService;
	
	
	
	/**
	 * �̳߳�
	 */
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	
	
	/**
	 * �service�ӿ�
	 */
	@Autowired
	private LqActivityService lqActivityService;
	
	
	
	
	/**
	 * �ӿ����ڵ�ַ
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	
	
	
	
	
	
	
	/**
	 * ��װ��־��¼
	 * @param data
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "installLog")
	@ResponseBody
	public String installLog(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		try {
			logger.error("login:" + data);
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					// �豸��
					String deviceType = postData.get("deviceType");
					// Ʒ��
					String brand = postData.get("brand");
					// �豸��
					String imei = postData.get("imei");
					// ƽ̨���ͣ�1:android ��2:IOS��
					String osType = postData.get("osType");
					if (StringUtils.isBlank(osType)) {
						result.setMessage("osType��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					// ����ϵͳ�汾
					String osVersion = postData.get("osVersion");
					
					//app�汾
					String appVersion = postData.get("appVersion");

					/**
					 * װ����־����
					 */
					LqInstallLogBO lqInstallLogBO = new LqInstallLogBO();
					lqInstallLogBO.setInstallTime(DateUtils.getDateTime());
					lqInstallLogBO.setImei(imei);
					lqInstallLogBO.setOsType(Short.parseShort(osType));
					lqInstallLogBO.setDeviceType(deviceType);
					lqInstallLogBO.setBrand(brand);
					lqInstallLogBO.setOsVersion(osVersion);
					lqInstallLogBO.setAppVersion(appVersion);
					installLogService.save(lqInstallLogBO);
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
	 * app��Ȩ��¼
	 * @return
	 */
	@RequestMapping(value = "login2")
	@ResponseBody
	public String login2(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			logger.info("login"+data);
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					RegisterWxUserDTO registerWxUserDTO=FastJsonUtils.toBean(postParams.getPostData(), RegisterWxUserDTO.class);
					String unionid = postData.get("unionid");
					if (StringUtils.isBlank(unionid)) {
						result.setMessage("unionid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					// �û���������id
					String pushId = postData.get("pushId");
					
					
					LqMemberBO lqMemberBO=lqMemberService.getByUnionid(unionid);
					MemberBO memberBO =null;
					LqAccount lqAccount=null;
					//���˻�δע�ᵽϵͳ��
					if(null==lqMemberBO){
						String registMemberKey=JedisKeyUtils.registMember+unionid;
						if(lqMemberService.jedisKeys.exists(registMemberKey)){
							result.setMessage("���Ժ�����");
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						/**
						 * �û�ע��
						 */
						lqMemberService.jedisStrings.setEx(registMemberKey, 5, DateUtils.getDateTime());
						Map<String,Object> registerMap=laiqiangService.findMember(registerWxUserDTO);
						if(registerMap.get("status").toString().equals("ERROR")){
							result.setMessage(registerMap.get("data").toString());
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						lqMemberService.jedisKeys.del(registMemberKey);
						
						/**
						 * ��Ա��Ϣ
						 */
						memberBO =JSON.parseObject(registerMap.get("data").toString(), MemberBO.class);
						
						/**
						 * app�û������ݴ���
						 */
						lqMemberBO=new LqMemberBO();
						lqMemberBO.setMemberId(memberBO.getId());
						lqMemberBO.setUuid(memberBO.getUuid());
						String invitationCode=lqInvitationCodeService.getInvitationCode(memberBO.getId());
						lqMemberBO.setInvitationCode(invitationCode);
						lqMemberBO.setPushId(pushId);
						
						/**
						 * ΢���û�Ψһ��ʶ
						 */
						lqMemberBO.setUnionid(unionid);
						
						/**
						 * app����˻�
						 */
						lqAccount=new LqAccount();
						lqAccount.setMemberId(memberBO.getId());
						lqAccount.setBalance(0.0d);
						lqAccount.setCreatedTime(DateUtils.getDateTime());
						lqAccount.setUpdatedTime(lqAccount.getCreatedTime());
						lqMemberService.insert(lqMemberBO,lqAccount);
						
					}else{
						memberBO=memberService.getMember(lqMemberBO.getUuid());
						lqAccount=lqAccountService.get(lqMemberBO.getMemberId());
						
						//���������û�id��Ϊ��
						if(StringUtils.isNotBlank(pushId)){
							String oldPushId=lqMemberBO.getPushId();
							if(StringUtils.isBlank(oldPushId) || !pushId.equals(oldPushId)){//��Ϊ�ջ������»�ȡ�Ĳ���ͬ��ʱ����и���
								lqMemberBO.setPushId(pushId);
								lqMemberService.updatePushId(lqMemberBO);
							}
						}
						
					}
					
					
					//�豸��
					String imei = postData.get("imei");
					if(StringUtils.isNotBlank(imei)){
						installLogService.updateLqInstallLogMemberId(imei, lqMemberBO.getMemberId());
					}
					
					
					/**
					 * �ӿ��û���������DTO
					 */
					MemberDTO memberDTO = new MemberDTO();
					BeanUtils.copyProperties(memberDTO, memberBO);
					memberDTO.setName(memberBO.getWxname());
					
					if(StringUtils.isBlank(lqMemberBO.getPayPassword())){
						memberDTO.setIsPayPass(0);//δ����
					}else{
						memberDTO.setIsPayPass(1);//������
					}
					
					/**
					 * token
					 */
					memberDTO.setToken(memberBO.getUuid());
					memberDTO.setBalance(lqAccount.getBalance());//�˺����
					
					params.put("member", memberDTO);
					result.setSuccess(true);
					result.setData(params);

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
	 * app��Ȩ��¼
	 * @return
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public String login(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			logger.info("login"+data);
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					RegisterWxUserDTO registerWxUserDTO=FastJsonUtils.toBean(postParams.getPostData(), RegisterWxUserDTO.class);
					String unionid = postData.get("unionid");
					if (StringUtils.isBlank(unionid)) {
						result.setMessage("unionid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					// �û���������id
					String pushId = postData.get("pushId");
					
					
					MemberBO memberBO =null;
					LqAccount lqAccount=null;
					
					/**
					 * �Ƿ��¼������APP
					 */
					boolean isLongin=lqMemberService.isExitRedisByUnionid(unionid);
					
					/**
					 * ��ѯAPP�û��Ƿ���ڻ���
					 */
					LqMemberBO lqMemberBO=lqMemberService.getByUnionid(unionid);
					
					
					boolean islongin=lqMemberService.isLogining(unionid);
					if(islongin){
						result.setMessage("���Ժ�����");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * �û�ע��
					 */
					lqMemberService.setLogining(unionid);
					Map<String,Object> registerMap=laiqiangService.findMember(registerWxUserDTO);
					if(registerMap.get("status").toString().equals("ERROR")){
						result.setMessage(registerMap.get("data").toString());
						result.setSuccess(false);
						lqMemberService.delLogining(unionid);
						return FastJsonUtils.toJSONString(result);
					}
					lqMemberService.delLogining(unionid);
					
					

					
					/**
					 * ��Ա��Ϣ
					 */
					memberBO =JSON.parseObject(registerMap.get("data").toString(), MemberBO.class);
					
					if(null==lqMemberBO){
						/**
						 * app�û������ݴ���
						 */
						lqMemberBO=new LqMemberBO();
						lqMemberBO.setMemberId(memberBO.getId());
						lqMemberBO.setUuid(memberBO.getUuid());
						String invitationCode=lqInvitationCodeService.getInvitationCode(memberBO.getId());
						lqMemberBO.setInvitationCode(invitationCode);
						lqMemberBO.setCreatedTime(DateUtils.getDateTime());
						lqMemberBO.setPushId(pushId);
						
						/**
						 * ΢���û�Ψһ��ʶ
						 */
						lqMemberBO.setUnionid(unionid);
						
						/**
						 * app����˻�
						 */
						lqAccount=new LqAccount();
						lqAccount.setMemberId(memberBO.getId());
						lqAccount.setBalance(0.0d);
						lqAccount.setCreatedTime(DateUtils.getDateTime());
						lqAccount.setUpdatedTime(lqAccount.getCreatedTime());
						lqMemberService.insert(lqMemberBO,lqAccount);
						
					}else{
						memberBO=memberService.getMember(lqMemberBO.getUuid());
						lqAccount=lqAccountService.get(lqMemberBO.getMemberId());
					}
					
					//���������û�id��Ϊ��
					if(StringUtils.isNotBlank(pushId)){
						String oldPushId=lqMemberBO.getPushId();
						if(StringUtils.isBlank(oldPushId) || !pushId.equals(oldPushId)){//��Ϊ�ջ������»�ȡ�Ĳ���ͬ��ʱ����и���
							lqMemberBO.setPushId(pushId);
							lqMemberService.updatePushId(lqMemberBO);
						}
					}
					
					
					
					
					//�豸��
					String imei = postData.get("imei");
					if(StringUtils.isNotBlank(imei)){
						installLogService.updateLqInstallLogMemberId(imei, lqMemberBO.getMemberId());
					}
					
					
					/**
					 * �ӿ��û���������DTO
					 */
					MemberDTO memberDTO = new MemberDTO();
					BeanUtils.copyProperties(memberDTO, memberBO);
					memberDTO.setName(memberBO.getWxname());
					
					if(StringUtils.isBlank(lqMemberBO.getPayPassword())){
						memberDTO.setIsPayPass(0);//δ����
					}else{
						memberDTO.setIsPayPass(1);//������
					}
					
					/**
					 * token
					 */
					memberDTO.setToken(memberBO.getUuid());
					memberDTO.setBalance(lqAccount.getBalance());//�˺����
					
					params.put("member", memberDTO);
					result.setSuccess(true);
					result.setData(params);

					final long memeberId=memberBO.getId();
					final boolean isLonginNew=isLongin;
					final String imeiNew=imei;
					try{
						taskExecutor.execute(new Runnable() {
							@Override
							public void run() {
								//�״ε�¼
								if(!isLonginNew){
									lqActivityService.activityReward(memeberId,imeiNew);
								}
							}
						});
					}catch (Exception e) {
						
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
	
	/**
	 * ��ȡ��Ա��Ϣ
	 * 
	 * @return
	 */
	@RequestMapping(value = "getMemberInfo")
	@ResponseBody
	public String getMemberInfo(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			logger.info("getMemberInfo:" + data);
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setMessage("uuid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}

					MemberBO memberBO = memberService.getMember(uuid);
					MemberDTO memberDTO = new MemberDTO();
					BeanUtils.copyProperties(memberDTO, memberBO);
					memberDTO.setName(memberBO.getWxname());
					
					Integer authstatus=memberDTO.getAuthstatus();
					if(null==authstatus){
						memberDTO.setAuthstatus(0);//δ��֤
					}
					
					/**
					 * token
					 */
					memberDTO.setToken(memberBO.getUuid());
					LqMemberBO lqMemberBO=lqMemberService.getByUuid(uuid);
					if(StringUtils.isBlank(lqMemberBO.getPayPassword())){
						memberDTO.setIsPayPass(0);//δ����
					}else{
						memberDTO.setIsPayPass(1);//������
					}
					LqAccount lqAccount=lqAccountService.get(memberBO.getId());
					memberDTO.setBalance(lqAccount.getBalance());
					params.put("member", memberDTO);
					result.setSuccess(true);
					result.setData(params);
				} else {
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
				}
			} else {
				result.setSuccess(false);
				result.setMessage("������ʽ����");
			}
		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}

	

	
	
	
	
	/**
	 * ��Ա��ַ�б�
	 */
	@RequestMapping(value = "getMemberAddressList")
	@ResponseBody
	public String getMemberAddressList(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try{
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setMessage("uuid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * ��Աid
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO) {
						result.setMessage("unionid��������");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					List<MemberaddressBO>memberaddressBOList=memberaddressService.findMemberaddressBOList(memberBO.getUuid());

					params.put("memberaddressList", memberaddressBOList);
					result.setData(params);
					result.setSuccess(true);
				}else{
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 * ��Ա��ַ����
	 */
	@RequestMapping(value = "memberAddressSave")
	@ResponseBody
	public String memberAddressSave(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.error("memberAddressSave:" + data);
		try{
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setMessage("uuid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * ��Աid
					 */
					MemberBO memberBO  = memberService.getMember(uuid);
					if (null == memberBO) {
						result.setMessage("uuid��������");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					MemberaddressBO memberaddressBO = FastJsonUtils.toBean(postParams.getPostData(), MemberaddressBO.class);
					
					if(StringUtils.isBlank(memberaddressBO.getName())){
						result.setMessage("name��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}else{
						memberaddressBO.setName(URLDecoder.decode(memberaddressBO.getName(), "UTF-8"));
					}
					
					
					if(StringUtils.isBlank(memberaddressBO.getPhone())){
						result.setMessage("phone��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					if(StringUtils.isBlank(memberaddressBO.getAddress())){
						result.setMessage("address��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}else{
						memberaddressBO.setAddress(URLDecoder.decode(memberaddressBO.getAddress(), "UTF-8"));
					}
					
					
					if(StringUtils.isBlank(memberaddressBO.getProvince())){
						result.setMessage("province��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}else{
						memberaddressBO.setProvince(URLDecoder.decode(memberaddressBO.getProvince(), "UTF-8"));
					}
					
					if(StringUtils.isBlank(memberaddressBO.getCity())){
						result.setMessage("city��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}else{
						memberaddressBO.setCity(URLDecoder.decode(memberaddressBO.getCity(), "UTF-8"));
					}
					
					if(StringUtils.isBlank(memberaddressBO.getZone())){
						result.setMessage("zone��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}else{
						memberaddressBO.setZone(URLDecoder.decode(memberaddressBO.getZone(), "UTF-8"));
					}
					
					if(StringUtils.isNotBlank(memberaddressBO.getId())){
						memberaddressService.update(memberBO.getUuid(), memberaddressBO);//��ַ����
					}else{
						memberaddressService.insert(memberBO.getUuid(), memberaddressBO);//��ַ����
					}
					
					result.setData(params);
					result.setSuccess(true);
				}else{
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 * �޸Ļ�Ա��ַ
	 */
	@RequestMapping(value = "updateMemberAddress")
	@ResponseBody
	public String updateMemberAddress(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			logger.info("memberAddressSetUp:" + data);
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setMessage("uuid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}

					
					/**
					 * ��Ա��Ϣ
					 */
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid��������");
						return FastJsonUtils.toJSONString(result);
					}
					
					String addressId = postData.get("addressId");
					if (StringUtils.isBlank(addressId)) {
						result.setMessage("addressId��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					MemberaddressBO memberaddressBO=memberaddressService.get(addressId, memberBO.getUuid());
					if (null==memberaddressBO) {
						result.setMessage("addressId��������");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					//0ɾ����1����ΪĬ�ϵ�ַ
					String type = postData.get("type");
					if (StringUtils.isBlank(type)) {
						result.setMessage("type��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					//ɾ��
					if(type.equals("0")){
						memberaddressService.delete(memberBO.getUuid(), memberaddressBO);
						result.setMessage("ɾ���ɹ�");
					}
					//����ΪĬ�ϵ�ַ
					else if(type.equals("1")){
						memberaddressService.setMemberAddressDefault(memberBO.getUuid(),addressId);
						result.setMessage("����Ĭ�ϳɹ�");
					}
					result.setSuccess(true);
					result.setData(params);
				} else {
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 * ��ȡ������Ϣ�ӿ�
	 */
	@RequestMapping(value = "getInvitation")
	@ResponseBody
	public String getInvitation(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			logger.info("getMemberInfo:" + data);
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setMessage("uuid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}

					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setMessage("uuid����ֵ����");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					LqMemberBO lqmemberBO=lqMemberService.getByUuid(uuid);
					//������
					params.put("inviteCode", lqmemberBO.getInvitationCode());
					
					//����ĺ��Ѹ���
					params.put("invitedFriends", lqMemberService.getinvitedFriends(memberBO.getId()));
					
//					Integer focusnum=memberBO.getFocusnum();
//					if(null==focusnum){
//						focusnum=0;
//					}
//					params.put("invitedFriends", focusnum);
					
					
					//�����ṩ��������
					Double totalFlowRate=lqDrawCommissionLogService.getTotalFlowRate(memberBO.getId());
					params.put("totalFlowRate", totalFlowRate.intValue());
					
					//�����ṩ�ܽ������
					Double totalAmountOfIncome=lqDrawCommissionLogService.getTotalAmountOfIncome(memberBO.getId());
					params.put("totalAmountOfIncome", totalAmountOfIncome);
					
					Long inviter=memberBO.getMemberid();
					if(null!=inviter){
						//�Ƿ�����
						params.put("beInvited", "1");
						MemberBO invitationMemberBO=memberService.getById(inviter);
						if(null!=invitationMemberBO){
							params.put("invitationName", invitationMemberBO.getWxname());//�����ҵĻ�Ա����
						}else{
							params.put("invitationName", "");//�����ҵĻ�Ա����
						}
					}else{
						//�Ƿ�����
						params.put("beInvited", "0");
						params.put("invitationName", "");//�����ҵĻ�Ա����
					}
					
					//app���ض�ά��
					String inviteBarcode=lqSysInfoService.getLqSysInfoInviteBarcode();
					params.put("qrCode",inviteBarcode);
					
					//ͼƬ˵��
					String imageUrl=lqSysInfoService.getLqSysInfoInviteImage();
					params.put("imageUrl", imageUrl);
					//����˵��
					String inviteDescUrl=domain+"laiqiang/app/sysInfo/html/inviteDesc";
					params.put("invDesUrl", inviteDescUrl);
					//������
					String invIntroduction=lqSysInfoService.getLqSysInfoInviteBriefIntro();
					params.put("invIntroduction", invIntroduction);
					
					
					//����ҳ�����ӵ�ַ
//					String invitePageUrl=domain+"laiqiang/app/sysInfo/html/invitePage";
					String invitePageUrl=lqSysInfoService.getInvitePageUrl();
					params.put("invitePageUrl", invitePageUrl);
					
					//�������
					String pageTitle=lqSysInfoService.getInvitePageTitle();
					params.put("pageTitle", pageTitle+"�ҵ������룺"+lqmemberBO.getInvitationCode());
					
					
					//����ҳ��->����ͼƬ
					String invitepageCover=lqSysInfoService.getInvitepageCover();
					params.put("invitepageCover", invitepageCover);
					
					
					//����ҳ��->������
					String pageBriefIntro=lqSysInfoService.getLqSysInfoPageBriefIntro();
					params.put("pageBriefIntro", pageBriefIntro);
					
					
					
					result.setSuccess(true);
					result.setData(params);
				} else {
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 * �û�����
	 * ��ȡ�����б�
	 */
	@RequestMapping(value = "getIncomeList")
	@ResponseBody
	public String getIncomeList(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			logger.error("getIncomeList:" + data);
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setMessage("unionid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setMessage("uuid����ֵ����");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					//type 0:����ĺ����б�1�������ṩ������
					String type=postData.get("type");
					if (StringUtils.isBlank(type)) {
						result.setMessage("type��������Ϊ��");
						result.setSuccess(false);
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
					
					if(type.equals("0")){//����ĺ����б�
						List<InviteFriendsDTO>inviteFriendsDTOList=lqInviteLogService.findLqInvitelogList(memberBO.getId(), start, end);
						params.put("inviteFriendsList", inviteFriendsDTOList);
						result.setSuccess(true);
						result.setData(params);
					}else if(type.equals("1")){
						//�����ṩ������
						List<InviteFriendsDTO>inviteFriendsDTOList=lqDrawCommissionLogService.findLqDrawCommissionLogList(memberBO.getId(), start, end);
						params.put("inviteFriendsList", inviteFriendsDTOList);
						result.setSuccess(true);
						result.setData(params);
					}else{
						result.setSuccess(false);
						result.setMessage("type��������");
						return FastJsonUtils.toJSONString(result);
					}
					
					
				} else {
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 * �Ƽ�
	 * ��д������
	 */
	@RequestMapping(value = "invitationRevenue")
	@ResponseBody
	public String invitationRevenue(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			logger.error("getMemberInfo:" + data);
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setMessage("unionid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					
					/**
					 * wx�û���
					 * ��������
					 */
					MemberBO beInvitedMemberBO=memberService.getMember(uuid);
					if(null==beInvitedMemberBO){
						result.setMessage("uuid����ֵ����");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					if(null!=beInvitedMemberBO.getMemberid()){
						result.setMessage("���ѱ��������");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * app�û���
					 * ��������
					 */
					LqMemberBO beInvitedLqMemberBO=lqMemberService.getByMemberId(beInvitedMemberBO.getId());
					//������ID
//					Long inviter=beInvitedLqMemberBO.getInviter();
					
//					if(inviter!=null){
//						result.setMessage("���ѱ��������");
//						result.setSuccess(false);
//						return FastJsonUtils.toJSONString(result);
//					}
					
					//������
					String inviteCode=postData.get("inviteCode");
					if(StringUtils.isBlank(inviteCode)){
						result.setMessage("inviteCode��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * ��֤������
					 * ������
					 */
					LqMemberBO inviteLqMemberBO=lqMemberService.getByInvitationCode(inviteCode);
					if(null==inviteLqMemberBO){
						result.setMessage("���������");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					
					/**
					 * �����벻���ύ�Լ���
					 */
					if(inviteLqMemberBO.getMemberId().longValue()==beInvitedLqMemberBO.getMemberId().longValue()){
						result.setMessage("������д�Լ���������Ŷ");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					Long inviterB=inviteLqMemberBO.getInviter();
					
					


					/**
					 * ��֤ 
					 * A����������B and  B����������A
					 */
					if(null!=inviterB && inviterB.longValue()==beInvitedMemberBO.getId().longValue()){
						result.setMessage("�����໥ͬʱ����Է���");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * ��ȡ�Ƽ�����ֵ ����
					 */
					LqParamBO lqParamBO=lqParamService.getParamValue(ParamName.inviteCommission);
					if(ParamType.flow==lqParamBO.getValueType().intValue()){
						int fownum=lqParamBO.getParamValue().intValue();
						if(fownum==0){
							result.setMessage("ϵͳ��������ֵ�쳣������ϵ�ͻ�");
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
					}else if(ParamType.cash==lqParamBO.getValueType().intValue()){
						double rewardQuantity=lqParamBO.getParamValue();//���뽱��
						if(rewardQuantity<=0){
							result.setMessage("ϵͳ��������ֵ�쳣������ϵ�ͻ�");
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
					}
					
					
					
					String updatememberinvitlogKey=JedisKeyUtils.updatememberinvitlog+beInvitedMemberBO.getMemberid();
					boolean resultInvitlog=lqMemberService.isIntefaceIng(updatememberinvitlogKey);
					if(resultInvitlog){
						result.setMessage("���Ժ�����");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					lqMemberService.setIntefaceIng(updatememberinvitlogKey);
					/**
					 * ����΢�Žӿ���֤
					 */
					Map<String,String>updatememberinvitlogParams=new HashMap<String, String>();
					updatememberinvitlogParams.put("iunionid", inviteLqMemberBO.getUnionid());
					updatememberinvitlogParams.put("biunionid", beInvitedLqMemberBO.getUnionid());
					Map<String,String>resultMap=laiqiangService.updatememberinvitlog(updatememberinvitlogParams);
					if(resultMap.get("status").equals("ERROR")){
						String msg=resultMap.get("data");
						result.setMessage(msg);
						result.setSuccess(false);
						lqMemberService.delIntefaceIng(updatememberinvitlogKey);
						return FastJsonUtils.toJSONString(result);
					}
					lqMemberService.delIntefaceIng(updatememberinvitlogKey);
					
					
					
					
					
					/**
					 * ��������������
					 */
					
					
					
					
					//������ID
					Long inviteMemberId=inviteLqMemberBO.getMemberId();//������id
					//��������ID
					Long beInvitedMemberId=beInvitedLqMemberBO.getMemberId();//��������id
					/**
					 * ��ȡ�Ƽ�����ֵ ����
					 */
					if(ParamType.flow==lqParamBO.getValueType().intValue()){//����
						int fownum=lqParamBO.getParamValue().intValue();
						/**
						 * �����˽���
						 */
						Map<String,String>updateBalanceParams=new HashMap<String, String>();
						updateBalanceParams.put("unionid", inviteLqMemberBO.getUnionid());//������uuid
						updateBalanceParams.put("flow", fownum+"");
						updateBalanceParams.put("descr","��������");
						updateBalanceParams.put("type", TransactionType.LanewAward+"");//���½���
						updateBalanceParams.put("orderid", inviteCode);
						Map<String,String>updateBalanceResult=laiqiangService.updatememberbalance(updateBalanceParams);
						
						/**
						 * ����ɹ�
						 */
						if(updateBalanceResult.get("status").equals("OK")){
							
							/**
							 * �������˽���
							 */
							updateBalanceParams.put("unionid", beInvitedLqMemberBO.getUnionid());
							updateBalanceParams.put("flow", fownum+"");
							updateBalanceParams.put("descr","����������");
							updateBalanceParams.put("type", TransactionType.LanewAward+"");//�Ƽ�����
							updateBalanceParams.put("orderid", inviteCode);
							Map<String,String>beInvitedupdateBalanceResult=laiqiangService.updatememberbalance(updateBalanceParams);
							if(beInvitedupdateBalanceResult.get("status").equals("ERROR")){
								/**
								 * ������־��¼...
								 */
								logger.error(beInvitedupdateBalanceResult.get("message"));
							}else{
								/**
								 * �����˺� ���½�������
								 */
								lqAccountService.updateLqAccountTotalflow(beInvitedMemberId, TransactionType.RecommendedAward, fownum);
							}
							
							
							
							//---------------------������־����-----------------
							//����������
							beInvitedLqMemberBO.setInviter(inviteLqMemberBO.getMemberId());
							LqInviteLog lqInviteLog=new LqInviteLog();
							lqInviteLog.setInviter(inviteMemberId);//������id
							lqInviteLog.setInvitee(beInvitedMemberId);//������id
							
							lqInviteLog.setInviteeCommission(fownum*1.0d);//�����˽���
							lqInviteLog.setInviterCommission(fownum*1.0d);//�����˽���
							lqInviteLog.setRewardType(RewardType.flow);//����
							lqInviteLog.setCreatedTime(DateUtils.getDateTime());
							
							lqMemberService.updateInviter(beInvitedLqMemberBO,lqInviteLog);
							
							
							/**
							 *  ��������������
							 */
							lqMemberService.updateInvitationNumber(inviteLqMemberBO);
							
							
							/**
							 * �����˺� ���½�������
							 */
							lqAccountService.updateLqAccountTotalflow(inviteMemberId, TransactionType.RecommendedAward, fownum);
							
							
							result.setSuccess(true);
							result.setMessage("����ɹ�");
						}else{
							result.setSuccess(false);
							result.setMessage("����ʧ�ܡ�����ͷ���ϵ");
						}
					}else if(ParamType.cash==lqParamBO.getValueType().intValue()){//�ֽ�
						//--------------�����˽���-----------------------------
						
						LqAccount lqAccount=lqAccountService.get(inviteMemberId);
						double rewardQuantity=lqParamBO.getParamValue();//���뽱��
						LqAccountSeq lqAccountSeq=new LqAccountSeq();
						lqAccountSeq.setMemberId(inviteMemberId);
						lqAccountSeq.setDirection(Direction.enter);
						lqAccountSeq.setTransactionTypeId(TransactionType.RecommendedAward);
						lqAccountSeq.setTransactionAmount(rewardQuantity);
						lqAccountSeq.setPreBalance(lqAccount.getBalance());
						lqAccountSeq.setBalance(lqAccount.getBalance()+rewardQuantity);
						lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
						lqAccountSeq.setRemarks("�Ƽ�����");
						//�˺��ʽ����
						lqAccountService.updateAccount(inviteMemberId, lqAccountSeq);
						//�˺��ʽ����
						lqAccountService.updateLqAccountTotalCash(inviteMemberId, TransactionType.RecommendedAward, rewardQuantity);
						
						
						
						
						
						//---------------�����˽���------------------------------
						LqAccount beInvitedlqAccount=lqAccountService.get(beInvitedMemberId);
						//�ʽ���ˮ
						LqAccountSeq beInvitedlqAccountSeq=new LqAccountSeq();
						beInvitedlqAccountSeq.setMemberId(beInvitedMemberId);
						beInvitedlqAccountSeq.setDirection(Direction.enter);
						beInvitedlqAccountSeq.setTransactionTypeId(TransactionType.RecommendedAward);
						beInvitedlqAccountSeq.setTransactionAmount(rewardQuantity);
						beInvitedlqAccountSeq.setPreBalance(beInvitedlqAccount.getBalance());
						beInvitedlqAccountSeq.setBalance(beInvitedlqAccount.getBalance()+rewardQuantity);
						beInvitedlqAccountSeq.setCreatedTime(DateUtils.getDateTime());
						beInvitedlqAccountSeq.setRemarks("���Ƽ�����");
						//�˺��ʽ����
						lqAccountService.updateAccount(beInvitedMemberId, lqAccountSeq);
						//�˺��ʽ����
						lqAccountService.updateLqAccountTotalCash(beInvitedMemberId, TransactionType.RecommendedAward, rewardQuantity);
						
						
						
						//--------------------������־����-------------------------
						//����������
						beInvitedLqMemberBO.setInviter(inviteLqMemberBO.getMemberId());
						//������־��¼
						LqInviteLog lqInviteLog=new LqInviteLog();
						lqInviteLog.setInviter(inviteMemberId);//������id
						lqInviteLog.setInvitee(beInvitedMemberId);//������id
						
						lqInviteLog.setInviteeCommission(rewardQuantity);//�����˽���
						lqInviteLog.setInviterCommission(rewardQuantity);//�����˽���
						lqInviteLog.setRewardType(RewardType.cash);//�ֽ�
						lqInviteLog.setCreatedTime(DateUtils.getDateTime());
						
						lqMemberService.updateInviter(beInvitedLqMemberBO,lqInviteLog);
						
						
						result.setSuccess(true);
						result.setMessage("����ɹ�");
					}else{
						result.setSuccess(false);
						result.setMessage("�Ƽ���������δ֪");
					}
				} else {
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 * ǩ����ȡ����
	 */
	@RequestMapping(value = "signTaskRevenue")
	@ResponseBody
	public String signTaskRevenue(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			logger.error("getMemberInfo:" + data);
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setMessage("unionid��������Ϊ��");
						result.setSuccess(false);
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
					
					LqAccount lqAccount=lqAccountService.get(memberBO.getId());
					if(null==lqAccount){
						result.setSuccess(false);
						result.setMessage("uuid����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}
					/**
					 * ����ID
					 */
					String taskId=postData.get("taskId");
					if(StringUtils.isBlank(taskId)){
						result.setMessage("taskId��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					

					LqTaskBO lqTaskBO=lqTaskService.get(taskId);
					if(null==lqTaskBO){
						result.setMessage("taskIdֵ����");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					LqMemberTaskBO lqMemberTaskBO  =lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if(null==lqMemberTaskBO){
						result.setMessage("������δ�����޷���ȡǩ������");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					int status=lqMemberTaskBO.getStatus();
					if(status!=4){
						result.setMessage("������δ��ɣ��޷���ȡǩ������");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					
					
					String startTime=lqMemberTaskBO.getAuditTime().split(" ")[0];
					String currentDate=DateUtils.getDate("yyyy-MM-dd");
					long disDay=DateUtils.getDistanceDays(startTime, currentDate);
					
//					//���Ƶ��������ǩ�������޷���ȡ
//					if(disDay==0){
//						result.setMessage("�������޷���ȡǩ��������");
//						result.setSuccess(false);
//						return FastJsonUtils.toJSONString(result);
//					}
					/**
					 * ��Чǩ������
					 */
					int signinDays=lqTaskBO.getSigninDays();
					
					/**
					 * �Ƿ�����Чǩ������
					 */
					if(!(disDay<=signinDays && disDay>0)){
						result.setMessage("δ����Чǩ���������޷���ȡǩ��������");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * �������
					 */
					Double rewardQuantity=lqTaskBO.getSigninReward();
					
					
					/**
					 * ��������(1:������2���ֽ�)
					 */
					Integer rewardType=lqTaskBO.getRewardType();
					
					/**
					 * �Ƿ��������
					 */
					String canDrawCommission=transactionTypeService.getTransactionCanDrawCommissionByTransactionTypeId(TransactionType.DownloadBonus);
					
					
					if(rewardType==RewardType.flow){//����
						Long signInCount=memberflowlogService.findTaskSigninRewardCount(memberBO.getId(), taskId,TransactionType.SigninReward);
						if(signInCount>0){
							result.setMessage("������ǩ��");
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						
						/**
						 * ���������˺������ӿ�
						 */
						Map<String,String>updateBalanceParams=new HashMap<String, String>();
						updateBalanceParams.put("unionid", memberBO.getUnionid());
						updateBalanceParams.put("flow", rewardQuantity.intValue()+"");
						updateBalanceParams.put("descr", "ǩ������");
						updateBalanceParams.put("type", TransactionType.SigninReward+"");//ǩ������
						updateBalanceParams.put("orderid", taskId);//
						
						Map<String,String>updateBalanceResult=laiqiangService.updatememberbalance(updateBalanceParams);
						if(updateBalanceResult.get("status").equals("OK")){
							/**
							 * �����˺�ǩ����������
							 */
							lqAccountService.updateLqAccountTotalflow(memberBO.getId(), TransactionType.SigninReward, rewardQuantity.intValue());
							lqMemberTaskService.updateTotalReward(lqMemberTaskBO.getId(), rewardQuantity.intValue()*1d);//����׬ȡ�����ܶ�
							if(canDrawCommission.equals(CanDrawCommission.yes)){//���
								LqParamBO lqParamBO =lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);
								Double paramValue=lqParamBO.getParamValue();
								Double commissionMoney=rewardQuantity*paramValue;//�������
								int flowcommisson=commissionMoney.intValue();
								double dd=commissionMoney-flowcommisson;
								if(dd>=0.8){
									flowcommisson+=1;
								}
								if(flowcommisson>0){
									lqAccountService.updateMemberFlowCommission(memberBO.getId(), TransactionType.DownloadBonus, taskId, flowcommisson);
								}
								
							}
							
							//���������ǩ����¼
							lqMemberTaskService.taskTodaySign(memberBO.getId(), taskId, DateUtils.getDate("yyyyMMdd"));
							
							result.setSuccess(true);
							result.setData(params);
						}else{
							String message=updateBalanceResult.get("message");
							result.setSuccess(false);
							result.setMessage(message);
							result.setData(params);
						}
					}else if(rewardType==RewardType.cash){//�ֽ�
						Long signInCount=lqAccountSeqService.findTaskSigninRewardCount(memberBO.getId(), taskId,TransactionType.SigninReward);
						if(signInCount>0){
							result.setMessage("������ǩ��");
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						LqAccountSeq lqAccountSeq=new LqAccountSeq();
						lqAccountSeq.setMemberId(memberBO.getId());
						lqAccountSeq.setDirection(Direction.enter);
						lqAccountSeq.setTransactionTypeId(TransactionType.SigninReward);
						lqAccountSeq.setTransactionAmount(rewardQuantity);
						lqAccountSeq.setPreBalance(lqAccount.getBalance());
						
						//�˻����
						Double balance=DoubleUtil.add(lqAccount.getBalance(), rewardQuantity);
						//��double���͵����ֱ�����λС�����������룩
						String balanceStr=FormatUtils.formatNumber(balance);
						double newBlance=Double.parseDouble(balanceStr);
						
						lqAccountSeq.setBalance(newBlance);
						lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
						lqAccountSeq.setRemarks("����ǩ������");
						lqAccountSeq.setTaskId(Long.parseLong(lqTaskBO.getId()));
						
						/**
						 * �����ʽ��˺�
						 */
						lqAccountService.updateAccount(memberBO.getId(), lqAccountSeq);
						
						/**
						 * �����˺�ǩ������������
						 */
						lqAccountService.updateLqAccountTotalCash(memberBO.getId(), TransactionType.SigninReward, rewardQuantity);
						lqMemberTaskService.updateTotalReward(lqMemberTaskBO.getId(), rewardQuantity);//����׬ȡ�����ܶ�
						
						//���������ǩ����¼
						lqMemberTaskService.taskTodaySign(memberBO.getId(), taskId, DateUtils.getDate("yyyyMMdd"));
						
						
						if(canDrawCommission.equals(CanDrawCommission.yes)){//���
							LqParamBO lqParamBO =lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);
							Double paramValue=lqParamBO.getParamValue();
							
							//��ɽ��
							double commissionMoney=DoubleUtil.mul(rewardQuantity, paramValue);
							String commissionMoneyStr=FormatUtils.formatNumber(commissionMoney);
							double newcommissionMoney=Double.parseDouble(commissionMoneyStr);
							
							lqAccountService.updateMemberCashCommission(memberBO.getId(), TransactionType.DownloadBonus, taskId, newcommissionMoney);
						}
						
						
						result.setSuccess(true);
						result.setData(params);
					}
				} else {
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 *  �����޸�֧������
	 * @param data
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatePassword")
	public String updatePassword(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		try {
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					/**
					 * APP�û�ʶ��ID
					 */
					String uuid = postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setMessage("uuid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Աid
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO) {
						result.setMessage("uuid����ֵ����");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					//
					/**
					 * 0:δ��֤��1����֤
					 * �Ƿ�����ֻ�����
					 * 
					 */
					String phoneNumber="";
					Integer authstatus=memberBO.getAuthstatus();
					if(authstatus==null)authstatus=0;
					if(null!=authstatus && authstatus.intValue()==1){//����֤
						phoneNumber=memberBO.getPhone();
					}else{
						//δ��֤
						phoneNumber=postData.get("phoneNumber");
						if(StringUtils.isBlank(phoneNumber)){
							result.setSuccess(false);
							result.setMessage("phoneNumber��������Ϊ��");
							return FastJsonUtils.toJSONString(result);
						}
					}
					
					
					/**
					 * ��֤����֤
					 */
					String verifyCode=postData.get("verifyCode");
					if(StringUtils.isBlank(verifyCode)){
						result.setSuccess(false);
						result.setMessage("verifyCode��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					//֧������
					String rawPassword=postData.get("password");
					if(StringUtils.isBlank(rawPassword)){
						result.setMessage("password����ֵ����Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					if(rawPassword.length()>50){
						result.setMessage("password���ȹ���");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * ��֤���Ƿ���ȷ
					 */
					boolean smsresult=lqVerifyCodeService.validateSMS(phoneNumber, VerifyCodeType.paymentPassword, verifyCode);
					if(smsresult){
						//δ��֤
						if(authstatus==0){
							/**
							 * �ֻ�����֤���
						 	 */
							boolean phoneResult=laiqiangService.validatePhone(memberBO.getId(), phoneNumber);
							if(phoneResult){
								result.setMessage("���ֻ����Ѿ��������˺Ű󶨣�������������ͷ�����");
								result.setSuccess(false);
								return FastJsonUtils.toJSONString(result);
							}else{
								/**
								 * ���ֻ�����
								 */
								laiqiangService.bindingPhone(memberBO, phoneNumber);
							}
						}
						
						
						
						lqMemberService.updatePassword(uuid, rawPassword);
						
						result.setSuccess(true);
						return FastJsonUtils.toJSONString(result);
					}else{
						result.setMessage("��֤�����");
						result.setSuccess(false);
					}
					
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
					return FastJsonUtils.toJSONString(result);
				}
			}
		} catch (Exception e) {
			logger.error("updatePassword", e);
			result.setMessage("ϵͳ�쳣");
			result.setSuccess(false);
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	/**
	 * ��֤֧������
	 * @param data
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "verifyPassword")
	public String verifyPassword(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		try{
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					/**
					 * APP�û�ʶ��ID
					 */
					String uuid = postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setMessage("uuid��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					LqMemberBO lqMemberBO=lqMemberService.getByUuid(uuid);
					if(null==lqMemberBO){
						result.setMessage("uuid����ֵ����");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					String password=postData.get("password");
					if(StringUtils.isBlank(password)){
						result.setMessage("password��������Ϊ��");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					boolean resultMap=lqMemberService.validatePayPassword(lqMemberBO.getPaySalt(), lqMemberBO.getPayPassword(), password);
					if(resultMap){
						result.setSuccess(true);
						return FastJsonUtils.toJSONString(result);
					}else{
						result.setMessage("֧���������");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
				}else{
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
					return FastJsonUtils.toJSONString(result);
				}
			}else{
				result.setMessage("�˻������쳣��");
				result.setSuccess(false);
				return FastJsonUtils.toJSONString(result);
			}
		}catch (Exception e) {
			logger.error("verifyPassword", e);
			result.setMessage("ϵͳ�쳣");
			result.setSuccess(false);
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	
	
	
	
	/**
	 * �˺����֧������
	 */
	@ResponseBody
	@RequestMapping(value = "accountBalancePayment")
	public String accountBalancePayment(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		try{
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
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
					LqMemberBO lqMemberBO=lqMemberService.getByUuid(uuid);
					if(null==lqMemberBO){
						result.setMessage("uuid����ֵ����");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					String orderNo=postData.get("orderNo");
					if(StringUtils.isBlank(orderNo)){
						result.setSuccess(false);
						result.setMessage("orderNo��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					String password=postData.get("password");
					if(StringUtils.isBlank(orderNo)){
						result.setSuccess(false);
						result.setMessage("password��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					boolean resultMap=lqMemberService.validatePayPassword(lqMemberBO.getPaySalt(), password,lqMemberBO.getPayPassword());
					if(resultMap){
						/**
						 * ���׶�����ѯ
						 */
						LqTransactionOrder lqTransactionOrder =lqTransactionOrderService.findLqTransactionOrderByOrderNo(orderNo);
						if(null==lqTransactionOrder){
							result.setSuccess(false);
							result.setMessage("orderNo����ֵ����");
							return FastJsonUtils.toJSONString(result);
						}
						//������
						Double amount=lqTransactionOrder.getAmount();
						LqAccount lqAccount=lqAccountService.get(memberBO.getId());
						if(lqAccount.getBalance()>amount){
							long transactionTypeId=lqTransactionOrder.getTransactionTypeId();
							boolean callbackResult=lqAccountService.callbackRecharge(orderNo, null, PaymentType.account);
							
							
							/**
							 * �ж�������������򡢻��ǻ��ѹ�����Ҫ���ó�ֵ�ӿ�
							 * 
							 * 
							 * 
							 */
							if(callbackResult){
								lqTransactionOrder.setStatus(TransactionOrderStatus.AlreadyPaid);
								//��������
								if(TransactionType.PurchaseFlow==transactionTypeId){
									
									/**
									 * ������ֵ
									 */
									Map<String,String>params=new HashMap<String, String>();
									params.put("unionid", lqMemberBO.getUnionid());
									params.put("phone", lqTransactionOrder.getPhoneNumber());
									params.put("flowpackage", lqTransactionOrder.getPackageType());
									params.put("buytype", "0");//���ѳ�ֵ
									params.put("paytype", "3");//���ѳ�ֵ
									
									
									Map<String,String>chargeResult=laiqiangService.flowrecharge(params);
									if(chargeResult.get("status").equals("OK")){
										String flowOrderNo=chargeResult.get("data");
										lqTransactionOrder.setFlowOrderNo(flowOrderNo);
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.success);//���óɹ�
										lqTransactionOrder.setFinishOn(DateUtils.getDateTime());//���ʱ��
										/**
										 * ����״̬����
										 */
										lqTransactionOrderService.update(lqTransactionOrder);
										
										
										result.setMessage("������ֵ�ɹ�");
										result.setSuccess(true);
										return FastJsonUtils.toJSONString(result);
									}else{
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.fail);//����ʧ��
										lqTransactionOrder.setFinishOn(DateUtils.getDateTime());//���ʱ��
										/**
										 * ����״̬����
										 */
										lqTransactionOrderService.update(lqTransactionOrder);
										
										
										//������Ϣ����
										String errorMessage=chargeResult.get("data");
										LqTransactionErrorLog lqTransactionErrorLog=new LqTransactionErrorLog();
										lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
										lqTransactionErrorLog.setErrorMessage(errorMessage);
										lqTransactionErrorLog.setOrderNo(orderNo);
										lqTransactionErrorLog.setMemberId(lqMemberBO.getMemberId());
										lqTransactionErrorLog.setAmount(lqTransactionOrder.getAmount());
										lqTransactionErrorLog.setTransactionTypeId(transactionTypeId);
										lqTransactionErrorLogService.insert(lqTransactionErrorLog);
										
										result.setSuccess(false);
										result.setMessage("������ֵʧ��");
										return FastJsonUtils.toJSONString(result);
									}
									
								}
								//���򻰷�
								else if(TransactionType.BuyCalls==transactionTypeId){
									/**
									 * ���ѳ�ֵ
									 */
									Map<String,String>params=new HashMap<String, String>();
									params.put("uuid", lqMemberBO.getUuid());
									params.put("phone", lqTransactionOrder.getPhoneNumber());
									params.put("recharge", lqTransactionOrder.getPackageType());
									params.put("money", lqTransactionOrder.getAmount().toString());//����
									
									Map<String,String>rechargeResult=laiqiangService.recharge(params);
									
									if(rechargeResult.get("status").equals("OK")){
										String flowOrderNo=rechargeResult.get("data");
										lqTransactionOrder.setFlowOrderNo(flowOrderNo);
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.success);//���óɹ�
										lqTransactionOrder.setFinishOn(DateUtils.getDateTime());//���ʱ��
										/**
										 * ����״̬����
										 */
										lqTransactionOrderService.update(lqTransactionOrder);
										
										
										
										result.setMessage("���ѳ�ֵ�ɹ�");
										result.setSuccess(true);
										return FastJsonUtils.toJSONString(result);
									}else{
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.fail);//����ʧ��
										lqTransactionOrder.setFinishOn(DateUtils.getDateTime());//���ʱ��
										/**
										 * ����״̬����
										 */
										lqTransactionOrderService.update(lqTransactionOrder);
										
										
										//������Ϣ����
										String errorMessage=rechargeResult.get("data");
										LqTransactionErrorLog lqTransactionErrorLog=new LqTransactionErrorLog();
										lqTransactionErrorLog.setCreatedTime(DateUtils.getDateTime());
										lqTransactionErrorLog.setErrorMessage(errorMessage);
										lqTransactionErrorLog.setMemberId(lqMemberBO.getMemberId());
										lqTransactionErrorLog.setOrderNo(orderNo);
										lqTransactionErrorLog.setAmount(lqTransactionOrder.getAmount());
										lqTransactionErrorLog.setTransactionTypeId(transactionTypeId);
										lqTransactionErrorLogService.insert(lqTransactionErrorLog);
										
										result.setSuccess(false);
										result.setMessage("���ѳ�ֵʧ��");
										return FastJsonUtils.toJSONString(result);
									}
								}else{
									result.setSuccess(false);
									result.setMessage("�쳣����");
								}
							}else{
								result.setSuccess(false);
								result.setMessage("�˺����֧��ʧ��");
							}
							
						}else{
							result.setSuccess(false);
							result.setMessage("�˺�����");
							return FastJsonUtils.toJSONString(result);
						}
					}else{
						result.setMessage("֧��������������ҵ�->�ҵ��˻�->�˻���ȫ���޸Ļ�����������");
						result.setSuccess(false);
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
			logger.error("accountBalancePayment", e);
			result.setMessage("ϵͳ�쳣");
			result.setSuccess(false);
		}
		return FastJsonUtils.toJSONString(result);
	}
	

	
	/**
	 * �ʽ���ˮ
	 */
	@ResponseBody
	@RequestMapping(value = "findCapitalFlow")
	public  String findCapitalFlow(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try{
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
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
					LqMemberBO lqMemberBO=lqMemberService.getByUuid(uuid);
					if(null==lqMemberBO){
						result.setMessage("uuid����ֵ����");
						result.setSuccess(false);
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
					
					
					String type=postData.get("type");
					if(StringUtils.isBlank(type)){
						result.setSuccess(false);
						result.setMessage("type��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					
					/**
					 * �û�����˺ŷ�ҳ��ѯ
					 */
					if(type.equals("0")){//����
						List<Memberflowlog>memberflowlogList=memberflowlogService.findMemberflowlog(lqMemberBO.getMemberId(), start, end);
						params.put("memberflowlogList", memberflowlogList);
						result.setData(params);
						result.setSuccess(true);
					}else if(type.equals("1")){//�ֽ�
						List<LqAccountSeq>lqAccountSeqList=lqAccountSeqService.findLqAccountSeqList(lqMemberBO.getMemberId(), start, end);
						params.put("lqAccountSeqList", lqAccountSeqList);
						result.setData(params);
						result.setSuccess(true);
					}else{
						result.setSuccess(false);
						result.setMessage("type����ֵ����");
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
			logger.error("findCapitalFlow", e);
			result.setMessage("ϵͳ�쳣");
			result.setSuccess(false);
		}
		
		return FastJsonUtils.toJSONString(result);
	}
}
