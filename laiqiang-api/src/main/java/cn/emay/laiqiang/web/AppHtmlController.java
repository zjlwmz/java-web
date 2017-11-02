package cn.emay.laiqiang.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.emay.laiqiang.bo.LqAppVersionBO;
import cn.emay.laiqiang.service.LqAppVersionService;
import cn.emay.laiqiang.service.LqSysInfoService;


/**
 * 
 * @Title 
 * @author zjlwm
 * @date 2016-12-29 下午3:35:29
 *
 */
@Controller
@RequestMapping(value = "/app/html/")
public class AppHtmlController  extends BaseController {

	@Autowired
	public LqSysInfoService lqSysInfoService;
	
	
	/**
	 * 接口所在地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	/**
	 * app版本管理service接口
	 */
	@Autowired
	private LqAppVersionService lqAppVersionService;
	
	/**
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("newest")
	public String newest(HttpServletRequest request,Model modelMap){
		try{
			modelMap.addAttribute("_project", domain);
			LqAppVersionBO lqAppVersionBO=lqAppVersionService.getNewestAppVersion();
			if(null!=lqAppVersionBO){
				modelMap.addAttribute("downloadUrl",lqAppVersionBO.getDownloadUrl());
			}else{
				modelMap.addAttribute("downloadUrl","");
			}
		}catch (Exception e) {
			
		}
		return "app/newest";
	}
}
