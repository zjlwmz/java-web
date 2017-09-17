/**
 * 
 */
package cn.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 文章点击、分享信息
 * @author zjlWm
 * @date 2015-09-08
 */
@Table("s_praise_share")
public class PraiseShare {

	@Name
	@Column("id")
	private String id;
	
	/**
	 * 文章id
	 */
	@Column("article_id")
	private String articleId;
	
	/**
	 * 文章url
	 */
	@Column("url")
	private String url;
	
	/**
	 * 会员id
	 */
	@Column("member_id")
	private String memberId;
	
	/**
	 * 点击时间
	 */
	@Column("create_date")
	private Date createDate;
	
	/**
	 * 类型：1点赞；2分享
	 */
	@Column("type")
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
