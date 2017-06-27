/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.devices.entity;

import com.tp.ems.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 监测点Entity
 * @author 徐钦政
 * @version 2016-11-03
 */
public class Monitordevices extends TreeEntity<Monitordevices> {
	
	private static final long serialVersionUID = 1L;
	private Monitordevices parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String name;		// name
	private String menu;

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public Monitordevices() {
		super();
	}

	public Monitordevices(String id){
		super(id);
	}


	public Monitordevices getParent() {
		return parent;
	}

	public void setParent(Monitordevices parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=2000, message="所有父级编号长度必须介于 1 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=100, message="name长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}