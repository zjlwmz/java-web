/**
 * 
 */
package cn.emay;

import java.util.List;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import cn.emay.dao.BasicDao;
import cn.emay.dao.MemberDao;
import cn.emay.model.Member;

/**
 * @author zjlWm
 * @date
 *
 */
public class MemberDaoTest {
	static Ioc ioc=null;
	static BasicDao basicDao;
	static MemberDao memberDao;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
					"/conf/datasource.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
					"cn.emay"));
			
			basicDao=ioc.get(BasicDao.class, "basicDao");
			memberDao=ioc.get(MemberDao.class,"memberDao");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test1(){
		memberDao.saveHXUser("1313132","1");
	}
	
	@Test
	public void test2(){
		String hxid=memberDao.getHxid("131313");
		System.out.println(hxid);
	}
	
	@Test
	public void test3(){
		memberDao.deleteHXUser("1313132");
	}
	
	
	@Test
	public void findList(){
		List<Member>memberList=memberDao.search(Member.class, Cnd.where("delFlag", "=", "0"));
		for(Member m:memberList){
			memberDao.saveHXUser(m.getId(), "1");
		}
	}
}
