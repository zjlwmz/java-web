package cn.emay.laiqiang.common.utils;

/**
 * double�ļ��㲻��ȷ����������0.0000000000000002������ȷ�ķ�����ʹ��BigDecimal����������
 ���͵ط����ʺ��ڻ��Ҿ�����֪�����������12.11+1.10ת��1211+110���㣬�����/100����
 ������ժ����BigDecimal����:
 */
import java.io.Serializable;
import java.math.BigDecimal;

public class DoubleUtil implements Serializable {
	private static final long serialVersionUID = -3345205828566485102L;
	// Ĭ�ϳ������㾫��
	private static final Integer DEF_DIV_SCALE = 2;

	/**
	 * �ṩ��ȷ�ļӷ����㡣
	 * @param value1 ������
	 * @param value2 ����
	 * @return ���������ĺ�
	 */
	public static Double add(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.add(b2).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param value1
	 *            ������
	 * @param value2
	 *            ����
	 * @return ���������Ĳ�
	 */
	public static double sub(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param value1
	 *            ������
	 * @param value2
	 *            ����
	 * @return ���������Ļ�
	 */
	public static Double mul(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ�� ��ȷ��С�����Ժ�10λ���Ժ�������������롣
	 * 
	 * @param dividend
	 *            ������
	 * @param divisor
	 *            ����
	 * @return ������������
	 */
	public static Double div(Double dividend, Double divisor) {
		return div(dividend, divisor, DEF_DIV_SCALE);
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㡣 �����������������ʱ����scale����ָ�����ȣ��Ժ�������������롣
	 * 
	 * @param dividend
	 *            ������
	 * @param divisor
	 *            ����
	 * @param scale
	 *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * @return ������������
	 */
	public static Double div(Double dividend, Double divisor, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(dividend));
		BigDecimal b2 = new BigDecimal(Double.toString(divisor));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param value
	 *            ��Ҫ�������������
	 * @param scale
	 *            С���������λ
	 * @return ���������Ľ��
	 */
	public static Double round(Double value, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
	public static void main(String[] args) {
		double dd=add(0.13, 0.45);
		System.out.println(dd);
	}
}
