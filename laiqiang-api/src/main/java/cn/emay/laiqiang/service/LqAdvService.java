package cn.emay.laiqiang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.LqAdvDTO;
import cn.emay.laiqiang.support.JedisKeyUtils;

import com.alibaba.fastjson.JSON;

/**
 * 轮播图service接口
 * @author zjlwm
 * @date 2016-11-25
 *
 */
@Service
public class LqAdvService extends BaseService{

	/**
	 * 位置类型(1:首页；2：最新活动；3：更多福利;4:手机充值;5:首次启动)
	 * @param type
	 * @return
	 */
	public List<LqAdvDTO> getLqAdvList(String type){
		List<LqAdvDTO>lqAdvBOList=new ArrayList<LqAdvDTO>();
		String lqAdvListkey=JedisKeyUtils.findLqAdvListByType+type;
		//为了排重处理
		List<String>repeatedList=new ArrayList<String>();
				
		List<String>advIdList=jedisLists.lrange(lqAdvListkey, 0, -1);//查询全部
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
