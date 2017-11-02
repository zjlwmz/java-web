package cn.emay.laiqiang.dto;


/**
 * 
 * @Title 广告轮播图
 * @author zjlwm
 * @date 2016-12-9 下午8:45:29
 *
 */
public class LqAdvDTO {

	
	/**
	 * 幻灯片标题
	 */
	private String title;
	
	
	/**
	 * 简介
	 */
	private String briefIntroduction;
	
	
	
	/**
	 * 幻灯片链接
	 */
	private String link;
	
	
	
	/**
	 * 幻灯片图片
	 */
	private String imageUrl;
	
	/**
	 * 0是不需要登录、1需要登录
	 */
	private String redirectNeedlogin="0";
	
	
	/**
	 * 跳转类型(1:链接；2：功能)
	 */
	private String redirectType;
	
	/**
	 *  功能id
	 *  每日签到
	 *	赚钱中心
	 *	可领取收益
	 *	最新活动
	 **	抢红包
	 *	更多福利
	 *	充值中心
	 *	我的卡券
	 *	游戏中心
	 *	邀请
	 */
	private String functionId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRedirectNeedlogin() {
		return redirectNeedlogin;
	}

	public void setRedirectNeedlogin(String redirectNeedlogin) {
		this.redirectNeedlogin = redirectNeedlogin;
	}


	public String getRedirectType() {
		return redirectType;
	}

	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
	
	
	
}
