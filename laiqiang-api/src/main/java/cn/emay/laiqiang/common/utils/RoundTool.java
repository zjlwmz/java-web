package cn.emay.laiqiang.common.utils;

public class RoundTool {
	
	// value ���������������ֵ,dotNum ������С��λ��
	public static String round(double value, int dotNum) {
		String strValue = String.valueOf(value);
		int pos = strValue.indexOf("."); // С�����λ��
		int len = strValue.length(); // ��ֵ����λ��
		int dotLen = len - pos - 1; // ʵ��С����λ��
		String endNum = ""; // ��󷵻ص��ַ���
		if (dotNum < dotLen) { // ��Ҫ������С��λ������ʵ�ʵ�С��λ������С���ж�
			String c = strValue.substring(pos + dotNum + 1, pos + dotNum + 2); // ��ñ���С��λ����һλ�����������������
			int cNum = Integer.parseInt(c); // ת��Ϊ����
			double tempValue = Double.parseDouble(strValue); // �������������м����
			if (cNum >= 5) { // cNum>=5,��λ��������С��λ�����һλ��1),��������λ�������0.01
				String tempDot = "";
				for (int i = 0; i < dotNum - 1; i++) {
					tempDot = tempDot + "0";
				}
				tempDot = "0." + tempDot + "1"; // ��Ҫ��λ��С��ֵ
				tempValue = tempValue + Double.parseDouble(tempDot);
				strValue = String.valueOf(tempValue); // ��λ���ֵת��Ϊ�ַ���
				endNum = strValue.substring(0, strValue.indexOf(".") + dotNum + 1);
			} else { // cNum<5,ֱ�ӽ�ȡ
				endNum = strValue.substring(0, strValue.indexOf(".") + dotNum + 1);
			}
		} else if (dotNum == dotLen) { // ��Ҫ������С��λ����ʵ�ʵ�С��λ�����
			endNum = String.valueOf(value);
		} else { // ��Ҫ������С��λ������ʵ�ʵ�С��λ�����
			for (int i = 0; i <= dotNum - dotLen - 1; i++) {
				strValue = strValue + "0"; // ����0��
			}
			endNum = strValue; // ���յ�ֵ
		}
		return endNum;
	}

	public static void main(String[] args) {
		System.out.println("��ֵ123.121������λС��:\t" + RoundTool.round(123.121, 2));
		System.out.println("��ֵ123.456789����3λС��:\t" + RoundTool.round(123.456789, 3));
		System.out.println("��ֵ123.1231����3λС��:\t" + RoundTool.round(123.1231, 3));
		System.out.println("��ֵ123.5����3λС��:\t" + RoundTool.round(123.5, 3));
		
		
		
		System.out.println("��ֵ123.121����0С��:\t" + RoundTool.round(123.121, 0));
	}
}
