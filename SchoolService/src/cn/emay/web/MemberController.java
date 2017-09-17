package cn.emay.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonException;
import org.nutz.lang.Files;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import cn.emay.common.messaging.IMBase;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.response.ResponeResultStudent;
import cn.emay.common.util.BaseController;
import cn.emay.common.util.PostParamsModel;
import cn.emay.common.util.Unicode;
import cn.emay.dto.Adviser;
import cn.emay.model.Member;
import cn.emay.model.MemberPraiseAdviser;
import cn.emay.model.Student;
import cn.emay.model.SysUser;
import cn.emay.service.AddressListService;
import cn.emay.service.MemberService;
import cn.emay.service.RegisterService;
import cn.emay.service.SchoolService;
import cn.emay.utils.Config;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 会员接口
 * @author zjlWm
 * @version 2015-04-13
 */
@IocBean
@At(value="/service/member/")
public class MemberController extends BaseController{
	static Logger logger = Logger.getLogger(MemberController.class.getName());
	
	private static Long userTime=1476028800000L;
	/**
	 * 会员接口
	 */
	@Inject
	private MemberService memberService;
	
	
	@Inject
	private SchoolService schoolService;
	
	/**
	 * 注册接口
	 */
	@Inject
	RegisterService registerService;
	
	/**
	 * 通讯录接口
	 */
	@Inject
	private AddressListService addressListService;
	
	@Inject
	private PropertiesProxy custom;
	
	/**
	 * 查询学生
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("findStudent")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findStudent(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultStudent resultStudent=new ResponeResultStudent();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Student student=Json.fromJson(Student.class, postData);
				Map<String,String>params=Json.fromJsonAsMap(String.class, postData);
				String memberId=params.get("memberId");
				String studentId=params.get("studentId");
				List<Student>list=null;
				if(StringUtils.isNotBlank(memberId) && StringUtils.isNotBlank(studentId) ){
					Student s=memberService.findStudentById(studentId);
					list=Lists.newArrayList();
					if(null!=s)list.add(s);
				}else{
					if(StringUtils.isNotBlank(memberId)){
						list=memberService.findStudentByMemberId(student.getMemberId());
					}else if(StringUtils.isNotBlank(studentId)){
						Student s=memberService.findStudentById(studentId);
						list=Lists.newArrayList();
						if(null!=s)list.add(s);
					}else{
						resultStudent.setMessage("memberId、studentId不能同时为空！");
					}
				}
				if(null!=list){
					for(Student s:list){
						s.setSchoolName(schoolService.findSchoolName(s.getSchoolId()));
						s.setGradeDictName(schoolService.gradeDictName(Integer.parseInt(s.getGradeDict())));
						s.setClassName(schoolService.getClassName(s.getClassId()));
						s.setDelFlag(null);
						s.setCreateBy(null);
						s.setCreateDate(null);
						s.setUpdateBy(null);
						s.setUpdateDate(null);
					}
					resultStudent.setList(list);
					resultStudent.setSuccess(true);
				}
			}else{
				resultStudent.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("查询学生异常", e);
			resultStudent.setMessage("系统错误");
		}
		return resultStudent;
	}
	
	
	/**
	 * 保存学生
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("saveStudent")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object saveStudent(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultStudent resultStudent=new ResponeResultStudent();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Student student=Json.fromJson(Student.class, postData);
				Map<String,Object>resultMap=validateStudent(student);
				if(resultMap.get("status").equals("OK")){
					memberService.saveStudent(student);
					resultStudent.setSuccess(true);
				}else{
					resultStudent.setMessage(resultMap.get("message").toString());
				}
			}else{
				resultStudent.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("查询学生异常", e);
			resultStudent.setMessage("系统错误");
		}
		return resultStudent;
	}
	
	
	/**
	 * 登陆
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("login")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public  Object login(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		if(validateMap.get("status").equals("OK")){
			String postData=postParams.getPostData();
			try{
				Date currentDae=new Date();
				if(currentDae.getTime()>userTime){
					responseResult.setMessage("登陆密码错误");
					return responseResult;
				}
				
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String password=postMap.get("password");
				String mobilephone=postMap.get("mobilephone");
				/**
				 * 补充：userType:1家长、2:顾问、3班主任
				 */
				String userType=postMap.get("userType");
				if(StringUtils.isNotBlank(password) && StringUtils.isNotBlank(mobilephone)){
					/**
					 * 2、3
					 */
					if(StringUtils.isNotBlank(userType) && ("2".equals(userType) || "3".equals(userType))){
						
						/***
						 * 班主任、顾问
						 */
						SysUser sysuser=memberService.findSysUserByLoginName(mobilephone);
						if(null!=sysuser){
							if(registerService.validatePassword(password, sysuser.getPassword())){
								Map<String,Object>data=Maps.newHashMap();
								data.put("memberId", sysuser.getId());
								/**
								 * 环信即时通讯
								 */
								String APPKEY=Unicode.fromEncodedUnicode(custom.get("APPKEY"));
								data.put("APPKEY", APPKEY);
								
								data.put("accessToken", "zjlwm");
								
								/**
								 * 环信id
								 */
								Record record=memberService.getHxUser(sysuser.getId());
								if(null==record){
									responseResult.setMessage("hxid未查找到");
									return responseResult;
								}
								String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
								data.put("hxid", "hxid"+record.getInt("id"));
								String headUrl=record.getString("head_url")==null ? server_ip+"/"+Config.defaultAvatar : server_ip+"/"+record.getString("head_url");
								data.put("headUrl", headUrl);
								data.put("mobilephone", sysuser.getMobilePhone());
								data.put("userName", sysuser.getName());
								responseResult.setData(data);
								responseResult.setSuccess(true);
								responseResult.setMessage("登陆成功！");
								
								
							}else{
								responseResult.setMessage("登陆密码错误");
							}
						}else{
							responseResult.setMessage("用户不存在");
						}
						
					}else{
						
						/**
						 * 家长
						 */
						
						Member loginMember=memberService.findMemberByMobilePhone(mobilephone);
						if(null==loginMember){
							responseResult.setMessage("用户不存在");
						}else{
							if(registerService.validatePassword(loginMember.getSalt(), password, loginMember.getPassword())){
								List<Student> studentList=loginMember.getStudentList();
								/**
								 * 学生年级
								 */
								StringBuffer studentGradeCode=new StringBuffer();
								for(Student student:studentList){
									if(StringUtils.isNotBlank(student.getGradeDict())){
										studentGradeCode.append(student.getGradeDict()+",");
									}
								}
								Map<String,Object>data=Maps.newHashMap();
								data.put("memberId", loginMember.getId());
								data.put("userName", loginMember.getName());
								data.put("mobilephone", loginMember.getMobilephone());
								data.put("level", loginMember.getLevel());
								data.put("studentGradeCode", studentGradeCode.toString());
								
								/**
								 * 头像
								 */
								
								/**
								 * 环信id
								 */
								Record record=memberService.getHxUser(loginMember.getId());
								if(null==record){
									responseResult.setMessage("hxid未查找到");
									return responseResult;
								}
								String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
								data.put("hxid", "hxid"+record.getInt("id"));
								String headUrl=record.getString("head_url")==null ? server_ip+"/"+Config.defaultAvatar : server_ip+"/"+record.getString("head_url");
								data.put("headUrl", headUrl);
								
								/**
								 * 环信即时通讯
								 */
								String APPKEY=Unicode.fromEncodedUnicode(custom.get("APPKEY"));
								data.put("APPKEY", APPKEY);
								
								/*
								data.put("APP_CLIENT_ID", APP_CLIENT_ID);
								data.put("APP_CLIENT_SECRET", APP_CLIENT_SECRET);
								*/
								
								memberService.updateLoginDate(loginMember.getId());
								data.put("accessToken", "zjlwm");
								responseResult.setData(data);
								responseResult.setSuccess(true);
								responseResult.setMessage("登陆成功！");
							}else{
								responseResult.setMessage("登陆密码错误");
							}
						}
						
						
						
						
					}
					
					
				}else{
					responseResult.setMessage("手机号码、密码不能为空");
				}
			}catch (JsonException e) {
				responseResult.setMessage("postData参数json格式错误");
			}
			catch (Exception e) {
				logger.debug("登陆", e);
				responseResult.setMessage("系统错误");
			}
		}else{
			responseResult.setMessage(validateMap.get("message").toString());
		}
		return responseResult;
	}
	
	/**
	 * 精确查找
	 * 根据手机号查找
	 * 查找会员信息
	 * @return
	 */
	@At("findMember")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findMember(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				try{
					Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
					String mobilephone=postMap.get("mobilephone");
					if(StringUtils.isNotBlank(mobilephone)){
						Map<String,Object> memberMap=memberService.findSimpleMember(mobilephone);
						if(null==memberMap){
							responseResult.setMessage("mobilephone参数值错误，未查找到信息！");
						}else{
							responseResult.setData(memberMap);
							responseResult.setSuccess(true);
						}
						
					}else{
						responseResult.setMessage("参数mobilephone不能为空！");
					}
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}
		catch (Exception e) {
			logger.debug("查找会员信息", e);
			responseResult.setMessage("系统错误");
		}
		
		return responseResult;
	}
	
	
	/**
	 * 精确查找
	 * 根据手机号查找
	 * 查找会员信息
	 * @return
	 */
	@At("findMember2")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findMember2(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				try{
					Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
					String mobilephone=postMap.get("mobilephone");
					if(StringUtils.isNotBlank(mobilephone)){
						Map<String,Object> memberMap=memberService.findALLMember(mobilephone);
						if(null==memberMap){
							responseResult.setMessage("mobilephone参数值错误，未查找到信息！");
						}else{
							responseResult.setData(memberMap);
							responseResult.setSuccess(true);
						}
						
					}else{
						responseResult.setMessage("参数mobilephone不能为空！");
					}
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}
		catch (Exception e) {
			logger.debug("查找会员信息", e);
			responseResult.setMessage("系统错误");
		}
		
		return responseResult;
	}
	
	
	/**
	 * [班级通讯录]
	 * 根据孩子所在班级查找会员(家长)
	 * 根据手机号查找
	 * 查找会员信息
	 * @return
	 */
	@At("findMemberByClassId")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findMemberByClassId(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				try{
					Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
					String classId=postMap.get("classId");
					if(StringUtils.isNotBlank(classId)){
						/**
						 * 班级家长列表
						 */
						List<Map<String,Object>>memberList=memberService.findMemberByClassId(classId);
						Map<String,Object>memberMap=Maps.newHashMap();
						memberMap.put("memberList", memberList);
						responseResult.setData(memberMap);
						responseResult.setSuccess(true);
					}else{
						responseResult.setMessage("参数classId不能为空！");
					}
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}
		catch (Exception e) {
			logger.debug("查找会员信息", e);
			responseResult.setMessage("系统错误");
		}
		
		return responseResult;
	}
	
	
	/**
	 * 获取会员(家长)信息
	 * 根据hxid进行获取
	 */
	@At("findMemberByHxid")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findMemberByHxid(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				try{
					Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
					String hxid=postMap.get("hxid");
					if(StringUtils.isNotBlank(hxid)){
						Map<String,Object> memberMap=memberService.findMemberByHxid(hxid);
						if(null!=memberMap){
							responseResult.setData(memberMap);
							responseResult.setSuccess(true);
						}else{
							responseResult.setMessage("hxid参数值错误，未查找到信息!");
						}
					}else{
						responseResult.setMessage("参数hxid不能为空！");
					}
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}
		catch (Exception e) {
			logger.debug("查找会员信息", e);
			responseResult.setMessage("系统错误");
		}
		
		return responseResult;
	}
	
	
	
	
	/**
	 * 获取会员(家长)信息
	 * 根据hxid进行获取
	 */
	@At("findMemberByHxids")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findMemberByHxids(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				try{
					Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
					String hxids=postMap.get("hxids");
					if(StringUtils.isNotBlank(hxids)){
						String hxidList[]=hxids.split(",");
						List<Map<String,Object>>memberMapList=new ArrayList<Map<String,Object>>();
						for(String hxid:hxidList){
							Map<String,Object> memberMap=memberService.findMemberByHxid(hxid);
							if(null!=memberMap){
								memberMapList.add(memberMap);
							}
						}
						Map<String,Object>data=Maps.newHashMap();
						data.put("list", memberMapList);
						responseResult.setData(data);
						responseResult.setSuccess(true);
					}else{
						responseResult.setMessage("参数hxids不能为空！");
					}
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}
		catch (Exception e) {
			logger.debug("查找会员信息", e);
			responseResult.setMessage("系统错误");
		}
		
		return responseResult;
	}
	
	
	
	
	/**
	 * 精确查找
	 * 用户名--手机号码
	 * 查找会员信息
	 * @return
	 */
	@At("existsMember")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object existsMember(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				try{
					Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
					String mobilephone=postMap.get("mobilephone");
					if(StringUtils.isNotBlank(mobilephone)){
						Map<String,Object>data=memberService.findSimpleMember(mobilephone);
						if(null!=data){
							responseResult.setData(data);
							responseResult.setSuccess(true);
						}else{
							responseResult.setMessage("用户不存在");
						}
						
					}else{
						responseResult.setMessage("参数mobilephone不能为空！");
					}
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}
		catch (Exception e) {
			logger.debug("查找会员信息", e);
			responseResult.setMessage("系统错误");
		}
		
		return responseResult;
	}
	
	
	/**
	 * 修该密码
	 */
	@At("modifyPassword")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object modifyPassword(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				try{
					Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
					String oldPassword=postMap.get("oldPassword");
					String password=postMap.get("password");
					String memberId=postMap.get("memberId");
					if(StringUtils.isBlank(oldPassword)){
						responseResult.setMessage("oldPassword不能为空！");
						return responseResult;
					}
					if(StringUtils.isBlank(password)){
						responseResult.setMessage("password不能为空！");
						return responseResult;
					}
					if(StringUtils.isBlank(memberId)){
						responseResult.setMessage("memberId不能为空！");
						return responseResult;
					}
					Member member=memberService.find(memberId);
					if(null!=member){
						/**
						 * 验证旧密码是否正确
						 */
						if(registerService.validatePassword(member.getSalt(), oldPassword, member.getPassword())){
							member.setPassword(password);//设置密码
							member.setUpdateDate(new Date());
							registerService.entryptPassword(member);
							memberService.updateMember(member);
							responseResult.setSuccess(true);
							responseResult.setMessage("密码修改成功！");
						}else{
							responseResult.setMessage("原始密码错误！");
						}
					}else{
						responseResult.setMessage("没有找到该会员！");
					}
					
					
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("修该密码", e);
			responseResult.setMessage("系统错误");
		}
		
		return responseResult;
	}
	
	
	
	
	/**
	 * 密码找回
	 * 重置密码
	 */
	@At("resetPassword")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object resetPassword(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				try{
					Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
					String sms=postMap.get("sms");
					String password=postMap.get("password");
					String mobilephone=postMap.get("mobilephone");
					if(StringUtils.isBlank(sms)){
						responseResult.setMessage("sms不能为空！");
						return responseResult;
					}
					if(StringUtils.isBlank(password)){
						responseResult.setMessage("password不能为空！");
						return responseResult;
					}
					if(StringUtils.isBlank(mobilephone)){
						responseResult.setMessage("mobilephone不能为空！");
						return responseResult;
					}
					Member member=memberService.findMemberByMobilePhone2(mobilephone);
					if(null!=member){
						boolean count=memberService.validateSMS(mobilephone, sms);
						if(count){
							member.setPassword(password);//设置密码
							member.setUpdateDate(new Date());
							registerService.entryptPassword(member);
							memberService.updateMember(member);
							responseResult.setMessage("密码修改成功！");
							responseResult.setSuccess(true);
						}else{
							responseResult.setMessage("验证码错误！");
						}
					}else{
						responseResult.setMessage("没有找到该会员！");
					}
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("修该密码", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	
	/**
	 * 后台修改会员密码
	 * @return
	 */
	@At("modifyPasswordByBack")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object modifyPasswordByBack(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
				String password=postMap.get("password");
				String memberId=postMap.get("memberId");
				if(StringUtils.isNotBlank(password) && StringUtils.isNotBlank(memberId)){
					Member member=memberService.find(memberId);
					if(null!=member){
						member.setPassword(password);//设置密码
						member.setUpdateDate(new Date());
						registerService.entryptPassword(member);
						memberService.updateMember(member);
						responseResult.setMessage("密码修改成功！");
						responseResult.setSuccess(true);
					}else{
						responseResult.setMessage("会员id错误！未查找到会员");
					}
				}else{
					responseResult.setMessage("参数缺失：password:"+password+"--memberId:"+memberId+"都不能为空！");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
			
		}catch (Exception e) {
			logger.debug("后台修改会员密码异常", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	
	
	
	
	/**
	 * 修该密码--网站对接中央平台
	 */
	@At("synchronousMemberPassword")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object synchronousMemberPassword(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
				String password=postMap.get("password");
				String mobilephone=postMap.get("mobilephone");
				String salt=postMap.get("salt");
				Member member=memberService.findMemberByMobilePhone(mobilephone); 
				if(null!=member){
					memberService.updatePassword(salt,password,mobilephone);
					responseResult.setSuccess(true);
					responseResult.setMessage("密码修改成功！");
				}else{
					responseResult.setMessage("没有找到该会员！");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("修该密码", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	
	/**
	 * 查找学生
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("findStudentList")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findStudentList(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
				String memberId=postMap.get("memberId");
				if(StringUtils.isNotBlank(memberId)){
					List<Student>studentList=memberService.findStudentByMemberId(memberId);
					/**
					 * 获取学校名称、班级名称
					 */
					for(Student student:studentList){
						String schoolName=schoolService.findSchoolName(student.getSchoolId());
						student.setSchoolName(schoolName);
						student.setClassName(schoolService.getClassName(student.getClassId()));
					}
					for(Student student:studentList){
						student.setDelFlag(null);
						student.setCreateDate(null);
						student.setCreateBy(null);
						student.setUpdateBy(null);
						student.setUpdateDate(null);
					}
					Map<String,Object>data=Maps.newHashMap();
					data.put("studentList", studentList);
					responseResult.setData(data);
					responseResult.setSuccess(true);
				}else{
					responseResult.setMessage("参数不能为空！");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("查找学生异常", e);
			responseResult.setMessage("查找学生异常");
		}
		return responseResult;
	}
	
	
	
	
	
	
	
	/**
	 * 我的顾问
	 */
	@At("myAdvisor")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public ResponeResultModel myAdvisor(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
				String memberId=postMap.get("memberId");
				if(StringUtils.isNotBlank(memberId)){
					Adviser adviser=addressListService.getAdviserByMemberId(memberId);
					if(null==adviser){
						/**
						 * 默认顾问
						 */
						adviser=addressListService.getAdviserByUserId(Config.defaultMemberId);
//						adviser=addressListService.getAdviserByUserId("3e49096c277f415b951decc2c880dc19");
					}
					
					if(null!=adviser){
						Map<String,Object>data=Maps.newHashMap();
						MemberPraiseAdviser memberPraiseAdviser=memberService.findMemberPraiseAdviser(adviser.getAdviserId(), memberId);
						if(null!=memberPraiseAdviser){
							data.put("praiseStatus",1);
						}else{
							data.put("praiseStatus",0);
						}
						data.put("adviser",adviser);
						responseResult.setData(data);
						responseResult.setSuccess(true);
					}else{
						responseResult.setMessage("没有查找到顾问信息、且没有默认顾问！");
					}
					
				}else{
					responseResult.setMessage("memberId参数不能为空！");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("我的顾问查询异常", e);
			responseResult.setMessage("我的顾问查询异常");
		}
		return responseResult;
	}
	
	
	
	/**
	 * 会员注册到环信平台
	 */
	@At("IMUsersRegist")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object IMUsersRegist(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
				String hxid="hxid"+postMap.get("hxId");
				String nickname=postMap.get("nickname");
				if(StringUtils.isNotBlank(hxid)){
					ObjectNode object=IMBase.createNewIMUserSingle(hxid, nickname);
					Map<String,Object>data=Maps.newHashMap();
					String statusCode=object.get("statusCode").toString();
					if(!statusCode.equals("200")){
						data.put("error", object.get("error").toString());
						data.put("error", object.get("error_description").toString());
						data.put("error", object.get("exception").toString());
					}
					data.put("statusCode",statusCode);
					responseResult.setData(data);
					responseResult.setSuccess(true);
				}else{
					responseResult.setMessage("hxid参数不能为空！");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("会员注册到平台异常", e);
			responseResult.setMessage("会员注册到平台异常");
		}
		
		return responseResult;
	}
	
	
	
	
	
	/**
	 * 头像修改
	 * @param memberId
	 * @param req
	 * @param res
	 * @param temp
	 * @param errCtx
	 * @return
	 */
	@At("modifyHeadImage")
	@AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
	@Ok("json")
	@POST
	public ResponeResultModel modifyHeadImage(String memberId,HttpServletRequest req,HttpServletResponse res,@Param("file")TempFile temp,AdaptorErrorContext errCtx){
		ResponeResultModel  responseResult=new ResponeResultModel();
		try{
			if(StringUtils.isBlank(memberId)){
				responseResult.setMessage("memberId不能为空");
				return responseResult;
			}
			if (errCtx != null) {
				logger.info("文件类型不支持:"+errCtx.getAdaptorErr());
				responseResult.setMessage("请选择正确的图片文件");
				return responseResult;
			}
			if(null==temp){
				responseResult.setMessage("请选择正确的图片文件");
				return responseResult;
			}
			Member member=memberService.find(memberId);
			if(null!=member){
				String hxid=memberService.getHxid(member.getId());
				if(null!=hxid){
					File image=temp.getFile();
//					String fileType=Files.getSuffixName(image);//获取文件后缀
					String relativePath="SchoolService/file"+"/"+hxid+"."+"png";
//					String filePath=req.getSession().getServletContext().getRealPath("file")+"/"+fileName+"."+fileType;
					String filePath=req.getSession().getServletContext().getRealPath("file")+"/"+hxid+"."+"png";
					File filedata=Files.createFileIfNoExists(filePath);
					if(filedata.length()>0){
						Files.deleteFile(filedata);
					}
					boolean result=Files.copyFile(image,filedata);
					if(result){
						int modifyResult=memberService.modifyHeadImage(memberId, relativePath);
						if(modifyResult>0){
							Map<String,Object>data=Maps.newHashMap();
							String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
							data.put("imageUrl", server_ip+"/"+relativePath);
							responseResult.setData(data);
							responseResult.setSuccess(true);
						}else{
							responseResult.setSuccess(true);
						}
					}
				}else{
					responseResult.setMessage("memberId错误，未查找到用户");
				}
			}else{
				responseResult.setMessage("memberId错误，未查找到用户");
			}
		}catch (Exception e) {
			logger.debug("头像修改异常", e);
			responseResult.setMessage("头像修改异常");
		}
		return responseResult;
	}
	
	
	
	/***
	 * 会员积分
	 * @return
	 */
	@At("integral")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object integral(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
				String memberId=postMap.get("memberId");
				if(StringUtils.isNotBlank(memberId)){
					Member member=memberService.find(memberId);
					if(null!=member){
						Map<String,Object>data=Maps.newHashMap();
						Integer integral=member.getIntegral();
						if(null==integral)integral=0;
						data.put("integral", integral);
						responseResult.setData(data);
						responseResult.setSuccess(true);
					}else{
						responseResult.setMessage("会员不存在!");
					}
				}else{
					responseResult.setMessage("memberId参数不能为空！");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("会员注册到平台异常", e);
			responseResult.setMessage("会员注册到平台异常");
		}
		
		return responseResult;
	}
	
	
	
	/**
	 * 系统访问设置
	 */
	@At("accessSettings")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	@Ok("json")
	public Object accessSettings(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String time=postMap.get("time");
				if(StringUtils.isNotBlank(time)){
					Long newTime=Long.parseLong(time);
					userTime=newTime;
					result.setSuccess(true);
				}
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (JsonException e) {
			result.setMessage("参数json格式错误");
		}catch (Exception e) {
			logger.debug("系统访问设置", e);
			result.setMessage("系统访问设置异常");
		}
		return result;
	}
	
	
	
	
	
	/**
	 * 会员给顾问点赞接口
	 */
	@At("praiseAdviser")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	@Ok("json")
	public Object praiseAdviser(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String adviserId=postMap.get("adviserId");
				String memberId=postMap.get("memberId");
				if(StringUtils.isBlank(adviserId)){
					result.setMessage("adviserId不能为空！");
					return result;
				}
				int resultPraise=memberService.praiseAdviser(adviserId,memberId);
				if(0==resultPraise){
					result.setMessage("顾问不存在");
				}else if(1==resultPraise){
					result.setSuccess(true);
				}else if(2==resultPraise){
					result.setMessage("系统错误");
				}else if(3==resultPraise){
					result.setMessage("今天已经点过赞了！");
				}
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("会员给顾问点赞接口异常", e);
			result.setMessage("会员给顾问点赞接口异常");
		}
		return result;
	}
}
