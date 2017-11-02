package cn.emay.laiqiang.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.emay.laiqiang.service.LqOrderNoService;


/**
 * 
 * @Title 
 * @author zjlwm
 * @date 2016-12-14 ÏÂÎç4:31:29
 *
 */
@Controller
@RequestMapping(value = "/laiqiang/app/orderNo/")
public class LqOrderNoController {

	@Autowired
	private LqOrderNoService lqOrderNoService;
	
	@RequestMapping("getOrderNo")
	@ResponseBody
	public String getOrderNo(){
		
		String orderNo=lqOrderNoService.getOrderNo();
		
		return orderNo;
	}
}
