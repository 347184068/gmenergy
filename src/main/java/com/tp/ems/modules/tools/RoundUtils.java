package com.tp.ems.modules.tools;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author XuYunXuan
 * @ClassName: RoundUtils
 * @Description:
 * @date 2017-07-20 14:45
 */
public class RoundUtils {
    public static String round(int round,String data){
        String value = null;
        if(data!=null){
            BigDecimal bigDecimal = new BigDecimal(data);
            value = bigDecimal.setScale(round,   BigDecimal.ROUND_HALF_UP).doubleValue()+"";
        }
        return value;
    }
}
