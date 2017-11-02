package cn.test;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import cn.emay.laiqiang.bo.LqActivityBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.StringUtils;



public class Test2 {

	public String taskInfoJson="{\"activityType\":1,\"createdBy\":5296,\"createdTime\":\"2017-02-28 10:30:41\",\"endTime\":\"2017-01-18 23:55:50\",\"id\":3,\"isPublished\":true,\"publishedTime\":\"2017-02-28 10:30:46\",\"rewardQuantity\":20.00,\"rewardType\":1,\"startTime\":\"2017-02-28 10:30:29\"}";
	
	public LqActivityBO t(){
		LqActivityBO lqActivityBO=JSON.parseObject(taskInfoJson, LqActivityBO.class);
		if(null!=lqActivityBO){
			String endTime=lqActivityBO.getEndTime();
			if(StringUtils.isNotBlank(endTime)){
				Date endDate=DateUtils.parseDate(endTime);
				Date currentDate=new Date();
				if(endDate.getTime()>currentDate.getTime()){
					return lqActivityBO;
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) {
		Test2 test2=new Test2();
		LqActivityBO lqActivityBO=test2.t();
		System.out.println(lqActivityBO);
	}
}
