package cn.emay.laiqiang.service;

import java.util.List;

import cn.emay.laiqiang.dto.InviteFriendsDTO;


/**
 * 
 * @Title �����־ service�ӿ�
 * @author zjlwm
 * @date 2016-12-21 ����9:35:37
 *
 */
public interface LqDrawCommissionLogService {

//	public int insert(LqDrawCommissionLog lqDrawCommissionLog);
	
	/**
	 * �����־ ��ҳ��ѯ
	 * @param memberId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<InviteFriendsDTO>findLqDrawCommissionLogList(Long memberId,Long start,Long end);
	
	
	/**
	 * �����ṩ����������
	 * @param memberId
	 * @return
	 */
	public Double getTotalFlowRate(Long memberId);
	
	
	/**
	 * �����ṩ�ܽ������
	 * @param memberId
	 * @return
	 */
	public Double getTotalAmountOfIncome(Long memberId);
	
	
	
}
