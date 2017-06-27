package com.tp.ems.modules.elecreport.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.utils.excel.annotation.ExcelField;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tepusoft on 2017/5/8.
 */
public class ElecPojo implements Serializable{

    private int id;
    private String linesName; //线路名称
    private String electricity;//电流
    private String voltage; //电压
    private double powerFactor; //功率因数
    private double instantaneousValue;  //瞬时值
    private double cumulativePower; //累计电量
    private Date startTime;
    private Date requestTime;

    private Integer timeInterval;
    private String type;
    private double value;


    //协助sql
    private String startDate;
    private String requestDate;


    @ExcelField(title="线路名称", align=2, sort=10)
    public String getLinesName() {
        return linesName;
    }

    public void setLinesName(String linesName) {
        this.linesName = linesName;
    }

    @ExcelField(title="电流", align=2, sort=20)
    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }
    @ExcelField(title="电压", align=2, sort=30)
    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }
    @ExcelField(title="功率因数", align=2, sort=40)
    public double getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(double powerFactor) {
        this.powerFactor = powerFactor;
    }
    @ExcelField(title="瞬时值", align=2, sort=50)
    public double getInstantaneousValue() {
        return instantaneousValue;
    }

    public void setInstantaneousValue(double instantaneousValue) {
        this.instantaneousValue = instantaneousValue;
    }
    @ExcelField(title="累计电量", align=2, sort=60)
    public double getCumulativePower() {
        return cumulativePower;
    }

    public void setCumulativePower(double cumulativePower) {
        this.cumulativePower = cumulativePower;
    }


    private Page<ElecPojo> page;

    @JsonIgnore
    @XmlTransient
    public Page<ElecPojo> getPage() {
        if (page == null){
            page = new Page<ElecPojo>();
        }
        return page;
    }

    public Page<ElecPojo> setPage(Page<ElecPojo> page) {
        this.page = page;
        return page;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
