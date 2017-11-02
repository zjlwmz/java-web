package cn.emay.laiqiang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.LqAdvDTO;
import cn.emay.laiqiang.support.JedisKeyUtils;

import com.alibaba.fastjson.JSON;

/**
 * �ֲ�ͼservice�ӿ�
 * @author zjlwm
 * @date 2016-11-25
 *
 */
@Service
public class LqAdvService extends BaseService{

	/**
	 * λ������(1:��ҳ��2�����»��3�����ร��;4:�ֻ���ֵ;5:�״�����)
	 * @param type
	 * @return
	 */
	public List<LqAdvDTO> getLqAdvList(String type){
		List<LqAdvDTO>lqAdvBOList=new ArrayList<LqAdvDTO>();
		String lqAdvListkey=JedisKeyUtils.findLqAdvListByType+type;
		//Ϊ�����ش���
		List<String>repeatedList=new ArrayList<String>();
				
		List<String>advIdList=jedisLists.lrange(lqAdvListkey, 0, -1);//��ѯȫ��
		if(null!=advIdList){
			for(String advId:advIdList){
				
				String advKey=JedisKeyUtils.getLqAdvById+advId;
				
				String advInfo=jedisStrings.get(advKey);
				if(StringUtils.isNotBlank(advInfo) && !repeatedList.contains(advId)){
					
					repeatedList.add(advId);
					
					
					LqAdvDTO lqAdvBO=JSON.parseObject(advInfo, LqAdvDTO.class);
					String redirectNeedlogin=lqAdvBO.getRedirectNeedlogin();
					if(redirectNeedlogin.equals("false")){
						lqAdvBO.setRedirectNeedlogin("0");
					}else if(redirectNeedlogin.equals("true")){
						lqAdvBO.setRedirectNeedlogin("1");
					}
					lqAdvBOList.add(lqAdvBO);
				}
			}
		}
		
		return lqAdvBOList;
	}
	
}
