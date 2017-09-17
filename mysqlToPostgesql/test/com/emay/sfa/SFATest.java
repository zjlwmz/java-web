package com.emay.sfa;

import java.util.List;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import com.emay.dao.BasicMysqlDao;
import com.emay.dao.BasicPostgresqlDao;
import com.emay.model.bean.SysArea;
import com.emay.model.bean.SysDict;
import com.emay.model.bean.SysMenu;
import com.emay.model.bean.SysOffice;
import com.emay.model.bean.SysRole;
import com.emay.model.bean.SysRoleMenu;
import com.emay.model.bean.SysRoleOffice;
import com.emay.model.bean.SysUser;
import com.emay.model.bean.SysUserRole;

public class SFATest {
	static Ioc ioc=null;
	static BasicPostgresqlDao basicPostgresqlDao;
	static BasicMysqlDao basicMysqlDao;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
					"/conf/datasource.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
					"com.emay"));
			
			basicPostgresqlDao=ioc.get(BasicPostgresqlDao.class, "basicPostgresqlDao");
			basicMysqlDao=ioc.get(BasicMysqlDao.class,"basicMysqlDao");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * sfa数据同步
	 * mysql-->postgresql
	 */
	@Test
	public void synchronousByMenu(){
		List<SysMenu>list=basicMysqlDao.search(SysMenu.class, Cnd.where("delFlag", "=", "0"));
		System.out.println(list.size());
		basicPostgresqlDao.save(list);
	}
	
	
	@Test
	public void synchronousOffice(){
		List<SysOffice>list=basicMysqlDao.search(SysOffice.class, Cnd.where("delFlag", "=", "0"));
		System.out.println(list.size());
		basicPostgresqlDao.save(list);
	}
	
	
	@Test
	public void synchronousRole(){
		List<SysRole>list=basicMysqlDao.search(SysRole.class, Cnd.where("delFlag", "=", "0"));
		System.out.println(list.size());
		basicPostgresqlDao.save(list);
	}
	
	
	@Test
	public void synchronousRoleMenu(){
		List<SysRoleMenu>list=basicMysqlDao.search(SysRoleMenu.class, Cnd.where("1", "=", "1"));
		System.out.println(list.size());
		basicPostgresqlDao.save(list);
	}
	
	
	
	@Test
	public void synchronousRoleOffice(){
		List<SysRoleOffice>list=basicMysqlDao.search(SysRoleOffice.class, Cnd.where("1", "=", "1"));
		System.out.println(list.size());
		basicPostgresqlDao.save(list);
	}
	
	
	@Test
	public void synchronousUser(){
		List<SysUser>list=basicMysqlDao.search(SysUser.class, Cnd.where("1", "=", "1"));
		System.out.println(list.size());
		basicPostgresqlDao.save(list);
	}
	
	
	
	@Test
	public void synchronousUserRole(){
		List<SysUserRole>list=basicMysqlDao.search(SysUserRole.class, Cnd.where("1", "=", "1"));
		System.out.println(list.size());
		basicPostgresqlDao.save(list);
	}
	
	
	@Test
	public void synchronousdict(){
		List<SysDict>list=basicMysqlDao.search(SysDict.class, Cnd.where("delFlag", "=", "0"));
		System.out.println(list.size());
		basicPostgresqlDao.save(list);
	}
	
	@Test
	public void synchronousArea(){
		List<SysArea>list=basicMysqlDao.search(SysArea.class, Cnd.where("delFlag", "=", "0"));
		System.out.println(list.size());
		basicPostgresqlDao.save(list);
	}
	
}
