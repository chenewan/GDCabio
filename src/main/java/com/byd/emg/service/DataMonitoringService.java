package com.byd.emg.service;

import com.byd.emg.pojo.*;


import java.util.List;
import java.util.Map;

public interface DataMonitoringService {
    public List<DataMonitoring_Power> AllDataByTzDataMonitoring(List<String> cabinet_number);

    public Map<String, String> cabinetnumberToName();

    public List<DataMonitoring_freezeWater> AllDataByTzDataMonitoring_freezeWater(  List<String> cabinet_number);


    public List<DataMonitoring_circulatingWate> AllDataByTzDataMonitoring_circulatingWate(  List<String> cabinet_number);


    public List<DataMonitoring_tapWater> AllDataByTzDataMonitoring_tapWater(  List<String> cabinet_number);



    public List<DataMonitoring_fermentationAir> AllDataByTzDataMonitoring_fermentationAir(  List<String> cabinet_number);



    public List<DataMonitoring_steamMeter> AllDataByTzDataMonitoring_steamMeter(  List<String> cabinet_number);


}
