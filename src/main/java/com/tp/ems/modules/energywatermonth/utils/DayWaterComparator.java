package com.tp.ems.modules.energywatermonth.utils;

import com.tp.ems.modules.energywaterday.entity.EnergyWaterDay;
import com.tp.ems.modules.energywaterhour.entity.EnergyWaterHour;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * @Author XuYunXuan
 * @Date 2017/6/26 19:07
 */
public class DayWaterComparator implements Comparator<EnergyWaterDay> {
    @Override
    public int compare(EnergyWaterDay o1, EnergyWaterDay o2) {
        return (Double.parseDouble(o1.getData()) < Double.parseDouble(o2.getData()) ? -1 : (Double.parseDouble(o1.getData()) == Double.parseDouble(o2.getData()) ? 0 : 1));
    }
    /**
     * 根据 传入的类型计算相应数值
     * sum
     * avg
     *
     */
    public static double getCount(List<EnergyWaterDay> energyWaterDays, String type) {
        BigDecimal res = BigDecimal.ZERO;
        for (EnergyWaterDay e : energyWaterDays) {
            res = res.add(BigDecimal.valueOf(Double.parseDouble(e.getData())));
        }
        if ("sum".equals(type)) {
            return res.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if ("avg".equals(type)&&energyWaterDays!=null&&energyWaterDays.size()!=0) {
            res = res.divide(BigDecimal.valueOf(energyWaterDays.size()),2,BigDecimal.ROUND_HALF_UP);
            return res.doubleValue();
        }
        return 0;
    }
}
