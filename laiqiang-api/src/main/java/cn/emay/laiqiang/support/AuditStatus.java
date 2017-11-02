package cn.emay.laiqiang.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���״̬��ʶ��
 * 
 * @author bzj
 * @date 2016-05-26
 */



public class AuditStatus {
	/**
	 * ���ύ 1
	 */
	public static final int WaitSubmit = 1;
	/**
	 * ����� 2
	 */
	public static final int WaitAudit = 2;
	/**
	 * ���ͨ�� 3
	 */
	public static final int AuditPass = 3;
	/**
	 * ��˲��� 4
	 */
	public static final int AuditReject = 4;
	
	public static List<NameValueBean> GetStatusList()
	{
		List<NameValueBean> statusList = new ArrayList<NameValueBean>();
		statusList.add(new NameValueBean(AuditStatus.WaitSubmit,"���ύ"));
		statusList.add(new NameValueBean(AuditStatus.WaitAudit, "�����"));
		statusList.add(new NameValueBean(AuditStatus.AuditPass, "���ͨ��"));
		statusList.add(new NameValueBean(AuditStatus.AuditReject, "��˲���"));
		
		return statusList;
	}	
	
	public static Map<Integer,String> GetStatusMap()
	{
		Map<Integer,String> statusMap = new HashMap<Integer,String>();
		statusMap.put(AuditStatus.WaitSubmit,"���ύ");
		statusMap.put(AuditStatus.WaitAudit, "�����");
		statusMap.put(AuditStatus.AuditPass, "���ͨ��");
		statusMap.put(AuditStatus.AuditReject, "��˲���");
		
		return statusMap;
	}	
}

