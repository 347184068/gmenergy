/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.devices.web;

import com.tp.ems.common.config.Global;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.devices.service.MonitordevicesService;
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
 * 监测点Controller
 *
 * @author 徐钦政
 * @version 2016-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/devices/monitordevices")
public class MonitordevicesController extends BaseController {

    @Autowired
    private MonitordevicesService monitordevicesService;

    @ModelAttribute
    public Monitordevices get(@RequestParam(required = false) String id) {
        Monitordevices entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = monitordevicesService.get(id);
        }
        if (entity == null) {
            entity = new Monitordevices();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(Monitordevices monitordevices, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Monitordevices> page = monitordevicesService.findPage(new Page<Monitordevices>(request, response), monitordevices);
        model.addAttribute("page", page);
        return "modules/devices/monitordevicesList";
    }

    @RequestMapping(value = "form")
    public String form(Monitordevices monitordevices, Model model) {
        model.addAttribute("monitordevices", monitordevices);
        return "modules/devices/monitordevicesForm";
    }

    @RequestMapping(value = "save")
    public String save(Monitordevices monitordevices, Model model, RedirectAttributes redirectAttributes) {
        /*if (!beanValidator(model, monitordevices)){
			return form(monitordevices, model);
		}*/
        monitordevicesService.save(monitordevices);
        addMessage(redirectAttributes, "保存设备成功！");
        return "redirect:" + Global.getAdminPath() + "/devices/monitordevices?menu=" + monitordevices.getMenu();
    }

    @RequestMapping(value = "delete")
    public String delete(Monitordevices monitordevices, RedirectAttributes redirectAttributes) {
        monitordevicesService.delete(monitordevices);
        addMessage(redirectAttributes, "删除设备成功！");
        return "redirect:" + Global.getAdminPath() + "/devices/monitordevices/?menu=" + monitordevices.getMenu();
    }

}