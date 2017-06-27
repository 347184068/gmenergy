/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.elecreport.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.elecreport.entity.ElecPojo;
import com.tp.ems.modules.expertdb.entity.ContrastDataAmount;

import java.util.List;

/**
 * 电量列表()DAO接口
 * @author 张丽
 * @version 2016-11-07
 */
@MyBatisDao
public interface ElecPojoDao extends CrudDao<ElecPojo> {

    public List<ElecPojo> getElecList(ElecPojo elecPojo);
}