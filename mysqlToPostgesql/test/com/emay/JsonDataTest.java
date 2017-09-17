package com.emay;

import java.util.List;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import com.emay.dao.BasicMysqlDao;
import com.emay.dao.BasicPostgresqlDao;
import com.emay.dao.BasicPostgresqlDao2;
import com.emay.model.bean.SysArea;
import com.emay.model.bean.SysOffice;
import com.emay.model.bean.SysUser;
import com.emay.sfa.Storeinfo;
import com.emay.sfa.Storeinfo2;
import com.emay.sfa.SysDevice;

public class JsonDataTest {
	static Ioc ioc=null;
	static BasicMysqlDao basicqmysqlDao;
	static BasicPostgresqlDao basicPostgresqlDao;
	static BasicPostgresqlDao2 basicPostgresqlDao2;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
					"/conf/datasource.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
					"com.emay"));
			
			basicPostgresqlDao=ioc.get(BasicPostgresqlDao.class, "basicPostgresqlDao");
			basicqmysqlDao=ioc.get(BasicMysqlDao.class, "basicMysqlDao");
			basicPostgresqlDao2=ioc.get(BasicPostgresqlDao2.class, "basicPostgresqlDao2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据更新
	 */
	@Test
	public void storeUpdate(){
		List<Storeinfo>list=basicPostgresqlDao.search(Storeinfo.class, Cnd.where("delFlag", "=", "0").and("name", "=", "wwww").asc("id"));
		
		for(Storeinfo s:list){
			Storeinfo2 s2=new Storeinfo2();
			s2.setStoreid(s.getStoreid());
			s2.setStorename(s.getStorename());
			s2.setUserid(s.getUserid());
			s2.setOwnername(s.getOwnername());
			s2.setStoretel(s.getStoretel());
			s2.setStoreaddr(s.getStoreaddr());
			s2.setStorelat(s.getStorelat());
			s2.setStorelong(s.getStorelong());
			s2.setCreatedby(s.getCreatedby());
			s2.setCreatedon(s.getCreatedon());
			s2.setModifiedby(s.getModifiedby());
			s2.setModifiedon(s.getModifiedon());
			s2.setOfficeId(s.getOfficeId());
			s2.setDelFlag(s.getDelFlag());
			s2.setRoundWeek(s.getRoundWeek());
			s2.setImageId(s.getImageId());
			s2.setImageUrl(s.getImageUrl());
//			basicqmysqlDao.save(s2);
		}

	}
	
	
	@Test
	public void areaUpdate(){
		List<SysArea>list=basicPostgresqlDao.search(SysArea.class, Cnd.where("delFlag", "=", "0").asc("id"));
		System.out.println(list.size());
		basicqmysqlDao.save(list);
	}
	
	
	@Test
	public void userUpdate(){
		List<SysUser>list=basicPostgresqlDao.search(SysUser.class, Cnd.where("delFlag", "=", "0").asc("id"));
		for(SysUser u:list){
			if(null==basicqmysqlDao.findByCondition(SysUser.class, Cnd.where("loginName", "=", u.getLoginName()))){
				basicqmysqlDao.save(u);
			}
		}
	}
	
	
	@Test
	public void officeUpdate(){
		List<SysOffice>list=basicPostgresqlDao.search(SysOffice.class, Cnd.where("delFlag", "=", "0").and("id", ">", 20).asc("id"));
		for(SysOffice u:list){
			String parentIds=u.getParentIds();
			int index=parentIds.indexOf(",");
			System.out.println(u.getParentIds()+"---"+index+"--====0,1"+parentIds.substring(2));
			u.setParentIds("0,1"+parentIds.substring(2));
			basicqmysqlDao.save(u);
		}
	}
	
	
	
	@Test
	public void diviceUpdate(){
		List<SysDevice>list=basicPostgresqlDao.search(SysDevice.class, Cnd.where("delFlag", "=", "0").and("id", ">", 75).asc("id"));
		for(SysDevice u:list){
			System.out.println(u.getImel());
			u.setCreatedby("1");
			u.setModifiedby("1");
			basicqmysqlDao.update(u);
		}
	}
	
	
	
	
	public void customerUpdate(){
		
	}
}
