package cn.emay.laiqiang.bo;

import cn.emay.laiqiang.common.utils.DateUtils;


/**
 *
 * @Title 个人卡卷
 * @talbe cardactivity
 * @author zjlwm 
 * @date 2016-12-14 下午5:14:58
 */
public class CardBo  implements Comparable<CardBo>{

	private Long id;
	
	/**
	 * 卡卷名称
	 */
	private String cardname;
	
	/**
	 * 1:通用卷；2：来抢卡劵；3：第三方的卡劵
	 */
	private Integer type;
	
	/**
	 * 开始时间
	 */
	private String starttime;
	
	/**
	 * 0：M码；1：代金券
	 */
	private Integer lqtype;
	
	
	/**
	 * 1：流量代金券；2：话费代金券；3：给账户余额充值代金券
	 */
	private Integer lqtype1;
	
	
	/**
	 * 1：定值；2：随机
	 */
	private Integer lqtype2;
	
	/**
	 * 面值随机区间下界
	 */
	private Double showstart;
	
	/**
	 * 面值随机区间上界
	 */
	private Double showend;
	
	/**
	 * 失效时间
	 */
	private String losetime;
	
	/**
	 * 链接（第三方的卡劵应用链接）
	 */
	private String url;
	
	/**
	 * 卡劵的价格
	 */
	private Double price;
	
	/**
	 * 代码
	 */
	private String code;
	

	
	/**
	 * 卡劵活动状态：0：编辑中；1：上线；2：下线
	 */
	private Long status;
	
	
	/**
	 * 0未开始、1可以使用、 2已失效、3已使用
	 *
	 */
	private Integer islose;

	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardname() {
		return cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLosetime() {
		return losetime;
	}

	public void setLosetime(String losetime) {
		this.losetime = losetime;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getLqtype1() {
		return lqtype1;
	}

	public void setLqtype1(Integer lqtype1) {
		this.lqtype1 = lqtype1;
	}

	public Integer getLqtype2() {
		return lqtype2;
	}

	public void setLqtype2(Integer lqtype2) {
		this.lqtype2 = lqtype2;
	}

	public Double getShowstart() {
		return showstart;
	}

	public void setShowstart(Double showstart) {
		this.showstart = showstart;
	}

	public Double getShowend() {
		return showend;
	}

	public void setShowend(Double showend) {
		this.showend = showend;
	}

	public Integer getLqtype() {
		return lqtype;
	}


	public void setLqtype(Integer lqtype) {
		this.lqtype = lqtype;
	}

	public Integer getIslose() {
		return islose;
	}

	public void setIslose(Integer islose) {
		this.islose = islose;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	
	
	
	
	
	
	
	
	
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	@Override
	public int compareTo(CardBo cardBo) {
		Long starttime1=DateUtils.parseDate(cardBo.getLosetime()).getTime();
		Long starttime2=DateUtils.parseDate(this.losetime).getTime();
		return starttime1.compareTo(starttime2);
	}
	
	
	
	
	

}
