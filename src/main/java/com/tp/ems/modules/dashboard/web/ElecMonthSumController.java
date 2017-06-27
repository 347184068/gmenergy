package com.tp.ems.modules.dashboard.web;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.dashboard.service.ElecMonthSumService;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;
import com.tp.ems.modules.poweranalysis.service.PowerAnalysisService;
import com.tp.ems.modules.tools.JqueryResult;
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
@RequestMapping(value = "${adminPath}/elec/dashboard")
public class ElecMonthSumController extends BaseController{
    @Autowired
    private PowerAnalysisService powerAnalysisService;
    @Autowired
    private ElecMonthSumService elecMonthSumService;

    @RequestMapping(value = {"getPowerElecList"})
     public String getPowerElecList(ElecDataAmount dataAmount, Model model) {
        Monitordevices monitordevices = new Monitordevices();
        monitordevices.setMenu("elec");
        List<Monitordevices> devicesList = powerAnalysisService.findList(monitordevices);
        model.addAttribute("deviceList",devicesList);

        return "modules/monthdashboard/elecDashBoard";
    }

    @RequestMapping(value = {"sumMonthPower"})
    @ResponseBody
    public JqueryResult sumMonthPower(ElecDataAmount dataAmount, Model model) {
        JqueryResult result = new JqueryResult();
        GsonOption option = new GsonOption();
        option = elecMonthSumService.getElecDashBoard(dataAmount);
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


