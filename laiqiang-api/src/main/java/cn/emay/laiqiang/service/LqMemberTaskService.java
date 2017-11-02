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
 * ��Ա��׬Ǯ����service�ӿ�
 * 
 * @author zjlwm
 * @date 2016-12-01
 */
@Service
public class LqMemberTaskService extends BaseService{
	
	/**
	 * д����redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private LqTaskService lqTaskService;
	
	
	
	/**
	 * �ӿ����ڵ�ַ
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	
	/**
	 * ��ѯ��Ա��׬Ǯ����
	 * @param memberId
	 * @param taskId
	 * @return
	 */
	public LqMemberTaskBO getLqMemberTask(Long memberId,String taskId) {
		String lqMemberTaskByMemberIdAndTaskIdKey=JedisKeyUtils.getLqMemberTaskByMemberIdAndTaskId;
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{0}", memberId+"");
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{1}", taskId+"");
		
		/**
		 * ��Ա׬Ǯ����keyֵ
		 */
		String lqMemberTaskBOKey=jedisStrings.get(lqMemberTaskByMemberIdAndTaskIdKey);
		if(StringUtils.isNotBlank(lqMemberTaskBOKey)){
			return getLqMemberTask(lqMemberTaskBOKey);
		}
		return null;
	}
	
	
	/**
	 * ��ѯ��Ա��׬Ǯ����
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
	 * ��Ա׬Ǯ���񱣴�
	 */
	public void insertLqMemberTask(LqMemberTaskBO lqMemberTaskBO){
		StringBuffer sqlBuff=new StringBuffer();
		Long memberId=lqMemberTaskBO.getMemberId();
		String taskId=lqMemberTaskBO.getTaskId();
		if(null!=getLqMemberTask(memberId, taskId))return;
		
		/**
		 * ���ɻ�Ա׬Ǯ����������
		 */
		Long lqMemberTaskId=jedisStrings.incrBy(JedisKeyUtils.lqMemberTaskIdKey, 1);
		lqMemberTaskBO.setId(lqMemberTaskId+"");
		
		//��ʼʱ��
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
		 * �������ݻ���
		 */
		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskId, JSON.toJSONString(lqMemberTaskBO));
		
		
		/**
		 * ҵ������
		 * ��Ա׬Ǯ����keyֵ
		 *  member+task key
		 */
		String lqMemberTaskByMemberIdAndTaskIdKey=JedisKeyUtils.getLqMemberTaskByMemberIdAndTaskId;
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{0}", memberId+"");
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{1}", taskId+"");
		jedisStrings.set(lqMemberTaskByMemberIdAndTaskIdKey, String.valueOf(lqMemberTaskId));
		
		
		/**
		 * ҵ�����ݻ���
		 * ��ӵ�����ǰ׬���񡿻�����
		 */	
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findCurrentLqMemberTaskSortSetList+memberId;
		long zlength=jedisSortSet.zlength(memberMemberCurrentLqMemberTaskKey)+1;
		jedisSortSet.zadd(memberMemberCurrentLqMemberTaskKey, zlength, String.valueOf(lqMemberTaskId));
		
		
		/**
		 * �������������ʣ����
		 */
		lqTaskService.updateSurplusQuantity(taskId);
	}
	
	
	
	/**
	 * �������
	 */
	public String shareMemberTaskInsert(LqMemberTaskBO lqMemberTaskBO){
		StringBuffer sqlBuff=new StringBuffer();
		Long memberId=lqMemberTaskBO.getMemberId();
		String taskId=lqMemberTaskBO.getTaskId();
		if(null!=getLqMemberTask(memberId, taskId))return null;
		
		/**
		 * ���ɻ�Ա׬Ǯ����������
		 */
		String lqMemberTaskId=jedisStrings.incrBy(JedisKeyUtils.lqMemberTaskIdKey, 1)+"";
		lqMemberTaskBO.setId(lqMemberTaskId);
		
		//��ʼʱ��
		lqMemberTaskBO.setStartTime(DateUtils.getDateTime());
		
		
		sqlBuff.append("insert into lq_member_task(id,member_id,task_id,start_time,status,audit_status) values( ");
		sqlBuff.append(""+lqMemberTaskId+","+memberId+","+taskId+",'"+lqMemberTaskBO.getStartTime()+"',"+lqMemberTaskBO.getStatus()+","+lqMemberTaskBO.getAuditStatus());
		sqlBuff.append(" )");
		
		String insertSql=sqlBuff.toString();
		
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertSql);
		
		/**
		 * �������ݻ���
		 */
		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskId, JSON.toJSONString(lqMemberTaskBO));
		
		
		/**
		 * ҵ������
		 * ��Ա׬Ǯ���� member+task key
		 */
		String lqMemberTaskByMemberIdAndTaskIdKey=JedisKeyUtils.getLqMemberTaskByMemberIdAndTaskId;
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{0}", memberId+"");
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{1}", taskId+"");
		jedisStrings.set(lqMemberTaskByMemberIdAndTaskIdKey, String.valueOf(lqMemberTaskId));
		
		
		/**
		 * ҵ�����ݻ���
		 * ��ӵ�����ʷ׬���񡿻�����
		 */
		String memberMemberHistoryLqMemberTaskKey=JedisKeyUtils.findHistoryLqMemberTaskSortSetList+memberId;
		long zlength=jedisSortSet.zlength(memberMemberHistoryLqMemberTaskKey)+1;
		jedisSortSet.zadd(memberMemberHistoryLqMemberTaskKey, zlength, String.valueOf(lqMemberTaskId));
		
		
		return lqMemberTaskId;
	}
	
	
	/**
	 * 
	 * ��ͼ׬Ǯ���� 
	 * ��Ա׬Ǯ����ȡ��
	 */
	public void taskCancelLqMemberTask(LqMemberTaskBO lqMemberTaskBO){
		lqMemberTaskBO.setStatus(MemberTaskStatus.HasBeenCanceled.intValue());
		Long memberId=lqMemberTaskBO.getMemberId();
		String lqMemberTaskId=lqMemberTaskBO.getId();
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("update lq_member_task set status="+lqMemberTaskBO.getStatus().intValue()+" where id="+lqMemberTaskBO.getId());
		String updateSql=sqlBuff.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
		
		lqMemberTaskBO.setTaskType(TaskType.screenshot+"");//��ͼ����
		/**
		 * �������ݻ���
		 */
		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskBO.getId(), JSON.toJSONString(lqMemberTaskBO));
		
		//�ӵ�ǰ���񻺴���ɽɾ��
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findCurrentLqMemberTaskSortSetList+memberId;
		jedisSortSet.zrem(memberMemberCurrentLqMemberTaskKey, String.valueOf(lqMemberTaskId));
		
		
		//��ӵ���ʷ���񻺴���
		String findHistoryLqMemberTaskListKey=JedisKeyUtils.findHistoryLqMemberTaskSortSetList+memberId;
		long zlength=jedisSortSet.zlength(findHistoryLqMemberTaskListKey)+1;
		jedisSortSet.zadd(findHistoryLqMemberTaskListKey, zlength, String.valueOf(lqMemberTaskId));
		
	}
	
	/**
	 * ������������
	 * ����װ�ɹ�
	 */
	public void taskInstallationComplete(LqMemberTaskBO lqMemberTaskBO){
		
		lqMemberTaskBO.setStatus(MemberTaskStatus.AuditSuccess.intValue());//�����
		Long memberId=lqMemberTaskBO.getMemberId();
		String lqMemberTaskId=lqMemberTaskBO.getId();
		lqMemberTaskBO.setAuditTime(DateUtils.getDateTime());//��Ա��Ŀ���ʱ��
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("update lq_member_task set status="+lqMemberTaskBO.getStatus()+",audit_time='"+lqMemberTaskBO.getAuditTime()+"' where id="+lqMemberTaskBO.getId());
		String updateSql=sqlBuff.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
		
		
		/**
		 * �������ݻ���
		 */
		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskBO.getId(), JSON.toJSONString(lqMemberTaskBO));
		
		
		//�ӵ�ǰ���񻺴���ɽɾ��
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findCurrentLqMemberTaskSortSetList+memberId;
		jedisSortSet.zrem(memberMemberCurrentLqMemberTaskKey, String.valueOf(lqMemberTaskId));
		
		//��ӵ���ʷ���񻺴���
		String findHistoryLqMemberTaskListKey=JedisKeyUtils.findHistoryLqMemberTaskSortSetList+memberId;
		long zlength=jedisSortSet.zlength(findHistoryLqMemberTaskListKey)+1;
		jedisSortSet.zadd(findHistoryLqMemberTaskListKey, zlength, String.valueOf(lqMemberTaskId));
		
		
		
		//��ӵ� ǩ�����񻺴���
		String findSignLqMemberTaskListKey=JedisKeyUtils.findSignLqMemberTaskSetList+memberId;
		jedisSets.sadd(findSignLqMemberTaskListKey, lqMemberTaskId);
	}
	
	

	
	
	
//	/**
//	 * ��Ա׬Ǯ������½�ͼ��ַ
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
//		 * �������ݻ���
//		 */
//		jedisStrings.set(JedisKeyUtils.getLqMemberTaskById+lqMemberTaskId, JSON.toJSONString(lqMemberTaskBO));
//		
//	}
	
	/**
	 * ��������
	 * ��Ա׬Ǯ����ɾ��
	 */
	public void deleteLqMemberTask(LqMemberTaskBO lqMemberTaskBO){
		
		StringBuffer sqlBuff=new StringBuffer();
		String lqMemberTaskId=lqMemberTaskBO.getId();
		
		Long memberId=lqMemberTaskBO.getMemberId();
		
		sqlBuff.append("delete from lq_member_task where id="+lqMemberTaskId+"");
		
		String deleteSql=sqlBuff.toString();
		
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, deleteSql);
		
		/**
		 * �������ݻ���ɾ��
		 */
		String getLqMemberTaskByIdKey=JedisKeyUtils.getLqMemberTaskById;
		
		
		/** ҵ������ ɾ��
		 *  ��Ա׬Ǯ����keyֵ
		 *  member+task key
		 */
		String lqMemberTaskByMemberIdAndTaskIdKey=JedisKeyUtils.getLqMemberTaskByMemberIdAndTaskId;
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{0}", memberId+"");
		lqMemberTaskByMemberIdAndTaskIdKey=lqMemberTaskByMemberIdAndTaskIdKey.replace("{1}", lqMemberTaskBO.getTaskId()+"");
		jedisKeys.del(getLqMemberTaskByIdKey,lqMemberTaskByMemberIdAndTaskIdKey);
		
		
		
		
		/**
		 * ҵ�����ݻ���
		 * �ӡ���ǰ׬���񡿻�����ɾ��
		 */
		String memberMemberCurrentLqMemberTaskKey=JedisKeyUtils.findCurrentLqMemberTaskSortSetList+memberId;
		jedisSortSet.zrem(memberMemberCurrentLqMemberTaskKey, lqMemberTaskId);
	
	
		
		
	}
	

	
	
	/**
	 * ��Ա��ǰ�����б�
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public List<LqTaskDTO>findCurrentTask2(Long memberId,long start,long end) throws IllegalAccessException, InvocationTargetException{
		/**
		 * ��Ա��ǰ׬Ǯ���񼯺�
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
				 * ��Ա����״̬
				 */
				int status=lqMemberTaskBO.getStatus();
				/**
				 * ����������Ϣ
				 */
				LqTaskDTO lqTaskDTO=new LqTaskDTO();
				BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
				
				
				/**
				 * ������Ա������Ϣ
				 */
				lqTaskDTO.setStatus(status);
				lqTaskDTO.setAuditStatus(lqMemberTaskBO.getAuditStatus());
				
				/**
				 * ����taskId
				 */
				lqTaskDTO.setTaskId(lqTaskBO.getId());
				if(status==MemberTaskStatus.Work.intValue()){
					//�����������
					Integer taskDuration=lqTaskBO.getTaskDuration();//����
					if(taskDuration>0){
						//����ʱ��
						Date startTime=DateUtils.parseDate(lqMemberTaskBO.getStartTime());
						
						//��ǰʱ��
						Date currentDate=new Date();
						//׬Ǯ����δ����
						long residualTime=(startTime.getTime()+taskDuration*60*1000)-currentDate.getTime();
						if(residualTime>0){
							/**
							 * ʣ��ʱ������
							 */
							lqTaskDTO.setResidualTime(residualTime/1000);//��
							
							lqTaskDTOList.add(lqTaskDTO);
						}
						//׬Ǯ�����ѹ���
						else{
							//��������
							Integer taskType=lqTaskDTO.getTaskType();
							if(taskType.intValue()==TaskType.screenshot){//��ͼ�������--����״̬Ϊ��ȡ��
								taskCancelLqMemberTask(lqMemberTaskBO);
							}else if(taskType.intValue()==TaskType.download){//�������������--ɾ��������¼
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
		 * ��Ա��ǰ׬Ǯ���񼯺�
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
				 * ��Ա����״̬
				 */
				int status=lqMemberTaskBO.getStatus();
				/**
				 * ����������Ϣ
				 */
				LqTaskDTO lqTaskDTO=new LqTaskDTO();
				BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
				
				
				/**
				 * ������Ա������Ϣ
				 */
				lqTaskDTO.setStatus(status);
				lqTaskDTO.setAuditStatus(lqMemberTaskBO.getAuditStatus());
				
				/**
				 * ����taskId
				 */
				lqTaskDTO.setTaskId(lqTaskBO.getId());
				if(status==MemberTaskStatus.Work.intValue()){
					//�����������
					Integer taskDuration=lqTaskBO.getTaskDuration();//����
					if(taskDuration>0){
						//����ʱ��
						Date startTime=DateUtils.parseDate(lqMemberTaskBO.getStartTime());
						
						//��ǰʱ��
						Date currentDate=new Date();
						//׬Ǯ����δ����
						long residualTime=(startTime.getTime()+taskDuration*60*1000)-currentDate.getTime();
						if(residualTime>0){
							/**
							 * ʣ��ʱ������
							 */
							lqTaskDTO.setResidualTime(residualTime/1000);//��
							
							lqTaskDTOList.add(lqTaskDTO);
						}
						//׬Ǯ�����ѹ���
						else{
							//��������
							Integer taskType=lqTaskDTO.getTaskType();
							if(taskType.intValue()==TaskType.screenshot){//��ͼ�������--����״̬Ϊ��ȡ��
								taskCancelLqMemberTask(lqMemberTaskBO);
							}else if(taskType.intValue()==TaskType.download){//�������������--ɾ��������¼
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
	 * ��ʷ ����
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * 
	 */
	public List<LqTaskDTO>findHistoryTaskList(Long memberId,long start,long end) throws IllegalAccessException, InvocationTargetException{
		/**
		 * ��Ա��ǰ׬Ǯ���񼯺�
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
					 * ����taskId
					 */
					lqTaskDTO.setTaskId(lqTaskBO.getId());
					/**
					 * ��Ա����״̬
					 */
					lqTaskDTO.setStatus(lqMemberTaskBO.getStatus());
					
					/**
					 * �����ܻ���
					 */
					Double totalReward=lqMemberTaskBO.getTotalReward();
					lqTaskDTO.setTotalReward(totalReward);
					
					
					int taskType=lqTaskBO.getTaskType();
					if(taskType==TaskType.share){//��������
						/**
						 * �������� ���ӵ�ַ
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
	 * ��ȡ����ǩ���Ļ�Ա׬Ǯ����
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
						 * ��Чǩ������
						 */
						int signinDays=lqTaskBO.getSigninDays();
						
						/**
						 * ���ʱ��
						 */
						String auditTime=lqMemberTaskBO.getAuditTime();
						
						String currentDate=DateUtils.getDate();
						
						//����
						if(type.equals(TaskSignType.tomorrow)){
							Date newDate=DateUtils.getDateAfter(new Date(), 1);
							currentDate=DateUtils.formatDate(newDate, "yyyy-MM-dd");
						}
						/**
						 * ��  ��Чǩ������(���غ��������) ��
						 */
						auditTime=auditTime.split(" ")[0];
						long distanceDay=DateUtils.getDistanceDays(auditTime, currentDate);
						
						if(distanceDay<=signinDays && distanceDay>0){//��Чǩ������(���غ��������) ����  ���˵�����ɵ���������
							TaskIncomeDTO taskIncomeDTO=new TaskIncomeDTO(); 
							BeanUtils.copyProperties(taskIncomeDTO, lqTaskBO);
							taskIncomeDTO.setTaskId(Long.parseLong(lqTaskBO.getId()));
							
							String today=currentDate.replaceAll("-", "");
							taskIncomeDTO.setRewardQuantity(lqTaskBO.getSigninReward());//ǩ������
							
							boolean issign=isTaskTodaySign(memberId, taskIncomeDTO.getTaskId()+"", today);
							if(issign){
								taskIncomeDTO.setIsSign("1");//��ǩ��
							}
							
							taskIncomeDTOList.add(taskIncomeDTO);
						}
						
						//������Чǩ������
						if(type.equals(TaskSignType.today)){
							if(distanceDay>signinDays){
								jedisSets.srem(findSignLqMemberTaskListKey, lqMemberTaskId);//������ɾ��
							}
						}
						
					}
				}
				
			}
		}
		
		resultData.put("taskIncomeDTOList", taskIncomeDTOList);
		
		resultData.put("totalMoney", 0.0d);//����Ҫͳ�ơ��ֶβ�ȥ��
		resultData.put("totalQuantity", 0.0d);//����Ҫͳ�ơ��ֶβ�ȥ��
		
		
		return resultData;
	}
	
	
	
	
	
	
	/**
	 * ����׬ȡ�����ܶ�
	 */
	public void updateTotalReward(String lqMemberTaskId,double num){
		LqMemberTaskBO lqMemberTaskBO=getLqMemberTask(lqMemberTaskId);
		if(lqMemberTaskBO==null){
			return;
		}
		
		Double totalReward=lqMemberTaskBO.getTotalReward();
		if(null==totalReward){
			lqMemberTaskBO.setTotalReward(num);//��������׬Ǯ����
		}else{
			//������
			Double totalRewardNew=DoubleUtil.add(totalReward, num);
			//��double���͵����ֱ�����λС�����������룩
			String totalRewardNewStr=FormatUtils.formatNumber(totalRewardNew);
			double totalRewardNew2=Double.parseDouble(totalRewardNewStr);
			
			lqMemberTaskBO.setTotalReward(totalRewardNew2);
		}
		
		/**
		 * �������ݻ���
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
	 * ��ͼ�����ύ
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
		 * ��ͼ�����ύ sql
		 */
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
		
		
		
	}
	
	
	
	
	
	
	/**
	 * ǩ������ ������ǩ��
	 * @param today yyyMMdd  20170117
	 */
	public void taskTodaySign(long memberId,String taskId,String today){
		String findAlreadySignLqMemberTaskSetListKey=JedisKeyUtils.findAlreadySignLqMemberTaskSetList+memberId;
		jedisSets.sadd(findAlreadySignLqMemberTaskSetListKey, taskId+":"+today);
	}
	
	
	/**
	 * ǩ������ �����Ƿ���ǩ��
	 * today yyyMMdd  20170117
	 */
	public boolean isTaskTodaySign(long memberId,String taskId,String today){
		String findAlreadySignLqMemberTaskSetListKey=JedisKeyUtils.findAlreadySignLqMemberTaskSetList+memberId;
		return jedisSets.sismember(findAlreadySignLqMemberTaskSetListKey, taskId+":"+today);
	}
	
	
}
