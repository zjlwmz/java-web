package cn.emay.laiqiang.bo;


/**
 * 
 * @Title app版本管理
 * @author zjlwm
 * @date 2016-12-9 上午11:34:55
 *
 */
public class LqAppVersionBO {

	private Long id;
	
	/**
	 * 内部版本号
	 */
	private Integer internalVersion;
	
	/**
	 * 发布版本号
	 */
	private String publishVersion;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 下载地址
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
