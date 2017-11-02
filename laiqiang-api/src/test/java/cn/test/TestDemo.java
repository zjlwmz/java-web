package cn.test;

import cn.emay.laiqiang.common.utils.DoubleUtil;


public class TestDemo {

	public static void main(String[] args) throws Exception {
		double d=4.89;
		Double moneyD=DoubleUtil.mul(d, 100);
		int  total_fee=moneyD.intValue();
		System.out.println(total_fee);
	}
}
