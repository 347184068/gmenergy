/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.wateranlysis.dao;

import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.wateranlysis.entity.WaterData;

import java.util.List;

/**
 * 用水分析DAO接口
 * @author 张丽
 * @version 2017-01-06
 */
@MyBatisDao
public interface WaterAnalysisDao {

    public List<WaterData> getRealTimeWaterData(WaterData waterData);
    public List<WaterData> getDayWaterData(WaterData waterData);
    public List<WaterData> getMonthWaterData(WaterData waterData);
    public List<WaterData> getYearWaterData(WaterData waterData);

    public WaterData getWaterMonthSum(WaterData waterData);
}