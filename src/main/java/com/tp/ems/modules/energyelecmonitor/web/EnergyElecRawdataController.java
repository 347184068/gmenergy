/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecmonitor.web;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.common.config.Global;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.utils.DateUtils;
import com.tp.ems.common.utils.excel.ExportExcel;
import com.tp.ems.modules.elecreport.entity.ElecPojo;
import com.tp.ems.modules.energydevices.entity.EnergyDevices;
import com.tp.ems.modules.energydevices.service.EnergyDevicesService;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;
import com.tp.ems.modules.energyelecmonitor.entity.ExportElec;
import com.tp.ems.modules.energyelecmonitor.utils.CovertPojo;
import com.tp.ems.modules.tools.JqueryResult;
import org.apache.poi.ss.formula.functions.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tp.ems.common.web.BaseController;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.modules.energyelecmonitor.entity.EnergyElecRawdata;
import com.tp.ems.modules.energyelecmonitor.service.EnergyElecRawdataService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.tp.ems.modules.energyelecmonitor.utils.CovertPojo.covertList;

/**
 * 电数据在线监控Controller
 *
 * @author 徐韵轩
 * @version 2017-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/energyelecmonitor/energyElecRawdata")
public class EnergyElecRawdataController extends BaseController {

    @Autowired
    private EnergyElecRawdataService rawdataService;

    @Autowired
    private EnergyDevicesService devicesService;

    @ModelAttribute
    public EnergyElecRawdata get(@RequestParam(required = false) String id) {
        EnergyElecRawdata entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = rawdataService.get(id);
        }
        if (entity == null) {
            entity = new EnergyElecRawdata();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        List<EnergyDevices> devicesList = devicesService.findAllElecDevices();
        model.addAttribute("deviceList", devicesList);
        return "modules/energyelecmonitor/energyElecRawdataList";
    }


    /**
     * 获取实时数据
     */
    @RequestMapping(value = "getRealData")
    public String showRealData(EnergyElecRawdata elecRawdata, Model model) {
        List<EnergyDevices> devicesList = devicesService.findAllElecDevices();
        List<EnergyElecRawdata> rawdatas = new ArrayList<EnergyElecRawdata>();
        for (EnergyDevices devices : devicesList) {
            EnergyElecRawdata rawdata = new EnergyElecRawdata();
            rawdata.setCount(1);
            rawdata.setDeviceId(devices.getDeviceId());
            List<EnergyElecRawdata> singleData = rawdataService.findNewDataByCount(rawdata);
            if(singleData.size()!=0){
                rawdatas.add(singleData.get(0));
            }
        }
        model.addAttribute("rawdatas", rawdatas);
        return "modules/energyelecmonitor/energyElecRawdataList";
    }


    @RequestMapping(value = "redirectReport")
    public String redirectReport(){
        return "modules/energyelecmonitor/energyElecRawdataReport";
    }

    /**
     * 监测历史展示
     */
    @RequestMapping(value = "rawDataReport")
    public String showRawDataReport(EnergyElecRawdata elecRawdata, HttpServletRequest request, HttpServletResponse response, Model model) {
        Calendar calendar = Calendar.getInstance();
        if (elecRawdata.getEndTime() == null) {
            calendar.setTime(new Date());
            elecRawdata.setEndTime(calendar.getTime());
            calendar.add(Calendar.MINUTE, -15);
            elecRawdata.setStartTime(calendar.getTime());
        }else {
            calendar.setTime(elecRawdata.getEndTime());
            calendar.add(Calendar.MINUTE, -15);
            elecRawdata.setStartTime(calendar.getTime());
            elecRawdata.setEndTime(elecRawdata.getEndTime());
        }
        Page<EnergyElecRawdata> page = rawdataService.findPage(new Page<EnergyElecRawdata>(request, response), elecRawdata);
        model.addAttribute("page", page);
        return "modules/energyelecmonitor/energyElecRawdataReport";
    }


    /**
     * 监测历史导出Excel
     */

    @RequestMapping(value = "rawDataExport")
    public String exportExcelData(EnergyElecRawdata elecRawdata, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "电表数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Calendar calendar = Calendar.getInstance();
            if (elecRawdata.getEndTime() == null) {
                calendar.setTime(new Date());
                elecRawdata.setEndTime(calendar.getTime());
                calendar.add(Calendar.MINUTE, -15);
                elecRawdata.setStartTime(calendar.getTime());
            }else {
                calendar.setTime(elecRawdata.getEndTime());
                calendar.add(Calendar.MINUTE, -15);
                elecRawdata.setStartTime(calendar.getTime());
                elecRawdata.setEndTime(elecRawdata.getEndTime());
            }
            List<EnergyElecRawdata> list = rawdataService.findList(elecRawdata);
            List<ExportElec> exportList = CovertPojo.covertList(list);
            String excelTitle = "电表数据(" + DateUtils.formatDateTime(elecRawdata.getEndTime())+")";
            new ExportExcel(excelTitle, ExportElec.class).setDataList(exportList).write(response, fileName).dispose();
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/energyelecmonitor/energyElecRawdata/redirectReport/?repage";
    }

}