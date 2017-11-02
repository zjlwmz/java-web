package cn.emay.laiqiang.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.emay.laiqiang.bo.LqAppVersionBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.service.LqAppVersionService;
import cn.emay.laiqiang.service.LqSysInfoService;
import cn.emay.laiqiang.support.IslinkStatus;

/**
 * @Title ϵͳ��Ϣ
 * @author zjlwm
 * @date 2016-12-6 ����1:24:11
 *
 */
@Controller
@RequestMapping(value = "/laiqiang/app/sysInfo/")
public class LqSysInfoController extends BaseController{
	
	private static Logger logger = Logger.getLogger(LqSysInfoController.class.getName());
	
	/**
	 * ϵͳ��Ϣservice�ӿ�
	 */
	@Autowired
	private LqSysInfoService lqSysInfoService;
	
	/**
	 * app�汾����service�ӿ�
	 */
	@Autowired
	private LqAppVersionService lqAppVersionService;
	
	/**
	 * �ӿ����ڵ�ַ
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	
	/**
	 * ��ȡϵͳ��Ϣ��ҳģ��
	 * @param type introduce�����ܽ��ܣ���contactUs����ϵ���ǣ���cooperation���г���������agreement���û�ʹ�������creditPromise�����ó�ŵ��
	 * @return
	 */
	@RequestMapping("/html/{type}")
	public String html(@PathVariable("type") String type,HttpServletRequest request,Model modelMap){
		logger.info("��ȡϵͳ��Ϣ��ҳģ��");
		modelMap.addAttribute("_project", domain);
		if(type.equals("introduce")){
			String introduceIslink=lqSysInfoService.getLqSysInfoIntroduceIslink();
			if(StringUtils.isNotBlank(introduceIslink)){
				if(introduceIslink.equals(String.valueOf(IslinkStatus.YES))){
					return "redirect:"+lqSysInfoService.getLqSysInfoIntroduceLink();
				}else{
					modelMap.addAttribute("title", "���ܽ���");
					String introduce=lqSysInfoService.getLqSysInfoIntroduce();
					modelMap.addAttribute("htmlText", introduce);
				}
			}else{
				modelMap.addAttribute("title", "���ܽ���");
				String introduce=lqSysInfoService.getLqSysInfoIntroduce();
				modelMap.addAttribute("htmlText", introduce);
			}
		}
		
		if(type.equals("contactUs")){
			String contactusIslink=lqSysInfoService.getLqSysInfoContactusIslink();
			if(StringUtils.isNotBlank(contactusIslink)){
				if(contactusIslink.equals(String.valueOf(IslinkStatus.YES))){
					return "redirect:"+lqSysInfoService.getLqSysInfoContactusLink();
				}else{
					modelMap.addAttribute("title", "��ϵ����");
					String introduce=lqSysInfoService.getLqSysInfoContactUs();
					modelMap.addAttribute("htmlText", introduce);
				}
			}else{
				modelMap.addAttribute("title", "��ϵ����");
				String introduce=lqSysInfoService.getLqSysInfoContactUs();
				modelMap.addAttribute("htmlText", introduce);
			}
			
		}
		
		if(type.equals("cooperation")){
			String cooperationIslink=lqSysInfoService.getLqSysInfoCooperationIslink();
			if(StringUtils.isNotBlank(cooperationIslink)){
				if(cooperationIslink.equals(String.valueOf(IslinkStatus.YES))){
					return "redirect:"+lqSysInfoService.getLqSysInfoCooperationLink();
				}else{
					modelMap.addAttribute("title", "�г�����");
					String introduce=lqSysInfoService.getLqSysInfoCooperation();
					modelMap.addAttribute("htmlText", introduce);
				}
			}else{
				modelMap.addAttribute("title", "�г�����");
				String introduce=lqSysInfoService.getLqSysInfoCooperation();
				modelMap.addAttribute("htmlText", introduce);
			}
			
		}
		
		if(type.equals("agreement")){
			String agreementIslink=lqSysInfoService.getLqSysInfoAgreementIslink();
			if(StringUtils.isNotBlank(agreementIslink)){
				if(agreementIslink.equals(String.valueOf(IslinkStatus.YES))){
					return "redirect:"+lqSysInfoService.getLqSysInfoAgreementLink();
				}else{
					modelMap.addAttribute("title", "�û�ʹ������");
					String introduce=lqSysInfoService.getLqSysInfoAgreement();
					modelMap.addAttribute("htmlText", introduce);
				}
			}else{
				modelMap.addAttribute("title", "�û�ʹ������");
				String introduce=lqSysInfoService.getLqSysInfoAgreement();
				modelMap.addAttribute("htmlText", introduce);
			}
			
		}
		
		if(type.equals("creditPromise")){
			modelMap.addAttribute("title", "����ʹ�ó�ŵ");
			String introduce=lqSysInfoService.getLqSysInfoCreditPromise();
			modelMap.addAttribute("htmlText", introduce);
		}
		
		//����˵��
		if(type.equals("inviteDesc")){
			modelMap.addAttribute("title", "����˵��");
			String introduce=lqSysInfoService.getLqSysInfoInviteDesc();
			modelMap.addAttribute("htmlText", introduce);
		}
		//����ҳ�����ӵ�ַ
		if(type.equals("invitePage")){
			String introduce=lqSysInfoService.getInvitePage();
			modelMap.addAttribute("title", "����ҳ��");
			modelMap.addAttribute("htmlText", introduce);
		}
		
		return "sysInfo/index";
	}
	
	
	
	
	/**
	 * app���°汾
	 */
	@RequestMapping(value="getNewestAppVersion")
	@ResponseBody
	public String getNewestAppVersion(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.error("getLqAdvList:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					LqAppVersionBO lqAppVersionBO=lqAppVersionService.getNewestAppVersion();
					if(null!=lqAppVersionBO){
						params.put("appVersion",lqAppVersionBO);
						result.setData(params);
						result.setSuccess(true);
					}else{
						result.setMessage("û�����°汾��");
						result.setSuccess(true);
					}
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
		
	}
}
