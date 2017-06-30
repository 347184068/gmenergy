/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energydevices.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;

/**
 * 能源设备管理DAO接口
 * @author 徐韵轩
 * @version 2017-06-30
 */
@MyBatisDao
public interface EnergyDevicesDao extends CrudDao<EnergyDevices> {
	
}