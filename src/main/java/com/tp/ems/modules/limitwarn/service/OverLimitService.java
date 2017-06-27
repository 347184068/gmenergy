/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.limitwarn.service;

import com.google.common.collect.Lists;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.limitwarn.dao.OverLimitDao;
import com.tp.ems.modules.limitwarn.dao.WaterOverLimitDao;
import com.tp.ems.modules.limitwarn.entity.OverLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 监测预警Service
 * @author 张丽
 * @version 2016-11-11
 */
@Service
@Transactional(readOnly = true)
public class OverLimitService extends CrudService<OverLimitDao, OverLimit> {

	@Autowired
	private OverLimitDao limitDao;
	@Autowired
	private WaterOverLimitDao waterOverLimitDao;

	public OverLimit get(String id) {
		return super.get(id);
	}
	
	public List<OverLimit> findList(OverLimit overLimit) {
		return super.findList(overLimit);
	}
	
	public Page<OverLimit> findPage(Page<OverLimit> page, OverLimit overLimit) {
		return super.findPage(page, overLimit);
	}
	
	@Transactional(readOnly = false)
	public void save(OverLimit overLimit) {
		super.save(overLimit);
	}
	
	@Transactional(readOnly = false)
	public void delete(OverLimit overLimit) {
		super.delete(overLimit);
	}

	public List<OverLimit> getLimitRecords(OverLimit overLimit){
		List<OverLimit> limitList = new ArrayList<>();
		if("elec".equals(overLimit.getMenu())){
			limitList = limitDao.getLimitRecord(overLimit);
		}/*else {
			limitList=waterOverLimitDao.getLimitRecord(overLimit);
		}*/
		return limitList;
	}

	//查询所有的越限记录
	public List<OverLimit> getAllOverLimits(OverLimit overLimit){
		List<OverLimit> limitList = new ArrayList<>();
		limitList = limitDao.getAllOverLimit(overLimit);
		return limitList;
	}

	/**
	 * 单项监测点所有越限记录
	 * @param overLimit
	 * @return
	 */
	public List<OverLimit> getOneDeviceAllOverLimit(OverLimit overLimit){
		List<OverLimit>  limitList = Lists.newArrayList();
		limitList = limitDao.getOneDeviceAllOverLimit(overLimit);
		return limitList;
	}

	/**
	 * 单项节点条件查询
	 * @param overLimit
	 * @return
	 */
	public List<OverLimit> getOneDeviceOverLimit(OverLimit overLimit){
		List<OverLimit> limitList = Lists.newArrayList();
		if("elec".equals(overLimit.getMenu())){
			limitList = limitDao.getOneDeviceOverLimit(overLimit);
		}/*else{
			limitList=waterOverLimitDao.getOneDeviceOverLimit(overLimit);
		}*/
		return limitList;
	}


}