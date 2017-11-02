package cn.emay.laiqiang.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.CardBo;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.JedisUtil;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.support.JedisKeyUtils;

/**
 * @Title 卡卷service接口
 * @author zjlwm
 * @date 2016-12-14 下午5:16:46
 * 
 */
@Service
public class CardBoService{

	/**
	 * 微信来抢redis 读取DAO
	 */
	@Autowired
	private JedisUtil jedisUtil;
	
	
	/**
	 * 写数据redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	/**
	 * 获取卡卷信息
	 * @param codeId
	 * @return
	 */
	public CardBo get(String codeId){
		String codeIdKey = JedisKeyUtils.getCardboPrivate + codeId;
		CardBo cardbo = new CardBo();
		cardbo.setId(Long.parseLong(codeId));
		jedisUtil.hgetBean(codeIdKey, cardbo);
		if(StringUtils.isNotBlank(cardbo.getCode())){
			Integer isloseI=cardbo.getIslose();
			if(isloseI==null){
				int islose=1;//0未开始、1可以使用、 2已失效、3已使用
				String starttime=cardbo.getStarttime();
				/**
				 * 失效时间
				 */
				String losetime = cardbo.getLosetime();
				Date now = new Date();
				if(StringUtils.isNotBlank(starttime)){
					Date startday= DateUtils.parseDate(starttime);
					if(now.after(startday)){
						islose=1;//可以使用
					}else{
						islose=0;//未开始
					}
				}
				
				Date losday = DateUtils.parseDate(losetime);
				if (now.after(losday)) {
					islose=2;//已失效
				}
				Long status=cardbo.getStatus();
				if(null!=status && status.longValue()==2){
					islose=3;//已使用
				}
				cardbo.setIslose(islose);
			}else{
				cardbo.setIslose(isloseI);
			}
			return cardbo;
		}
		return null;
	}
	
	
	/**
	 * 是为了验证卡卷是否属于当前会员
	 * 卡卷是否存在
	 */
	public boolean isCardExit(Long memberId,String codeid){
		String codeidskey = JedisKeyUtils.getCodeId + memberId;
		List<String> codeids = jedisUtil.lrange(codeidskey, 0, -1);
		return codeids.contains(codeid);
	}
	
	
	
	/**
	 * 获取任务卡卷包
	 * 
	 * @return
	 */
	public List<CardBo> findCardBoByMemberId(Long memberId,String type) {
		List<CardBo> cardBoList = new ArrayList<CardBo>();
		String codeidskey = JedisKeyUtils.getCodeId + memberId;
		List<String> codeids = jedisUtil.lrange(codeidskey, 0, -1);//查询全部
		if(null!=codeids){
			for (String codeId : codeids) {
				CardBo cardbo = get(codeId);
				if(null!=cardbo){
					
					int islose=cardbo.getIslose();
					
					if(type.equals("-1")){//所有
						cardBoList.add(cardbo);
					}else if(String.valueOf(islose).equals(type)){//未开始
						cardBoList.add(cardbo);
					}else if(String.valueOf(islose).equals(type)){//可以使用
						cardBoList.add(cardbo);
					}else if(String.valueOf(islose).equals(type)){//已失效
						cardBoList.add(cardbo);
					}
					
				}
			}
		}
		
		Collections.sort(cardBoList);
		return cardBoList;
	}

	
	
	
	/**
	 * 使用卡卷
	 */
	public void userCard(String codeId){
		CardBo cardbo = get(codeId);
		if(null!=cardbo){
			cardbo.setIslose(3);//已使用
			cardbo.setStatus(2l);//已使用
			String codeIdKey = JedisKeyUtils.getCardboPrivate + codeId;
			jedisUtil.hsetBean(codeIdKey, cardbo);
			
			
			String sql="update cardcode set status=2 where id="+codeId;//已使用
			redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, sql);
		}
	}
	
	
}
