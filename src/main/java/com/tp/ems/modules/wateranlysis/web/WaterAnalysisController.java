
package com.tp.ems.modules.wateranlysis.web;

import com.github.abel533.echarts.json.GsonOption;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.poweranalysis.service.PowerAnalysisService;
import com.tp.ems.modules.tools.JqueryResult;
import com.tp.ems.modules.wateranlysis.entity.WaterData;
import com.tp.ems.modules.wateranlysis.service.WaterAnalysisService;
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
 * 用水分析Controller
 *
 * @author 张丽
 * @version 2016-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/water/analysis")
public class WaterAnalysisController extends BaseController {

    @Autowired
    private PowerAnalysisService powerAnalysisService;
    @Autowired
    private WaterAnalysisService waterAnalysisService;

    private Date inDate =new Date();

    @RequestMapping(value = {"list"})
    public String list(WaterData waterData, Model model) {
        Monitordevices monitordevices = new Monitordevices();
        monitordevices.setMenu("water");
        List<Monitordevices> devicesList = powerAnalysisService.findList(monitordevices);
        model.addAttribute("deviceList", devicesList);
        return "modules/wateranlysis/waterChartShow";
    }
    @RequestMapping(value = {"getWaterData"})
    @ResponseBody
    public JqueryResult getWaterData(WaterData waterData){
        JqueryResult result = new JqueryResult();
        /*GsonOption option = new GsonOption();
        option = waterAnalysisService.generateBar(waterData);
        if(option==null){
            result.setFlag(false);
            result.setMsg("当前没有数据");
        }else{
            result.setFlag(true);
            result.setOption(option);
        }*/
        GsonOption option_line = new GsonOption();
        option_line = waterAnalysisService.generateLine(waterData);
        if(option_line==null){
            result.setFlag(false);
            result.setMsg("当前没有数据");
        }else{
            result.setFlag(true);
            result.setOtherOption(option_line);
        }
        return result;
    }


    @RequestMapping(value = {"getBar"})
    public String getBar(WaterData waterData, Model model) {


        System.out.println(inDate);
        if(waterData.getInDate()!=null){
            inDate = waterData.getInDate();
        }
       // Date currentDate = new Date();

        if (waterData.getDeviceId().equals("") || null == waterData.getDeviceId()) {
            //dataAmount.setInDate(currentDate);
            Monitordevices monitordevices = new Monitordevices();
            monitordevices.setMenu("water");
            List<Monitordevices> devicesList = powerAnalysisService.findList(monitordevices);

            if (devicesList.size() > 0) {
                waterData.setDeviceId(devicesList.get(1).getId());
            }
        }
        String type = waterData.getType();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (waterData.getInDate() == null) {
            waterData.setInDate(inDate);
            //waterData.setInDate(currentDate);
            /*if (type.equals("3")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(waterData.getInDate());
                cal.add(Calendar.DATE, -1);
                String yesterday = sdf.format(cal.getTime());
                try {
                    Date date = sdf.parse(yesterday);
                    waterData.setInDate(date);
                } catch (ParseException e) {
                    logger.info("日期转化失败----> " + e);
                }


            }*/
        }
        GsonOption option = new GsonOption();
        option = waterAnalysisService.generateBar(waterData);
        GsonOption option_line = new GsonOption();
        option_line = waterAnalysisService.generateLine(waterData);
        if(option==null){
            model.addAttribute("option", "0");
        }else{
            model.addAttribute("option", option);
        }
       if(option_line==null){

           model.addAttribute("option_line","0");
       }else {
           model.addAttribute("option_line",option_line);
       }

        model.addAttribute("tab",waterData.getParams());

        if (type.equals("1")) {
            sdf = new SimpleDateFormat("yyyy");
        } else if (type.equals("2")) {
            sdf = new SimpleDateFormat("yyyy-MM");
        }
        model.addAttribute("inDate", sdf.format(waterData.getInDate()));
        if(waterData.getType().equals("4")){
            return "modules/wateranlysis/waterChartShow";
        }
        if(waterData.getType().equals("1")){
            return "modules/wateranlysis/waterChartShowUse_year";
        }
        if(waterData.getType().equals("2")){
            return "modules/wateranlysis/waterChartShowUse_month";
        }
        if(waterData.getType().equals("3")){
            return "modules/wateranlysis/waterChartShowUse_day";
        }

        return "";
    }

   /* @RequestMapping(value = {"getPowerElecList"})
    public String getPowerElecList(ElecDataAmount dataAmount, Model model) {
        Date currentDate = new Date();
        if (dataAmount.getDeviceId().equals("") || null == dataAmount.getDeviceId()) {
            //dataAmount.setInDate(currentDate);
            List<Monitordevices> devicesList = powerAnalysisService.findList(new Monitordevices());
            if (devicesList.size() > 0) {
                waterD.setDeviceId(devicesList.get(1).getId());
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
        //List<ElecDataAmount> list = new ArrayList<ElecDataAmount>();
        GsonOption option = new GsonOption();
        option = powerAnalysisService.getYearElecData(dataAmount);
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
*/
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
                                              @RequestParam(required = false) Long grade, @RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Monitordevices> list = powerAnalysisService.findList(new Monitordevices());
        for (int i = 0; i < list.size(); i++) {
            Monitordevices e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
           /* map.put("pId", e.getParentId());
            map.put("pIds", e.getParentIds());*/
            map.put("iconCls","devices");
            map.put("icon","../../static/images/icon/device.png");
            map.put("name", e.getName());
            mapList.add(map);
        }
        return mapList;
    }


}