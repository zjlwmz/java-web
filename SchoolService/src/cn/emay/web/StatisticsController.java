package cn.emay.web;

import java.util.Date;
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
import cn.emay.common.util.IdGen;
import cn.emay.common.util.PostParamsModel;
import cn.emay.model.AppStatistics;
import cn.emay.service.StatisticsService;

/**
 * 移动应用下载统计
 * @author zjlWm
 * @version 2015-05-18
 */
@IocBean
@At(value="/service/statistics/")
public class StatisticsController extends BaseController {
	static Logger logger = Logger.getLogger(StatisticsController.class.getName());
	
	@Inject
	private StatisticsService statisticsService;
	/**
	 * 下载量统计
	 * @return
	 */
	@Ok("json")
	@At("downloads")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object downloads(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String type=postMap.get("type");
				String deviceId=postMap.get("deviceId");
				if(StringUtils.isNotBlank(type)){
					AppStatistics appStatistics=new AppStatistics();
					appStatistics.setAppType(type);
					appStatistics.setCreate_date(new Date());
					appStatistics.setType("downloads");
					appStatistics.setId(IdGen.uuid());
					appStatistics.setDeviceId(deviceId);
					statisticsService.saveStatistics(appStatistics);
					responseResult.setSuccess(true);
				}else{
					responseResult.setMessage("type不能为空！");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("下载量统计", e);
			responseResult.setMessage("系统错误！");
		}
		return responseResult;
	}
}
