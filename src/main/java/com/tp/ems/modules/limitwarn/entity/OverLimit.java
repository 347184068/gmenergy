/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.limitwarn.entity;

import com.tp.ems.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 监测预警Entity
 * @author 张丽
 * @version 2016-11-11
 */
public class OverLimit extends DataEntity<OverLimit> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 设备id
	private String indate;		// 日期
	/*private double value;*/		// 各监测点实际值
	private String monitorType;		// 类型 0负荷 1电量
	private double limitPercent;		// 越限最大值百分比

	private String menu;  //电、水

	private Date startTime;//用于查询条件
	private Date endTime;//用于查询条件
	private double upperLimit; //上限
	private double lowerLimit; //下限
	private double standardValue; //标准值
	private int limitCount; //越限次数
	private String deviceName; //设备名称



	public OverLimit() {
		super();
	}

	public OverLimit(String id){
		super(id);
	}

	@Length(min=1, max=64, message="设备id长度必须介于 1 和 64 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getIndate() {
		return indate;
	}

	public void setIndate(String indate) {
		this.indate = indate;
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

	public double getLimitPercent() {
		return limitPercent;
	}

	public void setLimitPercent(double limitPercent) {
		this.limitPercent = limitPercent;
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public double getStandardValue() {
		return standardValue;
	}

	public void setStandardValue(double standardValue) {
		this.standardValue = standardValue;
	}

	public int getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "OverLimit{" +
				"deviceId='" + deviceId + '\'' +
				", indate='" + indate + '\'' +
				", monitorType='" + monitorType + '\'' +
				", limitPercent=" + limitPercent +
				", menu='" + menu + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", upperLimit=" + upperLimit +
				", lowerLimit=" + lowerLimit +
				", standardValue=" + standardValue +
				", limitCount=" + limitCount +
				", deviceName='" + deviceName + '\'' +
				'}';
	}
}