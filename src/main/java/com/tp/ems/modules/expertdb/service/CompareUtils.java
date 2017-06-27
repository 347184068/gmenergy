package com.tp.ems.modules.expertdb.service;

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
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Series;
import com.tp.ems.modules.expertdb.entity.ContrastDataAmount;
import com.tp.ems.modules.poweranalysis.dao.PowerAnalysisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tepusoft on 2016/11/2.
 */
@Service
public class CompareUtils {

    @Autowired
    private PowerAnalysisDao analysisDao;

    //同比 环比， 多项监测点
    public GsonOption barChart(Map<String,List<ContrastDataAmount>> dataAmountMap){
        int len = dataAmountMap.size();

        GsonOption option = new GsonOption();
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);

        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（kWh）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();
        //柱状数据

        //显示数字
        //bar1.itemStyle().normal().label().show(true);

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        List<Series> barList = new ArrayList<>();
        for(int i=0;i<len;i++){

            List<ContrastDataAmount> dataAmountList = dataAmountMap.get(String.valueOf(i));
            Series bar = new Bar();
            if(dataAmountList.size()<=0){
                continue;
            }
            String deviceId = dataAmountList.get(0).getDeviceId();
            String deviceName = analysisDao.get(deviceId).getName();


            String indate = new SimpleDateFormat("yyyy-MM").format(dataAmountList.get(0).getIndate());
            bar.name(deviceName+"("+indate+")");
            option.legend().left(X.right).data(deviceName+"("+indate+")");

            for(ContrastDataAmount amount:dataAmountList){
                if(i==0){
                    String strDate  = sdf.format(amount.getIndate());
                    category.data(strDate);
                }
                bar.data(amount.getElectricity());
            }
            barList.add(bar);
        }
        option.xAxis(category);
        option.series(barList);

        return option;
    }

//负荷 折线图  电量  柱状图
    public GsonOption lineChart(Map<String,List<ContrastDataAmount>> dataAmountMap){

        GsonOption option = new GsonOption();

        option.tooltip().trigger(Trigger.axis);
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位：（kw）");
        valueAxis.axisLabel().formatter("{value}");
        option.yAxis(valueAxis);


        //todo 切换
        option.toolbox().show(true).feature(new MagicType(Magic.line,Magic.bar));


        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.axisLine().onZero(true);
        categoryAxis.axisLabel().formatter("{value}");
        categoryAxis.boundaryGap(false);

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        List<Series> lineList = new ArrayList<>();
        for(int i=0;i<dataAmountMap.size();i++){
            List<ContrastDataAmount> dataAmountList = dataAmountMap.get(String.valueOf(i));
            Series line = new Line();

            if(dataAmountList.size()<=0){
                continue;
            }

            String deviceId = dataAmountList.get(0).getDeviceId();
            String deviceName = analysisDao.get(deviceId).getName();


            String indate = new SimpleDateFormat("yyyy-MM").format(dataAmountList.get(0).getIndate());

            line.name(deviceName+"("+indate+")");
            option.legend().left(X.left).data(deviceName+"("+indate+")");
            for(ContrastDataAmount amount:dataAmountList){
                if(i==0){
                    String strDate  = sdf.format(amount.getIndate());
                    categoryAxis.data(strDate);
                }
                line.data(amount.getElectricity());
            }
            lineList.add(line);
        }

        option.xAxis(categoryAxis);
        option.series(lineList);
        return option;
    }





    public GsonOption barOneDeviceTwoInterval(List<ContrastDataAmount> dataAmountList){
        GsonOption option = new GsonOption();
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（kWh）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();
        category.name("月份");

        Bar bar = new Bar();

        //bar.itemStyle().normal().color("#60C0DD");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int len = dataAmountList.size();
        if(len<5){
            bar.barWidth(50);
        }
        String deviceName = dataAmountList.get(0).getDeviceName();
        option.legend().left(X.right).data(deviceName);

        bar.name(deviceName);


        for(int i=0;i<len;i++){
            ContrastDataAmount amount = dataAmountList.get(i);
            String inDate = sdf.format(amount.getIndate());/*sdf.format(charge.getIndate());*/
            category.data(inDate);
            bar.data(amount.getElectricity());
        }
        option.xAxis(category);
        option.series(bar);
        return option;
    }

    public GsonOption lineOneDeviceTwoInterval(List<ContrastDataAmount> dataAmountList){
        GsonOption option = new GsonOption();
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
        //String deviceName = dataAmountList.get(0).getDeviceName();
        option.legend().left(X.right).data("负荷");
        line_max.name("负荷");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for(ContrastDataAmount amount:dataAmountList){
            String strDate  = sdf.format(amount.getIndate());
            categoryAxis.data(strDate);
            line_max.data(amount.getMaxload());
        }
        option.xAxis(categoryAxis);
        option.series(line_max);
        System.out.println(JSON.toJSON(option));
        return option;
    }


}
