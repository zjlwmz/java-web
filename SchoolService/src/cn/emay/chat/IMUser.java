/**
 * 
 */
package cn.emay.chat;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 环信用户
 * @author zjlWm
 * @date 2015-07-131
 */
@Table("s_user_hx")
public class IMUser {

	@Id
	private Integer id;
	
	/**
	 * 用户名称
	 */
	@Column(value="username")
	private String username;
	
	@Column(value="nickname")
	private String nickname;
	
	@Column(value="user_id")
	private String userId;
	
	/**
	 * 用户类型1:家长、2:顾问
	 */
	@Column(value="user_type")
	private String userType;
	
	/**
	 * 头像
	 */
	@Column(value="head_url")
	private String headUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	
	
	
	
}
