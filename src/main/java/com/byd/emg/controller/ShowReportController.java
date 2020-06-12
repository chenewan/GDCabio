package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.RealValue;
import com.byd.emg.resultData.ReportHistoryData;
import com.byd.emg.service.ShowReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/showReport")
public class ShowReportController {

    @Autowired
    private ShowReportService iShowReport;

    @RequestMapping("/reportHistory")
    public ServerResponse reportHistory(String types,String start_time,String end_time){
        List<ReportHistoryData> reportHistoryList=iShowReport.getReportHistory(types,start_time,end_time);
        if (reportHistoryList.size() > 0) {
            return ServerResponse.createBySuccess("查询成功", reportHistoryList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }


}
