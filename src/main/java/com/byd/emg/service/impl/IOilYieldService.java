package com.byd.emg.service.impl;

import com.byd.emg.mapper.OilYieldMapper;
import com.byd.emg.pojo.OilYield;
import com.byd.emg.service.OilYieldService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myYieldService")
public class IOilYieldService implements OilYieldService {

    @Autowired
    private OilYieldMapper oilYieldMapper;


    @Override
    public int insertOilYield(OilYield oilYield,String tableName) {
        int resultCount=0;
        List<Integer> ids=oilYieldMapper.selectByTimes(oilYield,tableName);
        if(ids.size()>0){
            resultCount=oilYieldMapper.batchUpdateByIds(ids,oilYield,tableName);
        }else{
            resultCount=oilYieldMapper.insertOilYield(oilYield,tableName);
        }
        return resultCount;
    }

    @Override
    public List<OilYield> selectByDate(String date,String type,String tableName) {
        //默认显示前5条
        String topMethod="top 5";
        if(!StringUtils.isEmpty(date)) topMethod="";
        return oilYieldMapper.selectByDate(date,type,topMethod,tableName);
    }

    @Override
    public int updateOilYield(OilYield oilYield, String tableName) {
        return oilYieldMapper.updateOilYield(oilYield,tableName);
    }

    @Override
    public int deleteOilById(Long id,String tableName) {
        return oilYieldMapper.deleteOilById(id,tableName);
    }
}
