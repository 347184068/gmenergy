package com.tp.ems.modules.wateranlysis.utils;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.tp.ems.modules.wateranlysis.entity.WaterData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tepusoft on 2017/1/6.
 */
public class WaterUtils {
    public String titleConvert(WaterData waterData,String tab){
        String type = waterData.getType();
        String title ="";
        Calendar cal = Calendar.getInstance();
        cal.setTime(waterData.getInDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // String optionLegend = "年电量（"+year+"-01 ～"+year+"-12）";
        switch (type){
            case "1":
                title="年"+tab;
                /*title="年"+tab+"（"+year+"-01～"+year+"-12）";*/
                break;
            case "2":
                title="月"+tab;
                break;
            case "3":case "4":
                title="日"+tab;
                break;
            default:break;
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

    public String convertParam(String param){
        String title = "";
        if(param.equals("1")){
            title = "净累计流量";
        }else if(param.equals("2")){
            title = "瞬时流量";
        }else if(param.equals("3")){
            title="消耗流量";
        }
        return title;
    }
    public GsonOption dataToBar(List<WaterData> waterDataList,WaterData waterData){
        String type = waterData.getType();
        String params = waterData.getParams();
        String param = convertParam(params);

        GsonOption option = new GsonOption();
       /* String optionLegend = titleConvert(waterData, "负荷");*/
        String optionLegend = titleConvert(waterData, param);
        option.title().text(optionLegend).left(X.center);
        option.tooltip().trigger(Trigger.axis);
        option.legend().left(X.right).data(param);

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位：（方）");
        valueAxis.axisLabel().formatter("{value}");
        option.yAxis(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        /*categoryAxis.axisLine().onZero(true);
        categoryAxis.axisLabel().formatter("{value}");
        categoryAxis.boundaryGap(false);*/

        Bar bar = new Bar(param);
        bar.itemStyle().normal().label().show(true);

        int len = waterDataList.size();
        if(len<=10){
            bar.barWidth(45);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        for(WaterData data:waterDataList){
            if(type.equals("1")){
                sdf = new SimpleDateFormat("yyyy-MM");
            }
            if(type.equals("2")){
                sdf = new SimpleDateFormat("MM-dd");
            }
            String strDate  = sdf.format(data.getInDate());
            categoryAxis.data(strDate);
            if(param.equals("净累计流量")){
                bar.data(data.getJljll());
            }else if(param.equals("瞬时流量")){
                bar.data(data.getSsll());
            }else if(param.equals("消耗流量")){
                bar.data(data.getUseAmount());
            }
        }
        option.xAxis(categoryAxis);
        option.series(bar);
        return option;
    }

    public GsonOption dataToLine(List<WaterData> waterDataList,WaterData waterData){
        String type = waterData.getType();
        String params = waterData.getParams();
        String param = convertParam(params);

        GsonOption option = new GsonOption();
       /* String optionLegend = titleConvert(waterData, "负荷");*/
        String optionLegend = titleConvert(waterData, param);
        option.title().text(optionLegend).left(X.center);
        option.tooltip().trigger(Trigger.axis);
        option.legend().left(X.right).data(param);

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位：（方）");
        valueAxis.axisLabel().formatter("{value}");
        option.yAxis(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.axisLine().onZero(true);
        categoryAxis.axisLabel().formatter("{value}");
        categoryAxis.boundaryGap(false);


        Line line = new Line(param);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        for(WaterData data:waterDataList){
            if(type.equals("1")){
                sdf = new SimpleDateFormat("MM-dd");
            }
            if(type.equals("2")){
                sdf = new SimpleDateFormat("MM-dd");
            }
            String strDate  = sdf.format(data.getInDate());
            categoryAxis.data(strDate);
            if(param.equals("净累计流量")){
                line.data(data.getJljll());
            }else if(param.equals("瞬时流量")){
                line.data(data.getSsll());
            }else if(param.equals("消耗流量")){
                line.data(data.getUseAmount());
            }
        }
        option.xAxis(categoryAxis);
        option.series(line);
        return option;
    }


}
