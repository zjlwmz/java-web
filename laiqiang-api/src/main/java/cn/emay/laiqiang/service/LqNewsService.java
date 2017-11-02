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
 * @Title 图文资讯service接口
 * @author zjlwm
 * @date 2016-12-6 下午2:56:32
 *
 */
@Service
public class LqNewsService extends BaseService{

	/**
	 * 接口所在地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	/**
	 * 令牌工具类
	 */
	@Autowired
	private UserTokenUtils userTokenUtils;
	
	/**
	 * 根据id查询
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
	 * 图文咨询分页查询
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception 
	 */
	public List<LqNewsDTO>findList(String type,String isEnd,long start,long end) throws Exception{
		List<LqNewsDTO>lqTaskDTOList=new ArrayList<LqNewsDTO>();
		
		//为了排重处理
		List<String>repeatedList=new ArrayList<String>();
		
		List<String>lqNewsList=jedisLists.lrange(JedisKeyUtils.findLqNewsListByType+type, start, end);
		for(String newsId:lqNewsList){
			LqNewsBO lqNewsBO=get(newsId);
			
			if(lqNewsBO!=null && !repeatedList.contains(newsId)){
				
				repeatedList.add(newsId);
				
				LqNewsDTO lqNewsDTO=new LqNewsDTO();
				//更多付福利
				if(type.equals(LqNewsType.moreWelfare)){
					boolean result=jedisKeys.exists(JedisKeyUtils.getLqNewsByExpireId+newsId);
					//存在
					if(isEnd.equals("0") && result ){
						/**
						 * 富文本查看url
						 */
						String isLink=lqNewsBO.getIsLink();
						if(StringUtils.isNotBlank(isLink)){
							if(isLink.equals("1")){//使用外部连接
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
						 * 富文本查看url
						 */
						String isLink=lqNewsBO.getIsLink();
						if(StringUtils.isNotBlank(isLink)){
							if(isLink.equals("1")){//使用外部连接
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
					 * 富文本查看url
					 */
					String isLink=lqNewsBO.getIsLink();
					if(StringUtils.isNotBlank(isLink)){
						if(isLink.equals("1")){//使用外部连接
							lqNewsDTO.setDetailUrl(lqNewsBO.getLink());
							BeanUtils.copyProperties(lqNewsDTO, lqNewsBO);
							
							String endTimeStr=lqNewsBO.getEndTime();
							if(StringUtils.isNotBlank(endTimeStr)){
								Date endTime=DateUtils.parseDate(endTimeStr);
								Date currentDate=new Date();
								if(endTime.getTime()<currentDate.getTime()){
									lqNewsDTO.setOverdue("1");//已过期
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
									lqNewsDTO.setOverdue("1");//已过期
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
							lqNewsDTO.setOverdue("1");//已过期
						}
						
						
						lqTaskDTOList.add(lqNewsDTO);
					}
					
				}
			}
			
		}
		return lqTaskDTOList;
	}
}
