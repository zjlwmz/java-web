/**
 * 
 */
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
import cn.emay.common.util.PostParamsModel;
import cn.emay.model.PraiseShare;
import cn.emay.service.PraiseService;
import cn.emay.utils.IdGen;

import com.google.common.collect.Maps;

/**
 * 点赞接口
 * @author zjlWm
 * @date 2015-09-08
 */
@IocBean
@At(value="/service/praise/")
public class PraiseController   extends BaseController{
	static Logger logger = Logger.getLogger(PraiseController.class.getName());
	
	@Inject
	private PraiseService praiseService;
	
	/**
	 * 点赞添加\或分享
	 * @return
	 */
	@Ok("json")
	@At("praiseSave")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object praiseSave(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>mapData=Json.fromJsonAsMap(String.class, postData);
				String url=mapData.get("url");
				String memberId=mapData.get("memberId");
				String type=mapData.get("type");
				if(StringUtils.isBlank(url)){
					responseResult.setMessage("文章url参数不能为空！");
					return responseResult;
				}
				
				if(StringUtils.isBlank(memberId)){
					responseResult.setMessage("会员memberId参数不能为空！");
					return responseResult;
				}
				
				if(StringUtils.isBlank(type)){
					responseResult.setMessage("类型type参数不能为空！");
					return responseResult;
				}
				
				PraiseShare praiseShare=new PraiseShare();
				praiseShare.setId(IdGen.uuid());
				praiseShare.setUrl(url);
				praiseShare.setMemberId(memberId);
				praiseShare.setType(type);
				praiseShare.setCreateDate(new Date());
				if(praiseService.isPraiseShare(praiseShare)){
					responseResult.setMessage("已经点过赞了！");
					return responseResult;
				}
				
				praiseService.praiseAdd(praiseShare);
				int integral=praiseService.integralRule(praiseShare);
				Map<String,Object>data=Maps.newHashMap();
				data.put("integral", integral);
				responseResult.setData(data);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("点赞添加异常", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
	/**
	 * 是否已经点赞/或分享
	 * @return
	 */
	@Ok("json")
	@At("isPraise")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object isPraise(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel responseResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>mapData=Json.fromJsonAsMap(String.class, postData);
				String url=mapData.get("url");
				String memberId=mapData.get("memberId");
				String type=mapData.get("type");
				if(StringUtils.isBlank(url)){
					responseResult.setMessage("文章url参数不能为空！");
					return responseResult;
				}
				
				if(StringUtils.isBlank(memberId)){
					responseResult.setMessage("会员memberId参数不能为空！");
					return responseResult;
				}
				
				if(StringUtils.isBlank(type)){
					responseResult.setMessage("类型type参数不能为空！");
					return responseResult;
				}
				PraiseShare praise=new PraiseShare();
				praise.setUrl(url);
				praise.setMemberId(memberId);
				boolean result=praiseService.isPraiseShare(praise);
				Map<String,Object>data=Maps.newHashMap();
				data.put("isPraiseShare", result);
				responseResult.setData(data);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("是否已经点赞/或分享异常", e);
			responseResult.setMessage("系统错误");
		}
		return responseResult;
	}
	
}
