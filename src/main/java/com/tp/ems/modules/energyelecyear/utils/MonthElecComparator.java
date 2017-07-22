package com.tp.ems.modules.energyelecyear.utils;

import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelecmonth.entity.EnergyElecMonth;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * 月数据电量比较器
 *
 * @Author XuYunXuan
 * @Date 2017/6/26 14:55
 */
public class MonthElecComparator implements Comparator<EnergyElecMonth> {
    @Override
    public int compare(EnergyElecMonth o1, EnergyElecMonth o2) {
        return (Double.parseDouble(o1.getRealData()) < Double.parseDouble(o2.getRealData()) ? -1 : (Double.parseDouble(o1.getRealData()) == Double.parseDouble(o2.getRealData()) ? 0 : 1));
    }


    /**
     * 根据 传入的类型计算相应数值
     * sum
     * avg
     */
    public static double getCount(List<EnergyElecMonth> energyElecMonths, String type) {
        BigDecimal res = BigDecimal.ZERO;
        if(energyElecMonths == null){
            return 0;
        }
        for (EnergyElecMonth e : energyElecMonths) {
            res = res.add(BigDecimal.valueOf(Double.parseDouble(e.getRealData()==null ? "0":e.getRealData())));
        }
        if ("sum".equals(type)) {
            return res.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if ("avg".equals(type)&&energyElecMonths!=null&&energyElecMonths.size()!=0) {
            res = res.divide(BigDecimal.valueOf(energyElecMonths.size()),2,BigDecimal.ROUND_HALF_UP);
            return res.doubleValue();
        }
        return 0;
    }
}
