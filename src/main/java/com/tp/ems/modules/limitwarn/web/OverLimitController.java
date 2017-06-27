/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.limitwarn.web;

import com.google.common.collect.Lists;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.devices.service.MonitordevicesService;
import com.tp.ems.modules.limitwarn.entity.OverLimit;
import com.tp.ems.modules.limitwarn.service.OverLimitService;
import com.tp.ems.modules.monitoritem.entity.MonitorType;
import com.tp.ems.modules.monitoritem.service.MonitorTypeService;
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
import java.util.List;

/**
 * 监测预警Controller
 * @author 张丽
 * @version 2016-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/limitwarn/overLimit")
public class OverLimitController extends BaseController {

	@Autowired
	private OverLimitService overLimitService;
	@Autowired
	private MonitorTypeService typeService;
	@Autowired
	private MonitordevicesService deviceSevice;

	@ModelAttribute
	public OverLimit get(@RequestParam(required=false) String id) {
		OverLimit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = overLimitService.get(id);
		}
		if (entity == null){
			entity = new OverLimit();
		}
		return entity;
	}
	
//监测预警首页
	@RequestMapping(value = {"list", ""})
	public String list(OverLimit overLimit, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<OverLimit> page = overLimitService.findPage(new Page<OverLimit>(request, response), overLimit);
		List<MonitorType> typeList = new ArrayList();
		typeList = typeList(overLimit.getMenu());
		model.addAttribute("typeList", typeList);
		List<OverLimit> limitList = Lists.newArrayList();
		limitList = getAllOverLimit(overLimit);
		model.addAttribute("limitList", limitList);
		model.addAttribute("menu",overLimit.getMenu());
		return "modules/limitwarn/overLimitList";
	}


	public List<MonitorType> typeList(String menu) {
		List<MonitorType> typeList = new ArrayList();
		MonitorType monitorType = new MonitorType();
		monitorType.setMenu(menu);
		typeList= typeService.findList(monitorType);
		return typeList;
	}

	public List<OverLimit> getAllOverLimit(OverLimit overLimit){
		List<OverLimit> limitList = Lists.newArrayList();
		limitList = overLimitService.getAllOverLimits(overLimit);
		return limitList;
	}

	//监测预警条件查询
	@RequestMapping(value = {"limitList"})
	public String limitList(OverLimit overLimit, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<MonitorType> typeList = new ArrayList();
		typeList = typeList(overLimit.getMenu());
		model.addAttribute("typeList", typeList);

		List<OverLimit> limitList = new ArrayList<>();
		limitList = overLimitService.getLimitRecords(overLimit);
		model.addAttribute("selectedType",overLimit.getMonitorType());
		model.addAttribute("limitList",limitList);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(overLimit.getStartTime());
		String endDate = sdf.format(overLimit.getEndTime());
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("menu",overLimit.getMenu());
		return "modules/limitwarn/overLimitList";
	}



	//单项节点监测预警首页
	@RequestMapping(value = {"oneDeviceList", ""})
	public String oneDeviceList(OverLimit overLimit, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<OverLimit> page = overLimitService.findPage(new Page<OverLimit>(request, response), overLimit);
		List<MonitorType> typeList = new ArrayList();
		typeList = typeList(overLimit.getMenu());
		model.addAttribute("typeList", typeList);

		List<OverLimit> limitList = Lists.newArrayList();
		limitList = overLimitService.getOneDeviceAllOverLimit(overLimit);
		model.addAttribute("limitList", limitList);
		model.addAttribute("menu",overLimit.getMenu());
		return "modules/limitwarn/onedeviceoverLimitList";
	}

	/**
	 * 单项节点越限记录条件查询
	 * @param overLimit
	 * @return
	 */
	@RequestMapping(value = {"getOneDeviceOverLimitByCondition",""})
	public String getOneDeviceOverLimitByCondition(OverLimit overLimit,Model model){
		List<MonitorType> typeList = new ArrayList();
		typeList = typeList(overLimit.getMenu());
		model.addAttribute("typeList", typeList);
		List<OverLimit> overLimits = Lists.newArrayList();
		overLimits = overLimitService.getOneDeviceOverLimit(overLimit);
		model.addAttribute("limitList",overLimits);

		String deviceName = deviceSevice.get(overLimit.getDeviceId()).getName();
		model.addAttribute("deviceName",deviceName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(overLimit.getStartTime()==null){
		}else{
			String startDate = sdf.format(overLimit.getStartTime());
			model.addAttribute("startDate",startDate);
		}

		if(overLimit.getEndTime()==null){
		}else{
			String endDate = sdf.format(overLimit.getEndTime());
			model.addAttribute("endDate",endDate);
		}
		model.addAttribute("menu",overLimit.getMenu());
		return "modules/limitwarn/onedeviceoverLimitList";
	}


}