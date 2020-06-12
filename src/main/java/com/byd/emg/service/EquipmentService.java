package com.byd.emg.service;

import com.byd.emg.pojo.EquipmentStates;
import com.byd.emg.pojo.RealValue;
import com.byd.emg.resultData.EquipmentData;
import com.byd.emg.resultData.EquipmentParameterData;

import java.util.List;
import java.util.Map;

public interface EquipmentService {

    public Object getParameterData(List<String> tagnames,String media);

    public List<EquipmentData> equipmentAnalysis(List<String> tankNumbers, String media, String start_time_1, String end_time_1, String start_time_2, String end_time_2,String maxDateDb,String minDateDb);

    public Map<String,List<String[]>> equipmentAnalysisBar(List<String> tankNumbers, String media, String start_time_1, String end_time_1, String start_time_2, String end_time_2, String maxDateDb, String minDateDb);

    public String selectEquipmentRangeDate(String method);

    public List<EquipmentStates> getEquipmentStatesAll();

    public void batchUpdataEquipmentStates(List<EquipmentStates> equipmentStatesList,String time);
}
