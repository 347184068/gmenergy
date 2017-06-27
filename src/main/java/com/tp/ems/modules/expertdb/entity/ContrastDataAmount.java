/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.expertdb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp.ems.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 专家数据库(对比分析)Entity
 * @author 张丽
 * @version 2016-11-07
 */
public class ContrastDataAmount extends DataEntity<ContrastDataAmount> {

	private static final long serialVersionUID = 1L;
	private String deviceId;		// deviceid
	private String maxload;		// maxload
	private String minload;		// minload
	private String avgload;		// avgload
	private String electricity;		// electricity
	private Date indate;		// indate
	private Date startTime;
	private Date endTime;

	private String deviceName;
	private String compareType;
	
	public ContrastDataAmount() {
		super();
	}

	public ContrastDataAmount(String id){
		super(id);
	}

	@Length(min=0, max=64, message="deviceid长度必须介于 0 和 64 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getMaxload() {
		return maxload;
	}

	public void setMaxload(String maxload) {
		this.maxload = maxload;
	}
	
	public String getMinload() {
		return minload;
	}

	public void setMinload(String minload) {
		this.minload = minload;
	}
	
	public String getAvgload() {
		return avgload;
	}

	public void setAvgload(String avgload) {
		this.avgload = avgload;
	}
	
	public String getElectricity() {
		return electricity;
	}

	public void setElectricity(String electricity) {
		this.electricity = electricity;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
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


	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getCompareType() {
		return compareType;
	}

	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}

	@Override
	public String toString() {
		return "ContrastDataAmount{" +
				"deviceId='" + deviceId + '\'' +
				", maxload='" + maxload + '\'' +
				", minload='" + minload + '\'' +
				", avgload='" + avgload + '\'' +
				", electricity='" + electricity + '\'' +
				", indate=" + indate +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", deviceName='" + deviceName + '\'' +
				'}';
	}
}