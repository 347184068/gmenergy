package com.tp.ems.modules.energydevices.entity;

import java.util.Date;

/**
 * @Author XuYunXuan
 * @Date 2017/6/30 20:05
 */
public class ElecLimit {

    private String deviceId;

    private Date inDate;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }
}
