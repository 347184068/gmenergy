/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.monitoritem.web;

import com.tp.ems.common.config.Global;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.monitoritem.entity.MonitorType;
import com.tp.ems.modules.monitoritem.service.MonitorTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 监测项类型Controller
 * @author 张丽
 * @version 2016-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/monitoritem/monitorType")
public class MonitorTypeController extends BaseController {

	@Autowired
	private MonitorTypeService monitorTypeService;
	
	@ModelAttribute
	public MonitorType get(@RequestParam(required=false) String id) {
		MonitorType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = monitorTypeService.get(id);
		}
		if (entity == null){
			entity = new MonitorType();
		}
		return entity;
	}
	
	@RequiresPermissions("monitoritem:monitorType:view")
	@RequestMapping(value = {"list", ""})
	public String list(MonitorType monitorType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MonitorType> page = monitorTypeService.findPage(new Page<MonitorType>(request, response), monitorType); 
		model.addAttribute("page", page);
		return "modules/monitoritem/monitorTypeList";
	}

	@RequiresPermissions("monitoritem:monitorType:view")
	@RequestMapping(value = "form")
	public String form(MonitorType monitorType, Model model) {
		model.addAttribute("monitorType", monitorType);
		return "modules/monitoritem/monitorTypeForm";
	}

	@RequiresPermissions("monitoritem:monitorType:edit")
	@RequestMapping(value = "save")
	public String save(MonitorType monitorType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, monitorType)){
			return form(monitorType, model);
		}
		monitorTypeService.save(monitorType);
		addMessage(redirectAttributes, "保存监测项成功");
		return "redirect:"+Global.getAdminPath()+"/monitoritem/monitorType/?repage";
	}
	
	@RequiresPermissions("monitoritem:monitorType:edit")
	@RequestMapping(value = "delete")
	public String delete(MonitorType monitorType, RedirectAttributes redirectAttributes) {
		monitorTypeService.delete(monitorType);
		addMessage(redirectAttributes, "删除监测项成功");
		return "redirect:"+Global.getAdminPath()+"/monitoritem/monitorType/?repage";
	}

}