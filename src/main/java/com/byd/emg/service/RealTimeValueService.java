package com.byd.emg.service;

import com.byd.emg.pojo.EquipmentStates;
import com.byd.emg.pojo.RealValue;

import java.util.List;

public interface RealTimeValueService {

    public List<RealValue> findRealTimeValueAll();

    public List<RealValue> findRealTimeValueByTagname(String tagname);

    public List<RealValue> getRealValueList(List<EquipmentStates> equipmentStatesList);
}
