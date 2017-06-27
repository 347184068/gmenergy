package com.tp.ems.power;

import com.github.abel533.echarts.json.GsonOption;
import com.tp.ems.modules.poweranalysis.dao.ElecDataAmountDao;
import com.tp.ems.modules.poweranalysis.dao.PowerAnalysisDao;
import com.tp.ems.modules.poweranalysis.entity.ElecDataAmount;
import com.tp.ems.modules.poweranalysis.service.PowerAnalysisService;
import com.tp.ems.modules.poweranalysis.utils.PowerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tepusoft on 2016/10/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class TestPower {
    /*@Autowired
    private PowerAnalysisService powerAnalysisService;*/

    @Autowired
    private PowerAnalysisDao powerAnalysisDao;

    @Autowired
    private ElecDataAmountDao elecDataAmountDao;

    @Autowired
    private PowerAnalysisService powerAnalysisService;


   /* @Test
    public void TestInsert(){
        MonitorDevices devices = new MonitorDevices();
        devices.setName("父级监测点");
       //devices.setId("2");
        devices.setParentIds("1");
       *//* devices.setName("父级监测点");
        devices.setParentIds("0");*//*
        powerAnalysisService.save(devices);

    }

    @Test
    public void TestFindList(){
        List<MonitorDevices> list = new ArrayList<MonitorDevices>();
        //list = powerAnalysisService.findAllList(new MonitorDevices());
        list = powerAnalysisService.findList(new MonitorDevices());
        System.out.println(list.size());
    }
*/

    @Test
    public  void testGetYearLoad() throws ParseException {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
       String dstr="2016-4-24";
       Date date = sdf.parse(dstr);
        List<ElecDataAmount> list  = new ArrayList<ElecDataAmount>();
        ElecDataAmount dataAmount = new ElecDataAmount();
        dataAmount.setDeviceId("2");
        dataAmount.setInDate(date);
        dataAmount.setType("1");
        list = elecDataAmountDao.getYearPowerData(dataAmount);
        System.out.println(list.size()+"----------");
        GsonOption option = new GsonOption();
       // option = powerAnalysisService.getYearLoadData(dataAmount);
       // option = powerAnalysisService.getYearElecData(dataAmount);
        //list = elecDataAmountDao.getYearLoadData(dataAmount);
        //System.out.println(JSON.toJSON(option));

    }



    @Test
    public void testDateTrans(){
        Date date = new Date();
        System.out.println(date+"-------------");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        System.out.println("--------year----" +year);
    }

    @Test
    public void testConvertTitle(){
        ElecDataAmount amount = new ElecDataAmount();
        amount.setInDate(new Date());
        amount.setType("1");

        PowerUtils utils = new PowerUtils();
        utils.titleConvert(amount,"电量");
    }


    @Test
    public  void testGetMonthData() throws ParseException {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        String dstr="2016-01-01";
        Date date = sdf.parse(dstr);
        List<ElecDataAmount> list  = new ArrayList<ElecDataAmount>();
        ElecDataAmount dataAmount = new ElecDataAmount();
        dataAmount.setDeviceId("2");
        dataAmount.setInDate(date);
        dataAmount.setType("2");
        SimpleDateFormat sd = new SimpleDateFormat("d");
        String strDate  = sd.format(date);
        System.out.println(strDate);
       /* list = elecDataAmountDao.getMonthPowerData(dataAmount);
        System.out.println(list.size()+"----------");*/
       /* GsonOption option = new GsonOption();
         option = powerAnalysisService.getYearLoadData(dataAmount);*/
        // option = powerAnalysisService.getYearElecData(dataAmount);

        //System.out.println(JSON.toJSON(option));

    }


    @Test
    public void getPowerRealData() throws ParseException {
        ElecDataAmount dataAmount = new ElecDataAmount();
        dataAmount.setDeviceId("2");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        String dstr="2016-11-02";
        dataAmount.setType("3");
        Date date = sdf.parse(dstr);
        dataAmount.setInDate(new Date());
        List<ElecDataAmount> list  = new ArrayList<ElecDataAmount>();

        GsonOption option = new GsonOption();
        option = powerAnalysisService.getYearLoadData(dataAmount);
        System.out.println(option);

    }


    @Test
    public void testCalendar() throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");//小写的mm表示的是分钟
        String dstr="00:00:00";
        Date date = sdf.parse(dstr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        System.out.println(cal.toString());
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        while (hour!=23 || minute!=45){
            cal.add(Calendar.MINUTE,15);
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
            System.out.println(hour+":"+minute);

        }
      /*  int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
*/
    }


    @Test
    public void testHisdata() throws ParseException {
        List<ElecDataAmount> amounts = new ArrayList<ElecDataAmount>();
        ElecDataAmount amount = new ElecDataAmount();
        amount.setType("3");
        amount.setDeviceId("2");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        String dstr="2016-11-03 00:14:00";
        Date date = sdf.parse(dstr);
        amount.setInDate(date);
        //amounts = elecDataAmountDao.getRealTimePowerData(amount);
      //  amounts = powerAnalysisService.getRealData(amount);

        System.out.println(amounts.size()+"-------");
    }


    @Test
    public void testYesterDay() throws ParseException {
        Calendar   cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd ");
        String yesterday =sdf.format(cal.getTime());
        Date date = sdf.parse(yesterday);
        System.out.println(yesterday);
        System.out.println(date);



    }

}
