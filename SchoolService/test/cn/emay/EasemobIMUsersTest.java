package cn.emay;
import java.util.List;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import cn.emay.common.messaging.IMBase;
import cn.emay.dao.BasicDao;
import cn.emay.dto.Adviser;
import cn.emay.model.Member;
import cn.emay.service.AddressListService;

import com.easemob.server.example.comm.PropertiesUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EasemobIMUsersTest {
    
    static Ioc ioc=null;
	static BasicDao basicDao;
	static AddressListService addressListService;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
					"/conf/datasource.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
					"cn.emay"));
			
			basicDao=ioc.get(BasicDao.class, "basicDao");
			addressListService=ioc.get(AddressListService.class, "addressListService");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    
	
	/**
	 * 用户注册到环信平台
	 */
	@Test
	public void IMUsersRegist(){
		/**
		 * 家长列表
		 */
		String APPKEY=PropertiesUtils.getProperties().getProperty("APPKEY");
		System.out.println(APPKEY);
		List<Member>list=basicDao.search(Member.class, Cnd.where("delFlag", "=", "0"));
		for(Member m:list){
			IMBase.createNewIMUserSingle(m.getMobilephone(),m.getName());
		}
		
		/**
		 * 获取所有顾问
		 */
		List<Adviser>listAdviser=addressListService.getAdviserList();
		for(Adviser adviser:listAdviser){
			IMBase.createNewIMUserSingle(adviser.getAdviserMobile(), adviser.getAdviserName());
		}
		
		
	}
	
	
	
	@Test
	public void IMUsersRegist2(){
//		IMBase.deleteIMUserByuserName("13211111113");
		ObjectNode result=IMBase.createNewIMUserSingle("13211111113", "123456");
		System.out.println(result);
	}
	
	
	
	
	/**
	 * 获取好友列表
	 */
	@Test
	public void getFriends(){
		ObjectNode result=IMBase.getFriends("18610812209");
		System.out.println(result);
	}
	
	
	
	
}
