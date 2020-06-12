package com.byd.emg.service;

import com.byd.emg.resultData.ReportHistoryData;

import java.util.List;

public interface ShowReportService {

    public List<ReportHistoryData> getReportHistory(String types,String start_time,String end_time);
}
