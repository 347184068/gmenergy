/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywatermonitor.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.energyelecmonitor.entity.EnergyElecRawdata;
import com.tp.ems.modules.energywatermonitor.entity.EnergyWaterRawdata;

import java.util.List;

/**
 * 水表在线监控DAO接口
 * @author 徐韵轩
 * @version 2017-07-13
 */
@MyBatisDao
public interface EnergyWaterRawdataDao extends CrudDao<EnergyWaterRawdata> {

    List<EnergyWaterRawdata> findNewDataByCount(EnergyWaterRawdata waterRawdata);
}