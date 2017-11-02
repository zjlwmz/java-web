package cn.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.emay.laiqiang.bo.LqInterfaceLogBO;


public class Test {

	
	public void getInvitationCode(Long memberId){
		String memberIdstr=memberId.toString();
		int memberIdLength=memberIdstr.length();
		if(memberIdLength>7 && memberId.longValue()!=10000000){// ˝◊÷¥Û”⁄“ª«ßÕÚ
			int beginIndex=memberIdLength-7;
			String memberAdd=memberIdstr.substring(0, beginIndex);
			String memberIdNewStr=memberIdstr.substring(beginIndex);
			long memberIdNew=Long.parseLong(memberIdNewStr);
			System.out.println(memberId+"---1---:code:"+memberAdd+"-"+memberIdNew);
		}else if(memberId.longValue()==10000000){
//			LqInvitationCodeBO lqInvitationCodeBO=lqInvitationCodeDao.get(memberId);
//			String invitationCode=lqInvitationCodeBO.getInvitationCode();
			System.out.println(memberId+"---2---");
		}
		else{
//			LqInvitationCodeBO lqInvitationCodeBO=lqInvitationCodeDao.get(memberId);
//			return lqInvitationCodeBO.getInvitationCode();
			
			System.out.println(memberId+"---3---");
		}
	}
	
	
	public void insert(LqInterfaceLogBO lqInterfaceLogBO) {
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("insert lq_interface_log (interface,description,error_message,created_time) values(");
		sqlBuf.append("'").append(lqInterfaceLogBO.getInterfaceName()).append("'").append(",");
		sqlBuf.append("'").append(lqInterfaceLogBO.getDescription()).append("'").append(",");
		sqlBuf.append("'").append(lqInterfaceLogBO.getErrorMessage()).append("'").append(",");
		sqlBuf.append("'").append(lqInterfaceLogBO.getCreatedTime()).append("'");
		sqlBuf.append(")");
		String sql=sqlBuf.toString();
		System.out.println(sql);
	}
	
	public static void main(String[] args) {
//		Test test=new Test();
//		LqInterfaceLogBO lqInterfaceLogBO=new LqInterfaceLogBO();
//		lqInterfaceLogBO.setCreatedTime(DateUtils.getDateTime());
//		lqInterfaceLogBO.setDescription("≤‚ ‘");
//		lqInterfaceLogBO.setInterfaceName("≤‚ ‘");
//		lqInterfaceLogBO.setErrorMessage("¥ÌŒÛ≤‚ ‘");
//		
//		test.insert(lqInterfaceLogBO);
		
		
		
		
		String gdd="%E5%AF%8C%E8%BF%AA%E8%B6%85%E5%B8%82";
		try {
			System.out.println(URLDecoder.decode(gdd, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
