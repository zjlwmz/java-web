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
 * @Title 微信来抢接口测试
 * @author zjlwm
 * @date 2016-12-12 上午11:51:49
 * 
 */
public class LQServiceTest {

	
	public static String BASE_URI = "http://www1.m.cn/appinterface/server/";

	
	/**
	 * 1. 创建账户、或查询账户信息接口
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
	 * 2. 可充值流量包查询接口
	 */
	@Test
	public void checkphoneflow() {
		HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(BASE_URI + "checkphoneflow?phone=17718301027").build();
		String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
		System.out.println(result);
	}

	
	/**
	 * 3. 流量充值接口
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
	 * 4. M码兑换接口
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
	 *	用户流量变动接口
	 *
	 */
	public void updatememberbalance() {
		Map<String,String> resultMap=new HashMap<String, String>();
		StringBuffer updatememberbalanceUrl=new StringBuffer();
		updatememberbalanceUrl.append(BASE_URI);
		updatememberbalanceUrl.append("updatememberbalance?");
		updatememberbalanceUrl.append("unionid=").append("96df69ff-3664-40cb-8de4-6ca95dc5b6bf").append("&");
		updatememberbalanceUrl.append("flow=").append("10").append("&");
		updatememberbalanceUrl.append("descr=").append("测试").append("&");
		updatememberbalanceUrl.append("type=").append("101").append("&");//类型：1抢红包，2购买流量，3企业赠送，4未抢完退还，5流量大转盘,6大转盘用户支付流量，7手机充值，8发红包，9充值失败退还,11,游戏奖励,12:拉新奖励:14:调查问卷:99:安信赠送；101:任务奖励；102:推荐奖励(现金)；103：返利收益；104：下载奖励；105：签到奖励；106：限时促销
		updatememberbalanceUrl.append("orderid=").append("123");
		
		HttpUriRequest httpUriRequest = RequestBuilder.get().
				setUri(updatememberbalanceUrl.toString())
				.build();
		String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
		System.out.println("updatememberbalance:"+result);
		if(StringUtils.isNotBlank(result)){
			if(result.contains("error:")){
				resultMap.put("status", "ERROR");
				resultMap.put("data", result.split(":")[1]);//错误信息
			}else{
				resultMap.put("status", "OK");
				resultMap.put("data", result);//订单号
			}
		}else{
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");
		}
	}
	
	
	
	
	/**
	 * 手机号码查询
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
	 * 电话类型
	 */
	public void mobile_tel_segment(){
		HttpUriRequest httpUriRequest = RequestBuilder.post().setUri("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18600666666").build();
		String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
		System.out.println(result);
	}
	
	
	/**
	 *	保存邀请记录接口
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
					resultMap.put("data", result.split(":")[1]);//错误信息
				}else{
					resultMap.put("status", "OK");
					resultMap.put("data", result);//订单号
				}
			}else{
				resultMap.put("status", "ERROR");
				resultMap.put("data", "保存邀请记录接口");
			}
		}catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage(e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");
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
					resultMap.put("data", result.split(":")[1]);//错误信息
				}else{
					resultMap.put("status", "OK");
					resultMap.put("data", result);
				}
			}else{
				resultMap.put("status", "ERROR");
				resultMap.put("data", "抢拍支付成功回调");
			}
		}catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage(e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");
		}
		System.out.println(JSON.toJSONString(resultMap));
	}
	
	
	
	
	
}
