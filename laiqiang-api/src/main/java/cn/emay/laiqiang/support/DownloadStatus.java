package cn.emay.laiqiang.support;


/**
 * 下载状态
 * @Title 
 * @author zjlwm
 * @date 2016-12-7 下午5:29:17
 *
 */
/**
 * 是否App下载完成
 * 0：未申请
 * 1:已申请未安装
 * 2：已下载安装
 */
public class DownloadStatus {

	/**
	 * 未申请
	 */
	public final static int NotApply=0;
	
	
	/**
	 * 已申请未安装
	 */
	public final static int HasNotBeenInstalled=1;
	
	
	
	/**
	 * 已下载安装
	 */
	public final static int DownloadAndInstall=2;
	
	
}
