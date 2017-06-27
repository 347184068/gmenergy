/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.waterexpertdb.service;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.common.persistence.Page;
import com.tp.ems.common.service.CrudService;
import com.tp.ems.modules.waterexpertdb.dao.ContrastDataDao;
import com.tp.ems.modules.waterexpertdb.entity.ContrastData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 专家数据库(对比分析)Service
 * @author 张丽
 * @version 2016-11-07
 */
@Service
@Transactional(readOnly = true)
public class ContrastDataService extends CrudService<ContrastDataDao, ContrastData> {

	@Autowired
	private ContrastDataDao contrastDataAmountDao;

	@Autowired
	private WaterCompareUtils utils;

	public ContrastData get(String id) {
		return super.get(id);
	}
	
	public List<ContrastData> findList(ContrastData contrastDataAmount) {
		return super.findList(contrastDataAmount);
	}
	
	public Page<ContrastData> findPage(Page<ContrastData> page, ContrastData contrastDataAmount) {
		return super.findPage(page, contrastDataAmount);
	}
	
	@Transactional(readOnly = false)
	public void save(ContrastData contrastDataAmount) {
		super.save(contrastDataAmount);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContrastData contrastDataAmount) {
		super.delete(contrastDataAmount);
	}


	//不同监测点同一时间段 对比分析

	/**
	 * type:1 负荷 折线图   2 电量 柱状图
	 * @param dataAmountList
	 * @param type
	 * @return
	 */
	public GsonOption diffDeviceAnalysis(List<ContrastData> dataAmountList,String type){
		GsonOption option  = new GsonOption();
		Map<String,List<ContrastData>> map = new HashMap();
		for(int i =0;i<dataAmountList.size();i++){
			List<ContrastData> contrastDataAmounts = contrastDataAmountDao.getIntervalData(dataAmountList.get(i));
			map.put(String.valueOf(i),contrastDataAmounts);
		}
		int flag = 0;
		for(int j=0;j<map.size();j++){
			if(map.get(String.valueOf(j)).size()==0){
				flag++;
			}
		}
		if(flag>1){
			return null;
		}
		option = utils.barChart(map,type);

		return option;
	}


	//同一监测点同比 如：13年3月和14年4月

	//  环比 ：相邻时间段 如13年3月和13年2月

	/**
	 * type:1 负荷 折线图   2 电量 柱状图
	 * compareType 1:同比 2 : 环比
	 * @param dataAmount
	 * @return
	 * @throws java.text.ParseException
	 */

	public GsonOption sameDeviceDiffTime(ContrastData dataAmount) throws ParseException {
		GsonOption option  = new GsonOption();
		Map map = new HashMap();
		String compareType = dataAmount.getCompareType();
		//选中时间下
		List<ContrastData> contrastDataAmounts = contrastDataAmountDao.getIntervalData(dataAmount);
		map.put("0",contrastDataAmounts);

		Date startTime = dataAmount.getStartTime();
		Date endTime = dataAmount.getEndTime();

		// 同比 、环比

		Date startTime_basis = convertDateBasis(startTime,compareType);
		Date endTime_basis = convertDateBasis(endTime,compareType);

		dataAmount.setStartTime(startTime_basis);
		dataAmount.setEndTime(endTime_basis);

		List<ContrastData> contrastDataAmounts_basis = contrastDataAmountDao.getIntervalData(dataAmount);
		map.put("1",contrastDataAmounts_basis);
		if(contrastDataAmounts.size()<=0 && contrastDataAmounts_basis.size()<=0){
			return null;
		}
		option = utils.barChart(map,dataAmount.getType());

		return option;
	}


	/**
	 * 转化同比/环比时间  type:1 同比 type : 2 环比
	 * @param date
	 * @param compareType
	 * @return
	 */
	public Date convertDateBasis(Date date,String compareType){


		Calendar cal   =   Calendar.getInstance();
		cal.setTime(date);
		if(compareType.equals("1")){
			cal.add(Calendar.YEAR,   -1);
		}else if(compareType.equals("2")){
			cal.add(Calendar.MONTH,-1);
		}
		SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd");
		String date_convert =sdf.format(cal.getTime());
		try {
			Date indate = sdf.parse(date_convert);
			return indate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * type:1 1 JLJLL 净累计流量 2 SSLL 瞬时流量
	 * @param dataAmount
	 * @param type
	 * @return
	 */
	public GsonOption  sameDeviceTwoTimeInterval(ContrastData dataAmount,String type){
		GsonOption option = new GsonOption();
		List<ContrastData> contrastDataAmounts = new ArrayList<>();
		contrastDataAmounts = contrastDataAmountDao.getIntervalData(dataAmount);
		if(contrastDataAmounts.size()<=0){
			return null;
		}
		option = utils.barOneDeviceTwoInterval(contrastDataAmounts, type);
		return option;
	}
}

