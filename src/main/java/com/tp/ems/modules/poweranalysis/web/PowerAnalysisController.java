
package com.tp.ems.modules.poweranalysis.web;

import com.github.abel533.echarts.json.GsonOption;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;
import com.tp.ems.modules.poweranalysis.service.PowerAnalysisService;
import com.tp.ems.modules.tools.JqueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用电分析Controller
 *
 * @author 张丽
 * @version 2016-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/power/analysis")
public class PowerAnalysisController extends BaseController {

    @Autowired
    private PowerAnalysisService powerAnalysisService;

    /*@RequiresPermissions("sys:office:view")*/
    @RequestMapping(value = {""})
    public String index(Monitordevices devices, Model model, int type) {
        model.addAttribute("menu", type);
        model.addAttribute("tab", 2);
        return "modules/poweranalysis/powerAnalysisIndex";
    }

    @RequestMapping(value = {"getPowerLoadList"})
    public String getPowerLoadList(ElecDataAmount dataAmount, Model model) {
        Date currentDate = new Date();
        if (dataAmount.getDeviceId().equals("") || null == dataAmount.getDeviceId()) {
            List<Monitordevices> devicesList = powerAnalysisService.findList(new Monitordevices());
            if (devicesList.size() > 0) {
                dataAmount.setDeviceId(devicesList.get(1).getId());
            }
        }
        String type = dataAmount.getType();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (dataAmount.getInDate() == null) {
            dataAmount.setInDate(currentDate);
            if (type.equals("3")) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                String yesterday = sdf.format(cal.getTime());
                try {
                    Date date = sdf.parse(yesterday);
                    dataAmount.setInDate(date);
                } catch (ParseException e) {
                    logger.info("日期转化失败----> " + e);
                }
            }
        }
        GsonOption option = new GsonOption();
        option = powerAnalysisService.getYearLoadData(dataAmount);
        model.addAttribute("option", option);
        model.addAttribute("tab", 2);

        if (type.equals("1")) {
            sdf = new SimpleDateFormat("yyyy");
        } else if (type.equals("2")) {
            sdf = new SimpleDateFormat("yyyy-MM");
        }
        model.addAttribute("inDate", sdf.format(dataAmount.getInDate()));

        return "modules/poweranalysis/chartShow";
    }

    @RequestMapping(value = {"getRealTimeElec"})
    @ResponseBody
    public JqueryResult getRealTimeElec(ElecDataAmount dataAmount){
        GsonOption option = new GsonOption();
        JqueryResult result = new JqueryResult();
        option = powerAnalysisService.getYearElecData(dataAmount);
        if(option==null){
            result.setFlag(false);
            result.setMsg("当前没有数据");
        }else{
            result.setFlag(true);
            result.setOption(option);
        }
        return result;
    }

    @RequestMapping(value = {"getPowerElecList"})
    public String getPowerElecList(ElecDataAmount dataAmount, Model model) {
        Monitordevices monitordevices = new Monitordevices();
        monitordevices.setMenu("elec");
        List<Monitordevices> devicesList = powerAnalysisService.findList(monitordevices);
        model.addAttribute("deviceList",devicesList);

        return "modules/poweranalysis/chartShow";
    }

    /**
     * 获取监测点列表
     * @param extId
     * @param type
     * @param grade
     * @param isAll
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type,
                                              @RequestParam(required = false) Long grade, @RequestParam(required = false) Boolean isAll, HttpServletResponse response,String menu) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Monitordevices monitordevices = new Monitordevices();
        monitordevices.setMenu(menu);
        List<Monitordevices> list = powerAnalysisService.findList(monitordevices);
        for (int i = 0; i < list.size(); i++) {
            Monitordevices e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("pId", e.getParentId());
            map.put("pIds", e.getParentIds());
            map.put("iconCls","devices");
            map.put("icon","../../static/images/icon/device.png");
            map.put("name", e.getName());
            mapList.add(map);
        }
        return mapList;
    }


}