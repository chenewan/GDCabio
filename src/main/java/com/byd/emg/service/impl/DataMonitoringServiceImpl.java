package com.byd.emg.service.impl;


import com.byd.emg.mapper.DataMonitoringMapper;
import com.byd.emg.pojo.*;
import com.byd.emg.service.DataMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("DataMonitoringService")
public class DataMonitoringServiceImpl implements DataMonitoringService {

    @Autowired
    private DataMonitoringMapper dataMonitoringMapper;

    @Override
    public List<DataMonitoring_Power> AllDataByTzDataMonitoring(List<String> cabinet_number){
        List<DataMonitoring_Power> realTimeValueList=new ArrayList<>();
        if (cabinet_number.size()>0){
            realTimeValueList= dataMonitoringMapper.AllDataByTzDataMonitoring(cabinet_number);
        }
        return realTimeValueList;
    }

    @Override
    //柜体编号和柜体名称映射关系的集合
    public Map<String, String> cabinetnumberToName() {
        Map<String,String> resultMap=new HashMap<String,String>();
        List<DataMonitoring_Power> tzDataMonitoringList=dataMonitoringMapper.cabinetnumberToName();
        for(DataMonitoring_Power tzDataMonitoring:tzDataMonitoringList){
            resultMap.put(tzDataMonitoring.getCabinet_number(),tzDataMonitoring.getCabinet_name());
        }
        return resultMap;
    }

    @Override
    public List<DataMonitoring_freezeWater> AllDataByTzDataMonitoring_freezeWater(List<String> cabinet_number) {
        List<DataMonitoring_freezeWater> realTimeValueList=new ArrayList<>();
        if (cabinet_number.size()>0){
            realTimeValueList= dataMonitoringMapper.AllDataByTzDataMonitoring_freezeWater(cabinet_number);
        }
        return realTimeValueList;
    }



    @Override
    public List<DataMonitoring_circulatingWate> AllDataByTzDataMonitoring_circulatingWate(List<String> cabinet_number) {
        List<DataMonitoring_circulatingWate> realTimeValueList=new ArrayList<>();
        if (cabinet_number.size()>0){
            realTimeValueList= dataMonitoringMapper.AllDataByTzDataMonitoring_circulatingWate(cabinet_number);
        }
        return realTimeValueList;
    }


    @Override
    public List<DataMonitoring_tapWater> AllDataByTzDataMonitoring_tapWater(List<String> cabinet_number) {
        List<DataMonitoring_tapWater> realTimeValueList=new ArrayList<>();
        if (cabinet_number.size()>0){
            realTimeValueList= dataMonitoringMapper.AllDataByTzDataMonitoring_tapWater(cabinet_number);
        }
        return realTimeValueList;
    }

    @Override
    public List<DataMonitoring_fermentationAir> AllDataByTzDataMonitoring_fermentationAir(List<String> cabinet_number) {
        List<DataMonitoring_fermentationAir> realTimeValueList=new ArrayList<>();
        if (cabinet_number.size()>0){
            realTimeValueList= dataMonitoringMapper.AllDataByTzDataMonitoring_fermentationAir(cabinet_number);
        }
        return realTimeValueList;
    }



    @Override
    public List<DataMonitoring_steamMeter> AllDataByTzDataMonitoring_steamMeter(List<String> cabinet_number) {
        List<DataMonitoring_steamMeter> realTimeValueList=new ArrayList<>();
        if (cabinet_number.size()>0){
            realTimeValueList= dataMonitoringMapper.AllDataByTzDataMonitoring_steamMeter(cabinet_number);
        }
        return realTimeValueList;
    }

}
