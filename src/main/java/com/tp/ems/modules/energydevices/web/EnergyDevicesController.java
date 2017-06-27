/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energydevices.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tp.ems.common.config.Global;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;

/**
 * 能源设备管理Controller
 * @author 徐韵轩
 * @version 2017-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/energydevices/energyDevices")
public class EnergyDevicesController extends BaseController {

	@Autowired
	private EnergyDevicesService energyDevicesService;
	
	@ModelAttribute
	public EnergyDevices get(@RequestParam(required=false) String id) {
		EnergyDevices entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = energyDevicesService.get(id);
		}
		if (entity == null){
			entity = new EnergyDevices();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(EnergyDevices energyDevices, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EnergyDevices> page = energyDevicesService.findPage(new Page<EnergyDevices>(request, response), energyDevices); 
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
		if (!beanValidator(model, energyDevices)){
			return form(energyDevices, model);
		}
		energyDevicesService.save(energyDevices);
		addMessage(redirectAttributes, "保存设备成功");
		return "redirect:"+Global.getAdminPath()+"/energydevices/energyDevices?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(EnergyDevices energyDevices, RedirectAttributes redirectAttributes) {
		energyDevicesService.delete(energyDevices);
		addMessage(redirectAttributes, "删除设备成功");
		return "redirect:"+Global.getAdminPath()+"/energydevices/energyDevices/?repage";
	}

}