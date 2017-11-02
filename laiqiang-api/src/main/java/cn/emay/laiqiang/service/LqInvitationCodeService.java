package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqInvitationCodeBO;
import cn.emay.laiqiang.dao.LqInvitationCodeDao;

/**
 * 
 * @Title ������
 * @author zjlwm
 * @date 2016-12-14 ����11:11:09
 *
 */
@Service
public class LqInvitationCodeService {

	/**
	 * ������DAO�ӿ�
	 */
	@Autowired
	private LqInvitationCodeDao lqInvitationCodeDao;
	
	
	public LqInvitationCodeBO get(Long memberId){
		return lqInvitationCodeDao.get(memberId);
	}
	
	/**
	 * ǧ��λ��--7λ
	 * ��ʽ����ʹ��
	 * @param memberId
	 * @return
	 */
	public String getInvitationCode(Long memberId){
		String memberIdstr=memberId.toString();
		int memberIdLength=memberIdstr.length();
		if(memberIdLength>7 && memberId.longValue()!=10000000){//���ִ���һǧ��
			int beginIndex=memberIdLength-7;
			String memberAdd=memberIdstr.substring(0, beginIndex);
			String memberIdNewStr=memberIdstr.substring(beginIndex);
			long memberIdNew=Long.parseLong(memberIdNewStr);
			LqInvitationCodeBO lqInvitationCodeBO=lqInvitationCodeDao.get(memberIdNew);
			String invitationCode=lqInvitationCodeBO.getInvitationCode();
			invitationCode=memberAdd+invitationCode;
			return invitationCode;
		}else if(memberId.longValue()==10000000){
			LqInvitationCodeBO lqInvitationCodeBO=lqInvitationCodeDao.get(memberId);
			String invitationCode=lqInvitationCodeBO.getInvitationCode();
			return invitationCode;
		}
		else{
			LqInvitationCodeBO lqInvitationCodeBO=lqInvitationCodeDao.get(memberId);
			return lqInvitationCodeBO.getInvitationCode();
		}
	}
	
	
	
	public String getInvitationCode2(Long memberId){
		LqInvitationCodeBO lqInvitationCodeBO=lqInvitationCodeDao.get(memberId);
		if(null!=lqInvitationCodeBO){
			return lqInvitationCodeBO.getInvitationCode();
		}
		return "";
	}
}
