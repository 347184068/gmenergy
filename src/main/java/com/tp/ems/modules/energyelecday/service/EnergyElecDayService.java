/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecday.service;

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
import com.tp.ems.modules.energyelecday.utils.HourElecComparator;
import com.tp.ems.modules.energyelechour.entity.EnergyElecHour;
import com.tp.ems.modules.energyelecmonth.utils.DayElecComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energyelecday.entity.EnergyElecDay;
import com.tp.ems.modules.energyelecday.dao.EnergyElecDayDao;

/**
 * 电表每天数据Service
 * @author 徐韵轩
 * @version 2017-06-25
 */
@Service
@Transactional(readOnly = true)
public class EnergyElecDayService extends CrudService<EnergyElecDayDao, EnergyElecDay> {

	public EnergyElecDay get(String id) {
		return super.get(id);
	}
	
	public List<EnergyElecDay> findList(EnergyElecDay energyElecDay) {
		return super.findList(energyElecDay);
	}
	
	public Page<EnergyElecDay> findPage(Page<EnergyElecDay> page, EnergyElecDay energyElecDay) {
		return super.findPage(page, energyElecDay);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyElecDay energyElecDay) {
		super.save(energyElecDay);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyElecDay energyElecDay) {
		super.delete(energyElecDay);
	}

	public List<EnergyElecDay> findByMonth(String deviceId, Date inDate){
		EnergyElecDay energyElecDay = new EnergyElecDay();
		energyElecDay.setDeviceId(deviceId);
		energyElecDay.setDataTime(inDate);
		return findList(energyElecDay);
	}

	public GsonOption genDayChart(EnergyElecDay energyElecDay, List<EnergyElecDay> elecDays) {
		GsonOption option = new GsonOption();
		String optionLegend = titleConvert(energyElecDay,"电量","month");
		double sum = DayElecComparator.getCount(elecDays,"sum");
		option.title().text(optionLegend).subtext("总电量："+sum+"(kWh)").left(X.center);
		option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
		option.legend().data("电量").left(X.left);
		option.toolbox().show(true).right(X.right).feature(new MagicType(Magic.bar,Magic.line));
		//纵轴为值轴
		ValueAxis valueAxis = new ValueAxis();
		valueAxis.name("单位（kWh）");
		option.yAxis(valueAxis);
		// x轴为类目轴
		CategoryAxis category = new CategoryAxis();
		//柱状数据
		Bar bar = new Bar("电量");
		//显示数字
		bar.itemStyle().normal().label().show(true);
		int len = elecDays.size();
		if(len<=10){
			bar.barWidth(45);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		for(EnergyElecDay day:elecDays){
			String strDate  = sdf.format(day.getDataTime());
			category.data(strDate);
			bar.data(day.getRealData());
		}
		option.xAxis(category);
		option.series(bar);
		System.out.println(JSON.toJSON(option));
		return option;
	}

	public String titleConvert(EnergyElecDay elecDay, String tab, String type){
		String title ="";
		Calendar cal = Calendar.getInstance();
		cal.setTime(elecDay.getDataTime());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		switch (type){
			case "year":
				title="年"+tab+"（"+year+"-01～"+year+"-12）";
				break;
			case "month":
				title="月"+tab+"（"+month+"-01～"+month+"-"+getMonthDay(month,year)+"）";
				break;
			case "day":
				title="日"+tab+"（"+month+"-"+day+" 00:00～"+month+"-"+day+" 23:59）";
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