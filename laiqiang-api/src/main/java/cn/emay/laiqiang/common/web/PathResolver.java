package cn.emay.laiqiang.common.web;

/**
 * ·����ȡ�ӿ�
 * 
 * @author liufang
 * 
 */
public interface PathResolver {
	
	public String getPath(String uri);

	public String getPath(String uri, String prefix);
}
