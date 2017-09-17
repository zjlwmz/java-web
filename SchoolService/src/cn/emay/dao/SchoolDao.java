package cn.emay.dao;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.model.Member;

/**
 * 
 * @author zjlWm
 * @version 2015-04-07
 */
@IocBean
public class SchoolDao extends BasicDao {

	/**
	 * 验证手机号码是否已经存在
	 */
	public int validateMobile(String mobilephone){
		return dao.count(Member.class, Cnd.where("mobilephone", "=", mobilephone));
	}
	
}
