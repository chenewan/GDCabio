package com.byd.emg.mapper;

import com.byd.emg.pojo.RealValue;
import com.byd.emg.pojo.TimeJob;
import com.byd.emg.resultData.HistoryParameter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HistoryRecordMapper {

    public List<TimeJob> getHistoryRecordByTagnameList(@Param("startTime") String startTime, @Param("endTime") String endTime,@Param("startDate") String startDate, @Param("endDate") String endDate,@Param("type") String type,@Param("time_value") String time_value,@Param("historyParameterList") List<HistoryParameter> historyParameterList);

    //根据tagname和日期来查询当天的历史记录
    public List<String> getHistoryRecordByTagname(@Param("cn1")String cn1, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("type") String type, @Param("table_name") String table_name);

    public List<String> getHistoryRecordByTagnameToHour(@Param("cn1")String cn1, @Param("startTime") String startTime, @Param("endTime") String endTime,@Param("startDate") String startDate, @Param("endDate") String endDate,@Param("type") String type,@Param("table_name") String table_name);

    public List<String> getHistoryRecordByTagnameToDay(@Param("cn1")String cn1, @Param("startTime") String startTime, @Param("endTime") String endTime,@Param("startDate") String startDate, @Param("endDate") String endDate,@Param("type") String type,@Param("table_name") String table_name);

    public List<TimeJob> getmaxbymin(@Param("cn1")String cn1, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("type") String type, @Param("table_name") String table_name);

    public List<TimeJob> getminbymin(@Param("cn1")String cn1, @Param("startTime") String startTime, @Param("endTime") String endTime,@Param("startDate") String startDate, @Param("endDate") String endDate,@Param("type") String type,@Param("table_name") String table_name);

    public String selectMinTime(@Param("table_name")  String table_name);

    public String selectMaxTime(@Param("table_name") String table_name);

    public List<RealValue> selectData(@Param("tableName") String tableName);

    public String getHistoryRecordAnalysis(@Param("cabinet_number")String cabinet_number, @Param("type")String type,@Param("date")String date);

    public String getdescribe(@Param("cabinet_number")String cabinet_number, @Param("type")String type);

    public String countTankNumbers(@Param("startDate") String startDate,@Param("endDate")  String endDate,@Param("productType")  String productType);
}
