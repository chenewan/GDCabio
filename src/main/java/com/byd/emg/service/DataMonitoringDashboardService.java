package com.byd.emg.service;

import com.byd.emg.pojo.ResourceMeter;
import com.byd.emg.pojo.ResultPageData;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DataMonitoringDashboardService {

    public List<ResourceMeter> findEnergyMeterPageAll(String Search_parameters, List<String> cabinet_number);

    public List getPageData(int pageNum, int pageSize, PageInfo<ResultPageData> pageResult, String Search_parameters, List<String> cabinet_number);


    public List<String>   findTagname();

    public List<ResourceMeter> findDescribe(String tagname);
}
