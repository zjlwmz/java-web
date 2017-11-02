package cn.emay.laiqiang.service;

import org.springframework.stereotype.Service;

import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.support.JedisKeyUtils;


/**
 * 
 * @Title ������������service�ӿ�
 * @author zjlwm
 * @date 2016-12-19 ����11:14:27
 *
 */
@Service
public class TransactionTypeService extends BaseService{

	/**
	 * �Ƿ�ɱ��Ƽ������
	 * 0 	�����
	 * 1 	���
	 * @param id
	 * @return
	 */
	public String getTransactionCanDrawCommissionByTransactionTypeId(Long id){
		String transactionTypekey=JedisKeyUtils.getTransactionCanDrawCommissionByTransactionTypeId+id;
		String canDrawCommission=jedisStrings.get(transactionTypekey);
		if(StringUtils.isNotBlank(canDrawCommission)){
			return canDrawCommission;
		}
		return "0";//�����
	}
	
}
