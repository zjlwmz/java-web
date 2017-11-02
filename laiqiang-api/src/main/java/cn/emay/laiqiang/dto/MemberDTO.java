package cn.emay.laiqiang.dto;

/**
 * 会员用户信息DTO
 * 
 * @Title
 * @author zjlwm
 * @date 2016-12-5 下午1:23:51
 * 
 */
public class MemberDTO {

	private Long id;
	
	/**
	 * 用户id
	 */
	private String uuid;

	/**
	 * 会员状态(0未关注，1已关注，2取消关注）
	 */
	private String status;

	/**
	 * 微信名称
	 */
	private String name;
	
	
	private String wxname;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 性别:1:男;2:女
	 */
	private String sex;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 留言
	 */
	private String message;
	
	/**
	 * 黑名单：1 中等：2 一般：3 白名单：-1
	 */
	private String expandfield2;

	/**
	 * 是否可以充值成功1：永远不成功
	 */
	private String expandfield5;
	
	
	/**
	 * 是否可以充值成功1：永远不成功
	 */
	private String expandfield6;
	
	/**
	 * 会员卡id
	 */
	private String membercardid;
	
	
	/**
	 * 会员卡卡号
	 */
	private String membercardsn;
	
	
	/**
	 * 来源（0批量导入，1单个添加，2微信）
	 */
	private String source;

	
	/**
	 * 是否认证：1认证通过，0待认证，4拒审
	 */
	private String checkstatus;

	
	/**
	 * 剩余流量
	 */
	private String cardbalance;

	
	/**
	 * 绑定大号的id
	 */
	private String bigmemberid;
	
	/**
	 * 邀请人id
	 */
	private String memberid;

	/**
	 * 邀请其他人的次数
	 */
	private Integer invitnum;
	
	
	/**
	 * 邀请码
	 */
	private String invitcode;
	
	
	/**
	 * 邀请二维码
	 */
	private String qcode;
	
	/**
	 * 过期时间
	 */
	private String qcodetime;
	
	
	/**
	 * 是否可以被推荐1：可以 2：不可以
	 */
	private Integer isinvit;
	
	/**
	 * 0:未认证；1：认证
	 */
	private Integer authstatus;
	
	/**
	 * 邀请关注成功的人数
	 */
	private Integer focusnum;
	
	/**
	 * 邀请奖励
	 */
	private Integer flow;

	/**
	 * 头像
	 */
	private String headimgurl;

	
	
	/**
	 * 账号余额
	 */
	private Double balance;
	
	/**
	 * 用户token
	 */
	private String token;
	
	
	/**
	 * 是否设置了支付密码
	 * 0未设置、1已设置
	 */
	private Integer isPayPass;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExpandfield2() {
		return expandfield2;
	}

	public void setExpandfield2(String expandfield2) {
		this.expandfield2 = expandfield2;
	}

	public String getExpandfield5() {
		return expandfield5;
	}

	public void setExpandfield5(String expandfield5) {
		this.expandfield5 = expandfield5;
	}

	public String getExpandfield6() {
		return expandfield6;
	}

	public void setExpandfield6(String expandfield6) {
		this.expandfield6 = expandfield6;
	}

	public String getMembercardid() {
		return membercardid;
	}

	public void setMembercardid(String membercardid) {
		this.membercardid = membercardid;
	}

	public String getMembercardsn() {
		return membercardsn;
	}

	public void setMembercardsn(String membercardsn) {
		this.membercardsn = membercardsn;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCheckstatus() {
		return checkstatus;
	}

	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}

	public String getCardbalance() {
		return cardbalance;
	}

	public void setCardbalance(String cardbalance) {
		this.cardbalance = cardbalance;
	}

	public String getBigmemberid() {
		return bigmemberid;
	}

	public void setBigmemberid(String bigmemberid) {
		this.bigmemberid = bigmemberid;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public Integer getInvitnum() {
		return invitnum;
	}

	public void setInvitnum(Integer invitnum) {
		this.invitnum = invitnum;
	}

	public String getInvitcode() {
		return invitcode;
	}

	public void setInvitcode(String invitcode) {
		this.invitcode = invitcode;
	}

	public String getQcode() {
		return qcode;
	}

	public void setQcode(String qcode) {
		this.qcode = qcode;
	}

	public String getQcodetime() {
		return qcodetime;
	}

	public void setQcodetime(String qcodetime) {
		this.qcodetime = qcodetime;
	}

	public Integer getIsinvit() {
		return isinvit;
	}

	public void setIsinvit(Integer isinvit) {
		this.isinvit = isinvit;
	}

	public Integer getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(Integer authstatus) {
		this.authstatus = authstatus;
	}

	public Integer getFocusnum() {
		return focusnum;
	}

	public void setFocusnum(Integer focusnum) {
		this.focusnum = focusnum;
	}

	public Integer getFlow() {
		return flow;
	}

	public void setFlow(Integer flow) {
		this.flow = flow;
	}


	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWxname() {
		return wxname;
	}

	public void setWxname(String wxname) {
		this.wxname = wxname;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getIsPayPass() {
		return isPayPass;
	}

	public void setIsPayPass(Integer isPayPass) {
		this.isPayPass = isPayPass;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
	
	

}
