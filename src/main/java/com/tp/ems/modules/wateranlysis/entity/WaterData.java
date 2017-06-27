/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tp.ems.modules.wateranlysis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * 用水分析Entity
 * @author 张丽
 * @version 2017-01-06
 */
public class WaterData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String deviceId;		// 监测点id
	private Double jljll;		// 净累计流量
	private Double ssll;		// 瞬时流量
	private Double pressure;		// 压力
	private String presslowWarn;		// 压力下限报警
	private String pressupWarn;		// 上限报警
	private String ccxkmbj;		// 测控箱开门报警
	private String status;		// 状态
	private Date inDate;		// 采集时间 精确到小时
	private Date createTime;		// 创建时间

	private String type;  //类型 年4、月3 日2  实时1  //4 实时 3 日 2 月 1年
	private String params; //参数
	private double useAmount; //消耗量
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Double getJljll() {
		return jljll;
	}

	public void setJljll(Double jljll) {
		this.jljll = jljll;
	}
	
	public Double getSsll() {
		return ssll;
	}

	public void setSsll(Double ssll) {
		this.ssll = ssll;
	}
	
	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}
	
	@Length(min=0, max=64, message="压力下限报警长度必须介于 0 和 64 之间")
	public String getPresslowWarn() {
		return presslowWarn;
	}

	public void setPresslowWarn(String presslowWarn) {
		this.presslowWarn = presslowWarn;
	}
	
	@Length(min=0, max=64, message="上限报警长度必须介于 0 和 64 之间")
	public String getPressupWarn() {
		return pressupWarn;
	}

	public void setPressupWarn(String pressupWarn) {
		this.pressupWarn = pressupWarn;
	}
	
	@Length(min=0, max=64, message="测控箱开门报警长度必须介于 0 和 64 之间")
	public String getCcxkmbj() {
		return ccxkmbj;
	}

	public void setCcxkmbj(String ccxkmbj) {
		this.ccxkmbj = ccxkmbj;
	}
	
	@Length(min=0, max=64, message="状态长度必须介于 0 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public double getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(double useAmount) {
		this.useAmount = useAmount;
	}

	@Override
	public String toString() {
		return "WaterData{" +
				"id=" + id +
				", deviceId='" + deviceId + '\'' +
				", jljll=" + jljll +
				", ssll=" + ssll +
				", pressure=" + pressure +
				", presslowWarn='" + presslowWarn + '\'' +
				", pressupWarn='" + pressupWarn + '\'' +
				", ccxkmbj='" + ccxkmbj + '\'' +
				", status='" + status + '\'' +
				", inDate=" + inDate +
				", createTime=" + createTime +
				", type='" + type + '\'' +
				", params='" + params + '\'' +
				", useAmount=" + useAmount +
				'}';
	}
}