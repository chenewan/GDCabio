package com.byd.emg.mapper;

import com.byd.emg.pojo.EquipmentStates;
import com.byd.emg.pojo.RealValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RealTimeValueMapper {

    //查询所有仪表盘的实时值
    public List<RealValue> findRealTimeValueAll();
    //根据tagname查询所有的实时值
    public List<RealValue> findRealTimeValueByTagname(RealValue realTimeValue);

    public List<RealValue> findRealTimeValueByTagnameList(@Param("tagnameList") List<String> tagnameList);

    public List<RealValue> selectByTagnameList(@Param("tagnameList") List<String> tagnames,@Param("tableName")  String tableName);

    public List<RealValue> getRealValueList(@Param("equipmentStatesList")List<EquipmentStates> equipmentStatesList);
}
