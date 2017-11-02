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
 * @Title 系统信息
 * @author zjlwm
 * @date 2016-12-6 下午1:24:11
 *
 */
@Controller
@RequestMapping(value = "/laiqiang/app/sysInfo/")
public class LqSysInfoController extends BaseController{
	
	private static Logger logger = Logger.getLogger(LqSysInfoController.class.getName());
	
	/**
	 * 系统信息service接口
	 */
	@Autowired
	private LqSysInfoService lqSysInfoService;
	
	/**
	 * app版本管理service接口
	 */
	@Autowired
	private LqAppVersionService lqAppVersionService;
	
	/**
	 * 接口所在地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	
	/**
	 * 获取系统信息网页模板
	 * @param type introduce（功能介绍）、contactUs（联系我们）、cooperation（市场合作）、agreement（用户使用条款）、creditPromise（信用承诺）
	 * @return
	 */
	@RequestMapping("/html/{type}")
	public String html(@PathVariable("type") String type,HttpServletRequest request,Model modelMap){
		logger.info("获取系统信息网页模板");
		modelMap.addAttribute("_project", domain);
		if(type.equals("introduce")){
			String introduceIslink=lqSysInfoService.getLqSysInfoIntroduceIslink();
			if(StringUtils.isNotBlank(introduceIslink)){
				if(introduceIslink.equals(String.valueOf(IslinkStatus.YES))){
					return "redirect:"+lqSysInfoService.getLqSysInfoIntroduceLink();
				}else{
					modelMap.addAttribute("title", "功能介绍");
					String introduce=lqSysInfoService.getLqSysInfoIntroduce();
					modelMap.addAttribute("htmlText", introduce);
				}
			}else{
				modelMap.addAttribute("title", "功能介绍");
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
					modelMap.addAttribute("title", "联系我们");
					String introduce=lqSysInfoService.getLqSysInfoContactUs();
					modelMap.addAttribute("htmlText", introduce);
				}
			}else{
				modelMap.addAttribute("title", "联系我们");
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
					modelMap.addAttribute("title", "市场合作");
					String introduce=lqSysInfoService.getLqSysInfoCooperation();
					modelMap.addAttribute("htmlText", introduce);
				}
			}else{
				modelMap.addAttribute("title", "市场合作");
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
					modelMap.addAttribute("title", "用户使用条款");
					String introduce=lqSysInfoService.getLqSysInfoAgreement();
					modelMap.addAttribute("htmlText", introduce);
				}
			}else{
				modelMap.addAttribute("title", "用户使用条款");
				String introduce=lqSysInfoService.getLqSysInfoAgreement();
				modelMap.addAttribute("htmlText", introduce);
			}
			
		}
		
		if(type.equals("creditPromise")){
			modelMap.addAttribute("title", "诚信使用承诺");
			String introduce=lqSysInfoService.getLqSysInfoCreditPromise();
			modelMap.addAttribute("htmlText", introduce);
		}
		
		//邀请说明
		if(type.equals("inviteDesc")){
			modelMap.addAttribute("title", "邀请说明");
			String introduce=lqSysInfoService.getLqSysInfoInviteDesc();
			modelMap.addAttribute("htmlText", introduce);
		}
		//邀请页面链接地址
		if(type.equals("invitePage")){
			String introduce=lqSysInfoService.getInvitePage();
			modelMap.addAttribute("title", "分享页面");
			modelMap.addAttribute("htmlText", introduce);
		}
		
		return "sysInfo/index";
	}
	
	
	
	
	/**
	 * app最新版本
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
						result.setMessage("没有最新版本！");
						result.setSuccess(true);
					}
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("参数格式错误！");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("数据异常", e);
			result.setSuccess(false);
			result.setMessage("数据异常");
		}
		return FastJsonUtils.toJSONString(result);
		
	}
}
