package com.emay;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import com.emay.dao.BasicPostgresqlDao;
import com.emay.sfa.Simages;

public class SFATest {
	static Ioc ioc=null;
	static BasicPostgresqlDao basicPostgresqlDao;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
					"/conf/datasource.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
					"com.emay"));
			
			basicPostgresqlDao=ioc.get(BasicPostgresqlDao.class, "basicPostgresqlDao");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateCompeStatus(){
		try{
			List<Simages>list=basicPostgresqlDao.search(Simages.class, Cnd.where("1", "=", 1));
			for(Simages compete:list){
				Date created=new Date();
				String image=compete.getImageurl();
				if(null!=image){
					image=image.replace("116.58.220.121", "124.127.89.33");
					compete.setImageurl(image);
					compete.setModifiedon(created);
					basicPostgresqlDao.update(compete);
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
