package com.tp.ems.modules.energyelecday.utils;

import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;

import javax.swing.text.html.HTML;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * 小时电量比较器
 *
 * @Author XuYunXuan
 * @Date 2017/6/25 13:59
 */
public class HourElecComparator implements Comparator<EnergyElecHour> {

    @Override
    public int compare(EnergyElecHour o1, EnergyElecHour o2) {
        return (Double.parseDouble(o1.getData()) < Double.parseDouble(o2.getData()) ? -1 : (Double.parseDouble(o1.getData()) == Double.parseDouble(o2.getData()) ? 0 : 1));
    }

    /**
     * 根据 传入的类型计算相应数值
     * sum
     * avg
     *
     */
    public static double getCount(List<EnergyElecHour> energyElecHours, String type) {
        BigDecimal res = BigDecimal.ZERO;
        for (EnergyElecHour e : energyElecHours) {
            res = res.add(BigDecimal.valueOf(Double.parseDouble(e.getData())));
        }
        if ("sum".equals(type)) {
            return res.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if ("avg".equals(type)&&energyElecHours!=null&&energyElecHours.size()!=0) {
            res = res.divide(BigDecimal.valueOf(energyElecHours.size()),2,BigDecimal.ROUND_HALF_UP);
            return res.doubleValue();
        }
        return 0;
    }
}
