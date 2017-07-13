package com.tp.ems.modules.energywaterhour.service;

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
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energyelecday.utils.HourElecComparator;
import com.tp.ems.modules.energyelechour.dao.EnergyElecHourDao;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;
import com.tp.ems.modules.energywaterday.utils.HourWaterComparator;
import com.tp.ems.modules.energywaterhour.dao.EnergyWaterHourDao;
import com.tp.ems.modules.energywaterhour.entity.EnergyWaterHour;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EnergyWaterHourService extends CrudService<EnergyWaterHourDao, EnergyWaterHour> {
    public EnergyWaterHour get(String id) {
        return super.get(id);
    }

    public List<EnergyWaterHour> findList(EnergyWaterHour energyWaterHour) {
        return super.findList(energyWaterHour);
    }

    public Page<EnergyWaterHour> findPage(Page<EnergyWaterHour> page, EnergyWaterHour energyWaterHour) {
        return super.findPage(page, energyWaterHour);
    }

    @Transactional(readOnly = false)
    public void save(EnergyWaterHour energyWaterHour) {
        super.save(energyWaterHour);
    }

    @Transactional(readOnly = false)
    public void delete(EnergyWaterHour energyWaterHour) {
        super.delete(energyWaterHour);
    }

    public GsonOption genHourChart(EnergyWaterHour waterHour, List<EnergyWaterHour> waterHours) {
        GsonOption option = new GsonOption();
        String optionLegend = titleConvert(waterHour,"水量","day");
        double sum = HourWaterComparator.getCount(waterHours,"sum");
        option.title().text(optionLegend).subtext("总水量："+sum+"(吨)").left(X.center);
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend().data("水量").left(X.left);
        option.toolbox().show(true).right(X.right).feature(new MagicType(Magic.bar,Magic.line));
        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（吨）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();
        //柱状数据
        Bar bar = new Bar("水量");
        //显示数字
        bar.itemStyle().normal().label().show(true);
        int len = waterHours.size();
        if(len<=10){
            bar.barWidth(45);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for(EnergyWaterHour hour:waterHours){
            String strDate  = sdf.format(hour.getDataTime());
            category.data(strDate);
            bar.data(hour.getData());
        }
        option.xAxis(category);
        option.series(bar);
        System.out.println(JSON.toJSON(option));
        return option;
    }

    public String titleConvert(EnergyWaterHour elecHour,String tab,String type){
        String title ="";
        Calendar cal = Calendar.getInstance();
        cal.setTime(elecHour.getDataTime());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        switch (type){
            case "day":
                title="日"+tab+"（"+month+"-"+day+" 00:00～"+month+"-"+day+" 23:59）";
                break;
        }
        return title;
    }

}
