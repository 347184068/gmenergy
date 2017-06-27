/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecday.web;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelecday.service.EnergyElecDayService;
import com.tp.ems.modules.energyelecday.utils.HourElecComparator;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;
import com.tp.ems.modules.energyelechour.service.EnergyElecHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * 电表每天数据Controller
 * @author 徐韵轩
 * @version 2017-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/energyelecday/energyElecDay")
public class EnergyElecDayController extends BaseController {

	@Autowired
	private EnergyElecDayService elecDayService;

	@Autowired
	private EnergyDevicesService devicesService;

	@Autowired
	private EnergyElecHourService elecHourService;
	
	@ModelAttribute
	public EnergyElecDay get(@RequestParam(required=false) String id) {
		EnergyElecDay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = elecDayService.get(id);
		}
		if (entity == null){
			entity = new EnergyElecDay();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(EnergyElecDay energyElecDay, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<EnergyDevices> devicesList = devicesService.findAllElecDevices();
		model.addAttribute("deviceList", devicesList);
		EnergyElecHour energyElecHour = new EnergyElecHour();
		energyElecHour.setDeviceId(energyElecDay.getDeviceId());
		energyElecHour.setDataTime(energyElecDay.getDataTime());
		if(StringUtils.isBlank(energyElecDay.getDeviceId())){
			return "modules/energyelecday/energyElecDayList";
		}
		Page<EnergyElecHour> page = elecHourService.findPage(new Page<EnergyElecHour>(request, response), energyElecHour);
		model.addAttribute("page", page);
		List<EnergyElecHour> elecHours=page.getList();
		if(elecHours.size()>0){
			HourElecComparator hourElecComparator = new HourElecComparator();
			model.addAttribute("maxDayElec", Collections.max(elecHours,hourElecComparator));
			model.addAttribute("minDayElec", Collections.min(elecHours,hourElecComparator));
			model.addAttribute("sumDayElec", HourElecComparator.getCount(elecHours,"sum"));
			model.addAttribute("avgDayElec", HourElecComparator.getCount(elecHours,"avg"));
		}
		return "modules/energyelecday/energyElecDayList";
	}


}