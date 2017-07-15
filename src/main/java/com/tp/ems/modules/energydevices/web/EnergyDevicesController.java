/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energydevices.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.modules.energydevices.utils.DrawChartUtils;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelecday.service.EnergyElecDayService;
import com.tp.ems.modules.energyelecmonth.entity.EnergyElecMonth;
import com.tp.ems.modules.energyelecmonth.service.EnergyElecMonthService;
import com.tp.ems.modules.energyelecmonth.utils.DayElecComparator;
import com.tp.ems.modules.energyelecyear.entity.EnergyElecYear;
import com.tp.ems.modules.energyelecyear.service.EnergyElecYearService;
import com.tp.ems.modules.energyelecyear.utils.MonthElecComparator;
import com.tp.ems.modules.energywaterday.entity.EnergyWaterDay;
import com.tp.ems.modules.energywaterday.service.EnergyWaterDayService;
import com.tp.ems.modules.energywatermonth.entity.EnergyWaterMonth;
import com.tp.ems.modules.energywatermonth.service.EnergyWaterMonthService;
import com.tp.ems.modules.energywatermonth.utils.DayWaterComparator;
import com.tp.ems.modules.energywateryear.utils.MonthWaterComparator;
import com.tp.ems.modules.tools.LimitResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tp.ems.common.config.Global;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 能源设备管理Controller
 *
 * @author 徐韵轩
 * @version 2017-06-30
 */
@Controller
@RequestMapping(value = "${adminPath}/energydevices/energyDevices")
public class EnergyDevicesController extends BaseController {

    @Autowired
    private EnergyDevicesService devicesService;

    @Autowired
    private EnergyElecDayService elecDayService;

    @Autowired
    private EnergyWaterDayService waterDayService;

    @Autowired
    private EnergyElecMonthService elecMonthService;

    @Autowired
    private EnergyWaterMonthService waterMonthService;

    @ModelAttribute
    public EnergyDevices get(@RequestParam(required = false) String id) {
        EnergyDevices entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = devicesService.get(id);
        }
        if (entity == null) {
            entity = new EnergyDevices();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(EnergyDevices energyDevices, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<EnergyDevices> page = devicesService.findPage(new Page<EnergyDevices>(request, response), energyDevices);
        model.addAttribute("page", page);
        return "modules/energydevices/energyDevicesList";
    }

    @RequestMapping(value = "form")
    public String form(EnergyDevices energyDevices, Model model) {
        model.addAttribute("energyDevices", energyDevices);
        return "modules/energydevices/energyDevicesForm";
    }

    @RequestMapping(value = "save")
    public String save(EnergyDevices energyDevices, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, energyDevices)) {
            return form(energyDevices, model);
        }
        devicesService.save(energyDevices);
        addMessage(redirectAttributes, "保存设备成功");
        return "redirect:" + Global.getAdminPath() + "/energydevices/energyDevices/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(EnergyDevices energyDevices, RedirectAttributes redirectAttributes) {
        devicesService.delete(energyDevices);
        addMessage(redirectAttributes, "删除设备成功");
        return "redirect:" + Global.getAdminPath() + "/energydevices/energyDevices/?repage";
    }

    /**
     * 电设备限额占比
     *
     * @return
     */
    @RequestMapping(value = "elecLimt")
    public String energyElecLimit(Model model) {
        model.addAttribute("deviceList", devicesService.findAllElecDevices());
        return "modules/energydevices/energyElecLimit";
    }


    /**
     * 水设备限额占比
     *
     * @return
     */
    @RequestMapping(value = "waterLimt")
    public String energyWaterLimit(Model model) {
        model.addAttribute("deviceList", devicesService.findAllWaterDevices());
        return "modules/energydevices/energyWaterLimit";
    }

    /**
     * 根据时间获取某一电设备的限额占比图  月消耗，年消耗
     */
    @ResponseBody
    @RequestMapping(value = "showElecLimit")
    public LimitResult showElecLimit(String deviceId, Date inDate) {
        LimitResult result = new LimitResult();
        EnergyDevices devices = devicesService.getDeviceByDeviceId(deviceId);
        String monthLimit = devices.getMonthLimit();
        String yearLimit = devices.getYearLimit();
        List<EnergyElecDay> dayList = elecDayService.findByMonth(deviceId, inDate);
        List<EnergyElecMonth> monthList = elecMonthService.findByYear(deviceId, inDate);
        double monthSum = DayElecComparator.getCount(dayList, "sum");
        double yearSum = MonthElecComparator.getCount(monthList, "sum");
        setChartParam(result, monthLimit, yearLimit, monthSum, yearSum);
        return result;
    }


    /**
     * 根据时间获取某一水设备的限额占比图  月消耗，年消耗
     */
    @ResponseBody
    @RequestMapping(value = "showWaterLimit")
    public LimitResult showWaterLimit(String deviceId, Date inDate){
        LimitResult result = new LimitResult();
        EnergyDevices devices = devicesService.getDeviceByDeviceId(deviceId);
        String monthLimit = devices.getMonthLimit();
        String yearLimit = devices.getYearLimit();
        List<EnergyWaterDay> dayList = waterDayService.findByMonth(deviceId, inDate);
        List<EnergyWaterMonth> monthList = waterMonthService.findByYear(deviceId, inDate);
        double monthSum = DayWaterComparator.getCount(dayList, "sum");
        double yearSum = MonthWaterComparator.getCount(monthList, "sum");
        setChartParam(result, monthLimit, yearLimit, monthSum, yearSum);
        return result;
    }

    private void setChartParam(LimitResult result, String monthLimit, String yearLimit, double monthSum, double yearSum) {
        if (StringUtils.isBlank(monthLimit)) {
            result.setMonthFlag(false);
            result.setMonthMsg("未设定月限额");
            result.setMonthOption(DrawChartUtils.genNoneChart());
        } else {
            //绘制月限额
            GsonOption option = DrawChartUtils.genLimitChart(monthLimit, monthSum);
            result.setMonthFlag(true);
            result.setMonthOption(option);
            result.setMonthLimit(monthLimit);
            BigDecimal b = BigDecimal.valueOf(monthSum).divide(BigDecimal.valueOf(Double.parseDouble(monthLimit)), 5, BigDecimal.ROUND_HALF_UP);
            b = b.multiply(BigDecimal.valueOf(100));
            b = b.setScale(2,BigDecimal.ROUND_HALF_UP);
            result.setMonthPercent(b.toString() + "%");
        }
        if (StringUtils.isBlank(yearLimit)) {
            result.setYearFlag(false);
            result.setYearMsg("未设定年限额");
            result.setYearOption(DrawChartUtils.genNoneChart());
        } else {
            //绘制年限额
            GsonOption option = DrawChartUtils.genLimitChart(yearLimit, yearSum);
            result.setYearFlag(true);
            result.setYearOption(option);
            result.setYearLimit(yearLimit);
            BigDecimal b = BigDecimal.valueOf(yearSum).divide(BigDecimal.valueOf(Double.parseDouble(yearLimit)), 5, BigDecimal.ROUND_HALF_UP);
            b = b.multiply(BigDecimal.valueOf(100));
            b = b.setScale(2,BigDecimal.ROUND_HALF_UP);
            result.setYearPercent(b.toString() + "%");
        }
        result.setMonthSum(monthSum);
        result.setYearSum(yearSum);
    }

}