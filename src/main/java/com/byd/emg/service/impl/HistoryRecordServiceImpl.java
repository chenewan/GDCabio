package com.byd.emg.service.impl;


import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.mapper.HistoryRecordMapper;
import com.byd.emg.pojo.CycleValue;
import com.byd.emg.pojo.RealValue;
import com.byd.emg.pojo.Tb_analysis;
import com.byd.emg.pojo.TimeJob;
import com.byd.emg.resultData.HistoryParameter;
import com.byd.emg.service.DataMonitoringService;
import com.byd.emg.service.HistoryRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.MONTH;

@Service("historyRecordService")
public class HistoryRecordServiceImpl implements HistoryRecordService {
    @Autowired
    private HistoryRecordMapper historyRecordMapper;

    @Autowired
    private DataMonitoringService  DataMonitoringService;

    //根据tagname和日期来查询当天的历史记录
    public List<String[]> getHistoryRecordByTagname(List<String> cabinet_number, String type, String startTime, String endTime, String startDate, String endDate, String cycle,String showValue,String changing) {
        String table_name="";
        String table_prefix_name="";
        String time_value="time_value";
        String suffixType=type;
        if(changing.equals("电")){
            table_prefix_name="_power";
            time_value="cast(convert(decimal(38,1),cast(replace(case time_value when '' then '0.0' when null then '0.0' else time_value end,',','') as float)) as varchar) as time_value";
        }else if(changing.equals("自来水")){
            table_prefix_name="_tapWater";
            time_value="cast(convert(decimal(38,1),cast(replace(case time_value when '' then '0.0' when null then '0.0' else time_value end,',','') as float)) as varchar) as time_value";
        }else if(changing.equals("空气")){
            table_prefix_name="_fermentationAir";
            time_value="cast(convert(decimal(38,1),cast(replace(case time_value when '' then '0.0' when null then '0.0' else time_value end,',','') as float)) as varchar) as time_value";
        }else if(changing.equals("蒸汽")){
            table_prefix_name="_steamMeter";
            time_value="cast(convert(decimal(38,1),cast(replace(case time_value when '' then '0.0' when null then '0.0' else time_value end,',','') as float)) as varchar) as time_value";
        }else if(changing.equals("循环水")){
            table_prefix_name="_circulatingWate";
            time_value="cast(convert(decimal(38,1),cast(replace(case time_value when '' then '0.0' when null then '0.0' else time_value end,',','') as float)) as varchar) as time_value";
        }else if(changing.equals("冷冻水")){
            table_prefix_name="_freezeWater";
            time_value="cast(convert(decimal(38,1),cast(replace(case time_value when '' then '0.0' when null then '0.0' else time_value end,',','') as float)) as varchar) as time_value";
        }
        List<String[]> cn = new ArrayList<String[]>();
        List<HistoryParameter> historyParameterList=new ArrayList<HistoryParameter>();
        int cnSize=cabinet_number.size();
        if(StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)) return cn;
        if (showValue.equals("表码值")) {
            if (cycle.equals("5分钟")) {
                List<CycleValue> cycleValueList=this.getDateDayList(startTime, endTime, startDate, endDate,cycle);
                for(String number:cabinet_number){
                    suffixType=type;
                    if(!StringUtils.isEmpty(number)){
                        table_name="min"+table_prefix_name;
                    }else{
                        continue;
                    }
                    HistoryParameter historyParameter=new HistoryParameter();
                    historyParameter.setCa_number(number);
                    historyParameter.setTable_name(table_name);
                    if(number.contains("ALL")) suffixType="";
                    historyParameter.setType(suffixType);
                    historyParameterList.add(historyParameter);
                }
                Map<String,List<TimeJob>> minMap=new HashMap<>();
                if(historyParameterList.size()>0) {
                    minMap=this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate,type,time_value,historyParameterList);
                }
                return this.getTimevalueToDate(cycleValueList,cabinet_number,cycle,showValue,minMap);
            } else if (cycle.equals("一小时")) {
                String cn1=null;
                List<CycleValue> cycleValueList=this.getDateDayList(startTime, endTime, startDate, endDate,cycle);
                for(String number:cabinet_number){
                    suffixType=type;
                    if(!StringUtils.isEmpty(number)){
                        table_name="hour"+table_prefix_name;
                    }else{
                        continue;
                    }
                    HistoryParameter historyParameter=new HistoryParameter();
                    historyParameter.setCa_number(number);
                    historyParameter.setTable_name(table_name);
                    if(number.contains("ALL")) suffixType="";
                    historyParameter.setType(suffixType);
                    historyParameterList.add(historyParameter);
                }
                Map<String,List<TimeJob>> minMap=new HashMap<>();
                if(historyParameterList.size()>0) {
                    minMap=this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate,type,time_value,historyParameterList);
                }
                return this.getTimevalueToDate(cycleValueList,cabinet_number,cycle,showValue,minMap);
            } else if (cycle.equals("一天")) {
                List<CycleValue> cycleValueList=this.getDateDayList(startTime, endTime, startDate, endDate,cycle);
                String cn1=null;
                for(String number:cabinet_number){
                    suffixType=type;
                    if(!StringUtils.isEmpty(number)){
                        table_name="day"+table_prefix_name;
                    }else{
                        continue;
                    }
                    HistoryParameter historyParameter=new HistoryParameter();
                    historyParameter.setCa_number(number);
                    historyParameter.setTable_name(table_name);
                    if(number.contains("ALL")) suffixType="";
                    historyParameter.setType(suffixType);
                    historyParameterList.add(historyParameter);
                }
                Map<String,List<TimeJob>> minMap=new HashMap<>();
                if(historyParameterList.size()>0) {
                    minMap=this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate,type,time_value,historyParameterList);
                }
                return this.getTimevalueToDate(cycleValueList,cabinet_number,cycle,showValue,minMap);
            }else if(cycle.equals("月")){
                List<CycleValue> cycleValueList=this.getDateDayList(startTime, endTime, startDate, endDate,cycle);
                String cn1=null;
                for(String number:cabinet_number){
                    suffixType=type;
                    if(!StringUtils.isEmpty(number)){
                        table_name="month"+table_prefix_name;
                    }else{
                        continue;
                    }
                    HistoryParameter historyParameter=new HistoryParameter();
                    historyParameter.setCa_number(number);
                    historyParameter.setTable_name(table_name);
                    if(number.contains("ALL")) suffixType="";
                    historyParameter.setType(suffixType);
                    historyParameterList.add(historyParameter);
                }
                Map<String,List<TimeJob>> minMap=new HashMap<>();
                if(historyParameterList.size()>0) {
                    minMap=this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate,type,time_value,historyParameterList);
                }
                return this.getTimevalueToDate(cycleValueList,cabinet_number,cycle,showValue,minMap);
            }else if(cycle.equals("年")){
               /* List<CycleValue> cycleValueList=this.getDateDayList(startTime, endTime, startDate, endDate,cycle);
                String cn1=null;
                for(String number:cabinet_number){
                    if(!StringUtils.isEmpty(number)){

                    }else{
                        continue;
                    }
                    HistoryParameter historyParameter=new HistoryParameter();
                    historyParameter.setCa_number(number);
                    historyParameter.setTable_name(table_name);
                    historyParameterList.add(historyParameter);
                }
                Map<String,List<Sampling_period_5min>> minMap=new HashMap<>();
                if(historyParameterList.size()>0) {
                    minMap=this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate,type,time_value,historyParameterList);
                }
                return this.getTimevalueToDate(cycleValueList,cabinet_number,cycle,showValue,minMap);*/
                return null;
            } else {
                System.out.print("表值查询周期错误");
                String cn1;
                /*for (int index = 0; index < cabinet_number.size(); index++) {
                    cn1 = cabinet_number.get(index);
                    System.out.print(cn1);
                }*/
                return cn;
            }
        } else if (showValue.equals("消耗值")) {
            if (cycle.equals("5分钟")) {
                String cn1=null;
                List<CycleValue> cycleValueList=this.getDateDayList(startTime, endTime, startDate, endDate,cycle);
                for(String number:cabinet_number){
                    suffixType=type;
                    if(!StringUtils.isEmpty(number)){
                        table_name="min"+table_prefix_name;
                    }else{
                        continue;
                    }
                    HistoryParameter historyParameter=new HistoryParameter();
                    historyParameter.setCa_number(number);
                    historyParameter.setTable_name(table_name);
                    if(number.contains("ALL")) suffixType="";
                    historyParameter.setType(suffixType);
                    historyParameterList.add(historyParameter);
                }
                Map<String,List<TimeJob>> minMap=new HashMap<>();
                if(historyParameterList.size()>0) {
                    minMap=this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate,type,time_value,historyParameterList);
                }
                return this.getTimevalueToDate(cycleValueList,cabinet_number,cycle,showValue,minMap);
            } else if (cycle.equals("一小时")){
                String cn1=null;
                List<CycleValue> cycleValueList=this.getDateDayList(startTime, endTime, startDate, endDate,cycle);
                for(String number:cabinet_number){
                    suffixType=type;
                    if(!StringUtils.isEmpty(number)){
                        table_name="hour"+table_prefix_name;
                    }else{
                        continue;
                    }
                    HistoryParameter historyParameter=new HistoryParameter();
                    historyParameter.setCa_number(number);
                    historyParameter.setTable_name(table_name);
                    if(number.contains("ALL")) suffixType="";
                    historyParameter.setType(suffixType);
                    historyParameterList.add(historyParameter);
                }
                Map<String,List<TimeJob>> minMap=new HashMap<>();
                if(historyParameterList.size()>0) {
                    minMap=this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate,type,time_value,historyParameterList);
                }
                return this.getTimevalueToDate(cycleValueList,cabinet_number,cycle,showValue,minMap);
            } else if (cycle.equals("一天")) {
                endTime="00:00:00";
                List<CycleValue> cycleValueList=this.getDateDayList(startTime, endTime, startDate, endDate,cycle);
                String cn1=null;
                for(String number:cabinet_number){
                    suffixType=type;
                    if(!StringUtils.isEmpty(number)){
                        table_name="day"+table_prefix_name;
                    }else{
                        continue;
                    }
                    HistoryParameter historyParameter=new HistoryParameter();
                    historyParameter.setCa_number(number);
                    historyParameter.setTable_name(table_name);
                    if(number.contains("ALL")) suffixType="";
                    historyParameter.setType(suffixType);
                    historyParameterList.add(historyParameter);
                }
                Map<String,List<TimeJob>> minMap=new HashMap<>();
                if(historyParameterList.size()>0) {
                    minMap=this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate,type,time_value,historyParameterList);
                }
                return this.getTimevalueToDate(cycleValueList,cabinet_number,cycle,showValue,minMap);
            }else if(cycle.equals("月")){
                String cn1=null;
                List<CycleValue> cycleValueList=this.getDateDayList(startTime, endTime, startDate, endDate,cycle);
                for(String number:cabinet_number){
                    suffixType=type;
                    if(!StringUtils.isEmpty(number)){
                        table_name="month"+table_prefix_name;
                    }else{
                        continue;
                    }
                    HistoryParameter historyParameter=new HistoryParameter();
                    historyParameter.setCa_number(number);
                    historyParameter.setTable_name(table_name);
                    if(number.contains("ALL")) suffixType="";
                    historyParameter.setType(suffixType);
                    historyParameterList.add(historyParameter);
                }
                Map<String,List<TimeJob>> minMap=new HashMap<>();
                if(historyParameterList.size()>0) {
                    minMap=this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate,type,time_value,historyParameterList);
                }
                return this.getTimevalueToDate(cycleValueList,cabinet_number,cycle,showValue,minMap);
            }else if(cycle.equals("年")){
                /*String cn1=null;
                List<CycleValue> cycleValueList=this.getDateDayList(startTime, endTime, startDate, endDate,cycle);
                for(String number:cabinet_number){
                    if(!StringUtils.isEmpty(number)){
                        if(number.contains("TZ")) {
                            table_name="tz_year"+table_prefix_name;
                        }else if(number.contains("ZZ")) {
                            if(table_prefix_name.contains("_water")||table_prefix_name.contains("_NaturalGas")){
                                continue;
                            }
                            table_name="zz_year"+table_prefix_name;

                        }else if(number.contains("CH")){
                            if(table_prefix_name.contains("_NaturalGas")){
                                continue;
                            }
                            table_name="ch_year"+table_prefix_name;

                        }else if(number.contains("CJ")){
                            table_name="cj_year"+table_prefix_name;

                        }
                    }else{
                        continue;
                    }
                    HistoryParameter historyParameter=new HistoryParameter();
                    historyParameter.setCa_number(number);
                    historyParameter.setTable_name(table_name);
                    historyParameterList.add(historyParameter);
                }
                Map<String,List<TimeJob>> minMap=new HashMap<>();
                if(historyParameterList.size()>0) {
                    minMap=this.getHistoryRecordByTagnameMap(startTime, endTime, startDate, endDate,type,time_value,historyParameterList);
                }
                return this.getTimevalueToDate(cycleValueList,cabinet_number,cycle,showValue,minMap);*/
                return null;
            }
        }else if(showValue.equals("周期最大值")){
            /*List<CycleValue> cycleValueList=new ArrayList<CycleValue>();
            if (cycle.equals("一天")) {
                cn=this.getCycleValueByCabinet_numberList(cabinet_number, type,startTime,endTime,startDate,endDate,showValue,cycle, table_name, table_prefix_name, time_value,cycleValueList);
                if(cn.size()<cnSize){
                    int length=cnSize-cn.size();
                    for(int index=0;index<length;index++){
                        List<String[]> addList=new ArrayList<String[]>();
                        cn.add(addList);
                    }
                }*/
            return cn;
        }else if(cycle.equals("月")){
                /*cn=this.getCycleValueByCabinet_numberList(cabinet_number, type,startTime,endTime,startDate,endDate,showValue,cycle, table_name, table_prefix_name, time_value,cycleValueList);
                if(cn.size()<cnSize){
                    int length=cnSize-cn.size();
                    for(int index=0;index<length;index++){
                        List<String[]> addList=new ArrayList<String[]>();
                        cn.add(addList);
                    }
                }*/
            return cn;
        }else if(cycle.equals("年")){
                /*cn=this.getCycleValueByCabinet_numberList(cabinet_number, type,startTime,endTime,startDate,endDate,showValue,cycle, table_name, table_prefix_name, time_value,cycleValueList);
                if(cn.size()<cnSize){
                    int length=cnSize-cn.size();
                    for(int index=0;index<length;index++){
                        List<String[]> addList=new ArrayList<String[]>();
                        cn.add(addList);
                    }
                }*/
            return cn;

        }else if(showValue.equals("周期最小值")){
           /* List<CycleValue> cycleValueList=new ArrayList<CycleValue>();
            if (cycle.equals("一天")) {
                cn=this.getCycleValueByCabinet_numberList(cabinet_number, type,startTime,endTime,startDate,endDate,showValue,cycle, table_name, table_prefix_name, time_value,cycleValueList);
                if(cn.size()<cnSize){
                    int length=cnSize-cn.size();
                    for(int index=0;index<length;index++){
                        List<String[]> addList=new ArrayList<String[]>();
                        cn.add(addList);
                    }
                }*/
            return cn;
        }else if(cycle.equals("月")){
                /*cn=this.getCycleValueByCabinet_numberList(cabinet_number, type,startTime,endTime,startDate,endDate,showValue,cycle, table_name, table_prefix_name, time_value,cycleValueList);
                if(cn.size()<cnSize){
                    int length=cnSize-cn.size();
                    for(int index=0;index<length;index++){
                        List<String[]> addList=new ArrayList<String[]>();
                        cn.add(addList);
                    }
                }*/
            return cn;
        }else if(cycle.equals("年")){
                /*cn=this.getCycleValueByCabinet_numberList(cabinet_number, type,startTime,endTime,startDate,endDate,showValue,cycle, table_name, table_prefix_name, time_value,cycleValueList);
                if(cn.size()<cnSize){
                    int length=cnSize-cn.size();
                    for(int index=0;index<length;index++){
                        List<String[]> addList=new ArrayList<String[]>();
                        cn.add(addList);
                    }
                }*/
            return cn;
        }else if(showValue.equals("周期平均值")){
            List<CycleValue> cycleValueList=new ArrayList<CycleValue>();
            if (cycle.equals("一天")) {
               /* cn=this.getCycleValueByCabinet_numberList(cabinet_number, type,startTime,endTime,startDate,endDate,showValue,cycle, table_name, table_prefix_name, time_value,cycleValueList);
                if(cn.size()<cnSize){
                    int length=cnSize-cn.size();
                    for(int index=0;index<length;index++){
                        List<String[]> addList=new ArrayList<String[]>();
                        cn.add(addList);
                    }
                }*/
                return cn;
            }else if(cycle.equals("月")){
                /*cn=this.getCycleValueByCabinet_numberList(cabinet_number, type,startTime,endTime,startDate,endDate,showValue,cycle, table_name, table_prefix_name, time_value,cycleValueList);
                if(cn.size()<cnSize){
                    int length=cnSize-cn.size();
                    for(int index=0;index<length;index++){
                        List<String[]> addList=new ArrayList<String[]>();
                        cn.add(addList);
                    }
                }*/
                return cn;
            }else if(cycle.equals("年")){
                /*cn=this.getCycleValueByCabinet_numberList(cabinet_number, type,startTime,endTime,startDate,endDate,showValue,cycle, table_name, table_prefix_name, time_value,cycleValueList);
                if(cn.size()<cnSize){
                    int length=cnSize-cn.size();
                    for(int index=0;index<length;index++){
                        List<String[]> addList=new ArrayList<String[]>();
                        cn.add(addList);
                    }
                }*/
                return cn;
            }
        }else{
            System.out.print("查询类别错误");
            String cn1;
            for (int index = 0; index < cabinet_number.size(); index++) {
                cn1 = cabinet_number.get(index);
                System.out.print(cn1);
            }
        }
        return cn;
    }

    //获取周期的时间段的集合
    public List<CycleValue> getDateDayList(String startTime, String endTime, String startDate, String endDate,String cycle) {
        List<CycleValue> cycleValueList=new ArrayList<CycleValue>();
        String beginTime=startDate.trim()+" "+startTime.trim();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date begin_d1=df.parse(beginTime);
            Date end_d1=df.parse(endDate+" "+endTime);
            String setEndtime="";
            while(begin_d1.before(end_d1)){
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(begin_d1);
                if(cycle.equals("5分钟")){
                    rightNow.add(Calendar.MINUTE,5);
                }else if(cycle.equals("一小时")){
                    rightNow.add(Calendar.HOUR_OF_DAY,1);
                }else if(cycle.equals("一天")){
                    rightNow.add(Calendar.DAY_OF_MONTH,1);
                }else if(cycle.equals("月")){
                    rightNow.add(MONTH,1);
                }else if(cycle.equals("年")){
                    rightNow.add(Calendar.YEAR,1);
                }else{
                    rightNow.add(Calendar.MINUTE,5);
                }
                setEndtime=df.format(rightNow.getTime());
                CycleValue cycleValue=new CycleValue();
                cycleValue.setStartTime(beginTime);
                cycleValue.setEndTime(setEndtime);
                cycleValueList.add(cycleValue);
                beginTime=setEndtime;
                begin_d1=df.parse(beginTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cycleValueList;
    }

    //将历史值和时间映射上
    public List<String[]> getTimevalueToDate(List<CycleValue> cycleValueList,List<String> cabinet_numberList,String cycle,String showValue,Map<String,List<TimeJob>> minMap) {
        List<String[]> resultList=new ArrayList<String[]>();
        Map<String,List<String[]>> resultMap=new HashMap<>();
        String start_time=null;
        String end_time=null;
        int index=0;
        int length=cabinet_numberList.size();
        for(CycleValue cycleValue:cycleValueList){
            if(cycle.equals("5分钟")){
                start_time=DateTimeUtil.strToStr(cycleValue.getStartTime(),"yyyy-MM-dd HH:mm:ss","HH时mm分");
                end_time= DateTimeUtil.strToStr(cycleValue.getEndTime(),"yyyy-MM-dd HH:mm:ss","HH时mm分");
            }else if(cycle.equals("一小时")){
                start_time=DateTimeUtil.strToStr(cycleValue.getStartTime(),"yyyy-MM-dd HH:mm:ss","MM月dd日HH时");
                end_time=DateTimeUtil.strToStr(cycleValue.getEndTime(),"yyyy-MM-dd HH:mm:ss","MM月dd日HH时");
            }else if(cycle.equals("一天")){
                start_time=DateTimeUtil.strToStr(cycleValue.getStartTime(),"yyyy-MM-dd HH:mm:ss","MM月dd日");
                end_time=DateTimeUtil.strToStr(cycleValue.getEndTime(),"yyyy-MM-dd HH:mm:ss","MM月dd日");
            }else if(cycle.equals("月")){
                start_time=DateTimeUtil.strToStr(cycleValue.getStartTime(),"yyyy-MM-dd HH:mm:ss","yy年MM月");
                end_time=DateTimeUtil.strToStr(cycleValue.getEndTime(),"yyyy-MM-dd HH:mm:ss","yy年MM月");
            }else if(cycle.equals("年")){
                start_time=DateTimeUtil.strToStr(cycleValue.getStartTime(),"yyyy-MM-dd HH:mm:ss","yyyy年");
                end_time=DateTimeUtil.strToStr(cycleValue.getEndTime(),"yyyy-MM-dd HH:mm:ss","yyyy年");
            }
            index=0;
            String data[]=new String[length+1];
            if(showValue.equals("表码值")){
                data[index]=start_time;
                resultList.addAll(returnShowValue(index,cabinet_numberList,minMap,cycleValue,data,showValue));
            }else if(showValue.equals("消耗值")){
                data[index]=start_time+"—"+end_time;
                resultList.addAll(returnShowValue(index,cabinet_numberList,minMap,cycleValue,data,showValue));
            }
        }
        return resultList;
    }

    //返回表码值的方法
    public List<String[]> returnShowValue(int index,List<String> cabinet_numberList,Map<String,List<TimeJob>> minMap,CycleValue cycleValue,String[] data,String showValue){
        List<String[]> resultList=new ArrayList<String[]>();
        boolean flag=true;
        //历史值的最小时间、最大时间
        String min_date="";
        String max_date="";
        BigDecimal aa;
        BigDecimal bb;
        index++;
        int next_index=0;
        for(String cn1:cabinet_numberList){
            List<TimeJob> minList=minMap.get(cn1);
            if(minList==null) minList=new ArrayList<TimeJob>();
            if(minList.size()>0){
                min_date=minList.get(0).getHis_time();
                int num=minList.size()-1;
                max_date=minList.get(num).getHis_time();
            }
            flag=true;
            if(!StringUtils.isEmpty(cn1)&&this.compareToDate_2(min_date, max_date, cycleValue)==1){
                for(int j=0;j<minList.size();j++){
                    TimeJob timeJob=minList.get(j);
                    String queryDate=timeJob.getHis_time();
                    if(this.compareToDate(cycleValue,queryDate)){
                        if(showValue.equals("表码值")){
                            data[index]=timeJob.getTime_value();
                            index++;
                        }else if(showValue.equals("消耗值")){
                            next_index=j+1;
                            if(next_index>=minList.size()) break;
                            String value_1=timeJob.getTime_value();
                            if(StringUtils.isEmpty(value_1)) {
                                data[index]="";
                                index++;
                                minList.remove(j);
                                j--;
                                flag=false;
                                break;
                            }
                            while(value_1.contains(",")){
                                int index_1=value_1.indexOf(",");
                                value_1=value_1.substring(0,index_1)+value_1.substring(index_1+1);
                            }
                            aa = new BigDecimal(value_1);

                            String value_2=minList.get(next_index).getTime_value();
                            if(StringUtils.isEmpty(value_2)) {
                                data[index]="";
                                index++;
                                minList.remove(j);
                                j--;
                                flag=false;
                                break;
                            }
                            while(value_2.contains(",")){
                                int index_2=value_2.indexOf(",");
                                value_2=value_2.substring(0,index_2)+value_2.substring(index_2+1);
                            }
                            bb = new BigDecimal(value_2);
                            data[index]=bb.subtract(aa).toString();
                            index++;
                        }
                        minList.remove(j);
                        j--;
                        flag=false;
                        break;
                    }
                }
            }
            if(flag) {
                data[index]="";
                index++;
            }
        }
        resultList.add(data);
        return resultList;
    }

    //判断数据库查询出来的时间是否在周期时间段内
    public boolean compareToDate(CycleValue cycleValue, String queryDate){
        Date date_Db=null;
        Date startDate=null;
        Date endDate=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date_Db = sdf.parse(queryDate);
            startDate = sdf.parse(cycleValue.getStartTime());
            endDate=sdf.parse(cycleValue.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date_Db.compareTo(startDate)>=0&&date_Db.compareTo(endDate)<0){
            return true;
        }else{
            return false;
        }
    }

    public Map<String, List<TimeJob>> getHistoryRecordByTagnameMap(String startTime, String endTime, String startDate, String endDate, String type, String time_value, List<HistoryParameter> historyParameterList) {
        Map<String, List<TimeJob>> resultMap=new HashMap<>();
        List<TimeJob> minList=historyRecordMapper.getHistoryRecordByTagnameList(startTime,endTime,startDate,endDate,type,time_value,historyParameterList);
        for(HistoryParameter history:historyParameterList){
            if(StringUtils.isEmpty(history.getCa_number())) continue;
            List<TimeJob> minList_2=new ArrayList<>();
            for(int index=0;index<minList.size();index++){
                TimeJob minDb=minList.get(index);
                if((history.getCa_number()+history.getType()).equals(minDb.getTagname())){
                    minList_2.add(minDb);
                    minList.remove(index);
                    index--;
                }
            }
            resultMap.put(history.getCa_number(),minList_2);
        }
        return resultMap;
    }

    public  List< List<TimeJob>> getmaxbymin(List<String> cabinet_number, String type, String startTime, String endTime, String startDate, String endDate, String cycle, String showValue, String changing) {
        String table_name="";
        String table_prefix_name="";
        if(changing.equals("电")){
            table_prefix_name="_power";
        }else if(changing.equals("自来水")){
            table_prefix_name="_tapWater";
        }else if(changing.equals("空气")){
            table_prefix_name="_fermentationAir";
        }else if(changing.equals("蒸汽")){
            table_prefix_name="_steamMeter";
        }else if(changing.equals("循环水")){
            table_prefix_name="_circulatingWater";
        }else if(changing.equals("冷冻水")){
            table_prefix_name="_freezeWater";
        }
        if (showValue.equals("最大值")) {
            if (cycle.equals("5分钟")) {
                table_name="tz_5min"+table_prefix_name;
                String cn1;
                List cn = new ArrayList();
                for (int index = 0; index < cabinet_number.size(); index++) {
                    cn1 = cabinet_number.get(index);
                    System.out.print(cn1);
                    cn.add(historyRecordMapper.getmaxbymin(cn1, startTime, endTime, startDate, endDate,type,table_name));
                }
                return cn;
            } else {
                String cn1;
                List cn = new ArrayList();
                for (int index = 0; index < cabinet_number.size(); index++) {
                    cn1 = cabinet_number.get(index);
                    System.out.print(cn1);
                }
                return cn;
            }
        }
        else{
            String cn1;
            List cn = new ArrayList();
            for (int index = 0; index < cabinet_number.size(); index++) {
                cn1 = cabinet_number.get(index);
                System.out.print(cn1);
            }
            return cn;
            }

    }

    /*状态码："1"表示历史值的最小时间小于周期时间段的结束时间，并且历史值的最大时间大于周期时间段的起始时间(可以开始循环)，
     *         "0"表示历史值的最小时间大于等于周期时间段的结束时间(循环尚未开始，这部分需要补空字符串)
     *         "-1"表示周期时间段已超过历史值的最大时间(这部分可以不需要补空字符串，减少循环次数)
     */
    public int compareToDate_2(String min_date, String max_date, CycleValue cycleValue) {
        if(StringUtils.isEmpty(min_date)||StringUtils.isEmpty(max_date)) return -10;
        Date startDate_db=null;
        Date endDate_db=null;
        Date startDate=null;
        Date endDate=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            startDate_db = sdf.parse(min_date);
            endDate_db = sdf.parse(max_date);
            startDate = sdf.parse(cycleValue.getStartTime());
            endDate=sdf.parse(cycleValue.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(startDate_db.compareTo(endDate)<0&&endDate_db.compareTo(startDate)>=0){
            return 1;
        }else if(endDate_db.compareTo(startDate)<0){
            return -1;
        }else{
            return 0;
        }
    }

    public List<List<TimeJob>> getminbymin (List < String > cabinet_number, String type, String startTime, String endTime, String startDate, String endDate, String cycle,String showValue,String changing){
        String table_name="";
        String table_prefix_name="";
        if(changing.equals("电")){
            table_prefix_name="_power";
        }else if(changing.equals("自来水")){
            table_prefix_name="_tapWater";
        }else if(changing.equals("空气")){
            table_prefix_name="_fermentationAir";
        }else if(changing.equals("蒸汽")){
            table_prefix_name="_steamMeter";
        }else if(changing.equals("循环水")){
            table_prefix_name="_circulatingWater";
        }else if(changing.equals("冷冻水")){
            table_prefix_name="_freezeWater";
        }
        if (showValue.equals("最小值")) {
            if (cycle.equals("5分钟")) {
                table_name="tz_5min"+table_prefix_name;
                String cn1;
                List cn = new ArrayList();
                for (int index = 0; index < cabinet_number.size(); index++) {
                    cn1 = cabinet_number.get(index);
                    System.out.print(cn1);
                    cn.add(historyRecordMapper.getminbymin(cn1, startTime, endTime, startDate, endDate,type,table_name));
                }
                return cn;
            } else {
                String cn1;
                List cn = new ArrayList();
                for (int index = 0; index < cabinet_number.size(); index++) {
                    cn1 = cabinet_number.get(index);
                    System.out.print(cn1);
                }
                return cn;
            }
        }
        else{
           String cn1;
            List cn = new ArrayList();
            for (int index = 0; index < cabinet_number.size(); index++) {
                cn1 = cabinet_number.get(index);
                System.out.print(cn1);
            }
            return cn;
        }
    }

    @Override
    public List<Tb_analysis> getHistoryRecordAnalysis(String cabinet_number, String type, List<String> his_Date) {
        String date ;
        String date1;

        List<Tb_analysis> resultList=new ArrayList();
        for (int index = 0; index < his_Date.size(); index++) {
            date = his_Date.get(index);
            System.out.print(date);
            String startday = historyRecordMapper.getHistoryRecordAnalysis(cabinet_number,type,date);
            date1=date;
            for (int i = index+1; i < his_Date.size(); i++) {
                String value=null;
                date = his_Date.get(i);
                System.out.print(date);
                String endday = historyRecordMapper.getHistoryRecordAnalysis(cabinet_number,type,date);

          if (StringUtils.isEmpty(endday)&&StringUtils.isEmpty(startday)){

              break;
          }else {

              BigDecimal aa = new BigDecimal(endday);
              BigDecimal bb = new BigDecimal(startday);
              value = aa.subtract(bb).toString();
              String describe = historyRecordMapper.getdescribe(cabinet_number, type);

              Tb_analysis tb_analysis = new Tb_analysis();
              tb_analysis.setValue(value);
              tb_analysis.setDate(date1.substring(0, 7));
              tb_analysis.setDescribe(describe);
              resultList.add(tb_analysis);

              break;
          }
            }

        }
        return resultList;
    }

    //导出Excel表格所需要的数据
    @Override
    public Map<String,Object> exportExcelData(List<String> cabinet_number, String type, String startTime, String endTime, String startDate, String endDate, String cycle, String showValue,String changing,String tableName) {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<TimeJob> sampling_period_5minList=new ArrayList<TimeJob>();
        List<String[]> historyData=this.getHistoryRecordByTagname(cabinet_number,type,startTime,endTime,startDate,endDate,cycle,showValue,changing);
        //柜体编号和柜体名称映射关系的集合
        Map<String,String> cabinetNameMap=this.cabinetNameMap(tableName);
        resultMap.put("cabinetNameMap",cabinetNameMap);
        resultMap.put("historyData",historyData);
        return resultMap;
    }

    public Map<String,String> cabinetNameMap(String tableName){
        Map<String,String> resultMap=new HashMap<>();
        List<RealValue> list=historyRecordMapper.selectData(tableName);
        for(RealValue dataDb:list){
            resultMap.put(dataDb.getCabinet_number(),dataDb.getCabinet_name());
        }
        return resultMap;
    }


}