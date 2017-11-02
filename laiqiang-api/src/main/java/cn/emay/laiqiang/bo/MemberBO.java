package cn.emay.laiqiang.bo;

import java.io.Serializable;

public class MemberBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String wxname;
	private Integer status;
	private String headimgurl;
	private Integer cardstatus;
	private String cardcode;
	/**
	 * 账号流量余额
	 */
	private Integer cardbalance;
	private Integer isent;
	private Integer isagree;
	private Integer moneybalance;
	private String expandfield1;
	private String expandfield2;
	private String name;
	private String wxid;
	private String openid;
	private String inputtime;
	private String phone;
	private String qcode;
	private String qcodetime;
	private Integer invitnum;
	private Long memberid;
	private String uuid;
	private Integer flow;
	private Integer isinvit;
	
	/**
	 * 0:未认证；1：认证
	 */
	private Integer authstatus;
	
	/**
	 * 邀请关注成功的人数
	 * 
	 */
	private Integer focusnum;
	private String lastip;
	private String lastCity;
	private String region;
	private String isp;
	private String unionid;
	
	
	private String token;
	// private Long invitmemberid;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWxname() {
		return wxname;
	}

	public void setWxname(String wxname) {
		this.wxname = wxname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Integer getCardstatus() {
		return cardstatus;
	}

	public void setCardstatus(Integer cardstatus) {
		this.cardstatus = cardstatus;
	}

	public String getCardcode() {
		return cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}

	public Integer getCardbalance() {
		return cardbalance;
	}

	public void setCardbalance(Integer cardbalance) {
		this.cardbalance = cardbalance;
	}

	public Integer getIsent() {
		return isent;
	}

	public void setIsent(Integer isent) {
		this.isent = isent;
	}

	public Integer getIsagree() {
		return isagree;
	}

	public void setIsagree(Integer isagree) {
		this.isagree = isagree;
	}

	public Integer getMoneybalance() {
		return moneybalance;
	}

	public void setMoneybalance(Integer moneybalance) {
		this.moneybalance = moneybalance;
	}

	public String getExpandfield1() {
		return expandfield1;
	}

	public void setExpandfield1(String expandfield1) {
		this.expandfield1 = expandfield1;
	}

	public String getExpandfield2() {
		return expandfield2;
	}

	public void setExpandfield2(String expandfield2) {
		this.expandfield2 = expandfield2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getInputtime() {
		return inputtime;
	}

	public void setInputtime(String inputtime) {
		this.inputtime = inputtime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Integer getInvitnum() {
		return invitnum;
	}

	public void setInvitnum(Integer invitnum) {
		this.invitnum = invitnum;
	}

	public Long getMemberid() {
		return memberid;
	}

	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getFlow() {
		return flow;
	}

	public void setFlow(Integer flow) {
		this.flow = flow;
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

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public String getLastCity() {
		return lastCity;
	}

	public void setLastCity(String lastCity) {
		this.lastCity = lastCity;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	
	
}
