package com.tp.ems.modules.tools;

import com.github.abel533.echarts.json.GsonOption;

/**
 * Created by tepusoft on 2017/5/17.
 */
public class JqueryResult {
    private GsonOption option;
    private GsonOption otherOption;
    private String msg;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public GsonOption getOption() {
        return option;
    }

    public void setOption(GsonOption option) {
        this.option = option;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GsonOption getOtherOption() {
        return otherOption;
    }

    public void setOtherOption(GsonOption otherOption) {
        this.otherOption = otherOption;
    }
}
