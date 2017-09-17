package cn.emay.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonException;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;

import cn.emay.common.messaging.IMBase;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.util.BaseController;
import cn.emay.common.util.IdGen;
import cn.emay.common.util.PostParamsModel;
import cn.emay.common.util.RegexpUtils;
import cn.emay.model.Member;
import cn.emay.model.School;
import cn.emay.model.SmsConfig;
import cn.emay.model.SmsLog;
import cn.emay.model.Student;
import cn.emay.service.MemberService;
import cn.emay.service.RegisterService;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;

import example.SingletonClient;
/**
 * 学校接口
 * 用户注册
 * @author zjlwm
 * @version 2015-04-07
 *
 */
@IocBean
@At(value="/service/register/")
public class RegisterController extends BaseController{
	static Logger logger = Logger.getLogger(RegisterController.class.getName());
	@Inject
	private RegisterService registerService;
	
	
	@Inject
	private MemberService memberService;
	
	
	/**
	 * 会员注册
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("saveMember")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object saveMember(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				if(StringUtils.isNotBlank(postData)){
					try{
						/**
						 * 获取家长会员信息
						 */
						Member member=Json.fromJson(Member.class, postData);
						Map<String,Object> resultValidateMember=validateMember(member);
						if(resultValidateMember.get("status").equals("OK")){
							
							List<Student> studentList=member.getStudentList();
							if(studentList.size()==0){
								result.setMessage("postData 数据不包含学生数据");
								return result;
							}else{
								Student student=studentList.get(0);
								Map<String,Object> resultValidateStudent=validateStudent(student);
								if(resultValidateStudent.get("status").equals("OK")){
									if(StringUtils.isBlank(member.getId())){
										member.setId(IdGen.uuid());
									}
									if(StringUtils.isBlank(student.getId())){
										student.setId(IdGen.uuid());
									}
									School school=memberService.findSchool(student.getSchoolId());
									if(null==school){
										result.setMessage("学习id错误");
										return result;
									}
									member.setAreaId(school.getAreaId());
									/**
									 * 插入环信用户
									 */
									String hxid="hxid"+memberService.saveHXUser(member.getId(), "1").getInt("id");
									/**
									 * 先注册环信平台
									 */
									try{
										ObjectNode createNewIMUserSingleNode=IMBase.createNewIMUserSingle(hxid, member.getPassword(), member.getName());
										if(createNewIMUserSingleNode.toString().equals("{}")){
											logger.debug(member.getName()+"---"+member.getMobilephone()+"注册账号失败"+Json.toJson(createNewIMUserSingleNode));
											result.setMessage("注册账号失败");
											return Json.toJson(result);
										}
										if(null!=createNewIMUserSingleNode.get("error")){
											logger.debug(member.getName()+"---"+member.getMobilephone()+"注册账号失败"+Json.toJson(createNewIMUserSingleNode));
											result.setMessage("注册账号失败");
											return Json.toJson(result);
										}
									}catch (Exception e) {
										memberService.deleteHxid(member.getId());
										logger.debug("注册账号失败", e);
										result.setMessage("注册账号失败");
										return Json.toJson(result);
									}
									registerService.entryptPassword(member);
									registerService.saveMember(member);
									Map<String,Object>data=Maps.newHashMap();
									data.put("memberId", member.getId());
									result.setData(data);
									result.setSuccess(true);
									
								}else{
									result.setMessage(resultValidateStudent.get("message").toString());
								}
							}
						}else{
							result.setMessage(resultValidateMember.get("message").toString());
						}
					}catch (JsonException e) {
						result.setMessage("postData参数json格式错误");
					}catch(Exception e){
						logger.debug("会员注册", e);
						result.setMessage("系统错误");
					}
				}else{
					result.setMessage("postData参数不能为空");
				}
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("用户注册", e);
			result.setMessage("参数json格式错误");
		}
		return result;
	}
	
	
	
	/**
	 * 会员注册
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("saveCmsMember")
	@Ok("raw")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object saveCmsMember(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				if(StringUtils.isNotBlank(postData)){
					try{
						/**
						 * 获取家长会员信息
						 */
						Member member=Json.fromJson(Member.class, postData);
						Map<String,Object> resultValidateMember=validateMember(member);
						if(resultValidateMember.get("status").equals("OK")){
							
							List<Student> studentList=member.getStudentList();
							if(studentList.size()==0){
								result.setMessage("postData 数据不包含学生数据");
								return result;
							}else{
								Student student=studentList.get(0);
								Map<String,Object> resultValidateStudent=validateStudent(student);
								if(resultValidateStudent.get("status").equals("OK")){
									if(StringUtils.isBlank(member.getId())){
										member.setId(IdGen.uuid());
									}
									if(StringUtils.isBlank(student.getId())){
										student.setId(IdGen.uuid());
									}
									
									School school=memberService.findSchool(student.getSchoolId());
									if(null==school){
										result.setMessage("学校id错误");
										return result;
									}
									member.setAreaId(school.getAreaId());
									/**
									 * 插入环信用户
									 */
									String hxid="hxid"+memberService.saveHXUser(member.getId(), "1").getInt("id");
									/**
									 * 先注册环信平台
									 */
									try{
										ObjectNode createNewIMUserSingleNode=IMBase.createNewIMUserSingle(hxid, member.getPassword(), member.getName());
										if(null!=createNewIMUserSingleNode.get("error")){
											logger.debug(member.getName()+"---"+member.getMobilephone()+"注册账号失败"+Json.toJson(createNewIMUserSingleNode));
											result.setMessage("注册账号失败");
											return Json.toJson(result);
										}
									}catch (Exception e) {
										memberService.deleteHxid(member.getId());
										logger.debug("注册账号失败", e);
										result.setMessage("注册账号失败");
										return Json.toJson(result);
									}
									registerService.entryptPassword(member);
									registerService.saveMember(member);
									Map<String,Object>data=Maps.newHashMap();
									data.put("memberId", member.getId());
									data.put("password", member.getPassword());
									data.put("salt", member.getSalt());
									result.setData(data);
									result.setSuccess(true);
									
								}else{
									result.setMessage(resultValidateStudent.get("message").toString());
								}
							}
						}else{
							result.setMessage(resultValidateMember.get("message").toString());
						}
					}catch (JsonException e) {
						result.setMessage("postData参数json格式错误");
					}catch(Exception e){
						logger.debug("会员注册", e);
						result.setMessage("系统错误");
					}
				}else{
					result.setMessage("postData参数不能为空");
				}
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("用户注册", e);
			result.setMessage("参数json格式错误");
		}
		return Json.toJson(result);
	}
	
	
	/**
	 * 验证手机号是否已经注册了
	 * @param mobile
	 * @return
	 */
	@At("validateMobile")
	@Ok("raw")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object validateMobile(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				try{
					Member m=Json.fromJson(Member.class, postData);
					if(StringUtils.isNotBlank(m.getMobilephone())){
						boolean exist=registerService.validateMobile(m.getMobilephone());
						Map<String,Object>data=new HashMap<String, Object>();
						data.put("exist", exist);
						result.setData(data);
						result.setSuccess(true);
					}else{
						result.setMessage("电话号码不能为空！");
					}
				}catch (JsonException e) {
					result.setMessage("postData参数json格式错误");
				}catch(Exception e){
					logger.debug("验证手机号", e);
					result.setMessage("系统错误");
				}
				
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("验证手机号", e);
			result.setMessage("系统错误");
		}
		System.out.println("validateMobile:"+Json.toJson(result));
		return Json.toJson(result);
	}

	
	
	/**
	 * 发送短信
	 * @param memberId
	 * @return
	 */
	@At("sendSMS")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	@Ok("json")
	public Object sendSMS(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				try{
					Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
					String mobilephone=postMap.get("mobilephone");
					if(StringUtils.isBlank(mobilephone)){
						result.setMessage("手机号码不能为空！");
						return result;
					}
					if(registerService.validateMobile(mobilephone)){
						result.setMessage("该手机号码已经注册被注册了！");
						return result;
					}
					
					int countSMS=memberService.sendSMSCount(mobilephone);
					if(countSMS>6){
						result.setMessage("短信发送次数过多！");
						return result;
					}
					
						int sms=(int)((Math.random()*9+1)*100000);
						/**
						 * 发送短信
						 * 
						 * 
						 * XXXXXX
						 */
						SmsConfig smsConfig=registerService.getSmsConfig();
						if(null!=smsConfig){
							String content="【云南网上学校】验证码："+sms;
							int returnNum=-1;
							try{
								returnNum = SingletonClient.getClient(smsConfig.getSerialnumber(),smsConfig.getKey()).sendSMS(new String[] { mobilephone },content,"",5);
							}catch (Exception e) {
								e.printStackTrace();
							}
							
							SmsLog smslog=new SmsLog();
							smslog.setContent(content);
							smslog.setId(IdGen.uuid());
							smslog.setSendDate(new Date());
							smslog.setStatus(returnNum+"");
							smslog.setSmscode(sms+"");
							smslog.setMobilephone(mobilephone);
							registerService.saveSmsLog(smslog);
							if(returnNum==0){
								result.setSuccess(true);
								result.setMessage("发送成功");
							}else{
								logger.debug("发送失败；原因："+returnNum+"请查看短信发送状态码");
								result.setMessage("发送失败");
							}
						}else{
							result.setMessage("发送失败；原因短信配置不存在");
						}
				}catch (JsonException e) {
					result.setMessage("postData参数json格式错误");
				}catch (Exception e) {
					logger.debug("发送短信", e);
					result.setMessage("系统错误");
				}
				
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("发送短信", e);
			result.setMessage("系统异常");
		} 
		return result;
	}
	
	
	
	/**
	 * 重置密码-发送短信接口
	 * @param memberId
	 * @return
	 */
	@At("sendRestPasswordSMS")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	@Ok("json")
	public Object sendRestPasswordSMS(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				try{
					Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
					String mobilephone=postMap.get("mobilephone");
					if(StringUtils.isBlank(mobilephone)){
						result.setMessage("mobilephone不能为空！");
						return result;
					}
					int countSMS=memberService.sendSMSCount(mobilephone);
					if(countSMS>6){
						result.setMessage("短信发送次数过多！");
						return result;
					}
					
					int sms=(int)((Math.random()*9+1)*100000);
					/**
					 * 发送短信
					 * 
					 * 
					 * XXXXXX
					 */
					SmsConfig smsConfig=registerService.getSmsConfig();
					if(null!=smsConfig){
						String content="【云南网上学校】验证码："+sms;
						int returnNum=-1;
						try{
							returnNum = SingletonClient.getClient(smsConfig.getSerialnumber(),smsConfig.getKey()).sendSMS(new String[] { mobilephone },content,"",5);
						}catch (Exception e) {
							e.printStackTrace();
						}
						
						SmsLog smslog=new SmsLog();
						smslog.setContent(content);
						smslog.setId(IdGen.uuid());
						smslog.setSendDate(new Date());
						smslog.setStatus(returnNum+"");
						smslog.setSmscode(sms+"");
						smslog.setMobilephone(mobilephone);
						registerService.saveSmsLog(smslog);
						if(returnNum==0){
							result.setSuccess(true);
							result.setMessage("发送成功");
						}else{
							logger.debug("发送失败；原因："+returnNum+"请查看短信发送状态码");
							result.setMessage("发送失败");
						}
					}else{
						result.setMessage("发送失败；原因短信配置不存在");
					}
				}catch (JsonException e) {
					result.setMessage("postData参数json格式错误");
				}catch (Exception e) {
					logger.debug("发送短信", e);
					result.setMessage("系统错误");
				}
				
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("发送短信", e);
			result.setMessage("系统异常");
		} 
		return result;
	}
	
	
	
	
	/**
	 * 短信验证码验证
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("validateSMS")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	@Ok("json")
	public Object validateSMS(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String mobilephone=postMap.get("mobilephone");
				String sms=postMap.get("sms");
				if(StringUtils.isNotBlank(mobilephone) && StringUtils.isNotBlank(sms)){
					boolean resultSms=memberService.validateSMS(mobilephone, sms);
					if(resultSms){
						result.setSuccess(true);
						result.setMessage("验证码匹配成功！");
					}else{
						result.setMessage("验证码错误！");
					}
				}else{
					result.setMessage("memberId、sms参数不能为空！");
				}
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (JsonException e) {
			result.setMessage("参数json格式错误");
		}catch (Exception e) {
			logger.debug("短信验证码验证", e);
			result.setMessage("系统异常");
		}
		return result;
	}
	
	
	
	
	
	
	/**
	 * 验证家长信息
	 * @param member
	 * @return
	 */
	public Map<String,Object> validateMember(Member member){
		Map<String,Object>validateResult=new HashMap<String, Object>();
		if(null==member){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "提交数据为空！");
			return validateResult;
		}
		if(StringUtils.isBlank(member.getName())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "参数name不能为空");
			return validateResult;
		}
		if(StringUtils.isBlank(member.getMobilephone())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "参数mobilephone不能为空");
			return validateResult;
		}
		if(!RegexpUtils.isHardRegexpValidate(member.getMobilephone(), RegexpUtils.PHONE_REGEXP)){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "参数mobilephone不是正确的手机号码");
			return validateResult;
		}
		if(registerService.validateMobile(member.getMobilephone())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "该手机号码已经注册被注册了！");
			return validateResult;
		}
		if(StringUtils.isBlank(member.getPassword())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "密码不能为空");
			return validateResult;
		}
		if(StringUtils.isBlank(member.getSms())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "短信验证码不能为空");
			return validateResult;
		}
		if(!memberService.validateSMS(member.getMobilephone(), member.getSms())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "验证码错误！");
			return validateResult;
		}
		validateResult.put("status", "OK");
		return validateResult;
	}
	
	
}








