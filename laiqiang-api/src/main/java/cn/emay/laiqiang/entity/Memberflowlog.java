package cn.emay.laiqiang.entity;

/**
 * @Title �û�����������־
 * @author zjlwm
 * @date 2016-12-20 ����3:19:55
 *
 */
public class Memberflowlog {

	private Long id;
	
	/**
	 * ��Աid
	 */
	private Long  memberid;
	
	/**
	 * ����
	 */
	private Integer fownum;
	
	
	/**
	 * ʱ��
	 */
	private String createtime;
	
	
	/**
	 * ���ͣ�1�������2����������3��ҵ���ͣ�4δ�����˻���5������ת��,6��ת���û�֧��������7�ֻ���ֵ��8�������9��ֵʧ���˻�,11,��Ϸ����,12:���½���:14:�����ʾ�:99:�������ͣ�101:��������102:�Ƽ�����(�ֽ�)��103���������棻104�����ؽ�����105��ǩ��������106����ʱ����
	 */
	private String type;
	
	
	
	/**
	 * ��ע:��¼��ֵ
	 */
	private String descr;
	
	/**
	 * ��ֵ�ֻ���
	 */
	private String phone;
	
	
	/**
	 * ����Ǯ��
	 */
	private Integer money;
	
	
	private Integer flowbalance;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getMemberid() {
		return memberid;
	}


	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}


	public Integer getFownum() {
		return fownum;
	}


	public void setFownum(Integer fownum) {
		this.fownum = fownum;
	}


	public String getCreatetime() {
		return createtime;
	}


	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDescr() {
		return descr;
	}


	public void setDescr(String descr) {
		this.descr = descr;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Integer getMoney() {
		return money;
	}


	public void setMoney(Integer money) {
		this.money = money;
	}


	public Integer getFlowbalance() {
		return flowbalance;
	}


	public void setFlowbalance(Integer flowbalance) {
		this.flowbalance = flowbalance;
	}
	
	
	
	
	
	
	
	
	
	
	
}
