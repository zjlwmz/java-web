package cn.emay.laiqiang.service;

import java.util.List;

import cn.emay.laiqiang.entity.LqAccountSeq;



/**
 * 
 * @Title �ʽ�䶯��ˮservice�ӿ�
 * @author zjlwm
 * @date 2016-12-21 ����9:55:30
 *
 */
public interface LqAccountSeqService {

	/**
	 * �ʽ��˺���ˮ��ҳ��ѯ
	 */
	public List<LqAccountSeq>findLqAccountSeqList(Long memberId,Long start,Long end);
	
	/**
	 * ǩ������������
	 */
	public Long findTaskSigninRewardCount(Long memberId,String taskId,Long type);
	
	
	
	/**
	 * ����������
	 */
	public Long findTaskShareRewardCount(Long memberId,String taskId,Long type);
}
