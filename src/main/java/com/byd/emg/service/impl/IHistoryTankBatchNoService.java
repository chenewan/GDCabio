package com.byd.emg.service.impl;

import com.byd.emg.mapper.HistoryTankBatchNoMapper;
import com.byd.emg.pojo.HistoryTankBatchNo;
import com.byd.emg.pojo.RealValueTankBatchNo;
import com.byd.emg.service.HistoryTankBatchNoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("historyTankBatchNoService")
public class IHistoryTankBatchNoService implements HistoryTankBatchNoService {

    @Autowired
    private HistoryTankBatchNoMapper historyTankBatchNoMapper;

    @Override
    public List<HistoryTankBatchNo> selectHistoryData(List<RealValueTankBatchNo> startStateList) {
        return historyTankBatchNoMapper.selectHistoryData(startStateList);
    }

    @Override
    public void batchInsert(List<RealValueTankBatchNo> startStateList) {
        historyTankBatchNoMapper.batchInsert(startStateList);
    }

    @Override
    public void batchUpdate(List<HistoryTankBatchNo> hisUpdateList) {
        historyTankBatchNoMapper.batchUpdate(hisUpdateList);
    }

    @Override
    public String countTankNumbers(String startDate, String endDate,List<String> smallList, String productType) {
        return historyTankBatchNoMapper.countTankNumbers(startDate,endDate,smallList,productType);
    }

    @Override
    public String countSpanNumbers(String startDate, String endDate, List<String> lagreList, String productType) {
        return historyTankBatchNoMapper.countSpanNumbers(startDate,endDate,lagreList,productType);
    }

    @Override
    public Double getTotalAir(String startDate, String endDate,String type,String field) {
        List<String> totalString=historyTankBatchNoMapper.getTotalAir(startDate,endDate,type,field);
        Double result=0d;
        for(String airTotal:totalString){
            if(StringUtils.isEmpty(airTotal)) airTotal="0";
            result+=Double.valueOf(airTotal);
        }
        return result;
    }

    @Override
    public Double getTotalAirCrossMonth(String startDate, String endDate,String field,String type) {
        List<HistoryTankBatchNo> HistoryTankBatchNoList = historyTankBatchNoMapper.getTotalAirCrossMonth(startDate,endDate,field,type);

        Double result=0d;
        Double Consumption=0d;
        for(HistoryTankBatchNo historyTankBatchNo:HistoryTankBatchNoList){
            if(StringUtils.isEmpty(historyTankBatchNo.getSteamCostValue())) historyTankBatchNo.setSteamCostValue("0");
            if(StringUtils.isEmpty(historyTankBatchNo.getAirCostValue())) historyTankBatchNo.setAirCostValue("0");
            Date startDate_db=null;
            Date endDate_db=null;
            Date startTime=null;
            Date endTime=null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                startDate_db = sdf.parse(historyTankBatchNo.getStartTime());
                endDate_db = sdf.parse(historyTankBatchNo.getEndTime());
                startTime = sdf.parse(startDate);
                endTime=sdf.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if(field.equals("蒸汽")){
                Consumption=Double.valueOf(historyTankBatchNo.getSteamCostValue());
            }else{
                Consumption=Double.valueOf(historyTankBatchNo.getAirCostValue());
            }
            if(startDate_db.compareTo(startTime) >= 0 && startDate_db.compareTo(endTime) <= 0 && endDate_db.compareTo(endTime) > 0 ){//跨月在月尾
                int allDays = (int) ((endDate_db.getTime() - startDate_db.getTime()) / (1000*3600*24));
                int thisMonthDays = (int) ((endTime.getTime() - startDate_db.getTime()) / (1000*3600*24));
                result+= Consumption *thisMonthDays/allDays;
            }else if(startDate_db.compareTo(startTime) < 0 && endDate_db.compareTo(startTime) >= 0 && endDate_db.compareTo(endTime) <= 0 ){//跨月在月头
                int allDays = (int) ((endDate_db.getTime() - startDate_db.getTime()) / (1000*3600*24));
                int thisMonthDays = (int) ((endDate_db.getTime() - startTime.getTime()) / (1000*3600*24));
                result+=Consumption *thisMonthDays/allDays;
            }else{//跨整月
                int allDays = (int) ((endDate_db.getTime() - startDate_db.getTime()) / (1000*3600*24));
                int thisMonthDays = (int) ((endTime.getTime() - startTime.getTime()) / (1000*3600*24));
                result+=Consumption *thisMonthDays/allDays;
            }
        }
        return result;
    }
}
