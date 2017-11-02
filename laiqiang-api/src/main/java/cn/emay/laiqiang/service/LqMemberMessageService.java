package cn.emay.laiqiang.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqMemberMessageBO;
import cn.emay.laiqiang.common.utils.AESUtil;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dao.SysDao;
import cn.emay.laiqiang.dto.LqMemberMessagDTO;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.token.UserTokenUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title �ҵ���Ϣ
 * @author zjlwm
 * @date 2016-12-7 ����9:33:03
 *
 */
@Service
public class LqMemberMessageService extends BaseService{

	/**
	 * д����redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	/**
	 * �ӿ����ڵ�ַ
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	/**
	 * ���ƹ�����
	 */
	@Autowired
	private UserTokenUtils userTokenUtils;
	
	
	/**
	 * ϵͳDAO�ӿ�
	 */
	@Autowired
	private SysDao sysDao;
	
	/**
	 * ��ȡ��Ϣ
	 * @param id
	 * @return
	 */
	public LqMemberMessageBO get(String id){
		String lqMemberMessageKey=JedisKeyUtils.getLqMemberMessageById+id;
		String lqMemberMessageInfo=jedisStrings.get(lqMemberMessageKey);
		if(StringUtils.isNotBlank(lqMemberMessageInfo)){
			return JSON.parseObject(lqMemberMessageInfo, LqMemberMessageBO.class);
		}
		return null;
	}
	
	/**
	 * ������Ϣ״̬
	 */
	public void updateStatus(LqMemberMessageBO lqMemberMessageBO){
		lqMemberMessageBO.setIsReaded(1);//�Ѷ�
		Long memberId=lqMemberMessageBO.getMemberId();
		String lqMemberMessageKey=JedisKeyUtils.getLqMemberMessageById+lqMemberMessageBO.getId();
		jedisStrings.set(lqMemberMessageKey, JSON.toJSONString(lqMemberMessageBO));
		
		
		/**
		 * ����update sql
		 */
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("update lq_member_message set is_readed=1 where member_id="+memberId);
		String updateSql=sqlBuff.toString();
	
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
	}
	
	/**
	 * �ҵ���Ϣ��ҳ��ѯ
	 * @return
	 * @throws Exception 
	 */
	public List<LqMemberMessagDTO>findLqMemberMessageList(Long memberId,Long start,Long end) throws Exception{
		List<LqMemberMessagDTO>lqMemberMessageBOList=new ArrayList<LqMemberMessagDTO>();
		String lqMemberMessagekey=JedisKeyUtils.getLqMemberMessageByMemberId+memberId;
		
		List<String>lqMemberMessageList=jedisLists.lrange(lqMemberMessagekey, start, end);
		if(null!=lqMemberMessageList){
			for(String lqMemberMessageId:lqMemberMessageList){
				LqMemberMessageBO lqMemberMessageBO=get(lqMemberMessageId);
				if(null!=lqMemberMessageBO){
					
					updateStatus(lqMemberMessageBO);//������ϢΪ�Ѷ�
					
					
					LqMemberMessagDTO lqMemberMessagDTO=new LqMemberMessagDTO();
					BeanUtils.copyProperties(lqMemberMessagDTO, lqMemberMessageBO);
					lqMemberMessagDTO.setMessageType("0");//����
					
					
					/**
					 * ������Ϣ����鿴��ַ
					 */
					String desLqNesId=AESUtil.encrypt(lqMemberMessageId, userTokenUtils.getAsekey());
					String messageDetailUrl=domain+"laiqiang/app/message/html/detail/0/"+desLqNesId;
					lqMemberMessagDTO.setMessageDetailUrl(messageDetailUrl);
					
					
					
					lqMemberMessageBOList.add(lqMemberMessagDTO);
				}
			}
		}
		
		return lqMemberMessageBOList;
	}
	
	
	
	
	/**
	 * δ����Ϣ
	 */
	public Long findNoReadMessageCount(Long memberId){
		//Ⱥ��Ϣ����
		long messPushLength=jedisLists.llen(JedisKeyUtils.findLqMessagePushList);
		
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("memberId", memberId);
		long noreadMessage= sysDao.findNoReadMessageCount(params);
		
		long readMessagePush=sysDao.findReadMessagePushCount(params);
		//δ��Ⱥ��Ϣ����
		long noreadMessagePush=messPushLength-readMessagePush;
		
		return noreadMessage+noreadMessagePush;
	}
}
