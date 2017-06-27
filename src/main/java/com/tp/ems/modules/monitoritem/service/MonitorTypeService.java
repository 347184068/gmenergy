/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.monitoritem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.monitoritem.entity.MonitorType;
import com.tp.ems.modules.monitoritem.dao.MonitorTypeDao;

/**
 * 监测项类型Service
 * @author 张丽
 * @version 2016-11-11
 */
@Service
@Transactional(readOnly = true)
public class MonitorTypeService extends CrudService<MonitorTypeDao, MonitorType> {

	public MonitorType get(String id) {
		return super.get(id);
	}
	
	public List<MonitorType> findList(MonitorType monitorType) {
		return super.findList(monitorType);
	}
	
	public Page<MonitorType> findPage(Page<MonitorType> page, MonitorType monitorType) {
		return super.findPage(page, monitorType);
	}
	
	@Transactional(readOnly = false)
	public void save(MonitorType monitorType) {
		super.save(monitorType);
	}
	
	@Transactional(readOnly = false)
	public void delete(MonitorType monitorType) {
		super.delete(monitorType);
	}
	
}