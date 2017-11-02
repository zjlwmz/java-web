package cn.emay.laiqiang.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @Title 用户token
 * @author zjlwm
 * @date 2016-12-9 下午4:21:51
 *
 */
@Service
public class UserTokenUtils {

	/**
	 * 数据加密key
	 */
	@Value("#{configProperties['asekey']}")
	private String asekey;

	public String getAsekey() {
		return asekey;
	}
	
	
	
}
