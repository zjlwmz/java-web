package cn.emay.laiqiang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqAreaBO;
import cn.emay.laiqiang.dao.LqAreaDao;

/**
 * 
 * @Title ����service�ӿ�
 * @author zjlwm
 * @date 2016-12-12 ����10:18:21
 *
 */
@Service
public class LqAreaService {

	/**
	 * ����DAO�ӿ�
	 */
	@Autowired
	private LqAreaDao lqAreaDao;
	
	
	public List<LqAreaBO>findAreaByParentId(String parentId){
		return lqAreaDao.findAreaByParentId(parentId);
	}
}
