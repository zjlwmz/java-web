package cn.emay.laiqiang.service;

import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqAppVersionBO;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.support.JedisKeyUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title app�汾����
 * @author zjlwm
 * @date 2016-12-9 ����11:36:46
 *
 */
@Service
public class LqAppVersionService extends BaseService{

	/**
	 * ��ȡ���°汾
	 * @return
	 */
	public LqAppVersionBO getNewestAppVersion(){
		String key=JedisKeyUtils.getNewestAppVersion;
		
		String newsetInfo=jedisStrings.get(key);
		if(StringUtils.isNotBlank(newsetInfo)){
			return JSON.parseObject(newsetInfo, LqAppVersionBO.class);
		}
		return null;
	}
}
