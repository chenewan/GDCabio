package com.byd.emg.controller;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.SectionAnalysis;
import com.byd.emg.resultData.SankeyData;
import com.byd.emg.service.SectionAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/SectionAnalysis")
public class SectionAnalysisController {

    @Autowired
    private SectionAnalysisService sectionAnalysisService;

    @RequestMapping("/homePageData")
    @ResponseBody
    public ServerResponse homePageData(@RequestParam("sections") List<String> sections)  {
        List<SectionAnalysis>  resultList=sectionAnalysisService.getHomePageData(sections);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    @RequestMapping("/test")
    @ResponseBody
    public ServerResponse test(String Start_t, String End_t)  {
        List<SectionAnalysis>  resultList=sectionAnalysisService.sectionAnalysis(Start_t,End_t);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    @RequestMapping("/sankeyData")
    @ResponseBody
    public ServerResponse sankeyData(@RequestParam("sections") List<String> sections,String medium)  {
        List<SankeyData>  resultList=sectionAnalysisService.getSankeyData(sections,medium);
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功",resultList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    @RequestMapping("/sectionAnalysis")
    @ResponseBody
    public ServerResponse<List< List<SectionAnalysis>>> sectionAnalysis(
            @RequestParam(value = "Section") List<String> Section,//工段
            @RequestParam(value = "Start_time_1") String Start_time_1,
            @RequestParam(value = "End_time_1") String End_time_1,
            @RequestParam(value = "Start_time_2") String Start_time_2,
            @RequestParam(value = "End_time_2") String End_time_2,
            @RequestParam(value = "state") String state){
        //查询数据库的最大时间和最小时间
        String maxDateDb=sectionAnalysisService.selectSankeyRangeDate("max(his_date)");
        String minDateDb=sectionAnalysisService.selectSankeyRangeDate("min(his_date)");
        List< List<SectionAnalysis>> historyRecordsList = sectionAnalysisService.getsectionAnalysis(Section,Start_time_1,End_time_1,Start_time_2,End_time_2,state,maxDateDb,minDateDb);
        if(historyRecordsList==null){
            return ServerResponse.createByErrorMessage("该系统存储的数据的起始时间为:"+minDateDb+"至"+maxDateDb);
        }
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("获取工段分析数据成功", historyRecordsList);
        }
        return ServerResponse.createByErrorMessage("获取工段分析数据失败");
    }

    @RequestMapping("/sectionAnalysis_Histogram")
    @ResponseBody
    public ServerResponse< List<String[]>> sectionAnalysis_Histogram(
            @RequestParam(value = "Section") List<String> Section,//工段
            @RequestParam(value = "Start_time_1") String Start_time_1,
            @RequestParam(value = "End_time_1") String End_time_1,
            @RequestParam(value = "Start_time_2") String Start_time_2,
            @RequestParam(value = "End_time_2") String End_time_2,
            @RequestParam(value = "medium") String medium)  {
        //查询数据库的最大时间和最小时间
        String maxDateDb=sectionAnalysisService.selectSankeyRangeDate("max(his_date)");
        String minDateDb=sectionAnalysisService.selectSankeyRangeDate("min(his_date)");
        List<String[]> historyRecordsList = sectionAnalysisService.getsectionAnalysis_Histogram(Section,Start_time_1,End_time_1,Start_time_2,End_time_2,medium,maxDateDb,minDateDb);
        if(historyRecordsList==null){
            return ServerResponse.createByErrorMessage("该系统存储的数据的起始时间为:"+minDateDb+"至"+maxDateDb);
        }
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("获取工段分析数据成功", historyRecordsList);
        }
        return ServerResponse.createByErrorMessage("获取工段分析数据失败");
    }

    @RequestMapping("/gongduan_power")
    @ResponseBody
    public List<Map<String,String>> gongduan_power(
            @RequestParam(value = "Start_t") String Start_t,
            @RequestParam(value = "End_t") String End_t)  {

        List<SectionAnalysis>  list  = sectionAnalysisService.sectionAnalysis(Start_t, End_t);
        return sectionAnalysisService.gongduan_power(Start_t, End_t);

    }

}

