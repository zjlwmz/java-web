package cn.emay.laiqiang.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqMemberMessageBO;
import cn.emay.laiqiang.common.utils.AESUtil;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.LqMemberMessagDTO;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.token.UserTokenUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title 群发消息service接口
 * @author zjlwm
 * @date 2016-12-6 下午5:31:32
 *
 */
@Service
public class LqMessagePushService extends BaseService{

	@Autowired
	private UserTokenUtils userTokenUtils;
	
	
	/**
	 * 写数据redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	
	/**
	 * 接口所在地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public LqMemberMessageBO get(String id){
		String LqMemberMessageKey=JedisKeyUtils.getLqMessagePushById+id;
		
		String LqMemberMessageInfo=jedisStrings.get(LqMemberMessageKey);
		if(StringUtils.isNotBlank(LqMemberMessageInfo)){
			return JSON.parseObject(LqMemberMessageInfo, LqMemberMessageBO.class);
		}
		return null;
	}
	
	
	/**
	 * 群消息分页查询
	 * @return
	 * @throws Exception 
	 */
	public List<LqMemberMessagDTO>findLqMessagePushList(Long memberId,Long start,Long end) throws Exception{
		List<LqMemberMessagDTO>lqMemberMessageDTOList=new ArrayList<LqMemberMessagDTO>();
		List<String>lqMessagePushBOList=jedisLists.lrange(JedisKeyUtils.findLqMessagePushList, start, end);
		if(null!=lqMessagePushBOList){
			for(String lqMessagePushId:lqMessagePushBOList){
				/**
				 * 群消息
				 */
				LqMemberMessageBO lqMemberMessageBO=get(lqMessagePushId);
				if(null!=lqMemberMessageBO){
					LqMemberMessagDTO lqMemberMessagDTO=new LqMemberMessagDTO();
					BeanUtils.copyProperties(lqMemberMessagDTO, lqMemberMessageBO);
					lqMemberMessagDTO.setMessageType("1");//群消息
					
					
					/**
					 * 群消息里面没有会员ID
					 */
					lqMemberMessageBO.setMemberId(memberId);
					
					/**
					 * 个人消息详情查看地址
					 */
					String desLqNesId=AESUtil.encrypt(lqMessagePushId, userTokenUtils.getAsekey());
					String messageDetailUrl=domain+"laiqiang/app/message/html/detail/1/"+desLqNesId;
					lqMemberMessagDTO.setMessageDetailUrl(messageDetailUrl);
					
					
					
					
					/**
					 * 判断是否已读
					 */
					
					String pushMessageKey=JedisKeyUtils.getLqMemberMessagePushSetByMemberId+memberId;
					boolean isexit=jedisSets.sismember(pushMessageKey, lqMessagePushId);
					if(!isexit){
						/**
						 * 更新消息状态
						 */
						updateStatus(lqMemberMessageBO);
					}
					lqMemberMessagDTO.setIsReaded(1);//已读
					lqMemberMessageDTOList.add(lqMemberMessagDTO);
					
					
				}
				
			}
		}
		
		return lqMemberMessageDTOList;
	}


	
	/**
	 * 更新消息为已读状态
	 * @param messageId
	 */
	public void updateStatus(LqMemberMessageBO memberMessageBO) {
		Long memberId=memberMessageBO.getMemberId();
		long memssagePushId=memberMessageBO.getId();
		memberMessageBO.setIsReaded(1);//已读
		
		//添加到缓存中
		String pushMessageKey=JedisKeyUtils.getLqMemberMessagePushSetByMemberId+memberId;
		jedisSets.sadd(pushMessageKey, memssagePushId+"");
		
		/**
		 * 更新update sql
		 */
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append(" insert  lq_member_message(member_id,");
		sqlBuff.append(" title,brief_intro,content,is_readed,created_time,");	
		sqlBuff.append(" message_from ,message_push_id )");
		sqlBuff.append(" values( ");
		sqlBuff.append(memberMessageBO.getMemberId()).append(",");
		sqlBuff.append("'").append(memberMessageBO.getTitle()).append("'").append(",");
		sqlBuff.append("'"+memberMessageBO.getBriefintro()).append("'").append(",");
		sqlBuff.append("'").append(memberMessageBO.getContent()).append("'").append(",");
		sqlBuff.append(memberMessageBO.getIsReaded()).append(",");
		sqlBuff.append("'").append(memberMessageBO.getPublishTime()).append("'").append(",");
		sqlBuff.append("2").append(",");
		sqlBuff.append(memberMessageBO.getMessagePushId());
		sqlBuff.append(")");
		String updateSql=sqlBuff.toString();
	
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
		
		
	}
	
	
}
