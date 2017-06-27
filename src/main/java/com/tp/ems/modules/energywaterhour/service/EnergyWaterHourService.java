package com.tp.ems.modules.energywaterhour.service;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energyelechour.dao.EnergyElecHourDao;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;
import com.tp.ems.modules.energywaterhour.dao.EnergyWaterHourDao;
import com.tp.ems.modules.energywaterhour.entity.EnergyWaterHour;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EnergyWaterHourService extends CrudService<EnergyWaterHourDao, EnergyWaterHour> {
    public EnergyWaterHour get(String id) {
        return super.get(id);
    }

    public List<EnergyWaterHour> findList(EnergyWaterHour energyWaterHour) {
        return super.findList(energyWaterHour);
    }

    public Page<EnergyWaterHour> findPage(Page<EnergyWaterHour> page, EnergyWaterHour energyWaterHour) {
        return super.findPage(page, energyWaterHour);
    }

    @Transactional(readOnly = false)
    public void save(EnergyWaterHour energyWaterHour) {
        super.save(energyWaterHour);
    }

    @Transactional(readOnly = false)
    public void delete(EnergyWaterHour energyWaterHour) {
        super.delete(energyWaterHour);
    }
}
