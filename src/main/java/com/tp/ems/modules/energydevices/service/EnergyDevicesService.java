/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energydevices.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.dao.EnergyDevicesDao;

/**
 * 能源设备管理Service
 * @author 徐韵轩
 * @version 2017-06-25
 */
@Service
@Transactional(readOnly = true)
public class EnergyDevicesService extends CrudService<EnergyDevicesDao, EnergyDevices> {

	public EnergyDevices get(String id) {
		return super.get(id);
	}
	
	public List<EnergyDevices> findList(EnergyDevices energyDevices) {
		return super.findList(energyDevices);
	}
	
	public Page<EnergyDevices> findPage(Page<EnergyDevices> page, EnergyDevices energyDevices) {
		return super.findPage(page, energyDevices);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyDevices energyDevices) {
		super.save(energyDevices);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyDevices energyDevices) {
		super.delete(energyDevices);
	}


	public List<EnergyDevices> findAllElecDevices(){
		EnergyDevices energyDevices = new EnergyDevices();
		energyDevices.setType("0");
		return findList(energyDevices);
	}

	public List<EnergyDevices> findAllWaterDevices(){
		EnergyDevices energyDevices = new EnergyDevices();
		energyDevices.setType("1");
		return findList(energyDevices);
	}
	
}