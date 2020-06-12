package com.byd.emg.service.impl;

import com.byd.emg.mapper.RatedGasProductionMapper;
import com.byd.emg.pojo.RatedGasProduction;
import com.byd.emg.service.RatedGasProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ratedGasProductionService")
public class RatedGasProductionServiceImpl implements RatedGasProductionService {

    @Autowired
    private RatedGasProductionMapper ratedGasProductionMapper;

    @Override
    public Map<String, String> getratedGas() {
        Map<String, String> resultMap=new HashMap<String, String>();
        List<RatedGasProduction> ratedGasProductionList=ratedGasProductionMapper.getratedGas();
        for(RatedGasProduction ratedGasProduction:ratedGasProductionList){
            resultMap.put(ratedGasProduction.getTagName(),ratedGasProduction.getRatedValue());
        }
        return resultMap;
    }
}
