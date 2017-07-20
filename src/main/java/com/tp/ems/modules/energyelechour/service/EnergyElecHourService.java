package com.tp.ems.modules.energyelechour.service;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.feature.Feature;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelecday.utils.HourElecComparator;
import com.tp.ems.modules.energyelechour.dao.EnergyElecHourDao;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.tp.ems.modules.energyelecday.utils.HourElecComparator.getCount;

/**
 * @Author XuYunXuan
 * @Date 2017/6/25 13:05
 */
@Service
@Transactional(readOnly = true)
public class EnergyElecHourService extends CrudService<EnergyElecHourDao, EnergyElecHour> {
    public EnergyElecHour get(String id) {
        return super.get(id);
    }

    public List<EnergyElecHour> findList(EnergyElecHour energyElecHour) {
        return super.findList(energyElecHour);
    }

    public Page<EnergyElecHour> findPage(Page<EnergyElecHour> page, EnergyElecHour energyElecHour) {
        return super.findPage(page, energyElecHour);
    }

    @Transactional(readOnly = false)
    public void save(EnergyElecHour energyElecHour) {
        super.save(energyElecHour);
    }

    @Transactional(readOnly = false)
    public void delete(EnergyElecHour energyElecHour) {
        super.delete(energyElecHour);
    }

    /**
     * 生成一天电量图表，以小时为单位
     * @return
     */
    public GsonOption genHourChart(EnergyElecHour elecHour, List<EnergyElecHour> elecHours){
        GsonOption option = new GsonOption();
        String optionLegend = titleConvert(elecHour,"电量","day");
        double sum = HourElecComparator.getCount(elecHours,"sum");
        option.title().text(optionLegend).subtext("总电量："+sum+"(kWh)").left(X.center);
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend().data("电量").left(X.left);
        option.toolbox().show(true).right(X.right).feature(new MagicType(Magic.bar,Magic.line));
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
        int len = elecHours.size();
        if(len<=10){
            bar.barWidth(45);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for(EnergyElecHour hour:elecHours){
            String strDate  = sdf.format(hour.getDataTime());
            category.data(strDate);
            bar.data(hour.getRealData());
        }
        option.xAxis(category);
        option.series(bar);
        System.out.println(JSON.toJSON(option));
        return  option;
    }

    public String titleConvert(EnergyElecHour elecHour,String tab,String type){
        String title ="";
        Calendar cal = Calendar.getInstance();
        cal.setTime(elecHour.getDataTime());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        switch (type){
            case "year":
                title="年"+tab+"（"+year+"-01～"+year+"-12）";
                break;
            case "month":
                title="月"+tab+"（"+month+"-01～"+month+"-"+getMonthDay(month,year)+"）";
                break;
            case "day":
                title="日"+tab+"（"+month+"-"+day+" 00:00～"+month+"-"+day+" 23:59）";
                break;
        }
        return title;
    }
    public int getMonthDay(int month,int year){
        int days = 0;
        switch (month){
            case 1:case 3:case 5:case 7:case 8:case 10:case 12:
                days=31;
                break;
            case 2:
                if((year%4==0 && year%100!=0) || year%400==0){
                    days=29;
                }else {
                    days=28;
                }
                break;
            default:
                days=30;
                break;
        }
        return days;
    }
}
