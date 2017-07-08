/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecmonitor.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.energyelecmonitor.entity.EnergyElecRawdata;

import java.util.List;

/**
 * 电数据在线监控DAO接口
 * @author 徐韵轩
 * @version 2017-07-07
 */
@MyBatisDao
public interface EnergyElecRawdataDao extends CrudDao<EnergyElecRawdata> {
	List<EnergyElecRawdata> findNewData(EnergyElecRawdata energyElecRawdata);
}