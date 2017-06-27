/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywateryear.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energywateryear.entity.EnergyWaterYear;
import com.tp.ems.modules.energywateryear.dao.EnergyWaterYearDao;

/**
 * 水表每年数据Service
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class EnergyWaterYearService extends CrudService<EnergyWaterYearDao, EnergyWaterYear> {

	public EnergyWaterYear get(String id) {
		return super.get(id);
	}
	
	public List<EnergyWaterYear> findList(EnergyWaterYear energyWaterYear) {
		return super.findList(energyWaterYear);
	}
	
	public Page<EnergyWaterYear> findPage(Page<EnergyWaterYear> page, EnergyWaterYear energyWaterYear) {
		return super.findPage(page, energyWaterYear);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyWaterYear energyWaterYear) {
		super.save(energyWaterYear);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyWaterYear energyWaterYear) {
		super.delete(energyWaterYear);
	}
	
}