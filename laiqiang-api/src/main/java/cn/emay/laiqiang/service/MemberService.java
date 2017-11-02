package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.utils.JedisUtil;
import cn.emay.laiqiang.dao.MemberDao;
import cn.emay.laiqiang.support.JedisKeyUtils;

/**
 * ��ԱService�ӿ�
 * @author zjlwm
 * @date 2016-11-25
 */
@Service
public class MemberService{
	
	@Autowired
	private MemberDao memberDao;
	
	/**
	 * ΢������redis ��ȡDAO
	 */
	@Autowired
	private JedisUtil jedisUtil;
	
	/**
	 * �����ȡ
	 * ��ȡ��Ա������Ϣ
	 * @param unionid
	 * @return
	 */
	public MemberBO getMember(String uuid){
		String memberkey=JedisKeyUtils.getMemberByUuid+uuid;
		MemberBO memberBO = new MemberBO();// memberdao.findUniqueByProperty("uuid", po.getMemberuuid());
		
		jedisUtil.hgetBean(memberkey, memberBO);
		return memberBO;
	}
	
	
	
	
	public MemberBO getById(Long id){
		MemberBO memberBO =memberDao.getById(id);
		return memberBO;
	}
}
