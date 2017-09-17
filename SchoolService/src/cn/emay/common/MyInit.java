package cn.emay.common;

import java.nio.charset.Charset;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.nutz.lang.Tasks;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import cn.emay.common.client.LocalHttpClient;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.util.PostParamsModel;
import cn.emay.common.util.SignatureUtils;

public class MyInit implements Setup{
	static Logger logger = Logger.getLogger(MyInit.class.getName());
	static String BASE_URI="http://y.ssjyw.cn/SchoolService";
	public void destroy(NutConfig config) {
		
	}
	
	public void init(NutConfig config) {
//		Tasks.scheduleAtFixedRate(new Runnable() {
//		    public void run() {
//		    	String accessToken="zjlwm";
//				PostParamsModel params=new PostParamsModel();
//				params.setNonce((int)((Math.random()*9+1)*100000)+"");
//				params.setPostData("{\"time\":\"1412870400000\"}");
//				String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
//				params.setSignature(signature);
//				System.out.println(Json.toJson(params));
//				HttpUriRequest httpUriRequest = RequestBuilder.post()
//						.setUri(BASE_URI+"/service/member/accessSettings")
//						.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
//						.build();
//				ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
//				logger.info("accessSettings执行完成"+Json.toJson(result));
//		    }
//		}, 30);
	}
}
