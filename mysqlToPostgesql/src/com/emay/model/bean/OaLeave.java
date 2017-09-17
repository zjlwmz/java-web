package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("oa_leave")
public class OaLeave {

	/**
	 * 编号
	 */
	@Id
	@Column("id")
	private Long id;
	/**
	 * 流程实例编号
	 */
	@Column("process_instance_id")
	private String processInstanceId;
	/**
	 * 开始时间
	 */
	@Column("start_time")
	private java.util.Date startTime;
	/**
	 * 结束时间
	 */
	@Column("end_time")
	private java.util.Date endTime;
	/**
	 * 请假类型
	 */
	@Column("leave_type")
	private String leaveType;
	/**
	 * 请假理由
	 */
	@Column("reason")
	private String reason;
	/**
	 * 申请时间
	 */
	@Column("apply_time")
	private java.util.Date applyTime;
	/**
	 * 实际开始时间
	 */
	@Column("reality_start_time")
	private java.util.Date realityStartTime;
	/**
	 * 实际结束时间
	 */
	@Column("reality_end_time")
	private java.util.Date realityEndTime;
	/**
	 * 创建者
	 */
	@Column("create_by")
	private Long createBy;
	/**
	 * 创建时间
	 */
	@Column("create_date")
	private java.util.Date createDate;
	/**
	 * 更新者
	 */
	@Column("update_by")
	private Long updateBy;
	/**
	 * 更新时间
	 */
	@Column("update_date")
	private java.util.Date updateDate;
	/**
	 * 备注信息
	 */
	@Column("remarks")
	private String remarks;
	/**
	 * 删除标记（0：正常；1：删除）
	 */
	@Column("del_flag")
	private String delFlag;
}