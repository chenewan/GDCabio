package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.RealValue;
import com.byd.emg.resultData.EquipmentData;
import com.byd.emg.resultData.EquipmentParameterData;
import com.byd.emg.service.EquipmentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//设备管理的控制类
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    //向前端返回设备参数的方法
    @RequestMapping("/getParameterData")
    public ServerResponse getParameterData(@RequestParam("tagnames") List<String> tagnames,@RequestParam("media")String media){
        Object equipmentParameterData=equipmentService.getParameterData(tagnames,media);
        if (equipmentParameterData!=null) {
            return ServerResponse.createBySuccess("查询设备参数成功", equipmentParameterData);
        }
        return ServerResponse.createByErrorMessage("查询设备参数失败");
    }

    //设备分析
    @RequestMapping("/equipmentAnalysis")
    public ServerResponse equipmentAnalysis(@RequestParam("tankNumbers") List<String> tankNumbers,
                                            @RequestParam("media") String media,
                                            @RequestParam("Start_time_1") String Start_time_1,
                                            @RequestParam("End_time_1") String End_time_1,
                                            @RequestParam("Start_time_2") String Start_time_2,
                                            @RequestParam("End_time_2") String End_time_2){
        //查询数据库的最大时间和最小时间
        String maxDateDb=equipmentService.selectEquipmentRangeDate("max(startTime)");
        String minDateDb=equipmentService.selectEquipmentRangeDate("min(startTime)");
        List<EquipmentData> resultList=equipmentService.equipmentAnalysis(tankNumbers,media,Start_time_1,End_time_1,Start_time_2,End_time_2,maxDateDb,minDateDb);
        if(resultList==null){
            return ServerResponse.createByErrorMessage("该系统存储的数据的起始时间为:"+minDateDb+"至"+maxDateDb);
        }
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功", resultList);
        }
        return ServerResponse.createByErrorMessage("查询设备参数失败");
    }

    //设备分析柱状图
    @RequestMapping("/equipmentAnalysisBar")
    public ServerResponse equipmentAnalysisBar(@RequestParam("tankNumbers") List<String> tankNumbers,
                                            String media,
                                            String Start_time_1,
                                            String End_time_1,
                                            String Start_time_2,
                                            String End_time_2){
        //查询数据库的最大时间和最小时间
        String maxDateDb=equipmentService.selectEquipmentRangeDate("max(startTime)");
        String minDateDb=equipmentService.selectEquipmentRangeDate("min(startTime)");
        Map<String,List<String[]>> resultList=equipmentService.equipmentAnalysisBar(tankNumbers,media,Start_time_1,End_time_1,Start_time_2,End_time_2,maxDateDb,minDateDb);
        if(resultList==null){
            return ServerResponse.createByErrorMessage("该系统存储的数据的起始时间为:"+minDateDb+"至"+maxDateDb);
        }
        if(resultList.size()>0){
            return ServerResponse.createBySuccess("查询成功", resultList);
        }
        return ServerResponse.createByErrorMessage("查询设备参数失败");
    }




}
