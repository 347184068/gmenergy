package com.tp.ems.modules.watercharge.service;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.energycharge.entity.EnergyCharge;
import com.tp.ems.modules.poweranalysis.dao.PowerAnalysisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tepusoft on 2016/11/9.
 */
@Service
public class WaterChargeUtil {
    @Autowired
    private PowerAnalysisDao analysisDao;


    /**
     * 同一监测点不同时段电量柱状图
     * @param chargeList
     * @return
     */
    public GsonOption oneDeviceElecBar(List<EnergyCharge> chargeList){
        GsonOption option = new GsonOption();
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（方）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();
        category.name("月份");

        option.legend().left(X.right).data("流量");
        Bar bar = new Bar();
        bar.name("流量");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        int len = chargeList.size();
        if(len<5){
            bar.barWidth(50);
        }

        for(int i=0;i<len;i++){
            EnergyCharge charge = chargeList.get(i);
            String inDate = charge.getIndate();/*sdf.format(charge.getIndate())*/
            category.data(inDate);
            bar.data(charge.getSumElec());
        }
        option.xAxis(category);
        option.series(bar);
        return option;
    }

    /**
     * 同一监测点不同时段电费柱状图
     * @param chargeList
     * @return
     */
    public GsonOption oneDeviceChargeBar(List<EnergyCharge> chargeList){
        GsonOption option = new GsonOption();
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（元）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();
        category.name("月份");

        option.legend().left(X.right).data("水费");
        Bar bar = new Bar();
        bar.name("水费");
        bar.itemStyle().normal().color("#60C0DD");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        int len = chargeList.size();
        if(len<5){
            bar.barWidth(50);
        }
        for(int i=0;i<len;i++){
            EnergyCharge charge = chargeList.get(i);
            String inDate = charge.getIndate();/*sdf.format(charge.getIndate());*/
            category.data(inDate);
            bar.data(charge.getTotalPrice());
        }
        option.xAxis(category);
        option.series(bar);
        return option;
    }


    /**
     * 多监测点不同时段电量柱状图
     * @param chargeList
     * @param paramIds
     * @return
     */
    public GsonOption diffDeviceElecBar(List<EnergyCharge> chargeList,List<String> paramIds){
        GsonOption option = new GsonOption();
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（方）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();
        category.name("流量");

        option.legend().left(X.right).data("流量");
        Bar bar = new Bar();
        bar.name("流量");

        int len = chargeList.size();
        if(len<5){
            bar.barWidth(50);
        }

        for(int i=0;i<len;i++){
            EnergyCharge charge = chargeList.get(i);
            String deviceName = analysisDao.get(charge.getDeviceId()).getName();
            category.data(deviceName);
            bar.data(charge.getSumElec());
        }


        int len_ids = paramIds.size();
        if(len<len_ids){
            List<String> deviceNames = getNamesByDiffIds(chargeList,paramIds);
            for(int i = len;i<len_ids;i++){
                int j=i-len;
                category.data(deviceNames.get(j));
                bar.data(0);
            }
        }
        option.xAxis(category);
        option.series(bar);
        return option;
    }

    public GsonOption diffDeviceChargeBar(List<EnergyCharge> chargeList,List<String> paramIds){
        GsonOption option = new GsonOption();
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        //纵轴为值轴
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.name("单位（元）");
        option.yAxis(valueAxis);
        // x轴为类目轴
        CategoryAxis category = new CategoryAxis();
        category.name("水费");

        option.legend().left(X.right).data("水费");
        Bar bar = new Bar();
        bar.itemStyle().normal().color("#60C0DD");
        bar.name("水费");

        int len = chargeList.size();
        if(len<5){
            bar.barWidth(50);
        }

        for(int i=0;i<len;i++){
            EnergyCharge charge = chargeList.get(i);
            String deviceName = analysisDao.get(charge.getDeviceId()).getName();
            category.data(deviceName);
            bar.data(charge.getTotalPrice());
        }
        int len_ids = paramIds.size();
        if(len<len_ids){
            List<String> deviceNames = getNamesByDiffIds(chargeList,paramIds);
            for(int i = len;i<len_ids;i++){
                int j=i-len;
                category.data(deviceNames.get(j));
                bar.data(0);
            }
        }

        option.xAxis(category);
        option.series(bar);
        return option;
    }



    public List<String> compareDiffIds(List<String> resultIds,List<String> paramIds){
        List<String> list = new ArrayList<>();
        for(String str:paramIds){
            if(!resultIds.contains(str)){
                list.add(str);
            }
        }
        return list;
    }

    public List<String> getNamesByDiffIds(List<EnergyCharge> chargeList,List<String> paramIds){
        //resultIds:查询出的Ids
        List<String> resultIds = new ArrayList<>();
        int len = chargeList.size();
        for(int i=0;i<len;i++){
            resultIds.add(chargeList.get(i).getDeviceId());
        }
        // 没有数据的监测点id
        List<String> idList = compareDiffIds(resultIds,paramIds);

        List<String> names = new ArrayList<>();
        List<Monitordevices> devicesList = new ArrayList<>();
        devicesList = analysisDao.getDeviceListByIds(idList);
        for(Monitordevices device:devicesList){
            names.add(device.getName());
        }
        return names;
    }
}
