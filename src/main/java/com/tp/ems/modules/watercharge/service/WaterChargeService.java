/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.watercharge.service;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energycharge.entity.EnergyCharge;
import com.tp.ems.modules.watercharge.dao.WaterChargeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监测点电费计算Service
 * @author 张丽
 * @version 2016-11-09
 */
@Service
@Transactional(readOnly = true)
public class WaterChargeService extends CrudService<WaterChargeDao, EnergyCharge> {
	@Autowired
	private WaterChargeDao energyChargeDao;
	@Autowired
	private WaterChargeUtil util;

	public EnergyCharge get(String id) {
		return super.get(id);
	}

	public List<EnergyCharge> findList(EnergyCharge energyCharge) {
		return super.findList(energyCharge);
	}

	public Page<EnergyCharge> findPage(Page<EnergyCharge> page, EnergyCharge energyCharge) {
		return super.findPage(page, energyCharge);
	}

	@Transactional(readOnly = false)
	public void save(EnergyCharge energyCharge) {
		super.save(energyCharge);
	}

	@Transactional(readOnly = false)
	public void delete(EnergyCharge energyCharge) {
		super.delete(energyCharge);
	}


	/**
	 * 同一监测点不同时段柱状图
	 * @param energyCharge
	 * @return
	 */
	public Map<String,Object> getOneDeviceBar(EnergyCharge energyCharge){

		Map<String,Object> map = new HashMap<>();
		List<EnergyCharge> energyChargeList = new ArrayList<>();
		energyChargeList = energyChargeDao.getOneDeviceCharge(energyCharge);
		if (energyChargeList.size()<=0){
			return map;
		}
		map.put("list",energyChargeList);
		GsonOption option = new GsonOption();
		option = util.oneDeviceElecBar(energyChargeList);
		map.put("water",option);
		option = util.oneDeviceChargeBar(energyChargeList);
		map.put("charge",option);
		return map;
	}

	/**
	 * 多监测点不同时段电量柱状图
	 * @param map
	 * @param paramIds
	 * @return
	 */
	public Map<String,Object> getDiffDeviceBar(Map<String,Object> map,List<String> paramIds){
		Map<String,Object> resultMap = new HashMap<>();
		List<EnergyCharge> energyChargeList = new ArrayList<>();
		energyChargeList = energyChargeDao.getDiffDeviceCharge(map);
		if(energyChargeList.size()<=0){

			return resultMap;
		}
		resultMap.put("list",energyChargeList);
		GsonOption option = new GsonOption();

		option = util.diffDeviceElecBar(energyChargeList,paramIds);
		resultMap.put("water",option);
		option = util.diffDeviceChargeBar(energyChargeList,paramIds);
		resultMap.put("charge",option);
		return resultMap;
	}



	/**
	 * 所有监测点不同时段电量柱状图
	 * @param energyCharge
	 * @return
	 */
	public Map<String,Object> getAllDeviceBar(EnergyCharge energyCharge){

		Map<String,Object> map = new HashMap<>();
		List<EnergyCharge> energyChargeList = new ArrayList<>();
		energyChargeList = energyChargeDao.getAllDeviceCharge(energyCharge);
		if (energyChargeList.size()<=0){

			return map;
		}
		map.put("list",energyChargeList);
		GsonOption option = new GsonOption();
		option = util.oneDeviceElecBar(energyChargeList);
		map.put("water",option);
		option = util.oneDeviceChargeBar(energyChargeList);
		map.put("charge",option);
		return map;
	}
}