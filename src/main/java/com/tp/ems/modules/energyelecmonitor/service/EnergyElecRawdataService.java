/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.energyelecmonitor.service;

import java.text.SimpleDateFormat;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.energyelecmonitor.entity.EnergyElecRawdata;
import com.tp.ems.modules.energyelecmonitor.dao.EnergyElecRawdataDao;

/**
 * 电数据在线监控Service
 * @author 徐韵轩
 * @version 2017-07-07
 */
@Service
@Transactional(readOnly = true)
public class EnergyElecRawdataService extends CrudService<EnergyElecRawdataDao, EnergyElecRawdata> {

	public EnergyElecRawdata get(String id) {
		return super.get(id);
	}
	
	public List<EnergyElecRawdata> findList(EnergyElecRawdata energyElecRawdata) {
		return super.findList(energyElecRawdata);
	}
	
	public Page<EnergyElecRawdata> findPage(Page<EnergyElecRawdata> page, EnergyElecRawdata energyElecRawdata) {
		return super.findPage(page, energyElecRawdata);
	}
	
	@Transactional(readOnly = false)
	public void save(EnergyElecRawdata energyElecRawdata) {
		super.save(energyElecRawdata);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnergyElecRawdata energyElecRawdata) {
		super.delete(energyElecRawdata);
	}



	public List<EnergyElecRawdata> findNewDataByCount(EnergyElecRawdata energyElecRawdata){
		return dao.findNewData(energyElecRawdata);
	}

	/**
	 *  生成折线图
	 */
	public GsonOption genBrokenLine(List<EnergyElecRawdata> rawdatas){
		GsonOption option = new GsonOption();
		String optionLegend = "实时电量";
		option.title().text(optionLegend).left(X.center);
		option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
		option.legend().data("电量").left(X.right);
		//纵轴为值轴
		ValueAxis valueAxis = new ValueAxis();
		valueAxis.name("单位（kWh）");
		option.yAxis(valueAxis);
		// x轴为类目轴
		CategoryAxis category = new CategoryAxis();
		category.setBoundaryGap(false);
		//柱状数据
		Line line = new Line("电量");
		line.setSmooth(true);
		line.setAnimation(false);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		for(int i = rawdatas.size()-1 ; i >=0 ; i--){
			EnergyElecRawdata amount = rawdatas.get(i);
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