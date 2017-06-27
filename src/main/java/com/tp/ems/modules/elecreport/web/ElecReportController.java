package com.tp.ems.modules.elecreport.web;

import com.google.common.collect.Lists;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.utils.DateUtils;
import com.tp.ems.common.utils.excel.ExportExcel;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.elecreport.entity.ElecPojo;
import com.tp.ems.modules.elecreport.service.ElecReportService;
import com.tp.ems.modules.sys.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tepusoft on 2017/5/8.
 */
@Controller
@RequestMapping(value = "${adminPath}/elec")
public class ElecReportController  extends BaseController {

    @Autowired
    private ElecReportService elecReportService;


    @RequestMapping(value = {"list"})
    public String list(ElecPojo elecPojo, Model model,HttpServletRequest request) {
        List<ElecPojo> elecPojoList = Lists.newArrayList();
        HttpSession session = request.getSession();
        int time=(elecPojo.getTimeInterval()==null?5:elecPojo.getTimeInterval());
        if(elecPojo.getTimeInterval()==null){
            elecPojo.setTimeInterval(time);
        }
        session.setAttribute("timeInterval",time);
        System.out.println(session.getAttribute("timeInterval"));
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        if(elecPojo.getRequestTime()==null){
            elecPojo.setRequestTime(date);
            calendar.setTime(date);
        }else{
            calendar.setTime(elecPojo.getRequestTime());
        }
        calendar.add(Calendar.MINUTE, -5);
        elecPojo.setStartTime(calendar.getTime());

        elecPojo.setStartDate(sdf.format(elecPojo.getStartTime()));
        elecPojo.setRequestDate(sdf.format(elecPojo.getRequestTime()));

        elecPojoList = elecReportService.findElecList(elecPojo);
        model.addAttribute("list",elecPojoList);
        return "modules/elecreport/elecReportList";
    }

    @RequestMapping(value = {"queryList"})
    public String queryList(ElecPojo elecPojo, Model model,HttpServletRequest request) {
        List<ElecPojo> elecPojoList = Lists.newArrayList();
        HttpSession session = request.getSession();
        int time=(elecPojo.getTimeInterval()==null?5:elecPojo.getTimeInterval());
        if(elecPojo.getTimeInterval()==null){
            elecPojo.setTimeInterval(time);
        }
        session.setAttribute("timeInterval",time);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d HH:mm:ss");
        if(elecPojo.getRequestTime()==null){
            elecPojo.setRequestTime(date);
            calendar.setTime(date);
        }else{
            calendar.setTime(elecPojo.getRequestTime());
        }
        calendar.add(Calendar.MINUTE, -5);
        elecPojo.setStartTime(calendar.getTime());

        //协助查询
        elecPojo.setStartDate(sdf.format(elecPojo.getStartTime()));
        elecPojo.setRequestDate(sdf.format(elecPojo.getRequestTime()));

        elecPojoList = elecReportService.findElecList(elecPojo);
        model.addAttribute("list",elecPojoList);
        return "modules/elecreport/elecReportListQuery";
    }

    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(ElecPojo elecPojo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d HH:mm:ss");
        if(elecPojo.getRequestTime()==null){
            elecPojo.setRequestTime(date);
            calendar.setTime(date);
        }else{
            calendar.setTime(elecPojo.getRequestTime());
        }
        calendar.add(Calendar.MINUTE, -5);
        elecPojo.setStartTime(calendar.getTime());

        //协助查询
        elecPojo.setStartDate(sdf.format(elecPojo.getStartTime()));
        elecPojo.setRequestDate(sdf.format(elecPojo.getRequestTime()));


        try {
            String fileName = "电表数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<ElecPojo> list = elecReportService.findElecList(elecPojo);
            new ExportExcel("电表数据"+sdf.format(new Date()), ElecPojo.class).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }
}
