package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqOrderNoBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.dao.LqOrderNoDao;

/**
 * @Title 订单编号service接口
 * @author zjlwm
 * @date 2016-12-14 下午3:58:05
 */
@Service
public class LqOrderNoService {

	/**
	 * 订单编号接口
	 * M开头
	 */
	@Autowired
	private LqOrderNoDao lqOrderNoDao;
	
	public String getOrderNo() {
		String fdate=DateUtils.getDate("yyMMdd");
		int num=lqOrderNoDao.updateSerialNumber(fdate);
		LqOrderNoBO lqOrderNoBO=null;
		if(num==0){
			lqOrderNoBO=new LqOrderNoBO();
			lqOrderNoBO.setFdate(fdate);
			lqOrderNoBO.setSerialNumber(1l);
			lqOrderNoDao.insert(lqOrderNoBO);
			String orderNo = "M" +fdate+ String.format("%07d", lqOrderNoBO.getSerialNumber());
			return orderNo;
		}else{
			lqOrderNoBO=lqOrderNoDao.findLqOrderNoBOByFdate(fdate);
			String orderNo = "M" +fdate+ String.format("%07d", lqOrderNoBO.getSerialNumber());
			return orderNo;
		}
	}
}
