package cn.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.Test;

import cn.emay.laiqiang.bo.LqInterfaceLogBO;
import cn.emay.laiqiang.client.LocalHttpClient;
import cn.emay.laiqiang.common.response.wxlq.CallsPackage;
import cn.emay.laiqiang.common.utils.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * @Title ΢�������ӿڲ���
 * @author zjlwm
 * @date 2016-12-12 ����11:51:49
 * 
 */
public class LQServiceTest {

	
	public static String BASE_URI = "http://www1.m.cn/appinterface/server/";

	
	/**
	 * 1. �����˻������ѯ�˻���Ϣ�ӿ�
	 */
	public void findmember() {
		try{
			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(BASE_URI + "findmember").
					addHeader("unionid", "6a4d292c-0a2a-45bc-a514-dcc24a03bedb").
					build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			System.out.println(result);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	
	/**
	 * 2. �ɳ�ֵ��������ѯ�ӿ�
	 */
	@Test
	public void checkphoneflow() {
		HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(BASE_URI + "checkphoneflow?phone=17718301027").build();
		String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
		System.out.println(result);
	}

	
	/**
	 * 3. ������ֵ�ӿ�
	 */
	public void flowrecharge() {
		HttpUriRequest httpUriRequest = RequestBuilder.get().
				setUri(BASE_URI + "flowrecharge")
				.addHeader("unionid", "123123123")
				.addHeader("phone", "18600666515")
				.addHeader("flowpackage", "500")
				.addHeader("buytype", "0")
				.build();
		String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
		System.out.println(result);
	}
	
	
	/**
	 * 4. M��һ��ӿ�
	 */
	
	public void exchangemcode() {
		HttpUriRequest httpUriRequest = RequestBuilder.get().
				setUri(BASE_URI + "exchangemcode?unionid=6a4d292c-0a2a-45bc-a514-dcc24a03bedb&phone=17718301027&mcode=459d096c3dbf4982b933")
				.build();
		String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
		System.out.println(result);
	}
	
	
	
	
	
	/**
	 * 
	 *	�û������䶯�ӿ�
	 *
	 */
	public void updatememberbalance() {
		Map<String,String> resultMap=new HashMap<String, String>();
		StringBuffer updatememberbalanceUrl=new StringBuffer();
		updatememberbalanceUrl.append(BASE_URI);
		updatememberbalanceUrl.append("updatememberbalance?");
		updatememberbalanceUrl.append("unionid=").append("96df69ff-3664-40cb-8de4-6ca95dc5b6bf").append("&");
		updatememberbalanceUrl.append("flow=").append("10").append("&");
		updatememberbalanceUrl.append("descr=").append("����").append("&");
		updatememberbalanceUrl.append("type=").append("101").append("&");//���ͣ�1�������2����������3��ҵ���ͣ�4δ�����˻���5������ת��,6��ת���û�֧��������7�ֻ���ֵ��8�������9��ֵʧ���˻�,11,��Ϸ����,12:���½���:14:�����ʾ�:99:�������ͣ�101:��������102:�Ƽ�����(�ֽ�)��103���������棻104�����ؽ�����105��ǩ��������106����ʱ����
		updatememberbalanceUrl.append("orderid=").append("123");
		
		HttpUriRequest httpUriRequest = RequestBuilder.get().
				setUri(updatememberbalanceUrl.toString())
				.build();
		String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
		System.out.println("updatememberbalance:"+result);
		if(StringUtils.isNotBlank(result)){
			if(result.contains("error:")){
				resultMap.put("status", "ERROR");
				resultMap.put("data", result.split(":")[1]);//������Ϣ
			}else{
				resultMap.put("status", "OK");
				resultMap.put("data", result);//������
			}
		}else{
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");
		}
	}
	
	
	
	
	/**
	 * �ֻ������ѯ
	 */
	public void checkphonerecharege() {
		HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(BASE_URI + "checkphonerecharege?phone=13161454672").build();
		String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
		if (StringUtils.isNotBlank(result)) {
			List<CallsPackage>callsPackageList= JSON.parseArray(result, CallsPackage.class);
			System.out.println("callsPackageList:"+callsPackageList);
		}
		System.out.println(result);
	}

	
	
	
	/**
	 * �绰����
	 */
	public void mobile_tel_segment(){
		HttpUriRequest httpUriRequest = RequestBuilder.post().setUri("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18600666666").build();
		String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
		System.out.println(result);
	}
	
	
	/**
	 *	���������¼�ӿ�
	 */
	public void updatememberinvitlog() {
		Map<String,String>params=new HashMap<String, String>();
		params.put("iunionid","ow18PwAa3J6qoK_ZndIgUqaUDUaM1");
		params.put("biunionid", "ow18PwKczxCDQBB91ajdmCGL2fb4");
		Map<String,String> resultMap=new HashMap<String, String>();
		LqInterfaceLogBO lqInterfaceLogBO=new LqInterfaceLogBO();
		try{
			lqInterfaceLogBO.setInterfaceName("updatememberinvitlog");
			StringBuffer updatememberbalanceUrl=new StringBuffer();
			updatememberbalanceUrl.append(BASE_URI);
			updatememberbalanceUrl.append("updatememberinvitlog?");
			updatememberbalanceUrl.append("iunionid=").append(params.get("iunionid")).append("&");
			updatememberbalanceUrl.append("biunionid=").append(params.get("biunionid"));
			
			HttpUriRequest httpUriRequest = RequestBuilder.get().
					setUri(updatememberbalanceUrl.toString())
					.build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(updatememberbalanceUrl.toString()+"---"+result);
			if(StringUtils.isNotBlank(result)){
				if(result.contains("error:")){
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);//������Ϣ
				}else{
					resultMap.put("status", "OK");
					resultMap.put("data", result);//������
				}
			}else{
				resultMap.put("status", "ERROR");
				resultMap.put("data", "���������¼�ӿ�");
			}
		}catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage(e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");
		}
		
		System.out.println(JSON.toJSONString(resultMap));
	}
	
	
	
	
	
	
	
	
	
	
	public void paymentout() {
		Map<String,String> resultMap=new HashMap<String, String>();
		LqInterfaceLogBO lqInterfaceLogBO=new LqInterfaceLogBO();
		try{
			lqInterfaceLogBO.setInterfaceName("paymentout");
			StringBuffer updatememberbalanceUrl=new StringBuffer();
			updatememberbalanceUrl.append(BASE_URI);
			updatememberbalanceUrl.append("paymentout?");
			updatememberbalanceUrl.append("wxorderid=").append("1323131");
			
			HttpUriRequest httpUriRequest = RequestBuilder.get().
					setUri(updatememberbalanceUrl.toString())
					.build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(updatememberbalanceUrl.toString()+"---"+result);
			if(StringUtils.isNotBlank(result)){
				if(result.contains("error:")){
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);//������Ϣ
				}else{
					resultMap.put("status", "OK");
					resultMap.put("data", result);
				}
			}else{
				resultMap.put("status", "ERROR");
				resultMap.put("data", "����֧���ɹ��ص�");
			}
		}catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage(e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");
		}
		System.out.println(JSON.toJSONString(resultMap));
	}
	
	
	
	
	
}
