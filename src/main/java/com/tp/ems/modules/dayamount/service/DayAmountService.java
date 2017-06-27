/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.dayamount.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.dayamount.dao.DayAmountDao;
import com.tp.ems.modules.dayamount.entity.DayAmount;
import com.tp.ems.modules.sys.entity.Area;
import com.tp.ems.modules.sys.utils.UserUtils;

/**
 * 日报表统计Service
 * @author smallwei
 * @version 2016-11-02
 */
@Service
@Transactional(readOnly = true)
public class DayAmountService extends CrudService<DayAmountDao, DayAmount> {

	@Autowired
	private DayAmountDao dayAmountDao;
	
	public DayAmount get(String id) {
		return super.get(id);
	}
	
	public List<DayAmount> findList(DayAmount dayAmount) {
		return super.findList(dayAmount);
	}
	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}
	public Page<DayAmount> findPage(Page<DayAmount> page, DayAmount dayAmount) {
		return super.findPage(page, dayAmount);
	}
	@Transactional(readOnly = false)
	public void save(DayAmount dayAmount) {
		super.save(dayAmount);
	}

	public List<Map<String,Object>> findDayAmountByDate(String startdate,String enddate){
		return dayAmountDao.findDayAmountByDate(startdate,enddate);
	}
	@Transactional(readOnly = false)
	public void delete(DayAmount dayAmount) {
		super.delete(dayAmount);
	}
}