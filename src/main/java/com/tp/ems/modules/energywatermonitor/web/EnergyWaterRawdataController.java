/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywatermonitor.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import com.tp.ems.modules.energyelecmonitor.entity.EnergyElecRawdata;
import com.tp.ems.modules.tools.JqueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tp.ems.common.web.BaseController;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.modules.energywatermonitor.entity.EnergyWaterRawdata;
import com.tp.ems.modules.energywatermonitor.service.EnergyWaterRawdataService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 水表在线监控Controller
 * @author 徐韵轩
 * @version 2017-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/energywatermonitor/energyWaterRawdata")
public class EnergyWaterRawdataController extends BaseController {

	@Autowired
	private EnergyWaterRawdataService waterRawdataService;

	@Autowired
	private EnergyDevicesService devicesService;

	@ModelAttribute
	public EnergyWaterRawdata get(@RequestParam(required=false) String id) {
		EnergyWaterRawdata entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = waterRawdataService.get(id);
		}
		if (entity == null){
			entity = new EnergyWaterRawdata();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<EnergyDevices> devicesList = devicesService.findAllWaterDevices();
		model.addAttribute("deviceList", devicesList);
		return "modules/energywatermonitor/energyWaterRawdataList";
	}



	@ResponseBody
	@RequestMapping(value = "getRealData")
	public JqueryResult showWaterRealData(EnergyWaterRawdata waterRawdata){
		JqueryResult result = new JqueryResult();
		String deviceId = waterRawdata.getDeviceId();
		if(StringUtils.isBlank(deviceId)){
			result.setFlag(false);
			result.setMsg("获取信息失败");
			return result;
		}
		waterRawdata.setCount(60);
		List<EnergyWaterRawdata> rawdatas = waterRawdataService.findNewDataByCount(waterRawdata);
		GsonOption option = waterRawdataService.genBrokenLine(rawdatas);
		result.setFlag(true);
		result.setOption(option);
		return result;
	}



}