package cn.emay.laiqiang.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqNewsBO;
import cn.emay.laiqiang.common.utils.AESUtil;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.LqNewsDTO;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.support.LqNewsType;
import cn.emay.laiqiang.token.UserTokenUtils;

import com.alibaba.fastjson.JSON;


/**
 * @Title ͼ����Ѷservice�ӿ�
 * @author zjlwm
 * @date 2016-12-6 ����2:56:32
 *
 */
@Service
public class LqNewsService extends BaseService{

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
	 * ����id��ѯ
	 * @param id
	 * @return
	 */
	public LqNewsBO get(String id){
		String lqNewsKey=JedisKeyUtils.getLqNewsById+id;
		String lqNewsInfo=jedisStrings.get(lqNewsKey);
		if(StringUtils.isNotBlank(lqNewsInfo)){
			return JSON.parseObject(lqNewsInfo, LqNewsBO.class);
		}
		return null;
	}
	
	
	
	/**
	 * ͼ����ѯ��ҳ��ѯ
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception 
	 */
	public List<LqNewsDTO>findList(String type,String isEnd,long start,long end) throws Exception{
		List<LqNewsDTO>lqTaskDTOList=new ArrayList<LqNewsDTO>();
		
		//Ϊ�����ش���
		List<String>repeatedList=new ArrayList<String>();
		
		List<String>lqNewsList=jedisLists.lrange(JedisKeyUtils.findLqNewsListByType+type, start, end);
		for(String newsId:lqNewsList){
			LqNewsBO lqNewsBO=get(newsId);
			
			if(lqNewsBO!=null && !repeatedList.contains(newsId)){
				
				repeatedList.add(newsId);
				
				LqNewsDTO lqNewsDTO=new LqNewsDTO();
				//���ึ����
				if(type.equals(LqNewsType.moreWelfare)){
					boolean result=jedisKeys.exists(JedisKeyUtils.getLqNewsByExpireId+newsId);
					//����
					if(isEnd.equals("0") && result ){
						/**
						 * ���ı��鿴url
						 */
						String isLink=lqNewsBO.getIsLink();
						if(StringUtils.isNotBlank(isLink)){
							if(isLink.equals("1")){//ʹ���ⲿ����
								lqNewsDTO.setDetailUrl(lqNewsBO.getLink());
								BeanUtils.copyProperties(lqNewsDTO, lqNewsBO);
								lqTaskDTOList.add(lqNewsDTO);
							}else{
								String lqNesId=String.valueOf(lqNewsBO.getId());
								String desLqNesId=AESUtil.encrypt(lqNesId, userTokenUtils.getAsekey());
								String detailUrl=domain+"laiqiang/app/news/html/detail/"+desLqNesId;
								lqNewsDTO.setDetailUrl(detailUrl);
								BeanUtils.copyProperties(lqNewsDTO, lqNewsBO);
								lqTaskDTOList.add(lqNewsDTO);
							}
						}else{
							String lqNesId=String.valueOf(lqNewsBO.getId());
							String desLqNesId=AESUtil.encrypt(lqNesId, userTokenUtils.getAsekey());
							String detailUrl=domain+"laiqiang/app/news/html/detail/"+desLqNesId;
							lqNewsDTO.setDetailUrl(detailUrl);
							BeanUtils.copyProperties(lqNewsDTO, lqNewsBO);
							lqTaskDTOList.add(lqNewsDTO);
						}
						
					}else if(isEnd.equals("1") && !result){
						/**
						 * ���ı��鿴url
						 */
						String isLink=lqNewsBO.getIsLink();
						if(StringUtils.isNotBlank(isLink)){
							if(isLink.equals("1")){//ʹ���ⲿ����
								lqNewsDTO.setDetailUrl(lqNewsBO.getLink());
								BeanUtils.copyProperties(lqNewsDTO, lqNewsBO);
								lqTaskDTOList.add(lqNewsDTO);
							}else{
								String lqNesId=String.valueOf(lqNewsBO.getId());
								String desLqNesId=AESUtil.encrypt(lqNesId, userTokenUtils.getAsekey());
								String detailUrl=domain+"laiqiang/app/news/html/detail/"+desLqNesId;
								lqNewsDTO.setDetailUrl(detailUrl);
								BeanUtils.copyProperties(lqNewsDTO, lqNewsBO);
								lqTaskDTOList.add(lqNewsDTO);
							}
						}else{
							String lqNesId=String.valueOf(lqNewsBO.getId());
							String desLqNesId=AESUtil.encrypt(lqNesId, userTokenUtils.getAsekey());
							String detailUrl=domain+"laiqiang/app/news/html/detail/"+desLqNesId;
							lqNewsDTO.setDetailUrl(detailUrl);
							BeanUtils.copyProperties(lqNewsDTO, lqNewsBO);
							lqTaskDTOList.add(lqNewsDTO);
						}
						
					}
				}else if(type.equals(LqNewsType.historicalActivities)){
					/**
					 * ���ı��鿴url
					 */
					String isLink=lqNewsBO.getIsLink();
					if(StringUtils.isNotBlank(isLink)){
						if(isLink.equals("1")){//ʹ���ⲿ����
							lqNewsDTO.setDetailUrl(lqNewsBO.getLink());
							BeanUtils.copyProperties(lqNewsDTO, lqNewsBO);
							
							String endTimeStr=lqNewsBO.getEndTime();
							if(StringUtils.isNotBlank(endTimeStr)){
								Date endTime=DateUtils.parseDate(endTimeStr);
								Date currentDate=new Date();
								if(endTime.getTime()<currentDate.getTime()){
									lqNewsDTO.setOverdue("1");//�ѹ���
								}
								
								lqTaskDTOList.add(lqNewsDTO);
							}
							
						}else{
							String lqNesId=String.valueOf(lqNewsBO.getId());
							String desLqNesId=AESUtil.encrypt(lqNesId, userTokenUtils.getAsekey());
							String detailUrl=domain+"laiqiang/app/news/html/detail/"+desLqNesId;
							lqNewsDTO.setDetailUrl(detailUrl);
							BeanUtils.copyProperties(lqNewsDTO, lqNewsBO);
							
							String endTimeStr=lqNewsBO.getEndTime();
							if(StringUtils.isNotBlank(endTimeStr)){
								Date endTime=DateUtils.parseDate(endTimeStr);
								Date currentDate=new Date();
								if(endTime.getTime()<currentDate.getTime()){
									lqNewsDTO.setOverdue("1");//�ѹ���
								}
								
								lqTaskDTOList.add(lqNewsDTO);
							}
							
						}
					}else{
						String lqNesId=String.valueOf(lqNewsBO.getId());
						String desLqNesId=AESUtil.encrypt(lqNesId, userTokenUtils.getAsekey());
						String detailUrl=domain+"laiqiang/app/news/html/detail/"+desLqNesId;
						lqNewsDTO.setDetailUrl(detailUrl);
						BeanUtils.copyProperties(lqNewsDTO, lqNewsBO);
						
						String endTimeStr=lqNewsBO.getEndTime();
						Date endTime=DateUtils.parseDate(endTimeStr);
						Date currentDate=new Date();
						if(endTime.getTime()<currentDate.getTime()){
							lqNewsDTO.setOverdue("1");//�ѹ���
						}
						
						
						lqTaskDTOList.add(lqNewsDTO);
					}
					
				}
			}
			
		}
		return lqTaskDTOList;
	}
}
