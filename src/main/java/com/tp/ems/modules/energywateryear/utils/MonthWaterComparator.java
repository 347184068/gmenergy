package com.tp.ems.modules.energywateryear.utils;

import com.tp.ems.modules.energyelecmonth.entity.EnergyElecMonth;
import com.tp.ems.modules.energywatermonth.entity.EnergyWaterMonth;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * @Author XuYunXuan
 * @Date 2017/6/26 19:57
 */
public class MonthWaterComparator implements Comparator<EnergyWaterMonth>{
    @Override
    public int compare(EnergyWaterMonth o1, EnergyWaterMonth o2) {
        return (Double.parseDouble(o1.getData()) < Double.parseDouble(o2.getData()) ? -1 : (Double.parseDouble(o1.getData()) == Double.parseDouble(o2.getData()) ? 0 : 1));
    }

    /**
     * 根据 传入的类型计算相应数值
     * sum
     * avg
     */
    public static double getCount(List<EnergyWaterMonth> energyWaterMonths, String type) {
        BigDecimal res = BigDecimal.ZERO;
        for (EnergyWaterMonth e : energyWaterMonths) {
            res = res.add(BigDecimal.valueOf(Double.parseDouble(e.getData()==null ? "0":e.getData())));
        }
        if ("sum".equals(type)) {
            return res.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if ("avg".equals(type)&&energyWaterMonths!=null&&energyWaterMonths.size()!=0) {
            res = res.divide(BigDecimal.valueOf(energyWaterMonths.size()),2,BigDecimal.ROUND_HALF_UP);
            return res.doubleValue();
        }
        return 0;
    }
}
