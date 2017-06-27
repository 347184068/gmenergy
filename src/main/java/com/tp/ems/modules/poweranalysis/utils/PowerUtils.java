package com.tp.ems.modules.poweranalysis.utils;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tepusoft on 2016/11/2.
 */
public class PowerUtils {
    // chart title
    public String titleConvert(ElecDataAmount dataAmount,String tab){
        String type = dataAmount.getType();
        String title ="";
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataAmount.getInDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

       // String optionLegend = "年电量（"+year+"-01 ～"+year+"-12）";
        switch (type){
            case "1":
                title="年"+tab+"（"+year+"-01～"+year+"-12）";
                break;
            case "2":
                title="月"+tab+"（"+month+"-01～"+month+"-"+getMonthDay(month,year)+"）";
                break;
            case "3":
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

    // 年电量 月电量 柱状图

    public GsonOption BarMonth(List<ElecDataAmount> dataAmounts,ElecDataAmount dataAmount){
        GsonOption option = new GsonOption();
        String optionLegend = titleConvert(dataAmount,"电量");
        option.title().text(optionLegend).left(X.center);
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend().data("总-电量").left(X.right);
        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（kWh）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();

        //柱状数据
        Bar bar = new Bar("总-电量");
        //显示数字
        bar.itemStyle().normal().label().show(true);


        Calendar cal = Calendar.getInstance();
        cal.setTime(dataAmount.getInDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        /*String[] category_year ={year+"-01",year+"-02",year+"-03",year+"-04",year+"-05",year+"-06"
                ,year+"-07",year+"-08",year+"-09",year+"-10",year+"-11",year+"-12"};*/
        List<String> categoryYear = new ArrayList<String>();
        for(int i=1;i<=12;i++){
            categoryYear.add(year+String.valueOf(i));
        }
        int days = getMonthDay(month,year);
      //  String[] category_month=new String[days];
        List<String> categoryMonth = new ArrayList<String>();
        for(int i=0;i<days;i++){
            int j=(i+1);
            categoryMonth.add(String.valueOf(j));
            /*int j=(i+1);
            category_month[i]=String.valueOf(j);*/
        }

        String type=dataAmount.getType();

        int len = dataAmounts.size();

        if(type.equals("1")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            for(ElecDataAmount amount:dataAmounts){
                String strDate  = sdf.format(amount.getInDate());
                category.data(strDate);
                bar.data(amount.getElectricity());
            }
            if(len<12){
                for(int i=len;i<12;i++){
                    category.data(categoryYear.get(i));
                    bar.data(0);
                }
            }
        }
        if(type.equals("2")){
            SimpleDateFormat sdf = new SimpleDateFormat("d");
            for(ElecDataAmount amount:dataAmounts){
                String strDate  = sdf.format(amount.getInDate());
                category.data(strDate);
                bar.data(amount.getElectricity());
            }
            if(len<days){
                for(int i=len;i<days;i++){
                    category.data(categoryMonth.get(i));
                    bar.data(0);
                }
            }
        }
        option.xAxis(category);
        option.series(bar);
        return option;
    }


    //年负荷 、月负荷 折线图
    public GsonOption lineYearMonth(List<ElecDataAmount> dataAmounts,ElecDataAmount dataAmount){

        GsonOption option = new GsonOption();
        String optionLegend = titleConvert(dataAmount, "负荷");
        option.title().text(optionLegend).left(X.center);
        option.legend().left(X.right).data("最高-负荷","最低-负荷","平均-负荷");
        option.tooltip().trigger(Trigger.axis);
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位：（kw）");
        valueAxis.axisLabel().formatter("{value}");
        option.yAxis(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.axisLine().onZero(true);
        categoryAxis.axisLabel().formatter("{value}");
        categoryAxis.boundaryGap(false);

        Line line_max = new Line();
        line_max.name("最高-负荷");
        Line line_min = new Line();
        line_min.name("最低-负荷");
        Line line_avg = new Line();
        line_avg.name("平均-负荷");



        Calendar cal = Calendar.getInstance();
        cal.setTime(dataAmount.getInDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        List<String> categoryYear = new ArrayList<String>();
        for(int i=1;i<=12;i++){
            categoryYear.add(year+String.valueOf(i));
        }
        int days = getMonthDay(month,year);

        List<String> categoryMonth = new ArrayList<String>();
        for(int i=0;i<days;i++){
            int j=(i+1);
            categoryMonth.add(String.valueOf(j));
        }
        String type=dataAmount.getType();

        int len = dataAmounts.size();

        if(type.equals("1")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            for(ElecDataAmount amount:dataAmounts){
                String strDate  = sdf.format(amount.getInDate());
                categoryAxis.data(strDate);
                line_max.data(amount.getMaxLoad());
                line_min.data(amount.getMinLoad());
                line_avg.data(amount.getAvgLoad());
            }
            if(len<12){
                for(int i=len;i<12;i++){
                    categoryAxis.data(categoryYear.get(i));
                    line_avg.data(0);
                    line_max.data(0);
                    line_min.data(0);
                }
            }
        }
        if(type.equals("2")){
            SimpleDateFormat sdf = new SimpleDateFormat("d");
            for(ElecDataAmount amount:dataAmounts){
                String strDate  = sdf.format(amount.getInDate());
                categoryAxis.data(strDate);
                line_max.data(amount.getMaxLoad());
                line_min.data(amount.getMinLoad());
                line_avg.data(amount.getAvgLoad());
            }
            if(len<days){
                for(int i=len;i<days;i++){
                    categoryAxis.data(categoryMonth.get(i));
                    line_avg.data(0);
                    line_max.data(0);
                    line_min.data(0);
                }
            }
        }
        option.xAxis(categoryAxis);
        option.series(line_max,line_avg,line_min);
        return option;
    }


    // 实时、日负荷 折线图  值在表hour_amount中 maxLoad 字段取
    public GsonOption lineDay(List<ElecDataAmount> dataAmounts,ElecDataAmount dataAmount){
        GsonOption option = new GsonOption();
        String optionLegend = titleConvert(dataAmount,"负荷");
        option.title().text(optionLegend).left(X.center);
        option.legend().left(X.right).data("负荷");
        option.tooltip().trigger(Trigger.axis);
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位：（kw）");
        valueAxis.axisLabel().formatter("{value}");
        option.yAxis(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.axisLine().onZero(true);
        categoryAxis.axisLabel().formatter("{value}");
        categoryAxis.boundaryGap(false);

        Line line_max = new Line();
        line_max.name("负荷");
       /* Line line_min = new Line();
        line_min.name("最低-负荷");
        Line line_avg = new Line();
        line_avg.name("平均-负荷");*/

        List<String> categoryDay = new ArrayList<String>();
        try {
            categoryDay = getCateInterval();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int len = dataAmounts.size();
        int lenth = len-1;
        int hour = 0;
        int minute = 0;
        if(len>0){
            System.out.println(lenth);
            Date date = dataAmounts.get(lenth).getInDate();

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
             hour = cal.get(Calendar.HOUR_OF_DAY);
             minute = cal.get(Calendar.MINUTE);
        }



        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        for(ElecDataAmount amount:dataAmounts){
            String strDate  = sdf.format(amount.getInDate());
            categoryAxis.data(strDate);
            line_max.data(amount.getMaxLoad());
            /*line_min.data(amount.getMinLoad());
            line_avg.data(amount.getAvgLoad());*/
        }

        if(hour!=23 || minute!=45){
            for(int i=len;i<categoryDay.size();i++){
                categoryAxis.data(categoryDay.get(i));
                //line_avg.data(0);
                line_max.data(0);
                //line_min.data(0);
            }
        }
        option.xAxis(categoryAxis);
        option.series(line_max);
        System.out.println(JSON.toJSON(option));
        return option;
    }


    //实时 日电量图 柱状图
    public GsonOption BarDay(List<ElecDataAmount> dataAmounts,ElecDataAmount dataAmount){
        GsonOption option = new GsonOption();
        String optionLegend = titleConvert(dataAmount,"电量");
        option.title().text(optionLegend).left(X.center);
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend().data("总-电量").left(X.right);
        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（kWh）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();

        //柱状数据
        Bar bar = new Bar("总-电量");
        //显示数字
        bar.itemStyle().normal().label().show(true);


        List<String> categoryDay = new ArrayList<String>();
        for(int i=0;i<23;i++){
            categoryDay.add(String.valueOf(i));
        }

        int len = dataAmounts.size();
       /* int lenth = len-1;
        int hour=0;
        if(len>0){
            Date date = dataAmounts.get(lenth).getInDate();

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            hour = cal.get(Calendar.HOUR_OF_DAY);

        }*/

        String type = dataAmount.getType();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        if(type.equals("1")) {
            sdf = new SimpleDateFormat("yyyy-MM");
        }
        if(type.equals("2")){
            sdf = new SimpleDateFormat("MM-dd");
        }
        for(ElecDataAmount amount:dataAmounts){

            String strDate  = sdf.format(amount.getInDate());
            category.data(strDate);
            bar.data(amount.getElectricity());
        }

        if(len<24){
            for(int i=len;i<categoryDay.size();i++){
                category.data(categoryDay.get(i));
                bar.data(0);
            }
        }
        option.xAxis(category);
        option.series(bar);
        System.out.println(JSON.toJSON(option));
        return option;
    }



    public List<String> getCateInterval() throws ParseException {
        List<String> list = new ArrayList<String>();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");//小写的mm表示的是分钟
        String dstr="00:00:00";
        Date date = sdf.parse(dstr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        list.add(hour+":"+minute);
        while (hour!=23 || minute!=45){
            cal.add(Calendar.MINUTE,15);
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
            list.add(hour+":"+minute);
           // System.out.println(hour+":"+minute);
        }
        return list;
    }

    public GsonOption elecBar(List<ElecDataAmount> dataAmounts,ElecDataAmount dataAmount){
        GsonOption option = new GsonOption();
        String optionLegend = titleConvert(dataAmount,"电量");
        option.title().text(optionLegend).left(X.center);
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend().data("电量").left(X.right);
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



        int len = dataAmounts.size();
        if(len<=10){
            bar.barWidth(45);
        }
        String type=dataAmount.getType();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        if(type.equals("1")) {
            sdf = new SimpleDateFormat("yyyy-MM");
        }
        if(type.equals("2")){
            sdf = new SimpleDateFormat("MM-dd");
        }
       // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        for(ElecDataAmount amount:dataAmounts){
            String strDate  = sdf.format(amount.getInDate());
            category.data(strDate);
            bar.data(amount.getElectricity());
        }
        option.xAxis(category);
        option.series(bar);
        System.out.println(JSON.toJSON(option));
        return option;
    }

    public GsonOption elecLine(List<ElecDataAmount> dataAmounts,ElecDataAmount dataAmount){
        GsonOption option = new GsonOption();
        String optionLegend = titleConvert(dataAmount,"电量");
        option.title().text(optionLegend).left(X.center);
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend().data("电量").left(X.right);
        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（kWh）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();

        //柱状数据
        Line line = new Line("电量");





        int len = dataAmounts.size();

        String type=dataAmount.getType();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        if(type.equals("1")) {
            sdf = new SimpleDateFormat("yyyy-MM");
        }
        if(type.equals("2")){
            sdf = new SimpleDateFormat("MM-dd");
        }
        // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        for(ElecDataAmount amount:dataAmounts){
            String strDate  = sdf.format(amount.getInDate());
            category.data(strDate);
            line.data(amount.getElectricity());
        }
        option.xAxis(category);
        option.series(line);
        System.out.println(JSON.toJSON(option));
        return option;
    }

}
