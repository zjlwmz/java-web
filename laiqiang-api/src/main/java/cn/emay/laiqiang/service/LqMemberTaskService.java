package cn.emay.laiqiang.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqMemberTaskBO;
import cn.emay.laiqiang.bo.LqTaskBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.DoubleUtil;
import cn.emay.laiqiang.common.utils.FormatUtils;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.LqTaskDTO;
import cn.emay.laiqiang.dto.TaskIncomeDTO;
import cn.emay.laiqiang.support.AuditStatus;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.support.MemberTaskStatus;
import cn.emay.laiqiang.support.TaskSignType;
import cn.emay.laiqiang.support.TaskType;

import com.alibaba.fastjson.JSON;

/**
 * 会员的赚钱任务service接口
 * 
 * @author zjlwm
 * @date 2016-12-01
 */
@Service
public class LqMemberTaskService extends BaseService{
	
	/**
	 * 写数据redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 任务service接口
	 */
	@Autowired
	private LqTaskService lqTaskService;
	
	
	
	/**
	 * 接口所在地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	
	/**
	 * 查询会员的赚钱任务
	 * @param memberId
	 * @param taskId
	 * @return
	 */
	public LqMemberTaskBO getLqMemberTask(Long memberId,String taskId) {
		String lqMemberTaskByMemberIdAndTaskIdKey=JedisKeyUtils.getLqMemberTaskByMemberIdAndTaskId;
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{0}", memberId+"");
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{1}", taskId+"");
		
		/**
		 * 会员赚钱任务key值
		 */
		String lqMemberTaskBOKey=jedisStrings.get(lqMemberTaskByMemberIdAndTaskIdKey);
		if(StringUtils.isNotBlank(lqMemberTaskBOKey)){
			return getLqMemberTask(lqMemberTaskBOKey);
		}
		return null;
	}
	
	
	/**
	 * 查询会员的赚钱任务
	 * @param memberId
	 * @param taskId
	 * @return
	 */
	public LqMemberTaskBO getLqMemberTask(String id) {
		String lqMemberTaskkey = JedisKeyUtils.getLqMemberTaskById +id;
		String lqMemberTaskInfo=jedisStrings.get(lqMemberTaskkey);
		if(StringUtils.isNoneBlank(lqMemberTaskInfo)){
			LqMemberTaskBO lqMemberTaskBO=JSON.parseObject(lqMemberTaskInfo, LqMemberTaskBO.class);
			return lqMemberTaskBO;
		}
		return null;
	}
	
	
	/**
	 * 会员赚钱任务保存
	 */
	public void insertLqMemberTask(LqMemberTaskBO lqMemberTaskBO){
		StringBuffer sqlBuff=new StringBuffer();
		Long memberId=lqMemberTaskBO.getMemberId();
		String taskId=lqMemberTaskBO.getTaskId();
		if(null!=getLqMemberTask(memberId, taskId))return;
		
		/**
		 * 生成会员赚钱任务【主键】
		 */
		Long lqMemberTaskId=jedisStrings.incrBy(JedisKeyUtils.lqMemberTaskIdKey, 1);
		lqMemberTaskBO.setId(lqMemberTaskId+"");
		
		//开始时间
		lqMemberTaskBO.setStartTime(DateUtils.getDateTime());
		
		
		sqlBuff.append("insert into lq_member_task(id,member_id,task_id,start_time,status,audit_status) values( ");
		sqlBuff.append(lqMemberTaskId).append(",");
		sqlBuff.append(memberId).append(",");
		sqlBuff.append(taskId).append(",");
		sqlBuff.append("'").append(lqMemberTaskBO.getStartTime()).append("'").append(",");
		sqlBuff.append(lqMemberTaskBO.getStatus()).append(",");
		sqlBuff.append(lqMemberTaskBO.getAuditStatus());
		sqlBuff.append(" )");
		
		String insertSql=sqlBuff.toString();
		
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertSql);
		
		/**
		 * 基础数据缓存
		 */
		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskId, JSON.toJSONString(lqMemberTaskBO));
		
		
		/**
		 * 业务数据
		 * 会员赚钱任务key值
		 *  member+task key
		 */
		String lqMemberTaskByMemberIdAndTaskIdKey=JedisKeyUtils.getLqMemberTaskByMemberIdAndTaskId;
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{0}", memberId+"");
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{1}", taskId+"");
		jedisStrings.set(lqMemberTaskByMemberIdAndTaskIdKey, String.valueOf(lqMemberTaskId));
		
		
		/**
		 * 业务数据缓存
		 * 添加到【当前赚任务】缓存中
		 */	
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findCurrentLqMemberTaskSortSetList+memberId;
		long zlength=jedisSortSet.zlength(memberMemberCurrentLqMemberTaskKey)+1;
		jedisSortSet.zadd(memberMemberCurrentLqMemberTaskKey, zlength, String.valueOf(lqMemberTaskId));
		
		
		/**
		 * 更新任务可申请剩余量
		 */
		lqTaskService.updateSurplusQuantity(taskId);
	}
	
	
	
	/**
	 * 任务分享
	 */
	public String shareMemberTaskInsert(LqMemberTaskBO lqMemberTaskBO){
		StringBuffer sqlBuff=new StringBuffer();
		Long memberId=lqMemberTaskBO.getMemberId();
		String taskId=lqMemberTaskBO.getTaskId();
		if(null!=getLqMemberTask(memberId, taskId))return null;
		
		/**
		 * 生成会员赚钱任务【主键】
		 */
		String lqMemberTaskId=jedisStrings.incrBy(JedisKeyUtils.lqMemberTaskIdKey, 1)+"";
		lqMemberTaskBO.setId(lqMemberTaskId);
		
		//开始时间
		lqMemberTaskBO.setStartTime(DateUtils.getDateTime());
		
		
		sqlBuff.append("insert into lq_member_task(id,member_id,task_id,start_time,status,audit_status) values( ");
		sqlBuff.append(""+lqMemberTaskId+","+memberId+","+taskId+",'"+lqMemberTaskBO.getStartTime()+"',"+lqMemberTaskBO.getStatus()+","+lqMemberTaskBO.getAuditStatus());
		sqlBuff.append(" )");
		
		String insertSql=sqlBuff.toString();
		
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertSql);
		
		/**
		 * 基础数据缓存
		 */
		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskId, JSON.toJSONString(lqMemberTaskBO));
		
		
		/**
		 * 业务数据
		 * 会员赚钱任务 member+task key
		 */
		String lqMemberTaskByMemberIdAndTaskIdKey=JedisKeyUtils.getLqMemberTaskByMemberIdAndTaskId;
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{0}", memberId+"");
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{1}", taskId+"");
		jedisStrings.set(lqMemberTaskByMemberIdAndTaskIdKey, String.valueOf(lqMemberTaskId));
		
		
		/**
		 * 业务数据缓存
		 * 添加到【历史赚任务】缓存中
		 */
		String memberMemberHistoryLqMemberTaskKey=JedisKeyUtils.findHistoryLqMemberTaskSortSetList+memberId;
		long zlength=jedisSortSet.zlength(memberMemberHistoryLqMemberTaskKey)+1;
		jedisSortSet.zadd(memberMemberHistoryLqMemberTaskKey, zlength, String.valueOf(lqMemberTaskId));
		
		
		return lqMemberTaskId;
	}
	
	
	/**
	 * 
	 * 截图赚钱任务 
	 * 会员赚钱任务取消
	 */
	public void taskCancelLqMemberTask(LqMemberTaskBO lqMemberTaskBO){
		lqMemberTaskBO.setStatus(MemberTaskStatus.HasBeenCanceled.intValue());
		Long memberId=lqMemberTaskBO.getMemberId();
		String lqMemberTaskId=lqMemberTaskBO.getId();
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("update lq_member_task set status="+lqMemberTaskBO.getStatus().intValue()+" where id="+lqMemberTaskBO.getId());
		String updateSql=sqlBuff.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
		
		lqMemberTaskBO.setTaskType(TaskType.screenshot+"");//截图任务
		/**
		 * 基础数据缓存
		 */
		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskBO.getId(), JSON.toJSONString(lqMemberTaskBO));
		
		//从当前任务缓存中山删除
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findCurrentLqMemberTaskSortSetList+memberId;
		jedisSortSet.zrem(memberMemberCurrentLqMemberTaskKey, String.valueOf(lqMemberTaskId));
		
		
		//添加到历史任务缓存中
		String findHistoryLqMemberTaskListKey=JedisKeyUtils.findHistoryLqMemberTaskSortSetList+memberId;
		long zlength=jedisSortSet.zlength(findHistoryLqMemberTaskListKey)+1;
		jedisSortSet.zadd(findHistoryLqMemberTaskListKey, zlength, String.valueOf(lqMemberTaskId));
		
	}
	
	/**
	 * 下载类型任务
	 * 任务安装成功
	 */
	public void taskInstallationComplete(LqMemberTaskBO lqMemberTaskBO){
		
		lqMemberTaskBO.setStatus(MemberTaskStatus.AuditSuccess.intValue());//已完成
		Long memberId=lqMemberTaskBO.getMemberId();
		String lqMemberTaskId=lqMemberTaskBO.getId();
		lqMemberTaskBO.setAuditTime(DateUtils.getDateTime());//会员项目完成时间
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("update lq_member_task set status="+lqMemberTaskBO.getStatus()+",audit_time='"+lqMemberTaskBO.getAuditTime()+"' where id="+lqMemberTaskBO.getId());
		String updateSql=sqlBuff.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
		
		
		/**
		 * 基础数据缓存
		 */
		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskBO.getId(), JSON.toJSONString(lqMemberTaskBO));
		
		
		//从当前任务缓存中山删除
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findCurrentLqMemberTaskSortSetList+memberId;
		jedisSortSet.zrem(memberMemberCurrentLqMemberTaskKey, String.valueOf(lqMemberTaskId));
		
		//添加到历史任务缓存中
		String findHistoryLqMemberTaskListKey=JedisKeyUtils.findHistoryLqMemberTaskSortSetList+memberId;
		long zlength=jedisSortSet.zlength(findHistoryLqMemberTaskListKey)+1;
		jedisSortSet.zadd(findHistoryLqMemberTaskListKey, zlength, String.valueOf(lqMemberTaskId));
		
		
		
		//添加到 签到任务缓存中
		String findSignLqMemberTaskListKey=JedisKeyUtils.findSignLqMemberTaskSetList+memberId;
		jedisSets.sadd(findSignLqMemberTaskListKey, lqMemberTaskId);
	}
	
	

	
	
	
//	/**
//	 * 会员赚钱任务更新截图地址
//	 */
//	public void updateImageLqMemberTask(LqMemberTaskBO lqMemberTaskBO){
//		StringBuffer sqlBuff=new StringBuffer();
//		String lqMemberTaskId=lqMemberTaskBO.getId();
//		
//		sqlBuff.append("update lq_member_task set screenshot='"+lqMemberTaskBO.getScreenshot()+"' where id="+lqMemberTaskId);
//		String updateSql=sqlBuff.toString();
//		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
//		
//		/**
//		 * 基础数据缓存
//		 */
//		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskId, JSON.toJSONString(lqMemberTaskBO));
//		
//	}
	
	/**
	 * 下载任务
	 * 会员赚钱任务删除
	 */
	public void deleteLqMemberTask(LqMemberTaskBO lqMemberTaskBO){
		
		StringBuffer sqlBuff=new StringBuffer();
		String lqMemberTaskId=lqMemberTaskBO.getId();
		
		Long memberId=lqMemberTaskBO.getMemberId();
		
		sqlBuff.append("delete from lq_member_task where id="+lqMemberTaskId+"");
		
		String deleteSql=sqlBuff.toString();
		
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, deleteSql);
		
		/**
		 * 基础数据缓存删除
		 */
		String getLqMemberTaskByIdKey=JedisKeyUtils.getLqMemberTaskById;
		
		
		/** 业务数据 删除
		 *  会员赚钱任务key值
		 *  member+task key
		 */
		String lqMemberTaskByMemberIdAndTaskIdKey=JedisKeyUtils.getLqMemberTaskByMemberIdAndTaskId;
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{0}", memberId+"");
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{1}", lqMemberTaskBO.getTaskId()+"");
		jedisKeys.del(getLqMemberTaskByIdKey,lqMemberTaskByMemberIdAndTaskIdKey);
		
		
		
		
		/**
		 * 业务数据缓存
		 * 从【当前赚任务】缓存中删除
		 */
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findCurrentLqMemberTaskSortSetList+memberId;
		jedisSortSet.zrem(memberMemberCurrentLqMemberTaskKey, lqMemberTaskId);
	
	
		
		
	}
	

	
	
	/**
	 * 会员当前任务列表
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public List<LqTaskDTO>findCurrentTask2(Long memberId,long start,long end) throws IllegalAccessException, InvocationTargetException{
		/**
		 * 会员当前赚钱任务集合
		 */
		List<LqTaskDTO> lqTaskDTOList=new ArrayList<LqTaskDTO>();
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findCurrentLqMemberTaskSortSetList+memberId;
		Set<String>currentLqMemberTaskList=jedisSortSet.zrevrange(memberMemberCurrentLqMemberTaskKey, start, end);
		if(null!=currentLqMemberTaskList){
			for(String lqMemberTaskId:currentLqMemberTaskList){
				String getLqMemberTaskKey=JedisKeyUtils.getLqMemberTaskById+lqMemberTaskId;
				
				String lqMemberTaskInfo=jedisStrings.get(getLqMemberTaskKey);
				LqMemberTaskBO lqMemberTaskBO=JSON.parseObject(lqMemberTaskInfo, LqMemberTaskBO.class);
				String taskId=lqMemberTaskBO.getTaskId();
				
				String lqTaskKey=JedisKeyUtils.getLqTaskById+taskId;
				String lqTaskInfo=jedisStrings.get(lqTaskKey);
				LqTaskBO lqTaskBO=JSON.parseObject(lqTaskInfo, LqTaskBO.class);
				
				/**
				 * 会员任务状态
				 */
				int status=lqMemberTaskBO.getStatus();
				/**
				 * 拷贝任务信息
				 */
				LqTaskDTO lqTaskDTO=new LqTaskDTO();
				BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
				
				
				/**
				 * 拷贝会员任务信息
				 */
				lqTaskDTO.setStatus(status);
				lqTaskDTO.setAuditStatus(lqMemberTaskBO.getAuditStatus());
				
				/**
				 * 设置taskId
				 */
				lqTaskDTO.setTaskId(lqTaskBO.getId());
				if(status==MemberTaskStatus.Work.intValue()){
					//任务完成期限
					Integer taskDuration=lqTaskBO.getTaskDuration();//分钟
					if(taskDuration>0){
						//申请时间
						Date startTime=DateUtils.parseDate(lqMemberTaskBO.getStartTime());
						
						//当前时间
						Date currentDate=new Date();
						//赚钱任务未过期
						long residualTime=(startTime.getTime()+taskDuration*60*1000)-currentDate.getTime();
						if(residualTime>0){
							/**
							 * 剩余时间设置
							 */
							lqTaskDTO.setResidualTime(residualTime/1000);//秒
							
							lqTaskDTOList.add(lqTaskDTO);
						}
						//赚钱任务已过期
						else{
							//任务类型
							Integer taskType=lqTaskDTO.getTaskType();
							if(taskType.intValue()==TaskType.screenshot){//截图任务过期--更新状态为已取消
								taskCancelLqMemberTask(lqMemberTaskBO);
							}else if(taskType.intValue()==TaskType.download){//下载类任务过期--删除该条记录
								deleteLqMemberTask(lqMemberTaskBO);
							}
						}
					}
				}else if(status==MemberTaskStatus.hasSubmitted){
					lqTaskDTOList.add(lqTaskDTO);
				}
				
			}
		}
		
		return lqTaskDTOList;
	}
	
	
	
	
	public List<LqTaskDTO>findCurrentTask(Long memberId,long start,long currentPage,long pageSize) throws IllegalAccessException, InvocationTargetException{
		/**
		 * 会员当前赚钱任务集合
		 */
		List<LqTaskDTO> lqTaskDTOList=new ArrayList<LqTaskDTO>();
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findCurrentLqMemberTaskSortSetList+memberId;
		Set<String>currentLqMemberTaskList=jedisSortSet.zrevrange(memberMemberCurrentLqMemberTaskKey, start, -1);
		if(null!=currentLqMemberTaskList){
			for(String lqMemberTaskId:currentLqMemberTaskList){
				if(lqTaskDTOList.size()==pageSize)break;
				
				String getLqMemberTaskKey=JedisKeyUtils.getLqMemberTaskById+lqMemberTaskId;
				String lqMemberTaskInfo=jedisStrings.get(getLqMemberTaskKey);
				LqMemberTaskBO lqMemberTaskBO=JSON.parseObject(lqMemberTaskInfo, LqMemberTaskBO.class);
				String taskId=lqMemberTaskBO.getTaskId();
				
				String lqTaskKey=JedisKeyUtils.getLqTaskById+taskId;
				String lqTaskInfo=jedisStrings.get(lqTaskKey);
				LqTaskBO lqTaskBO=JSON.parseObject(lqTaskInfo, LqTaskBO.class);
				
				/**
				 * 会员任务状态
				 */
				int status=lqMemberTaskBO.getStatus();
				/**
				 * 拷贝任务信息
				 */
				LqTaskDTO lqTaskDTO=new LqTaskDTO();
				BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
				
				
				/**
				 * 拷贝会员任务信息
				 */
				lqTaskDTO.setStatus(status);
				lqTaskDTO.setAuditStatus(lqMemberTaskBO.getAuditStatus());
				
				/**
				 * 设置taskId
				 */
				lqTaskDTO.setTaskId(lqTaskBO.getId());
				if(status==MemberTaskStatus.Work.intValue()){
					//任务完成期限
					Integer taskDuration=lqTaskBO.getTaskDuration();//分钟
					if(taskDuration>0){
						//申请时间
						Date startTime=DateUtils.parseDate(lqMemberTaskBO.getStartTime());
						
						//当前时间
						Date currentDate=new Date();
						//赚钱任务未过期
						long residualTime=(startTime.getTime()+taskDuration*60*1000)-currentDate.getTime();
						if(residualTime>0){
							/**
							 * 剩余时间设置
							 */
							lqTaskDTO.setResidualTime(residualTime/1000);//秒
							
							lqTaskDTOList.add(lqTaskDTO);
						}
						//赚钱任务已过期
						else{
							//任务类型
							Integer taskType=lqTaskDTO.getTaskType();
							if(taskType.intValue()==TaskType.screenshot){//截图任务过期--更新状态为已取消
								taskCancelLqMemberTask(lqMemberTaskBO);
							}else if(taskType.intValue()==TaskType.download){//下载类任务过期--删除该条记录
								deleteLqMemberTask(lqMemberTaskBO);
							}
						}
					}
				}else if(status==MemberTaskStatus.hasSubmitted){
					lqTaskDTOList.add(lqTaskDTO);
				}
				
			}
		}
		
		return lqTaskDTOList;
	}
	
	
	
	
	/**
	 * 
	 * 历史 任务
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * 
	 */
	public List<LqTaskDTO>findHistoryTaskList(Long memberId,long start,long end) throws IllegalAccessException, InvocationTargetException{
		/**
		 * 会员当前赚钱任务集合
		 */
		List<LqTaskDTO> lqTaskDTOList=new ArrayList<LqTaskDTO>();
		
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findHistoryLqMemberTaskSortSetList+memberId;
		
		Set<String>currentLqMemberTaskList=jedisSortSet.zrevrange(memberMemberCurrentLqMemberTaskKey, start, end);
		if(null!=currentLqMemberTaskList){
			for(String lqMemberTaskId:currentLqMemberTaskList){
				String getLqMemberTaskKey=JedisKeyUtils.getLqMemberTaskById+lqMemberTaskId;
				
				String lqMemberTaskInfo=jedisStrings.get(getLqMemberTaskKey);
				if(StringUtils.isNotBlank(lqMemberTaskInfo)){
					LqMemberTaskBO lqMemberTaskBO=JSON.parseObject(lqMemberTaskInfo, LqMemberTaskBO.class);
					String taskId=lqMemberTaskBO.getTaskId();
					
					String lqTaskKey=JedisKeyUtils.getLqTaskById+taskId;
					String lqTaskInfo=jedisStrings.get(lqTaskKey);
					LqTaskBO lqTaskBO=JSON.parseObject(lqTaskInfo, LqTaskBO.class);
					
					LqTaskDTO lqTaskDTO=new LqTaskDTO();
					
					BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
					/**
					 * 设置taskId
					 */
					lqTaskDTO.setTaskId(lqTaskBO.getId());
					/**
					 * 会员任务状态
					 */
					lqTaskDTO.setStatus(lqMemberTaskBO.getStatus());
					
					/**
					 * 任务总获利
					 */
					Double totalReward=lqMemberTaskBO.getTotalReward();
					lqTaskDTO.setTotalReward(totalReward);
					
					
					int taskType=lqTaskBO.getTaskType();
					if(taskType==TaskType.share){//分享任务
						/**
						 * 分享任务 连接地址
						 */
						String shareLink=lqTaskBO.getShareLink();
						if(StringUtils.isBlank(shareLink)){
							shareLink=domain+"laiqiang/app/task/html/app/share/"+taskId;
						}
						lqTaskDTO.setShareUrl(shareLink);
					}
					
					
					lqTaskDTOList.add(lqTaskDTO);
				}
				
			}
		}
		
		return lqTaskDTOList;
	}
	
	
	
	
	/**
	 * 获取可以签到的会员赚钱任务
	 * @throws Exception 
	 * 
	 */
	public Map<String,Object> findSignLqMemberTask(String type,Long memberId) throws Exception{
		Map<String,Object> resultData=new HashMap<String, Object>();
		List<TaskIncomeDTO>taskIncomeDTOList=new ArrayList<TaskIncomeDTO>();
		
		String findSignLqMemberTaskListKey=JedisKeyUtils.findSignLqMemberTaskSetList+memberId;
		Set<String> lqMemberTaskListInfo=jedisSets.smembers(findSignLqMemberTaskListKey);
		if(null!=lqMemberTaskListInfo){
			for(String lqMemberTaskId:lqMemberTaskListInfo){
				LqMemberTaskBO lqMemberTaskBO=getLqMemberTask(lqMemberTaskId);
				if(null!=lqMemberTaskBO){
					LqTaskBO lqTaskBO=lqTaskService.get(lqMemberTaskBO.getTaskId());
					if(null!=lqTaskBO){
						
						/**
						 * 有效签到期限
						 */
						int signinDays=lqTaskBO.getSigninDays();
						
						/**
						 * 完成时间
						 */
						String auditTime=lqMemberTaskBO.getAuditTime();
						
						String currentDate=DateUtils.getDate();
						
						//明天
						if(type.equals(TaskSignType.tomorrow)){
							Date newDate=DateUtils.getDateAfter(new Date(), 1);
							currentDate=DateUtils.formatDate(newDate, "yyyy-MM-dd");
						}
						/**
						 * 在  有效签到期限(下载后多少天内) 内
						 */
						auditTime=auditTime.split(" ")[0];
						long distanceDay=DateUtils.getDistanceDays(auditTime, currentDate);
						
						if(distanceDay<=signinDays && distanceDay>0){//有效签到期限(下载后多少天内) 并且  过滤当天完成的下载任务
							TaskIncomeDTO taskIncomeDTO=new TaskIncomeDTO(); 
							BeanUtils.copyProperties(taskIncomeDTO, lqTaskBO);
							taskIncomeDTO.setTaskId(Long.parseLong(lqTaskBO.getId()));
							
							String today=currentDate.replaceAll("-", "");
							taskIncomeDTO.setRewardQuantity(lqTaskBO.getSigninReward());//签到奖励
							
							boolean issign=isTaskTodaySign(memberId, taskIncomeDTO.getTaskId()+"", today);
							if(issign){
								taskIncomeDTO.setIsSign("1");//已签到
							}
							
							taskIncomeDTOList.add(taskIncomeDTO);
						}
						
						//超过有效签到期限
						if(type.equals(TaskSignType.today)){
							if(distanceDay>signinDays){
								jedisSets.srem(findSignLqMemberTaskListKey, lqMemberTaskId);//过期则删除
							}
						}
						
					}
				}
				
			}
		}
		
		resultData.put("taskIncomeDTOList", taskIncomeDTOList);
		
		resultData.put("totalMoney", 0.0d);//不需要统计、字段不去掉
		resultData.put("totalQuantity", 0.0d);//不需要统计、字段不去掉
		
		
		return resultData;
	}
	
	
	
	
	
	
	/**
	 * 更新赚取奖励总额
	 */
	public void updateTotalReward(String lqMemberTaskId,double num){
		LqMemberTaskBO lqMemberTaskBO=getLqMemberTask(lqMemberTaskId);
		if(lqMemberTaskBO==null){
			return;
		}
		
		Double totalReward=lqMemberTaskBO.getTotalReward();
		if(null==totalReward){
			lqMemberTaskBO.setTotalReward(num);//更新任务赚钱奖励
		}else{
			//金额汇总
			Double totalRewardNew=DoubleUtil.add(totalReward, num);
			//将double类型的数字保留两位小数（四舍五入）
			String totalRewardNewStr=FormatUtils.formatNumber(totalRewardNew);
			double totalRewardNew2=Double.parseDouble(totalRewardNewStr);
			
			lqMemberTaskBO.setTotalReward(totalRewardNew2);
		}
		
		/**
		 * 基础数据缓存
		 */
		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskId, JSON.toJSONString(lqMemberTaskBO));
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append(" update lq_member_task set ");
		if(null==totalReward){
			sqlBuff.append(" total_reward="+num);
		}else{
			sqlBuff.append(" total_reward=total_reward+"+num);
		}
		
		sqlBuff.append(" where id=").append(lqMemberTaskId);
		String updateSql=sqlBuff.toString();
		/**
		 * 
		 */
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
	}


	/**
	 * 截图任务提交
	 */
	public void screenshotTaskSubmit(LqMemberTaskBO lqMemberTaskBO) {
		String lqMemberTaskkey = JedisKeyUtils.getLqMemberTaskById +lqMemberTaskBO.getId();
		
		lqMemberTaskBO.setStatus(MemberTaskStatus.hasSubmitted.intValue());
		lqMemberTaskBO.setAuditStatus(AuditStatus.WaitAudit);
		lqMemberTaskBO.setSubmitTime(DateUtils.getDateTime());
		jedisStrings.set(lqMemberTaskkey, JSON.toJSONString(lqMemberTaskBO));
		
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append(" update lq_member_task set status="+lqMemberTaskBO.getStatus());
		sqlBuff.append(" ,audit_status ="+lqMemberTaskBO.getAuditStatus());
		sqlBuff.append(" ,submit_time='").append(lqMemberTaskBO.getSubmitTime()).append("'");
		sqlBuff.append(" where id=").append(lqMemberTaskBO.getId());
		
		String updateSql=sqlBuff.toString();
		/**
		 * 截图任务提交 sql
		 */
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
		
		
		
	}
	
	
	
	
	
	
	/**
	 * 签到任务 今日已签到
	 * @param today yyyMMdd  20170117
	 */
	public void taskTodaySign(long memberId,String taskId,String today){
		String findAlreadySignLqMemberTaskSetListKey=JedisKeyUtils.findAlreadySignLqMemberTaskSetList+memberId;
		jedisSets.sadd(findAlreadySignLqMemberTaskSetListKey, taskId+":"+today);
	}
	
	
	/**
	 * 签到任务 今日是否已签到
	 * today yyyMMdd  20170117
	 */
	public boolean isTaskTodaySign(long memberId,String taskId,String today){
		String findAlreadySignLqMemberTaskSetListKey=JedisKeyUtils.findAlreadySignLqMemberTaskSetList+memberId;
		return jedisSets.sismember(findAlreadySignLqMemberTaskSetListKey, taskId+":"+today);
	}
	
	
}
