package com.byd.emg.service.impl;

import com.byd.emg.mapper.DataMonitoringDashboardMapper;
import com.byd.emg.mapper.RealTimeValueMapper;
import com.byd.emg.pojo.RealValue;
import com.byd.emg.pojo.ResourceMeter;
import com.byd.emg.pojo.ResultPageData;
import com.byd.emg.service.DataMonitoringDashboardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("DataMonitoringDashboardService")
public class DataMonitoringDashboardServiceImpl implements DataMonitoringDashboardService {
    @Autowired
    private DataMonitoringDashboardMapper dataMonitoringDashboardMapper;

    @Autowired
    private RealTimeValueMapper realTimeValueMapper;



    public List<ResourceMeter> findEnergyMeterPageAll(String Search_parameters, List<String> cabinet_number) {
        return dataMonitoringDashboardMapper.findEnergyMeterPageAll(Search_parameters,cabinet_number);
    }


    public List getPageData(int pageNum, int pageSize, PageInfo<ResultPageData> pageResult, String Search_parameters, List<String> cabinet_number) {
        List<ResultPageData> resultList=new ArrayList();
        //real_tz_dashboard表与real_tz_original_table表关联的所有数据
        List<ResourceMeter> resourceMeterList=new ArrayList<>();

        if (cabinet_number.size()>0) {
            PageHelper.startPage(pageNum,pageSize);
            resourceMeterList = this.findEnergyMeterPageAll(Search_parameters, cabinet_number);
            PageInfo pageInfo=new PageInfo(resourceMeterList);
            pageResult.setTotal(pageInfo.getTotal());
        }
        //real_tz_dashboard表与real_time_value表关联的所有数据
        List<String> tagnameList=new ArrayList<String>();
        for (ResourceMeter resourceMeter : resourceMeterList) {
            tagnameList.add(resourceMeter.getTagname());
        }
        List<RealValue> realTimeValueList=new ArrayList<>();
        if (tagnameList.size()>0){
            realTimeValueList=realTimeValueMapper.findRealTimeValueByTagnameList(tagnameList);
        }
        resultList=this.mergeList(realTimeValueList,resourceMeterList);
        return resultList;
    }
    public List<ResultPageData> mergeList(List<RealValue> realTimeValueList,List<ResourceMeter> resourceMeterList){
        List<ResultPageData> resultList=new ArrayList();
        //将两次关联的数据整合到一个集合中
        if(resourceMeterList.size()==realTimeValueList.size()) {
            for (int i = 0; i < resourceMeterList.size(); i++) {
                ResultPageData resultPageData = new ResultPageData();
                //ResourceMeter
                resultPageData.setNum(resourceMeterList.get(i).getNum());
                resultPageData.setTagname(resourceMeterList.get(i).getTagname());
                resultPageData.setMaxValue(resourceMeterList.get(i).getMaxValue());
                resultPageData.setMinValue(resourceMeterList.get(i).getMinValue());
                resultPageData.setDescribe(resourceMeterList.get(i).getDescribe());
                resultPageData.setArea(resourceMeterList.get(i).getArea());
                resultPageData.setWorkShop(resourceMeterList.get(i).getWorkShop());
                resultPageData.setUnitSymbol(resourceMeterList.get(i).getUnitSymbol());
                resultPageData.setType(resourceMeterList.get(i).getType());
                //EnergyMeterPage
                resultPageData.setName_display(resourceMeterList.get(i).getDataMonitoringDashboard().getName_display());
                resultPageData.setCustom_name(resourceMeterList.get(i).getDataMonitoringDashboard().getCustomname());
                resultPageData.setUpperLimit(resourceMeterList.get(i).getDataMonitoringDashboard().getUpper_limit());
                resultPageData.setLowerLimit(resourceMeterList.get(i).getDataMonitoringDashboard().getLower_limit());
                resultPageData.setMonitoring_id(resourceMeterList.get(i).getDataMonitoringDashboard().getMonitoring_id());
                //RealTimeValue
                resultPageData.setRealValue_id(realTimeValueList.get(i).getRealValue_id());
                resultPageData.setRealValue(realTimeValueList.get(i).getRealValue());
                resultPageData.setTime(realTimeValueList.get(i).getTime());
                resultList.add(resultPageData);
            }
        }
        return resultList;
    }
    @Override
    public List<String> findTagname() {
        return dataMonitoringDashboardMapper.findTagname();
    }

    @Override
    public List<ResourceMeter> findDescribe(String tagname) {
        return dataMonitoringDashboardMapper.findDescribe(tagname);
    }
}
