package cn.emay.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;

import cn.emay.common.messaging.IMBase;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.util.BaseController;
import cn.emay.common.util.PostParamsModel;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;

/**
 * 即时通讯接口
 * 会员接口
 * @author zjlWm
 * @version 2015-04-13
 */
@IocBean
@At(value="/service/insMessaging/")
public class InsMessagingController extends BaseController{
	static Logger logger = Logger.getLogger(InsMessagingController.class.getName());
	
	
	
	
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
				String mobilephone=postMap.get("mobilephone");
				String nickname=postMap.get("nickname");
				if(StringUtils.isNotBlank(mobilephone)){
					ObjectNode object=IMBase.createNewIMUserSingle(mobilephone, nickname);
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
					responseResult.setMessage("mobilephone参数不能为空！");
				}
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("会员注册到环信平台异常", e);
			responseResult.setMessage("会员注册异常");
		}
		
		return responseResult;
	}
	
	
	
	
	/**
	 * 
	 * IM用户添加好友
	 */
	@At("addfriend")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object addfriend(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
				String ownerUserName=postMap.get("ownerUserName");
				String friendUserName=postMap.get("friendUserName");
				if(StringUtils.isNotBlank(ownerUserName) && StringUtils.isNotBlank(friendUserName)){
					ObjectNode object=IMBase.addFriendSingle(ownerUserName, friendUserName);
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
					responseResult.setMessage("参数ownerUserName、friendUserName不能为空！");
				}
				
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("会员注册到环信平台异常", e);
			responseResult.setMessage("会员注册异常");
		}
		
		return responseResult;
	}
	
	
	/**
	 * 
	 * IM用户批量添加好友
	 */
	@At("addfriendList")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object addfriendList(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel  responseResult=new ResponeResultModel();
		Map<String,Object>validateMap=validateParams(postParams);
		try{
			if(validateMap.get("status").equals("OK")){
				Map<String,String> postMap=Json.fromJsonAsMap(String.class, postParams.getPostData());
				String ownerUserName=postMap.get("ownerUserName");
				String friendUserNames=postMap.get("friendUserName");
				if(StringUtils.isNotBlank(ownerUserName) && StringUtils.isNotBlank(friendUserNames)){
					String friendUserNameArray[]=friendUserNames.split(",");
					Map<String,Object>data=Maps.newHashMap();
					for(String friendUserName:friendUserNameArray){
						ObjectNode object=IMBase.addFriendSingle(ownerUserName, friendUserName);
						String statusCode=object.get("statusCode").toString();
						if(!statusCode.equals("200")){
							data.put("error", object.get("error").toString());
							data.put("error", object.get("error_description").toString());
							data.put("error", object.get("exception").toString());
						}
						data.put("statusCode",statusCode);
					}
					responseResult.setData(data);
					responseResult.setSuccess(true);
				}else{
					responseResult.setMessage("参数ownerUserName、friendUserName不能为空！");
				}
				
			}else{
				responseResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.debug("会员注册到环信平台异常", e);
			responseResult.setMessage("会员注册异常");
		}
		
		return responseResult;
	}
	
}
