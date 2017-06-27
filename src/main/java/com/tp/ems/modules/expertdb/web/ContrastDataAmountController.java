/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.expertdb.web;

import com.github.abel533.echarts.json.GsonOption;
import com.google.common.collect.Lists;
import com.tp.ems.common.config.Global;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.utils.StringUtils;
import com.tp.ems.common.web.BaseController;
import com.tp.ems.modules.devices.entity.Monitordevices;
import com.tp.ems.modules.expertdb.entity.ContrastDataAmount;
import com.tp.ems.modules.expertdb.service.ContrastDataAmountService;
import com.tp.ems.modules.poweranalysis.service.PowerAnalysisService;
import com.tp.ems.modules.tools.JqueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 专家数据库(对比分析)Controller
 * @author 张丽
 * @version 2016-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/expertdb/contrastDataAmount")
public class ContrastDataAmountController extends BaseController {

	@Autowired
	private ContrastDataAmountService contrastDataAmountService;

	@Autowired
	private PowerAnalysisService analysisService;
	
	@ModelAttribute
	public ContrastDataAmount get(@RequestParam(required=false) String id) {
		ContrastDataAmount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contrastDataAmountService.get(id);
		}
		if (entity == null){
			entity = new ContrastDataAmount();
		}
		return entity;
	}
	

	@RequestMapping(value = {"list", ""})
	public String list(ContrastDataAmount contrastDataAmount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContrastDataAmount> page = contrastDataAmountService.findPage(new Page<ContrastDataAmount>(request, response), contrastDataAmount); 
		model.addAttribute("page", page);
		return "modules/expertdb/contrastDataAmountList";
	}


	/*@RequestMapping(value = "form")
	public String form(ContrastDataAmount contrastDataAmount, Model model) {
		model.addAttribute("contrastDataAmount", contrastDataAmount);
		return "modules/expertdb/contrastDataAmountForm";
	}*/


	/*@RequestMapping(value = "save")
	public String save(ContrastDataAmount contrastDataAmount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contrastDataAmount)){
			return form(contrastDataAmount, model);
		}
		contrastDataAmountService.save(contrastDataAmount);
		addMessage(redirectAttributes, "保存对比分析完成成功");
		return "redirect:"+Global.getAdminPath()+"/expertdb/contrastDataAmount/?repage";
	}*/
	

	@RequestMapping(value = "delete")
	public String delete(ContrastDataAmount contrastDataAmount, RedirectAttributes redirectAttributes) {
		contrastDataAmountService.delete(contrastDataAmount);
		addMessage(redirectAttributes, "删除对比分析完成成功");
		return "redirect:"+Global.getAdminPath()+"/expertdb/contrastDataAmount/?repage";
	}


	/**
	 * 不同监测点同一时间段
	 * @param contrastDataAmount
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"diffDevice", ""})
	public String diffDevice(ContrastDataAmount contrastDataAmount, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*Page<ContrastDataAmount> page = contrastDataAmountService.findPage(new Page<ContrastDataAmount>(request, response), contrastDataAmount);
		model.addAttribute("page", page);*/
		model.addAttribute("type", 1);//1 负荷 2电量
		return "modules/expertdb/contrastDataAmountList";
	}

	/**
	 * 不同监测点同一时间段对比分析
	 * @param dataAmount
	 * @param type
	 * @return
	 */

	@RequestMapping(value = "getCompareChart")
	public String getCompareChart(ContrastDataAmount dataAmount,String type,HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ContrastDataAmount> amountList = Lists.newArrayList();
		ContrastDataAmount compareDataAmount = new ContrastDataAmount();
		compareDataAmount.setStartTime(dataAmount.getStartTime());
		compareDataAmount.setEndTime(dataAmount.getEndTime());
		String deviceId = dataAmount.getDeviceId();
		String[] ids = deviceId.split(",");
		dataAmount.setDeviceId(ids[0]);
		compareDataAmount.setDeviceId(ids[1]);
		amountList.add(dataAmount);
		amountList.add(compareDataAmount);
		GsonOption option = new GsonOption();
		option = contrastDataAmountService.diffDeviceAnalysis(amountList,type);


		String deviceName = analysisService.get(ids[0]).getName();
		String compareDeviceName = analysisService.get(ids[1]).getName();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		model.addAttribute("deviceId",dataAmount.getDeviceId());
		model.addAttribute("compareDeviceId",compareDataAmount.getDeviceId());
		model.addAttribute("deviceName",deviceName);
		model.addAttribute("compareDeviceName",compareDeviceName);
		model.addAttribute("startDate",sdf.format(dataAmount.getStartTime()));
		model.addAttribute("endDate",sdf.format(dataAmount.getEndTime()));
		model.addAttribute("type",type);
		model.addAttribute("option",option);
		return "modules/expertdb/contrastDataAmountList";
	}


	/**
	 *
	 * @param contrastDataAmount
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"sameDevice"})
	public String sameDevice(ContrastDataAmount contrastDataAmount, HttpServletRequest request, HttpServletResponse response, Model model) {


		Monitordevices monitordevices = new Monitordevices();
		monitordevices.setMenu("elec");
		List<Monitordevices> devicesList = analysisService.findList(monitordevices);
		model.addAttribute("deviceList",devicesList);

		return "modules/expertdb/getSameDeviceList";
	}

	/**
	 * 同比、环比
	 * @param dataAmount
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "getSameDeviceChart")
	@ResponseBody
	public JqueryResult getSameDeviceChart(ContrastDataAmount dataAmount,HttpServletRequest request, HttpServletResponse response, Model model) {

		JqueryResult result = new JqueryResult();
		String deviceId = dataAmount.getDeviceId();
		GsonOption option = new GsonOption();
		try {
			option = contrastDataAmountService.sameDeviceDiffTime(dataAmount,"1",dataAmount.getCompareType());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(option ==null ){
			result.setFlag(false);
			result.setMsg("当前没有数据");
		}else{
			result.setFlag(true);
			result.setOption(option);
		}
		 return result;



		/*model.addAttribute("deviceId",dataAmount.getDeviceId());
		model.addAttribute("deviceName",deviceName);*/

		/*model.addAttribute("type",type);
		model.addAttribute("option",option);
		model.addAttribute("compareType", compareType);*/
	}

	/**
	 * 同一监测点不同时间区间对比分析
	 * @param contrastDataAmount
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"oneDeviceTwoInterval", ""})
	public String oneDeviceTwoInterval(ContrastDataAmount contrastDataAmount ,HttpServletRequest request, HttpServletResponse response, Model model) {
		//1 负荷 2电量
		model.addAttribute("type", 1);
		return "modules/expertdb/oneDeviceTwoIntervalList";
	}

	@RequestMapping(value = "getOneDeviceEwoIntervalChart")
	public String getOneDeviceEwoIntervalChart(ContrastDataAmount dataAmount,String type,String start,String end,HttpServletRequest request, HttpServletResponse response, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("startDate",sdf.format(dataAmount.getStartTime()));
		model.addAttribute("endDate",sdf.format(dataAmount.getEndTime()));
		model.addAttribute("start",start);
		model.addAttribute("end",end);

		String deviceId = dataAmount.getDeviceId();
		GsonOption option = new GsonOption();

		option = contrastDataAmountService.sameDeviceTwoTimeInterval(dataAmount,type);
		model.addAttribute("option",option);


		String deviceName = analysisService.get(deviceId).getName();

		model.addAttribute("deviceId",dataAmount.getDeviceId());
		model.addAttribute("deviceName",deviceName);

		//另一个时间区间
		try {
			dataAmount.setStartTime(sdf.parse(start));
			dataAmount.setEndTime(sdf.parse(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		option = contrastDataAmountService.sameDeviceTwoTimeInterval(dataAmount,type);
		model.addAttribute("type",type);
		model.addAttribute("option_compare",option);
		return "modules/expertdb/oneDeviceTwoIntervalList";
	}


}