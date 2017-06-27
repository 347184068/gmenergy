/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecmonth.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelecday.service.EnergyElecDayService;
import com.tp.ems.modules.energyelecday.utils.HourElecComparator;
import com.tp.ems.modules.energyelecmonth.utils.DayElecComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.modules.energyelecmonth.entity.EnergyElecMonth;
import com.tp.ems.modules.energyelecmonth.service.EnergyElecMonthService;

import java.util.Collections;
import java.util.List;

/**
 * 电表每月数据Controller
 *
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/energyelecmonth/energyElecMonth")
public class EnergyElecMonthController extends BaseController {

    @Autowired
    private EnergyElecMonthService elecMonthService;

    @Autowired
    private EnergyDevicesService devicesService;

    @Autowired
    private EnergyElecDayService elecDayService;

    @ModelAttribute
    public EnergyElecMonth get(@RequestParam(required = false) String id) {
        EnergyElecMonth entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = elecMonthService.get(id);
        }
        if (entity == null) {
            entity = new EnergyElecMonth();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(EnergyElecMonth energyElecMonth, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<EnergyDevices> devicesList = devicesService.findAllElecDevices();
        model.addAttribute("deviceList", devicesList);
        EnergyElecDay energyElecDay = new EnergyElecDay();
        energyElecDay.setDeviceId(energyElecMonth.getDeviceId());
        energyElecDay.setDataTime(energyElecMonth.getDataTime());
        if (StringUtils.isBlank(energyElecMonth.getDeviceId())) {
            return "modules/energyelecmonth/energyElecMonthList";
        }
        Page<EnergyElecDay> page = elecDayService.findPage(new Page<EnergyElecDay>(request, response), energyElecDay);
        model.addAttribute("page", page);
        List<EnergyElecDay> elecDays = page.getList();
        if (elecDays.size() > 0) {
            DayElecComparator dayElecComparator = new DayElecComparator();
            model.addAttribute("maxMonthElec", Collections.max(elecDays,dayElecComparator));
            model.addAttribute("minMonthElec", Collections.min(elecDays,dayElecComparator));
            model.addAttribute("sumMonthElec", DayElecComparator.getCount(elecDays,"sum"));
            model.addAttribute("avgMonthElec", DayElecComparator.getCount(elecDays,"avg"));
        }
        return "modules/energyelecmonth/energyElecMonthList";
    }

    @RequestMapping(value = "form")
    public String form(EnergyElecMonth energyElecMonth, Model model) {
        model.addAttribute("energyElecMonth", energyElecMonth);
        return "modules/energyelecmonth/energyElecMonthForm";
    }

}