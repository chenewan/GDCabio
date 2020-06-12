package com.byd.emg.service;


import com.byd.emg.pojo.GjtYield;
import com.byd.emg.resultData.GjtEnergyData;
import com.byd.emg.resultData.UnitCostData;
import com.byd.emg.resultData.UnitEnergyData;

import java.util.List;

public interface GjtYieldService {

    public int insertGjtYield(GjtYield gjtYield);

    public List<GjtEnergyData> energyData(String startDate, String endDate, String productType, String media);

    public List<UnitEnergyData> unitEnergyData(String media);

    public List<UnitCostData> unitCost(String media);

    public List<GjtYield> selectByProductType(String date, String productType);

    public int updateGjtYield(GjtYield gjtYield);

    public int deleteGjtById(Long id);

    public Double getProductionDays(String startDate, String endDate, String type, String tableName);
}
