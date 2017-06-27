package com.tp.ems.modules.energyelechour.service;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelechour.dao.EnergyElecHourDao;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author XuYunXuan
 * @Date 2017/6/25 13:05
 */
@Service
@Transactional(readOnly = true)
public class EnergyElecHourService extends CrudService<EnergyElecHourDao, EnergyElecHour> {
    public EnergyElecHour get(String id) {
        return super.get(id);
    }

    public List<EnergyElecHour> findList(EnergyElecHour energyElecHour) {
        return super.findList(energyElecHour);
    }

    public Page<EnergyElecHour> findPage(Page<EnergyElecHour> page, EnergyElecHour energyElecHour) {
        return super.findPage(page, energyElecHour);
    }

    @Transactional(readOnly = false)
    public void save(EnergyElecHour energyElecHour) {
        super.save(energyElecHour);
    }

    @Transactional(readOnly = false)
    public void delete(EnergyElecHour energyElecHour) {
        super.delete(energyElecHour);
    }
}
