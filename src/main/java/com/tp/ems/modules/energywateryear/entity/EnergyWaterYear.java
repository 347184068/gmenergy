/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywateryear.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.tp.ems.common.persistence.DataEntity;

/**
 * 水表每年数据Entity
 * @author 徐韵轩
 * @version 2017-06-26
 */
public class EnergyWaterYear extends DataEntity<EnergyWaterYear> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 设备ID
	private String data;		// 设备采集一年的耗水数据
	private Date dataTime;		// 采集数据时间

	private String selectYear;

	public EnergyWaterYear() {
		super();
	}

	public EnergyWaterYear(String id){
		super(id);
	}

	@Length(min=0, max=255, message="设备ID长度必须介于 0 和 255 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=1, max=100, message="设备采集一年的耗水数据长度必须介于 1 和 100 之间")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
}