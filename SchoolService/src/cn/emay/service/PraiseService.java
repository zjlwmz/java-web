/**
 * 
 */
package cn.emay.service;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import cn.emay.dao.MemberDao;
import cn.emay.dao.PraiseDao;
import cn.emay.model.IntegralRule;
import cn.emay.model.Member;
import cn.emay.model.MemberIntegral;
import cn.emay.model.PraiseShare;
import cn.emay.utils.Config;
import cn.emay.utils.IdGen;

/**
 * 点赞接口
 * @author zjlWm
 * @date 2015-09-08
 */
@IocBean
public class PraiseService {

	@Inject
	private PraiseDao praiseDao;
	
	@Inject
	private MemberDao memberDao;
	
	
	public int integralRule(PraiseShare praiseShare) throws Exception{
		String type=praiseShare.getType();
		String praiseKey=Config.praiseKey;
		if("1".equals(type)){
			praiseKey=Config.praiseKey;
		}else{
			praiseKey=Config.shareKey;
		}
		IntegralRule integralRule=praiseDao.find(praiseKey, IntegralRule.class);
		if(null==integralRule){
			throw new Exception("积分规则没有！");
		}
		return integralRule.getIntegral();
	}
	
	
	
	/**
	 * 点赞新增
	 * @throws Exception 
	 */
	public void praiseAdd(final PraiseShare praiseShare) throws Exception{
		String type=praiseShare.getType();
		String praiseKey=Config.praiseKey;
		final MemberIntegral memberIntegral=new MemberIntegral();
		if("1".equals(type)){
			praiseKey=Config.praiseKey;
		}else{
			praiseKey=Config.shareKey;
		}
		final IntegralRule integralRule=praiseDao.find(praiseKey, IntegralRule.class);
		if(null==integralRule){
			throw new Exception("积分规则没有！");
		}
		final Member member=memberDao.find(praiseShare.getMemberId(), Member.class);
		if(null==member){
			throw new Exception("会员不存在！");
		}
		memberIntegral.setContentType(praiseKey);
		// Begin transaction		
		Trans.exec(new Atom(){
			public void run() {
				praiseDao.save(praiseShare);
				memberIntegral.setIntegral(integralRule.getIntegral());
				memberIntegral.setMemberId(praiseShare.getMemberId());
				memberIntegral.setStatus("1");
				memberIntegral.setId(IdGen.uuid());
				memberIntegral.setContentUrl(praiseShare.getUrl());
				praiseDao.save(memberIntegral);
				int integral=(member.getIntegral()==null ? 0 : member.getIntegral())+integralRule.getIntegral();
				praiseDao.update(Member.class, Chain.make("integral", integral), Cnd.where("id", "=", praiseShare.getMemberId()));
			}
		});
		// End transaction
	}
	
	/**
	 * 是否已经点赞\分享
	 * @return
	 */
	public boolean isPraiseShare(PraiseShare praiseShare){
		int count=praiseDao.searchCount(PraiseShare.class, Cnd.where("memberId", "=", praiseShare.getMemberId()).and("url", "=", praiseShare.getUrl()).and("type", "=", praiseShare.getType()));
		if(count>0){
			return true;
		}
		return false;
	}
}
