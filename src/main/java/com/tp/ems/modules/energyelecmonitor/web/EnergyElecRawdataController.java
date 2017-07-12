/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecmonitor.web;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;
import com.tp.ems.modules.tools.JqueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tp.ems.common.web.BaseController;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.modules.energyelecmonitor.entity.EnergyElecRawdata;
import com.tp.ems.modules.energyelecmonitor.service.EnergyElecRawdataService;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 电数据在线监控Controller
 * @author 徐韵轩
 * @version 2017-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/energyelecmonitor/energyElecRawdata")
public class EnergyElecRawdataController extends BaseController {

	@Autowired
	private EnergyElecRawdataService rawdataService;

	@Autowired
	private EnergyDevicesService devicesService;
	
	@ModelAttribute
	public EnergyElecRawdata get(@RequestParam(required=false) String id) {
		EnergyElecRawdata entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rawdataService.get(id);
		}
		if (entity == null){
			entity = new EnergyElecRawdata();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<EnergyDevices> devicesList = devicesService.findAllElecDevices();
		model.addAttribute("deviceList",devicesList);
		return "modules/energyelecmonitor/energyElecRawdataList";
	}


	@ResponseBody
	@RequestMapping(value = "getRealData")
	public JqueryResult showRealData(EnergyElecRawdata elecRawdata){
		JqueryResult result = new JqueryResult();
		String deviceId = elecRawdata.getDeviceId();
		if(StringUtils.isBlank(deviceId)){
			result.setFlag(false);
			result.setMsg("获取信息失败");
			return result;
		}
		elecRawdata.setCount(60);
		List<EnergyElecRawdata> rawdatas = rawdataService.findNewDataByCount(elecRawdata);
		GsonOption option = rawdataService.genBrokenLine(rawdatas);
		result.setFlag(true);
		result.setOption(option);
		return result;
	}


	/**
	 *
	 * 实时数据报表展示
	 */
	@RequestMapping(value = "rawDataReport")
	public String showRawDataReport(EnergyElecRawdata elecRawdata, HttpServletRequest request, HttpServletResponse response, Model model){
		List<EnergyDevices> devicesList = devicesService.findAllElecDevices();
		model.addAttribute("deviceList",devicesList);
		if(StringUtils.isBlank(elecRawdata.getDeviceId())){
			return "modules/energyelecmonitor/energyElecRawdataReport";
		}
		Page<EnergyElecRawdata> page = rawdataService.findPage(new Page<EnergyElecRawdata>(request, response), elecRawdata);
		model.addAttribute("page", page);
		return "modules/energyelecmonitor/energyElecRawdataReport";
	}



}