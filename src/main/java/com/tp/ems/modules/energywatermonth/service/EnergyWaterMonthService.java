/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywatermonth.service;

import java.text.SimpleDateFormat;
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
import com.tp.ems.modules.energyelecmonth.entity.EnergyElecMonth;
import com.tp.ems.modules.energyelecyear.utils.MonthElecComparator;
import com.tp.ems.modules.energywaterday.entity.EnergyWaterDay;
import com.tp.ems.modules.energywateryear.utils.MonthWaterComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energywatermonth.entity.EnergyWaterMonth;
import com.tp.ems.modules.energywatermonth.dao.EnergyWaterMonthDao;

/**
 * 水表每月数据Service
 * @author 徐韵轩
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class EnergyWaterMonthService extends CrudService<EnergyWaterMonthDao, EnergyWaterMonth> {

	public EnergyWaterMonth get(String id) {
		return super.get(id);
	}
	
	public List<EnergyWaterMonth> findList(EnergyWaterMonth energyWaterMonth) {
		return super.findList(energyWaterMonth);
	}
	
	public Page<EnergyWaterMonth> findPage(Page<EnergyWaterMonth> page, EnergyWaterMonth energyWaterMonth) {
		return super.findPage(page, energyWaterMonth);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyWaterMonth energyWaterMonth) {
		super.save(energyWaterMonth);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyWaterMonth energyWaterMonth) {
		super.delete(energyWaterMonth);
	}

	public List<EnergyWaterMonth> findByYear(String deviceId, Date inDate){
		EnergyWaterMonth energyWaterMonth = new EnergyWaterMonth();
		energyWaterMonth.setDeviceId(deviceId);
		energyWaterMonth.setDataTime(inDate);
		return findList(energyWaterMonth);
	}

	public GsonOption genMonthChart(EnergyWaterMonth waterMonth, List<EnergyWaterMonth> waterMonths) {
		GsonOption option = new GsonOption();
		String optionLegend = titleConvert(waterMonth, "水量");
		double sum = MonthWaterComparator.getCount(waterMonths, "sum");
		option.title().text(optionLegend).subtext("总水量：" + sum+"(吨)").left(X.center);
		option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
		option.legend().data("水量").left(X.left);
		option.toolbox().show(true).right(X.right).feature(new MagicType(Magic.bar, Magic.line));
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
		int len = waterMonths.size();
		if (len <= 10) {
			bar.barWidth(45);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		for (EnergyWaterMonth month : waterMonths) {
			String strDate = sdf.format(month.getDataTime());
			category.data(strDate);
			bar.data(month.getData());
		}
		option.xAxis(category);
		option.series(bar);
		System.out.println(JSON.toJSON(option));
		return option;
	}

	public String titleConvert(EnergyWaterMonth waterMonth, String tab) {
		String title = "";
		String year = waterMonth.getDataYear();
		title = "年" + tab + "（" + year + "-01～" + year + "-12）";
		return title;
	}
}