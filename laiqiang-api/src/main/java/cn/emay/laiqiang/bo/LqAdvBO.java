package cn.emay.laiqiang.bo;


/**
 * ����ֲ�ͼ
 * @author lenovo
 *
 */
public class LqAdvBO {

	private int id;
	
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
	private String redirectNeedlogin;
	
	
	/**
	 * ����
	 */
	private int displayorder;
	
	
	
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
	private int functionId;
	
	/**
	 * λ������
	 */
	private String locationType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getDisplayorder() {
		return displayorder;
	}

	public void setDisplayorder(int displayorder) {
		this.displayorder = displayorder;
	}

	public String getRedirectType() {
		return redirectType;
	}

	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
	}

	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getRedirectNeedlogin() {
		return redirectNeedlogin;
	}

	public void setRedirectNeedlogin(String redirectNeedlogin) {
		this.redirectNeedlogin = redirectNeedlogin;
	}
	
	
	
	
	
}
