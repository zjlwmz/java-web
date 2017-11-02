package cn.emay.laiqiang.dto;

/**
 * @Title 邀请好友
 * @author zjlwm
 * @date 2016-12-11 下午4:29:39
 */
public class InviteFriendsDTO {

	/**
	 * 微信名称
	 */
	private String wxname;
	
	/**
	 * 头像
	 */
	private String headimgurl;
	
	
	/**
	 * 收益金额
	 */
	private Double money;
	
	/**
	 * 收益流量
	 */
	private Integer flow;
	
	/**
	 * 提交时间
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
