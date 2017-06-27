/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecday.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelecday.dao.EnergyElecDayDao;

/**
 * 电表每天数据Service
 * @author 徐韵轩
 * @version 2017-06-25
 */
@Service
@Transactional(readOnly = true)
public class EnergyElecDayService extends CrudService<EnergyElecDayDao, EnergyElecDay> {

	public EnergyElecDay get(String id) {
		return super.get(id);
	}
	
	public List<EnergyElecDay> findList(EnergyElecDay energyElecDay) {
		return super.findList(energyElecDay);
	}
	
	public Page<EnergyElecDay> findPage(Page<EnergyElecDay> page, EnergyElecDay energyElecDay) {
		return super.findPage(page, energyElecDay);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyElecDay energyElecDay) {
		super.save(energyElecDay);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyElecDay energyElecDay) {
		super.delete(energyElecDay);
	}
	
}