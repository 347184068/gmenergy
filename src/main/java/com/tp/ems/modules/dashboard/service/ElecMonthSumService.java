package com.tp.ems.modules.dashboard.service;

import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Gauge;
import com.tp.ems.common.service.TreeService;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.poweranalysis.dao.ElecDataAmountDao;
import com.tp.ems.modules.poweranalysis.dao.PowerAnalysisDao;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;
import com.tp.ems.modules.poweranalysis.utils.PowerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tepusoft on 2016/10/29.
 */
@Service
@Transactional(readOnly = true)
public class ElecMonthSumService  {
    @Autowired
    private ElecDataAmountDao dao;


    public GsonOption getElecDashBoard(ElecDataAmount dataAmount){
        GsonOption option = new GsonOption();
        option.tooltip().formatter("{a}:{c}");
        dataAmount = dao.getMonthSum(dataAmount);
        if(dataAmount == null ||dataAmount.getElectricity()==null){
            return null;
        }
        Gauge gauge = new Gauge();
        gauge.name("电量");
        gauge.data(dataAmount.getElectricity());
        option.series(gauge);
        return  option;
    }




}
