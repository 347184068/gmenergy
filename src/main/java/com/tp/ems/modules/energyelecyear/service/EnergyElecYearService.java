/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecyear.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energyelecyear.entity.EnergyElecYear;
import com.tp.ems.modules.energyelecyear.dao.EnergyElecYearDao;

/**
 * 电表每年数据Service
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class EnergyElecYearService extends CrudService<EnergyElecYearDao, EnergyElecYear> {

	public EnergyElecYear get(String id) {
		return super.get(id);
	}
	
	public List<EnergyElecYear> findList(EnergyElecYear energyElecYear) {
		return super.findList(energyElecYear);
	}
	
	public Page<EnergyElecYear> findPage(Page<EnergyElecYear> page, EnergyElecYear energyElecYear) {
		return super.findPage(page, energyElecYear);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyElecYear energyElecYear) {
		super.save(energyElecYear);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyElecYear energyElecYear) {
		super.delete(energyElecYear);
	}


}