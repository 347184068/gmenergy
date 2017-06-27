package com.tp.ems.modules.poweranalysis.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tepusoft on 2016/10/29.
 */
public class ElecDataAmount implements Serializable {
    private int id;
    private String deviceId;
    private double maxLoad;
    private double minLoad;
    private double avgLoad;
    private Double electricity;
    private Date inDate;
    private String type;  //4实时、3日、2月、1年

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(double maxLoad) {
        this.maxLoad = maxLoad;
    }

    public double getMinLoad() {
        return minLoad;
    }

    public void setMinLoad(double minLoad) {
        this.minLoad = minLoad;
    }

    public double getAvgLoad() {
        return avgLoad;
    }

    public void setAvgLoad(double avgLoad) {
        this.avgLoad = avgLoad;
    }

    public Double getElectricity() {
        return electricity;
    }

    public void setElectricity(Double electricity) {
        this.electricity = electricity;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "ElecDataAmount{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", maxLoad=" + maxLoad +
                ", minLoad=" + minLoad +
                ", avgLoad=" + avgLoad +
                ", electricity=" + electricity +
                ", inDate=" + inDate +
                ", type='" + type + '\'' +
                '}';
    }
}
