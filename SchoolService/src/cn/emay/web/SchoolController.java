package cn.emay.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonException;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import cn.emay.common.response.ResponeResultClasses;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.response.ResponeResultSchool;
import cn.emay.common.util.BaseController;
import cn.emay.common.util.PostParamsModel;
import cn.emay.common.util.SignatureUtils;
import cn.emay.model.Classes;
import cn.emay.model.Grade;
import cn.emay.model.School;
import cn.emay.service.SchoolService;

import com.google.common.collect.Maps;

/**
 * 学校接口
 * @author zjlwm
 * @version 2015-04-07
 *
 */
@At(value="/service/school/")
@IocBean
public class SchoolController extends BaseController{
	static Logger logger = Logger.getLogger(SchoolController.class.getName());
	
	@Inject
	private SchoolService schoolService;
	/**
	 * 获取Token
	 * @return
	 */
	@At(value="getToken")
	@GET
	@Ok("raw")
	public String getToken(@Param(value="loginname")String loginname,@Param(value="password")String password){
		String token="";
		try{
			
			/**
			 * 
			 * 如果用户密码正确、则放回token
			 */
			String nonce=Math.random()+"";
			String timestamp=(new Date().getTime()/1000)+"";
			token=SignatureUtils.getSignature(loginname+password, timestamp, nonce);
		}catch (Exception e) {
			logger.debug("获取Token异常", e);
		}
		return token;
	}
	
	/**
	 * get请求
	 */
	@At(value="get")
	@GET
	public void get(){
		
	}
	
	
	/**
	 * post请求
	 */
	@At("post")
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
	@Ok("json")
	@Aop(value="aopInterceptor")
	public void post(HttpServletRequest req,HttpServletResponse res){
		try{
			InputStream in=req.getInputStream();
			Reader reader=new InputStreamReader(in);
			BufferedReader buffere=new BufferedReader(reader);
			String data;
			while((data=buffere.readLine())!=null){
				System.out.println(data);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 学校数据提交
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("saveSchool")
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
	@Ok("json")
	public ResponeResultModel saveSchool(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				try{
					School school=Json.fromJson(School.class, postData);
					if(StringUtils.isBlank(school.getSchoolName())){
						responseResult.setMessage("学校名称不能为空！");
						return responseResult;
					}
					if(StringUtils.isBlank(school.getAreaId())){
						responseResult.setMessage("区域id不能为空！");
						return responseResult;
					}
					school.setDelFlag("0");
					school.setFreezeFlag("0");
					String schoolId=schoolService.saveSchool(school).getId();
					Map<String,Object>data=Maps.newConcurrentMap();
					data.put("schoolId", schoolId);
					responseResult.setData(data);
					responseResult.setSuccess(true);
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}catch(Exception e){
					responseResult.setMessage("系统错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			responseResult.setMessage("参数json格式错误");
		}
		return responseResult;
	}
	
	
	/**
	 * 学校查找
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("findSchool")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public ResponeResultSchool findSchool(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultSchool responseResult=new ResponeResultSchool();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				try{
					School school=Json.fromJson(School.class, postData);
					if(StringUtils.isNotBlank(school.getAreaId())){
						List<School>list=schoolService.findSchool(school.getAreaId());
						for(School s:list){
							s.setDelFlag(null);
							s.setFreezeFlag(null);
						}
						responseResult.setList(list);
						responseResult.setSuccess(true);
					}
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}catch(Exception e){
					responseResult.setMessage("系统错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			e.printStackTrace();
			responseResult.setMessage("参数json格式错误");
		}
		return responseResult;
	}
	
	@At("findSchoolAll")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public ResponeResultSchool findSchoolAll(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultSchool responseResult=new ResponeResultSchool();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				try{
					School school=Json.fromJson(School.class, postData);
					if(StringUtils.isNotBlank(school.getAreaId())){
						List<School>list=schoolService.findSchool(school.getAreaId());
						for(School s:list){
							s.setDelFlag(null);
							s.setFreezeFlag(null);
							List<Grade> gradeList=schoolService.findGrade();
							for(Grade grade:gradeList){
								Classes classes=new Classes();
								classes.setSchoolId(s.getId());
								classes.setGradeCode(grade.getGradeCode());
								List<Classes>classesList=schoolService.findClasses(classes);
								grade.setList(classesList);
							}
							s.setList(schoolService.findGrade());
						}
						responseResult.setList(list);
						responseResult.setSuccess(true);
					}
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}catch(Exception e){
					responseResult.setMessage("系统错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("系统异常", e);
			responseResult.setMessage("参数json格式错误");
		}
		System.out.println("findSchool:"+Json.toJson(responseResult));
		return responseResult;
	}
	
	
	/**
	 * 年级查找
	 */
	@At("findGrade")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public ResponeResultModel findGrade(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				List<Grade> gradeList=schoolService.findGrade();
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", gradeList);
				responseResult.setData(data);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			e.printStackTrace();
			responseResult.setMessage("参数json格式错误");
		}
		return responseResult;
	}
	
	/**
	 * 查找班级
	 * @return
	 */
	@At("findClasses")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public ResponeResultClasses findClasses(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultClasses responseResult=new ResponeResultClasses();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				try{
					Classes classes=Json.fromJson(Classes.class, postData);
					List<Classes>list=schoolService.findClasses(classes);
					for(Classes s:list){
						s.setDelFlag(null);
						s.setGraduationFlag(null);
					}
					responseResult.setList(list);
					responseResult.setSuccess(true);
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}catch(Exception e){
					responseResult.setMessage("系统错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			e.printStackTrace();
			responseResult.setMessage("参数json格式错误");
		}
		return responseResult;
	}
	
	
	/**
	 * 班级保存
	 * @return
	 */
	@At("saveClasses")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public ResponeResultModel saveClasses(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				try{
					Classes classes=Json.fromJson(Classes.class, postData);
					List<Classes>list=schoolService.findClasses(classes);
					for(Classes s:list){
						s.setDelFlag(null);
						s.setGraduationFlag(null);
					}
					Map<String,Object>data=Maps.newHashMap();
					data.put("list", list);
					responseResult.setData(data);
					responseResult.setSuccess(true);
				}catch (JsonException e) {
					responseResult.setMessage("postData参数json格式错误");
				}catch(Exception e){
					responseResult.setMessage("系统错误");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			e.printStackTrace();
			responseResult.setMessage("参数json格式错误");
		}
		return responseResult;
	}
	
	
	
//	/**
//	 * 参数验证
//	 * @param postParams
//	 */
//	public Map<String,Object> validateParams(PostParamsModel postParams){
//		Map<String,Object>validateResult=new HashMap<String, Object>();
//		if(null==postParams){
//			validateResult.put("status", "ERROR");
//			validateResult.put("message", "提交数据为空！");
//			return validateResult;
//		}
//		if(StringUtils.isBlank(postParams.getNonce())){
//			validateResult.put("status", "ERROR");
//			validateResult.put("message", "参数noce不能为空");
//			return validateResult;
//		}
//		if(StringUtils.isBlank(postParams.getSignature())){
//			validateResult.put("status", "ERROR");
//			validateResult.put("message", "参数signature不能为空");
//			return validateResult;
//		}
//		if(StringUtils.isBlank(postParams.getPostData())){
//			validateResult.put("status", "ERROR");
//			validateResult.put("message", "参数postData不能为空");
//			return validateResult;
//		}
//		validateResult.put("status", "OK");
//		return validateResult;
//	}
}
