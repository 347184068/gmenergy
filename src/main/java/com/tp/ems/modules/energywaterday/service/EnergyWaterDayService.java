/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywaterday.service;

import java.util.Date;
import java.util.List;

import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energywaterday.entity.EnergyWaterDay;
import com.tp.ems.modules.energywaterday.dao.EnergyWaterDayDao;

/**
 * 水表每天数据Service
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class EnergyWaterDayService extends CrudService<EnergyWaterDayDao, EnergyWaterDay> {

	public EnergyWaterDay get(String id) {
		return super.get(id);
	}
	
	public List<EnergyWaterDay> findList(EnergyWaterDay energyWaterDay) {
		return super.findList(energyWaterDay);
	}
	
	public Page<EnergyWaterDay> findPage(Page<EnergyWaterDay> page, EnergyWaterDay energyWaterDay) {
		return super.findPage(page, energyWaterDay);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyWaterDay energyWaterDay) {
		super.save(energyWaterDay);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyWaterDay energyWaterDay) {
		super.delete(energyWaterDay);
	}

	public List<EnergyWaterDay> findByMonth(String deviceId, Date inDate){
		EnergyWaterDay energyWaterDay = new EnergyWaterDay();
		energyWaterDay.setDeviceId(deviceId);
		energyWaterDay.setDataTime(inDate);
		return findList(energyWaterDay);
	}
	
}