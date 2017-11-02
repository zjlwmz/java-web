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
 * 会员信息接口
 * 
 * @author zjlwm
 * @date 2016-11-25
 */
@Controller
@RequestMapping(value = "/laiqiang/app/member/")
public class MemberController extends BaseController {

	private static Logger logger = Logger.getLogger(MemberController.class.getName());

	
	/**
	 * 会员信息接口
	 */
	@Autowired
	private MemberService memberService;
	
	
	/**
	 * 来抢service接口
	 */
	@Autowired
	private LaiqiangService laiqiangService;
	
	
	/**
	 * 会员收货地址service接口
	 */
	@Autowired
	private MemberaddressService memberaddressService;

	/**
	 * 装机日志service接口
	 */
	@Autowired
	private LqInstallLogService installLogService;

	
	/**
	 * 邀请码service接口
	 */
	@Autowired
	private LqInvitationCodeService lqInvitationCodeService;
	
	/**
	 * app用户service接口
	 */
	@Autowired
	private LqMemberService lqMemberService;
	
	
	
	/**
	 * 会员账号service接口
	 */
	@Autowired
	private LqAccountService lqAccountService;
	
	
	
	/**
	 * 交易订单service接口
	 */
	@Autowired
	private LqTransactionOrderService lqTransactionOrderService;
	
	
	
	/**
	 * 短信验证码service接口
	 */
	@Autowired
	private LqVerifyCodeService lqVerifyCodeService;
	
	
	
	/**
	 * 任务service接口
	 */
	@Autowired
	private LqTaskService lqTaskService;
	
	
	/**
	 * 参数service接口
	 */
	@Autowired
	private LqParamService lqParamService;
	
	
	
	/**
	 * 邀请日志service接口
	 */
	@Autowired
	private LqInviteLogService lqInviteLogService;
	
	/**
	 * 账号流量日志service接口
	 */
	@Autowired
	private MemberflowlogService memberflowlogService;
	
	
	/**
	 * 账户资金日志service接口
	 */
	@Autowired
	private LqAccountSeqService lqAccountSeqService;
	
	
	/**
	 * 好友提成service接口
	 */
	@Autowired
	private LqDrawCommissionLogService lqDrawCommissionLogService;
	
	
	
	
	/**
	 * 系统信息service接口
	 */
	@Autowired
	private LqSysInfoService lqSysInfoService;
	
	
	
	/**
	 * 会员赚钱任务service接口
	 */
	@Autowired
	private LqMemberTaskService lqMemberTaskService;
	
	
	
	
	/**
	 * 充值流量接口调用错误日志记录
	 */
	@Autowired
	private LqTransactionErrorLogService lqTransactionErrorLogService;
	
	
	
	
	/**
	 * 交易类型
	 */
	@Autowired
	private TransactionTypeService transactionTypeService;
	
	
	
	/**
	 * 线程池
	 */
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	
	
	/**
	 * 活动service接口
	 */
	@Autowired
	private LqActivityService lqActivityService;
	
	
	
	
	/**
	 * 接口所在地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	
	
	
	
	
	
	
	/**
	 * 安装日志记录
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
					// 设备号
					String deviceType = postData.get("deviceType");
					// 品牌
					String brand = postData.get("brand");
					// 设备号
					String imei = postData.get("imei");
					// 平台类型（1:android ；2:IOS）
					String osType = postData.get("osType");
					if (StringUtils.isBlank(osType)) {
						result.setMessage("osType参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					// 操作系统版本
					String osVersion = postData.get("osVersion");
					
					//app版本
					String appVersion = postData.get("appVersion");

					/**
					 * 装机日志保存
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
	 * app授权登录
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
						result.setMessage("unionid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					// 用户激光推送id
					String pushId = postData.get("pushId");
					
					
					LqMemberBO lqMemberBO=lqMemberService.getByUnionid(unionid);
					MemberBO memberBO =null;
					LqAccount lqAccount=null;
					//该账户未注册到系统中
					if(null==lqMemberBO){
						String registMemberKey=JedisKeyUtils.registMember+unionid;
						if(lqMemberService.jedisKeys.exists(registMemberKey)){
							result.setMessage("请稍后再试");
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						/**
						 * 用户注册
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
						 * 会员信息
						 */
						memberBO =JSON.parseObject(registerMap.get("data").toString(), MemberBO.class);
						
						/**
						 * app用户表数据创建
						 */
						lqMemberBO=new LqMemberBO();
						lqMemberBO.setMemberId(memberBO.getId());
						lqMemberBO.setUuid(memberBO.getUuid());
						String invitationCode=lqInvitationCodeService.getInvitationCode(memberBO.getId());
						lqMemberBO.setInvitationCode(invitationCode);
						lqMemberBO.setPushId(pushId);
						
						/**
						 * 微信用户唯一标识
						 */
						lqMemberBO.setUnionid(unionid);
						
						/**
						 * app余额账户
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
						
						//激光推送用户id不为空
						if(StringUtils.isNotBlank(pushId)){
							String oldPushId=lqMemberBO.getPushId();
							if(StringUtils.isBlank(oldPushId) || !pushId.equals(oldPushId)){//当为空或者与新获取的不相同的时候进行更新
								lqMemberBO.setPushId(pushId);
								lqMemberService.updatePushId(lqMemberBO);
							}
						}
						
					}
					
					
					//设备号
					String imei = postData.get("imei");
					if(StringUtils.isNotBlank(imei)){
						installLogService.updateLqInstallLogMemberId(imei, lqMemberBO.getMemberId());
					}
					
					
					/**
					 * 接口用户传输数据DTO
					 */
					MemberDTO memberDTO = new MemberDTO();
					BeanUtils.copyProperties(memberDTO, memberBO);
					memberDTO.setName(memberBO.getWxname());
					
					if(StringUtils.isBlank(lqMemberBO.getPayPassword())){
						memberDTO.setIsPayPass(0);//未设置
					}else{
						memberDTO.setIsPayPass(1);//已设置
					}
					
					/**
					 * token
					 */
					memberDTO.setToken(memberBO.getUuid());
					memberDTO.setBalance(lqAccount.getBalance());//账号余额
					
					params.put("member", memberDTO);
					result.setSuccess(true);
					result.setData(params);

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
	 * app授权登录
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
						result.setMessage("unionid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					// 用户激光推送id
					String pushId = postData.get("pushId");
					
					
					MemberBO memberBO =null;
					LqAccount lqAccount=null;
					
					/**
					 * 是否登录过来抢APP
					 */
					boolean isLongin=lqMemberService.isExitRedisByUnionid(unionid);
					
					/**
					 * 查询APP用户是否存在缓存
					 */
					LqMemberBO lqMemberBO=lqMemberService.getByUnionid(unionid);
					
					
					boolean islongin=lqMemberService.isLogining(unionid);
					if(islongin){
						result.setMessage("请稍后再试");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * 用户注册
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
					 * 会员信息
					 */
					memberBO =JSON.parseObject(registerMap.get("data").toString(), MemberBO.class);
					
					if(null==lqMemberBO){
						/**
						 * app用户表数据创建
						 */
						lqMemberBO=new LqMemberBO();
						lqMemberBO.setMemberId(memberBO.getId());
						lqMemberBO.setUuid(memberBO.getUuid());
						String invitationCode=lqInvitationCodeService.getInvitationCode(memberBO.getId());
						lqMemberBO.setInvitationCode(invitationCode);
						lqMemberBO.setCreatedTime(DateUtils.getDateTime());
						lqMemberBO.setPushId(pushId);
						
						/**
						 * 微信用户唯一标识
						 */
						lqMemberBO.setUnionid(unionid);
						
						/**
						 * app余额账户
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
					
					//激光推送用户id不为空
					if(StringUtils.isNotBlank(pushId)){
						String oldPushId=lqMemberBO.getPushId();
						if(StringUtils.isBlank(oldPushId) || !pushId.equals(oldPushId)){//当为空或者与新获取的不相同的时候进行更新
							lqMemberBO.setPushId(pushId);
							lqMemberService.updatePushId(lqMemberBO);
						}
					}
					
					
					
					
					//设备号
					String imei = postData.get("imei");
					if(StringUtils.isNotBlank(imei)){
						installLogService.updateLqInstallLogMemberId(imei, lqMemberBO.getMemberId());
					}
					
					
					/**
					 * 接口用户传输数据DTO
					 */
					MemberDTO memberDTO = new MemberDTO();
					BeanUtils.copyProperties(memberDTO, memberBO);
					memberDTO.setName(memberBO.getWxname());
					
					if(StringUtils.isBlank(lqMemberBO.getPayPassword())){
						memberDTO.setIsPayPass(0);//未设置
					}else{
						memberDTO.setIsPayPass(1);//已设置
					}
					
					/**
					 * token
					 */
					memberDTO.setToken(memberBO.getUuid());
					memberDTO.setBalance(lqAccount.getBalance());//账号余额
					
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
								//首次登录
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
	 * 获取会员信息
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
						result.setMessage("uuid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}

					MemberBO memberBO = memberService.getMember(uuid);
					MemberDTO memberDTO = new MemberDTO();
					BeanUtils.copyProperties(memberDTO, memberBO);
					memberDTO.setName(memberBO.getWxname());
					
					Integer authstatus=memberDTO.getAuthstatus();
					if(null==authstatus){
						memberDTO.setAuthstatus(0);//未认证
					}
					
					/**
					 * token
					 */
					memberDTO.setToken(memberBO.getUuid());
					LqMemberBO lqMemberBO=lqMemberService.getByUuid(uuid);
					if(StringUtils.isBlank(lqMemberBO.getPayPassword())){
						memberDTO.setIsPayPass(0);//未设置
					}else{
						memberDTO.setIsPayPass(1);//已设置
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
				result.setMessage("参数格式错误！");
			}
		} catch (Exception e) {
			logger.error("数据异常", e);
			result.setSuccess(false);
			result.setMessage("数据异常");
		}
		return FastJsonUtils.toJSONString(result);
	}

	

	
	
	
	
	/**
	 * 会员地址列表
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
						result.setMessage("uuid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * 会员id
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO) {
						result.setMessage("unionid参数错误");
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
	 * 会员地址保存
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
						result.setMessage("uuid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * 会员id
					 */
					MemberBO memberBO  = memberService.getMember(uuid);
					if (null == memberBO) {
						result.setMessage("uuid参数错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					MemberaddressBO memberaddressBO = FastJsonUtils.toBean(postParams.getPostData(), MemberaddressBO.class);
					
					if(StringUtils.isBlank(memberaddressBO.getName())){
						result.setMessage("name参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}else{
						memberaddressBO.setName(URLDecoder.decode(memberaddressBO.getName(), "UTF-8"));
					}
					
					
					if(StringUtils.isBlank(memberaddressBO.getPhone())){
						result.setMessage("phone参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					if(StringUtils.isBlank(memberaddressBO.getAddress())){
						result.setMessage("address参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}else{
						memberaddressBO.setAddress(URLDecoder.decode(memberaddressBO.getAddress(), "UTF-8"));
					}
					
					
					if(StringUtils.isBlank(memberaddressBO.getProvince())){
						result.setMessage("province参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}else{
						memberaddressBO.setProvince(URLDecoder.decode(memberaddressBO.getProvince(), "UTF-8"));
					}
					
					if(StringUtils.isBlank(memberaddressBO.getCity())){
						result.setMessage("city参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}else{
						memberaddressBO.setCity(URLDecoder.decode(memberaddressBO.getCity(), "UTF-8"));
					}
					
					if(StringUtils.isBlank(memberaddressBO.getZone())){
						result.setMessage("zone参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}else{
						memberaddressBO.setZone(URLDecoder.decode(memberaddressBO.getZone(), "UTF-8"));
					}
					
					if(StringUtils.isNotBlank(memberaddressBO.getId())){
						memberaddressService.update(memberBO.getUuid(), memberaddressBO);//地址更新
					}else{
						memberaddressService.insert(memberBO.getUuid(), memberaddressBO);//地址新增
					}
					
					result.setData(params);
					result.setSuccess(true);
				}else{
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 * 修改会员地址
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
						result.setMessage("uuid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}

					
					/**
					 * 会员信息
					 */
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid参数错误");
						return FastJsonUtils.toJSONString(result);
					}
					
					String addressId = postData.get("addressId");
					if (StringUtils.isBlank(addressId)) {
						result.setMessage("addressId参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					MemberaddressBO memberaddressBO=memberaddressService.get(addressId, memberBO.getUuid());
					if (null==memberaddressBO) {
						result.setMessage("addressId参数错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					//0删除、1设置为默认地址
					String type = postData.get("type");
					if (StringUtils.isBlank(type)) {
						result.setMessage("type参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					//删除
					if(type.equals("0")){
						memberaddressService.delete(memberBO.getUuid(), memberaddressBO);
						result.setMessage("删除成功");
					}
					//设置为默认地址
					else if(type.equals("1")){
						memberaddressService.setMemberAddressDefault(memberBO.getUuid(),addressId);
						result.setMessage("设置默认成功");
					}
					result.setSuccess(true);
					result.setData(params);
				} else {
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 * 获取邀请信息接口
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
						result.setMessage("uuid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}

					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setMessage("uuid参数值错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					LqMemberBO lqmemberBO=lqMemberService.getByUuid(uuid);
					//邀请码
					params.put("inviteCode", lqmemberBO.getInvitationCode());
					
					//邀请的好友个数
					params.put("invitedFriends", lqMemberService.getinvitedFriends(memberBO.getId()));
					
//					Integer focusnum=memberBO.getFocusnum();
//					if(null==focusnum){
//						focusnum=0;
//					}
//					params.put("invitedFriends", focusnum);
					
					
					//好友提供总提流量
					Double totalFlowRate=lqDrawCommissionLogService.getTotalFlowRate(memberBO.getId());
					params.put("totalFlowRate", totalFlowRate.intValue());
					
					//好友提供总金额收益
					Double totalAmountOfIncome=lqDrawCommissionLogService.getTotalAmountOfIncome(memberBO.getId());
					params.put("totalAmountOfIncome", totalAmountOfIncome);
					
					Long inviter=memberBO.getMemberid();
					if(null!=inviter){
						//是否被邀请
						params.put("beInvited", "1");
						MemberBO invitationMemberBO=memberService.getById(inviter);
						if(null!=invitationMemberBO){
							params.put("invitationName", invitationMemberBO.getWxname());//邀请我的会员名称
						}else{
							params.put("invitationName", "");//邀请我的会员名称
						}
					}else{
						//是否被邀请
						params.put("beInvited", "0");
						params.put("invitationName", "");//邀请我的会员名称
					}
					
					//app下载二维码
					String inviteBarcode=lqSysInfoService.getLqSysInfoInviteBarcode();
					params.put("qrCode",inviteBarcode);
					
					//图片说明
					String imageUrl=lqSysInfoService.getLqSysInfoInviteImage();
					params.put("imageUrl", imageUrl);
					//邀请说明
					String inviteDescUrl=domain+"laiqiang/app/sysInfo/html/inviteDesc";
					params.put("invDesUrl", inviteDescUrl);
					//邀请简介
					String invIntroduction=lqSysInfoService.getLqSysInfoInviteBriefIntro();
					params.put("invIntroduction", invIntroduction);
					
					
					//邀请页面链接地址
//					String invitePageUrl=domain+"laiqiang/app/sysInfo/html/invitePage";
					String invitePageUrl=lqSysInfoService.getInvitePageUrl();
					params.put("invitePageUrl", invitePageUrl);
					
					//邀请标题
					String pageTitle=lqSysInfoService.getInvitePageTitle();
					params.put("pageTitle", pageTitle+"我的邀请码："+lqmemberBO.getInvitationCode());
					
					
					//邀请页面->封面图片
					String invitepageCover=lqSysInfoService.getInvitepageCover();
					params.put("invitepageCover", invitepageCover);
					
					
					//邀请页面->邀请简介
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
	 * 用户邀请
	 * 获取收益列表
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
						result.setMessage("unionid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setMessage("uuid参数值错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					//type 0:邀请的好友列表、1：好友提供总收益
					String type=postData.get("type");
					if (StringUtils.isBlank(type)) {
						result.setMessage("type参数不能为空");
						result.setSuccess(false);
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
					
					if(type.equals("0")){//邀请的好友列表
						List<InviteFriendsDTO>inviteFriendsDTOList=lqInviteLogService.findLqInvitelogList(memberBO.getId(), start, end);
						params.put("inviteFriendsList", inviteFriendsDTOList);
						result.setSuccess(true);
						result.setData(params);
					}else if(type.equals("1")){
						//好友提供总收益
						List<InviteFriendsDTO>inviteFriendsDTOList=lqDrawCommissionLogService.findLqDrawCommissionLogList(memberBO.getId(), start, end);
						params.put("inviteFriendsList", inviteFriendsDTOList);
						result.setSuccess(true);
						result.setData(params);
					}else{
						result.setSuccess(false);
						result.setMessage("type参数错误");
						return FastJsonUtils.toJSONString(result);
					}
					
					
				} else {
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 * 推荐
	 * 填写邀请码
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
						result.setMessage("unionid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					
					/**
					 * wx用户表
					 * 被邀请人
					 */
					MemberBO beInvitedMemberBO=memberService.getMember(uuid);
					if(null==beInvitedMemberBO){
						result.setMessage("uuid参数值错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					if(null!=beInvitedMemberBO.getMemberid()){
						result.setMessage("你已被邀请过了");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * app用户表
					 * 被邀请人
					 */
					LqMemberBO beInvitedLqMemberBO=lqMemberService.getByMemberId(beInvitedMemberBO.getId());
					//邀请人ID
//					Long inviter=beInvitedLqMemberBO.getInviter();
					
//					if(inviter!=null){
//						result.setMessage("你已被邀请过了");
//						result.setSuccess(false);
//						return FastJsonUtils.toJSONString(result);
//					}
					
					//邀请码
					String inviteCode=postData.get("inviteCode");
					if(StringUtils.isBlank(inviteCode)){
						result.setMessage("inviteCode参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * 验证邀请码
					 * 邀请人
					 */
					LqMemberBO inviteLqMemberBO=lqMemberService.getByInvitationCode(inviteCode);
					if(null==inviteLqMemberBO){
						result.setMessage("邀请码错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					
					/**
					 * 邀请码不能提交自己的
					 */
					if(inviteLqMemberBO.getMemberId().longValue()==beInvitedLqMemberBO.getMemberId().longValue()){
						result.setMessage("不能填写自己的邀请码哦");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					Long inviterB=inviteLqMemberBO.getInviter();
					
					


					/**
					 * 验证 
					 * A的邀请人是B and  B的邀请人是A
					 */
					if(null!=inviterB && inviterB.longValue()==beInvitedMemberBO.getId().longValue()){
						result.setMessage("不能相互同时邀请对方！");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * 获取推荐奖励值 流量
					 */
					LqParamBO lqParamBO=lqParamService.getParamValue(ParamName.inviteCommission);
					if(ParamType.flow==lqParamBO.getValueType().intValue()){
						int fownum=lqParamBO.getParamValue().intValue();
						if(fownum==0){
							result.setMessage("系统参数设置值异常，请联系客户");
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
					}else if(ParamType.cash==lqParamBO.getValueType().intValue()){
						double rewardQuantity=lqParamBO.getParamValue();//邀请奖励
						if(rewardQuantity<=0){
							result.setMessage("系统参数设置值异常，请联系客户");
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
					}
					
					
					
					String updatememberinvitlogKey=JedisKeyUtils.updatememberinvitlog+beInvitedMemberBO.getMemberid();
					boolean resultInvitlog=lqMemberService.isIntefaceIng(updatememberinvitlogKey);
					if(resultInvitlog){
						result.setMessage("请稍后再试");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					lqMemberService.setIntefaceIng(updatememberinvitlogKey);
					/**
					 * 来抢微信接口验证
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
					 * 更新邀请人总数
					 */
					
					
					
					
					//邀请人ID
					Long inviteMemberId=inviteLqMemberBO.getMemberId();//邀请人id
					//被邀请人ID
					Long beInvitedMemberId=beInvitedLqMemberBO.getMemberId();//受邀请人id
					/**
					 * 获取推荐奖励值 流量
					 */
					if(ParamType.flow==lqParamBO.getValueType().intValue()){//流量
						int fownum=lqParamBO.getParamValue().intValue();
						/**
						 * 邀请人奖励
						 */
						Map<String,String>updateBalanceParams=new HashMap<String, String>();
						updateBalanceParams.put("unionid", inviteLqMemberBO.getUnionid());//邀请人uuid
						updateBalanceParams.put("flow", fownum+"");
						updateBalanceParams.put("descr","拉新收益");
						updateBalanceParams.put("type", TransactionType.LanewAward+"");//拉新奖励
						updateBalanceParams.put("orderid", inviteCode);
						Map<String,String>updateBalanceResult=laiqiangService.updatememberbalance(updateBalanceParams);
						
						/**
						 * 邀请成功
						 */
						if(updateBalanceResult.get("status").equals("OK")){
							
							/**
							 * 受邀请人奖励
							 */
							updateBalanceParams.put("unionid", beInvitedLqMemberBO.getUnionid());
							updateBalanceParams.put("flow", fownum+"");
							updateBalanceParams.put("descr","被邀请收益");
							updateBalanceParams.put("type", TransactionType.LanewAward+"");//推荐奖励
							updateBalanceParams.put("orderid", inviteCode);
							Map<String,String>beInvitedupdateBalanceResult=laiqiangService.updatememberbalance(updateBalanceParams);
							if(beInvitedupdateBalanceResult.get("status").equals("ERROR")){
								/**
								 * 错误日志记录...
								 */
								logger.error(beInvitedupdateBalanceResult.get("message"));
							}else{
								/**
								 * 更新账号 拉新奖励汇总
								 */
								lqAccountService.updateLqAccountTotalflow(beInvitedMemberId, TransactionType.RecommendedAward, fownum);
							}
							
							
							
							//---------------------邀请日志保存-----------------
							//设置邀请人
							beInvitedLqMemberBO.setInviter(inviteLqMemberBO.getMemberId());
							LqInviteLog lqInviteLog=new LqInviteLog();
							lqInviteLog.setInviter(inviteMemberId);//邀请人id
							lqInviteLog.setInvitee(beInvitedMemberId);//受邀人id
							
							lqInviteLog.setInviteeCommission(fownum*1.0d);//受邀人奖励
							lqInviteLog.setInviterCommission(fownum*1.0d);//邀请人奖励
							lqInviteLog.setRewardType(RewardType.flow);//流量
							lqInviteLog.setCreatedTime(DateUtils.getDateTime());
							
							lqMemberService.updateInviter(beInvitedLqMemberBO,lqInviteLog);
							
							
							/**
							 *  更新邀请人总数
							 */
							lqMemberService.updateInvitationNumber(inviteLqMemberBO);
							
							
							/**
							 * 更新账号 拉新奖励汇总
							 */
							lqAccountService.updateLqAccountTotalflow(inviteMemberId, TransactionType.RecommendedAward, fownum);
							
							
							result.setSuccess(true);
							result.setMessage("邀请成功");
						}else{
							result.setSuccess(false);
							result.setMessage("邀请失败、请与客服联系");
						}
					}else if(ParamType.cash==lqParamBO.getValueType().intValue()){//现金
						//--------------邀请人奖励-----------------------------
						
						LqAccount lqAccount=lqAccountService.get(inviteMemberId);
						double rewardQuantity=lqParamBO.getParamValue();//邀请奖励
						LqAccountSeq lqAccountSeq=new LqAccountSeq();
						lqAccountSeq.setMemberId(inviteMemberId);
						lqAccountSeq.setDirection(Direction.enter);
						lqAccountSeq.setTransactionTypeId(TransactionType.RecommendedAward);
						lqAccountSeq.setTransactionAmount(rewardQuantity);
						lqAccountSeq.setPreBalance(lqAccount.getBalance());
						lqAccountSeq.setBalance(lqAccount.getBalance()+rewardQuantity);
						lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
						lqAccountSeq.setRemarks("推荐收益");
						//账号资金更新
						lqAccountService.updateAccount(inviteMemberId, lqAccountSeq);
						//账号资金汇总
						lqAccountService.updateLqAccountTotalCash(inviteMemberId, TransactionType.RecommendedAward, rewardQuantity);
						
						
						
						
						
						//---------------受邀人奖励------------------------------
						LqAccount beInvitedlqAccount=lqAccountService.get(beInvitedMemberId);
						//资金流水
						LqAccountSeq beInvitedlqAccountSeq=new LqAccountSeq();
						beInvitedlqAccountSeq.setMemberId(beInvitedMemberId);
						beInvitedlqAccountSeq.setDirection(Direction.enter);
						beInvitedlqAccountSeq.setTransactionTypeId(TransactionType.RecommendedAward);
						beInvitedlqAccountSeq.setTransactionAmount(rewardQuantity);
						beInvitedlqAccountSeq.setPreBalance(beInvitedlqAccount.getBalance());
						beInvitedlqAccountSeq.setBalance(beInvitedlqAccount.getBalance()+rewardQuantity);
						beInvitedlqAccountSeq.setCreatedTime(DateUtils.getDateTime());
						beInvitedlqAccountSeq.setRemarks("被推荐收益");
						//账号资金更新
						lqAccountService.updateAccount(beInvitedMemberId, lqAccountSeq);
						//账号资金汇总
						lqAccountService.updateLqAccountTotalCash(beInvitedMemberId, TransactionType.RecommendedAward, rewardQuantity);
						
						
						
						//--------------------邀请日志保存-------------------------
						//设置邀请人
						beInvitedLqMemberBO.setInviter(inviteLqMemberBO.getMemberId());
						//邀请日志记录
						LqInviteLog lqInviteLog=new LqInviteLog();
						lqInviteLog.setInviter(inviteMemberId);//邀请人id
						lqInviteLog.setInvitee(beInvitedMemberId);//受邀人id
						
						lqInviteLog.setInviteeCommission(rewardQuantity);//受邀人奖励
						lqInviteLog.setInviterCommission(rewardQuantity);//邀请人奖励
						lqInviteLog.setRewardType(RewardType.cash);//现金
						lqInviteLog.setCreatedTime(DateUtils.getDateTime());
						
						lqMemberService.updateInviter(beInvitedLqMemberBO,lqInviteLog);
						
						
						result.setSuccess(true);
						result.setMessage("邀请成功");
					}else{
						result.setSuccess(false);
						result.setMessage("推荐奖励类型未知");
					}
				} else {
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					result.setData(params);
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
	 * 签到领取收益
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
						result.setMessage("unionid参数不能为空");
						result.setSuccess(false);
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
					
					LqAccount lqAccount=lqAccountService.get(memberBO.getId());
					if(null==lqAccount){
						result.setSuccess(false);
						result.setMessage("uuid参数值错误");
						return FastJsonUtils.toJSONString(result);
					}
					/**
					 * 任务ID
					 */
					String taskId=postData.get("taskId");
					if(StringUtils.isBlank(taskId)){
						result.setMessage("taskId参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					

					LqTaskBO lqTaskBO=lqTaskService.get(taskId);
					if(null==lqTaskBO){
						result.setMessage("taskId值错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					LqMemberTaskBO lqMemberTaskBO  =lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if(null==lqMemberTaskBO){
						result.setMessage("该任务未做，无法领取签到奖励");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					int status=lqMemberTaskBO.getStatus();
					if(status!=4){
						result.setMessage("该任务未完成，无法领取签到奖励");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					
					
					String startTime=lqMemberTaskBO.getAuditTime().split(" ")[0];
					String currentDate=DateUtils.getDate("yyyy-MM-dd");
					long disDay=DateUtils.getDistanceDays(startTime, currentDate);
					
//					//限制当天的任务签到奖励无法领取
//					if(disDay==0){
//						result.setMessage("任务当天无法领取签到任务奖励");
//						result.setSuccess(false);
//						return FastJsonUtils.toJSONString(result);
//					}
					/**
					 * 有效签到期限
					 */
					int signinDays=lqTaskBO.getSigninDays();
					
					/**
					 * 是否在有效签到期限
					 */
					if(!(disDay<=signinDays && disDay>0)){
						result.setMessage("未在有效签到期限内无法领取签到任务奖励");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * 奖励额度
					 */
					Double rewardQuantity=lqTaskBO.getSigninReward();
					
					
					/**
					 * 奖励类型(1:流量；2：现金)
					 */
					Integer rewardType=lqTaskBO.getRewardType();
					
					/**
					 * 是否允许提成
					 */
					String canDrawCommission=transactionTypeService.getTransactionCanDrawCommissionByTransactionTypeId(TransactionType.DownloadBonus);
					
					
					if(rewardType==RewardType.flow){//流量
						Long signInCount=memberflowlogService.findTaskSigninRewardCount(memberBO.getId(), taskId,TransactionType.SigninReward);
						if(signInCount>0){
							result.setMessage("今日已签到");
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						
						/**
						 * 调用增加账号流量接口
						 */
						Map<String,String>updateBalanceParams=new HashMap<String, String>();
						updateBalanceParams.put("unionid", memberBO.getUnionid());
						updateBalanceParams.put("flow", rewardQuantity.intValue()+"");
						updateBalanceParams.put("descr", "签到收益");
						updateBalanceParams.put("type", TransactionType.SigninReward+"");//签到奖励
						updateBalanceParams.put("orderid", taskId);//
						
						Map<String,String>updateBalanceResult=laiqiangService.updatememberbalance(updateBalanceParams);
						if(updateBalanceResult.get("status").equals("OK")){
							/**
							 * 更新账号签到奖励汇总
							 */
							lqAccountService.updateLqAccountTotalflow(memberBO.getId(), TransactionType.SigninReward, rewardQuantity.intValue());
							lqMemberTaskService.updateTotalReward(lqMemberTaskBO.getId(), rewardQuantity.intValue()*1d);//更新赚取奖励总额
							if(canDrawCommission.equals(CanDrawCommission.yes)){//提成
								LqParamBO lqParamBO =lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);
								Double paramValue=lqParamBO.getParamValue();
								Double commissionMoney=rewardQuantity*paramValue;//提成流量
								int flowcommisson=commissionMoney.intValue();
								double dd=commissionMoney-flowcommisson;
								if(dd>=0.8){
									flowcommisson+=1;
								}
								if(flowcommisson>0){
									lqAccountService.updateMemberFlowCommission(memberBO.getId(), TransactionType.DownloadBonus, taskId, flowcommisson);
								}
								
							}
							
							//添加任务已签到记录
							lqMemberTaskService.taskTodaySign(memberBO.getId(), taskId, DateUtils.getDate("yyyyMMdd"));
							
							result.setSuccess(true);
							result.setData(params);
						}else{
							String message=updateBalanceResult.get("message");
							result.setSuccess(false);
							result.setMessage(message);
							result.setData(params);
						}
					}else if(rewardType==RewardType.cash){//现金
						Long signInCount=lqAccountSeqService.findTaskSigninRewardCount(memberBO.getId(), taskId,TransactionType.SigninReward);
						if(signInCount>0){
							result.setMessage("今日已签到");
							result.setSuccess(false);
							return FastJsonUtils.toJSONString(result);
						}
						LqAccountSeq lqAccountSeq=new LqAccountSeq();
						lqAccountSeq.setMemberId(memberBO.getId());
						lqAccountSeq.setDirection(Direction.enter);
						lqAccountSeq.setTransactionTypeId(TransactionType.SigninReward);
						lqAccountSeq.setTransactionAmount(rewardQuantity);
						lqAccountSeq.setPreBalance(lqAccount.getBalance());
						
						//账户金额
						Double balance=DoubleUtil.add(lqAccount.getBalance(), rewardQuantity);
						//将double类型的数字保留两位小数（四舍五入）
						String balanceStr=FormatUtils.formatNumber(balance);
						double newBlance=Double.parseDouble(balanceStr);
						
						lqAccountSeq.setBalance(newBlance);
						lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
						lqAccountSeq.setRemarks("任务签到金额奖励");
						lqAccountSeq.setTaskId(Long.parseLong(lqTaskBO.getId()));
						
						/**
						 * 更新资金账号
						 */
						lqAccountService.updateAccount(memberBO.getId(), lqAccountSeq);
						
						/**
						 * 更新账号签到奖励金额汇总
						 */
						lqAccountService.updateLqAccountTotalCash(memberBO.getId(), TransactionType.SigninReward, rewardQuantity);
						lqMemberTaskService.updateTotalReward(lqMemberTaskBO.getId(), rewardQuantity);//更新赚取奖励总额
						
						//添加任务已签到记录
						lqMemberTaskService.taskTodaySign(memberBO.getId(), taskId, DateUtils.getDate("yyyyMMdd"));
						
						
						if(canDrawCommission.equals(CanDrawCommission.yes)){//提成
							LqParamBO lqParamBO =lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);
							Double paramValue=lqParamBO.getParamValue();
							
							//提成金额
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
	 *  设置修改支付密码
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
					 * APP用户识别ID
					 */
					String uuid = postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setMessage("uuid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员id
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO) {
						result.setMessage("uuid参数值错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					//
					/**
					 * 0:未认证；1：认证
					 * 是否绑定了手机号码
					 * 
					 */
					String phoneNumber="";
					Integer authstatus=memberBO.getAuthstatus();
					if(authstatus==null)authstatus=0;
					if(null!=authstatus && authstatus.intValue()==1){//已认证
						phoneNumber=memberBO.getPhone();
					}else{
						//未认证
						phoneNumber=postData.get("phoneNumber");
						if(StringUtils.isBlank(phoneNumber)){
							result.setSuccess(false);
							result.setMessage("phoneNumber参数不能为空");
							return FastJsonUtils.toJSONString(result);
						}
					}
					
					
					/**
					 * 验证码认证
					 */
					String verifyCode=postData.get("verifyCode");
					if(StringUtils.isBlank(verifyCode)){
						result.setSuccess(false);
						result.setMessage("verifyCode参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					//支付密码
					String rawPassword=postData.get("password");
					if(StringUtils.isBlank(rawPassword)){
						result.setMessage("password参数值不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					if(rawPassword.length()>50){
						result.setMessage("password长度过长");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * 验证码是否正确
					 */
					boolean smsresult=lqVerifyCodeService.validateSMS(phoneNumber, VerifyCodeType.paymentPassword, verifyCode);
					if(smsresult){
						//未认证
						if(authstatus==0){
							/**
							 * 手机号验证检查
						 	 */
							boolean phoneResult=laiqiangService.validatePhone(memberBO.getId(), phoneNumber);
							if(phoneResult){
								result.setMessage("该手机号已经被其他账号绑定，如有疑问请给客服留言");
								result.setSuccess(false);
								return FastJsonUtils.toJSONString(result);
							}else{
								/**
								 * 绑定手机号码
								 */
								laiqiangService.bindingPhone(memberBO, phoneNumber);
							}
						}
						
						
						
						lqMemberService.updatePassword(uuid, rawPassword);
						
						result.setSuccess(true);
						return FastJsonUtils.toJSONString(result);
					}else{
						result.setMessage("验证码错误");
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
			result.setMessage("系统异常");
			result.setSuccess(false);
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	/**
	 * 验证支付密码
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
					 * APP用户识别ID
					 */
					String uuid = postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setMessage("uuid参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					LqMemberBO lqMemberBO=lqMemberService.getByUuid(uuid);
					if(null==lqMemberBO){
						result.setMessage("uuid参数值错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					String password=postData.get("password");
					if(StringUtils.isBlank(password)){
						result.setMessage("password参数不能为空");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					boolean resultMap=lqMemberService.validatePayPassword(lqMemberBO.getPaySalt(), lqMemberBO.getPayPassword(), password);
					if(resultMap){
						result.setSuccess(true);
						return FastJsonUtils.toJSONString(result);
					}else{
						result.setMessage("支付密码错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
				}else{
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
					return FastJsonUtils.toJSONString(result);
				}
			}else{
				result.setMessage("账户数据异常！");
				result.setSuccess(false);
				return FastJsonUtils.toJSONString(result);
			}
		}catch (Exception e) {
			logger.error("verifyPassword", e);
			result.setMessage("系统异常");
			result.setSuccess(false);
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	
	
	
	
	/**
	 * 账号余额支付订单
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
					LqMemberBO lqMemberBO=lqMemberService.getByUuid(uuid);
					if(null==lqMemberBO){
						result.setMessage("uuid参数值错误");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
					}
					
					
					String orderNo=postData.get("orderNo");
					if(StringUtils.isBlank(orderNo)){
						result.setSuccess(false);
						result.setMessage("orderNo参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					String password=postData.get("password");
					if(StringUtils.isBlank(orderNo)){
						result.setSuccess(false);
						result.setMessage("password参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					boolean resultMap=lqMemberService.validatePayPassword(lqMemberBO.getPaySalt(), password,lqMemberBO.getPayPassword());
					if(resultMap){
						/**
						 * 交易订单查询
						 */
						LqTransactionOrder lqTransactionOrder =lqTransactionOrderService.findLqTransactionOrderByOrderNo(orderNo);
						if(null==lqTransactionOrder){
							result.setSuccess(false);
							result.setMessage("orderNo参数值错误");
							return FastJsonUtils.toJSONString(result);
						}
						//付款金额
						Double amount=lqTransactionOrder.getAmount();
						LqAccount lqAccount=lqAccountService.get(memberBO.getId());
						if(lqAccount.getBalance()>amount){
							long transactionTypeId=lqTransactionOrder.getTransactionTypeId();
							boolean callbackResult=lqAccountService.callbackRecharge(orderNo, null, PaymentType.account);
							
							
							/**
							 * 判断如果是流量购买、还是话费购买需要调用充值接口
							 * 
							 * 
							 * 
							 */
							if(callbackResult){
								lqTransactionOrder.setStatus(TransactionOrderStatus.AlreadyPaid);
								//购买流量
								if(TransactionType.PurchaseFlow==transactionTypeId){
									
									/**
									 * 流量充值
									 */
									Map<String,String>params=new HashMap<String, String>();
									params.put("unionid", lqMemberBO.getUnionid());
									params.put("phone", lqTransactionOrder.getPhoneNumber());
									params.put("flowpackage", lqTransactionOrder.getPackageType());
									params.put("buytype", "0");//付费充值
									params.put("paytype", "3");//付费充值
									
									
									Map<String,String>chargeResult=laiqiangService.flowrecharge(params);
									if(chargeResult.get("status").equals("OK")){
										String flowOrderNo=chargeResult.get("data");
										lqTransactionOrder.setFlowOrderNo(flowOrderNo);
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.success);//调用成功
										lqTransactionOrder.setFinishOn(DateUtils.getDateTime());//完成时间
										/**
										 * 订单状态更新
										 */
										lqTransactionOrderService.update(lqTransactionOrder);
										
										
										result.setMessage("流量充值成功");
										result.setSuccess(true);
										return FastJsonUtils.toJSONString(result);
									}else{
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.fail);//调用失败
										lqTransactionOrder.setFinishOn(DateUtils.getDateTime());//完成时间
										/**
										 * 订单状态更新
										 */
										lqTransactionOrderService.update(lqTransactionOrder);
										
										
										//错误信息保存
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
										result.setMessage("流量充值失败");
										return FastJsonUtils.toJSONString(result);
									}
									
								}
								//购买话费
								else if(TransactionType.BuyCalls==transactionTypeId){
									/**
									 * 话费充值
									 */
									Map<String,String>params=new HashMap<String, String>();
									params.put("uuid", lqMemberBO.getUuid());
									params.put("phone", lqTransactionOrder.getPhoneNumber());
									params.put("recharge", lqTransactionOrder.getPackageType());
									params.put("money", lqTransactionOrder.getAmount().toString());//付费
									
									Map<String,String>rechargeResult=laiqiangService.recharge(params);
									
									if(rechargeResult.get("status").equals("OK")){
										String flowOrderNo=rechargeResult.get("data");
										lqTransactionOrder.setFlowOrderNo(flowOrderNo);
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.success);//调用成功
										lqTransactionOrder.setFinishOn(DateUtils.getDateTime());//完成时间
										/**
										 * 订单状态更新
										 */
										lqTransactionOrderService.update(lqTransactionOrder);
										
										
										
										result.setMessage("话费充值成功");
										result.setSuccess(true);
										return FastJsonUtils.toJSONString(result);
									}else{
										lqTransactionOrder.setInterfaceReturnStatus(InterfaceReturnStatus.fail);//调用失败
										lqTransactionOrder.setFinishOn(DateUtils.getDateTime());//完成时间
										/**
										 * 订单状态更新
										 */
										lqTransactionOrderService.update(lqTransactionOrder);
										
										
										//错误信息保存
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
										result.setMessage("话费充值失败");
										return FastJsonUtils.toJSONString(result);
									}
								}else{
									result.setSuccess(false);
									result.setMessage("异常订单");
								}
							}else{
								result.setSuccess(false);
								result.setMessage("账号余额支付失败");
							}
							
						}else{
							result.setSuccess(false);
							result.setMessage("账号余额不足");
							return FastJsonUtils.toJSONString(result);
						}
					}else{
						result.setMessage("支付密码错误，请在我的->我的账户->账户安全中修改或者重置密码");
						result.setSuccess(false);
						return FastJsonUtils.toJSONString(result);
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
			logger.error("accountBalancePayment", e);
			result.setMessage("系统异常");
			result.setSuccess(false);
		}
		return FastJsonUtils.toJSONString(result);
	}
	

	
	/**
	 * 资金流水
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
					LqMemberBO lqMemberBO=lqMemberService.getByUuid(uuid);
					if(null==lqMemberBO){
						result.setMessage("uuid参数值错误");
						result.setSuccess(false);
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
					
					
					String type=postData.get("type");
					if(StringUtils.isBlank(type)){
						result.setSuccess(false);
						result.setMessage("type参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					
					/**
					 * 用户浏览账号分页查询
					 */
					if(type.equals("0")){//流量
						List<Memberflowlog>memberflowlogList=memberflowlogService.findMemberflowlog(lqMemberBO.getMemberId(), start, end);
						params.put("memberflowlogList", memberflowlogList);
						result.setData(params);
						result.setSuccess(true);
					}else if(type.equals("1")){//现金
						List<LqAccountSeq>lqAccountSeqList=lqAccountSeqService.findLqAccountSeqList(lqMemberBO.getMemberId(), start, end);
						params.put("lqAccountSeqList", lqAccountSeqList);
						result.setData(params);
						result.setSuccess(true);
					}else{
						result.setSuccess(false);
						result.setMessage("type参数值错误");
						return FastJsonUtils.toJSONString(result);
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
			logger.error("findCapitalFlow", e);
			result.setMessage("系统异常");
			result.setSuccess(false);
		}
		
		return FastJsonUtils.toJSONString(result);
	}
}
