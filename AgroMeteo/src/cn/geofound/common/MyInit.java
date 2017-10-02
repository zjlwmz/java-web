package cn.geofound.common;

import java.util.logging.Logger;

import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MyInit implements Setup{
	static Logger logger = Logger.getLogger(MyInit.class.getName());
	static String BASE_URI="http://y.ssjyw.cn/SchoolService";
	public void destroy(NutConfig config) {
		
	}
	
	public void init(NutConfig config) {
		
	}
}
