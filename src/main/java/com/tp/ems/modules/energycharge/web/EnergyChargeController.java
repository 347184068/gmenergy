/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energycharge.web;

import com.github.abel533.echarts.json.GsonOption;
import com.google.common.collect.Lists;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.energycharge.entity.EnergyCharge;
import com.tp.ems.modules.energycharge.service.EnergyChargeService;
import com.tp.ems.modules.poweranalysis.service.PowerAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监测点电费计算Controller
 * @author 张丽
 * @version 2016-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/energycharge/energyCharge")
public class EnergyChargeController extends BaseController {

	@Autowired
	private EnergyChargeService energyChargeService;
	@Autowired
	private PowerAnalysisService analysisService;
	
	@ModelAttribute
	public EnergyCharge get(@RequestParam(required=false) String id) {
		EnergyCharge entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = energyChargeService.get(id);
		}
		if (entity == null){
			entity = new EnergyCharge();
		}
		return entity;
	}

	/**
	 * 单监测点电费页面
	 * @param energyCharge
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(EnergyCharge energyCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<EnergyCharge> list= Lists.newArrayList();
		model.addAttribute("option_elec","0");
		model.addAttribute("option_charge","0");
		model.addAttribute("list",list);
		return "modules/energycharge/energyChargeList";
	}

	@RequestMapping(value = {"getOneDeviceChartInfo", ""})
	public String getOneDeviceChartInfo(EnergyCharge energyCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String,Object> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		model.addAttribute("startDate",sdf.format(energyCharge.getStartTime()));
		model.addAttribute("endDate",sdf.format(energyCharge.getEndTime()));
		List<EnergyCharge> list= Lists.newArrayList();
		String deviceName = analysisService.get(energyCharge.getDeviceId()).getName();
		model.addAttribute("deviceName",deviceName);
		map = energyChargeService.getOneDeviceElecBar(energyCharge);
		if(map.size()>0){
			GsonOption option_elec = (GsonOption) map.get("elec");
			GsonOption option_charge = (GsonOption)map.get("charge");
			list = (List<EnergyCharge>) map.get("list");
			model.addAttribute("option_elec",option_elec);
			model.addAttribute("option_charge",option_charge);
			model.addAttribute("list",list);
		}else{
			model.addAttribute("option_elec","0");
			model.addAttribute("option_charge","0");
			model.addAttribute("list",list);
		}
		return "modules/energycharge/energyChargeList";
	}

	//多监测点电费页面
	@RequestMapping(value = {"diffDeviceList", ""})
	public String diffDeviceList(EnergyCharge energyCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<EnergyCharge> list= Lists.newArrayList();
		model.addAttribute("option_elec","0");
		model.addAttribute("option_charge","0");
		model.addAttribute("list",list);
		return "modules/energycharge/energyChargeDiffDeviceList";
	}

	@RequestMapping(value = {"getDiffDeviceChartInfo", ""})
	public String getDiffDeviceChartInfo(EnergyCharge energyCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String,Object> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		model.addAttribute("startDate",sdf.format(energyCharge.getStartTime()));
		model.addAttribute("endDate",sdf.format(energyCharge.getEndTime()));

		//多id
		String deviceIds = energyCharge.getDeviceId();
		String[] ids = deviceIds.split(",");
		List<String> idList = new ArrayList<>();
		int len = ids.length;
		for(int i=0;i<len;i++){
			idList.add(ids[i]);
		}
		String deviceNames ="";
		List<Monitordevices> deviceList= analysisService.findDeviceListByIds(idList);
		int len_name = deviceList.size();
		for(int i=0;i<len;i++){
			String deviceName = deviceList.get(i).getName();
			deviceNames += deviceName+",";
		}
		deviceNames= deviceNames.substring(0,deviceNames.length()-1);
		model.addAttribute("deviceName", deviceNames);
		//condition param
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("unitPrice",energyCharge.getUnitPrice());
		paramMap.put("list",idList);
		paramMap.put("startTime",energyCharge.getStartTime());
		paramMap.put("endTime",energyCharge.getEndTime());
		List<EnergyCharge> list=Lists.newArrayList();

				map = energyChargeService.getDiffDeviceElecBar(paramMap,idList);
		if(map.size()>0){
			GsonOption option_elec = (GsonOption) map.get("elec");
			GsonOption option_charge = (GsonOption)map.get("charge");
			list = (List<EnergyCharge>) map.get("list");
			model.addAttribute("deviceName",deviceNames);
			model.addAttribute("option_elec",option_elec);
			model.addAttribute("option_charge",option_charge);
			model.addAttribute("list",list);
		}else{
			model.addAttribute("option_elec","0");
			model.addAttribute("option_charge","0");
			model.addAttribute("list",list);
		}
		return "modules/energycharge/energyChargeDiffDeviceList";
	}


	//所有监测点电费页面
	@RequestMapping(value = {"allDeviceList", ""})
	public String allDeviceList(EnergyCharge energyCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<EnergyCharge> list= Lists.newArrayList();
		model.addAttribute("option_elec","0");
		model.addAttribute("option_charge","0");
		model.addAttribute("list",list);
		return "modules/energycharge/energyChargeAllDeviceList";
	}

	@RequestMapping(value = {"getAllDeviceChartInfo", ""})
	public String getAllDeviceChartInfo(EnergyCharge energyCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String,Object> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		model.addAttribute("startDate",sdf.format(energyCharge.getStartTime()));
		model.addAttribute("endDate",sdf.format(energyCharge.getEndTime()));
		List<EnergyCharge> list=Lists.newArrayList();
		map = energyChargeService.getAllDeviceElecBar(energyCharge);
		if(map.size()>0){
			GsonOption option_elec = (GsonOption) map.get("elec");
			GsonOption option_charge = (GsonOption)map.get("charge");
			list = (List<EnergyCharge>) map.get("list");
			model.addAttribute("option_elec",option_elec);
			model.addAttribute("option_charge",option_charge);
			model.addAttribute("list",list);
		}else{
			model.addAttribute("option_elec","0");
			model.addAttribute("option_charge","0");
			model.addAttribute("list",list);
		}
		return "modules/energycharge/energyChargeAllDeviceList";
	}



}