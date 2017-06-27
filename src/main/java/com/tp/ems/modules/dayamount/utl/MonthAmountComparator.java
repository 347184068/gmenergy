package com.tp.ems.modules.dayamount.utl;

import java.util.Comparator;
import java.util.List;

import com.tp.ems.modules.dayamount.entity.MonthAmount;



public class MonthAmountComparator implements Comparator<MonthAmount>{

	@Override
	public int compare(MonthAmount o1, MonthAmount o2) {
		// TODO Auto-generated method stub
		 return (o1.getElectricity() < o2.getElectricity()? -1 : (o1.getElectricity() ==o2.getElectricity() ? 0 : 1));
	}
	public static double allCount(List<MonthAmount> monthAmountList){
		double result=0;
		for(int i=0;i<monthAmountList.size();i++){
			result=result+monthAmountList.get(i).getElectricity();
		}
		return result;
	}
}
