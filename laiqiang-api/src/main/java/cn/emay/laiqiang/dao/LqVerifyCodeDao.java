package cn.emay.laiqiang.dao;

import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqVerifyCode;


/**
 * 
 * @Title 验证码DAO接口
 * @author zjlwm
 * @date 2017-1-10 下午4:28:00
 *
 */
@MyBatisDao
public interface LqVerifyCodeDao extends CrudDao<LqVerifyCode>{

	/**
	 * 当天该手机号该类型已经发送短信条数
	 * @param mobile
	 * @param type
	 * @param date
	 * @return
	 */
	public Long findSendSMSCount(Map<String,Object>params);
	
	
	/**
	 * 当前该手机号该类型发送的验证码
	 */
	public LqVerifyCode findCurrentSMS(Map<String,Object>params);
}
