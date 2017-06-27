/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.dayamount.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp.ems.common.persistence.DataEntity;
import com.tp.ems.common.utils.excel.annotation.ExcelField;
import com.tp.ems.modules.devices.entity.Monitordevices;

/**
 * 日报表统计Entity
 * @author smallwei
 * @version 2016-11-02
 */
public class MonthAmount extends DataEntity<MonthAmount> {
	
	private static final long serialVersionUID = 1L;
	private Monitordevices deviceid;		// deviceid
	private double maxload;		// maxload
	private double minload;		// minload
	private double avgload;		// avgload
	private double electricity;		// electricity
	private Date indate;		// indate
	private String deviceId;
	
	public MonthAmount() {
		super();
	}

	public MonthAmount(String id){
		super(id);
	}

	@Length(min=0, max=64, message="deviceid长度必须介于 0 和 64 之间")
	@ExcelField(title="设备名称", type=1, align=1, sort=110,value="deviceid.name")
	public Monitordevices getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(Monitordevices deviceid) {
		this.deviceid = deviceid;
	}
	@ExcelField(title="最大值", type=1, align=1, sort=110)
	public double getMaxload() {
		return maxload;
	}

	public void setMaxload(double maxload) {
		this.maxload = maxload;
	}
	@ExcelField(title="最小值", type=1, align=1, sort=110)
	public double getMinload() {
		return minload;
	}

	public void setMinload(double minload) {
		this.minload = minload;
	}
	@ExcelField(title="平均值", type=1, align=1, sort=110)
	public double getAvgload() {
		return avgload;
	}

	public void setAvgload(double avgload) {
		this.avgload = avgload;
	}
	@ExcelField(title="电量", type=1, align=1, sort=110)
	public double getElectricity() {
		return electricity;
	}

	public void setElectricity(double electricity) {
		this.electricity = electricity;
	}
	@ExcelField(title="记录时间", type=1, align=1, sort=110)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "MonthAmount [deviceid=" + deviceid + ", maxload=" + maxload
				+ ", minload=" + minload + ", avgload=" + avgload
				+ ", electricity=" + electricity + ", indate=" + indate + "]";
	}

	

	
	
}