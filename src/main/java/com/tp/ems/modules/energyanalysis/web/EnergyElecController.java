package com.tp.ems.modules.energyanalysis.web;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 电能源分析controller
 * @Author XuYunXuan
 * @Date 2017/6/27 19:32
 */
@Controller
@RequestMapping(value = "${adminPath}/energyAnalysis/elec")
public class EnergyElecController extends BaseController {

    @Autowired
    private EnergyDevicesService devicesService;

    @RequestMapping(value = "")
    public String index(Model model){
        List<EnergyDevices> devicesList = devicesService.findAllElecDevices();
        model.addAttribute("deviceList",devicesList);
        return "modules/energyanalysis/elecDataShow";
    }
}
