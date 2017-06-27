package com.tp.ems.modules.poweranalysis.service;

import com.github.abel533.echarts.json.GsonOption;
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
public class PowerAnalysisService extends TreeService<PowerAnalysisDao,Monitordevices> {


    @Autowired
    private ElecDataAmountDao elecDataAmountDao;

    @Autowired
    private PowerAnalysisDao powerAnalysisDao;


    public List<ElecDataAmount> getDayLoadData(){
        return null;
    }

    // 负荷chart 折线图生成

    /**
     * type:1 年负荷数据  type:2 月负荷数据 type:3 日负荷数据 type :4 实时负荷数据
     * @param dataAmount
     * @return
     */
    public GsonOption getYearLoadData(ElecDataAmount dataAmount){
        PowerUtils utils = new PowerUtils();
        List<ElecDataAmount> dataAmounts = new ArrayList<ElecDataAmount>();
        String type= dataAmount.getType();
        GsonOption option = new GsonOption();
        if(type.equals("1")){
            dataAmounts = elecDataAmountDao.getYearPowerData(dataAmount);
            option = utils.lineYearMonth(dataAmounts, dataAmount);
        }else if(type.equals("2")){
            dataAmounts = elecDataAmountDao.getMonthPowerData(dataAmount);
            option = utils.lineYearMonth(dataAmounts, dataAmount);
        }else if(type.equals("3")){
            dataAmounts = elecDataAmountDao.getDayPowerData(dataAmount);
            option = utils.lineDay(dataAmounts, dataAmount);
        }else if(type.equals("4")){
            dataAmounts = elecDataAmountDao.getRealTimePowerData(dataAmount);
            option = utils.lineDay(dataAmounts, dataAmount);
        }
        return option;
    }


    //电量 柱状图生成

    /**
     * type:1 年电量 type:2 月电量 tyoe:3 日电量 type:4实时电量数据
     * @param dataAmount
     * @return
     */
    public GsonOption getYearElecData(ElecDataAmount dataAmount){

        PowerUtils utils = new PowerUtils();

        GsonOption option = new GsonOption();
        List<ElecDataAmount> dataAmounts = new ArrayList<ElecDataAmount>();
        String type= dataAmount.getType();
        if(type.equals("1")){
            dataAmounts = elecDataAmountDao.getYearPowerData(dataAmount);
           /* option = utils.BarMonth(dataAmounts,dataAmount);*/
            option = utils.elecBar(dataAmounts, dataAmount);
        }else if(type.equals("2")){
            dataAmounts = elecDataAmountDao.getMonthPowerData(dataAmount);
            /*option = utils.BarMonth(dataAmounts,dataAmount);*/
            option = utils.elecBar(dataAmounts,dataAmount);
        }else if(type.equals("3")){
            dataAmounts = elecDataAmountDao.getDayPowerData(dataAmount);
            option = utils.elecBar(dataAmounts,dataAmount);
           /* option = utils.BarDay(dataAmounts, dataAmount);*/
        }else if(type.equals("4")){
            dataAmounts = elecDataAmountDao.getRealTimeElecData(dataAmount);
            option = utils.elecLine(dataAmounts,dataAmount);
           /* option = utils.BarDay(dataAmounts, dataAmount);*/
        }

        if(dataAmounts.size()<=0){
            return null;
        }
        return option;
    }

    public List<Monitordevices> findDeviceListByIds(List<String> ids){
        List<Monitordevices> list = powerAnalysisDao.getDeviceListByIds(ids);
        return list;
    }
}
