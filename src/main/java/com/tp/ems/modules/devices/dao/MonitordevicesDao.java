/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.devices.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.devices.entity.Monitordevices;

/**
 * 监测点DAO接口
 * @author 徐钦政
 * @version 2016-11-03
 */
@MyBatisDao
public interface MonitordevicesDao extends CrudDao<Monitordevices> {
	
}