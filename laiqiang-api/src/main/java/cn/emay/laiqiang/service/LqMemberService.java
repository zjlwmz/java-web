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
 * @Title app��Աservice�ӿ�
 * @author zjlwm
 * @date 2016-12-13 ����5:06:34
 *
 */
@Service
public class LqMemberService extends BaseService{

	/**
	 * ������־DAO�ӿ�
	 */
	@Autowired
	private LqInviteLogDao lqInviteLogDao;
	
	/**
	 * app��Ա��ԱDAO�ӿ�
	 */
	@Autowired
	private LqMemberDao lqMemberDao;
	
	/**
	 * ��Ա�˺�DAO�ӿ�
	 */
	@Autowired
	private LqAccountDao lqAccountDao;
	
	/**
	 * ֤�����
	 */
	@Autowired
	private CredentialsDigest credentialsDigest;
	
	private static final int SALT_SIZE = 8;
	
	/**
	 * �Ƿ��ڵ�¼��
	 * @return
	 */
	public boolean isLogining(String unionid){
		String registMemberKey=JedisKeyUtils.registMember+unionid;
		boolean result=jedisKeys.exists(registMemberKey);
		return result;
	}
	
	
	/**
	 * �����ڵ�¼
	 * @return
	 */
	public void setLogining(String unionid){
		String registMemberKey=JedisKeyUtils.registMember+unionid;
		/**
		 * �û�ע��
		 */
		jedisStrings.setEx(registMemberKey, 5, DateUtils.getDateTime());
	}
	
	
	/**
	 * ɾ���ڵ�¼��
	 * @return
	 */
	public void delLogining(String unionid){
		String registMemberKey=JedisKeyUtils.registMember+unionid;
		jedisKeys.del(registMemberKey);
	}
	
	
	
	
	
	
	/**
	 * �ӿڵ�����
	 * @return
	 */
	public boolean isIntefaceIng(String key){
		boolean result=jedisKeys.exists(key);
		return result;
	}
	
	
	/**
	 * ���ýӿڵ�����
	 * @return
	 */
	public void setIntefaceIng(String key){
		/**
		 * �û�ע��
		 */
		jedisStrings.setEx(key, 5, DateUtils.getDateTime());
	}
	
	
	/**
	 * ɾ���ӿڵ�������
	 * @return
	 */
	public void delIntefaceIng(String key){
		jedisKeys.del(key);
	}
	
	
	
	
	
	
	/**
	 * ��ȡapp�û���Ϣ
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
	 * �жϻ������Ƿ����
	 * @param unionid
	 * @return
	 */
	public boolean isExitRedisByUnionid(String unionid){
		/**
		 * app�û���Ϣ
		 */
		String lqMemberKey=JedisKeyUtils.getlqMemberByUnionid+unionid;
		return jedisKeys.exists(lqMemberKey);
	}
	
	
	/**
	 * ͨ��΢���û�Ψһ��ʶ����
	 * @param unionid
	 * @return
	 */
	public LqMemberBO getByUnionid(String unionid){
		LqMemberBO lqMemberBO=null;
		
		/**
		 * app�û���Ϣ
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
	 * ����ĺ��Ѹ���
	 * @return
	 */
	public long getinvitedFriends(Long memberId){
		return lqMemberDao.getinvitedFriends(memberId);
	}
	
	
	
	/**
	 * ��Ա����
	 * @param lqMemberBO
	 * @return
	 */
	@Transactional(readOnly=false)
	public int insert(LqMemberBO lqMemberBO,LqAccount lqAccount){
		lqMemberDao.insert(lqMemberBO);
		lqAccountDao.insert(lqAccount);
		
		/**
		 * app�û���Ϣ
		 */
		String lqMemberKey=JedisKeyUtils.getlqMemberByUnionid+lqMemberBO.getUnionid();
		jedisStrings.set(lqMemberKey, JSON.toJSONString(lqMemberBO));
		
		
		/**
		 * app�ʽ��˺���Ϣ
		 */
		String lqAccountKey=JedisKeyUtils.getLqAccountByMemberId+lqMemberBO.getMemberId();
		jedisStrings.set(lqAccountKey, JSON.toJSONString(lqAccount));
		
		
		return 1;
	}
	
	
	/**
	 * ����֧������
	 */
	@Transactional(readOnly=false)
	public void updatePassword(String uuid, String payPassword){
		LqMemberBO lqMemberBO=getByUuid(uuid);
		lqMemberBO.setPayPassword(payPassword);
		entryptPassword(lqMemberBO);
		lqMemberDao.updatePassword(lqMemberBO);
		
		/**
		 * ���»���
		 */
		String lqMemberKey=JedisKeyUtils.getlqMemberByUnionid+lqMemberBO.getUnionid();
		jedisStrings.set(lqMemberKey, JSON.toJSONString(lqMemberBO));
	}
	
	
	
	/**
	 * ���ɰ�ȫ������
	 * ��������
	 * @param lqMemberBO
	 * @return LqMemberBO
	 */
	public LqMemberBO entryptPassword(LqMemberBO lqMemberBO){
		byte[] saltBytes = Digests.generateSalt(SALT_SIZE);
		//������
		String paySalt = Encodes.encodeHex(saltBytes);
		lqMemberBO.setPaySalt(paySalt);
		String encPass =credentialsDigest.digest(lqMemberBO.getPayPassword(), saltBytes);
		lqMemberBO.setPayPassword(encPass);
		return lqMemberBO;
	}
	
	/**
	 * ��֤֧������
	 * 
	 * @param salt
	 *            ������
	 * @param plainPassword
	 *            ��������
	 * @param password
	 *            ��������
	 * @return
	 */
	public boolean validatePayPassword(String salt, String plainPassword,String password){
		return credentialsDigest.matches(password, plainPassword, Encodes.decodeHex(salt));
	}

	/**
	 * �����������ѯ��Ա
	 * @param invitationCode
	 * @return
	 */
	public LqMemberBO getByInvitationCode(String invitationCode){
		return lqMemberDao.getByInvitationCode(invitationCode);
	}
	
	
	/**
	 * ����������
	 * @param lqMemberBO
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateInviter(LqMemberBO lqMemberBO, LqInviteLog lqInviteLog) {
		lqInviteLogDao.insert(lqInviteLog);
		lqMemberDao.updateInviter(lqMemberBO);
		
		
		/**
		 * app�û���Ϣ
		 */
		String lqMemberKey=JedisKeyUtils.getlqMemberByUnionid+lqMemberBO.getUnionid();
		jedisStrings.set(lqMemberKey, JSON.toJSONString(lqMemberBO));
		
		return 1;
	}
	
	
	
	
	/**
	 * ���¼��������û�id
	 */
	@Transactional(readOnly=false)
	public int updatePushId(LqMemberBO lqMemberBO){
		return lqMemberDao.updatePushId(lqMemberBO);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * ���������û�������
	 */
	public void updateInvitationNumberByAllMember(){
		List<Long>memberIdList=sysDao.findAllLqMember();
		for(Long memberId:memberIdList){
			updateInvitationNumber(this.getByMemberId(memberId));
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * ��������������
	 * @param inviter ������id
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
