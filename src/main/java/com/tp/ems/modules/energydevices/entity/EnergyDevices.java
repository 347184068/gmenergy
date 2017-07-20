/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energydevices.entity;

import org.hibernate.validator.constraints.Length;

import com.tp.ems.common.persistence.DataEntity;

/**
 * 能源设备管理Entity
 * @author 徐韵轩
 * @version 2017-06-30
 */
public class EnergyDevices extends DataEntity<EnergyDevices> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 真实设备编号
	private String type;		// 设备类型0电表，水表
	private String name;		// 名称
	private Integer ratio;
	private String monthLimit;		// 月限定额度
	private String yearLimit;		// 年限额
	
	public EnergyDevices() {
		super();
	}

	public EnergyDevices(String id){
		super(id);
	}

	@Length(min=0, max=255, message="真实设备编号长度必须介于 0 和 255 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=0, max=255, message="设备类型0电表，水表长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="月限定额度长度必须介于 0 和 255 之间")
	public String getMonthLimit() {
		return monthLimit;
	}

	public void setMonthLimit(String monthLimit) {
		this.monthLimit = monthLimit;
	}
	
	@Length(min=0, max=255, message="年限额长度必须介于 0 和 255 之间")
	public String getYearLimit() {
		return yearLimit;
	}

	public void setYearLimit(String yearLimit) {
		this.yearLimit = yearLimit;
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
}