package com.byd.emg.mapper;

import com.byd.emg.pojo.Contractual_capacity;
import com.byd.emg.pojo.Tax;
import com.byd.emg.pojo.TotalElectricityAddition;
import com.byd.emg.pojo.Unit_Price;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ElectricityPriceStatementMapper {
    public int insert_Contractual_capacity (@Param("contractual_capacity") List<Contractual_capacity> contractual_capacity,@Param("time") String time);

    public int insert_tax(@Param("tax") String tax,@Param("start_time") String start_time);

    public int update_tax(@Param("start_time") String start_time);

    public int insert_TotalElectricityAddition();

    public int update_TotalElectricityAddition();

    public List<Contractual_capacity> select_Contractual_capacity (@Param("Applicable_time" )String Applicable_time );

    public List<Map<String,String>> select_Total_Electricity (@Param("Applicable_time" )String Applicable_time );

    public List<Unit_Price> select_Unit_Price (@Param("Applicable_time" )String Applicable_time );

    public List<TotalElectricityAddition> select_TotalElectricityAddition (@Param("Applicable_time" )String Applicable_time );

    public List<Contractual_capacity> selectContractual_capacityByList(@Param("contractual_capacity") List<Contractual_capacity> contractual_capacity);

    public List<Tax> select_tax (@Param("Applicable_time" )String Applicable_time );

    public List<TotalElectricityAddition> selectAddtionByList(@Param("additionList" ) List<TotalElectricityAddition> additionList,@Param("type") String type);

    public int batchUpdateAddtion(@Param("updateAddtionList") List<TotalElectricityAddition> updateAddtionList);

    public int batchInsertAddtion(@Param("additionList") List<TotalElectricityAddition> additionList,@Param("type") String type);

    public List<Tax> selectByTime(@Param("start_time") String start_time);

    public int batchUpdateTax(@Param("taxList") List<Tax> taxList);

    public int updateTax(Tax tax);

    public int updateContractualCapacity(@Param("updateList") List<Contractual_capacity> updateList,@Param("time")  String time);

    public int delTaxById(@Param("id") Long id);

    public List<Tax> taxRecords(@Param("start_time") String start_time);

    public int Total_Electricity_Update(TotalElectricityAddition totalElectricityAddition);

    public List<TotalElectricityAddition> Total_Electricity_Select(@Param("searchTime") String searchTime, @Param("type") String type);

    public int Total_Electricity_Delete(@Param("id") Long id);

    public String selectLatestTime(@Param("type") String type);

    public int electricityFeesUpdate(Contractual_capacity contractualCapacity);

    public List<Contractual_capacity> electricityFeesSelect();

    public String selectElectricityFeesLatestTime();

    public String selectLastTime(@Param("tableName") String tableName,@Param("field") String field,@Param("type") String type);

    public int electricityFeesDelete(@Param("id") Long id);

    public Contractual_capacity selectByCapacity(Contractual_capacity capacity);

    public int updateCapacityById(Contractual_capacity capacity);

    public int insertCapacity(Contractual_capacity capacity);

    public int addElectricityFees(Contractual_capacity contractualCapacity);

    public void UpdateSmTable(@Param("tableName") String tableName, @Param("columnName") String columnName, @Param("columnValue") String columnValue, @Param("whereColumnName") String whereColumnName, @Param("whereValue") String whereValue);
}
