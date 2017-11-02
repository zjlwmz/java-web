package cn.emay.laiqiang.service;

import java.util.List;

import cn.emay.laiqiang.bo.LqMemberTaskScreenshotBO;


/**
 * 
 * @Title ��ͼ�����ͼservice�ӿ�
 * @author zjlwm
 * @date 2017-1-3 ����10:38:16
 *
 */
public interface LqMemberTaskScreenshotService {

	public LqMemberTaskScreenshotBO get(String lqMemberTaskScreenshotBOId);
	
	/**
	 * �ϴ���ͼ����
	 * @param lqInviteLog
	 * @return
	 */
	public void insert(LqMemberTaskScreenshotBO lqMemberTaskScreenshotBO);
	
	
	
	/**
	 * ɾ���ϴ���ͼ
	 */
	public void delete(String memberTaskId,String displayorder);
	
	/**
	 * ���¸��ǽ�ͼ
	 * @param memberTaskId
	 * @param displayorder
	 */
	public void update(String memberTaskId,String displayorder,String screenshot);
	
	
	public List<LqMemberTaskScreenshotBO>findList(String memberTaskId);
}
