package cn.emay.laiqiang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqGuestbookBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.support.JedisKeyUtils;

import com.alibaba.fastjson.JSON;


/**
 * 留言service接口
 */
@Service
public class LqGuestbookService extends BaseService{

	
	/**
	 * 写数据redis到mysql
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 文件查看服务器地址
	 */
	@Value("#{configProperties['file.view.url']}")
	private String fileViewUrl;
	
	
	
	/**
	 * 获取留言
	 * @param id
	 * @return
	 */
	public LqGuestbookBO get(String id){
		String getLqGuestbookKey=JedisKeyUtils.getLqGuestbookById+id;
		String lqGuestbookInfo=jedisStrings.get(getLqGuestbookKey);
		if(StringUtils.isBlank(lqGuestbookInfo)){
			return null;
		}
		return JSON.parseObject(lqGuestbookInfo, LqGuestbookBO.class);
	}
	
	
	/**
	 * 留言保存
	 * @param lqGuestbookBO
	 */
	public void save(LqGuestbookBO lqGuestbookBO){
		/**
		 * 生成会员赚钱任务【主键】
		 */
		Long lqGuestbookId=jedisStrings.incrBy(JedisKeyUtils.getLqGuestbookIdKey, 1);
		lqGuestbookBO.setId(lqGuestbookId);
		
		lqGuestbookBO.setCreatedTime(DateUtils.getDateTime());
		
		StringBuffer sqlBuff=new StringBuffer();
		
		sqlBuff.append("insert into lq_guestbook (id,member_id,comment,image_url,created_time) values( ");
		sqlBuff.append(lqGuestbookId).append(",");
		sqlBuff.append(lqGuestbookBO.getMemberId()).append(",");
		sqlBuff.append("'").append(lqGuestbookBO.getComment()).append("'").append(",");
		sqlBuff.append("'").append(lqGuestbookBO.getImageUrl()).append("'").append(",");
		sqlBuff.append("'").append(lqGuestbookBO.getCreatedTime()).append("'");
		sqlBuff.append(" )");
	
		String insertSql=sqlBuff.toString();
		
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertSql);
		
		
		
		/**
		 * 基础数据缓存
		 * 留言数据添加到缓存中
		 */
		jedisStrings.set(JedisKeyUtils.getLqGuestbookById+lqGuestbookId, JSON.toJSONString(lqGuestbookBO));
		
		
		/**
		 * 业务数据
		 * 留言数据添加到 会员留言列表中
		 */
		String getLqGuestbookSortSetListByMemberIdKey=JedisKeyUtils.getLqGuestbookSortSetListByMemberId+lqGuestbookBO.getMemberId();
		long zlength=jedisSortSet.zlength(getLqGuestbookSortSetListByMemberIdKey)+1;
		jedisSortSet.zadd(getLqGuestbookSortSetListByMemberIdKey, zlength, String.valueOf(lqGuestbookId));
		
	}
	
	
	
	/**
	 * 留言分页查询
	 * @param memberId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<LqGuestbookBO>findLqGuestbookBOList(Long memberId,Long start,Long end){
		List<LqGuestbookBO>lqGuestbookList=new ArrayList<LqGuestbookBO>();
		String getLqGuestbookByMemberIdKey=JedisKeyUtils.getLqGuestbookSortSetListByMemberId+memberId;
		Set<String>getLqGuestbookByMemberIdInfo=jedisSortSet.zrevrange(getLqGuestbookByMemberIdKey, start, end);
		if(null!=getLqGuestbookByMemberIdInfo){
			for(String lqGuestbookId:getLqGuestbookByMemberIdInfo){
				LqGuestbookBO lqGuestbookBO=get(lqGuestbookId);
				if(null!=lqGuestbookBO){
					if(StringUtils.isNotBlank(lqGuestbookBO.getImageUrl())){
						lqGuestbookBO.setImageUrl(fileViewUrl+lqGuestbookBO.getImageUrl());
					}
					lqGuestbookList.add(lqGuestbookBO);
				}
			}
		}
		return lqGuestbookList;
	}
	
	
	
	
}
