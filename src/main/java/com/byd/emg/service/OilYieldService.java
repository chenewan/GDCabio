package com.byd.emg.service;

import com.byd.emg.pojo.OilYield;

import java.util.List;

public interface OilYieldService {

    public int insertOilYield(OilYield oilYield,String tableName);

    public List<OilYield> selectByDate(String date,String type,String tableName);

    public int updateOilYield(OilYield oilYield, String tableName);

    public int deleteOilById(Long id,String tableName);
}
