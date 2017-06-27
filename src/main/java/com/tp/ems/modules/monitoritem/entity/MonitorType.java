/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.monitoritem.entity;

import org.hibernate.validator.constraints.Length;

import com.tp.ems.common.persistence.DataEntity;

/**
 * 监测项类型Entity
 * @author 张丽
 * @version 2016-11-11
 */
public class MonitorType extends DataEntity<MonitorType> {
	
	private static final long serialVersionUID = 1L;
	private String itemType;		// 类型0负荷 1 电量
	private String itemValue;		// 数字对应类型
	private String menu;

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public MonitorType() {
		super();
	}

	public MonitorType(String id){
		super(id);
	}

	@Length(min=0, max=11, message="类型0负荷 1 电量长度必须介于 0 和 11 之间")
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	@Length(min=0, max=255, message="数字对应类型长度必须介于 0 和 255 之间")
	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	@Override
	public String toString() {
		return "MonitorType{" +
				"itemType='" + itemType + '\'' +
				", itemValue='" + itemValue + '\'' +
				", menu='" + menu + '\'' +
				'}';
	}
}