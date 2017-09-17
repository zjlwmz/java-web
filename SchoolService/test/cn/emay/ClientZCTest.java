package cn.emay;

import java.nio.charset.Charset;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.junit.Test;
import org.nutz.json.Json;

import cn.emay.common.client.LocalHttpClient;
import cn.emay.common.response.ResponeResultModel;

/**
 * 
 * @author zjlWm
 * @date 2016-3-3
 */
public class ClientZCTest {

	@Test
	public void test(){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri("http://baidu.kuaidi100.com/query?type=zhongtong&postid=701185389979&id=4")
				.build();
		
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
}
