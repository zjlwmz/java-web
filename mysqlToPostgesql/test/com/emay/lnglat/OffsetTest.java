package com.emay.lnglat;

import java.util.List;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import com.emay.dao.BasicPostgresqlDao2;
import com.emay.dao.BasicSqlServerDao;
import com.emay.lnglatoffset.Offset;

public class OffsetTest {
	static Ioc ioc=null;
	static BasicPostgresqlDao2 basicPostgresqlDao2;
	static BasicSqlServerDao basicSqlServerDao;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
					"/conf/datasource.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
					"com.emay"));
			
			basicSqlServerDao=ioc.get(BasicSqlServerDao.class, "basicSqlServerDao");
			basicPostgresqlDao2=ioc.get(BasicPostgresqlDao2.class, "basicPostgresqlDao2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void test(){
		Integer count=basicSqlServerDao.searchCount(Offset.class);
		Integer pageTotal=count/50000+1;
		System.out.println(count);
		Integer currentPage=224;//224
		while(currentPage<=pageTotal){
			List<Offset>offsetList=basicSqlServerDao.searchByPage(Offset.class, Cnd.where("1", "=", 1).desc("id"), currentPage, 50000);
			currentPage++;
			System.out.println(offsetList.size());
//			basicPostgresqlDao2.save(offsetList);
		}
		
	}
	
	public static void main(String[] args) {
		OffsetTest offset=new OffsetTest();
		offset.test();
	}
}
