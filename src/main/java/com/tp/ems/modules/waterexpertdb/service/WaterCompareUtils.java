package com.tp.ems.modules.waterexpertdb.service;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Series;
import com.tp.ems.modules.poweranalysis.dao.PowerAnalysisDao;
import com.tp.ems.modules.waterexpertdb.entity.ContrastData;
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
public class WaterCompareUtils {

    @Autowired
    private PowerAnalysisDao analysisDao;

    //同比 环比， 多项监测点
    public GsonOption barChart(Map<String,List<ContrastData>> dataAmountMap,String type){
        int len = dataAmountMap.size();

        GsonOption option = new GsonOption();
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.toolbox().show(true).feature(new MagicType(Magic.bar,Magic.line));
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
        int flag=0;
        int dataLen=0;
        for(int i=0;i<len;i++){

            List<ContrastData> dataAmountList = dataAmountMap.get(String.valueOf(i));
            if(dataLen ==0){
                dataLen  = dataAmountList.size();
            }
            int dataLenContrast = dataAmountList.size();
            Bar bar = new Bar();
            if(dataAmountList.size()<=0){
                continue;
            }
            String deviceId = dataAmountList.get(0).getDeviceId();
            String deviceName = analysisDao.get(deviceId).getName();

            String indate = new SimpleDateFormat("yyyy-MM-dd").format(dataAmountList.get(0).getIndate());
            bar.name(deviceName+"("+indate+")");
            option.legend().left(X.left).data(deviceName+"("+indate+")");

            if(type.equals("1")){
                for(ContrastData amount:dataAmountList){
                    String strDate  = sdf.format(amount.getIndate());
                    if(flag==0){
                        category.data(strDate);
                    }
                    if(flag==1){
                        List dateList = category.getData();
                        if(!dateList.contains(strDate)){
                            category.data(strDate);
                        }
                    }

                    bar.data(amount.getJljll());
                }
                flag=1;
            }
            if(type.equals("2")){
                for(ContrastData amount:dataAmountList){

                    String strDate  = sdf.format(amount.getIndate());
                    if(flag==0){
                        category.data(strDate);
                    }
                    if(flag==1){
                        List dateList = category.getData();
                        if(!dateList.contains(strDate)){
                            category.data(strDate);
                        }
                    }
                    bar.data(amount.getSsll());
                }
                flag=1;
            }

            if(dataAmountList.size()<=10){
                bar.barWidth(45);
            }
            barList.add(bar);
        }

        option.xAxis(category);
        option.series(barList);

        return option;
    }

//负荷 折线图  电量  柱状图
   public GsonOption barOneDeviceTwoInterval(List<ContrastData> dataAmountList,String type){
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

       if(type.equals("1")){
           for(int i=0;i<len;i++){
               ContrastData amount = dataAmountList.get(i);
               String inDate = sdf.format(amount.getIndate());/*sdf.format(charge.getIndate());*/
               category.data(inDate);
               bar.data(amount.getJljll());
           }
       }else if(type.equals("2")){
           for(int i=0;i<len;i++){
               ContrastData amount = dataAmountList.get(i);
               String inDate = sdf.format(amount.getIndate());/*sdf.format(charge.getIndate());*/
               category.data(inDate);
               bar.data(amount.getSsll());
           }
       }

        option.xAxis(category);
        option.series(bar);
        return option;
    }

}
