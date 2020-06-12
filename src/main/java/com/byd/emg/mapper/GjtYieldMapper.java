package com.byd.emg.mapper;

import com.byd.emg.pojo.GjtYield;
import com.byd.emg.resultData.GjtEnergyData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GjtYieldMapper {

    public int insertGjtYield(GjtYield gjtYield);

    public List<GjtEnergyData> selectEnergyData(@Param("tableName") String tableName,@Param("fields") String fields);

    public Double selectTotalYields(@Param("tableName") String tableName);

    public List<GjtEnergyData> selectEnergyDataConditon(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("productType") String productType,@Param("tableName") String tableName,@Param("fields") String fields);

    public Double selectTotalYieldsCondition(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("productType") String productType,@Param("tableName") String tableName);

    public List<Integer> selectByTimes(@Param("gjtYield") GjtYield gjtYield);

    public int batchUpdateByIds(@Param("ids") List<Integer> ids,@Param("gjtYield") GjtYield gjtYield);

    public List<GjtYield> selectByProductType(@Param("date") String date,@Param("productType")  String productType,@Param("topMethod")  String topMethod);

    public int updateGjtYield(@Param("gjtYield") GjtYield gjtYield);

    public int deleteGjtById(@Param("id") Long id);

    public Double getProductionDays(@Param("queryDate") String queryDate,@Param("type")  String type,@Param("tableName")  String tableName);

    public List<GjtYield> selectGjtYields(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("type") String type,@Param("tableName") String tableName);
}
