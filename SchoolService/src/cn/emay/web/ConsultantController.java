package cn.emay.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;

import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.response.ResponeResultStudent;
import cn.emay.common.util.BaseController;
import cn.emay.common.util.DateUtils;
import cn.emay.common.util.IdGen;
import cn.emay.common.util.PostParamsModel;
import cn.emay.dto.Adviser;
import cn.emay.model.AdviserActivity;
import cn.emay.model.AdviserActivityRecord;
import cn.emay.model.Consult;
import cn.emay.model.Member;
import cn.emay.model.School;
import cn.emay.model.Student;
import cn.emay.model.consultant.MemberStar;
import cn.emay.model.consultant.PublishLearning;
import cn.emay.model.consultant.StudentRemarks;
import cn.emay.model.consultant.StudentRemind;
import cn.emay.service.AddressListService;
import cn.emay.service.ConsultantService;
import cn.emay.service.MemberService;
import cn.emay.utils.Config;

import com.google.common.collect.Maps;


/**
 * 
 * 顾问接口
 * @author zjlWm
 * @version 2015-04-13
 */
@IocBean
@At(value="/service/consultant/")
public class ConsultantController  extends BaseController{
	static Logger logger = Logger.getLogger(ConsultantController.class.getName());
	
	@Inject
	private ConsultantService consultantService;
	
	@Inject
	private AddressListService addressListService;
	
	@Inject
	private MemberService memberService;
	
	/**
	 * 发布学习内容
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("publishLearning/add")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object addPublishLearning(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultStudent responseResult=new ResponeResultStudent();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				PublishLearning publishLearning=Json.fromJson(PublishLearning.class, postData);
				if(StringUtils.isBlank(publishLearning.getMemberId())){
					responseResult.setMessage("memberId不能空");
					return responseResult;
				}
				
				
				if(StringUtils.isBlank(publishLearning.getTitle())){
					responseResult.setMessage("title不能空");
					return responseResult;
				}
				
				if(StringUtils.isBlank(publishLearning.getContent())){
					responseResult.setMessage("content不能空");
					return responseResult;
				}
				
				
				if(StringUtils.isBlank(publishLearning.getType())){
					responseResult.setMessage("类型type不能空");
					return responseResult;
				}
					
				String type=publishLearning.getType();
				if(type.equals("1")){
					/**
					 * 1、区域+学校
					 */
					if(StringUtils.isBlank(publishLearning.getAreaId())){
						responseResult.setMessage("areaId不能空");
						return responseResult;
					}
				}else{
					/**
					 * 2、年级
					 */
					if(StringUtils.isBlank(publishLearning.getGradeCode())){
						responseResult.setMessage("gradeCode不能空");
						return responseResult;
					}
				}
				
				publishLearning.setId(IdGen.uuid());
				Date createDate=new Date();
				publishLearning.setCreateDate(createDate);
				publishLearning.setCreateBy(publishLearning.getMemberId());
				publishLearning.setUpdateDate(createDate);
				publishLearning.setUpdateBy(publishLearning.getMemberId());
				consultantService.publishLearningSave(publishLearning);
				responseResult.setSuccess(true);
				
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("发布学习内容异常", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	
	
	/**
	 * 提供给家长版本app查询发布学习内容
	 * 发布学习内容查询
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("publishLearning/list")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findPublishLearning(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String memberId=postMap.get("memberId");
				if(StringUtils.isBlank(memberId)){
					responseResult.setMessage("memberId不能空");
					return responseResult;
				}
				/**
				 * 家长的孩子
				 */
				List<Student>studentList=memberService.findStudentByMemberId(memberId);
				/**
				 * 区域id
				 */
				List<String>areaList=new ArrayList<String>();
				
				/**
				 * 学校id
				 */
				List<String>schoolIdList=new ArrayList<String>();
				
				/**
				 * 年级id
				 */
				List<String>gradeList=new ArrayList<String>();
				
				for(Student student:studentList){
					if(!schoolIdList.contains(student.getSchoolId())){
						schoolIdList.add(student.getSchoolId());
						
						School school=memberService.findSchool(student.getSchoolId());
						if(null!=school && !areaList.contains(school.getAreaId())){
							areaList.add(school.getAreaId());
						}
						
					}
					if(!gradeList.contains(student.getGradeDict())){
						gradeList.add(student.getGradeDict());
					}
					
				}
				
				Adviser adviser=addressListService.getAdviserByMemberId(memberId);
				String adviserId=Config.defaultMemberId;
				if(null!=adviser){
					adviserId=adviser.getAdviserId();
				}
				List<PublishLearning>list=consultantService.findPublishLearningByWhere(adviserId, schoolIdList, areaList, gradeList);
				for(PublishLearning p:list){
					p.setDelFlag(null);
					p.setUpdateDate(null);
					p.setUpdateBy(null);
					p.setCreateDate(null);
					p.setCreateBy(null);
				}
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", list);
				responseResult.setData(data);
				responseResult.setSuccess(true);
				
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("发布学习内容查询异常", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	
	
	
	
	
	/**
	 * 提供给顾问app查询
	 * 发布学习内容查询
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("publishLearning/list2")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findPublishLearning2(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String memberId=postMap.get("memberId");
				if(StringUtils.isBlank(memberId)){
					responseResult.setMessage("memberId不能空");
					return responseResult;
				}
				List<PublishLearning>list=consultantService.findPublishLearningList(memberId);
				for(PublishLearning p:list){
					p.setDelFlag(null);
					p.setUpdateDate(null);
					p.setUpdateBy(null);
					p.setCreateDate(null);
					p.setCreateBy(null);
				}
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", list);
				responseResult.setData(data);
				responseResult.setSuccess(true);
				
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("发布学习内容异常", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	
	
	      
	
	
	/**
	 * 家长提醒内容添加
	 */
	@At("studentRemind/add")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object addStudentRemind(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultStudent responseResult=new ResponeResultStudent();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String content=postMap.get("content");
				String remindDate=postMap.get("remindDate");
				String memberId=postMap.get("memberId");
				if(StringUtils.isBlank(memberId)){
					responseResult.setMessage("memberId不能为空！");
					return responseResult;
				}
				
				if(StringUtils.isBlank(content)){
					responseResult.setMessage("content不能为空！");
					return responseResult;
				}
				
				
				if(StringUtils.isBlank(remindDate)){
					responseResult.setMessage("remindDate不能为空！");
					return responseResult;
				}
				
				if(StringUtils.isBlank(memberId)){
					responseResult.setMessage("memberId不能为空！");
					return responseResult;
				}
				
				try{
					DateUtils.parseDate(remindDate, "yyyy-MM-dd HH:mm");
				}catch (Exception e) {
					responseResult.setMessage("remindDate时间格式错误！例如：2015-07-10 12:34");
					return responseResult;
				}
				
				StudentRemind  studentRemind=new StudentRemind();
				studentRemind.setId(IdGen.uuid());
				studentRemind.setMemberId(memberId);
				studentRemind.setContent(content);
				studentRemind.setRemindDate(DateUtils.parseDate(remindDate, "yyyy-MM-dd HH:mm"));
				studentRemind.setCreateBy(memberId);
				studentRemind.setCreateDate(new Date());
				consultantService.studentRemindSave(studentRemind);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("学生提醒内容添加异常", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	
	
	
	/**
	 * 家长【学生】提醒内容查询
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("studentRemind/list")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findStudentRemind(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				/**
				 * 顾问id
				 */
				String adviserId=postMap.get("adviserId");
				String status=postMap.get("status");
				if(StringUtils.isBlank(adviserId)){
					responseResult.setMessage("adviserId不能空");
					return responseResult;
				}
				String memberId=postMap.get("memberId");
				List<StudentRemind>list=new ArrayList<StudentRemind>();
				if(StringUtils.isBlank(memberId)){
					list=consultantService.findAllStudentRemind(adviserId, status);
				}else{
					list=consultantService.findStudentRemindList(memberId,status);
				}
				
				for(StudentRemind p:list){
					p.setDelFlag(null);
					p.setCreateDate(null);
					p.setCreateBy(null);
					Member member=memberService.find(p.getMemberId());
					String hxid=memberService.getHxid(p.getMemberId());
					if(StringUtils.isNotBlank(hxid)){
						p.setHxid(hxid);
					}else{
						p.setHxid("");
					}
					if(null!=member){
						p.setMemberName(member.getName());
					}else{
						p.setMemberName("");
					}
				}
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", list);
				responseResult.setData(data);
				responseResult.setSuccess(true);
				
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("设置学生提醒内容查询异常", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	
	
	
	
//	/**
//	 * 顾问的所有家长【学生】提醒查询
//	 * @param postParams
//	 * @param req
//	 * @param res
//	 * @return
//	 */
//	@At("studentRemind/listALL")
//	@Ok("json")
//	@AdaptBy(type=JsonAdaptor.class)
//	@POST
//	public Object findAllStudentRemind(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
//		ResponeResultModel responseResult=new ResponeResultModel();
//		try{
//			Map<String,Object>validateMap=validateParams(postParams);
//			if(validateMap.get("status").equals("OK")){
//				String postData=postParams.getPostData();
//				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
//				/**
//				 * 顾问id
//				 */
//				String adviserId=postMap.get("adviserId");
//				String status=postMap.get("status");
//				if(StringUtils.isBlank(adviserId)){
//					responseResult.setMessage("adviserId不能空");
//					return responseResult;
//				}
//				List<StudentRemind>list=consultantService.findAllStudentRemind(adviserId, status);
//				Map<String,Object>data=Maps.newHashMap();
//				data.put("list", list);
//				responseResult.setData(data);
//				responseResult.setSuccess(true);
//			}else{
//				responseResult.setMessage(validateMap.get("message").toString());
//			}
//		}catch (Exception e) {
//			logger.error("所有家长【学生】提醒查询异常", e);
//			responseResult.setMessage("所有家长【学生】提醒查询异常");
//		}
//		return responseResult;
//	}
//	
	
	
	
	
	/**
	 * 对家长备注添加
	 */
	@At("studentRemarks/add")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object addStudentRemarks(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultStudent responseResult=new ResponeResultStudent();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String remarks=postMap.get("remarks");
				String memberId=postMap.get("memberId");
				if(StringUtils.isBlank(memberId)){
					responseResult.setMessage("memberId不能为空！");
					return responseResult;
				}
				
				StudentRemarks  studentRemarks=new StudentRemarks();
				studentRemarks.setId(IdGen.uuid());
				studentRemarks.setMemberId(memberId);
				studentRemarks.setRemarks(remarks);
				studentRemarks.setCreateBy(memberId);
				studentRemarks.setCreateDate(new Date());
				consultantService.studentRemarksSave(studentRemarks);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("顾问对学生备注添加异常", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	
	
	/**
	 * 对家长备注查询
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("studentRemarks/list")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findStudentRemarks(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String memberId=postMap.get("memberId");
				if(StringUtils.isBlank(memberId)){
					responseResult.setMessage("memberId不能空");
					return responseResult;
				}
				List<StudentRemarks>list=consultantService.findStudentRemarks(memberId);
				for(StudentRemarks p:list){
					p.setDelFlag(null);
					p.setCreateBy(null);
				}
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", list);
				responseResult.setData(data);
				responseResult.setSuccess(true);
				
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("顾问对学生备注查询异常", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	
	
	
	
	/**
	 * 咨询记录添加
	 */
	@At("consult/add")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public ResponeResultModel consultAdd(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				logger.info("consult/add+===postData:"+postData);
				Consult consult=Json.fromJson(Consult.class, postData);
				logger.info("consult/add+===postData--json:"+Json.toJson(consult));
				if(StringUtils.isBlank(consult.getMemberId())){
					responseResult.setMessage("memberId不能空");
					return responseResult;
				}
				
				if(StringUtils.isBlank(consult.getDiscription())){
					responseResult.setMessage("discription不能空");
					return responseResult;
				}
				consult.setId(IdGen.uuid());
				consult.setCreateDate(new Date());
				consult.setUpdateDate(consult.getCreateDate());
				consultantService.consultAdd(consult);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("咨询记录添加异常", e);
			responseResult.setMessage("咨询记录添加异常");
		}
		return responseResult;
	}
	
	/**
	 * 咨询记录添加
	 */
	@At("consult/list")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public ResponeResultModel findConsult(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Consult consult=Json.fromJson(Consult.class, postData);
				
				if(StringUtils.isBlank(consult.getMemberId())){
					responseResult.setMessage("memberId不能空");
					return responseResult;
				}
				
				List<Consult>list=consultantService.findConsult(consult.getMemberId());
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", list);
				responseResult.setData(data);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("咨询记录查询异常", e);
			responseResult.setMessage("咨询记录查询异常");
		}
		return responseResult;
	}
	
	
	
	
	
	/**
	 * 对家长的星级设置
	 */
	@At("member/updateStar")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object memberStar(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String memberId=postMap.get("memberId");
				String starLevel=postMap.get("starLevel");
				if(StringUtils.isBlank(memberId)){
					responseResult.setMessage("memberId不能空");
					return responseResult;
				}
				if(StringUtils.isBlank(starLevel)){
					responseResult.setMessage("starLevel不能空");
					return responseResult;
				}
				
				boolean result=memberService.updateMemberStar(memberId, starLevel);
				if(result){
					responseResult.setSuccess(true);
				}else{
					responseResult.setMessage("memberId错误");
				}
				
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("对家长的星级设置异常", e);
			responseResult.setMessage("对家长的星级设置异常");
		}
		return responseResult;
	}
	
	
	
	/**
	 * 家长的星级列表查询
	 */
	@At("member/star")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findMemberStar(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String memberId=postMap.get("memberId");
				if(StringUtils.isBlank(memberId)){
					responseResult.setMessage("memberId不能空");
					return responseResult;
				}
					
				List<MemberStar>list=memberService.findMemberStar(memberId);
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", list);
				responseResult.setData(data);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("家长的星级查询异常", e);
			responseResult.setMessage("家长的星级查询异常");
		}
		return responseResult;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 参加活动类型列表
	 */
	@At("adviserActivity/list")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findAdviserActivity(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				List<AdviserActivity>list=consultantService.findAdviserActivity();
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", list);
				responseResult.setData(data);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("参加活动类型列表异常", e);
			responseResult.setMessage("参加活动类型列表异常");
		}
		return responseResult;
	}
	
	
	
	/**
	 * 活动添加
	 * @return
	 */
	@At("adviserActivityRecord/add")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object adviserActivityRecordSave(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String  postData=postParams.getPostData();
				AdviserActivityRecord adviserActivityRecord=Json.fromJson(AdviserActivityRecord.class, postData);
				if(StringUtils.isBlank(adviserActivityRecord.getMemberId())){
					responseResult.setMessage("memberId不能空");
					return responseResult;
				}
				if(StringUtils.isBlank(adviserActivityRecord.getAdviserActivityId())){
					responseResult.setMessage("adviserActivityId不能空");
					return responseResult;
				}
				adviserActivityRecord.setId(IdGen.uuid());
				adviserActivityRecord.setCreateDate(new Date());
				adviserActivityRecord.setUpdateDate(adviserActivityRecord.getCreateDate());
				consultantService.adviserActivityRecordSave(adviserActivityRecord);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("活动保存异常", e);
			responseResult.setMessage("活动保存异常");
		}
		
		return responseResult;
	}
	
	
	
	/**
	 * 活动查询
	 * @return
	 */
	@At("adviserActivityRecord/list")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findAdviserActivityRecord(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String  postData=postParams.getPostData();
				AdviserActivityRecord adviserActivityRecord=Json.fromJson(AdviserActivityRecord.class, postData);
				if(StringUtils.isBlank(adviserActivityRecord.getMemberId())){
					responseResult.setMessage("memberId不能空");
					return responseResult;
				}
				
				List<AdviserActivityRecord>list=consultantService.findAdviserActivityRecord(adviserActivityRecord.getMemberId());
				for(AdviserActivityRecord record:list){
					AdviserActivity adviserActivity= consultantService.findAdviserActivityById(record.getId());
					if(null!=adviserActivity){
						record.setAdviserActivityName(adviserActivity.getTitle());
						record.setType(adviserActivity.getType());
					}else{
						record.setAdviserActivityName("");
						record.setType("");
					}
					
				}
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", list);
				responseResult.setData(data);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("活动保存异常", e);
			responseResult.setMessage("活动保存异常");
		}
		
		return responseResult;
	}
	
}
