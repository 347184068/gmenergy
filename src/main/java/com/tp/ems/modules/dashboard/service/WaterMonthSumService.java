package com.tp.ems.modules.dashboard.service;

import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Gauge;
import com.tp.ems.modules.poweranalysis.dao.ElecDataAmountDao;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;
import com.tp.ems.modules.wateranlysis.dao.WaterAnalysisDao;
import com.tp.ems.modules.wateranlysis.entity.WaterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tepusoft on 2016/10/29.
 */
@Service
@Transactional(readOnly = true)
public class WaterMonthSumService {
    @Autowired
    private WaterAnalysisDao dao;


    public GsonOption getWaterMonthSum(WaterData waterData){
        GsonOption option = new GsonOption();
        option.tooltip().formatter("{a}:{c}");
        waterData = dao.getWaterMonthSum(waterData);
        if(waterData == null ||waterData.getJljll().equals(0.00)){
            return null;
        }
        Gauge gauge = new Gauge();
        gauge.name("累计水量");
        gauge.data(waterData.getJljll());
        option.series(gauge);
        return  option;
    }




}
