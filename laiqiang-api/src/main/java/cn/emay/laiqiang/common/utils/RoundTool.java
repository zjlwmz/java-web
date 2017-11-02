package cn.emay.laiqiang.common.utils;

public class RoundTool {
	
	// value 进行四舍五入的数值,dotNum 保留的小数位数
	public static String round(double value, int dotNum) {
		String strValue = String.valueOf(value);
		int pos = strValue.indexOf("."); // 小数点的位置
		int len = strValue.length(); // 数值的总位数
		int dotLen = len - pos - 1; // 实际小数的位数
		String endNum = ""; // 最后返回的字符串
		if (dotNum < dotLen) { // 需要保留的小数位数少于实际的小数位数，即小数有多
			String c = strValue.substring(pos + dotNum + 1, pos + dotNum + 2); // 获得保留小数位的下一位，对其进行四舍五入
			int cNum = Integer.parseInt(c); // 转换为整数
			double tempValue = Double.parseDouble(strValue); // 保留运算结果的中间变量
			if (cNum >= 5) { // cNum>=5,进位处理（保留小数位的最后一位加1),若保留两位，则加上0.01
				String tempDot = "";
				for (int i = 0; i < dotNum - 1; i++) {
					tempDot = tempDot + "0";
				}
				tempDot = "0." + tempDot + "1"; // 需要进位的小数值
				tempValue = tempValue + Double.parseDouble(tempDot);
				strValue = String.valueOf(tempValue); // 进位后的值转换为字符串
				endNum = strValue.substring(0, strValue.indexOf(".") + dotNum + 1);
			} else { // cNum<5,直接截取
				endNum = strValue.substring(0, strValue.indexOf(".") + dotNum + 1);
			}
		} else if (dotNum == dotLen) { // 需要保留的小数位数与实际的小数位数相等
			endNum = String.valueOf(value);
		} else { // 需要保留的小数位数大于实际的小数位数相等
			for (int i = 0; i <= dotNum - dotLen - 1; i++) {
				strValue = strValue + "0"; // 补“0”
			}
			endNum = strValue; // 最终的值
		}
		return endNum;
	}

	public static void main(String[] args) {
		System.out.println("数值123.121保留两位小数:\t" + RoundTool.round(123.121, 2));
		System.out.println("数值123.456789保留3位小数:\t" + RoundTool.round(123.456789, 3));
		System.out.println("数值123.1231保留3位小数:\t" + RoundTool.round(123.1231, 3));
		System.out.println("数值123.5保留3位小数:\t" + RoundTool.round(123.5, 3));
		
		
		
		System.out.println("数值123.121保留0小数:\t" + RoundTool.round(123.121, 0));
	}
}
