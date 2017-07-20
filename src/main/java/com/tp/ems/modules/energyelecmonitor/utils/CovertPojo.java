package com.tp.ems.modules.energyelecmonitor.utils;

import com.tp.ems.common.utils.DateUtils;
import com.tp.ems.modules.energyelecmonitor.entity.EnergyElecRawdata;
import com.tp.ems.modules.energyelecmonitor.entity.ExportElec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XuYunXuan
 * @ClassName: CovertPojo
 * @Description:
 * @date 2017-07-20 11:06
 */
public class CovertPojo {


    public static ExportElec covert(EnergyElecRawdata elecRawdata) {
        ExportElec exportElec = new ExportElec();
        exportElec.setDeviceId(elecRawdata.getDeviceId());
        exportElec.setDeviceName(elecRawdata.getDeviceName());
        exportElec.setDataTime(DateUtils.formatDateTime(elecRawdata.getDataTime()));
        exportElec.setRawData(elecRawdata.getRawData());
        exportElec.setRatio(elecRawdata.getRatio() + "");
        exportElec.setRealData(elecRawdata.getRealData() + "");
        exportElec.setaU(elecRawdata.getaU());
        exportElec.setbU(elecRawdata.getbU());
        exportElec.setcU(elecRawdata.getcU());
        exportElec.setaI(elecRawdata.getaI());
        exportElec.setbI(elecRawdata.getbI());
        exportElec.setcI(elecRawdata.getcI());
        exportElec.setpF(elecRawdata.getpF());
        exportElec.setP(elecRawdata.getP());
        return exportElec;
    }


    public static List<ExportElec> covertList(List<EnergyElecRawdata> elecRawdatas) {
        List<ExportElec> list = new ArrayList<ExportElec>();
        for (EnergyElecRawdata rawdata : elecRawdatas) {
            list.add(covert(rawdata));
        }
        return list;
    }


}
