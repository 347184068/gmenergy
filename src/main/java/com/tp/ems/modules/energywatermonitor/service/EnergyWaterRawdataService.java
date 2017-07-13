/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energywatermonitor.service;

import java.text.SimpleDateFormat;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.tp.ems.modules.energyelecmonitor.entity.EnergyElecRawdata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energywatermonitor.entity.EnergyWaterRawdata;
import com.tp.ems.modules.energywatermonitor.dao.EnergyWaterRawdataDao;

/**
 * 水表在线监控Service
 * @author 徐韵轩
 * @version 2017-07-13
 */
@Service
@Transactional(readOnly = true)
public class EnergyWaterRawdataService extends CrudService<EnergyWaterRawdataDao, EnergyWaterRawdata> {

	public EnergyWaterRawdata get(String id) {
		return super.get(id);
	}
	
	public List<EnergyWaterRawdata> findList(EnergyWaterRawdata energyWaterRawdata) {
		return super.findList(energyWaterRawdata);
	}
	
	public Page<EnergyWaterRawdata> findPage(Page<EnergyWaterRawdata> page, EnergyWaterRawdata energyWaterRawdata) {
		return super.findPage(page, energyWaterRawdata);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyWaterRawdata energyWaterRawdata) {
		super.save(energyWaterRawdata);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyWaterRawdata energyWaterRawdata) {
		super.delete(energyWaterRawdata);
	}

	public List<EnergyWaterRawdata> findNewDataByCount(EnergyWaterRawdata waterRawdata) {
		return dao.findNewDataByCount(waterRawdata);
	}

	public GsonOption genBrokenLine(List<EnergyWaterRawdata> rawdatas) {
		GsonOption option = new GsonOption();
		String optionLegend = "实时水量";
		option.title().text(optionLegend).left(X.center);
		option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
		option.legend().data("水量").left(X.right);
		//纵轴为值轴
		ValueAxis valueAxis = new ValueAxis();
		valueAxis.name("单位（吨）");
		option.yAxis(valueAxis);
		// x轴为类目轴
		CategoryAxis category = new CategoryAxis();
		category.setBoundaryGap(false);
		//柱状数据
		Line line = new Line("水量");
		line.setSmooth(true);
		line.setAnimation(false);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		for(int i = rawdatas.size()-1 ; i >=0 ; i--){
			EnergyWaterRawdata amount = rawdatas.get(i);
			String strDate  = sdf.format(amount.getDataTime());
			category.data(strDate);
			line.data(amount.getRawData());
		}
		option.xAxis(category);
		option.series(line);
		System.out.println(JSON.toJSON(option));
		return option;
	}
}