package com.byd.emg.service;

import com.byd.emg.pojo.Contractual_capacity;
import com.byd.emg.pojo.Unit_Price;

import java.util.List;

public interface Unit_PriceService {

    public List<Unit_Price> getPowerUnit_Price();

    public List<Unit_Price> getWaterUnit_Price();

    public List<Unit_Price> getSteamUnit_Price();

    public Unit_Price getLastUnit_Price(String tableName);

    public List<Unit_Price> getCirculatWaterUnit_Price();

    public List<Unit_Price> getFreezeWaterUnit_Price();

    public List<Unit_Price> getFermentationAirUnit_Price();

    public int addPowerUnit_Price(Unit_Price unit_price);

    public int addWaterUnit_Price(Unit_Price unit_price);

    public int addSteamUnit_Price(Unit_Price unit_price);

    public int addNaturalGasUnit_Price(Unit_Price unit_price);

    public int addCirculatingWaterUnit_Price(Unit_Price unit_price);

    public int addFreezeWaterUnit_Price(Unit_Price unit_price);

    public int addFermentationAirUnit_Price(Unit_Price unit_price);

    public Double selectUnitPrice(String strtDate, String endDate, String unit_price_power);

    public void insertComprehensivePricepower(String unitPricePower, String date);

    public Double getUnitPrice(String start_t,String end_t,String tableName);

    public List<Unit_Price> unitPriceRecords(String start_time, String tableName);

    public int updateUnit_Price(Unit_Price unit_price,String tableName);

    public int deleteUnit_PriceById(Long id, String tableName);

    public int updatePowerUnit_Price(Unit_Price unit_price);

    public List<Unit_Price> powerUnit_PriceRecords(String start_time, String tableName);

    public List<Unit_Price> selectPowerUnit_Price();

    public int deletePowerUnit_Price(Long id);

    public Long getIDFromPowerUnit_Price(String start_time);

    public int addCapacity(Contractual_capacity capacity);
}
