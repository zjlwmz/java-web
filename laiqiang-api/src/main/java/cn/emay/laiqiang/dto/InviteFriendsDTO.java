package cn.emay.laiqiang.dto;

/**
 * @Title �������
 * @author zjlwm
 * @date 2016-12-11 ����4:29:39
 */
public class InviteFriendsDTO {

	/**
	 * ΢������
	 */
	private String wxname;
	
	/**
	 * ͷ��
	 */
	private String headimgurl;
	
	
	/**
	 * ������
	 */
	private Double money;
	
	/**
	 * ��������
	 */
	private Integer flow;
	
	/**
	 * �ύʱ��
	 */
	private String createDate;

	public String getWxname() {
		return wxname;
	}

	public void setWxname(String wxname) {
		this.wxname = wxname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getFlow() {
		return flow;
	}

	public void setFlow(Integer flow) {
		this.flow = flow;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
	
	 
}
