package cn.emay.laiqiang.service;

import java.util.List;


/**
 * 
 * @Title ���־service�ӿ�
 * @author zjlwm
 * @date 2017-2-15 ����4:44:30
 *
 */
public interface LqActivityService {
	
	/**
	 * �����
	 */
	public void activityReward(long memberId,String imeiNew);
	
	
	public List<Long>rewardExport();
	
}
