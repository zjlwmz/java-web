package cn.emay.laiqiang.entity;


import cn.emay.laiqiang.common.utils.DateUtils;


/**
 * @Title ��ֵ������¼
 * @author zjlwm
 * @date 2016-12-15 ����4:27:28
 *
 */
public class Rechargelog implements Comparable<Rechargelog>{
	
	/**
	 * �ֻ���
	 */
	private String phone;
	
	
	/**
	 * ��Ӫ��
	 */
	private String operator;
	
	
	/**
	 * ״̬ -2:�����رգ�1(4)���ύ 2�ɹ� 3ʧ��,0:���ύ
	 */
	private Integer status;
	
	/**
	 * �ײ�
	 */
	private Integer packageType;
	
	
	
	
	/**
	 * ���
	 */
	private String money="0.0";
	
	
	/**
	 * ����ʱ��
	 */
	private String createtime;
	
	/**
	 * ʧ������
	 */
	private Integer failflow;
	
	/**
	 * 0:����֧�� 1��H5΢��֧�� 2��M��һ���3��App΢��֧����4���ʽ��˻����֧����5��֧����App֧��
	 */
	private String type;
	
	
	/**
	 * �˿�״̬
	 * �����˿�״̬1�������У�2�ܾ��˿3ͬ���˿4�˿�ɹ���5�˿�ʧ��
	 */
	private String rstatus;


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}



	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPackageType() {
		return packageType;
	}

	public void setPackageType(Integer packageType) {
		this.packageType = packageType;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getRstatus() {
		return rstatus;
	}

	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}

	public Integer getFailflow() {
		return failflow;
	}

	public void setFailflow(Integer failflow) {
		this.failflow = failflow;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int compareTo(Rechargelog o) {
		Rechargelog rechargelog=(Rechargelog)o;
		Long createtime1=DateUtils.parseDate(rechargelog.getCreatetime()).getTime();
		Long createtime2=DateUtils.parseDate(this.getCreatetime()).getTime();
		return createtime1.compareTo(createtime2);
	}

	
	
	
	
	
	
	
	
}
