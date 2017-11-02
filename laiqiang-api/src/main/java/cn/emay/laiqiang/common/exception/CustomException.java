package cn.emay.laiqiang.common.exception;

/**
 * 自定义异常
 * 
 * @Title
 * @author zjlwm
 * @date 2016-12-8 下午5:35:47
 * 
 */
public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
		super(message);
	}
}
