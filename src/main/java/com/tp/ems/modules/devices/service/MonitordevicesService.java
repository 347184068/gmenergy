/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.devices.service;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.devices.dao.MonitordevicesDao;
import com.tp.ems.modules.devices.entity.Monitordevices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 监测点Service
 *
 * @author 徐钦政
 * @version 2016-11-03
 */
@Service
@Transactional(readOnly = true)
public class MonitordevicesService extends CrudService<MonitordevicesDao, Monitordevices> {

    public Monitordevices get(String id) {
        return super.get(id);
    }

    public List<Monitordevices> findList(Monitordevices monitordevices) {
        return super.findList(monitordevices);
    }

    public Page<Monitordevices> findPage(Page<Monitordevices> page, Monitordevices monitordevices) {
        return super.findPage(page, monitordevices);
    }

    @Transactional(readOnly = false)
    public void save(Monitordevices monitordevices) {
        super.save(monitordevices);
    }

    @Transactional(readOnly = false)
    public void delete(Monitordevices monitordevices) {
        super.delete(monitordevices);
    }

}