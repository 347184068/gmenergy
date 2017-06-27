package com.tp.ems.modules.dashboard.web;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.dashboard.service.WaterMonthSumService;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;
import com.tp.ems.modules.poweranalysis.service.PowerAnalysisService;
import com.tp.ems.modules.tools.JqueryResult;
import com.tp.ems.modules.wateranlysis.entity.WaterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by tepusoft on 2017/5/22.
 */
@Controller
@RequestMapping(value = "${adminPath}/water/dashboard")
public class WaterMonthSumController extends BaseController{
    @Autowired
    private PowerAnalysisService powerAnalysisService;
    @Autowired
    private WaterMonthSumService waterMonthSumService;

    @RequestMapping(value = {"getWaterSumList"})
    public String getWaterSumList(WaterData waterData, Model model) {
        Monitordevices monitordevices = new Monitordevices();
        monitordevices.setMenu("water");
        List<Monitordevices> devicesList = powerAnalysisService.findList(monitordevices);
        model.addAttribute("deviceList",devicesList);

        return "modules/monthdashboard/waterDashBoard";
    }

    @RequestMapping(value = {"sumMonthWater"})
    @ResponseBody
    public JqueryResult sumMonthPower(WaterData waterData, Model model) {
        JqueryResult result = new JqueryResult();
        GsonOption option = new GsonOption();
        option = waterMonthSumService.getWaterMonthSum(waterData);
        if(option == null){
            result.setFlag(false);
            result.setMsg("当前没有数据");
            return result;
        }else{
            result.setFlag(true);
            result.setOption(option);
        }
        return result;
    }
}
