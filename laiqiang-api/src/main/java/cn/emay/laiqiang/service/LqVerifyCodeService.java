package cn.emay.laiqiang.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.dao.LqVerifyCodeDao;
import cn.emay.laiqiang.entity.LqVerifyCode;


/**
 * @Title  ��֤��service�ӿ�
 * @author zjlwm
 * @date 2016-12-18 ����10:23:45
 *
 */
@Service
@Transactional(readOnly=true)
public class LqVerifyCodeService {

	/**
	 * ��֤��DAO�ӿ�
	 */
	@Autowired
	private LqVerifyCodeDao lqVerifyCodeDao;
	
	@Transactional(readOnly=false)
	public int insert(LqVerifyCode lqVerifyCode){
		return lqVerifyCodeDao.insert(lqVerifyCode);
	}
	
	/**
	 * �ֻ�������֤�뷢�ʹ�����ѯ
	 * @param mobile
	 * @param type
	 * @param date
	 * @return
	 */
	public Long findSendSMSCount(String phoneNumber,Integer type,String date){
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("phoneNumber", phoneNumber);
		params.put("type", type);
		params.put("date", date);
		return lqVerifyCodeDao.findSendSMSCount(params);
	}
	
	
	/**
	 * ������֤����֤
	 * @param mobile
	 * @param type
	 * @param smscode
	 * @return
	 */
	public boolean validateSMS(String phoneNumber,Integer type, String smscode) {
		String date=DateUtils.getDate("yyyy-MM-dd");
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("phoneNumber", phoneNumber);
		params.put("type", type);
		params.put("date", date);
		LqVerifyCode lqVerifyCode=lqVerifyCodeDao.findCurrentSMS(params);
		if(null!=lqVerifyCode){
			Date sendDate=DateUtils.parseDate(lqVerifyCode.getCreatedTime());
			Date currentDate=new Date();
			if(currentDate.getTime()/1000-sendDate.getTime()/1000<5*60){
				if(lqVerifyCode.getVerifyCode().equals(smscode)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	
	
}
