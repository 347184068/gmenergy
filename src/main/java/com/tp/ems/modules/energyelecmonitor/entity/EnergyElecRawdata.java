/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecmonitor.entity;

import com.tp.ems.common.utils.excel.annotation.ExcelField;
import com.tp.ems.modules.tools.RoundUtils;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.tp.ems.common.persistence.DataEntity;

import static com.sun.tools.doclint.Entity.nu;

/**
 * 电数据在线监控Entity
 *
 * @author 徐韵轩
 * @version 2017-07-07
 */
public class EnergyElecRawdata extends DataEntity<EnergyElecRawdata> {

    private static final long serialVersionUID = 1L;

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

    private Date dataTime;        // 采集数 据时间

    private String deviceName; //设备名称

    private Integer ratio;//设备倍率

    private Double realData;

    private Date startTime;

    private Date endTime;

    private Integer count;  //获取最新数据条数

    private String timeInterval;

    public EnergyElecRawdata() {
        super();
    }

    public EnergyElecRawdata(String id) {
        super(id);
    }

    @Length(min = 0, max = 255, message = "设备ID长度必须介于 0 和 255 之间")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Length(min = 1, max = 100, message = "设备采集原始数据长度必须介于 1 和 100 之间")
    public String getRawData() {
        String value = null;
        if (this.rawData != null) {
            value = RoundUtils.round(2,this.rawData);
        }
        return value;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getP() {
        String value = null;
        if (this.p != null) {
            value = RoundUtils.round(2,this.p);
        }
        return value;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getpF() {
        String value = null;
        if (this.pF != null) {
            value = RoundUtils.round(2,this.pF);
        }
        return value;
    }

    public void setpF(String pF) {
        this.pF = pF;
    }

    public String getaU() {
        String value = null;
        if (this.aU != null) {
            value = RoundUtils.round(2,this.aU);
        }
        return value;
    }

    public void setaU(String aU) {
        this.aU = aU;
    }

    public String getbU() {
        String value = null;
        if (this.bU != null) {
            value = RoundUtils.round(2,this.bU);
        }
        return value;
    }

    public void setbU(String bU) {
        this.bU = bU;
    }

    public String getcU() {
        String value = null;
        if (this.cU != null) {
            value = RoundUtils.round(2,this.cU);
        }
        return value;
    }

    public void setcU(String cU) {
        this.cU = cU;
    }

    public String getaI() {
        String value = null;
        if (this.aI != null) {
            value = RoundUtils.round(2,this.aI);
        }
        return value;
    }

    public void setaI(String aI) {
        this.aI = aI;
    }

    public String getbI() {
        String value = null;
        if (this.bI != null) {
            value = RoundUtils.round(2,this.bI);
        }
        return value;
    }

    public void setbI(String bI) {
        this.bI = bI;
    }

    public String getcI() {
        String value = null;
        if (this.cI != null) {
            value = RoundUtils.round(2,this.cI);
        }
        return value;
    }

    public void setcI(String cI) {
        this.cI = cI;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public Double getRealData() {
        Double value = null;
        if (this.rawData != null) {
            BigDecimal bigDecimal = new BigDecimal(this.rawData);
            if (this.ratio == null || this.ratio == 0) {
                this.ratio = 1;
            }
            value = bigDecimal.multiply(BigDecimal.valueOf(this.ratio)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        this.realData = value;
        return realData;
    }

    public void setRealData(Double realData) {
        this.realData = realData;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }
}