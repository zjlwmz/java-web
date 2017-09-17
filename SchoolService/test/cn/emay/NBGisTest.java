package cn.emay;

import java.nio.charset.Charset;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.nutz.json.Json;

import cn.emay.common.client.LocalHttpClient;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.util.PostParamsModel;
import cn.emay.common.util.SignatureUtils;


/**
 * 宁波轨迹
 * @author lenovo
 *
 */
public class NBGisTest {

	public static String BASE_URI="http://api.map.baidu.com";
	
	public void tt(){
		PostParamsModel params=new PostParamsModel();
		params.setNonce("11313");
		params.setPostData("{mobilephone:\"13161454672\"}");
		params.setSiteId("zjl");
		String signature=SignatureUtils.getSignature("zjlwm", params.getPostData(), params.getNonce());
		System.err.println(signature);
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/findMember")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("utf-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
}
