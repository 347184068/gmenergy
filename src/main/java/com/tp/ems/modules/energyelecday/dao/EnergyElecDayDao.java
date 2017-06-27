/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecday.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;

/**
 * 电表每天数据DAO接口
 * @author 徐韵轩
 * @version 2017-06-25
 */
@MyBatisDao
public interface EnergyElecDayDao extends CrudDao<EnergyElecDay> {
	
}