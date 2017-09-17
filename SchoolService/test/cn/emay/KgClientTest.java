package cn.emay;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.nutz.json.Json;

import cn.emay.common.client.LocalHttpClient;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.utils.MD5;

public class KgClientTest {

	/**
	 * 15811576128密码6128
	 */
//	String baseURI="http://123.56.84.40/";
	private static String baseURI="http://123.57.207.159:80";   //正式使用
	private static String baseURI_kgzt="http://123.57.207.159:8081";
	
	@Test
	public  void login(){
//		HttpUriRequest httpUriRequest = RequestBuilder.get()
//				.setUri(baseURI+"/kgUser/open/login.htm?userId=18513003559&password=f11bec1411101c743f64df596773d0b2")
//				.build();
//		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
//		System.out.println(Json.toJson(result));
		
		String password=MD5.GetMD5Code("6128");
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
		nvps.add(new BasicNameValuePair("userId", "15811576128"));
		nvps.add(new BasicNameValuePair("password", password));
		HttpUriRequest httpUriRequest;
		try {
			httpUriRequest = RequestBuilder.post()
					.setUri(baseURI+"/kgUser/open/login.htm")
					.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"))
					.build();
			ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
			System.out.println(Json.toJson(result));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public  void user(){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(baseURI+"/kgUser/open/user.htm?token=a62ac2021cf849e5c1e37a98cd6701393138353133303033353539-0000014daf91fce4")
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	
	/**
	 * 
	 */
	@Test
	public  void project(){
		try{
			HttpUriRequest httpUriRequest = RequestBuilder.get()
					.setUri(baseURI+"/kgUser/open/project.htm?token=09004395696d7d2931cd81c088686c173135383131353736313238-0000015080d81a94")
					.build();
			ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
			System.out.println(Json.toJson(result));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	/**
	 * 2.6.2.	所有探孔数据时间戳
	 */
	@Test
	public  void tkAllDataTimes(){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
//				.setUri(baseURI_kgzt+"/kgzt/open/tkAllDataTimes.htm?projectId=zt_hjt&token=09004395696d7d2931cd81c088686c173135383131353736313238-0000015080d81a94")
				.setUri(baseURI_kgzt+"/kgzt/open/tkAllDataTimes.htm?projectId=zt_plt&token=09004395696d7d2931cd81c088686c173135383131353736313238-0000015080d81a94")
				.build();
		String url=httpUriRequest.getURI().toString();
		System.out.println(url);
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 2.6.3.	所有探孔数据tkAllData
	 */
	@Test
	public  void tkAllData(){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(baseURI+"/kgzt/open/tkAllData.htm?projectId=zt_plc&token=addbd2e146066048b6833e4f17c793183138353133303033353539-0000014e146afeec")
				.build();
		System.out.println(httpUriRequest.getURI().toString());
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	/**
	 * 2.6.4.	下载探孔数据
	 */
	@Test
	public  void tkDownLoadData(){
		String urlparams=baseURI+"/kgzt/open/tkDownLoadData.htm?projectId=zt_plc&token=3dfd4d569d873bce80fad2e54a2bf25a3138353133303033353539-0000014e1475a6a0&&tkIds=[\"5290-7310\",\"5300-7310\"]";
		System.out.println(urlparams);
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(urlparams)
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 2.6.5.	上传探孔数据
	 */
	@Test
	public  void tkUpLoadData(){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(baseURI+"/kgzt/open/tkUpLoadData.htm?projectId=zt_plc&tkIds=5150-7370&token=a1c51eee6fcf6efeefa2551ea89a57513138353133303033353539-0000014e14482171")
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 2.6.6.	删除探孔数据
	 */
	@Test
	public  void tkDelData(){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(baseURI+"/kgzt/open/tkDelData.htm?projectId=zt_plc&id=5150-7370&token=a1c51eee6fcf6efeefa2551ea89a57513138353133303033353539-0000014e14482171")
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 2.6.11.	堆积性质列表
	 */
	@Test
	public  void tkBulkProp(){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(baseURI_kgzt+"/kgzt/open/tkBulkProp.htm?projectId=zt_plt&token=0de1c944d0da06436b6231912641aafd3135383131353736313238-000001508b0ca637")
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	
	
	@Test
	public void logon2(){
		Map<String, String> params = new HashMap<String, String>();  
		params.put("userId", "18513003559");
		params.put("password", "f11bec1411101c743f64df596773d0b2");
    	String xml = HttpXmlClient.post(baseURI+"/kgUser/open/login.htm", params);  
    	System.out.println(xml);
	}
	
	@Test
	public void tkDownLoadData2(){
		Map<String, String> params = new HashMap<String, String>();  
		params.put("projectId", "zt_plc");
		params.put("token", "e2d34e0cf685bc8f48de2a47a1399b743138353133303033353539-0000014e481cfcef");
		List<String>tkIds=new ArrayList<String>();
		tkIds.add("5290-7310");
		tkIds.add("5300-7310");
		params.put("tkIds", tkIds.toString());
		String url=baseURI+"/kgzt/open/tkDownLoadData.htm";
    	String xml = HttpXmlClient.post(url, params);  
    	System.out.println(xml);
	}
}
