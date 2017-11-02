package cn.emay.laiqiang.bo;

import cn.emay.laiqiang.common.utils.DateUtils;


/**
 *
 * @Title ���˿���
 * @talbe cardactivity
 * @author zjlwm 
 * @date 2016-12-14 ����5:14:58
 */
public class CardBo  implements Comparable<CardBo>{

	private Long id;
	
	/**
	 * ��������
	 */
	private String cardname;
	
	/**
	 * 1:ͨ�þ�2������������3���������Ŀ���
	 */
	private Integer type;
	
	/**
	 * ��ʼʱ��
	 */
	private String starttime;
	
	/**
	 * 0��M�룻1������ȯ
	 */
	private Integer lqtype;
	
	
	/**
	 * 1����������ȯ��2�����Ѵ���ȯ��3�����˻�����ֵ����ȯ
	 */
	private Integer lqtype1;
	
	
	/**
	 * 1����ֵ��2�����
	 */
	private Integer lqtype2;
	
	/**
	 * ��ֵ��������½�
	 */
	private Double showstart;
	
	/**
	 * ��ֵ��������Ͻ�
	 */
	private Double showend;
	
	/**
	 * ʧЧʱ��
	 */
	private String losetime;
	
	/**
	 * ���ӣ��������Ŀ���Ӧ�����ӣ�
	 */
	private String url;
	
	/**
	 * �����ļ۸�
	 */
	private Double price;
	
	/**
	 * ����
	 */
	private String code;
	

	
	/**
	 * �����״̬��0���༭�У�1�����ߣ�2������
	 */
	private Long status;
	
	
	/**
	 * 0δ��ʼ��1����ʹ�á� 2��ʧЧ��3��ʹ��
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
