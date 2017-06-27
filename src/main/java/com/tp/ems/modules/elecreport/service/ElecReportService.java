package com.tp.ems.modules.elecreport.service;

import com.github.abel533.echarts.json.GsonOption;
import com.google.common.collect.Lists;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.TreeService;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.elecreport.dao.ElecPojoDao;
import com.tp.ems.modules.elecreport.entity.ElecPojo;
import com.tp.ems.modules.poweranalysis.dao.ElecDataAmountDao;
import com.tp.ems.modules.poweranalysis.dao.PowerAnalysisDao;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;
import com.tp.ems.modules.poweranalysis.utils.PowerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tepusoft on 2016/10/29.
 */
@Service
@Transactional(readOnly = true)
public class ElecReportService  {

    @Autowired
    private ElecPojoDao dao;
    public List<ElecPojo> findElecList(ElecPojo elecPojo){
        List<ElecPojo> elecPojoList = Lists.newArrayList();
        elecPojoList = dao.getElecList(elecPojo);
        List<ElecPojo> list = Lists.newArrayList();
        String name="";
        for(ElecPojo pojo:elecPojoList){
            if(pojo.getLinesName().equals(name)){
                continue;
            }
            ElecPojo elec = new ElecPojo();
            name=pojo.getLinesName();
            elec.setLinesName(name);
            String val="";//电流
            String vol="";//电压
            for(ElecPojo obj:elecPojoList){
                if(obj.getLinesName().equals(pojo.getLinesName())){
                    String type = obj.getType();
                    double value = obj.getValue();
                    switch (type){
                        case "01":
                            val+=roundTwo(value);
                            elec.setElectricity(val);
                            break;
                        case "02":
                            val+=","+roundTwo(value);
                            elec.setElectricity(val);
                            break;
                        case "03":
                            val+=","+roundTwo(value);
                            elec.setElectricity(val);
                            break;
                        case "04":
                            vol+=roundTwo(value);
                            elec.setVoltage(vol);
                            break;
                        case "05":
                            vol+=","+roundTwo(value);
                            elec.setVoltage(vol);
                            break;
                        case "06":
                            vol+=","+roundTwo(value);
                            elec.setVoltage(vol);
                            break;
                        case "07":
                            elec.setPowerFactor(Double.parseDouble(roundTwo(value)));
                            break;
                        case "08":
                            elec.setInstantaneousValue(Double.parseDouble(roundTwo(value)));
                            break;
                        case "09":
                            elec.setCumulativePower(Double.parseDouble(roundTwo(value)));
                            break;
                        default:break;
                    }
                }else{
                    continue;
                }
            }
            list.add(elec);
        }
        return list;
    }



    public String roundTwo(double val){
        DecimalFormat df   =new   DecimalFormat("#0.00");
        String value = df.format(val);
        return value;
    }

}
