package cn.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.common.test.SpringTransactionalContextTests;
import cn.emay.laiqiang.dao.LqInvitationCodeDao;

/**
 * 
 * @Title 邀请码测试工具类型
 * @author zjlwm
 * @date 2016-12-14 上午10:08:29
 *
 */
@Transactional
public class LqInvitationCodeDaoTest extends SpringTransactionalContextTests{

	@Autowired
	private LqInvitationCodeDao invitationCodeDao;
	
	
	@Test
	public void test(){
		System.out.println("invitationCodeDao:"+invitationCodeDao);
	}
}
