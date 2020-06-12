package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.Tb_analysis;
import com.byd.emg.pojo.TimeJob;
import com.byd.emg.service.HistoryRecordService;
import com.byd.emg.service.RealTimeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

//获取折线图数据的控制类
@Controller
@RequestMapping("/brokenLine")
public class BrokenLineController {

    @Autowired
    private HistoryRecordService historyRecordService;

    @Autowired
    private RealTimeValueService realTimeValueService;

    /**
     * @param changing:介质(水、电、汽)变换，默认为”电“
     * @return
     */
    @RequestMapping("/brokeLineRealValue")
    @ResponseBody
    public ServerResponse<List<String[]>> brokeLineRealValue(
            @RequestParam(value = "cycle") String cycle, //周期
            @RequestParam(value = "startTime") String startTime,
            @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "type") String type, //类型
            @RequestParam(value = "cabinet_number") List<String> cabinet_number,
            @RequestParam(value = "showValue") String showValue,
            @RequestParam(value = "changing",defaultValue = "电") String changing){
        List<String[]> historyRecordsList = historyRecordService.getHistoryRecordByTagname(cabinet_number, type, startTime, endTime, startDate, endDate, cycle,showValue,changing);
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("折线图数据获取成功", historyRecordsList);
        }
        return ServerResponse.createByErrorMessage("折线图数据获取失败");
    }

    @RequestMapping("/brokeLineRealValueAnalysis")
    @ResponseBody
    public ServerResponse brokeLineRealValueAnalysis(
            @RequestParam(value = "his_Date") List<String> his_Date,
            @RequestParam(value = "type") String type, //类型
            @RequestParam(value = "cabinet_number")String cabinet_number)
           // @RequestParam(value = "changing",defaultValue = "电") String changing)
            {
                List<Tb_analysis> historyRecordsList = historyRecordService.getHistoryRecordAnalysis(cabinet_number, type, his_Date);
        if (historyRecordsList.size()>0) {
            return ServerResponse.createBySuccess("数据同比查询成功", historyRecordsList);
        }
        return ServerResponse.createByErrorMessage("数据同比查询失败");
    }




    /**
     * @param changing:介质(水、电、汽)变换，默认为”电“
     * @return
     */
    @RequestMapping("/getminbymin")
    @ResponseBody
    public ServerResponse<List<List<TimeJob>>> getminbymin(
            @RequestParam(value = "cycle") String cycle, //周期
            @RequestParam(value = "startTime") String startTime,
            @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "type") String type, //类型
            @RequestParam(value = "cabinet_number") List<String> cabinet_number,
            @RequestParam(value = "showValue")String showValue,
            @RequestParam(value = "changing",defaultValue = "电") String changing){
        List<List<TimeJob>>DateList =historyRecordService.getminbymin(cabinet_number, type, startTime, endTime, startDate, endDate, cycle,showValue,changing);
        if (DateList.size() > 0) {
            return ServerResponse.createBySuccess("获取分钟最小值成功", DateList);
        }
        return ServerResponse.createByErrorMessage("获取分钟最小值失败");
    }

    /**
     * @param changing:介质(水、电、汽)变换，默认为”电“
     * @return
     */
    @RequestMapping("/getmaxbymin")
    @ResponseBody
    public ServerResponse<List<List<TimeJob> >> getmaxbymin(
            @RequestParam(value = "cycle") String cycle, //周期
            @RequestParam(value = "startTime") String startTime,//开始时间
            @RequestParam(value = "endTime") String endTime, //结束时间
            @RequestParam(value = "startDate") String startDate,//开始日期
            @RequestParam(value = "endDate") String endDate,//结束日期
            @RequestParam(value = "type") String type, //类型
            @RequestParam(value = "cabinet_number") List<String> cabinet_number,
            @RequestParam(value = "showValue")String showValue,
            @RequestParam(value = "changing",defaultValue = "电") String changing){

        List<List<TimeJob>> DateList =historyRecordService.getmaxbymin(cabinet_number, type, startTime, endTime, startDate, endDate, cycle,showValue,changing);

        if (DateList.size() > 0) {

            return ServerResponse.createBySuccess("获取分钟最大值成功", DateList);
        }

        return ServerResponse.createByErrorMessage("获取分钟最大值失败");
    }





}




