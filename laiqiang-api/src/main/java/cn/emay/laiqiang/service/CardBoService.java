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
 * @Title ����service�ӿ�
 * @author zjlwm
 * @date 2016-12-14 ����5:16:46
 * 
 */
@Service
public class CardBoService{

	/**
	 * ΢������redis ��ȡDAO
	 */
	@Autowired
	private JedisUtil jedisUtil;
	
	
	/**
	 * д����redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	/**
	 * ��ȡ������Ϣ
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
				int islose=1;//0δ��ʼ��1����ʹ�á� 2��ʧЧ��3��ʹ��
				String starttime=cardbo.getStarttime();
				/**
				 * ʧЧʱ��
				 */
				String losetime = cardbo.getLosetime();
				Date now = new Date();
				if(StringUtils.isNotBlank(starttime)){
					Date startday= DateUtils.parseDate(starttime);
					if(now.after(startday)){
						islose=1;//����ʹ��
					}else{
						islose=0;//δ��ʼ
					}
				}
				
				Date losday = DateUtils.parseDate(losetime);
				if (now.after(losday)) {
					islose=2;//��ʧЧ
				}
				Long status=cardbo.getStatus();
				if(null!=status && status.longValue()==2){
					islose=3;//��ʹ��
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
	 * ��Ϊ����֤�����Ƿ����ڵ�ǰ��Ա
	 * �����Ƿ����
	 */
	public boolean isCardExit(Long memberId,String codeid){
		String codeidskey = JedisKeyUtils.getCodeId + memberId;
		List<String> codeids = jedisUtil.lrange(codeidskey, 0, -1);
		return codeids.contains(codeid);
	}
	
	
	
	/**
	 * ��ȡ���񿨾��
	 * 
	 * @return
	 */
	public List<CardBo> findCardBoByMemberId(Long memberId,String type) {
		List<CardBo> cardBoList = new ArrayList<CardBo>();
		String codeidskey = JedisKeyUtils.getCodeId + memberId;
		List<String> codeids = jedisUtil.lrange(codeidskey, 0, -1);//��ѯȫ��
		if(null!=codeids){
			for (String codeId : codeids) {
				CardBo cardbo = get(codeId);
				if(null!=cardbo){
					
					int islose=cardbo.getIslose();
					
					if(type.equals("-1")){//����
						cardBoList.add(cardbo);
					}else if(String.valueOf(islose).equals(type)){//δ��ʼ
						cardBoList.add(cardbo);
					}else if(String.valueOf(islose).equals(type)){//����ʹ��
						cardBoList.add(cardbo);
					}else if(String.valueOf(islose).equals(type)){//��ʧЧ
						cardBoList.add(cardbo);
					}
					
				}
			}
		}
		
		Collections.sort(cardBoList);
		return cardBoList;
	}

	
	
	
	/**
	 * ʹ�ÿ���
	 */
	public void userCard(String codeId){
		CardBo cardbo = get(codeId);
		if(null!=cardbo){
			cardbo.setIslose(3);//��ʹ��
			cardbo.setStatus(2l);//��ʹ��
			String codeIdKey = JedisKeyUtils.getCardboPrivate + codeId;
			jedisUtil.hsetBean(codeIdKey, cardbo);
			
			
			String sql="update cardcode set status=2 where id="+codeId;//��ʹ��
			redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, sql);
		}
	}
	
	
}
