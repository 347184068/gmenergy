/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywateryear.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.energywateryear.entity.EnergyWaterYear;

/**
 * 水表每年数据DAO接口
 * @author 徐韵轩
 * @version 2017-06-26
 */
@MyBatisDao
public interface EnergyWaterYearDao extends CrudDao<EnergyWaterYear> {
	
}