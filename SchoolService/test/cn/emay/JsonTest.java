package cn.emay;

import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.nutz.json.Json;

import cn.emay.common.util.DateUtils;
import cn.emay.common.util.IdGen;
import cn.emay.common.util.Unicode;
import cn.emay.dao.BasicDao;
import cn.emay.model.Area;
import cn.emay.service.MemberService;
import cn.emay.service.RegisterService;
import cn.emay.service.SchoolService;

@SuppressWarnings("unchecked")
public class JsonTest {
	static Ioc ioc=null;
	static BasicDao basicDao;
//	static BasicMysqlDao basicMysqlDao;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
					"/conf/datasource.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
					"cn.emay"));
			
			basicDao=ioc.get(BasicDao.class, "basicDao");
//			basicMysqlDao=ioc.get(BasicMysqlDao.class, "basicMysqlDao");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void memberservice(){
		MemberService memberService=ioc.get(MemberService.class, "memberService");
		List<Map<String,Object>>memberList=memberService.findMembereByAdviserForMap("dddd");
	}
	
	
	
	
	@Test
	public void test3(){
		FileReader reader;
		try {
			reader = new FileReader("F:/myeclipseWorkspace/SchoolService/test/cn/emay/BaiduMap_cityCenter.txt");
			Map<String,Object> map=(Map<String, Object>) Json.fromJson(reader);
			/**
			 * 自治区
			 */
			Date createdon=new Date();
			String parentId_="1";
			List<Map<String,String>> municipalities=(List<Map<String,String>>)map.get("municipalities");
			for(int i=0;i<municipalities.size();i++){
				Map<String,String> municipalitie=municipalities.get(i);
				Area baidumap=new Area();
				String g[]=municipalitie.get("g").split("\\|");
				String gps[]=g[0].split(",");
//				Integer zoomLevel=Integer.parseInt(g[1]);
//				baidumap.setZoomLevel(zoomLevel);
				baidumap.setLon(Double.parseDouble(gps[0]));
				baidumap.setLat(Double.parseDouble(gps[1]));
				baidumap.setName(municipalitie.get("n")+"市");
//				baidumap.setType("municipalities");
				baidumap.setCreateDate(createdon);
				baidumap.setAreaid(IdGen.uuid());
				baidumap.setParentId(parentId_);
				baidumap.setDelFlag("0");
//				basicMysqlDao.save(baidumap);
			}
			
			
			/**
			 * 省provinces
			 */
			List<Map<String,Object>> provinces=(List<Map<String,Object>>)map.get("provinces");
			for(int i=0;i<provinces.size();i++){
				Map<String,Object> province=provinces.get(i);
				Area baidumap=new Area();
				String g[]=province.get("g").toString().split("\\|");
				String gps[]=g[0].split(",");
//				Integer zoomLevel=Integer.parseInt(g[1]);
//				baidumap.setZoomLevel(zoomLevel);
				baidumap.setLon(Double.parseDouble(gps[0]));
				baidumap.setLat(Double.parseDouble(gps[1]));
				baidumap.setName(province.get("n").toString()+"省");
//				baidumap.setType("provinces");
				baidumap.setCreateDate(createdon);
				String pid=IdGen.uuid();
				baidumap.setAreaid(pid);
				baidumap.setParentId(parentId_);
				baidumap.setDelFlag("0");
//				basicMysqlDao.save(baidumap);
				
				/**
				 * 市
				 */
				List<Map<String,String>> cities=(List<Map<String,String>>)province.get("cities");
				for(int j=0;j<cities.size();j++){
					Map<String,String> citie=cities.get(j);
					Area baidumap2=new Area();
					String g2[]=citie.get("g").split("\\|");
					String gps2[]=g2[0].split(",");
//					Integer zoomLevel2=Integer.parseInt(g2[1]);
//					baidumap2.setZoomLevel(zoomLevel2);
					baidumap2.setLon(Double.parseDouble(gps2[0]));
					baidumap2.setLat(Double.parseDouble(gps2[1]));
					baidumap2.setName(citie.get("n")+"市");
//					baidumap2.setType("cities");
					baidumap2.setCreateDate(createdon);
					baidumap2.setParentId(pid);
					baidumap2.setAreaid(IdGen.uuid());
					baidumap2.setDelFlag("0");
//					basicMysqlDao.save(baidumap2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void aa(){
		SchoolService sss=ioc.get(SchoolService.class, "schoolService");
		System.err.println(sss);
	}
	
	
	@Test
	public void passwordTest(){
		RegisterService registerService=ioc.get(RegisterService.class, "registerService");
		String salt="9207b112361e6109";
		String password="35ee6c50f627c3594eaed8519e43052d69ba0d04";
		String plainPassword="123456";
		boolean result=registerService.validatePassword(salt, plainPassword, password);
		System.out.println(result);
		
	}
	
	
	
	@Test
	public void testConfig(){
		PropertiesProxy custom=ioc.get(PropertiesProxy.class, "custom");
		String host=custom.get("host");
		System.out.println(host+"--"+Unicode.fromEncodedUnicode(host));
	}
	
	
	@Test
	public void userDate(){
		Long time=DateUtils.parseDate("2014-10-10").getTime();
		System.out.println(time);
	}
}
