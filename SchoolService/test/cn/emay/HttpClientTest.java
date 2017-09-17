package cn.emay;

import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.junit.Test;
import org.nutz.json.Json;

import cn.emay.common.client.LocalHttpClient;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.util.PostParamsModel;
import cn.emay.common.util.SignatureUtils;


public class HttpClientTest {

	@Test
	public void test1(){
		PostParamsModel params=new PostParamsModel();
		params.setNonce("11313");
		params.setPostData("{mobilephone:\"18658227291\"}");
		params.setSiteId("zjl");
		String signature=SignatureUtils.getSignature("zjlwm", params.getPostData(), params.getNonce());
		System.err.println(signature);
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri("api/service/payslip/list")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("utf-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
		
		
		Map<String,Object>reponseData=result.getData();
		
		String memberJson=reponseData.get("member").toString();
		System.out.println(memberJson);
	}
}
