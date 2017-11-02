package cn.emay.laiqiang.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqParamBO;
import cn.emay.laiqiang.common.utils.JedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.support.ParamName;

import com.alibaba.fastjson.JSON;


/**
 * 
 * @Title 
 * @author zjlwm
 * @date 2016-12-19 上午10:38:53
 *
 */
@Service
public class LqParamService extends BaseService{

	/**
	 * 微信来抢redis 读取DAO
	 */
	@Autowired
	private JedisUtil jedisUtil;
	
	/**
	 * 获取参数值
	 * @param paramName
	 * @return
	 */
	public LqParamBO getParamValue(String paramName){
		String paramKey=JedisKeyUtils.getParamsValue+paramName;
		
		String paramInfo=jedisStrings.get(paramKey);
		if(StringUtils.isNotBlank(paramInfo)){
			return JSON.parseObject(paramInfo, LqParamBO.class);
		}
		return null;
	}
	
	
	/**
	 * 判断话费通道是否正常
	 */
	public Map<String,String> buyphoneRechargetatus(){
		Map<String,String>messageMap=new HashMap<String, String>();
		String buyflowstatus = jedisUtil.get("buyflowstatus");
		if(buyflowstatus != null && buyflowstatus.equals("-1") ){
			messageMap.put("mesage", "话费充值维护中......");
			messageMap.put("status", "ERROR");
		}else{
			messageMap.put("status", "OK");
		}
		return messageMap;
	}
	
	
	
	/**
	 * 判断流量通道是否正常
	 */
	public Map<String,String> buyflowRechargestatus(){
		Map<String,String>messageMap=new HashMap<String, String>();
		String paramKey=JedisKeyUtils.getParamsValue+ParamName.enableFlowRecharge;
		String buyflowstatus = jedisStrings.get(paramKey);
		if(buyflowstatus != null && buyflowstatus.equals("0") ){//维护
			messageMap.put("mesage", "流量充值维护中......");
			messageMap.put("status", "ERROR");
		}else{
			messageMap.put("status", "OK");
		}
		return messageMap;
	}
	
	
	
}
