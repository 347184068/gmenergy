/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.dayamount.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tp.ems.common.config.Global;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.utils.DateUtils;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.common.utils.excel.ExportExcel;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.dayamount.entity.MonthAmount;
import com.tp.ems.modules.dayamount.service.MonthAmountService;
import com.tp.ems.modules.dayamount.utl.MonthAmountComparator;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.poweranalysis.service.PowerAnalysisService;
/**
 * 日报表统计Controller
 * @author smallwei
 * @version 2016-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/monthamount/monthAmount")
public class MonthAmountController extends BaseController {

	@Autowired
	private MonthAmountService monthAmountService;
	@Autowired
	private PowerAnalysisService powerAnalysisService;
	@ModelAttribute
	public MonthAmount get(@RequestParam(required=false) String id) {
		MonthAmount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = monthAmountService.get(id);
		}
		if (entity == null){
			entity = new MonthAmount();
		}
		return entity;
	}
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(MonthAmount monthAmount,HttpServletRequest request, HttpServletResponse response, Model model) {
		Monitordevices monitordevices = new Monitordevices();
		monitordevices.setMenu("elec");
		List<Monitordevices> devicesList = powerAnalysisService.findList(monitordevices);
		model.addAttribute("deviceList",devicesList);

		if(StringUtils.isBlank(monthAmount.getDeviceId())){
			return "modules/monthamount/monthAmountList";
		}

		Page<MonthAmount> page = monthAmountService.findPage(new Page<MonthAmount>(request, response), monthAmount); 
		model.addAttribute("page", page);
		List<MonthAmount> monthAmountList=page.getList();
		if(monthAmountList.size()>0){
			model.addAttribute("maxMonthAmount",Collections.max(monthAmountList,new MonthAmountComparator()));
			model.addAttribute("minMonthAmount", Collections.min(monthAmountList,new MonthAmountComparator()));
			model.addAttribute("allMonthAmount",MonthAmountComparator.allCount(monthAmountList));
		}
		model.addAttribute("page", page);
		return "modules/monthamount/monthAmountList";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(MonthAmount monthAmount, Model model) {
		model.addAttribute("monthAmount", monthAmount);
		return "modules/monthAmount/monthAmountForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(MonthAmount monthAmount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, monthAmount)){
			return form(monthAmount, model);
		}
		monthAmountService.save(monthAmount);
		addMessage(redirectAttributes, "保存月报表统计成功");
		return "redirect:"+Global.getAdminPath()+"/monthAmount/monthAmount/?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(MonthAmount monthAmount, RedirectAttributes redirectAttributes) {
		monthAmountService.delete(monthAmount);
		addMessage(redirectAttributes, "删除月报表统计成功");
		return "redirect:"+Global.getAdminPath()+"/monthAmount/monthAmount/?repage";
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
    public String exportFile(MonthAmount monthAmount,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "日用报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MonthAmount> page =monthAmountService.findPage(new Page<MonthAmount>(request, response), monthAmount); 
            List<MonthAmount> monthAmountList=page.getList();
    		if(monthAmountList.size()>0){
    			MonthAmount maxMonthAmount=Collections.max(monthAmountList,new MonthAmountComparator());
    			MonthAmount minMonthAmount=Collections.min(monthAmountList,new MonthAmountComparator());
    			double allMonthAmount=MonthAmountComparator.allCount(monthAmountList);
        		new ExportExcel("月用报表数据", MonthAmount.class).setDataList(page.getList()).write(response, fileName).dispose();
    		}else{
        		new ExportExcel("月用报表数据", MonthAmount.class).setDataList(page.getList()).write(response, fileName).dispose();
    		}
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出日用报表失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/monthamount/monthAmount/list?repage";
    }

}