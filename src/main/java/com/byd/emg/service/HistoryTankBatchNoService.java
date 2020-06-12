package com.byd.emg.service;

import com.byd.emg.pojo.HistoryTankBatchNo;
import com.byd.emg.pojo.RealValueTankBatchNo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("historyTankBatchNoService")
public interface HistoryTankBatchNoService {

    public List<HistoryTankBatchNo> selectHistoryData(List<RealValueTankBatchNo> startStateList);

    public void batchInsert(List<RealValueTankBatchNo> startStateList);

    public void batchUpdate(List<HistoryTankBatchNo> hisUpdateList);

    public String countTankNumbers(String startDate, String endDate,List<String> smallList,String productType);

    public String countSpanNumbers(String startDate, String endDate, List<String> lagreList, String productType);

    public Double getTotalAir(String startDate,String endDate,String type,String field);

    public Double getTotalAirCrossMonth(String startDate,String endDate,String field,String type);

}
