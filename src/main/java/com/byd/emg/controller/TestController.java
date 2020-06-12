package com.byd.emg.controller;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.common.ZongHeDianJiaUtil;
import com.byd.emg.mapper.SectionAnalysisMapper;
import com.byd.emg.pojo.*;
import com.byd.emg.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private  SectionAnalysisService SectionAnalysisService;

    @Autowired
    private ReportFormService ReportFormService;

    @Autowired
    private SectionAnalysisMapper sectionAnalysisMapper;

    @Autowired
    private Unit_PriceService unit_priceService;

    @Autowired
    private TimeJobService timeJobService;

    @Autowired
    private IPowerCompensation iPowerCompensation;


    @RequestMapping("/compensationValue")
    public ServerResponse compensationValue(){
        ReportFormService.getAllData("2019-10-01","2019-10-02",2,2,1);
        ReportFormService.getAllData("2019-10-02","2019-10-03",2,2,1);
        ReportFormService.getAllData("2019-10-03","2019-10-04",2,2,1);
        ReportFormService.getAllData("2019-10-04","2019-10-05",2,2,1);
        ReportFormService.getAllData("2019-10-05","2019-10-06",2,2,1);
        ReportFormService.getAllData("2019-10-06","2019-10-07",2,2,1);
        ReportFormService.getAllData("2019-10-07","2019-10-08",2,2,1);
        ReportFormService.getAllData("2019-10-08","2019-10-09",2,2,1);
        ReportFormService.getAllData("2019-10-09","2019-10-10",2,2,1);
        ReportFormService.getAllData("2019-10-10","2019-10-11",2,2,1);
        ReportFormService.getAllData("2019-10-11","2019-10-12",2,2,1);
        ReportFormService.getAllData("2019-10-12","2019-10-13",2,2,1);
        ReportFormService.getAllData("2019-10-13","2019-10-14",2,2,1);
        ReportFormService.getAllData("2019-10-14","2019-10-15",2,2,1);
        ReportFormService.getAllData("2019-10-15","2019-10-16",2,2,1);
        ReportFormService.getAllData("2019-10-16","2019-10-17",2,2,1);
        ReportFormService.getAllData("2019-10-17","2019-10-18",2,2,1);
        ReportFormService.getAllData("2019-10-18","2019-10-19",2,2,1);
        ReportFormService.getAllData("2019-10-20","2019-10-21",2,2,1);
        ReportFormService.getAllData("2019-10-21","2019-10-22",2,2,1);
        ReportFormService.getAllData("2019-10-22","2019-10-23",2,2,1);
        ReportFormService.getAllData("2019-10-23","2019-10-24",2,2,1);
        ReportFormService.getAllData("2019-10-24","2019-10-25",2,2,1);
        ReportFormService.getAllData("2019-10-25","2019-10-26",2,2,1);
        ReportFormService.getAllData("2019-10-26","2019-10-27",2,2,1);
        ReportFormService.getAllData("2019-10-27","2019-10-28",2,2,1);
        return ServerResponse.createBySuccess("查询成功");
    }


    @RequestMapping("/sectionAnalysis")
    public ServerResponse sectionAnalysis(){
        /*String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd ");
        String minDateDb=sectionAnalysisMapper.selectDate_time("min(his_date)");
        String maxDateDb=sectionAnalysisMapper.selectDate_time("max(his_date)");
        List<String> dateList=sectionAnalysisMapper.getTestDateList();*/
        Acl a=new Acl();
        //for(String date_1:dateList){
            //List<SectionAnalysis> sectionList=SectionAnalysisService.sectionAnalysis(subtractDay(date_1),date_1);
        //}
        return ServerResponse.createBySuccess("查询成功",a);
    }

//    @RequestMapping("/TimeJobByMonth")
//    public void TimeJobByMonth() {
//        /*System.out.println("每月执行一次");
//        String date= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
//        String time= DateTimeUtil.dateToStr(new Date(),"HH:mm:ss");
//        String Applicable_time=DateTimeUtil.dateToStr(new Date(),"yyyy-MM");
//        String pathUrl=ReportFormService.power_price(Applicable_time,"").getData().toString();
//        //生成后的变动电价的报表的路径
//        String reportPath="D:/dist"+pathUrl;
//        //获取综合电价的方法
//        String unitPricePower= ZongHeDianJiaUtil.getPricePowerByExcel(reportPath);
//        unit_priceService.insertComprehensivePricepower(unitPricePower,date);*/
//        Date now=new Date();
//        String date= DateTimeUtil.dateToStr(now,"yyyy-MM-dd");
//        String time= DateTimeUtil.dateToStr(now,"HH:mm:ss");
//        String Applicable_time=DateTimeUtil.dateToStr(now,"yyyy-MM");
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
//        //电单价
//        price=unit_priceService.getLastUnit_Price("Unit_Price_Power");
//        if(price!=null) unit_priceService.addPowerUnit_Price(new Unit_Price(price,Applicable_time));
//
//        //平目录单价
//        lastTime=ReportFormService.selectLastTime("Total_Electricity_Addition","Start_time","平目录");
//        List<TotalElectricityAddition> additionList=ReportFormService.Total_Electricity_Select(lastTime,"平目录");
//        for(TotalElectricityAddition aa:additionList){
//            aa.setStart_time(Applicable_time);
//        }
//        if(additionList.size()>0) ReportFormService.insertAddition(additionList,"平目录");
//        //工业贷征
//        lastTime=ReportFormService.selectLastTime("Total_Electricity_Addition","Start_time","工业电");
//        List<TotalElectricityAddition> gongYeList=ReportFormService.Total_Electricity_Select(lastTime,"工业电");
//        for(TotalElectricityAddition aa:gongYeList){
//            aa.setStart_time(Applicable_time);
//        }
//        if(gongYeList.size()>0) ReportFormService.insertAddition(gongYeList,"工业电");
//        //新增综合电单价
//        String pathUrl=ReportFormService.power_price(Applicable_time,"").getData().toString();
//        String reportPath="D:/dist"+pathUrl;
//        String unitPricePower=ZongHeDianJiaUtil.getPricePowerByExcel(reportPath);
//        if(!StringUtils.isEmpty(unitPricePower)) unit_priceService.insertComprehensivePricepower(unitPricePower,date);
//
//    }

    @RequestMapping("/TimeJobByDay")
    public void TimeJobByDay() {
        //新增综合电单价
        String pathUrl=ReportFormService.power_price("2019-09","").getData().toString();
        String reportPath="D:/dist"+pathUrl;
        String unitPricePower=ZongHeDianJiaUtil.getPricePowerByExcel(reportPath);
        if(!StringUtils.isEmpty(unitPricePower)) unit_priceService.updatePowerUnit_Price(new Unit_Price(unitPricePower) );
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

    public List<String> getDateDayList(String startDate, String endDate) {
        List<String> resultList=new ArrayList<String>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date begin_d1=df.parse(startDate);
            Date end_d1=df.parse(endDate);
            while(begin_d1.before(end_d1)){
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(begin_d1);
                rightNow.add(Calendar.DAY_OF_MONTH,+1);
                String returnDate=df.format(rightNow.getTime());
                resultList.add(returnDate);
                begin_d1=df.parse(returnDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultList;
    }


}
