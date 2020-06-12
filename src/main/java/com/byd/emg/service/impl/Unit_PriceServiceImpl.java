package com.byd.emg.service.impl;

import com.byd.emg.mapper.ElectricityPriceStatementMapper;
import com.byd.emg.mapper.Unit_PriceMapper;
import com.byd.emg.pojo.Contractual_capacity;
import com.byd.emg.pojo.Unit_Price;
import com.byd.emg.service.Unit_PriceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("Unit_PriceService")
public class Unit_PriceServiceImpl implements Unit_PriceService {


    @Autowired
    private Unit_PriceMapper unit_priceMapper;

    @Autowired
    private ElectricityPriceStatementMapper electricityPriceStatementMapper;

    @Override
    public List<Unit_Price> getPowerUnit_Price() {

        return unit_priceMapper.getPowerUnit_Price();
    }

    @Override
    public List<Unit_Price> getWaterUnit_Price() {
        return unit_priceMapper.getWaterUnit_Price();
    }

    @Override
    public List<Unit_Price> getSteamUnit_Price() {
        return unit_priceMapper.getSteamUnit_Price();
    }

    @Override
    public Unit_Price getLastUnit_Price(String tableName) {
        return unit_priceMapper.getLastUnit_Price(tableName);
    }

    @Override
    public List<Unit_Price> getCirculatWaterUnit_Price() {
        return  unit_priceMapper.getCirculatWaterUnit_Price();
    }

    @Override
    public List<Unit_Price> getFreezeWaterUnit_Price() {
        return unit_priceMapper.getFreezeWaterUnit_Price();
    }

    @Override
    public List<Unit_Price> getFermentationAirUnit_Price() {
        return unit_priceMapper.getFermentationAirUnit_Price();
    }

    @Override
    public int addPowerUnit_Price(Unit_Price unit_price) {
        int updateCount=0;
        int insertCount=0;
        List<Unit_Price> unit_priceList=unit_priceMapper.selectByTime(unit_price.getStart_time());
        if(unit_priceList.size()>0){
            for(Unit_Price unit_Price:unit_priceList){
                unit_Price.setPeacetime_electricity_price(unit_price.getPeak_electricity_price());
                unit_Price.setPeacetime_electricity_price(unit_price.getPeacetime_electricity_price());
                unit_Price.setTroughtime_electricity_price(unit_price.getTroughtime_electricity_price());
            }
            updateCount=unit_priceMapper.batchUpdateUnit_Price(unit_priceList);
        }else{
            //updateCount=unit_priceMapper.updatePowerUnit_Price_times(start_time);
            insertCount=unit_priceMapper.addPowerUnit_Price(unit_price);
        }
        return updateCount+insertCount;
    }


    @Override
    public int addWaterUnit_Price(Unit_Price unit_price) {
        int updateCount=0;
        int insertCount=0;
        String tablaName="Unit_Price_water";
        List<Unit_Price> unit_priceList=unit_priceMapper.getUnitPriceByTime(tablaName,unit_price.getStart_time());
        if(unit_priceList.size()>0){
            for(Unit_Price unit_Price:unit_priceList){
                unit_Price.setPrice(unit_price.getPrice());
            }
            updateCount=unit_priceMapper.batchUpdate(unit_priceList,tablaName);
        }else{
            //updateCount=unit_priceMapper.updateWaterUnit_Price_times(start_time);
            insertCount=unit_priceMapper.addWaterUnit_Price(unit_price.getPrice(),unit_price.getStart_time()) ;
        }
        return updateCount+insertCount;
    }

    @Override
    public int addSteamUnit_Price(Unit_Price unit_price) {
        int updateCount=0;
        int insertCount=0;
        String tablaName="Unit_Price_steam";
        List<Unit_Price> unit_priceList=unit_priceMapper.getUnitPriceByTime(tablaName,unit_price.getStart_time());
        if(unit_priceList.size()>0){
            for(Unit_Price unit_Price:unit_priceList){
                unit_Price.setPrice(unit_price.getPrice());
            }
            updateCount=unit_priceMapper.batchUpdate(unit_priceList,tablaName);
        }else{
            //updateCount=unit_priceMapper.updateSteamUnit_Price_times(start_time);
            insertCount=unit_priceMapper.addSteamUnit_Price(unit_price.getPrice(),unit_price.getStart_time()) ;
        }
        return updateCount+insertCount;
    }

    @Override
    public int addNaturalGasUnit_Price(Unit_Price unit_price) {
        int updateCount=0;
        int insertCount=0;
        String tablaName="Unit_Price_NaturalGas";
        List<Unit_Price> unit_priceList=unit_priceMapper.getUnitPriceByTime(tablaName,unit_price.getStart_time());
        if(unit_priceList.size()>0){
            for(Unit_Price unit_Price:unit_priceList){
                unit_Price.setPrice(unit_price.getPrice());
            }
            updateCount=unit_priceMapper.batchUpdate(unit_priceList,tablaName);
        }else{
            //updateCount=unit_priceMapper.updateNaturalGasUnit_Price_times(start_time);
            insertCount=unit_priceMapper.addNaturalGasUnit_Price(unit_price.getPrice(),unit_price.getStart_time()) ;
        }
        return updateCount+insertCount;
    }

    @Override
    public int addCirculatingWaterUnit_Price(Unit_Price unit_price) {
        int updateCount=0;
        int insertCount=0;
        String tablaName="Unit_Price_CirculatingWater";
        List<Unit_Price> unit_priceList=unit_priceMapper.getUnitPriceByTime(tablaName,unit_price.getStart_time());
        if(unit_priceList.size()>0){
            for(Unit_Price unit_Price:unit_priceList){
                unit_Price.setPrice(unit_price.getPrice());
            }
            updateCount=unit_priceMapper.batchUpdate(unit_priceList,tablaName);
        }else{
            //updateCount=unit_priceMapper.updateCirculatingWaterUnit_Price_times(start_time);
            insertCount=unit_priceMapper.addCirculatingWaterUnit_Price(unit_price.getPrice(),unit_price.getStart_time()) ;
        }
        return updateCount+insertCount;
    }

    @Override
    public int addFreezeWaterUnit_Price(Unit_Price unit_price) {
        int updateCount=0;
        int insertCount=0;
        String tablaName="Unit_Price_freezeWater";
        List<Unit_Price> unit_priceList=unit_priceMapper.getUnitPriceByTime(tablaName,unit_price.getStart_time());
        if(unit_priceList.size()>0){
            for(Unit_Price unit_Price:unit_priceList){
                unit_Price.setPrice(unit_price.getPrice());
            }
            updateCount=unit_priceMapper.batchUpdate(unit_priceList,tablaName);
        }else{
            //updateCount=unit_priceMapper.updateFreezeWaterUnit_Price(start_time);
            insertCount=unit_priceMapper.addFreezeWaterUnit_Price(unit_price.getPrice(),unit_price.getStart_time()) ;
        }
        return updateCount+insertCount;
    }

    @Override
    public int addFermentationAirUnit_Price(Unit_Price unit_price) {
        int updateCount=0;
        int insertCount=0;
        String tablaName="Unit_Price__fermentationAir";
        List<Unit_Price> unit_priceList=unit_priceMapper.getUnitPriceByTime(tablaName,unit_price.getStart_time());
        if(unit_priceList.size()>0){
            for(Unit_Price unit_Price:unit_priceList){
                unit_Price.setPrice(unit_price.getPrice());
            }
            updateCount=unit_priceMapper.batchUpdate(unit_priceList,tablaName);
        }else{
            //updateCount=unit_priceMapper.updateFermentationAirUnit_Price_times(start_time);
            insertCount=unit_priceMapper.addFermentationAirUnit_Price(unit_price.getPrice(),unit_price.getStart_time()) ;
        }
        return updateCount+insertCount;
    }

    @Override
    public Double selectUnitPrice(String strtDate, String endDate, String tableName) {
        return unit_priceMapper.selectUnitPrice(strtDate,endDate,tableName);
    }

    @Override
    public void insertComprehensivePricepower(String unitPricePower, String date) {
        unit_priceMapper.insertComprehensivePricepower(unitPricePower,date);
    }

    @Override
    public Double getUnitPrice(String start_t, String end_t,String tableName) {
        return unit_priceMapper.getUnitPrice(start_t,end_t,tableName);
    }

    @Override
    public List<Unit_Price> unitPriceRecords(String start_time, String tableName) {
        return unit_priceMapper.unitPriceRecords(start_time,tableName);
    }

    @Override
    public int updateUnit_Price(Unit_Price unit_price,String tableName) {
        return unit_priceMapper.updateUnit_Price(unit_price,tableName);
    }

    @Override
    public int deleteUnit_PriceById(Long id, String tableName) {
        return unit_priceMapper.deleteUnit_PriceById(id,tableName);
    }

    @Override
    public int updatePowerUnit_Price(Unit_Price unit_price) {
        return unit_priceMapper.updatePowerUnit_Price(unit_price);
    }

    @Override
    public List<Unit_Price> powerUnit_PriceRecords(String start_time, String tableName) {
        return unit_priceMapper.powerUnit_PriceRecords(start_time,tableName);
    }

    @Override
    public List<Unit_Price> selectPowerUnit_Price() {
        return unit_priceMapper.selectPowerUnit_Price();
    }

    @Override
    public int deletePowerUnit_Price(Long id) {
        return unit_priceMapper.deletePowerUnit_Price(id);
    }

    @Override
    public Long getIDFromPowerUnit_Price(String start_time) {
        return unit_priceMapper.getIDFromPowerUnit_Price(start_time);
    }

    @Override
    public int addCapacity(Contractual_capacity capacity) {
        int resultCount=0;
        Contractual_capacity capacityDb=electricityPriceStatementMapper.selectByCapacity(capacity);
        if(capacityDb!=null){
            capacity.setId(capacityDb.getId());
            resultCount=electricityPriceStatementMapper.updateCapacityById(capacity);
        }else{
            resultCount=electricityPriceStatementMapper.insertCapacity(capacity);
        }
        return resultCount;
    }
}
