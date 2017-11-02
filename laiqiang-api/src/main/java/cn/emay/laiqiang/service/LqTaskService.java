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
 * 任务service接口
 * @author lenovo
 *
 */
@Service
public class LqTaskService extends BaseService{

	
	/**
	 * 接口所在地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	/**
	 * 会员的赚钱任务service接口
	 */
	@Autowired
	private LqMemberTaskService lqMemberTaskService;
	
	/**
	 * 任务攻略详情
	 */
	@Autowired
	private LqTaskStrategyService lqTaskStrategyService;
	
	
	/**
	 * 截图任务截图service接口
	 */
	@Autowired
	private LqMemberTaskScreenshotService lqMemberTaskScreenshotService;
	
	/**
	 * 写数据redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	
	/**
	 * 获取任务
	 * @param id
	 * @return
	 */
	public LqTaskBO get(String id){
		/**
		 * 赚钱任务详情
		 */
		String lqTaskKey=JedisKeyUtils.getLqTaskById+id;
		String taskInfoJson=jedisStrings.get(lqTaskKey);
		if(StringUtils.isBlank(taskInfoJson)){
			return null;
		}
		return JSON.parseObject(taskInfoJson, LqTaskBO.class);
	}
	
	
	
	/**
	 * 更新可申请剩余量
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
	 * 查询任务分页查询
	 * 
	 * @return 返回list集合json字符串
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
				if(null!=memberId){//已经登录
					//查询做的赚钱任务
					LqMemberTaskBO lqMemberTaskBO=lqMemberTaskService.getLqMemberTask(memberId, taskId);
					if(null==lqMemberTaskBO){
						
						BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
						/**
						 * 任务id
						 */
						lqTaskDTO.setTaskId(lqTaskBO.getId());
						lqTaskDTOList.add(lqTaskDTO);
					}
				}
				else{//未登录
					BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
					/**
					 * 任务id
					 */
					lqTaskDTO.setTaskId(lqTaskBO.getId());
					lqTaskDTOList.add(lqTaskDTO);
				}
			}
			
		}
		return lqTaskDTOList;
	}
	
	
	/**
	 * 获取查询数据在第几页
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
	 * 任务列表
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
					
					//任务ID
					lqTaskDTO.setTaskId(lqTaskBO.getId());
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
					
					if(null!=memberId){//已经登录
						//查询做的赚钱任务
						LqMemberTaskBO lqMemberTaskBO=lqMemberTaskService.getLqMemberTask(memberId, taskId);
						if(null==lqMemberTaskBO){
							BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
							lqTaskDTOList.add(lqTaskDTO);
						}
					}
					else{//未登录
						BeanUtils.copyProperties(lqTaskDTO, lqTaskBO);
						lqTaskDTOList.add(lqTaskDTO);
					}
				}
				
				if(lqTaskDTOList.size()==pageSize){
					pageValue=getCurrentPage(JedisKeyUtils.findLqTaskList, taskId, pageSize);
					//如果查询列表还是当前页
					if(pageValue==currentPage){
						break;
					}
				}
				
				
				if(lqTaskDTOList.size()>pageSize){
					/**
					 * 如果查询到下一页、或者下下页(N页)
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
	 * 获取赚钱任务详情
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public LqTaskDTO getLqTaskInfo(String taskId,Long memberId) throws IllegalAccessException, InvocationTargetException{
		LqTaskDTO lqTaskDTO=new LqTaskDTO();
		/**
		 * 赚钱任务详情
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
		 * 会员的赚钱任务详情
		 * 需要判断是否过期--根据taskDuration（任务完成期限）
		 */
		if(null!=memberId){
			LqMemberTaskBO lqMemberTaskBO=lqMemberTaskService.getLqMemberTask(memberId, taskId);
			if(null!=lqMemberTaskBO){
				//任务完成期限
				Integer taskDuration=lqTaskBO.getTaskDuration();//分钟
				int status=lqMemberTaskBO.getStatus();
				//有时间现限制且未提交截图
				if(taskDuration>0 && status<3){
					//申请时间
					Date startTime=DateUtils.parseDate(lqMemberTaskBO.getStartTime());
					
					//当前时间
					Date currentDate=new Date();
					//没有过期
					long residualTime=(startTime.getTime()+taskDuration*60*1000)-currentDate.getTime();
					if(residualTime>0){
						BeanUtils.copyProperties(lqTaskDTO, lqMemberTaskBO);
						/**
						 * 剩余时间
						 */
						lqTaskDTO.setResidualTime(residualTime/1000);//秒
					}
					//已经过期
					else{
						lqTaskDTO.setResidualTime(-1l);
						//任务类型
						Integer taskType=lqTaskDTO.getTaskType();
						if(taskType.intValue()==TaskType.screenshot){//截图任务过期--更新状态为已取消
							lqMemberTaskService.taskCancelLqMemberTask(lqMemberTaskBO);
						}else if(taskType.intValue()==TaskType.download){//下载类任务过期--删除该条记录
							lqMemberTaskService.deleteLqMemberTask(lqMemberTaskBO);
						}
					}
				}
				
				BeanUtils.copyProperties(lqTaskDTO, lqMemberTaskBO);
				
				/**
				 * 截图任务
				 * 屏幕截图url
				 */
				List<LqMemberTaskScreenshotBO>screenshotList=lqMemberTaskScreenshotService.findList(lqMemberTaskBO.getId());
				lqTaskDTO.setScreenshotList(screenshotList);
			}
			
		}
		
		
		
		/**
		 * 任务类型
		 */
		Integer taskType=lqTaskDTO.getTaskType();
		
		//截图截图
		if(taskType.intValue()==TaskType.screenshot.intValue()){
			
			/**
			 * 任务攻略(截图)url地址
			 */
			String strategyUrl=domain+"laiqiang/app/task/html/app/strategy/"+taskId;
			lqTaskDTO.setStrategyUrl(strategyUrl);
			
			/**
			 * 任务详情(截图) url地址
			 */
			String detailUrl=domain+"laiqiang/app/task/html/app/detail/"+taskId;
			lqTaskDTO.setDetailUrl(detailUrl);
			
			/**
			 *  注册方式(1:下载app注册；2：跳转至Html5网页注册)
			 */
			String registerMode=lqTaskDTO.getRegisterMode();
			if(registerMode.equals("1")){
				String registerUrl=domain+"laiqiang/app/task/html/app/des/"+taskId;
				lqTaskDTO.setRegisterUrl(registerUrl);
			}
		}
		
		//下载任务
		if(taskType.intValue()==TaskType.download.intValue()){
			
			/**
			 * 下载类型使用
			 *  奖励详情url地址
			 */
			String registerUrl=domain+"laiqiang/app/task/html/app/register/"+taskId;
			lqTaskDTO.setRegisterUrl(registerUrl);
		}

		return lqTaskDTO;
	}
	
	
	
	
	
	/**
	 * 任务【流量】汇总
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
	 * 任务【人民币】汇总
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
