package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqInvitationCodeBO;
import cn.emay.laiqiang.dao.LqInvitationCodeDao;

/**
 * 
 * @Title 邀请码
 * @author zjlwm
 * @date 2016-12-14 上午11:11:09
 *
 */
@Service
public class LqInvitationCodeService {

	/**
	 * 邀请码DAO接口
	 */
	@Autowired
	private LqInvitationCodeDao lqInvitationCodeDao;
	
	
	public LqInvitationCodeBO get(Long memberId){
		return lqInvitationCodeDao.get(memberId);
	}
	
	/**
	 * 千万位数--7位
	 * 正式服务使用
	 * @param memberId
	 * @return
	 */
	public String getInvitationCode(Long memberId){
		String memberIdstr=memberId.toString();
		int memberIdLength=memberIdstr.length();
		if(memberIdLength>7 && memberId.longValue()!=10000000){//数字大于一千万
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
