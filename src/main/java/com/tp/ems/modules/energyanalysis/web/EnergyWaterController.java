package com.tp.ems.modules.energyanalysis.web;

import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 水能源分析controller
 * @Author XuYunXuan
 * @Date 2017/6/27 19:33
 */
@Controller
@RequestMapping(value = "/energyAnalysis/water")
public class EnergyWaterController extends BaseController {

    @Autowired
    private EnergyDevicesService devicesService;

    @RequestMapping(value = "")
    public String index(Model model){
        List<EnergyDevices> devicesList = devicesService.findAllWaterDevices();
        model.addAttribute("deviceList",devicesList);
        return "modules/energyanalysis/waterDataShow";
    }
}
