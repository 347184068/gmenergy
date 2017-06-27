package com.tp.ems.modules.poweranalysis.dao;

import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;

import java.util.List;

/**
 * Created by tepusoft on 2016/10/29.
 */
@MyBatisDao
public interface ElecDataAmountDao {

    //获取年数据
    public List<ElecDataAmount> getYearPowerData(ElecDataAmount dataAmount);

    //获取月数据
    public List<ElecDataAmount> getMonthPowerData(ElecDataAmount dataAmount);

    //获取日数据
    public List<ElecDataAmount> getDayPowerData(ElecDataAmount dataAmount);

    //负荷数据---用于15min去一次的折线图
    public List<ElecDataAmount> getRealTimePowerData(ElecDataAmount dataAmount);

    //柱状图 ----- 1h 取一次 电量
    public List<ElecDataAmount> getRealTimeElecData(ElecDataAmount dataAmount);


    public ElecDataAmount getMonthSum(ElecDataAmount dataAmount);


}
