/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.waterexpertdb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp.ems.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 专家数据库(对比分析)Entity
 * @author 张丽
 * @version 2016-11-07
 */
public class ContrastData extends DataEntity<ContrastData> {

	private static final long serialVersionUID = 1L;
	private String deviceId;		// deviceid
	private double jljll; //净累计流量
	private double ssll;
	private Date indate;		// indate
	private Date startTime;
	private Date endTime;

	private String deviceName;
	private String compareType;//同比环比
	private String type;//净累计、瞬时
	
	public ContrastData() {
		super();
	}

	public ContrastData(String id){
		super(id);
	}

	@Length(min=0, max=64, message="deviceid长度必须介于 0 和 64 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public double getSsll() {
		return ssll;
	}

	public void setSsll(double ssll) {
		this.ssll = ssll;
	}

	public double getJljll() {
		return jljll;
	}

	public void setJljll(double jljll) {
		this.jljll = jljll;
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


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ContrastData{" +
				"deviceId='" + deviceId + '\'' +
				", jljll=" + jljll +
				", ssll=" + ssll +
				", indate=" + indate +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", deviceName='" + deviceName + '\'' +
				'}';
	}
}