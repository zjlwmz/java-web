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
 * @Title �ҵ���Ϣ
 * @author zjlwm
 * @date 2016-12-7 ����9:12:29
 *
 */
@Controller
@RequestMapping(value = "/laiqiang/app/message/")
public class LqMemberMessageController extends BaseController{
	
	private static Logger logger = Logger.getLogger(LqMemberMessageController.class.getName());
	
	/**
	 * �ҵ���Ϣservice�ӿ�
	 */
	@Autowired
	private LqMemberMessageService lqMemberMessageService;
	
	
	/**
	 * Ⱥ��Ϣservice�ӿ�
	 */
	@Autowired
	private LqMessagePushService messagePushService;
	
	
	
	/**
	 * ��Աservice�ӿ�
	 */
	@Autowired
	private MemberService memberService;
	
	
	/**
	 * ���ƹ�����
	 */
	@Autowired
	private UserTokenUtils userTokenUtils;
	
	
	
	
	/**
	 * �ҵ���Ϣ��ѯ
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
					 * APP�û�ʶ��ID
					 */
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * ��Ϣ����
					 * 0������Ϣ��1Ⱥ����Ϣ
					 */
					String messageType=postData.get("messageType");
					if(StringUtils.isBlank(messageType)){
						result.setSuccess(false);
						result.setMessage("messageType��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid��������");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					String currentPageStr=postData.get("currentPage");
					if(StringUtils.isBlank(currentPageStr)){
						result.setSuccess(false);
						result.setMessage("currentPage��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					String pageSizeStr=postData.get("pageSize");
					if(StringUtils.isBlank(pageSizeStr)){
						result.setSuccess(false);
						result.setMessage("pageSize��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					Long currentPage=Long.parseLong(currentPageStr);
					Long pageSize=Long.parseLong(pageSizeStr);
					long start=(currentPage-1)*pageSize;
					long end=currentPage*pageSize-1;
					
					if(messageType.equals(MessageType.personalNews)){//������Ϣ
						List<LqMemberMessagDTO>messageList=lqMemberMessageService.findLqMemberMessageList(memberBO.getId(), start, end);
						Collections.sort(messageList);
						params.put("messageList", messageList);
						result.setSuccess(true);
						result.setData(params);
					}else if(messageType.equals(MessageType.groupMessage)){//Ⱥ��Ϣ
						List<LqMemberMessagDTO>messageList=messagePushService.findLqMessagePushList(memberBO.getId(), start, end);
						Collections.sort(messageList);
						params.put("messageList", messageList);
						result.setSuccess(true);
						result.setData(params);
					}else{
						result.setMessage("��Ϣ���ʹ���");
						result.setSuccess(false);
					}
				}else{
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			}else{
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		}catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	/**
	 * ������Ϣ״̬
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
						result.setMessage("messageId��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					//0
					String messageType=postData.get("messageType");
					if(StringUtils.isBlank(messageId)){
						result.setSuccess(false);
						result.setMessage("messageType��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					if(MessageType.personalNews.equals(messageType)){
						LqMemberMessageBO memberMessageBO=lqMemberMessageService.get(messageId);
						if(null!=memberMessageBO){
							lqMemberMessageService.updateStatus(memberMessageBO);
							result.setSuccess(true);
						}else{
							result.setSuccess(false);
							result.setMessage("messageId��������");
							return FastJsonUtils.toJSONString(result);
						}
					}else if(MessageType.groupMessage.equals(messageType)){
						LqMemberMessageBO memberMessageBO=messagePushService.get(messageId);
						if(null!=memberMessageBO){
							messagePushService.updateStatus(memberMessageBO);
							result.setSuccess(true);
						}else{
							result.setSuccess(false);
							result.setMessage("messageId��������");
							return FastJsonUtils.toJSONString(result);
						}
					}
				}else{
					result.setMessage("������ʽ����");
					result.setSuccess(false);
				}
			}
		}catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	
	
	/**
	 * δ����Ϣ����
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
					 * APP�û�ʶ��ID
					 */
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("unionid��������");
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
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		}catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		
		return FastJsonUtils.toJSONString(result);
		
	}
	
	
	
	
	
	
	
	
	/**
	 * ��Ϣ����鿴
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
			//������Ϣ
			if(type==0){
				lqMemberMessageBO=lqMemberMessageService.get(lqNesId);
			}
			//Ⱥ��Ϣ
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
