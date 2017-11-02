package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqOrderNoBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.dao.LqOrderNoDao;

/**
 * @Title �������service�ӿ�
 * @author zjlwm
 * @date 2016-12-14 ����3:58:05
 */
@Service
public class LqOrderNoService {

	/**
	 * ������Žӿ�
	 * M��ͷ
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
