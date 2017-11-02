package cn.emay.laiqiang.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.emay.laiqiang.bo.LqMemberMessageBO;
import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.AESUtil;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.LqMemberMessagDTO;
import cn.emay.laiqiang.service.LqMemberMessageService;
import cn.emay.laiqiang.service.LqMessagePushService;
import cn.emay.laiqiang.service.MemberService;
import cn.emay.laiqiang.support.MessageType;
import cn.emay.laiqiang.token.UserTokenUtils;


/**
 * 
 * @Title 我的消息
 * @author zjlwm
 * @date 2016-12-7 上午9:12:29
 *
 */
@Controller
@RequestMapping(value = "/laiqiang/app/message/")
public class LqMemberMessageController extends BaseController{
	
	private static Logger logger = Logger.getLogger(LqMemberMessageController.class.getName());
	
	/**
	 * 我的消息service接口
	 */
	@Autowired
	private LqMemberMessageService lqMemberMessageService;
	
	
	/**
	 * 群消息service接口
	 */
	@Autowired
	private LqMessagePushService messagePushService;
	
	
	
	/**
	 * 会员service接口
	 */
	@Autowired
	private MemberService memberService;
	
	
	/**
	 * 令牌工具类
	 */
	@Autowired
	private UserTokenUtils userTokenUtils;
	
	
	
	
	/**
	 * 我的消息查询
	 * @param data
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="findMessageList")
	@ResponseBody
	public String findMessageList(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		
		try{
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
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
					 * 消息类型
					 * 0个人消息、1群发消息
					 */
					String messageType=postData.get("messageType");
					if(StringUtils.isBlank(messageType)){
						result.setSuccess(false);
						result.setMessage("messageType参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid参数错误");
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
					
					if(messageType.equals(MessageType.personalNews)){//个人消息
						List<LqMemberMessagDTO>messageList=lqMemberMessageService.findLqMemberMessageList(memberBO.getId(), start, end);
						Collections.sort(messageList);
						params.put("messageList", messageList);
						result.setSuccess(true);
						result.setData(params);
					}else if(messageType.equals(MessageType.groupMessage)){//群消息
						List<LqMemberMessagDTO>messageList=messagePushService.findLqMessagePushList(memberBO.getId(), start, end);
						Collections.sort(messageList);
						params.put("messageList", messageList);
						result.setSuccess(true);
						result.setData(params);
					}else{
						result.setMessage("消息类型错误");
						result.setSuccess(false);
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
			logger.error("数据异常", e);
			result.setSuccess(false);
			result.setMessage("数据异常");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	/**
	 * 更新消息状态
	 */
	@RequestMapping(value="updateMessageStatus")
	@ResponseBody
	public String updateMessageStatus(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		try{
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String messageId=postData.get("messageId");
					if(StringUtils.isBlank(messageId)){
						result.setSuccess(false);
						result.setMessage("messageId参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					//0
					String messageType=postData.get("messageType");
					if(StringUtils.isBlank(messageId)){
						result.setSuccess(false);
						result.setMessage("messageType参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					if(MessageType.personalNews.equals(messageType)){
						LqMemberMessageBO memberMessageBO=lqMemberMessageService.get(messageId);
						if(null!=memberMessageBO){
							lqMemberMessageService.updateStatus(memberMessageBO);
							result.setSuccess(true);
						}else{
							result.setSuccess(false);
							result.setMessage("messageId参数错误");
							return FastJsonUtils.toJSONString(result);
						}
					}else if(MessageType.groupMessage.equals(messageType)){
						LqMemberMessageBO memberMessageBO=messagePushService.get(messageId);
						if(null!=memberMessageBO){
							messagePushService.updateStatus(memberMessageBO);
							result.setSuccess(true);
						}else{
							result.setSuccess(false);
							result.setMessage("messageId参数错误");
							return FastJsonUtils.toJSONString(result);
						}
					}
				}else{
					result.setMessage("参数格式错误！");
					result.setSuccess(false);
				}
			}
		}catch (Exception e) {
			logger.error("数据异常", e);
			result.setSuccess(false);
			result.setMessage("数据异常");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	
	
	/**
	 * 未读消息条数
	 */
	@RequestMapping(value="noReadMessageCount")
	@ResponseBody
	public String noReadMessageCount(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try{
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					/**
					 * APP用户识别ID
					 */
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("unionid参数错误");
						return FastJsonUtils.toJSONString(result);
					}
					
					Long count=lqMemberMessageService.findNoReadMessageCount(memberBO.getId());
					params.put("count", count);
					
					
					result.setData(params);
					result.setSuccess(true);
				}else{
					result.setSuccess(false);
					result.setMessage(validateMap.get("message").toString());
					return FastJsonUtils.toJSONString(result);
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
	 * 消息详情查看
	 * @param id
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/detail/{type}/{id}")
	public String getLqNewsDetail(@PathVariable("type") Integer type,@PathVariable("id") String id,HttpServletRequest request,Model modelMap){
		try{
			String lqNesId=AESUtil.decrypt(id, userTokenUtils.getAsekey());
			LqMemberMessageBO lqMemberMessageBO=null;
			//个人消息
			if(type==0){
				lqMemberMessageBO=lqMemberMessageService.get(lqNesId);
			}
			//群消息
			else if(type==1){
				lqMemberMessageBO=messagePushService.get(lqNesId);
			}
			if(null!=lqMemberMessageBO){
				String content=lqMemberMessageBO.getContent();
				String title=lqMemberMessageBO.getTitle();
				modelMap.addAttribute("title", title);
				modelMap.addAttribute("htmlText", content);
			}
		}catch (Exception e) {
			
		}
		return "news/index";
	}
	
	
}
