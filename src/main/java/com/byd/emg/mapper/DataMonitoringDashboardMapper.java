package com.byd.emg.mapper;

import com.byd.emg.pojo.ResourceMeter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataMonitoringDashboardMapper {
    public List<ResourceMeter> findEnergyMeterPageAll(@Param("Search_parameters") String Search_parameters, @Param("cabinet_number") List<String> cabinet_number);

    public List<String>   findTagname();

    public List<ResourceMeter> findDescribe(@Param ("tagname") String tagname);



}
