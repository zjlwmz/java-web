package cn.emay.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.common.security.CredentialsDigest;
import cn.emay.common.security.Digests;
import cn.emay.common.util.Encodes;
import cn.emay.dao.MemberDao;
import cn.emay.dao.SchoolDao;
import cn.emay.model.Member;
import cn.emay.model.SmsConfig;
import cn.emay.model.SmsLog;

@IocBean
public class RegisterService {
	
	private static final int SALT_SIZE = 8;
	public static final int HASH_INTERATIONS = 1024;
	@Inject
	private SchoolDao schoolDao;
	
	@Inject
	private MemberDao memberDao;

	@Inject
	private CredentialsDigest credentialsDigest;
	
	
	/**
	 * 手机号码验证
	 */
	public boolean validateMobile(String mobilephone){
		int count=schoolDao.validateMobile(mobilephone);
		if(count>0){
			return true;
			
		}
		return false;
	}
	
	/**
	 * 保存会员
	 */
	public void saveMember(Member member){
		memberDao.saveWidth(member, "studentList");
	}
	
	/**
	 * 插入环信用记录
	 * @param memberId
	 */
	public void saveHXUser(String memberId,String userType){
		memberDao.saveHXUser(memberId,userType);
	}
	
	/**
	 * 删除环信用户
	 * @param memberId
	 */
	public void deleteHXUser(String memberId){
		memberDao.deleteHXUser(memberId);
	}
	
	
	/**
	 * 获取短信配置
	 * @return
	 */
	public SmsConfig getSmsConfig(){
		List<SmsConfig> list=schoolDao.search(SmsConfig.class, Cnd.where("delFlag", "=", "0"));
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 短信发送日志
	 */
	public void saveSmsLog(SmsLog smslog){
		schoolDao.save(smslog);
	}
	
	
	
	
	/**
	 * 生成安全的密码
	 * 设置密码
	 * @param member
	 */
	public void entryptPassword(Member member){
		byte[] saltBytes = Digests.generateSalt(SALT_SIZE);
		String salt = Encodes.encodeHex(saltBytes);
		member.setSalt(salt);
		String encPass=credentialsDigest.digest(member.getPassword(), saltBytes);
		member.setPassword(encPass);
	}
	
	
	/**
	 * 家长登陆验证
	 * 验证密码
	 * @param salt
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public  boolean  validatePassword(String salt,String plainPassword, String password){
		return  credentialsDigest.matches(password, plainPassword, Encodes.decodeHex(salt));
	}
	
	/**
	 * 顾问、班主任验证
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	
}
