/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energycharge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp.ems.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 监测点电费计算Entity
 * @author 张丽
 * @version 2016-11-09
 */
public class EnergyCharge extends DataEntity<EnergyCharge> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// deviceid
	private String electricity;		// electricity
	private String indate;		// indate

	private String sumElec;		// electricity
	private double totalPrice;		// indate 电费总价
	private double unitPrice; //单价

	private Date startTime;
	private Date endTime;
	private String deviceName;

	private double jljll;
	public EnergyCharge() {
		super();
	}

	public EnergyCharge(String id){
		super(id);
	}

	@Length(min=0, max=64, message="deviceid长度必须介于 0 和 64 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getElectricity() {
		return electricity;
	}

	public void setElectricity(String electricity) {
		this.electricity = electricity;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getIndate() {
		return indate;
	}

	public void setIndate(String indate) {
		this.indate = indate;
	}

	public String getSumElec() {
		return sumElec;
	}

	public void setSumElec(String sumElec) {
		this.sumElec = sumElec;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
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

	public double getJljll() {
		return jljll;
	}

	public void setJljll(double jljll) {
		this.jljll = jljll;
	}

	@Override
	public String toString() {
		return "EnergyCharge{" +
				"deviceId='" + deviceId + '\'' +
				", electricity='" + electricity + '\'' +
				", indate='" + indate + '\'' +
				", sumElec='" + sumElec + '\'' +
				", totalPrice=" + totalPrice +
				", unitPrice=" + unitPrice +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", deviceName='" + deviceName + '\'' +
				", jljll=" + jljll +
				'}';
	}
}