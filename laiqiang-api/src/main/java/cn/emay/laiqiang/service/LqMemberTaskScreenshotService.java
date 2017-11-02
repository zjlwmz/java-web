package cn.emay.laiqiang.service;

import java.util.List;

import cn.emay.laiqiang.bo.LqMemberTaskScreenshotBO;


/**
 * 
 * @Title 截图任务截图service接口
 * @author zjlwm
 * @date 2017-1-3 上午10:38:16
 *
 */
public interface LqMemberTaskScreenshotService {

	public LqMemberTaskScreenshotBO get(String lqMemberTaskScreenshotBOId);
	
	/**
	 * 上传截图保存
	 * @param lqInviteLog
	 * @return
	 */
	public void insert(LqMemberTaskScreenshotBO lqMemberTaskScreenshotBO);
	
	
	
	/**
	 * 删除上传截图
	 */
	public void delete(String memberTaskId,String displayorder);
	
	/**
	 * 更新覆盖截图
	 * @param memberTaskId
	 * @param displayorder
	 */
	public void update(String memberTaskId,String displayorder,String screenshot);
	
	
	public List<LqMemberTaskScreenshotBO>findList(String memberTaskId);
}
