package com.tp.ems.modules.dayamount.utl;

import java.util.Comparator;
import java.util.List;

import com.tp.ems.modules.dayamount.entity.DayAmount;



public class DayAmountComparator implements Comparator<DayAmount>{

	@Override
	public int compare(DayAmount o1, DayAmount o2) {
		// TODO Auto-generated method stub
		 return (o1.getElectricity() < o2.getElectricity()? -1 : (o1.getElectricity() ==o2.getElectricity() ? 0 : 1));
	}
	public static double allCount(List<DayAmount> dayAmountList){
		double result=0;
		for(int i=0;i<dayAmountList.size();i++){
			result=result+dayAmountList.get(i).getElectricity();
		}
		return result;
	}
}
