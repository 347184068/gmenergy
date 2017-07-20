/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecyear.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.tp.ems.common.persistence.DataEntity;

/**
 * 电表每年数据Entity
 * @author 徐韵轩
 * @version 2017-06-26
 */
public class EnergyElecYear extends DataEntity<EnergyElecYear> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 设备ID
	private String data;		// 设备采集一年的耗电数据
	private Date dataTime;		// 采集数据时间

	private String deviceName; //设备名称

	private Integer ratio;//设备倍率

	private String realData; //真实电量  =  倍率* 数据

	private String selectYear;
	
	public EnergyElecYear() {
		super();
	}

	public EnergyElecYear(String id){
		super(id);
	}

	@Length(min=0, max=255, message="设备ID长度必须介于 0 和 255 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=1, max=100, message="设备采集一年的耗电数据长度必须介于 1 和 100 之间")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	@JsonFormat(pattern = "yyyy")
	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public String getSelectYear() {
		return selectYear;
	}

	public void setSelectYear(String selectYear) {
		this.selectYear = selectYear;
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

	public String getRealData() {
		String value = null;
		if (this.data != null) {
			BigDecimal bigDecimal = new BigDecimal(this.data);
			if (this.ratio == null || this.ratio == 0) {
				this.ratio = 1;
			}
			value = bigDecimal.multiply(BigDecimal.valueOf(this.ratio)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
		}
		this.realData = value;
		return realData;
	}

	public void setRealData(String realData) {
		this.realData = realData;
	}
}