package cn.emay.laiqiang.entity;

/**
 * @Title 用户流量消费日志
 * @author zjlwm
 * @date 2016-12-20 下午3:19:55
 *
 */
public class Memberflowlog {

	private Long id;
	
	/**
	 * 会员id
	 */
	private Long  memberid;
	
	/**
	 * 流量
	 */
	private Integer fownum;
	
	
	/**
	 * 时间
	 */
	private String createtime;
	
	
	/**
	 * 类型：1抢红包，2购买流量，3企业赠送，4未抢完退还，5流量大转盘,6大转盘用户支付流量，7手机充值，8发红包，9充值失败退还,11,游戏奖励,12:拉新奖励:14:调查问卷:99:安信赠送；101:任务奖励；102:推荐奖励(现金)；103：返利收益；104：下载奖励；105：签到奖励；106：限时促销
	 */
	private String type;
	
	
	
	/**
	 * 备注:记录充值
	 */
	private String descr;
	
	/**
	 * 充值手机号
	 */
	private String phone;
	
	
	/**
	 * 购买钱数
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
