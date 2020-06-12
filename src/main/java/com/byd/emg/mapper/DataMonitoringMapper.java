package com.byd.emg.mapper;


import com.byd.emg.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataMonitoringMapper {

    public List<DataMonitoring_Power> AllDataByTzDataMonitoring(@Param("cabinet_number") List<String> cabinet_number);

    public List<DataMonitoring_Power> cabinetnumberToName();
    //电

    public List<DataMonitoring_freezeWater> AllDataByTzDataMonitoring_freezeWater(@Param("cabinet_number") List<String> cabinet_number);

    public List<DataMonitoring_freezeWater> cabinetnumberToName_freezeWater();
    //冷冻水

    public List<DataMonitoring_circulatingWate> AllDataByTzDataMonitoring_circulatingWate(@Param("cabinet_number") List<String> cabinet_number);

    public List<DataMonitoring_circulatingWate> cabinetnumberToName_circulatingWate();
    //循环水
    public List<DataMonitoring_tapWater> AllDataByTzDataMonitoring_tapWater(@Param("cabinet_number") List<String> cabinet_number);

    public List<DataMonitoring_tapWater> cabinetnumberToName_tapWater();
    //自来水

    public List<DataMonitoring_fermentationAir> AllDataByTzDataMonitoring_fermentationAir(@Param("cabinet_number") List<String> cabinet_number);

    public List<DataMonitoring_fermentationAir> cabinetnumberToName_fermentationAir();
    //压缩空气

    public List<DataMonitoring_steamMeter> AllDataByTzDataMonitoring_steamMeter(@Param("cabinet_number") List<String> cabinet_number);

    public List<DataMonitoring_steamMeter> cabinetnumberToName_steamMeter();
    //蒸汽

   }
