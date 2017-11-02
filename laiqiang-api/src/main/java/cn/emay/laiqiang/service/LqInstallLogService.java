package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqInstallLogBO;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.support.JedisKeyUtils;

/**
 * װ����־servic�ӿ�
 * @Title 
 * @author zjlwm
 * @date 2016-12-5 ����1:53:49
 *
 */
@Service
public class LqInstallLogService {

	/**
	 * redis sql����DAO�ӿ�
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * ��װ��־����
	 */
	public void save(LqInstallLogBO lqInstallLogBO){
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("insert into lq_install_log (install_time,imei,os_type,device_type,member_id,brand,os_version,app_version) values(");
		sqlBuff.append("'"+lqInstallLogBO.getInstallTime()+"'").append(",");
		sqlBuff.append("'"+lqInstallLogBO.getImei()+"'").append(",");
		sqlBuff.append(lqInstallLogBO.getOsType()).append(",");
		sqlBuff.append("'"+lqInstallLogBO.getDeviceType()+"'").append(",");
		sqlBuff.append(lqInstallLogBO.getMemberId()).append(",");
		sqlBuff.append("'"+lqInstallLogBO.getBrand()+"'").append(",");
		sqlBuff.append("'"+lqInstallLogBO.getOsVersion()+"'").append(",");
		sqlBuff.append("'").append(lqInstallLogBO.getAppVersion()).append("'");
		sqlBuff.append(")");
		String insertSql=sqlBuff.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertSql);
	}
	
	
	
	
	
	/**
	 * ���°�װ��¼memberID
	 */
	public void updateLqInstallLogMemberId(String imei,Long memberId){
		//���°�װ�豸��ԱID
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("update lq_install_log set member_id=").append(memberId);
		sqlBuff.append(" where imei=").append("'").append(imei).append("'");
		String insertSql=sqlBuff.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertSql);
		
		//���»�Ա�û���¼�豸��
		StringBuffer sqlMBuff=new StringBuffer();
		sqlMBuff.append("update lq_member set imei='").append(imei).append("'");
		sqlMBuff.append(" where member_id=").append(memberId);
		String insertMSql=sqlMBuff.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertMSql);
		
	}
	
}
