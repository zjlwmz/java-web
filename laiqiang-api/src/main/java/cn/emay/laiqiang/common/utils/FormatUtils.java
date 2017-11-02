package cn.emay.laiqiang.common.utils;

import java.text.DecimalFormat;

/**
 * @Title  ��ʽ������
 * @author zjlwm
 * @date 2016-12-26 ����1:34:16
 *
 */
public class FormatUtils {

	/**
	 * ��double���͵����ֱ�����λС�����������룩
	 * 
	 * @param number
	 * @return
	 */
	public static String formatNumber(double number) {
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#0.00");
		return df.format(number);
	}
	
	/**
	 * ��doubleת���ٷֱ�
	 * @param number
	 * @return
	 */
    public static String formatPercent(double number){
    	DecimalFormat df= new DecimalFormat("#0.0#%"); 
		return df.format(number);
    }
    
    
    public static void main(String[] args) {
    	String dd=formatPercent(1.0010);
    	System.out.println(dd);
	}
}
