package com.byd.emg.mapper;

import com.byd.emg.pojo.EquipmentStates;
import com.byd.emg.resultData.EquipmentData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquipmentMapper {

    public String selectEquipmentRangeDate(@Param("method") String method);

    public List<EquipmentData> getDataByTimes(@Param("tankNumbers") List<String> tankNumbers,@Param("condition") String condition,@Param("start_time_1") String start_time_1,@Param("end_time_1") String end_time_1);

    public EquipmentStates getEquipmentStates(@Param("media") String media);

    public List<Double> getActiveEnergy(@Param("tableName") String tableName, @Param("tagName") String tagName);

    public List<EquipmentStates> getEquipmentStatesAll();

    public void batchUpdataEquipmentStates(@Param("equipmentStatesList") List<EquipmentStates> equipmentStatesList,@Param("time") String time);

    public void batchUpdateOpenTimes(@Param("equipmentStatesList")List<EquipmentStates> equipmentStatesList);

}
