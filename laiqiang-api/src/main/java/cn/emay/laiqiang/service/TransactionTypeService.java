package cn.emay.laiqiang.service;

import org.springframework.stereotype.Service;

import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.support.JedisKeyUtils;


/**
 * 
 * @Title 交易事务类型service接口
 * @author zjlwm
 * @date 2016-12-19 上午11:14:27
 *
 */
@Service
public class TransactionTypeService extends BaseService{

	/**
	 * 是否可被推荐人提成
	 * 0 	不提成
	 * 1 	提成
	 * @param id
	 * @return
	 */
	public String getTransactionCanDrawCommissionByTransactionTypeId(Long id){
		String transactionTypekey=JedisKeyUtils.getTransactionCanDrawCommissionByTransactionTypeId+id;
		String canDrawCommission=jedisStrings.get(transactionTypekey);
		if(StringUtils.isNotBlank(canDrawCommission)){
			return canDrawCommission;
		}
		return "0";//不提成
	}
	
}
