/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecmonth.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.tp.ems.modules.energyelecday.utils.HourElecComparator;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;
import com.tp.ems.modules.energyelecyear.utils.MonthElecComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energyelecmonth.entity.EnergyElecMonth;
import com.tp.ems.modules.energyelecmonth.dao.EnergyElecMonthDao;

/**
 * 电表每月数据Service
 *
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class EnergyElecMonthService extends CrudService<EnergyElecMonthDao, EnergyElecMonth> {

    public EnergyElecMonth get(String id) {
        return super.get(id);
    }

    public List<EnergyElecMonth> findList(EnergyElecMonth energyElecMonth) {
        return super.findList(energyElecMonth);
    }

    public Page<EnergyElecMonth> findPage(Page<EnergyElecMonth> page, EnergyElecMonth energyElecMonth) {
        return super.findPage(page, energyElecMonth);
    }

    @Transactional(readOnly = false)
    public void save(EnergyElecMonth energyElecMonth) {
        super.save(energyElecMonth);
    }

    @Transactional(readOnly = false)
    public void delete(EnergyElecMonth energyElecMonth) {
        super.delete(energyElecMonth);
    }

    public List<EnergyElecMonth> findByYear(String deviceId, Date inDate) {
        EnergyElecMonth energyElecMonth = new EnergyElecMonth();
        energyElecMonth.setDeviceId(deviceId);
        energyElecMonth.setDataTime(inDate);
        return findList(energyElecMonth);
    }


    public GsonOption genMonthChart(EnergyElecMonth energyElecMonth, List<EnergyElecMonth> elecMonths) {
        GsonOption option = new GsonOption();
        String optionLegend = titleConvert(energyElecMonth, "电量");
        double sum = MonthElecComparator.getCount(elecMonths, "sum");
        option.title().text(optionLegend).subtext("总电量：" + sum+"(kWh)").left(X.center);
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend().data("电量").left(X.left);
        option.toolbox().show(true).right(X.right).feature(new MagicType(Magic.bar, Magic.line));
        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（kWh）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();
        //柱状数据
        Bar bar = new Bar("电量");
        //显示数字
        bar.itemStyle().normal().label().show(true);
        int len = elecMonths.size();
        if (len <= 10) {
            bar.barWidth(45);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for (EnergyElecMonth month : elecMonths) {
            String strDate = sdf.format(month.getDataTime());
            category.data(strDate);
            bar.data(month.getRealData());
        }
        option.xAxis(category);
        option.series(bar);
        System.out.println(JSON.toJSON(option));
        return option;
    }

    public String titleConvert(EnergyElecMonth elecMonth, String tab) {
        String title = "";
        String year = elecMonth.getDataYear();
        title = "年" + tab + "（" + year + "-01～" + year + "-12）";
        return title;
    }
}