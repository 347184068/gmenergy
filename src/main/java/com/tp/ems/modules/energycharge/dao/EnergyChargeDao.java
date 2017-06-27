/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energycharge.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.energycharge.entity.EnergyCharge;

import java.util.List;
import java.util.Map;

/**
 * 监测点电费计算DAO接口
 * @author 张丽
 * @version 2016-11-09
 */
@MyBatisDao
public interface EnergyChargeDao extends CrudDao<EnergyCharge> {

    //单监测点时间区间内数据获取
    public List<EnergyCharge> getOneDeviceCharge(EnergyCharge energyCharge);

    //多项监测点时间区间内数据获取
    public List<EnergyCharge> getDiffDeviceCharge(Map<String,Object> map);

    public List<EnergyCharge> getAllDeviceCharge(EnergyCharge energyCharge);

}