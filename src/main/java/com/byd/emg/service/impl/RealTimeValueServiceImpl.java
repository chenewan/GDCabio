package com.byd.emg.service.impl;

import com.byd.emg.mapper.RealTimeValueMapper;
import com.byd.emg.pojo.EquipmentStates;
import com.byd.emg.pojo.RealValue;
import com.byd.emg.service.RealTimeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("realTimeValueService")
public class RealTimeValueServiceImpl implements RealTimeValueService {

    @Autowired
    private RealTimeValueMapper realTimeValueMapper;

    //查询所有仪表盘的实时值(eal_tz_dashboard表与real_time_value表关联的所有数据)
    public List<RealValue> findRealTimeValueAll() {
        return realTimeValueMapper.findRealTimeValueAll();
    }

    //根据tagname查询所有的实时值
    public List<RealValue> findRealTimeValueByTagname(String tagname) {
        RealValue realTimeValue=new RealValue();
        realTimeValue.setRealValue_tagname(tagname);

        return realTimeValueMapper.findRealTimeValueByTagname(realTimeValue);
    }

    @Override
    public List<RealValue> getRealValueList(List<EquipmentStates> equipmentStatesList) {
        return realTimeValueMapper.getRealValueList(equipmentStatesList);
    }
}

