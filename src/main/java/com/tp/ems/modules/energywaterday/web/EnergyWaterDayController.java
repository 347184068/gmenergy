/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywaterday.web;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import com.tp.ems.modules.energywaterday.entity.EnergyWaterDay;
import com.tp.ems.modules.energywaterday.service.EnergyWaterDayService;
import com.tp.ems.modules.energywaterday.utils.HourWaterComparator;
import com.tp.ems.modules.energywaterhour.entity.EnergyWaterHour;
import com.tp.ems.modules.energywaterhour.service.EnergyWaterHourService;
import com.tp.ems.modules.tools.JqueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * 水表每天数据Controller
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/energywaterday/energyWaterDay")
public class EnergyWaterDayController extends BaseController {

	@Autowired
	private EnergyWaterDayService waterDayService;

	@Autowired
	private EnergyDevicesService devicesService;

	@Autowired
	private EnergyWaterHourService waterHourService;
	
	@ModelAttribute
	public EnergyWaterDay get(@RequestParam(required=false) String id) {
		EnergyWaterDay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = waterDayService.get(id);
		}
		if (entity == null){
			entity = new EnergyWaterDay();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(EnergyWaterDay energyWaterDay, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<EnergyDevices> devicesList = devicesService.findAllWaterDevices();
		model.addAttribute("deviceList", devicesList);
		EnergyWaterHour energyWaterHour = new EnergyWaterHour();
		energyWaterHour.setDeviceId(energyWaterDay.getDeviceId());
		energyWaterHour.setDataTime(energyWaterDay.getDataTime());
		if(StringUtils.isBlank(energyWaterDay.getDeviceId())){
			return "modules/energywaterday/energyWaterDayList";
		}
		Page<EnergyWaterHour> page = waterHourService.findPage(new Page<EnergyWaterHour>(request, response), energyWaterHour);
		model.addAttribute("page", page);
		List<EnergyWaterHour> waterHours = page.getList();
		if(waterHours.size()>0){
			HourWaterComparator hourWaterComparator = new HourWaterComparator();
			model.addAttribute("maxDayWater", Collections.max(waterHours,hourWaterComparator));
			model.addAttribute("minDayWater", Collections.min(waterHours,hourWaterComparator));
			model.addAttribute("sumDayWater", HourWaterComparator.getCount(waterHours,"sum"));
			model.addAttribute("avgDayWater", HourWaterComparator.getCount(waterHours,"avg"));
		}
		return "modules/energywaterday/energyWaterDayList";
	}

	@RequestMapping(value = "showWaterDayChart")
	public String showWaterChart(Model model){
		List<EnergyDevices> devicesList = devicesService.findAllWaterDevices();
		model.addAttribute("deviceList",devicesList);
		return "modules/energywaterday/energyWaterDayChart";
	}


	@ResponseBody
	@RequestMapping(value = "getWaterDayChart")
	public JqueryResult getWaterDayChart(EnergyWaterDay energyWaterDay){
		JqueryResult result = new JqueryResult();
		EnergyWaterHour waterHour = new EnergyWaterHour();
		waterHour.setDeviceId(energyWaterDay.getDeviceId());
		waterHour.setDataTime(energyWaterDay.getDataTime());
		if(StringUtils.isBlank(energyWaterDay.getDeviceId())){
			result.setFlag(false);
			result.setMsg("获取信息失败");
			return result;
		}
		List<EnergyWaterHour> waterHours = waterHourService.findList(waterHour);
		GsonOption option = waterHourService.genHourChart(waterHour,waterHours);
		result.setFlag(true);
		result.setOption(option);
		return result;
	}

}