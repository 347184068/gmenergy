package com.tp.ems.modules.wateranlysis.service;

import com.github.abel533.echarts.json.GsonOption;
import com.google.common.collect.Lists;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.poweranalysis.dao.ElecDataAmountDao;
import com.tp.ems.modules.poweranalysis.dao.PowerAnalysisDao;
import com.tp.ems.modules.wateranlysis.dao.WaterAnalysisDao;
import com.tp.ems.modules.wateranlysis.entity.WaterData;
import com.tp.ems.modules.wateranlysis.utils.WaterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tepusoft on 2016/10/29.
 */
@Service
@Transactional(readOnly = true)
public class WaterAnalysisService {


    @Autowired
    private WaterAnalysisDao waterAnalysisDao;
    @Autowired
    private ElecDataAmountDao elecDataAmountDao;

    @Autowired
    private PowerAnalysisDao powerAnalysisDao;

    public List<WaterData> getWaterDataList(WaterData waterData){
        List<WaterData> waterDataList = Lists.newArrayList();

        String type= waterData.getType();
        if(type.equals("4")){   //4 实时 3 日 2 月 1年
            waterDataList = waterAnalysisDao.getRealTimeWaterData(waterData);

        }else if(type.equals("3")){
            waterDataList = waterAnalysisDao.getDayWaterData(waterData);

        }else if(type.equals("2")){
            waterDataList = waterAnalysisDao.getMonthWaterData(waterData);

        }else if(type.equals("1")){
            waterDataList = waterAnalysisDao.getYearWaterData(waterData);
        }
        return waterDataList;
    }

    //组装GsonOption
    public GsonOption generateBar(WaterData waterData){
        WaterUtils utils = new WaterUtils();
        GsonOption option = new GsonOption();
        List<WaterData> waterDataList = Lists.newArrayList();
        waterDataList = getWaterDataList(waterData);
        if(waterDataList.size()==0){
            return null;
        }
        option = utils.dataToBar(waterDataList,waterData);
        return option;
    }
    //组装GsonOption
    public GsonOption generateLine(WaterData waterData){
        WaterUtils utils = new WaterUtils();
        GsonOption option = new GsonOption();
        List<WaterData> waterDataList = Lists.newArrayList();
        waterDataList = getWaterDataList(waterData);
        if(waterDataList.size()==0){
            return null;
        }
        option = utils.dataToLine(waterDataList,waterData);
        return option;
    }

    public List<Monitordevices> findDeviceListByIds(List<String> ids){
        List<Monitordevices> list = powerAnalysisDao.getDeviceListByIds(ids);
        return list;
    }
}
