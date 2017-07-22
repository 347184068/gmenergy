package com.tp.ems.modules.energywaterday.utils;

import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;
import com.tp.ems.modules.energywaterday.entity.EnergyWaterDay;
import com.tp.ems.modules.energywaterhour.entity.EnergyWaterHour;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * 每小时水量比较器
 *
 * @Author XuYunXuan
 * @Date 2017/6/25 13:59
 */
public class HourWaterComparator implements Comparator<EnergyWaterHour> {

    @Override
    public int compare(EnergyWaterHour o1, EnergyWaterHour o2) {
        return (Double.parseDouble(o1.getData()) < Double.parseDouble(o2.getData()) ? -1 : (Double.parseDouble(o1.getData()) == Double.parseDouble(o2.getData()) ? 0 : 1));
    }
    /**
     * 根据 传入的类型计算相应数值
     * sum
     * avg
     *
     */
    public static double getCount(List<EnergyWaterHour> energyWaterHours, String type) {
        BigDecimal res = BigDecimal.ZERO;
        for (EnergyWaterHour e : energyWaterHours) {
            res = res.add(BigDecimal.valueOf(Double.parseDouble(e.getData()==null ? "0":e.getData())));
        }
        if ("sum".equals(type)) {
            return res.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if ("avg".equals(type)&&energyWaterHours!=null&&energyWaterHours.size()!=0) {
            res = res.divide(BigDecimal.valueOf(energyWaterHours.size()),2,BigDecimal.ROUND_HALF_UP);
            return res.doubleValue();
        }
        return 0;
    }
}
