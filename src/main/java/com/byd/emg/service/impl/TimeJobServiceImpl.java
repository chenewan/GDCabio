package com.byd.emg.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.common.TimerJobs;
import com.byd.emg.mapper.*;
import com.byd.emg.pojo.*;
import com.byd.emg.service.HistoryTankBatchNoService;
import com.byd.emg.service.ProductInfoService;
import com.byd.emg.service.RealValueTankBatchNoService;
import com.byd.emg.service.TimeJobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service("timeJobService")
public class TimeJobServiceImpl  implements TimeJobService {

    @Autowired
    private TimeJobMapper timeJobMapper;

    @Autowired
    private RealValueTankBatchNoService realValueTankBatchNoService;

    @Autowired
    private HistoryTankBatchNoService historyTankBatchNoService;

    @Autowired
    private ProductInfoService iProductInfo;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private RealTimeValueMapper realTimeValueMapper;

    @Autowired
    private InstrumentMapper instrumentMapper;

    @Autowired
    private TimeTableMapper timeTableMapper;

    @Override
    public void equipmentJob() {
//        List<RealValueTankBatchNo> screenRealValueList=realValueTankBatchNoService.screenRealValue();
//        for(RealValueTankBatchNo screenRealValue:screenRealValueList){
//            if(screenRealValue.getAid()==0 && screenRealValue.getState()=="开始"){
//                HistoryTankBatchNo hisUpdateList = new HistoryTankBatchNo();
//                hisUpdateList.setTankNumber(screenRealValue.getTankNumber());
//                hisUpdateList.setBatchNumber(screenRealValue.getBatchNumber());
//                Map<String,String> productMap=iProductInfo.productInfoMap();
//                if(!StringUtils.isEmpty(screenRealValue.getProductType().trim())){
//                    hisUpdateList.setProductType(productMap.get(screenRealValue.getProductType().trim()));
//                }
//                hisUpdateList.setState(screenRealValue.getState());
//                hisUpdateList.setStartTime(screenRealValue.getTime());
//            }
//        }
        List<HistoryTankBatchNo> hisUpdateList=new ArrayList<HistoryTankBatchNo>();
        //从实时表筛选出所有开始和所有结束的记录
        Map<String, List<RealValueTankBatchNo>> tankBatchMap=realValueTankBatchNoService.screenRealValue();
        List<RealValueTankBatchNo> startStateList=tankBatchMap.get("startState");
        List<RealValueTankBatchNo> endStateList=tankBatchMap.get("endState");
        List<RealValueTankBatchNo> allRealValueList=new ArrayList<RealValueTankBatchNo>();
        allRealValueList.addAll(startStateList);
        allRealValueList.addAll(endStateList);
        //编号和产品的映射集合
        Map<String,String> productMap=iProductInfo.productInfoMap();
        //从历史记录表中查出对应的开始状态的记录
        List<HistoryTankBatchNo> historyList=historyTankBatchNoService.selectHistoryData(allRealValueList);
        List<HistoryTankBatchNo> screenList=new ArrayList<>();
        screenList.addAll(historyList);
        //筛选出新开始的记录
        for(int index=0;index<startStateList.size();index++){
            RealValueTankBatchNo realValueDb=startStateList.get(index);
            if(!StringUtils.isEmpty(realValueDb.getProductType().trim())){
                realValueDb.setProductType(productMap.get(realValueDb.getProductType().trim()));
            }
            for(int inner=0;inner<screenList.size();inner++){
                HistoryTankBatchNo hisData=screenList.get(inner);
                if(realValueDb.getTankNumber().equals(hisData.getTankNumber())){
                    if(realValueDb.getBatchNumber().equals(hisData.getBatchNumber())){
                        startStateList.remove(index);
                        index--;
                        screenList.remove(inner);
                        inner--;
                        break;
                    }
                }
            }
        }
        if(startStateList.size()>0) historyTankBatchNoService.batchInsert(startStateList);
        for(RealValueTankBatchNo endRealValue:endStateList){
            for(HistoryTankBatchNo updateData:historyList){
                if(endRealValue.getTankNumber().equals(updateData.getTankNumber())){
                    if(endRealValue.getBatchNumber().equals(updateData.getBatchNumber())){
                        updateData.setSteamTotalFlowEnd(endRealValue.getSteamTotalFlow());
                        updateData.setAirTotalFlowEnd(endRealValue.getAirTotalFlow());
                        double steamCostValue=this.subtraction(updateData.getSteamTotalFlowStart(),updateData.getSteamTotalFlowEnd());
                        double airCostValue=this.subtraction(updateData.getAirTotalFlowStart(),updateData.getAirTotalFlowEnd());
//                        if(steamCostValue>5){
//                            updateData.setMedia("蒸汽");
//                            updateData.setState(endRealValue.getState());
//                            updateData.setSteamCostValue(steamCostValue+"");
//                        }else if(airCostValue>5){
//                            updateData.setMedia("空气");
//                            updateData.setState(endRealValue.getState());
//                            updateData.setAirCostValue(airCostValue+"");
//                        }else{
//                            updateData.setState("异常");
//                        }
                        updateData.setState(endRealValue.getState());
                        updateData.setSteamCostValue(steamCostValue+"");
                        updateData.setAirCostValue(airCostValue+"");
                        updateData.setEndTime(endRealValue.getTime());
                        hisUpdateList.add(updateData);
                        break;
                    }
                }
            }
        }
        if(hisUpdateList.size()>0)  historyTankBatchNoService.batchUpdate(hisUpdateList);
    }

    @Override
    public int addSteam(Steam steam) {
        int result=0;
        Steam steamDb=timeJobMapper.selectSteam(steam);
        if(steamDb==null){//新增
            result=timeJobMapper.insertSteam(steam);
        }else{
            result=timeJobMapper.updateSteam(steam);
        }
        return result;
    }

    @Override
    public Map<String, List<TimeJob>> selectEletric(String startDate,String endDate) {
        Map<String,List<TimeJob>> resultMap=new HashMap<String,List<TimeJob>>();
        //计算华三三的峰、平、谷
        List<String> tagnameList=new ArrayList<>();
        tagnameList.add("JT-110900-02-EP");tagnameList.add("JT-110900-02-EQ");tagnameList.add("JT-110900-02-EP2");
        tagnameList.add("JT-110900-02-EP3");tagnameList.add("JT-110900-02-EP4");
        List<List<TimeJob>> hssList=this.getShowDatas(tagnameList,startDate,endDate);
        resultMap.put("hssStartShowDatas",setList(hssList.get(0),tagnameList));
        resultMap.put("hssCurrShowDatas",setList(hssList.get(1),tagnameList));
        //计算张三三的封、平、谷
        List<String> tagnameList_2=new ArrayList<>();
        tagnameList_2.add("JT-110900-01-EP");tagnameList_2.add("JT-110900-01-EP2");tagnameList_2.add("JT-110900-01-EP3");
        tagnameList_2.add("JT-110900-01-EP4");tagnameList_2.add("JT-110900-01-EQ");
        List<List<TimeJob>> zssList=this.getShowDatas(tagnameList_2,startDate,endDate);
        resultMap.put("zssStartShowDatas",setList(zssList.get(0),tagnameList_2));
        resultMap.put("zssCurrShowDatas",setList(zssList.get(1),tagnameList_2));
        return resultMap;
    }

    public List<List<TimeJob>> getShowDatas(List<String> tagnameList,String startDate,String endDate){
        List<List<TimeJob>> resultList=new ArrayList<>();
        //上次示数
        List<TimeJob> startShowDatas=new ArrayList<>();
        startShowDatas=timeJobMapper.zssEletric(tagnameList,startDate,endDate,"order by his_date asc");
        //本次示数
        List<TimeJob> currShowDatas=new ArrayList<>();
        currShowDatas=timeJobMapper.zssEletric(tagnameList,startDate,endDate,"order by his_date desc");

        //从手输的记录表中查询数据
        List<TimeJob> handStartDatas=new ArrayList<>();
        List<TimeJob> handEndDatas=new ArrayList<>();
        List<Instrument> instrumentList=instrumentMapper.selectByTagnameList(tagnameList,startDate,endDate);
        for(Instrument instrument:instrumentList){
            if(!StringUtils.isEmpty(instrument.getStartValue())) handStartDatas.add(new TimeJob(instrument.getStartValue(),instrument));
            if(!StringUtils.isEmpty(instrument.getEndValue())) handEndDatas.add(new TimeJob(instrument.getEndValue(),instrument));
        }
        if(handStartDatas.size()>0){
            startShowDatas.removeAll(handStartDatas);
            startShowDatas.addAll(handStartDatas);
        }
        if(handEndDatas.size()>0){
            currShowDatas.removeAll(handEndDatas);
            currShowDatas.addAll(handEndDatas);
        }
        resultList.add(startShowDatas);
        resultList.add(currShowDatas);
        return  resultList;
    }




    @Override
    public List<String> insertHistoryData(String dataSourceTableName,String historyTableName,String condition,String date,String time) {
        List<TimeJob> insertTimeJobList=new ArrayList<>();
        //获取实时表所有tagname的集合
        List<String> tagnameList=timeJobMapper.selectRealTimeTagnameAll(dataSourceTableName,condition);
        List<TimeJob> ziCaiTimeJobList=timeJobMapper.selectZiCaiData(tagnameList,dataSourceTableName);
        insertTimeJobList.addAll(ziCaiTimeJobList);
        timeJobMapper.batchInsertHistoryData(insertTimeJobList,historyTableName,date,time);
        return tagnameList;
    }

    /*#########################################计算每5分钟的耗值(并生成历史记录)开始##########################################*/
    @Override
    public void insert5minConsumeData(String dataSourceTableName, String historyTableNameConsume,String lastTimeTableName, String condition) {
        DateTime datime= DateUtil.date();
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("yyyy-MM-dd HH:mm:ss");
        //获取实时表所有tagname的集合
        List<String> tagnameList=timeJobMapper.selectRealTimeTagnameAll(dataSourceTableName,condition);
        List<TimeJob> ziCaiTimeJobList=timeJobMapper.selectZiCaiData(tagnameList,dataSourceTableName);
        //获取上一刻的实时值
        List<TimeJob> lastTimeJobList=timeJobMapper.getTimeJobByTableName(lastTimeTableName);
        List<TimeJob> lastTimeJobDbList=new ArrayList<TimeJob>();
        lastTimeJobDbList.addAll(lastTimeJobList);
        List<TimeJob> consumeDataList=new ArrayList<TimeJob>();
        Double dd=0d;
        for(int kk=0;kk<ziCaiTimeJobList.size();kk++){
            TimeJob timeJob=ziCaiTimeJobList.get(kk);
            if(lastTimeJobList.size()>0){
                for(int index=0;index<lastTimeJobList.size();index++){
                    TimeJob lastTimeJob=lastTimeJobList.get(index);
                    if(timeJob.getTagname().equals(lastTimeJob.getTagname())){
                        dd=0d;
                        try{
                            dd=Double.valueOf(timeJob.getTime_value())-Double.valueOf(lastTimeJob.getTime_value());
                        }catch (Exception e){
                            dd=0d;
                        }
                        //如果当前时刻的耗值小于0，就舍弃
                        if(dd>=0) consumeDataList.add(new TimeJob(timeJob,dd+"",date,time));
                        //timeJob.setTime_value(dd+"");
                        lastTimeJobList.remove(index);
                        index--;
                        break;
                    }
                }
            }
            //如果当前时刻的值为0，就不更新上一刻的值(需要从集合中删掉)
            if(Double.valueOf(timeJob.getTime_value())==0){
                ziCaiTimeJobList.remove(kk);
                kk--;
            }
        }
        if(ziCaiTimeJobList.size()>0) this.batchInsertLastTimeJob(ziCaiTimeJobList,lastTimeJobDbList,lastTimeTableName);
        if(consumeDataList.size()>0) timeJobMapper.batchInsertConsumeData(consumeDataList,historyTableNameConsume);
    }

    private void batchInsertLastTimeJob(List<TimeJob> ziCaiTimeJobList,List<TimeJob> lastTimeJobDbList,String lastTimeTableName) {
        List<TimeJob> updateTimeJobList=new ArrayList<TimeJob>();
        for(int index=0;index<ziCaiTimeJobList.size();index++){
            TimeJob timeJob=ziCaiTimeJobList.get(index);
            for(int kk=0;kk<lastTimeJobDbList.size();kk++){
                TimeJob timeJobDb=lastTimeJobDbList.get(kk);
                if(timeJob.getTagname().trim().equals(timeJobDb.getTagname().trim())){
                    updateTimeJobList.add(timeJob);
                    ziCaiTimeJobList.remove(index);
                    index--;
                    lastTimeJobDbList.remove(kk);
                    kk--;
                    break;
                }
            }
        }
        if(updateTimeJobList.size()>0)  timeJobMapper.batchUpdateTimeJob(updateTimeJobList,lastTimeTableName);
        if(ziCaiTimeJobList.size()>0)  timeJobMapper.batchInsertTimeJob(ziCaiTimeJobList,lastTimeTableName);
    }
    /*#########################################计算每5分钟的耗值结束##########################################*/

    //计算每小时、天的耗值
    @Override
    public void insertConsumeData(String datasourceTable, String hisTable,String queryDate,DateTime datime) {
        String date= datime.toString("yyyy-MM-dd");
        String time= datime.toString("yyyy-MM-dd HH:mm:ss");
        Map<String, String> dataMap = this.selectCurrData(queryDate);
        //查询该小时的耗值
        List<TimeJob> dataList=timeJobMapper.getSumData(datasourceTable,queryDate);
        if(dataList.size()>0){
            for(TimeJob dataDb:dataList){
                dataDb.setHis_date(date);
                dataDb.setHis_time(time);
            }
            if(dataMap.get(hisTable)==null) {//新增
                timeJobMapper.batchInsertConsumeData(dataList,hisTable);
            }else{//更新
                timeJobMapper.batchUpdateConsumeData(dataList,hisTable,queryDate);
            }
            timeTableMapper.deleteByTableName(hisTable);
            timeTableMapper.insert(hisTable,queryDate);
            dataList.clear();
        }

    }

    private Map<String, String> selectCurrData(String queryDate) {
        Map<String, String> resultMap=new HashMap<>();
        List<TimeTable> currList=timeTableMapper.selectCurrData(queryDate);
        for(TimeTable data:currList){
            resultMap.put(data.getTableName(),data.getUpdateTime());
        }
        return resultMap;
    }

    @Override
    public void TimeJobByMonthHistoryData(String dataSourceTableName, String historyTableName,String realValueTableName,String condition,String date, String time) {
        String queryDate= DateTimeUtil.dateToStr(new Date(),"yyyy-MM");
        //从实时表中获取所有的tagname的集合
        List<String> tagnameList=timeJobMapper.selectRealTimeTagnameAll(realValueTableName,condition);
        //从天的历史记录表中查询当月的历史记录
        List<TimeJob> monthCurrDataList=timeJobMapper.getMonthCurrData(dataSourceTableName,queryDate,tagnameList);
        if(monthCurrDataList.size()>0) timeJobMapper.batchInsertHistoryData(monthCurrDataList,historyTableName,date,time);
    }

    @Override
    public void updateOpenTimes() {
        //取上一刻电流值
        List<EquipmentStates> equipmentStatesList = equipmentMapper.getEquipmentStatesAll();
        //取实时电流值
        List<RealValue> realValueList = realTimeValueMapper.getRealValueList(equipmentStatesList);
        for(EquipmentStates equipmentStates:equipmentStatesList){
            for(RealValue realValue:realValueList){
                if(equipmentStates.getRealTagName().equals(realValue.getRealValue_tagname())){
                    if(Double.valueOf(equipmentStates.getTagNameValue())<=10 && Double.valueOf(realValue.getRealValue())>10){
                        equipmentStates.setOpenTimes((Long.valueOf(equipmentStates.getOpenTimes())+1)+"");
                    }
                    equipmentStates.setTagNameValue(realValue.getRealValue());
                }
            }
        }
        if(equipmentStatesList.size()>0){
            equipmentMapper.batchUpdateOpenTimes(equipmentStatesList);
        }
    }

    @Override
    public String selectTopXHLDWater() {
        String selectTopXHLDWater = timeJobMapper.selectTopXHLDWater();
        return selectTopXHLDWater;
    }

    @Override
    public Double selectRealTemp(String realValue_table, String tagName) {
        Double selectRealTemp = timeJobMapper.selectRealTemp(realValue_table,tagName);
        return selectRealTemp;
    }

    @Override
    public void insertXHLDWater(String stateNow, String time) {
        timeJobMapper.insertXHLDWater(stateNow,time);
    }


    public List<TimeJob> setList(List<TimeJob> setList,List<String> tagnameList){
        List<TimeJob> resultList=new ArrayList<>();
        for(String  tagname:tagnameList){
            boolean flag=true;
            for(int index=0;index<setList.size();index++){
                TimeJob timeJob=setList.get(index);
                System.out.println("===>>"+timeJob.getTagname());
                if(timeJob!=null){
                    if(tagname.equals(timeJob.getTagname().trim())){
                        resultList.add(timeJob);
                        setList.remove(index);
                        index--;
                        flag=false;
                        break;
                    }
                }
            }
            if(flag){
                TimeJob timeJobOut=new TimeJob();
                timeJobOut.setTagname(tagname);
                resultList.add(timeJobOut);
            }
        }
        return resultList;
    }


    //
    public double subtraction(String startValue,String endValue){
        double d_value=0d;
        double startDou=0d;
        if(!StringUtils.isEmpty(startValue)) startDou=Double.valueOf(startValue);
        double endDou=0d;
        if(!StringUtils.isEmpty(endValue)) endDou=Double.valueOf(endValue);
        d_value=endDou-startDou;
        return d_value;
    }
}
