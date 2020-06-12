package com.byd.emg.common;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.mapper.TimeJobMapper;
import com.byd.emg.pojo.*;
import com.byd.emg.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TimerJobs {

    @Autowired
    private TimeJobService timeJobService;

    @Autowired
    private SectionAnalysisService sectionAnalysisService;

    @Autowired
    private ReportFormService ReportFormService;

    @Autowired
    private Unit_PriceService unit_priceService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private RealTimeValueService realTimeValueService;

    //每10秒     开机次数的定时任务
    @Async
    @Scheduled(cron="0/10 * * * * ?")
    public void updateOpenTimes() {
        timeJobService.updateOpenTimes();
    }

    //每分钟      空压机设备的定时任务
    @Async
    @Scheduled(cron="0 0/1 * * * ?")
    public void equipmentStates() {
//        System.out.println("每分钟执行一次");
        //设备的定时任务
        timeJobService.equipmentJob();
        List<EquipmentStates> equipmentStatesList = equipmentService.getEquipmentStatesAll();
        List<RealValue> realValueList = realTimeValueService.getRealValueList(equipmentStatesList);
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        for(EquipmentStates equipmentStates:equipmentStatesList ){
            for(RealValue realValue:realValueList){
                if(equipmentStates.getRealTagName().equals(realValue.getRealValue_tagname())){
                    Double IbRealValue= Double.valueOf(realValue.getRealValue());
                    if(IbRealValue<=10){

                    }else if(IbRealValue<30){
                        //空载、运行
                        equipmentStates.setTotalRunningTime((Long.valueOf(equipmentStates.getTotalRunningTime())+1)+"");
                        equipmentStates.setDeadTime((Long.valueOf(equipmentStates.getDeadTime())+1)+"");

                    }else if(IbRealValue>=30){
                        //加载、运行
                        equipmentStates.setTotalRunningTime((Long.valueOf(equipmentStates.getTotalRunningTime())+1)+"");
                        equipmentStates.setLoadTime((Long.valueOf(equipmentStates.getLoadTime())+1)+"");
                    }
                }
            }
        }
        equipmentService.batchUpdataEquipmentStates(equipmentStatesList,time);
    }

    //每分钟计算桑基图数据的方法
    @Async
    @Scheduled(cron="0 0/1 * * * ?")
    public void calculateSankeyData(){
        sectionAnalysisService.calculateSankeyData();
    }

    @Async
    @Scheduled(cron="0 0/5 * * * ?")
    public void TimeJobBy5min() {
        System.out.println("每5分钟执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_power_table";
        String historyTableName="min_power";
        String condition="where realValue_tagname like '%EP%' or realValue_tagname like '%EQ%' or realValue_tagname like '%PF%'or realValue_tagname like '%P%'";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);
        //timeJobMapper.TimeJobBy5min(date,time);
        //计算每5分钟的耗值
        String historyTableNameConsume="min_consume_power";
        String lastTimeTableName="lastTime_power_table";
        timeJobService.insert5minConsumeData(dataSourceTableName,historyTableNameConsume,lastTimeTableName,condition);
        System.out.print("整理五分钟数据成功");
    }

    //周期耗值定时计算
   /* @Async
    @Scheduled(cron="0 59 0/1 * * ?")*/
    public void TimeJobByHour_Consume() {
        DateTime datime= DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("yyyy-MM-dd HH:mm:ss");
        //判断是否有当前小时、天的耗值，有就更新，没有就新增
        String minConsumeTable="min_consume_power";
        String hourConsumeTable="hour_consume_power";
        String dayConsumeTable="day_consume_power";
        timeJobService.insertConsumeData(minConsumeTable,hourConsumeTable,datime.toString("yyyy-MM-dd HH"),datime);
        timeJobService.insertConsumeData(hourConsumeTable,dayConsumeTable,datime.toString("yyyy-MM-dd"),datime);
        //循环水
        minConsumeTable="min_consume_circulatingWater";
        hourConsumeTable="hour_consume_circulatingWater";
        dayConsumeTable="day_consume_circulatingWater";
        timeJobService.insertConsumeData(minConsumeTable,hourConsumeTable,datime.toString("yyyy-MM-dd HH"),datime);
        timeJobService.insertConsumeData(hourConsumeTable,dayConsumeTable,datime.toString("yyyy-MM-dd"),datime);
        //压缩空气
        minConsumeTable="min_consume_fermentationAir";
        hourConsumeTable="hour_consume_fermentationAir";
        dayConsumeTable="day_consume_fermentationAir";
        timeJobService.insertConsumeData(minConsumeTable,hourConsumeTable,datime.toString("yyyy-MM-dd HH"),datime);
        timeJobService.insertConsumeData(hourConsumeTable,dayConsumeTable,datime.toString("yyyy-MM-dd"),datime);
        //冷冻水
        minConsumeTable="min_consume_freezeWater";
        hourConsumeTable="hour_consume_freezeWater";
        dayConsumeTable="day_consume_freezeWater";
        timeJobService.insertConsumeData(minConsumeTable,hourConsumeTable,datime.toString("yyyy-MM-dd HH"),datime);
        timeJobService.insertConsumeData(hourConsumeTable,dayConsumeTable,datime.toString("yyyy-MM-dd"),datime);
        //蒸汽
        minConsumeTable="min_consume_steamMeter";
        hourConsumeTable="hour_consume_steamMeter";
        dayConsumeTable="day_consume_steamMeter";
        timeJobService.insertConsumeData(minConsumeTable,hourConsumeTable,datime.toString("yyyy-MM-dd HH"),datime);
        timeJobService.insertConsumeData(hourConsumeTable,dayConsumeTable,datime.toString("yyyy-MM-dd"),datime);
        //自来水
        minConsumeTable="min_consume_tapWater";
        hourConsumeTable="hour_consume_tapWater";
        dayConsumeTable="day_consume_tapWater";
        timeJobService.insertConsumeData(minConsumeTable,hourConsumeTable,datime.toString("yyyy-MM-dd HH"),datime);
        timeJobService.insertConsumeData(hourConsumeTable,dayConsumeTable,datime.toString("yyyy-MM-dd"),datime);


    }

    @Async
    @Scheduled(cron="0 0 0/1 * * ?")
    public void TimeJobByHour() {
        System.out.println("每小时执行一次");
        DateTime datime= DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_power_table";
        String condition="where realValue_tagname like '%EP%' or realValue_tagname like '%EQ%' or realValue_tagname like '%PF%'or realValue_tagname like '%P%'";
        String historyTableName="hour_power";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);

    }

    @Async
    @Scheduled(cron="0 0 0 * * ?" )
    public void TimeJobByDay() {
        System.out.println("每天执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_power_table";
        String condition="where realValue_tagname like '%EP%' or realValue_tagname like '%EQ%' or realValue_tagname like '%PF%'or realValue_tagname like '%P%'";
        String historyTableName="day_power";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);

    }

    //循环水的定时任务
    @Async
    @Scheduled(cron="0 0/5 * * * ?")
    public void TimeJobBy5min_circulatingWater() {
        System.out.println("每5分钟执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_circulatingWater_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%TE%'";
        String historyTableName="min_circulatingWater";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);
        //计算每5分钟的耗值
        String historyTableNameConsume="min_consume_circulatingWater";
        String lastTimeTableName="lastTime_circulatingWater_table";
        timeJobService.insert5minConsumeData(dataSourceTableName,historyTableNameConsume,lastTimeTableName,condition);
        System.out.print("整理五分钟数据成功");
    }

    @Async
    @Scheduled(cron="0 0 0/1 * * ?")
    public void TimeJobByHour_circulatingWater() {
        System.out.println("每小时执行一次");
        DateTime datime= DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_circulatingWater_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%TE%'";
        String historyTableName="hour_circulatingWate";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);


    }

    @Async
    @Scheduled(cron="0 0 0 * * ?" )
    public void TimeJobByDay_circulatingWater() {
        System.out.println("每天执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_circulatingWater_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%TE%'";
        String historyTableName="day_circulatingWate";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);

    }

//发酵空气的定时任务
   @Async
   @Scheduled(cron="0 0/5 * * * ?")
    public void TimeJobBy5min_fermentationAir() {
        System.out.println("每5分钟执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
       String dataSourceTableName="realValue_fermentationAir_table";
       String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%EQ1%' or realValue_tagname like '%EQ2%'";
       String historyTableName="min_fermentationAir";
       timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);
       //计算每5分钟的耗值
       String historyTableNameConsume="min_consume_fermentationAir";
       String lastTimeTableName="lastTime_fermentationAir_table";
       timeJobService.insert5minConsumeData(dataSourceTableName,historyTableNameConsume,lastTimeTableName,condition);
       System.out.print("整理五分钟数据成功");
    }

    @Async
    @Scheduled(cron="0 0 0/1 * * ?")
    public void TimeJobByHour_fermentationAir() {
        System.out.println("每小时执行一次");
        DateTime datime= DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_fermentationAir_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%EQ1%' or realValue_tagname like '%EQ2%'";
        String historyTableName="hour_fermentationAir";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);


    }

    @Async
    @Scheduled(cron="0 0 0 * * ?" )
    public void TimeJobByDay_fermentationAir() {
        System.out.println("每天执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_fermentationAir_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%EQ1%' or realValue_tagname like '%EQ2%'";
        String historyTableName="day_fermentationAir";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);

    }

    //冷冻水的定时任务
    @Async
    @Scheduled(cron="0 0/5 * * * ?")
    public void TimeJobBy5min_freezeWater() {
        System.out.println("每5分钟执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_freezeWater_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%TE%'";
        String historyTableName="min_freezeWater";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);
        //计算每5分钟的耗值
        String historyTableNameConsume="min_consume_freezeWater";
        String lastTimeTableName="lastTime_freezeWater_table";
        timeJobService.insert5minConsumeData(dataSourceTableName,historyTableNameConsume,lastTimeTableName,condition);
        System.out.print("整理五分钟数据成功");
    }

    @Async
    @Scheduled(cron="0 0 0/1 * * ?")
    public void TimeJobByHour_freezeWater() {
        System.out.println("每小时执行一次");
        DateTime datime= DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_freezeWater_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%TE%'";
        String historyTableName="hour_freezeWater";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);


    }

    @Async
   @Scheduled(cron="0 0 0 * * ?" )
    public void TimeJobByDay_freezeWater() {
        System.out.println("每天执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_freezeWater_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%TE%'";
        String historyTableName="day_freezeWater";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);

    }

//蒸汽的定时任务
    @Async
    @Scheduled(cron="0 0/5 * * * ?")
    public void TimeJobBy5min_steamMeter() {
        System.out.println("每5分钟执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_steamMeter_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%EQ1%' or realValue_tagname like '%EQ2%' or realValue_tagname like '%ALL%'";
        String historyTableName="min_steamMeter";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);
        //计算每5分钟的耗值
        String historyTableNameConsume="min_consume_steamMeter";
        String lastTimeTableName="lastTime_steamMeter_table";
        timeJobService.insert5minConsumeData(dataSourceTableName,historyTableNameConsume,lastTimeTableName,condition);
        //timeJobMapper.TimeJobBy5min_steamMeter(date,time);

        System.out.print("整理五分钟数据成功");
    }

    @Async
   @Scheduled(cron="0 0 0/1 * * ?")
    public void TimeJobByHour_steamMeter() {
        System.out.println("每小时执行一次");
        DateTime datime= DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
       String dataSourceTableName="realValue_steamMeter_table";
       String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%EQ1%' or realValue_tagname like '%EQ2%' or realValue_tagname like '%ALL%'";
       String historyTableName="hour_steamMeter";
       timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);

    }

    @Async
    @Scheduled(cron="0 0 0 * * ?" )
    public void TimeJobByDay_steamMeter() {
        System.out.println("每天执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_steamMeter_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%EQ1%' or realValue_tagname like '%EQ2%' or realValue_tagname like '%ALL%'";
        String historyTableName="day_steamMeter";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);

    }

   //自来水的定时任务
   @Async
    @Scheduled(cron="0 0/5 * * * ?")
    public void TimeJobBy5min_tapWater() {
        System.out.println("每5分钟执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_tapWater_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%REQ%'";
        String historyTableName="min_tapWater";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);
        //计算每5分钟的耗值
        String historyTableNameConsume="min_consume_tapWater";
        String lastTimeTableName="lastTime_tapWater_table";
        timeJobService.insert5minConsumeData(dataSourceTableName,historyTableNameConsume,lastTimeTableName,condition);
        //timeJobMapper.TimeJobBy5min_tapWater(date,time);

        System.out.print("整理五分钟数据成功");
    }
    //循环水、冷冻水判定的定时任务
    //4-11月，t1供水温度小于15度为冷冻水
    @Async
    @Scheduled(cron="0 0 0/1 * * ?")
    public void TimeJobByHour_xhldWater(){
        System.out.println("每小时执行一次");
        String state = timeJobService.selectTopXHLDWater();
        String stateNow = "";
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        Double temp = timeJobService.selectRealTemp("realValue_circulatingWater_table","FIT-092202-02-T2");
        if(temp<15 && 4<Integer.valueOf(date.substring(5,7)) && Integer.valueOf(date.substring(5,7))<11 ){
            stateNow="冷冻水";
        }else{
            stateNow="循环水";
        }
        if(state!=stateNow){
            timeJobService.insertXHLDWater("stateNow",time);
        }

//        System.out.println(time);
//        String dataSourceTableName="realValue_steamMeter_table";
//        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%EQ1%' or realValue_tagname like '%EQ2%' or realValue_tagname like '%ALL%'";
//        String historyTableName="hour_steamMeter";

        //timeJobMapper.TimeJobByHour_steamMeter(date,time);
    }
    @Scheduled(cron="0 0 0/1 * * ?")
    @Async
    public void TimeJobByHour_tapWater() {
        System.out.println("每小时执行一次");
        DateTime datime= DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_tapWater_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%REQ%'";
        String historyTableName="hour_tapWater";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);

    }

  @Scheduled(cron="0 0 0 * * ?" )
  @Async
    public void TimeJobByDay_tapWater() {
        System.out.println("每天执行一次");
        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String time= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        String dataSourceTableName="realValue_tapWater_table";
        String condition="where realValue_tagname like '%EQ%' or realValue_tagname like '%REQ%'";
        String historyTableName="day_tapWater";
        timeJobService.insertHistoryData(dataSourceTableName,historyTableName,condition,date,time);

    }

    //每月新增所有的能源单价，默认沿用上个月的
//    @Async
//   @Scheduled(cron="0 0 0 1 * ?" )
//    public void TimeJobByMonth() {
//
//        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
//        String time= DateTimeUtil.dateToStr(new Date(),"HH:mm:ss");
//        String Applicable_time=DateTimeUtil.dateToStr(new Date(),"yyyy-MM");
//        //电税率
//        List<Tax> taxList=ReportFormService.taxRecords("");
//        if(taxList.size()>0) ReportFormService.tax(new Tax(taxList.get(0),Applicable_time));
//        //新增自来水单价
//        Unit_Price price=unit_priceService.getLastUnit_Price("Unit_Price_water");
//        if(price!=null) unit_priceService.addWaterUnit_Price(new Unit_Price(price,Applicable_time));
//        //新增循环水单价
//        price=unit_priceService.getLastUnit_Price("Unit_Price_CirculatingWater");
//        if(price!=null) unit_priceService.addCirculatingWaterUnit_Price(new Unit_Price(price,Applicable_time));
//        //新增冷冻水单价
//        price=unit_priceService.getLastUnit_Price("Unit_Price_freezeWater");
//        if(price!=null) unit_priceService.addFreezeWaterUnit_Price(new Unit_Price(price,Applicable_time));
//        //新增蒸汽单价
//        price=unit_priceService.getLastUnit_Price("Unit_Price_steam");
//        if(price!=null) unit_priceService.addSteamUnit_Price(new Unit_Price(price,Applicable_time));
//        //新增空气单价
//        price=unit_priceService.getLastUnit_Price("Unit_Price__fermentationAir");
//        if(price!=null) unit_priceService.addFermentationAirUnit_Price(new Unit_Price(price,Applicable_time));
//        //天然气单价
//        price=unit_priceService.getLastUnit_Price("Unit_Price_NaturalGas");
//        if(price!=null) unit_priceService.addNaturalGasUnit_Price(new Unit_Price(price,Applicable_time));
////        //立约容量
////        String lastTime=ReportFormService.selectLastTime("Contractual_capacity","Applicable_time","");
////        List<Contractual_capacity> capacityList=ReportFormService.electricityFeesSelect(lastTime);
////        for(Contractual_capacity ca:capacityList){
////            ca.setApplicable_time(Applicable_time);
////        }
////        ReportFormService.insert_Contractual_capacity(capacityList,Applicable_time);
//
//        //电单价
//        price=unit_priceService.getLastUnit_Price("Unit_Price_Power");
//        if(price!=null) unit_priceService.addPowerUnit_Price(new Unit_Price(price,Applicable_time));
//
//        //平目录单价
//        lastTime=ReportFormService.selectLastTime("Total_Electricity_Addition","Start_time","平目录");
//        List<TotalElectricityAddition> additionList=ReportFormService.Total_Electricity_Select(lastTime,"平目录");
//        ReportFormService.insertAddition(additionList,"平目录");
//
//        //工业贷征
//        lastTime=ReportFormService.selectLastTime("Total_Electricity_Addition","Start_time","工业电");
//        List<TotalElectricityAddition> gongYeList=ReportFormService.Total_Electricity_Select(lastTime,"工业电");
//        ReportFormService.insertAddition(gongYeList,"工业电");
//
//        //新增综合电单价
//        String pathUrl=ReportFormService.power_price(Applicable_time,"").getData().toString();
//        String reportPath="D:/dist"+pathUrl;
//        String unitPricePower=ZongHeDianJiaUtil.getPricePowerByExcel(reportPath);
//        if(!StringUtils.isEmpty(unitPricePower)) unit_priceService.updatePowerUnit_Price(new Unit_Price(unitPricePower) );
//
//
//    }

    //每月最后一天00:00,执行一次
    @Async
    @Scheduled(cron= "0 0 0 * * ?")
    public void TimeJobByMonthHistoryData() {
        if(this.isLastDayOfMonth(new Date())){
            System.out.println("每月执行一次");
            String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
            String time= DateTimeUtil.dateToStr(new Date(),"HH:mm:ss");
            System.out.println(time);
            //电
            String realValueTableName="realValue_power_table";
            String condition="where realValue_tagname like '%EP%' or realValue_tagname like '%EQ%' or realValue_tagname like '%PF%'or realValue_tagname like '%P%'";
            String dataSourceTableName="day_power";
            String historyTableName="month_power";
            timeJobService.TimeJobByMonthHistoryData(dataSourceTableName,realValueTableName,condition,historyTableName,date,time);
            //循环水
            String realValueCirculatingWater="realValue_circulatingWater_table";
            String conditionCirculatingWater="where realValue_tagname like '%EQ%' or realValue_tagname like '%TE%'";
            String dataSourceCirculatingWater="day_circulatingWate";
            String historyCirculatingWater="month_circulatingWate";
            timeJobService.TimeJobByMonthHistoryData(dataSourceCirculatingWater,realValueCirculatingWater,conditionCirculatingWater,historyCirculatingWater,date,time);
            //压缩空气
            String realValueFer="realValue_fermentationAir_table";
            String conditionFer="where realValue_tagname like '%EQ%' or realValue_tagname like '%EQ1%' or realValue_tagname like '%EQ2%'";
            String dataSourceFer="day_fermentationAir";
            String historyFer="month_fermentationAir";
            timeJobService.TimeJobByMonthHistoryData(dataSourceFer,realValueFer,conditionCirculatingWater,historyFer,date,time);
            //
            String realValueFreezeWater="realValue_freezeWater_table";
            String conditionFreezeWater="where realValue_tagname like '%EQ%' or realValue_tagname like '%TE%'";
            String dataSourceFreezeWater="day_freezeWater";
            String historyFreezeWater="month_freezeWater";
            timeJobService.TimeJobByMonthHistoryData(dataSourceFreezeWater,realValueFreezeWater,conditionFreezeWater,historyFreezeWater,date,time);
            //
            String realValueSteamMeter="realValue_steamMeter_table";
            String conditionSteamMeter="where realValue_tagname like '%EQ%' or realValue_tagname like '%EQ1%' or realValue_tagname like '%EQ2%' or realValue_tagname like '%ALL%'";
            String dataSourceSteamMeter="day_steamMeter";
            String historySteamMeter="month_steamMeter";
            timeJobService.TimeJobByMonthHistoryData(dataSourceSteamMeter,realValueSteamMeter,conditionSteamMeter,historySteamMeter,date,time);
            //
            String realValueTapWater="realValue_tapWater_table";
            String conditionTapWater="where realValue_tagname like '%EQ%' or realValue_tagname like '%REQ%'";
            String dataSourceTapWater="day_tapWater";
            String historyTapWater="month_tapWater";
            timeJobService.TimeJobByMonthHistoryData(dataSourceTapWater,realValueTapWater,conditionTapWater,historyTapWater,date,time);

        }
    }

    //根据传入的年月减去一天
    public String subtractDay(String date) {
        SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
        String returnDate=date;
        try {
            Calendar rightNow = Calendar.getInstance();
            Date begin_d1=df.parse(date);
            rightNow.setTime(begin_d1);
            rightNow.add(Calendar.DAY_OF_MONTH,-1);
            returnDate=df.format(rightNow.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    //判断当前日期是否是本月的最后一天
    public  boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }




}
