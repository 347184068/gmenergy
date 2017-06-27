/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywatermonth.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import com.tp.ems.modules.energywaterday.entity.EnergyWaterDay;
import com.tp.ems.modules.energywaterday.service.EnergyWaterDayService;
import com.tp.ems.modules.energywaterday.utils.HourWaterComparator;
import com.tp.ems.modules.energywatermonth.utils.DayWaterComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.modules.energywatermonth.entity.EnergyWaterMonth;
import com.tp.ems.modules.energywatermonth.service.EnergyWaterMonthService;

import java.util.Collections;
import java.util.List;

/**
 * 水表每月数据Controller
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/energywatermonth/energyWaterMonth")
public class EnergyWaterMonthController extends BaseController {

	@Autowired
	private EnergyWaterMonthService waterMonthService;

	@Autowired
	private EnergyDevicesService devicesService;

	@Autowired
	private EnergyWaterDayService waterDayService;
	
	@ModelAttribute
	public EnergyWaterMonth get(@RequestParam(required=false) String id) {
		EnergyWaterMonth entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = waterMonthService.get(id);
		}
		if (entity == null){
			entity = new EnergyWaterMonth();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(EnergyWaterMonth energyWaterMonth, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<EnergyDevices> devicesList = devicesService.findAllWaterDevices();
		model.addAttribute("deviceList", devicesList);
		EnergyWaterDay energyWaterDay = new EnergyWaterDay();
		energyWaterDay.setDeviceId(energyWaterMonth.getDeviceId());
		energyWaterDay.setDataTime(energyWaterMonth.getDataTime());
		if(StringUtils.isBlank(energyWaterMonth.getDeviceId())){
			return "modules/energywatermonth/energyWaterMonthList";
		}
		Page<EnergyWaterDay> page = waterDayService.findPage(new Page<EnergyWaterDay>(request, response), energyWaterDay);
		model.addAttribute("page", page);
		List<EnergyWaterDay> waterDays = page.getList();
		if(waterDays.size() > 0){
			DayWaterComparator dayWaterComparator = new DayWaterComparator();
			model.addAttribute("maxMonthWater", Collections.max(waterDays,dayWaterComparator));
			model.addAttribute("minMonthWater", Collections.min(waterDays,dayWaterComparator));
			model.addAttribute("sumMonthWater", DayWaterComparator.getCount(waterDays,"sum"));
			model.addAttribute("avgMonthWater", DayWaterComparator.getCount(waterDays,"avg"));
		}
		return "modules/energywatermonth/energyWaterMonthList";
	}

}