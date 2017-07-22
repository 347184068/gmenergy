package com.tp.ems.modules.energyelecmonth.utils;

import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * @Author XuYunXuan
 * @Date 2017/6/26 10:21
 */
public class DayElecComparator implements Comparator<EnergyElecDay> {
    @Override
    public int compare(EnergyElecDay o1, EnergyElecDay o2) {
        return (Double.parseDouble(o1.getRealData()) < Double.parseDouble(o2.getRealData()) ? -1 : (Double.parseDouble(o1.getRealData()) == Double.parseDouble(o2.getRealData()) ? 0 : 1));
    }

    /**
     * 根据 传入的类型计算相应数值
     * sum
     * avg
     *
     */
    public static double getCount(List<EnergyElecDay> energyElecDays, String type) {
        BigDecimal res = BigDecimal.ZERO;
        if(energyElecDays == null){
            return 0;
        }
        for (EnergyElecDay e : energyElecDays) {
            res = res.add(BigDecimal.valueOf(Double.parseDouble(e.getRealData()==null ? "0":e.getRealData())));
        }
        if ("sum".equals(type)) {
            return res.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if ("avg".equals(type)&&energyElecDays!=null&&energyElecDays.size()!=0) {
            res = res.divide(BigDecimal.valueOf(energyElecDays.size()),2,BigDecimal.ROUND_HALF_UP);
            return res.doubleValue();
        }
        return 0;
    }
}
