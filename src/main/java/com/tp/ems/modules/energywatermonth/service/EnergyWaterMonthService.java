/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywatermonth.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energywatermonth.entity.EnergyWaterMonth;
import com.tp.ems.modules.energywatermonth.dao.EnergyWaterMonthDao;

/**
 * 水表每月数据Service
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class EnergyWaterMonthService extends CrudService<EnergyWaterMonthDao, EnergyWaterMonth> {

	public EnergyWaterMonth get(String id) {
		return super.get(id);
	}
	
	public List<EnergyWaterMonth> findList(EnergyWaterMonth energyWaterMonth) {
		return super.findList(energyWaterMonth);
	}
	
	public Page<EnergyWaterMonth> findPage(Page<EnergyWaterMonth> page, EnergyWaterMonth energyWaterMonth) {
		return super.findPage(page, energyWaterMonth);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyWaterMonth energyWaterMonth) {
		super.save(energyWaterMonth);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyWaterMonth energyWaterMonth) {
		super.delete(energyWaterMonth);
	}
	
}