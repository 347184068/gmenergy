/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecmonth.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.energyelecmonth.entity.EnergyElecMonth;

import java.util.Date;

/**
 * 电表每月数据DAO接口
 * @author 徐韵轩
 * @version 2017-06-26
 */
@MyBatisDao
public interface EnergyElecMonthDao extends CrudDao<EnergyElecMonth> {

}