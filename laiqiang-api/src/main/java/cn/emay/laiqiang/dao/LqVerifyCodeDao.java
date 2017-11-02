package cn.emay.laiqiang.dao;

import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqVerifyCode;


/**
 * 
 * @Title ��֤��DAO�ӿ�
 * @author zjlwm
 * @date 2017-1-10 ����4:28:00
 *
 */
@MyBatisDao
public interface LqVerifyCodeDao extends CrudDao<LqVerifyCode>{

	/**
	 * ������ֻ��Ÿ������Ѿ����Ͷ�������
	 * @param mobile
	 * @param type
	 * @param date
	 * @return
	 */
	public Long findSendSMSCount(Map<String,Object>params);
	
	
	/**
	 * ��ǰ���ֻ��Ÿ����ͷ��͵���֤��
	 */
	public LqVerifyCode findCurrentSMS(Map<String,Object>params);
}
