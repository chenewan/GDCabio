package com.byd.emg.mapper;

import com.byd.emg.pojo.Unit_Price;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Unit_PriceMapper {

    public List<Unit_Price> getPowerUnit_Price();

    public List<Unit_Price> getWaterUnit_Price();

    public List<Unit_Price> getSteamUnit_Price();

    public List<Unit_Price> getCirculatWaterUnit_Price();

    public List<Unit_Price> getFreezeWaterUnit_Price();

    public List<Unit_Price> getFermentationAirUnit_Price();

    public int addPowerUnit_Price(Unit_Price unit_price);

    public int addWaterUnit_Price(@Param("price")String price,@Param("start_time")String start_time);

    public int addSteamUnit_Price(@Param("price")String price,@Param("start_time")String start_time);

    public int addNaturalGasUnit_Price(@Param("price") String price,@Param("start_time") String start_time);

    public int addCirculatingWaterUnit_Price(@Param("price") String price,@Param("start_time")  String start_time);

    public int addFermentationAirUnit_Price(@Param("price") String price,@Param("start_time")   String start_time);

    public int addFreezeWaterUnit_Price(@Param("price") String price,@Param("start_time")   String start_time);

    public int updatePowerUnit_Price_times(@Param("start_time")String start_time);

    public int updateWaterUnit_Price_times(@Param("start_time")String start_time);

    public int updateSteamUnit_Price_times(@Param("start_time")String start_time);

    public int updateNaturalGasUnit_Price_times(@Param("start_time") String start_time);

    public int updateCirculatingWaterUnit_Price_times(@Param("start_time") String start_time);

    public int updateFermentationAirUnit_Price_times(@Param("start_time") String start_time);

    public int updateFreezeWaterUnit_Price(@Param("start_time") String start_time);

    public Double selectUnitPrice(@Param("strtDate") String strtDate,@Param("endDate")  String endDate,@Param("tableName")  String tableName);

    public void insertComprehensivePricepower(@Param("unitPricePower") String unitPricePower,@Param("date")  String date);

    public Double getUnitPrice(@Param("start_t") String start_t,@Param("end_t") String end_t,@Param("tableName") String tableName);

    public List<Unit_Price> selectByTime(@Param("start_time") String start_time);

    public int batchUpdateUnit_Price(@Param("unit_priceList") List<Unit_Price> unit_priceList);

    public List<Unit_Price> getUnitPriceByTime(@Param("tableName") String tableName,@Param("start_time") String start_time);

    public int batchUpdate(@Param("unit_priceList") List<Unit_Price> unit_priceList,@Param("tableName") String tableName);

    public List<Unit_Price> unitPriceRecords(@Param("start_time") String start_time,@Param("tableName") String tableName);

    public List<Unit_Price> powerUnit_PriceRecords(@Param("start_time") String start_time,@Param("tableName")  String tableName);

    public int updateUnit_Price(@Param("unit_price") Unit_Price unit_price,@Param("tableName") String tableName);

    public int deleteUnit_PriceById(@Param("id") Long id,@Param("tableName") String tableName);

    public int updatePowerUnit_Price(Unit_Price unit_price);

    public List<Unit_Price> selectPowerUnit_Price();

    public int deletePowerUnit_Price(@Param("id") Long id);

    public Long getIDFromPowerUnit_Price(@Param("start_time") String start_time);

    public Unit_Price getLastUnit_Price(@Param("tableName") String tableName);
}
