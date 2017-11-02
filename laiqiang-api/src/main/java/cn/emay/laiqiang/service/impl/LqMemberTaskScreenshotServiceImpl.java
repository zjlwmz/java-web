package cn.emay.laiqiang.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqMemberTaskScreenshotBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.service.BaseService;
import cn.emay.laiqiang.service.LqMemberTaskScreenshotService;
import cn.emay.laiqiang.support.JedisKeyUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title 截图任务截图service接口 实现
 * @author zjlwm
 * @date 2017-1-3 上午10:39:09
 *
 */
@Service
public class LqMemberTaskScreenshotServiceImpl extends BaseService implements LqMemberTaskScreenshotService {

	
	/**
	 * 文件查看服务器地址
	 */
	@Value("#{configProperties['file.view.url']}")
	private String fileViewUrl;
	
	
	/**
	 * 写数据redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	/**
	 * 获取一个截图任务
	 * @param lqMemberTaskScreenshotBOId
	 * @return
	 */
	public LqMemberTaskScreenshotBO get(String lqMemberTaskScreenshotBOId){
		//缓存截图key
		String getLqMemberTaskScreeshotKey=JedisKeyUtils.getLqMemberTaskScreeshot;
		getLqMemberTaskScreeshotKey=MessageFormat.format(getLqMemberTaskScreeshotKey ,lqMemberTaskScreenshotBOId);
		String getLqMemberTaskScreeshotInfo=jedisStrings.get(getLqMemberTaskScreeshotKey);
		if(StringUtils.isNotBlank(getLqMemberTaskScreeshotInfo)){
			return JSON.parseObject(getLqMemberTaskScreeshotInfo, LqMemberTaskScreenshotBO.class);
		}
		return null;
	}
	
	
	/**
	 * 截图任务保存
	 */
	@Override
	public void insert(LqMemberTaskScreenshotBO lqMemberTaskScreenshotBO) {
		lqMemberTaskScreenshotBO.setCreatedTime(DateUtils.getDateTime());
		
		//缓存截图key
		String getLqMemberTaskScreeshotKey=JedisKeyUtils.getLqMemberTaskScreeshot;
		String lqMemberTaskScreenshotBOId=lqMemberTaskScreenshotBO.getMemberTaskId()+":"+lqMemberTaskScreenshotBO.getDisplayorder();
		getLqMemberTaskScreeshotKey=MessageFormat.format(getLqMemberTaskScreeshotKey ,lqMemberTaskScreenshotBOId);
		
		
		
		//缓存截图对象
		jedisStrings.set(getLqMemberTaskScreeshotKey, JSON.toJSONString(lqMemberTaskScreenshotBO));
		
		
		/*
		 * 截图列表缓存key
		 */
		String key=JedisKeyUtils.findLqMemberTaskScreeshotSortList+lqMemberTaskScreenshotBO.getMemberTaskId();
		jedisSortSet.zadd(key, lqMemberTaskScreenshotBO.getDisplayorder(), lqMemberTaskScreenshotBOId);
		
		//增加
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("insert lq_member_task_screenshot (member_task_id,screenshot,displayorder,created_time )");
		sqlBuf.append("values(");
			sqlBuf.append(lqMemberTaskScreenshotBO.getMemberTaskId()).append(",");
			sqlBuf.append("'").append(lqMemberTaskScreenshotBO.getScreenshot()).append("'").append(",");
			sqlBuf.append(lqMemberTaskScreenshotBO.getDisplayorder()).append(",");
			sqlBuf.append("'").append(lqMemberTaskScreenshotBO.getCreatedTime()).append("'");
		sqlBuf.append(")");
		String sql=sqlBuf.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, sql);
	}


	
	/**
	 * 截图任务删除
	 */
	@Override
	public void delete(String memberTaskId, String displayorder) {
		
		//缓存截图key
		String getLqMemberTaskScreeshotKey=JedisKeyUtils.getLqMemberTaskScreeshot;
		String lqMemberTaskScreenshotBOId=memberTaskId+":"+displayorder;
		getLqMemberTaskScreeshotKey=MessageFormat.format(getLqMemberTaskScreeshotKey ,lqMemberTaskScreenshotBOId);
		
		
		/*
		 * 截图列表缓存key
		 */
		String key=JedisKeyUtils.findLqMemberTaskScreeshotSortList+memberTaskId;
		 
		jedisSortSet.zrem(key, lqMemberTaskScreenshotBOId);
		
		
		
		//截图缓存删除
		jedisKeys.del(getLqMemberTaskScreeshotKey);
		
		
		
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("delete from lq_member_task_screenshot where member_task_id=").append(memberTaskId).append(" and ");
		sqlBuf.append(" displayorder =").append(displayorder);
		String sql=sqlBuf.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, sql);
		
		
	}


	/**
	 * 截图任务查询
	 */
	@Override
	public List<LqMemberTaskScreenshotBO> findList(String memberTaskId) {
		List<LqMemberTaskScreenshotBO>LqMemberTaskScreenshotList=new ArrayList<LqMemberTaskScreenshotBO>();
		/*
		 * 截图列表缓存key
		 */
		String key=JedisKeyUtils.findLqMemberTaskScreeshotSortList+memberTaskId;
		Set<String>lqMemberTaskScreenshotList=jedisSortSet.zrange(key, 0, -1);
		if(null!=lqMemberTaskScreenshotList){
			for(String lqMemberTaskScreenshotId:lqMemberTaskScreenshotList){
				LqMemberTaskScreenshotBO lqMemberTaskScreenshotBO=get(lqMemberTaskScreenshotId);
				if(null!=lqMemberTaskScreenshotBO){
					String screenshot=lqMemberTaskScreenshotBO.getScreenshot();
					lqMemberTaskScreenshotBO.setScreenshot(fileViewUrl+screenshot);//图片地址
					LqMemberTaskScreenshotList.add(lqMemberTaskScreenshotBO);
				}
			}
		}
		return LqMemberTaskScreenshotList;
	}

	
	/**
	 * 截图任务更新
	 */
	@Override
	public void update(String memberTaskId, String displayorder,String screenshot) {
		//缓存截图key
		String getLqMemberTaskScreeshotKey=JedisKeyUtils.getLqMemberTaskScreeshot;
		String lqMemberTaskScreenshotBOId=memberTaskId+":"+displayorder;
		getLqMemberTaskScreeshotKey=MessageFormat.format(getLqMemberTaskScreeshotKey ,lqMemberTaskScreenshotBOId);
		LqMemberTaskScreenshotBO lqMemberTaskScreenshotBO=get(lqMemberTaskScreenshotBOId);
		if(null!=lqMemberTaskScreenshotBO){
			
			lqMemberTaskScreenshotBO.setScreenshot(screenshot);
			//缓存截图对象
			jedisStrings.set(getLqMemberTaskScreeshotKey, JSON.toJSONString(lqMemberTaskScreenshotBO));
			
			//替换更新
			StringBuffer sqlBuf=new StringBuffer();
			sqlBuf.append("update lq_member_task_screenshot set screenshot='"+screenshot+"' where member_task_id=").append(memberTaskId);
			sqlBuf.append(" and displayorder=").append(displayorder);
			String sql=sqlBuf.toString();
			redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, sql);
			
		}
	}

}
