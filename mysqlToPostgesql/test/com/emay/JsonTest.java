package com.emay;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.nutz.json.Json;
import org.nutz.lang.Files;

import com.emay.check.Customer;
import com.emay.dao.BasicMysqlDao;
import com.emay.dao.BasicPostgresqlDao;
import com.emay.dao.BasicPostgresqlDao2;
import com.emay.dao.BasicSqlServerDao;
import com.emay.model.bean.SysArea;
import com.emay.sfa.Product;
import com.emay.utils.RequestUtils;
import com.emay.wx.WeixinUser;

public class JsonTest {
	static Ioc ioc=null;
	static BasicMysqlDao basicqmysqlDao;
	static BasicPostgresqlDao basicPostgresqlDao;
	static BasicPostgresqlDao2 basicPostgresqlDao2;
	static BasicSqlServerDao basicSqlServerDao;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
					"/conf/datasource.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
					"com.emay"));
			
			basicqmysqlDao=ioc.get(BasicMysqlDao.class, "basicMysqlDao");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void findWexinUser(){
		System.out.print(basicqmysqlDao);
		try{
			File file=Files.createFileIfNoExists("c:/weixinuser_11.txt");
			List<WeixinUser>list=basicqmysqlDao.searchByPage(WeixinUser.class,Cnd.wrap("mobilephone is not null  ORDER BY subscribe_time desc") , 11, 10000);
			for(WeixinUser weixinuser:list){
				String username=weixinuser.getUsername();
				String mobilephone=weixinuser.getMobilephone();
				Files.appendWrite(file, "名称："+username+"-电话号码:"+mobilephone+"\r\n");
			}
		}catch (Exception e) {
			
		}
		
	}
	
	
	
	@Test
	public void create(){
		try{
			
//			List<SysDict>list=basicPostgresqlDao.search(SysDict.class,Cnd.where("1", "=", 1).asc("id"));
//			System.out.println("list:"+list);
//			System.out.println("basicSqlServerDao:"+basicSqlServerDao);
//			String json=Json.toJson(list);
//			basicSqlServerDao.save(Json.fromJsonAsList(SysDict2.class, json));
			
			
			List<Customer>list=basicPostgresqlDao.search(Customer.class,Cnd.where("1", "=", 1).asc("id"));
			for(Customer c:list){
				if(null!=c.getImageUrl()){
					String imageUrl=c.getImageUrl().replace("116.58.220.116:8989", "172.16.1.45:8989");
					c.setImageUrl(imageUrl);
					basicPostgresqlDao.update(c);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDataSource(){
		try{
			NutDao dao=ioc.get(NutDao.class, "sqlserverdao");
			System.out.println(dao);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	/**
	 * ��ݸ���
	 */
	@Test
	public void dataUpdate(){
		List<Product>list=basicPostgresqlDao.search(Product.class, Cnd.where("delFlag", "=", "0").asc("id"));
		for(Product product:list){
			System.out.println(product.getId()+"----"+product.getName());
		}
	}
	
	
	@Test
	public void areaAdd(){
		SysArea sysarea=basicPostgresqlDao2.find(414, SysArea.class);
		sysarea.setName("˫����");
		sysarea.setParentId(sysarea.getId());
		sysarea.setParentIds(sysarea.getParentIds()+sysarea.getId()+",");
		sysarea.setId(null);
		sysarea.setType("4");
		sysarea.setCode("116001");
		basicPostgresqlDao2.save(sysarea);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void readJsonFile(){
		SysArea sysarea=basicPostgresqlDao2.find(83, SysArea.class);
		Map<String,Object>map=(Map<String,Object>)Json.fromJson(Files.read("c:/json.txt"));
		Map<String,Object>province=(Map<String,Object>)map.get("content");
		List<Map<String,Object>>citys=(List<Map<String,Object>>) province.get("sub");
		sysarea.setParentId(sysarea.getId());
		sysarea.setParentIds(sysarea.getParentIds()+sysarea.getId()+",");
		sysarea.setId(null);
		sysarea.setType("4");
		for(Map<String,Object> city:citys){
			System.out.println(city.get("area_name"));
			System.out.println(city.get("area_code"));
			
			sysarea.setName(city.get("area_name").toString());
			sysarea.setCode(city.get("area_code").toString());
			basicPostgresqlDao2.save(sysarea);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void readJsonUrl(){
		try {
			SysArea sysareaProvince=basicPostgresqlDao2.find(14, SysArea.class);
			/**
			 * ��ȡʡ��������
			 */
			String json=RequestUtils.testPost(23);
			Map<String,Object>map=(Map<String,Object>)Json.fromJson(json);
			Map<String,Object>content=(Map<String,Object>)map.get("content");
			List<Map<String,Object>>citys=(List<Map<String,Object>>) content.get("sub");
			System.out.println("area_name:---"+content.get("area_name"));
			
			//���б�
			for(Map<String,Object> city:citys){
				String areaName=city.get("area_name").toString();
				Integer areaCode =Integer.parseInt(city.get("area_code").toString());
				SysArea sysareaCity=basicPostgresqlDao2.findByCondition(SysArea.class, Cnd.where("name", "=", areaName).and("parentId", "=", sysareaProvince.getId()).and("delFlag", "=", "0"));
				if(null!=sysareaCity){
					sysareaCity.setCode(areaCode.toString());
					sysareaCity.setType("3");
					basicPostgresqlDao2.update(sysareaCity);
				}else{
					sysareaCity=new SysArea();
					sysareaCity.setParentId(sysareaProvince.getId());
					sysareaCity.setParentIds(sysareaProvince.getParentIds()+sysareaProvince.getId()+",");
					sysareaCity.setCode(areaCode.toString());
					sysareaCity.setName(areaName);
					sysareaCity.setType("3");
					sysareaCity.setLon(null);
					sysareaCity.setCreateDate(new Date());
					sysareaCity.setDelFlag("0");
					sysareaCity.setUpdateDate(new Date());
//					Integer syscityId=basicPostgresqlDao2.save(sysareaCity).getId();
//					sysareaCity.setId(syscityId);
				}
				System.out.println(areaName+"------"+areaCode);
				
				
				//��ȡ�����������б�
				String jsoncity=RequestUtils.testPost(areaCode);
				Map<String,Object>mapcity=(Map<String,Object>)Json.fromJson(jsoncity);
				Map<String,Object>content_=(Map<String,Object>)mapcity.get("content");
				if(null!=content_.get("sub")){
					List<Map<String,Object>>citys_=(List<Map<String,Object>>) content_.get("sub");
					//�����б�ѭ��
					for(Map<String,Object> city_:citys_){
						String areaName_=city_.get("area_name").toString();
						Integer areaCode_ =Integer.parseInt(city_.get("area_code").toString());
						System.out.println(areaName_+"------"+areaCode_);
						SysArea sysareaRegion=basicPostgresqlDao2.findByCondition(SysArea.class, Cnd.where("name", "=", areaName_).and("parentId", "=", sysareaCity.getId()).and("delFlag", "=", "0"));
						if(null!=sysareaRegion){
							sysareaRegion.setType("4");
							sysareaRegion.setCode(areaCode_.toString());
							basicPostgresqlDao2.update(sysareaRegion);
						}else{
							sysareaRegion=new SysArea();
							sysareaRegion.setParentId(sysareaCity.getId());
							sysareaRegion.setParentIds(sysareaCity.getParentIds()+sysareaCity.getId()+",");
							sysareaRegion.setCode(areaCode_.toString());
							sysareaRegion.setName(areaName_);
							sysareaRegion.setType("4");
							sysareaRegion.setCreateDate(new Date());
							sysareaRegion.setDelFlag("0");
							sysareaRegion.setUpdateDate(new Date());
							basicPostgresqlDao2.save(sysareaRegion);
						}
					}
				}
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@Test
	public void updateWxUser() throws ParseException{
		List<WeixinUser>list=basicqmysqlDao.search(WeixinUser.class, Cnd.where("DATE_FORMAT(subscribe_time,'%Y-%m-%d')", "<", "2014-01-01"));
		for(WeixinUser user:list){
			Date subscribeTime=user.getSubscribeTime();
			 String datestr=DateFormatUtils.format(subscribeTime, "yyyy-MM-dd HH:mm:ss");
			 String newTimestr="2014"+datestr.substring(4);
			 Date newtime=DateUtils.parseDate(newTimestr, "yyyy-MM-dd HH:mm:ss");
			 user.setSubscribeTime(newtime);
			 basicqmysqlDao.update(user);
		}
	}
}
