package cn.emay.laiqiang.service.impl;

import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqTaskStrategyBO;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.service.BaseService;
import cn.emay.laiqiang.service.LqTaskStrategyService;
import cn.emay.laiqiang.support.JedisKeyUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title ������service�ӿ�ʵ��
 * @author zjlwm
 * @date 2016-12-21 ����2:43:24
 *
 */
@Service
public class LqTaskStrategyServiceImpl extends BaseService implements LqTaskStrategyService{

	@Override
	public LqTaskStrategyBO get(String taskId) {
		/**
		 * ׬Ǯ����������Ϣ�������ԡ��������顢˵�����������飩
		 */
		String lqTaskStrateKey=JedisKeyUtils.getLqTaskStrategy+taskId;
		
		String lqTaskStrateInfo=jedisStrings.get(lqTaskStrateKey);
		if(StringUtils.isNotBlank(lqTaskStrateInfo)){
			LqTaskStrategyBO lqTaskStrategyBO=JSON.parseObject(lqTaskStrateInfo, LqTaskStrategyBO.class);
			return lqTaskStrategyBO;
		}
		
		return null;
	}

}
