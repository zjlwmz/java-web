package cn.emay.web;

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
import cn.emay.common.util.BaseController;
import cn.emay.common.util.PostParamsModel;
import cn.emay.model.Activity;
import cn.emay.model.GradeTheme;
import cn.emay.service.ActivityService;
import cn.emay.service.GradeThemeService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * 推送记录查询
 * @author zjlwm
 *
 */
@IocBean
@At(value="/service/push/")
public class PushController  extends BaseController{
	static Logger logger = Logger.getLogger(PushController.class.getName());
	
	/**
	 * 学习主题接口
	 */
	@Inject
	private GradeThemeService gradeThemeService;
	
	/**
	 * 活动接口
	 */
	@Inject
	private ActivityService activityService;
	
	
	/**
	 * 每周主题推送
	 * @return
	 */
	@At("gradeTheme/list")
	@POST
	@AdaptBy(type=JsonAdaptor.class)
	@Ok("json")
	public ResponeResultModel gradeTheme(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String gradeCodestr=postMap.get("gradeCode");
				if(StringUtils.isNotBlank(gradeCodestr)){
					String gradeCodeArray[]=gradeCodestr.split(",");
					List<GradeTheme>listData=Lists.newArrayList();
					for(String gradeCode:gradeCodeArray){
						List<GradeTheme>list=gradeThemeService.findGradeTheme(Integer.parseInt(gradeCode));
						for(GradeTheme g:list){
							boolean result=isExits(listData, g.getId());
							if(!result)listData.add(g);
						}
					}
					Map<String,Object>data=Maps.newHashMap();
					data.put("list", listData);
					responseResult.setData(data);
					responseResult.setSuccess(true);
				}else{
					responseResult.setMessage("gradeCode不能为空！");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("每周主题推送查询异常", e);
			responseResult.setMessage("系统错误");
		}
		
		return responseResult;
	}
	
	
	public boolean isExits(List<GradeTheme> list,String id){
		for(GradeTheme g:list){
			if(g.getId().equals(id)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 活动推送
	 * @return
	 */
	@At("activity/list")
	@POST
	@AdaptBy(type=JsonAdaptor.class)
	@Ok("json")
	public ResponeResultModel activityList(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String gradeCode=postMap.get("gradeCode");
				if(StringUtils.isNotBlank(gradeCode)){
					List<Activity>list=activityService.findActivity(gradeCode);
					Map<String,Object>data=Maps.newHashMap();
					data.put("list", list);
					responseResult.setData(data);
					responseResult.setSuccess(true);
				}else{
					responseResult.setMessage("gradeCode不能为空！");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("活动推送查询异常", e);
			responseResult.setMessage("系统错误");
		}
		
		return responseResult;
	}
	
}
