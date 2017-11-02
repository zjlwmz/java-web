package cn.emay.laiqiang.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @Title �û�token
 * @author zjlwm
 * @date 2016-12-9 ����4:21:51
 *
 */
@Service
public class UserTokenUtils {

	/**
	 * ���ݼ���key
	 */
	@Value("#{configProperties['asekey']}")
	private String asekey;

	public String getAsekey() {
		return asekey;
	}
	
	
	
}
