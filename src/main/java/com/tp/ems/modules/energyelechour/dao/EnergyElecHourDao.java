/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelechour.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;

/**
 * 电表每小时数据DAO接口
 * @author 徐韵轩
 * @version 2017-06-25
 */
@MyBatisDao
public interface EnergyElecHourDao extends CrudDao<EnergyElecHour> {
	
}