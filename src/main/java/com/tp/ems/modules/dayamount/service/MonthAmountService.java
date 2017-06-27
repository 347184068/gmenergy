/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.dayamount.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.dayamount.dao.MonthAmountDao;
import com.tp.ems.modules.dayamount.entity.MonthAmount;
import com.tp.ems.modules.sys.entity.Area;
import com.tp.ems.modules.sys.utils.UserUtils;

/**
 * 日报表统计Service
 * @author smallwei
 * @version 2016-11-02
 */
@Service
@Transactional(readOnly = true)
public class MonthAmountService extends CrudService<MonthAmountDao, MonthAmount> {

	@Autowired
	private MonthAmountDao monthAmountDao;
	
	public MonthAmount get(String id) {
		return super.get(id);
	}
	
	public List<MonthAmount> findList(MonthAmount monthAmount) {
		return super.findList(monthAmount);
	}
	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}
	public Page<MonthAmount> findPage(Page<MonthAmount> page, MonthAmount monthAmount) {
		return super.findPage(page, monthAmount);
	}
	
	@Transactional(readOnly = false)
	public void save(MonthAmount monthAmount) {
		super.save(monthAmount);
	}
	
	@Transactional(readOnly = false)
	public void delete(MonthAmount monthAmount) {
		super.delete(monthAmount);
	}
}