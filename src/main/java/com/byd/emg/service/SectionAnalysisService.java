package com.byd.emg.service;

import com.byd.emg.pojo.SectionAnalysis;
import com.byd.emg.resultData.SankeyData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SectionAnalysisService {

    public List< List<SectionAnalysis>> getsectionAnalysis ( List<String> Section, String Start_time_1, String End_time_1,String Start_time_2,String End_time_2,String state,String maxDateDb,String minDateDb);

    public List<String[]> getsectionAnalysis_Histogram ( List<String> Section, String Start_time_1, String End_time_1,String Start_time_2,String End_time_2,String medium,String maxDateDb,String minDateDb);

    public double wushui_power(String Start_t,String End_t);

    public List<Map<String,String>> gongduan_power(String Start_t,String End_t);

    public List<SectionAnalysis> sectionAnalysis(String Start_t,String End_t);

    public List<SectionAnalysis> getHomePageData(List<String> sections);

    public List<SankeyData> getSankeyData(List<String> sections, String medium);

    public void calculateSankeyData();

    public String selectSankeyRangeDate(String method);

    public Map<String,Double> getConsumptionValue(List<String> tagnameList,String startDate,String endDate,String realMark,String hisTablename,String realTablename);

}
