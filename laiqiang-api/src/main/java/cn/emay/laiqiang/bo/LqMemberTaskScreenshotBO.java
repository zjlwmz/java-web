package cn.emay.laiqiang.bo;

/**
 * 截图任务截图表
 * @Title 
 * @author zjlwm
 * @date 2017-1-3 上午10:34:49
 *
 */
public class LqMemberTaskScreenshotBO {

	private Long id;
	
	/**
	 * 会员任务ID
	 */
	private String memberTaskId;
	
	/**
	 * 屏幕截图
	 */
	private String screenshot;
	
	/**
	 * 第几张
	 */
	private Integer displayorder;
	
	/**
	 * 提交时间
	 */
	private String createdTime;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMemberTaskId() {
		return memberTaskId;
	}


	public void setMemberTaskId(String memberTaskId) {
		this.memberTaskId = memberTaskId;
	}


	public String getScreenshot() {
		return screenshot;
	}


	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}


	public String getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}


	public Integer getDisplayorder() {
		return displayorder;
	}


	public void setDisplayorder(Integer displayorder) {
		this.displayorder = displayorder;
	}
	
	
	
	
}
