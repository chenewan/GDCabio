package com.byd.emg.mapper;

import com.byd.emg.pojo.Steam;
import com.byd.emg.pojo.TimeJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimeJobMapper {


    //power
    public int  TimeJobBy5min (@Param("date")String date, @Param("time") String time);

    public int TimeJobByHour(@Param("date")String date,@Param("time") String time);

    public int TimeJobByDay(@Param("date")String date,@Param("time") String time);
    //circulatingWater
    public int  TimeJobBy5min_circulatingWater (@Param("date")String date, @Param("time") String time);

    public int TimeJobByHour_circulatingWater(@Param("date")String date,@Param("time") String time);

    public int TimeJobByDay_circulatingWater(@Param("date")String date,@Param("time") String time);
    //_fermentationAir
    public int  TimeJobBy5min_fermentationAir (@Param("date")String date, @Param("time") String time);

    public int TimeJobByHour_fermentationAir(@Param("date")String date,@Param("time") String time);

    public int TimeJobByDay_fermentationAir(@Param("date")String date,@Param("time") String time);
    //_freezeWater
    public int  TimeJobBy5min_freezeWater (@Param("date")String date, @Param("time") String time);

    public int TimeJobByHour_freezeWater(@Param("date")String date,@Param("time") String time);

    public int TimeJobByDay_freezeWater(@Param("date")String date,@Param("time") String time);
    //_steamMeter
    public int  TimeJobBy5min_steamMeter (@Param("date")String date, @Param("time") String time);

    public int TimeJobByHour_steamMeter(@Param("date")String date,@Param("time") String time);

    public int TimeJobByDay_steamMeter(@Param("date")String date,@Param("time") String time);
    //_tapWater
    public int  TimeJobBy5min_tapWater (@Param("date")String date, @Param("time") String time);

    public int TimeJobByHour_tapWater(@Param("date")String date,@Param("time") String time);

    public int TimeJobByDay_tapWater(@Param("date")String date,@Param("time") String time);

    public Steam selectSteam(Steam steam);

    public int insertSteam(Steam steam);

    public int updateSteam(Steam steam);

    public List<TimeJob> zssEletric(@Param("tagnameList") List<String> tagnameList, @Param("startDate") String startDate,@Param("endDate") String endDate, @Param("condition") String condition);

    public List<String> selectRealTimeTagnameAll(@Param("dataSourceTableName") String dataSourceTableName,@Param("condition") String condition);

    public List<String> selectShouShuTagnameList(@Param("tagnameList") List<String> tagnameList);

    public List<TimeJob> selectZiCaiData(@Param("tagnameList") List<String> tagnameList,@Param("dataSourceTableName") String dataSourceTableName);

    public List<TimeJob> selectShouShuData(@Param("shouShuTagnameList") List<String> shouShuTagnameList);

    public void batchInsertHistoryData(@Param("insertTimeJobList") List<TimeJob> insertTimeJobList,@Param("historyTableName") String historyTableName,@Param("date") String date,@Param("time") String time);

    public List<TimeJob> getMonthCurrData(@Param("dataSourceTableName") String dataSourceTableName,@Param("queryDate") String queryDate,@Param("tagnameList") List<String> tagnameList);

    public List<TimeJob> getTimeJobByTableName(@Param("tableName") String tableName);

    public void batchUpdateTimeJob(@Param("updateTimeJobList") List<TimeJob> updateTimeJobList,@Param("tableName") String tableName);

    public void batchInsertTimeJob(@Param("ziCaiTimeJobList") List<TimeJob> ziCaiTimeJobList, @Param("tableName") String tableName);

    public void batchInsertConsumeData(@Param("consumeDataList") List<TimeJob> consumeDataList,@Param("tableName") String tableName);

    public void batchUpdateConsumeData(@Param("consumeDataList")  List<TimeJob> consumeDataList,@Param("tableName")  String tableName,@Param("queryTime") String queryTime);

    public List<TimeJob> selectByTagnameList(@Param("tableName") String tableName,@Param("hisTime") String hisTime,@Param("tagnameList")  List<String> tagnameList);

    public String selectMaxHisTime(@Param("tableName") String tableName);

    public List<TimeJob> selectTotalConsume(@Param("queryDate") String queryDate,@Param("tagnameList")  List<String> tagnameList,@Param("tableName")  String tableName);

    public List<TimeJob> getSumData(@Param("datasourceTable") String datasourceTable,@Param("queryDate")  String queryDate);



    public String selectTopXHLDWater();

    public Double selectRealTemp(@Param("realValue_table") String realValue_table, @Param("tagName") String tagName);

    public void insertXHLDWater(@Param("stateNow") String stateNow, @Param("time") String time);
}
