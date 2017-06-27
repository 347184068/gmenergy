/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.limitwarn.dao;

import com.tp.ems.common.persistence.CrudDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.limitwarn.entity.OverLimit;

import java.util.List;

/**
 * 监测预警DAO接口
 * @author 张丽
 * @version 2016-11-11
 */
@MyBatisDao
public interface WaterOverLimitDao extends CrudDao<OverLimit> {
	/**
	 * 根据时间条件查询所有节点的越限记录
	 * @param overLimit
	 * @return
	 */
	public List<OverLimit> getLimitRecord(OverLimit overLimit);

	/**
	 * 查询所有节点的越限记录
	 * @param overLimit
	 * @return
	 */
	public List<OverLimit> getAllOverLimit(OverLimit overLimit);

	/**
	 * 查询单项节点的越限记录
	 * @param overLimit
	 * @return
	 */
	public List<OverLimit> getOneDeviceAllOverLimit(OverLimit overLimit);

	/**
	 * 根据时间条件查询单项节点的越限记录
	 * @param overLimit
	 * @return
	 */
	public List<OverLimit> getOneDeviceOverLimit(OverLimit overLimit);

}