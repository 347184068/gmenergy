/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.expertdb.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.expertdb.entity.ContrastDataAmount;

import java.util.List;

/**
 * 专家数据库(对比分析)DAO接口
 * @author 张丽
 * @version 2016-11-07
 */
@MyBatisDao
public interface ContrastDataAmountDao extends CrudDao<ContrastDataAmount> {

    //1.一个监测点某一时间段
    public List<ContrastDataAmount> getIntervalData(ContrastDataAmount contrastDataAmount);
}