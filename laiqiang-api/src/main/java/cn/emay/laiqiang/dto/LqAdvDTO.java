package cn.emay.laiqiang.dto;


/**
 * 
 * @Title ����ֲ�ͼ
 * @author zjlwm
 * @date 2016-12-9 ����8:45:29
 *
 */
public class LqAdvDTO {

	
	/**
	 * �õ�Ƭ����
	 */
	private String title;
	
	
	/**
	 * ���
	 */
	private String briefIntroduction;
	
	
	
	/**
	 * �õ�Ƭ����
	 */
	private String link;
	
	
	
	/**
	 * �õ�ƬͼƬ
	 */
	private String imageUrl;
	
	/**
	 * 0�ǲ���Ҫ��¼��1��Ҫ��¼
	 */
	private String redirectNeedlogin="0";
	
	
	/**
	 * ��ת����(1:���ӣ�2������)
	 */
	private String redirectType;
	
	/**
	 *  ����id
	 *  ÿ��ǩ��
	 *	׬Ǯ����
	 *	����ȡ����
	 *	���»
	 **	�����
	 *	���ร��
	 *	��ֵ����
	 *	�ҵĿ�ȯ
	 *	��Ϸ����
	 *	����
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
