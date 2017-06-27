/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywateryear.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import com.tp.ems.modules.energyelecyear.utils.MonthElecComparator;
import com.tp.ems.modules.energywatermonth.entity.EnergyWaterMonth;
import com.tp.ems.modules.energywatermonth.service.EnergyWaterMonthService;
import com.tp.ems.modules.energywateryear.utils.MonthWaterComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.modules.energywateryear.entity.EnergyWaterYear;
import com.tp.ems.modules.energywateryear.service.EnergyWaterYearService;

import java.util.Collections;
import java.util.List;

/**
 * 水表每年数据Controller
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/energywateryear/energyWaterYear")
public class EnergyWaterYearController extends BaseController {

	@Autowired
	private EnergyWaterYearService waterYearService;

	@Autowired
	private EnergyWaterMonthService waterMonthService;

	@Autowired
	private EnergyDevicesService devicesService;
	
	@ModelAttribute
	public EnergyWaterYear get(@RequestParam(required=false) String id) {
		EnergyWaterYear entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = waterYearService.get(id);
		}
		if (entity == null){
			entity = new EnergyWaterYear();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(EnergyWaterYear energyWaterYear, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<EnergyDevices> devicesList = devicesService.findAllWaterDevices();
		model.addAttribute("deviceList", devicesList);
		EnergyWaterMonth energyWaterMonth = new EnergyWaterMonth();
		energyWaterMonth.setDeviceId(energyWaterYear.getDeviceId());
		energyWaterMonth.setDataYear(energyWaterYear.getSelectYear());
		if(StringUtils.isBlank(energyWaterYear.getDeviceId())){
			return "modules/energywateryear/energyWaterYearList";
		}
		Page<EnergyWaterMonth> page = waterMonthService.findPage(new Page<EnergyWaterMonth>(request, response), energyWaterMonth);
		model.addAttribute("page", page);
		List<EnergyWaterMonth> waterMonths = page.getList();
		if(waterMonths.size() > 0){
			MonthWaterComparator monthWaterComparator = new MonthWaterComparator();
			model.addAttribute("maxMonthWater", Collections.max(waterMonths,monthWaterComparator));
			model.addAttribute("minMonthWater", Collections.min(waterMonths,monthWaterComparator));
			model.addAttribute("sumMonthWater", MonthWaterComparator.getCount(waterMonths,"sum"));
			model.addAttribute("avgMonthWater", MonthWaterComparator.getCount(waterMonths,"avg"));
		}
		return "modules/energywateryear/energyWaterYearList";
	}


}