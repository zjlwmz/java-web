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

import com.google.common.collect.Maps;

import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.util.BaseController;
import cn.emay.common.util.PostParamsModel;
import cn.emay.model.AppAd;
import cn.emay.service.AppAdService;


/**
 * 广告接口
 * @author zjlWm
 * @version 2015-07-131
 */
@IocBean
@At(value="/service/appAd/")
public class AppAdController extends BaseController{
	static Logger logger = Logger.getLogger(AppAdController.class.getName());
	
	@Inject
	private AppAdService appAdService;
	
	/**
	 * 广告查找
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("list")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findAppAdList(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String adType=postMap.get("adType");
				String typeApp=postMap.get("typeApp");
				/**
				 * 广告类型 类型(1:登陆广告,2:首页广告、3全部(推广))
				 */
				if(StringUtils.isBlank(adType)){
					result.setMessage("adType不能为空");
					return result;
				}
				/**
				 * app类型（1：家长app；2：班主任app；3：顾问app）
				 */
				if(StringUtils.isBlank(typeApp)){
					result.setMessage("typeApp不能为空");
					return result;
				}
				List<AppAd>list=appAdService.findAppAdList(adType, typeApp);
				for(AppAd appad:list){
					appad.setDelFlag(null);
					appad.setCreateDate(null);
					appad.setEndDate(null);
					appad.setBeginDate(null);
					
				}
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", list);
				result.setData(data);
				result.setSuccess(true);
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("广告查找异常", e);
			result.setMessage("系统错误");
		}
		return result;
	}
}
