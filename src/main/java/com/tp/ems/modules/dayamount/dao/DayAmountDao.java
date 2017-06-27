/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.dayamount.dao;

import java.util.List;
import java.util.Map;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.dayamount.entity.DayAmount;

/**
 * 日报表统计DAO接口
 * @author smallwei
 * @version 2016-11-02
 */
@MyBatisDao
public interface DayAmountDao extends CrudDao<DayAmount> {
	public List<Map<String,Object>> findDayAmountByDate(String startdate,String enddate);
}