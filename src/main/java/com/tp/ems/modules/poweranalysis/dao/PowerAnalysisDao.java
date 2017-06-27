package com.tp.ems.modules.poweranalysis.dao;

import com.tp.ems.common.persistence.TreeDao;
import com.tp.ems.common.persistence.annotation.MyBatisDao;
import com.tp.ems.modules.devices.entity.Monitordevices;

import java.util.List;

/**
 * Created by tepusoft on 2016/10/29.
 */
@MyBatisDao
public interface PowerAnalysisDao extends TreeDao<Monitordevices> {

    public List<Monitordevices> getDeviceListByIds(List<String> ids);

}
