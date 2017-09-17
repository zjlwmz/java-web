package cn.emay;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import cn.emay.dao.AddessListDao;
import cn.emay.dao.AppAdDao;
import cn.emay.dao.BasicDao;
import cn.emay.dao.ConsultantDao;
import cn.emay.dto.Adviser;
import cn.emay.model.consultant.PublishLearning;

public class AddessListDaoTest {
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
	public void getAdviserByMemberId(){
		AddessListDao addessListDao=ioc.get(AddessListDao.class, "addessListDao");
		Adviser adviser=addessListDao.getAdviserByMemberId("1';drop table app_ad--");
		System.out.println("adviser:"+adviser);
	}
	@Test
	public void AppAdDaoTest(){
		AppAdDao AppAdDao=ioc.get(AppAdDao.class,"appAdDao");
		AppAdDao.findAppAdList("5", "1';BEGIN;drop table s_1;COMMIT;--");
	}
	
	
	@Test
	public void findPublishLearningByWhere(){
		ConsultantDao consultantDao=ioc.get(ConsultantDao.class,"consultantDao");
		List<String>schoolIdList=new ArrayList<String>();
		List<String>areaIdList=new ArrayList<String>();
		List<String>gradeList=new ArrayList<String>();
//		consultantDao.findPublishLearningByWhere("1'  or 1=(select count(*) from s_member)--",schoolIdList,areaIdList,gradeList);
		List<PublishLearning> list=consultantDao.findPublishLearningByWhere("1' FOR UPDATE ;SAVEPOINT s; update app_ad set create_by='' where  del_flag='0'; ROLLBACK TO s;--",schoolIdList,areaIdList,gradeList);
		
		System.out.println(list.size());
	}
}
