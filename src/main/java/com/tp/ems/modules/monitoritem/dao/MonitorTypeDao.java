/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.monitoritem.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.monitoritem.entity.MonitorType;

/**
 * 监测项类型DAO接口
 * @author 张丽
 * @version 2016-11-11
 */
@MyBatisDao
public interface MonitorTypeDao extends CrudDao<MonitorType> {
	
}