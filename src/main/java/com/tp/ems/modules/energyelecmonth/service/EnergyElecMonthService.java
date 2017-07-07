/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecmonth.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energyelecmonth.entity.EnergyElecMonth;
import com.tp.ems.modules.energyelecmonth.dao.EnergyElecMonthDao;

/**
 * 电表每月数据Service
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class EnergyElecMonthService extends CrudService<EnergyElecMonthDao, EnergyElecMonth> {

	public EnergyElecMonth get(String id) {
		return super.get(id);
	}
	
	public List<EnergyElecMonth> findList(EnergyElecMonth energyElecMonth) {
		return super.findList(energyElecMonth);
	}
	
	public Page<EnergyElecMonth> findPage(Page<EnergyElecMonth> page, EnergyElecMonth energyElecMonth) {
		return super.findPage(page, energyElecMonth);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyElecMonth energyElecMonth) {
		super.save(energyElecMonth);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyElecMonth energyElecMonth) {
		super.delete(energyElecMonth);
	}

	public List<EnergyElecMonth> findByYear(String deviceId, Date inDate){
		EnergyElecMonth energyElecMonth = new EnergyElecMonth();
		energyElecMonth.setDeviceId(deviceId);
		energyElecMonth.setDataTime(inDate);
		return findList(energyElecMonth);
	}


}