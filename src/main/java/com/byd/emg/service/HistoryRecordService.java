package com.byd.emg.service;

import com.byd.emg.pojo.Tb_analysis;
import com.byd.emg.pojo.TimeJob;

import java.util.List;
import java.util.Map;


public interface HistoryRecordService {

    public List<String[]>  getHistoryRecordByTagname(List<String> cabinet_number, String type, String startTime, String endTime, String startDate, String endDate, String cycle, String showValue, String changing);

    public List< List<TimeJob>> getmaxbymin(List<String> cabinet_number, String type, String startTime, String endTime, String startDate, String endDate, String cycle, String showValue, String changing);

    public List<List<TimeJob>> getminbymin(List<String> cabinet_number, String type, String startTime, String endTime, String startDate, String endDate, String cycle, String showValue,String changing);

    Map<String, Object> exportExcelData(List<String> cabinet_number, String type, String startTime, String endTime, String startDate, String endDate, String cycle, String showValue, String changing,String tableName);

    public List<Tb_analysis>  getHistoryRecordAnalysis(String cabinet_number, String type, List<String> his_Date);

}
