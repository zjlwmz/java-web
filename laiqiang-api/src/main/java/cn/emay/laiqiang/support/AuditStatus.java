package cn.emay.laiqiang.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审核状态标识类
 * 
 * @author bzj
 * @date 2016-05-26
 */



public class AuditStatus {
	/**
	 * 待提交 1
	 */
	public static final int WaitSubmit = 1;
	/**
	 * 待审核 2
	 */
	public static final int WaitAudit = 2;
	/**
	 * 审核通过 3
	 */
	public static final int AuditPass = 3;
	/**
	 * 审核驳回 4
	 */
	public static final int AuditReject = 4;
	
	public static List<NameValueBean> GetStatusList()
	{
		List<NameValueBean> statusList = new ArrayList<NameValueBean>();
		statusList.add(new NameValueBean(AuditStatus.WaitSubmit,"待提交"));
		statusList.add(new NameValueBean(AuditStatus.WaitAudit, "待审核"));
		statusList.add(new NameValueBean(AuditStatus.AuditPass, "审核通过"));
		statusList.add(new NameValueBean(AuditStatus.AuditReject, "审核驳回"));
		
		return statusList;
	}	
	
	public static Map<Integer,String> GetStatusMap()
	{
		Map<Integer,String> statusMap = new HashMap<Integer,String>();
		statusMap.put(AuditStatus.WaitSubmit,"待提交");
		statusMap.put(AuditStatus.WaitAudit, "待审核");
		statusMap.put(AuditStatus.AuditPass, "审核通过");
		statusMap.put(AuditStatus.AuditReject, "审核驳回");
		
		return statusMap;
	}	
}

