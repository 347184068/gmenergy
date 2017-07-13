/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywaterday.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energywaterday.utils.HourWaterComparator;
import com.tp.ems.modules.energywaterhour.entity.EnergyWaterHour;
import com.tp.ems.modules.energywatermonth.utils.DayWaterComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energywaterday.entity.EnergyWaterDay;
import com.tp.ems.modules.energywaterday.dao.EnergyWaterDayDao;

/**
 * 水表每天数据Service
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class EnergyWaterDayService extends CrudService<EnergyWaterDayDao, EnergyWaterDay> {

	public EnergyWaterDay get(String id) {
		return super.get(id);
	}
	
	public List<EnergyWaterDay> findList(EnergyWaterDay energyWaterDay) {
		return super.findList(energyWaterDay);
	}
	
	public Page<EnergyWaterDay> findPage(Page<EnergyWaterDay> page, EnergyWaterDay energyWaterDay) {
		return super.findPage(page, energyWaterDay);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyWaterDay energyWaterDay) {
		super.save(energyWaterDay);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyWaterDay energyWaterDay) {
		super.delete(energyWaterDay);
	}

	public List<EnergyWaterDay> findByMonth(String deviceId, Date inDate){
		EnergyWaterDay energyWaterDay = new EnergyWaterDay();
		energyWaterDay.setDeviceId(deviceId);
		energyWaterDay.setDataTime(inDate);
		return findList(energyWaterDay);
	}

	public GsonOption genDayChart(EnergyWaterDay waterDay, List<EnergyWaterDay> waterDays) {
		GsonOption option = new GsonOption();
		String optionLegend = titleConvert(waterDay,"水量","month");
		double sum = DayWaterComparator.getCount(waterDays,"sum");
		option.title().text(optionLegend).subtext("总水量："+sum+"(吨)").left(X.center);
		option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
		option.legend().data("水量").left(X.left);
		option.toolbox().show(true).right(X.right).feature(new MagicType(Magic.bar,Magic.line));
		//纵轴为值轴
		ValueAxis valueAxis = new ValueAxis();
		valueAxis.name("单位（吨）");
		option.yAxis(valueAxis);
		// x轴为类目轴
		CategoryAxis category = new CategoryAxis();
		//柱状数据
		Bar bar = new Bar("水量");
		//显示数字
		bar.itemStyle().normal().label().show(true);
		int len = waterDays.size();
		if(len<=10){
			bar.barWidth(45);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		for(EnergyWaterDay day:waterDays){
			String strDate  = sdf.format(day.getDataTime());
			category.data(strDate);
			bar.data(day.getData());
		}
		option.xAxis(category);
		option.series(bar);
		System.out.println(JSON.toJSON(option));
		return option;
	}

	public String titleConvert(EnergyWaterDay elecDay, String tab, String type){
		String title ="";
		Calendar cal = Calendar.getInstance();
		cal.setTime(elecDay.getDataTime());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		switch (type){
			case "month":
				title="月"+tab+"（"+month+"-01～"+month+"-"+getMonthDay(month,year)+"）";
				break;
		}
		return title;
	}
	public int getMonthDay(int month,int year){
		int days = 0;
		switch (month){
			case 1:case 3:case 5:case 7:case 8:case 10:case 12:
				days=31;
				break;
			case 2:
				if((year%4==0 && year%100!=0) || year%400==0){
					days=29;
				}else {
					days=28;
				}
				break;
			default:
				days=30;
				break;
		}
		return days;
	}
}