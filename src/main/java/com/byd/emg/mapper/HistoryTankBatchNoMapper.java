package com.byd.emg.mapper;

import com.byd.emg.pojo.HistoryTankBatchNo;
import com.byd.emg.pojo.RealValueTankBatchNo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HistoryTankBatchNoMapper {

    public List<HistoryTankBatchNo> selectHistoryData(@Param("startStateList") List<RealValueTankBatchNo> startStateList);

    public void batchInsert(@Param("startStateList") List<RealValueTankBatchNo> startStateList);

    public void batchUpdate(@Param("hisUpdateList") List<HistoryTankBatchNo> hisUpdateList);

    public String countTankNumbers(@Param("startDate") String startDate,@Param("endDate")  String endDate,@Param("tankNumberList") List<String> tankNumberList,@Param("productType") String productType);

    public String countSpanNumbers(@Param("startDate") String startDate,@Param("endDate")  String endDate,@Param("tankNumberList")  List<String> tankNumberList,@Param("productType")  String productType);

    public List<String> getTotalAir(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("type") String type, @Param("field") String field);

    public List<HistoryTankBatchNo> getTotalAirCrossMonth(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("field") String field,@Param("type") String type);
}
