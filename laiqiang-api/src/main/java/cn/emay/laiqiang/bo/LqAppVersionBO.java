package cn.emay.laiqiang.bo;


/**
 * 
 * @Title app�汾����
 * @author zjlwm
 * @date 2016-12-9 ����11:34:55
 *
 */
public class LqAppVersionBO {

	private Long id;
	
	/**
	 * �ڲ��汾��
	 */
	private Integer internalVersion;
	
	/**
	 * �����汾��
	 */
	private String publishVersion;
	
	/**
	 * ����
	 */
	private String description;
	
	/**
	 * ���ص�ַ
	 */
	private String downloadUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getInternalVersion() {
		return internalVersion;
	}

	public void setInternalVersion(Integer internalVersion) {
		this.internalVersion = internalVersion;
	}

	public String getPublishVersion() {
		return publishVersion;
	}

	public void setPublishVersion(String publishVersion) {
		this.publishVersion = publishVersion;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
