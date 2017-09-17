package cn.emay.dao;

import java.util.List;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.common.util.DateUtils;
import cn.emay.common.util.Unicode;
import cn.emay.model.AppAd;

/**
 * 广告DAO接口
 * @author zjlWm
 * @version 2015-07-07
 */
@IocBean
public class AppAdDao  extends BasicDao{

	@Inject
	private PropertiesProxy custom;
	
	/**
	 * 广告查询
	 * @param adType
	 * @param typeApp
	 * @return
	 */
	public List<AppAd> findAppAdList(String adType,String typeApp) {
		String currentDate=DateUtils.getDate();
		String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
		String url=server_ip+"/school/wap/html/grade/appAd?id=";
		StringBuffer sqlbuff=new StringBuffer();
		sqlbuff.append("select ad.* ,");
//		sqlbuff.append("concat ('"+server_ip+"', sp.url) AS picture_url, ");
		sqlbuff.append("sp.url AS picture_url, ");
		sqlbuff.append("concat ('"+url+"', ad.id) AS url ");
		sqlbuff.append("from app_ad ad LEFT JOIN sys_picture sp ON ad.picture_id = sp.id ");
		sqlbuff.append(" where ad.del_flag='0' and ");
		if(!"3".equals(adType)){
			sqlbuff.append(" ad.ad_type='"+adType+"' and ");
		}
		sqlbuff.append(" ad.type_app='"+typeApp+"' and  ");
		sqlbuff.append(" (to_char(ad.begin_date, 'yyyy-MM-dd')<='"+currentDate+"' and to_char(ad.end_date, 'yyyy-MM-dd')>='"+currentDate+"') ORDER BY create_date desc ");
		
		Sql sql= Sqls.create(sqlbuff.toString());
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(AppAd.class));
		dao.execute(sql);
		List<AppAd> list = sql.getList(AppAd.class);
		return list;
	}

}
