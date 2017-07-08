/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecday.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.tp.ems.common.persistence.DataEntity;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

/**
 * 电表每天数据Entity
 * @author 徐韵轩
 * @version 2017-06-25
 */
public class EnergyElecDay extends DataEntity<EnergyElecDay> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 设备ID
	private String data;		// 设备采集一天的耗电数据
	private Date dataTime;		// 采集数据时间
	
	public EnergyElecDay() {
		super();
	}

	public EnergyElecDay(String id){
		super(id);
	}

	@Length(min=0, max=255, message="设备ID长度必须介于 0 和 255 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=1, max=100, message="设备采集一天的耗电数据长度必须介于 1 和 100 之间")
	public String getData() {
		String value = null;
		if(this.data!=null){
			BigDecimal bigDecimal = new BigDecimal(this.data);
			value = bigDecimal.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue()+"";
		}
		return value;
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
	
}