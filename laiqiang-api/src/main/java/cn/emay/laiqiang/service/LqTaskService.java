package cn.emay.laiqiang.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqMemberTaskBO;
import cn.emay.laiqiang.bo.LqMemberTaskScreenshotBO;
import cn.emay.laiqiang.bo.LqTaskBO;
import cn.emay.laiqiang.bo.LqTaskStrategyBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.LqTaskDTO;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.support.TaskType;

import com.alibaba.fastjson.JSON;

/**
 * ����service�ӿ�
 * @author lenovo
 *
 */
@Service
public class LqTaskService extends BaseService{

	
	/**
	 * �ӿ����ڵ�ַ
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	/**
	 * ��Ա��׬Ǯ����service�ӿ�
	 */
	@Autowired
	private LqMemberTaskService lqMemberTaskService;
	
	/**
	 * ����������
	 */
	@Autowired
	private LqTaskStrategyService lqTaskStrategyService;
	
	
	/**
	 * ��ͼ�����ͼservice�ӿ�
	 */
	@Autowired
	private LqMemberTaskScreenshotService lqMemberTaskScreenshotService;
	
	/**
	 * д����redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	
	/**
	 * ��ȡ����
	 * @param id
	 * @return
	 */
	public LqTaskBO get(String id){
		/**
		 * ׬Ǯ��������
		 */
		String lqTaskKey=JedisKeyUtils.getLqTaskById+id;
		String taskInfoJson=jedisStrings.get(lqTaskKey);
		if(StringUtils.isBlank(taskInfoJson)){
			return null;
		}
		return JSON.parseObject(taskInfoJson, LqTaskBO.class);
	}
	
	
	
	/**
	 * ���¿�����ʣ����
	 */
	public void updateSurplusQuantity(String id){
		LqTaskBO lqTaskBO=get(id);
		if(null!=lqTaskBO){
			Integer surplusQuantity=lqTaskBO.getSurplusQuantity();
			if(null!=surplusQuantity && surplusQuantity>0){
				String lqTaskKey=JedisKeyUtils.getLqTaskById+id;
				jedisStrings.set(lqTaskKey, JSON.toJSONString(lqTaskBO));
				
				
				StringBuffer sqlBuff=new StringBuffer();
				sqlBuff.append("update lq_task set ");
				sqlBuff.append("surplus_quantity = surplus_quantity-1 ");
				sqlBuff.append(" where id=").append(id);
				
				
				String updateSql=sqlBuff.toString();
				
				
				redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
			}
			
		}
	}
	
	
	
	/**
	 * 
	 * ��ѯ�����ҳ��ѯ
	 * 
	 * @return ����list����json�ַ���
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @example [{id:"",name:"",appIcon:"",briefIntro:"",taskType:"",rewardType:"",rewardQuantity:""}]
	 */
	public List<LqTaskDTO> findTask2(Long memberId,long start,long end) throws IllegalAccessException, InvocationTargetException{
		List<LqTaskDTO>lqTaskDTOList=new ArrayList<LqTaskDTO>();
		List<String>lqtaskList=jedisLists.lrange(JedisKeyUtils.findLqTaskList, start, end);
		for(String taskId:lqtaskList){
			LqTaskBO lqTaskBO=get(taskId);
			LqTaskDTO lqTaskDTO=new LqTaskDTO();
			if(null!=lqTaskBO){
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
				if(null!=memberId){//�Ѿ���¼
					//��ѯ����׬Ǯ����
					LqMemberTaskBO lqMemberTaskBO=lqMemberTaskService.getLqMemberTask(memberId, taskId);
					if(null==lqMemberTaskBO){
						
						BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
						/**
						 * ����id
						 */
						lqTaskDTO.setTaskId(lqTaskBO.getId());
						lqTaskDTOList.add(lqTaskDTO);
					}
				}
				else{//δ��¼
					BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
					/**
					 * ����id
					 */
					lqTaskDTO.setTaskId(lqTaskBO.getId());
					lqTaskDTOList.add(lqTaskDTO);
				}
			}
			
		}
		return lqTaskDTOList;
	}
	
	
	/**
	 * ��ȡ��ѯ�����ڵڼ�ҳ
	 * @return
	 */
	public long getCurrentPage(String key,String taskId,long pageSize){
		long index=jedisSortSet.zrank(key, taskId);
		if(index>-1){
			index+=1;
			long currentPage=index/pageSize;
			long remainder =index%pageSize;
			if(remainder>0){
				currentPage+=1;
			}
			return currentPage;
		}
		return -1;
	}
	
	
	/**
	 * �����б�
	 * @param memberId
	 * @param start
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<LqTaskDTO> findTask(Long memberId,long start,long currentPage,long pageSize) throws IllegalAccessException, InvocationTargetException{
		List<LqTaskDTO>lqTaskDTOList=new ArrayList<LqTaskDTO>();
		Set<String>lqtaskList=jedisSortSet.zrange(JedisKeyUtils.findLqTaskList, start, -1);
		long pageValue=currentPage;
		if(null!=lqtaskList){
			for(String taskId:lqtaskList){
				LqTaskBO lqTaskBO=get(taskId);
				LqTaskDTO lqTaskDTO=new LqTaskDTO();
				if(null!=lqTaskBO){
					Date endDate=DateUtils.parseDate(lqTaskBO.getEndDate());
					Date currentDate=new Date();
					if(currentDate.after(endDate)){
						jedisSortSet.zrem(JedisKeyUtils.findLqTaskList, taskId);
						continue;
					}
					
					//����ID
					lqTaskDTO.setTaskId(lqTaskBO.getId());
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
					
					if(null!=memberId){//�Ѿ���¼
						//��ѯ����׬Ǯ����
						LqMemberTaskBO lqMemberTaskBO=lqMemberTaskService.getLqMemberTask(memberId, taskId);
						if(null==lqMemberTaskBO){
							BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
							lqTaskDTOList.add(lqTaskDTO);
						}
					}
					else{//δ��¼
						BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
						lqTaskDTOList.add(lqTaskDTO);
					}
				}
				
				if(lqTaskDTOList.size()==pageSize){
					pageValue=getCurrentPage(JedisKeyUtils.findLqTaskList, taskId, pageSize);
					//�����ѯ�б��ǵ�ǰҳ
					if(pageValue==currentPage){
						break;
					}
				}
				
				
				if(lqTaskDTOList.size()>pageSize){
					/**
					 * �����ѯ����һҳ����������ҳ(Nҳ)
					 */
					long pageCal=getCurrentPage(JedisKeyUtils.findLqTaskList, taskId, pageSize);
					if(pageCal!=pageValue){
						break;
					}
				}
			}
		}
		
		return lqTaskDTOList;
	}
	
	
	/**
	 * ��ȡ׬Ǯ��������
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public LqTaskDTO getLqTaskInfo(String taskId,Long memberId) throws IllegalAccessException, InvocationTargetException{
		LqTaskDTO lqTaskDTO=new LqTaskDTO();
		/**
		 * ׬Ǯ��������
		 */
		String lqTaskKey=JedisKeyUtils.getLqTaskById+taskId;
		String taskInfoJson=jedisStrings.get(lqTaskKey);
		if(StringUtils.isBlank(taskInfoJson)){
			return null;
		}
		LqTaskBO lqTaskBO=JSON.parseObject(taskInfoJson, LqTaskBO.class);
		BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
		lqTaskDTO.setTaskId(taskId);	
		
		LqTaskStrategyBO lqTaskStrategyBO =lqTaskStrategyService.get(taskId);
		if(null!=lqTaskStrategyBO){
			String description=lqTaskStrategyBO.getDescription();
			lqTaskDTO.setDescription(description);
		}
		
		/**
		 * ��Ա��׬Ǯ��������
		 * ��Ҫ�ж��Ƿ����--����taskDuration������������ޣ�
		 */
		if(null!=memberId){
			LqMemberTaskBO lqMemberTaskBO=lqMemberTaskService.getLqMemberTask(memberId, taskId);
			if(null!=lqMemberTaskBO){
				//�����������
				Integer taskDuration=lqTaskBO.getTaskDuration();//����
				int status=lqMemberTaskBO.getStatus();
				//��ʱ����������δ�ύ��ͼ
				if(taskDuration>0 && status<3){
					//����ʱ��
					Date startTime=DateUtils.parseDate(lqMemberTaskBO.getStartTime());
					
					//��ǰʱ��
					Date currentDate=new Date();
					//û�й���
					long residualTime=(startTime.getTime()+taskDuration*60*1000)-currentDate.getTime();
					if(residualTime>0){
						BeanUtils.copyProperties(lqTaskDTO, lqMemberTaskBO);
						/**
						 * ʣ��ʱ��
						 */
						lqTaskDTO.setResidualTime(residualTime/1000);//��
					}
					//�Ѿ�����
					else{
						lqTaskDTO.setResidualTime(-1l);
						//��������
						Integer taskType=lqTaskDTO.getTaskType();
						if(taskType.intValue()==TaskType.screenshot){//��ͼ�������--����״̬Ϊ��ȡ��
							lqMemberTaskService.taskCancelLqMemberTask(lqMemberTaskBO);
						}else if(taskType.intValue()==TaskType.download){//�������������--ɾ��������¼
							lqMemberTaskService.deleteLqMemberTask(lqMemberTaskBO);
						}
					}
				}
				
				BeanUtils.copyProperties(lqTaskDTO, lqMemberTaskBO);
				
				/**
				 * ��ͼ����
				 * ��Ļ��ͼurl
				 */
				List<LqMemberTaskScreenshotBO>screenshotList=lqMemberTaskScreenshotService.findList(lqMemberTaskBO.getId());
				lqTaskDTO.setScreenshotList(screenshotList);
			}
			
		}
		
		
		
		/**
		 * ��������
		 */
		Integer taskType=lqTaskDTO.getTaskType();
		
		//��ͼ��ͼ
		if(taskType.intValue()==TaskType.screenshot.intValue()){
			
			/**
			 * ������(��ͼ)url��ַ
			 */
			String strategyUrl=domain+"laiqiang/app/task/html/app/strategy/"+taskId;
			lqTaskDTO.setStrategyUrl(strategyUrl);
			
			/**
			 * ��������(��ͼ) url��ַ
			 */
			String detailUrl=domain+"laiqiang/app/task/html/app/detail/"+taskId;
			lqTaskDTO.setDetailUrl(detailUrl);
			
			/**
			 *  ע�᷽ʽ(1:����appע�᣻2����ת��Html5��ҳע��)
			 */
			String registerMode=lqTaskDTO.getRegisterMode();
			if(registerMode.equals("1")){
				String registerUrl=domain+"laiqiang/app/task/html/app/des/"+taskId;
				lqTaskDTO.setRegisterUrl(registerUrl);
			}
		}
		
		//��������
		if(taskType.intValue()==TaskType.download.intValue()){
			
			/**
			 * ��������ʹ��
			 *  ��������url��ַ
			 */
			String registerUrl=domain+"laiqiang/app/task/html/app/register/"+taskId;
			lqTaskDTO.setRegisterUrl(registerUrl);
		}

		return lqTaskDTO;
	}
	
	
	
	
	
	/**
	 * ��������������
	 * @param transactionype
	 * @param flow
	 */
	public void updateLqTaskTotalflow(String taskId,long transactionype,Integer flow){
		LqTaskBO lqTaskBO=get(taskId);
		String key="redis:lq_task:";
		if(lqTaskBO==null)return;
		if(transactionype==101){
			key=key+"totalflow101:"+taskId;
			jedisStrings.incrBy(key, flow*100);
		}
		else if(transactionype==102){
			key=key+"totalflow102:"+taskId;
			jedisStrings.incrBy(key, flow*100);
		}
		else if(transactionype==103){
			key=key+"totalflow103:"+taskId;
			jedisStrings.incrBy(key, flow*100);
		}
		else if(transactionype==104){
			key=key+"totalflow104:"+taskId;
			jedisStrings.incrBy(key, flow*100);
		}
		else if(transactionype==105){
			key=key+"totalflow105:"+taskId;
			jedisStrings.incrBy(key, flow*100);
		}else if(transactionype==109){
			key=key+"totalflow109:"+taskId;
			jedisStrings.incrBy(key, flow*100);
		}else{
			return;
		}
	}
	
	
	
	
	
	/**
	 * ��������ҡ�����
	 * @param transactionype
	 * @param flow
	 */
	public void updateLqTaskTotalCash(String taskId,long transactionype,double cash){
		String key="redis:lq_task:";
		if(transactionype==101){
			key=key+"totalcash101:"+taskId;
			Double cashI=cash*100;
			jedisStrings.incrBy(key, cashI.intValue());
		}
		else if(transactionype==102){
			key=key+"totalcash102:"+taskId;
			Double cashI=cash*100;
			jedisStrings.incrBy(key, cashI.intValue());
		}
		else if(transactionype==103){
			key=key+"totalcash103:"+taskId;
			Double cashI=cash*100;
			jedisStrings.incrBy(key, cashI.intValue());
		}
		else if(transactionype==104){
			key=key+"totalcash104:"+taskId;
			Double cashI=cash*100;
			jedisStrings.incrBy(key, cashI.intValue());
		}
		else if(transactionype==105){
			key=key+"totalcash105:"+taskId;
			Double cashI=cash*100;
			jedisStrings.incrBy(key, cashI.intValue());
		}else if(transactionype==109){
			key=key+"totalcash109:"+taskId;
			Double cashI=cash*100;
			jedisStrings.incrBy(key, cashI.intValue());
		}else{
			return;
		}
	}
	
	
	
	
}
