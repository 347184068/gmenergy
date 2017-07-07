package com.tp.ems.modules.energydevices.utils;

import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Gauge;

/**
 * @Author XuYunXuan
 * @Date 2017/6/30 19:39
 */
public class DrawChartUtils  {

    /**
     *  生成限额占比图
     */
    public static GsonOption genLimitChart(String limit,double currentValue){
        GsonOption option = new GsonOption();
        option.tooltip().formatter("{a}:{c}");
        Gauge gauge = new Gauge();
        gauge.setMax(Integer.parseInt(limit));
        gauge.name("限额占比");
        gauge.data(currentValue);
        option.series(gauge);
        return option;
    }

    /**
     * 没有设置限额比数据的图
     * @return
     */

    public static GsonOption genNoneChart(){
        GsonOption option = new GsonOption();
        option.tooltip().formatter("{a}:{c}");
        Gauge gauge = new Gauge();
        gauge.setMax(100);
        gauge.name("限额占比");
        gauge.data(0);
        option.series(gauge);
        return option;
    }
}
