/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecyear.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import com.tp.ems.modules.energyelecmonth.entity.EnergyElecMonth;
import com.tp.ems.modules.energyelecmonth.service.EnergyElecMonthService;
import com.tp.ems.modules.energyelecyear.utils.MonthElecComparator;
import com.tp.ems.modules.tools.JqueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.modules.energyelecyear.entity.EnergyElecYear;
import com.tp.ems.modules.energyelecyear.service.EnergyElecYearService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * 电表每年数据Controller
 *
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/energyelecyear/energyElecYear")
public class EnergyElecYearController extends BaseController {

    @Autowired
    private EnergyElecYearService elecYearService;

    @Autowired
    private EnergyElecMonthService elecMonthService;

    @Autowired
    private EnergyDevicesService devicesService;

    @ModelAttribute
    public EnergyElecYear get(@RequestParam(required = false) String id) {
        EnergyElecYear entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = elecYearService.get(id);
        }
        if (entity == null) {
            entity = new EnergyElecYear();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(EnergyElecYear energyElecYear, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<EnergyDevices> devicesList = devicesService.findAllElecDevices();
        model.addAttribute("deviceList", devicesList);
        EnergyElecMonth energyElecMonth = new EnergyElecMonth();
        energyElecMonth.setDeviceId(energyElecYear.getDeviceId());
        energyElecMonth.setDataYear(energyElecYear.getSelectYear());
        if (StringUtils.isBlank(energyElecYear.getDeviceId())) {
            return "modules/energyelecyear/energyElecYearList";
        }
        Page<EnergyElecMonth> page = elecMonthService.findPage(new Page<EnergyElecMonth>(request, response), energyElecMonth);
        model.addAttribute("page", page);
        List<EnergyElecMonth> elecMonths = page.getList();
        if (elecMonths.size() > 0) {
            MonthElecComparator monthElecComparator = new MonthElecComparator();
            model.addAttribute("maxMonthElec", Collections.max(elecMonths, monthElecComparator));
            model.addAttribute("minMonthElec", Collections.min(elecMonths, monthElecComparator));
            model.addAttribute("sumMonthElec", MonthElecComparator.getCount(elecMonths, "sum"));
            model.addAttribute("avgMonthElec", MonthElecComparator.getCount(elecMonths, "avg"));
        }
        return "modules/energyelecyear/energyElecYearList";
    }

    @RequestMapping(value = "form")
    public String form(EnergyElecYear energyElecYear, Model model) {
        model.addAttribute("energyElecYear", energyElecYear);
        return "modules/energyelecyear/energyElecYearForm";
    }

    @RequestMapping(value = "showElecMonthChart")
    public String showElecYearChart(Model model) {
        List<EnergyDevices> devicesList = devicesService.findAllElecDevices();
        model.addAttribute("deviceList", devicesList);
        return "modules/energyelecyear/energyElecYearChart";
    }


    @ResponseBody
    @RequestMapping(value = "getElecYearChart")
    public JqueryResult drawMonthChart(EnergyElecYear energyElecYear) {
        JqueryResult result = new JqueryResult();
        EnergyElecMonth energyElecMonth = new EnergyElecMonth();
        energyElecMonth.setDeviceId(energyElecYear.getDeviceId());
        energyElecMonth.setDataYear(energyElecYear.getSelectYear());
        if (StringUtils.isBlank(energyElecYear.getDeviceId())) {
            result.setFlag(false);
            result.setMsg("获取信息失败");
            return result;
        }
        List<EnergyElecMonth> elecMonths = elecMonthService.findList(energyElecMonth);
        GsonOption option = elecMonthService.genMonthChart(energyElecMonth,elecMonths);
        result.setFlag(true);
        result.setOption(option);
        return result;
    }

}