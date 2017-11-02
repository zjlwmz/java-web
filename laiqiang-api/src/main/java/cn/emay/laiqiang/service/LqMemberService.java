package cn.emay.laiqiang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.bo.LqMemberBO;
import cn.emay.laiqiang.common.security.CredentialsDigest;
import cn.emay.laiqiang.common.security.Digests;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.Encodes;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dao.LqAccountDao;
import cn.emay.laiqiang.dao.LqInviteLogDao;
import cn.emay.laiqiang.dao.LqMemberDao;
import cn.emay.laiqiang.entity.LqAccount;
import cn.emay.laiqiang.entity.LqInviteLog;
import cn.emay.laiqiang.support.JedisKeyUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title app会员service接口
 * @author zjlwm
 * @date 2016-12-13 下午5:06:34
 *
 */
@Service
public class LqMemberService extends BaseService{

	/**
	 * 邀请日志DAO接口
	 */
	@Autowired
	private LqInviteLogDao lqInviteLogDao;
	
	/**
	 * app会员会员DAO接口
	 */
	@Autowired
	private LqMemberDao lqMemberDao;
	
	/**
	 * 会员账号DAO接口
	 */
	@Autowired
	private LqAccountDao lqAccountDao;
	
	/**
	 * 证书加密
	 */
	@Autowired
	private CredentialsDigest credentialsDigest;
	
	private static final int SALT_SIZE = 8;
	
	/**
	 * 是否在登录中
	 * @return
	 */
	public boolean isLogining(String unionid){
		String registMemberKey=JedisKeyUtils.registMember+unionid;
		boolean result=jedisKeys.exists(registMemberKey);
		return result;
	}
	
	
	/**
	 * 设置在登录
	 * @return
	 */
	public void setLogining(String unionid){
		String registMemberKey=JedisKeyUtils.registMember+unionid;
		/**
		 * 用户注册
		 */
		jedisStrings.setEx(registMemberKey, 5, DateUtils.getDateTime());
	}
	
	
	/**
	 * 删除在登录中
	 * @return
	 */
	public void delLogining(String unionid){
		String registMemberKey=JedisKeyUtils.registMember+unionid;
		jedisKeys.del(registMemberKey);
	}
	
	
	
	
	
	
	/**
	 * 接口调用中
	 * @return
	 */
	public boolean isIntefaceIng(String key){
		boolean result=jedisKeys.exists(key);
		return result;
	}
	
	
	/**
	 * 设置接口调用中
	 * @return
	 */
	public void setIntefaceIng(String key){
		/**
		 * 用户注册
		 */
		jedisStrings.setEx(key, 5, DateUtils.getDateTime());
	}
	
	
	/**
	 * 删除接口调用总中
	 * @return
	 */
	public void delIntefaceIng(String key){
		jedisKeys.del(key);
	}
	
	
	
	
	
	
	/**
	 * 获取app用户信息
	 * @param memberId
	 * @return
	 */
	public LqMemberBO getByMemberId(Long memberId){
		LqMemberBO lqMemberBO=null;
		String getlqMemberByMemberIdKey=JedisKeyUtils.getlqMemberByMemberId+memberId;
		String unionid=jedisStrings.get(getlqMemberByMemberIdKey);
		if(StringUtils.isNotBlank(unionid)){
			String lqMemberByUnionidKey=JedisKeyUtils.getlqMemberByUnionid+unionid;
			String lqMemberInfo=jedisStrings.get(lqMemberByUnionidKey);
			if(StringUtils.isNotBlank(lqMemberInfo)){
				return JSON.parseObject(lqMemberInfo, LqMemberBO.class);
			}
		}
		if(null==lqMemberBO){
			lqMemberBO=lqMemberDao.getByMemberId(memberId);
			if(null!=lqMemberBO){
				jedisStrings.set(getlqMemberByMemberIdKey, lqMemberBO.getUnionid());
			}
		}
		return lqMemberBO;
	}
	
	public LqMemberBO getByUuid(String uuid){
		LqMemberBO lqMemberBO=null;
		
		String getlqMemberByUuidKey=JedisKeyUtils.getlqMemberByUuid+uuid;
		String unionid=jedisStrings.get(getlqMemberByUuidKey);
		if(StringUtils.isNotBlank(unionid)){
			String lqMemberByUnionidKey=JedisKeyUtils.getlqMemberByUnionid+unionid;
			String lqMemberInfo=jedisStrings.get(lqMemberByUnionidKey);
			if(StringUtils.isNotBlank(lqMemberInfo)){
				return JSON.parseObject(lqMemberInfo, LqMemberBO.class);
			}
		}
		
		if(null==lqMemberBO){
			lqMemberBO=lqMemberDao.getByUuid(uuid);
			if(null!=lqMemberBO){
				jedisStrings.set(getlqMemberByUuidKey, lqMemberBO.getUnionid());
			}
		}
		return lqMemberBO;
	}
	
	
	/**
	 * 判断缓存中是否存在
	 * @param unionid
	 * @return
	 */
	public boolean isExitRedisByUnionid(String unionid){
		/**
		 * app用户信息
		 */
		String lqMemberKey=JedisKeyUtils.getlqMemberByUnionid+unionid;
		return jedisKeys.exists(lqMemberKey);
	}
	
	
	/**
	 * 通过微信用户唯一标识查找
	 * @param unionid
	 * @return
	 */
	public LqMemberBO getByUnionid(String unionid){
		LqMemberBO lqMemberBO=null;
		
		/**
		 * app用户信息
		 */
		String lqMemberKey=JedisKeyUtils.getlqMemberByUnionid+unionid;
		String lqMemberInfo=jedisStrings.get(lqMemberKey);
		if(StringUtils.isNotBlank(lqMemberInfo)){
			lqMemberBO=JSON.parseObject(lqMemberInfo, LqMemberBO.class);
		}else{
			lqMemberBO=lqMemberDao.getByUnionid(unionid);
			if(null!=lqMemberBO){
				jedisStrings.set(lqMemberKey, JSON.toJSONString(lqMemberBO));
			}
		}
		return lqMemberBO;
	}
	
	
	
	/**
	 * 邀请的好友个数
	 * @return
	 */
	public long getinvitedFriends(Long memberId){
		return lqMemberDao.getinvitedFriends(memberId);
	}
	
	
	
	/**
	 * 会员创建
	 * @param lqMemberBO
	 * @return
	 */
	@Transactional(readOnly=false)
	public int insert(LqMemberBO lqMemberBO,LqAccount lqAccount){
		lqMemberDao.insert(lqMemberBO);
		lqAccountDao.insert(lqAccount);
		
		/**
		 * app用户信息
		 */
		String lqMemberKey=JedisKeyUtils.getlqMemberByUnionid+lqMemberBO.getUnionid();
		jedisStrings.set(lqMemberKey, JSON.toJSONString(lqMemberBO));
		
		
		/**
		 * app资金账号信息
		 */
		String lqAccountKey=JedisKeyUtils.getLqAccountByMemberId+lqMemberBO.getMemberId();
		jedisStrings.set(lqAccountKey, JSON.toJSONString(lqAccount));
		
		
		return 1;
	}
	
	
	/**
	 * 设置支付密码
	 */
	@Transactional(readOnly=false)
	public void updatePassword(String uuid, String payPassword){
		LqMemberBO lqMemberBO=getByUuid(uuid);
		lqMemberBO.setPayPassword(payPassword);
		entryptPassword(lqMemberBO);
		lqMemberDao.updatePassword(lqMemberBO);
		
		/**
		 * 更新缓存
		 */
		String lqMemberKey=JedisKeyUtils.getlqMemberByUnionid+lqMemberBO.getUnionid();
		jedisStrings.set(lqMemberKey, JSON.toJSONString(lqMemberBO));
	}
	
	
	
	/**
	 * 生成安全的密码
	 * 设置密码
	 * @param lqMemberBO
	 * @return LqMemberBO
	 */
	public LqMemberBO entryptPassword(LqMemberBO lqMemberBO){
		byte[] saltBytes = Digests.generateSalt(SALT_SIZE);
		//加密盐
		String paySalt = Encodes.encodeHex(saltBytes);
		lqMemberBO.setPaySalt(paySalt);
		String encPass =credentialsDigest.digest(lqMemberBO.getPayPassword(), saltBytes);
		lqMemberBO.setPayPassword(encPass);
		return lqMemberBO;
	}
	
	/**
	 * 验证支付密码
	 * 
	 * @param salt
	 *            加密盐
	 * @param plainPassword
	 *            明文密码
	 * @param password
	 *            加密密码
	 * @return
	 */
	public boolean validatePayPassword(String salt, String plainPassword,String password){
		return credentialsDigest.matches(password, plainPassword, Encodes.decodeHex(salt));
	}

	/**
	 * 根据邀请码查询会员
	 * @param invitationCode
	 * @return
	 */
	public LqMemberBO getByInvitationCode(String invitationCode){
		return lqMemberDao.getByInvitationCode(invitationCode);
	}
	
	
	/**
	 * 设置邀请人
	 * @param lqMemberBO
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateInviter(LqMemberBO lqMemberBO, LqInviteLog lqInviteLog) {
		lqInviteLogDao.insert(lqInviteLog);
		lqMemberDao.updateInviter(lqMemberBO);
		
		
		/**
		 * app用户信息
		 */
		String lqMemberKey=JedisKeyUtils.getlqMemberByUnionid+lqMemberBO.getUnionid();
		jedisStrings.set(lqMemberKey, JSON.toJSONString(lqMemberBO));
		
		return 1;
	}
	
	
	
	
	/**
	 * 更新激光推送用户id
	 */
	@Transactional(readOnly=false)
	public int updatePushId(LqMemberBO lqMemberBO){
		return lqMemberDao.updatePushId(lqMemberBO);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 更新所有用户邀请数
	 */
	public void updateInvitationNumberByAllMember(){
		List<Long>memberIdList=sysDao.findAllLqMember();
		for(Long memberId:memberIdList){
			updateInvitationNumber(this.getByMemberId(memberId));
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * 更新邀请人总数
	 * @param inviter 邀请人id
	 * @return
	 */
	@Transactional(readOnly=false)
	public int updateInvitationNumber(LqMemberBO lqMemberBO){
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("inviter", lqMemberBO.getMemberId());
		long invitationNumber=lqMemberDao.findInvitationNumber(params);
		lqMemberBO.setInvitationNumber(invitationNumber);
		lqMemberDao.updateInvitationNumber(lqMemberBO);
		return 1;
	}
	
}
