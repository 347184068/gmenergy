package com.tp.ems.modules.energyelecmonitor.entity;

import com.tp.ems.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * @author XuYunXuan
 * @ClassName: ExportElec
 * @Description:
 * @date 2017-07-20 11:04
 */
public class ExportElec {
    private String deviceId;        // 设备ID
    private String rawData;        // 设备采集原始数据
    private String p;//瞬时有功功率
    private String pF; //功率因数
    private String aU;//a相电压
    private String bU; //b相电压
    private String cU; //c相电压
    private String aI;//a相电流
    private String bI; //b相电流
    private String cI; //c相电流
    private String dataTime;        // 采集数 据时间
    private String deviceName; //设备名称
    private String ratio;//设备倍率
    private String realData;


    @ExcelField(title="电表号", align=2, sort=1)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    @ExcelField(title="电表显示电量(度)", align=2, sort=4)
    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
    @ExcelField(title="瞬时有功功率(瓦)", align=2, sort=14)
    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    @ExcelField(title="功率因数", align=2, sort=13)
    public String getpF() {
        return pF;
    }

    public void setpF(String pF) {
        this.pF = pF;
    }
    @ExcelField(title="A相电压(伏)", align=2, sort=7)
    public String getaU() {
        return aU;
    }

    public void setaU(String aU) {
        this.aU = aU;
    }
    @ExcelField(title="B相电压(伏)", align=2, sort=8)
    public String getbU() {
        return bU;
    }

    public void setbU(String bU) {
        this.bU = bU;
    }
    @ExcelField(title="C相电压(伏)", align=2, sort=9)
    public String getcU() {
        return cU;
    }

    public void setcU(String cU) {
        this.cU = cU;
    }
    @ExcelField(title="A相电压(安)", align=2, sort=10)
    public String getaI() {
        return aI;
    }

    public void setaI(String aI) {
        this.aI = aI;
    }
    @ExcelField(title="B相电压(安)", align=2, sort=11)
    public String getbI() {
        return bI;
    }

    public void setbI(String bI) {
        this.bI = bI;
    }
    @ExcelField(title="C相电压(安)", align=2, sort=12)
    public String getcI() {
        return cI;
    }

    public void setcI(String cI) {
        this.cI = cI;
    }
    @ExcelField(title="时间(小时)", align=2, sort=3)
    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
    @ExcelField(title="电表名称", align=2, sort=2)
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    @ExcelField(title="倍率", align=2, sort=5)
    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
    @ExcelField(title="用电量(度)", align=2, sort=6)
    public String getRealData() {
        return realData;
    }

    public void setRealData(String realData) {
        this.realData = realData;
    }
}
