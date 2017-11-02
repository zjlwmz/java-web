package cn.emay.laiqiang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqNewsCommentBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.support.AuditStatus;
import cn.emay.laiqiang.support.JedisKeyUtils;

import com.alibaba.fastjson.JSON;


/**
 * 
 * @Title 新闻评论service接口
 * @author zjlwm
 * @date 2016-12-8 上午11:37:27
 *
 */
@Service
public class LqNewsCommentService extends BaseService{
	
	/**
	 * 写数据redis到mysql
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	/**
	 * 获取评论
	 * @param id
	 * @return
	 */
	public LqNewsCommentBO get(String id){
		/**
		 * 新闻评论id
		 */
		String getLqNewsCommentKey=JedisKeyUtils.getLqNewsCommentById+id;
		
		String lqNewsCommentInfo=jedisStrings.get(getLqNewsCommentKey);
		if(StringUtils.isBlank(lqNewsCommentInfo)){
			return null;
		}
		return JSON.parseObject(lqNewsCommentInfo, LqNewsCommentBO.class);
	}
	
	
	/**
	 * 获取新闻所有通过审核的评论
	 */
	public List<LqNewsCommentBO>findAuditPassLqNewsCommentBO(String newsId){
		List<LqNewsCommentBO>LqNewsCommentList=new ArrayList<LqNewsCommentBO>();
		String newsCommentListKey=JedisKeyUtils.getLqNewsCommentByNewsId+newsId;
		
		Set<String>newsCommentList=jedisSortSet.zrange(newsCommentListKey, 0, -1);
		if(null!=newsCommentList){
			for(String newsCommentId:newsCommentList){
				LqNewsCommentBO lqNewsCommentBO=get(newsCommentId);
				if(null!=lqNewsCommentBO){
					LqNewsCommentList.add(lqNewsCommentBO);
				}
			}
		}
		return LqNewsCommentList;
	}
	
	
	
	
	/**
	 * 新闻评论保存
	 * @param lqGuestbookBO
	 */
	public void save(LqNewsCommentBO lqNewsCommentBO){
		/**
		 * 生成会员赚钱任务【主键】
		 */
		Long lqNewsCommentId=jedisStrings.incrBy(JedisKeyUtils.getLqNewsCommentIdKey, 1);
		lqNewsCommentBO.setId(lqNewsCommentId+"");
		lqNewsCommentBO.setCreatedTime(DateUtils.getDateTime());
		lqNewsCommentBO.setAuditStatus(AuditStatus.WaitAudit);//待审核
		
		StringBuffer sqlBuff=new StringBuffer();
		
		sqlBuff.append("insert into lq_news_comment (id,news_id,member_id,comment,created_time,audit_status) values( ");
		sqlBuff.append(lqNewsCommentId).append(",");
		sqlBuff.append(lqNewsCommentBO.getNewsId()).append(",");
		sqlBuff.append(lqNewsCommentBO.getMemberId()).append(",");
		sqlBuff.append("'").append(lqNewsCommentBO.getComment()).append("'").append(",");
		sqlBuff.append("'").append(lqNewsCommentBO.getCreatedTime()).append("'").append(",");
		sqlBuff.append(lqNewsCommentBO.getAuditStatus());
		
		sqlBuff.append(" )");
		String insertSql=sqlBuff.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertSql);
		
	}
	
}
