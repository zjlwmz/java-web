package cn.emay.laiqiang.bo;

/**
 * ��ͼ�����ͼ��
 * @Title 
 * @author zjlwm
 * @date 2017-1-3 ����10:34:49
 *
 */
public class LqMemberTaskScreenshotBO {

	private Long id;
	
	/**
	 * ��Ա����ID
	 */
	private String memberTaskId;
	
	/**
	 * ��Ļ��ͼ
	 */
	private String screenshot;
	
	/**
	 * �ڼ���
	 */
	private Integer displayorder;
	
	/**
	 * �ύʱ��
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
