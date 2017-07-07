package com.tp.ems.modules.tools;

import com.github.abel533.echarts.json.GsonOption;

/**
 * Created by tepusoft on 2017/5/17.
 */
public class LimitResult {

    private GsonOption monthOption;
    private GsonOption yearOption;
    private String monthMsg;
    private String yearMsg;
    private boolean monthFlag;
    private boolean yearFlag;
    private double monthSum;
    private double yearSum;
    private String monthLimit;
    private String yearLimit;
    private String monthPercent;
    private String yearPercent;


    public GsonOption getMonthOption() {
        return monthOption;
    }

    public void setMonthOption(GsonOption monthOption) {
        this.monthOption = monthOption;
    }

    public GsonOption getYearOption() {
        return yearOption;
    }

    public void setYearOption(GsonOption yearOption) {
        this.yearOption = yearOption;
    }

    public String getMonthMsg() {
        return monthMsg;
    }

    public void setMonthMsg(String monthMsg) {
        this.monthMsg = monthMsg;
    }

    public String getYearMsg() {
        return yearMsg;
    }

    public void setYearMsg(String yearMsg) {
        this.yearMsg = yearMsg;
    }

    public boolean isMonthFlag() {
        return monthFlag;
    }

    public void setMonthFlag(boolean monthFlag) {
        this.monthFlag = monthFlag;
    }

    public boolean isYearFlag() {
        return yearFlag;
    }

    public void setYearFlag(boolean yearFlag) {
        this.yearFlag = yearFlag;
    }

    public double getMonthSum() {
        return monthSum;
    }

    public void setMonthSum(double monthSum) {
        this.monthSum = monthSum;
    }

    public double getYearSum() {
        return yearSum;
    }

    public void setYearSum(double yearSum) {
        this.yearSum = yearSum;
    }

    public String getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(String monthLimit) {
        this.monthLimit = monthLimit;
    }

    public String getYearLimit() {
        return yearLimit;
    }

    public void setYearLimit(String yearLimit) {
        this.yearLimit = yearLimit;
    }

    public String getMonthPercent() {
        return monthPercent;
    }

    public void setMonthPercent(String monthPercent) {
        this.monthPercent = monthPercent;
    }

    public String getYearPercent() {
        return yearPercent;
    }

    public void setYearPercent(String yearPercent) {
        this.yearPercent = yearPercent;
    }
}
