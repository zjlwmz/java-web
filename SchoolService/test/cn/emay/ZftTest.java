/**
 * 
 */
package cn.emay;

import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.junit.Test;
import org.nutz.http.Http;
import org.nutz.json.Json;

import cn.emay.common.client.LocalHttpClient;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.util.PostParamsModel;

import com.google.common.collect.Maps;

/**
 * @author zjlWm
 * @date
 *
 */
public class ZftTest {
//	static String BASE_URI="http://100.100.11.202:8080/zft/api/service";
	static String url="http://microinfo2014.oicp.net:8004/api/WorkDiaryAPI/WorkSiteDiaryCreateAPI";
	
	
	@Test
	public void login(){
//		PostParamsModel params=new PostParamsModel();
//		params.setPostData("{AuditStatus:\"13161454672\",BaseInfo:\"123456\"}");
//		HttpUriRequest httpUriRequest = RequestBuilder.post()
//				.setUri(url)
//				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("utf-8")))
//				.build();
//		
//		
//		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
//		System.out.println(Json.toJson(result));
		
		
		
		Map<String,Object>dataParams=Maps.newHashMap();
		dataParams.put("AuditStatus", "13161454672");
		dataParams.put("BaseInfo", "123456");
		String dda=Http.post(url, dataParams, 1000*100);
		System.out.println(dda);
	}
}
