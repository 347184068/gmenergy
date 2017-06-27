package com.tp.ems.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Test {

	public static void main(String[] args) {
		double   f   =   111231.5585;
		BigDecimal   b   =   new BigDecimal(f);
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(f1);

		DecimalFormat df   =new   DecimalFormat("#0.00");
		String f2=df.format(10.3456789);
		System.out.println(f2);
	}
}
