package cn.emay.laiqiang.bo;

/**
 * @Title 抢拍订单
 * @author zjlwm
 * @date 2017-1-11 下午2:02:55
 *
 */
public class Yiorder {

	private String id;
	/**
	 * 1 待支付；-2支付失败；大于等于1 支付成功
	 */
	private Integer status;
	private String createtime;
	private String memberuuid;
	/**
	 * 商品id
	 */
	private String pid;
	private String awards;
	private String createdate;
	/**
	 * 购买数量
	 */
	private Integer buynum;
	private String maddress;
	private String phone;
	private String ipno;
	private String region;
	private String isp;
	private String city;
	private String wxorderid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getMemberuuid() {
		return memberuuid;
	}
	public void setMemberuuid(String memberuuid) {
		this.memberuuid = memberuuid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public Integer getBuynum() {
		return buynum;
	}
	public void setBuynum(Integer buynum) {
		this.buynum = buynum;
	}
	public String getMaddress() {
		return maddress;
	}
	public void setMaddress(String maddress) {
		this.maddress = maddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIpno() {
		return ipno;
	}
	public void setIpno(String ipno) {
		this.ipno = ipno;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWxorderid() {
		return wxorderid;
	}
	public void setWxorderid(String wxorderid) {
		this.wxorderid = wxorderid;
	}
	
	
	
}
