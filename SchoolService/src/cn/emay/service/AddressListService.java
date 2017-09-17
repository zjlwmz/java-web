package cn.emay.service;

import java.util.List;

import org.nutz.dao.entity.Record;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.common.util.Unicode;
import cn.emay.dao.AddessListDao;
import cn.emay.dao.MemberDao;
import cn.emay.dto.Adviser;
import cn.emay.dto.Headmaster;
import cn.emay.model.SysUser;
import cn.emay.utils.Config;


/**
 * 通讯录接口
 * @author Administrator
 *
 */
@IocBean
public class AddressListService {

	/**
	 * 通讯录Dao接口
	 */
	@Inject
	private AddessListDao addessListDao;
	
	@Inject
	private MemberDao memberDao;
	
	@Inject
	private PropertiesProxy custom;
	
	/**
	 * 顾问
	 * @param memberId  会员id
	 * @return
	 */
	public Adviser getAdviserByMemberId(String memberId){
		return addessListDao.getAdviserByMemberId(memberId);
	}
	
	/**
	 * 获取默认顾问
	 * @param userId
	 * @return
	 */
	public Adviser getAdviserByUserId(String userId){
		SysUser user=addessListDao.find(userId,SysUser.class);
		if(null==user){
			return null;
		}
		Adviser adviser=new Adviser();
		adviser.setAdviserId(user.getId());
		adviser.setAdviserMobile(user.getMobilePhone());
		adviser.setAdviserName(user.getName());
		adviser.setGender(user.getGender());
		adviser.setAssistantName(user.getAssistantName());
		adviser.setAssistantPhone(user.getAssistantPhone());
		adviser.setIntroduction(user.getIntroduction());
		adviser.setAssistantSex(user.getAssistantSex());
		Integer zanNumber=user.getZanNumber();
		if(null==zanNumber)zanNumber=0;
		adviser.setZanNumber(zanNumber);
		Record imuser=memberDao.getHxUser(user.getId());
		if(null!=imuser){
			String hxid="hxid"+imuser.getInt("id");
			String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
			String headUrl=imuser.getString("head_url")==null ? server_ip+"/"+Config.defaultAvatar : server_ip+"/"+imuser.getString("head_url");
			adviser.setHxid(hxid);
			adviser.setHeadUrl(headUrl);
			return adviser;
		}
		return null;
	}
	
	
	
	/**
	 * 班主任
	 * @param memberId
	 * @return
	 */
	public List<Headmaster>Headmaster(String memberId){
		return addessListDao.headmaster(memberId);
	}
	
	
	
	/**
	 * 获取所有顾问
	 */
	public List<Adviser>getAdviserList(){
		return addessListDao.getAdviserList();
	}
}
