/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecmonitor.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.tp.ems.common.persistence.DataEntity;

/**
 * 电数据在线监控Entity
 * @author 徐韵轩
 * @version 2017-07-07
 */
public class EnergyElecRawdata extends DataEntity<EnergyElecRawdata> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 设备ID
	private String rawData;		// 设备采集原始数据
	private Date dataTime;		// 采集数据时间


	private Integer count;  //获取最新数据条数
	
	public EnergyElecRawdata() {
		super();
	}

	public EnergyElecRawdata(String id){
		super(id);
	}

	@Length(min=0, max=255, message="设备ID长度必须介于 0 和 255 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=1, max=100, message="设备采集原始数据长度必须介于 1 和 100 之间")
	public String getRawData() {
		String value = null;
		if(this.rawData!=null){
			BigDecimal bigDecimal = new BigDecimal(this.rawData);
			value = bigDecimal.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue()+"";
		}
		return value;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}


	@Override
	public String toString() {
		return "EnergyElecRawdata{" +
				"deviceId='" + deviceId + '\'' +
				", rawData='" + rawData + '\'' +
				", dataTime=" + dataTime +
				", count=" + count +
				'}';
	}
}