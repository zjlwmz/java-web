package com.emay.model.bean;

import lombok.Data;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Data
@Table("act_id_membership")
@PK({
"userId",
"groupId" 
})
public class ActIdMembership {

	/**
	 * 
	 */
	@Column("USER_ID_")
	private String userId;
	/**
	 * 
	 */
	@Column("GROUP_ID_")
	private String groupId;
}