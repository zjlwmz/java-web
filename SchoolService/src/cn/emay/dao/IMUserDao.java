/**
 * 
 */
package cn.emay.dao;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.chat.IMUser;

/**
 * 环信用户Dao
 * @author zjlWm
 * @date 2015-09-02
 */
@IocBean
public class IMUserDao extends BasicDao{

	
	/**
	 * 修改头像
	 */
	public int updateHeadImage(String userid,String headUrl){
		int result=dao.update(IMUser.class,  Chain.make("head_url", userid), Cnd.where("user_id", "=", userid));
		return result;
	}
	
	/**
	 * 环信用户对象
	 * @param hxid
	 * @return
	 */
	public IMUser getUseridByHxid(String hxid){
		String hxidNew=hxid=hxid.replace("hxid", "");
		IMUser imuser=dao.fetch(IMUser.class, Cnd.where("id", "=",Integer.parseInt(hxidNew)));
		return imuser;
	}
	
	/**
	 * 获取环信id
	 * @return
	 */
	public String getHxid(String userId){
		IMUser imuser=dao.fetch(IMUser.class, Cnd.where("userId", "=",userId));
		if(null!=imuser){
			String hxid="hxid"+imuser.getId();
			return hxid;
		}
		return null;
	}
	public IMUser getHxUser(String userId){
		IMUser imuser=dao.fetch(IMUser.class, Cnd.where("userId", "=",userId));
		return imuser;
	}
	
	
	/**
	 * 设置环信用户id
	 * @param userId
	 * @param userType 1家长、2顾问
	 */
	public IMUser saveHXUser(String userId,String userType){
		IMUser imuser=dao.fetch(IMUser.class, Cnd.where("userId", "=",userId));
		if(null==imuser){
			dao.insert(IMUser.class, Chain.make("user_id", userId).add("user_type", userType));
			return dao.fetch(IMUser.class, Cnd.where("user_id", "=", userId));
		}else{
			return imuser;
		}
	}
	
	/**
	 * 删除环信用户记录
	 * @param userId
	 */
	public void deleteHXUser(String userId){
		IMUser imuser=dao.fetch(IMUser.class, Cnd.where("userId", "=",userId));
		dao.delete(imuser);
	}
}
