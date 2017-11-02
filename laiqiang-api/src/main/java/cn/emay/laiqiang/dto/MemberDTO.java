package cn.emay.laiqiang.dto;

/**
 * ��Ա�û���ϢDTO
 * 
 * @Title
 * @author zjlwm
 * @date 2016-12-5 ����1:23:51
 * 
 */
public class MemberDTO {

	private Long id;
	
	/**
	 * �û�id
	 */
	private String uuid;

	/**
	 * ��Ա״̬(0δ��ע��1�ѹ�ע��2ȡ����ע��
	 */
	private String status;

	/**
	 * ΢������
	 */
	private String name;
	
	
	private String wxname;

	/**
	 * �ֻ���
	 */
	private String phone;

	/**
	 * �Ա�:1:��;2:Ů
	 */
	private String sex;

	/**
	 * ��ַ
	 */
	private String address;

	/**
	 * ����
	 */
	private String message;
	
	/**
	 * ��������1 �еȣ�2 һ�㣺3 ��������-1
	 */
	private String expandfield2;

	/**
	 * �Ƿ���Գ�ֵ�ɹ�1����Զ���ɹ�
	 */
	private String expandfield5;
	
	
	/**
	 * �Ƿ���Գ�ֵ�ɹ�1����Զ���ɹ�
	 */
	private String expandfield6;
	
	/**
	 * ��Ա��id
	 */
	private String membercardid;
	
	
	/**
	 * ��Ա������
	 */
	private String membercardsn;
	
	
	/**
	 * ��Դ��0�������룬1������ӣ�2΢�ţ�
	 */
	private String source;

	
	/**
	 * �Ƿ���֤��1��֤ͨ����0����֤��4����
	 */
	private String checkstatus;

	
	/**
	 * ʣ������
	 */
	private String cardbalance;

	
	/**
	 * �󶨴�ŵ�id
	 */
	private String bigmemberid;
	
	/**
	 * ������id
	 */
	private String memberid;

	/**
	 * ���������˵Ĵ���
	 */
	private Integer invitnum;
	
	
	/**
	 * ������
	 */
	private String invitcode;
	
	
	/**
	 * �����ά��
	 */
	private String qcode;
	
	/**
	 * ����ʱ��
	 */
	private String qcodetime;
	
	
	/**
	 * �Ƿ���Ա��Ƽ�1������ 2��������
	 */
	private Integer isinvit;
	
	/**
	 * 0:δ��֤��1����֤
	 */
	private Integer authstatus;
	
	/**
	 * �����ע�ɹ�������
	 */
	private Integer focusnum;
	
	/**
	 * ���뽱��
	 */
	private Integer flow;

	/**
	 * ͷ��
	 */
	private String headimgurl;

	
	
	/**
	 * �˺����
	 */
	private Double balance;
	
	/**
	 * �û�token
	 */
	private String token;
	
	
	/**
	 * �Ƿ�������֧������
	 * 0δ���á�1������
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
