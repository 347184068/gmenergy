/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.dayamount.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tp.ems.common.config.Global;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.utils.DateUtils;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.common.utils.excel.ExportExcel;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.dayamount.entity.DayAmount;
import com.tp.ems.modules.dayamount.service.DayAmountService;
import com.tp.ems.modules.dayamount.utl.DayAmountComparator;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.poweranalysis.service.PowerAnalysisService;
import net.sf.json.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 日报表统计Controller
 * @author smallwei
 * @version 2016-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/dayamount/dayAmount")
public class DayAmountController extends BaseController {

	@Autowired
	private DayAmountService dayAmountService;
	@Autowired
	private PowerAnalysisService powerAnalysisService;
	@ModelAttribute
	public DayAmount get(@RequestParam(required=false) String id) {
		DayAmount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dayAmountService.get(id);
		}
		if (entity == null){
			entity = new DayAmount();
		}
		return entity;
	}
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(DayAmount dayAmount,HttpServletRequest request, HttpServletResponse response, Model model) {
		Monitordevices monitordevices = new Monitordevices();
		monitordevices.setMenu("elec");
		List<Monitordevices> devicesList = powerAnalysisService.findList(monitordevices);
		model.addAttribute("deviceList",devicesList);
		if(StringUtils.isBlank(dayAmount.getDeviceId())){
			return "modules/dayamount/dayAmountList";
		}
		Page<DayAmount> page = dayAmountService.findPage(new Page<DayAmount>(request, response), dayAmount); 
		model.addAttribute("page", page);
		List<DayAmount> dayAmountList=page.getList();
		if(dayAmountList.size()>0){
			model.addAttribute("maxDayAmount",Collections.max(dayAmountList,new DayAmountComparator()));
			model.addAttribute("minDayAmount", Collections.min(dayAmountList,new DayAmountComparator()));
			model.addAttribute("allDayAmount",DayAmountComparator.allCount(dayAmountList));
		}
		model.addAttribute("page", page);
		return "modules/dayamount/dayAmountList";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(DayAmount dayAmount, Model model) {
		model.addAttribute("dayAmount", dayAmount);
		return "modules/dayamount/dayAmountForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(DayAmount dayAmount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dayAmount)){
			return form(dayAmount, model);
		}
		dayAmountService.save(dayAmount);
		addMessage(redirectAttributes, "保存日报表统计成功");
		return "redirect:"+Global.getAdminPath()+"/dayamount/dayAmount/?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(DayAmount dayAmount, RedirectAttributes redirectAttributes) {
		dayAmountService.delete(dayAmount);
		addMessage(redirectAttributes, "删除日报表统计成功");
		return "redirect:"+Global.getAdminPath()+"/dayamount/dayAmount/?repage";
	}
	/**
	 * 树形节点图生成
	 * @param extId
	 * @param response
	 * @return mapList
	 * @author smallwei
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Monitordevices monitordevices = new Monitordevices();
		monitordevices.setMenu("elec");
		List<Monitordevices> list = powerAnalysisService.findList(monitordevices);
		for (int i=0; i<list.size(); i++){
			Monitordevices e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	/**
	 * 导出用户数据
	 * @param dayAmount
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @author smallwei
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DayAmount dayAmount,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "日用报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DayAmount> page =dayAmountService.findPage(new Page<DayAmount>(request, response), dayAmount); 
            List<DayAmount> dayAmountList=page.getList();
    		if(dayAmountList.size()>0){
    			DayAmount maxDayAmount=Collections.max(dayAmountList,new DayAmountComparator());
    			DayAmount minDayAmount=Collections.min(dayAmountList,new DayAmountComparator());
    			double allDayAmount=DayAmountComparator.allCount(dayAmountList);
        		new ExportExcel("日用报表数据", DayAmount.class).setDataList(page.getList()).write(response, fileName).dispose();
    		}else{
        		new ExportExcel("日用报表数据", DayAmount.class).setDataList(page.getList()).write(response, fileName).dispose();
    		}
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出日用报表失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/dayamount/dayAmount/list?repage";
    }
	/**
	 * 分段日报表
	 * @return list
	 * @author smallwei
	 *
	 * */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "linelist")
	public String linelist(DayAmount dayAmount,HttpServletRequest request, HttpServletResponse response, Model model) {
		if(dayAmount.getIndate()!=null){
			Date date=dayAmount.getIndate();
			int day=Integer.parseInt(new SimpleDateFormat ("dd").format(date));
			int month=Integer.parseInt(new SimpleDateFormat ("MM").format(date));
			int year=Integer.parseInt(new SimpleDateFormat ("yyyy").format(date));
			List<String> daylist=new ArrayList<String>();
			List<Map<String,Object>> data=dayAmountService.findDayAmountByDate(year+"-"+month+"-"+day,"2016-01-30");
			daylist.add("'监测点'");
			daylist.add("'时段'");
			daylist.add("'用电量'");
			for(int i=day;i<31;i++){
				daylist.add("'"+year+"-"+month+"-"+i+"'");
			};
			JSONArray jsonArr = JSONArray.fromObject(data);
			model.addAttribute("datalist", jsonArr.toString());
			model.addAttribute("daylist", daylist);
		}
		return "modules/lineamount/lineAmountList";
	}

}