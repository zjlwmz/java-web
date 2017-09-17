/**
 * 
 */
package cn.emay;

import java.util.Date;

import org.junit.Test;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import cn.emay.common.util.IdGen;
import cn.emay.dao.BasicDao;
import cn.emay.model.SmsConfig;
import cn.emay.model.SmsLog;
import cn.emay.service.RegisterService;

import example.SingletonClient;

/**
 * 短信发送测试
 * @author zjlWm
 * @date
 *
 */
public class SmsTest {

	static Ioc ioc=null;
	static BasicDao basicDao;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
					"/conf/datasource.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
					"cn.emay"));
			
			basicDao=ioc.get(BasicDao.class, "basicDao");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test(){
		String content="【云南网上学校】验证码："+123456;
		try{
			int returnNum = SingletonClient.getClient("9SDK-EMY-0999-JBYUQ","df311175c5040abf3f6d79d5203afeda").sendSMS(new String[] { "13161454672" },content,"",5);
			System.err.println(returnNum);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test2(){
		int sms=(int)((Math.random()*9+1)*100000);
		/**
		 * 发送短信
		 * 
		 * 
		 * XXXXXX
		 */
		RegisterService registerService=ioc.get(RegisterService.class);
		SmsConfig smsConfig=registerService.getSmsConfig();
		if(null!=smsConfig){
			String content="【云南网上学校】验证码："+sms;
			int returnNum=-1;
			try{
				returnNum = SingletonClient.getClient(smsConfig.getSerialnumber(),smsConfig.getKey()).sendSMS(new String[] { "13161454672" },content,"",5);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			SmsLog smslog=new SmsLog();
			smslog.setContent(content);
			smslog.setId(IdGen.uuid());
			smslog.setSendDate(new Date());
			smslog.setStatus(returnNum+"");
			smslog.setSmscode(sms+"");
			smslog.setMobilephone("13161454672");
			registerService.saveSmsLog(smslog);
			
		}else{
			System.out.println("-----");
		}
	}
	
}
