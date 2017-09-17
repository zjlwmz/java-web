package cn.emay.common.secret;

import java.util.Random;

public class RandomUtil {

	/**
	 *  随机生成16位字符串,从WXBizMsgCrypt类抽取出来
	 * @return
	 */
	public static String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
