package com.byd.emg.controller;


import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.*;
import com.byd.emg.service.DataMonitoringDashboardService;
import com.byd.emg.service.DataMonitoringService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/TzDataMonitoring")
public class DataMonitoringController {

    @Autowired
    private DataMonitoringService dataMonitoringService;
    @RequestMapping("/DataMonitoring_Power")
    @ResponseBody
    public ServerResponse<PageInfo<DataMonitoring_Power>> getPowerPageData_power(@RequestParam(value = "pageNum") int pageNum,
                                                                                 @RequestParam(value = "pageSize") int pageSize,
                                                                                 @RequestParam(value = "cabinet_number") List<String> cabinet_number
    ){
        PageHelper.startPage(pageNum,pageSize);
        List<DataMonitoring_Power> tdm= dataMonitoringService.AllDataByTzDataMonitoring(cabinet_number);
        PageInfo<DataMonitoring_Power> pageResult = new PageInfo<DataMonitoring_Power>(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("电表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createByErrorMessage("电表监控实时查询失败");
        }
    }

    @RequestMapping("/DataMonitoring_freezeWater")
    @ResponseBody
    public ServerResponse<PageInfo<DataMonitoring_freezeWater>> AllDataByTzDataMonitoring_freezeWater(@RequestParam(value = "pageNum") int pageNum,
                                                                                                      @RequestParam(value = "pageSize") int pageSize,
                                                                                                      @RequestParam(value = "cabinet_number") List<String> cabinet_number
    ){
        PageHelper.startPage(pageNum,pageSize);
        List<DataMonitoring_freezeWater> tdm= dataMonitoringService.AllDataByTzDataMonitoring_freezeWater(cabinet_number);
        PageInfo<DataMonitoring_freezeWater> pageResult = new PageInfo<DataMonitoring_freezeWater>(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("冷冻水表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createByErrorMessage("冷冻水表监控实时查询失败");
        }
    }

    @RequestMapping("/DataMonitoring_circulatingWate")
    @ResponseBody
    public ServerResponse<PageInfo<DataMonitoring_circulatingWate>> AllDataByTzDataMonitoring_circulatingWate(@RequestParam(value = "pageNum") int pageNum,
                                                                                                              @RequestParam(value = "pageSize") int pageSize,
                                                                                                              @RequestParam(value = "cabinet_number") List<String> cabinet_number
    ){
        PageHelper.startPage(pageNum,pageSize);
        List<DataMonitoring_circulatingWate> tdm= dataMonitoringService.AllDataByTzDataMonitoring_circulatingWate(cabinet_number);
        PageInfo<DataMonitoring_circulatingWate> pageResult = new PageInfo<DataMonitoring_circulatingWate>(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("循环水表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createByErrorMessage("循环水表监控实时查询失败");
        }
    }

    @RequestMapping("/DataMonitoring_tapWater")
    @ResponseBody
    public ServerResponse<PageInfo<DataMonitoring_tapWater>> AllDataByTzDataMonitoring_tapWater(@RequestParam(value = "pageNum") int pageNum,
                                                                                                @RequestParam(value = "pageSize") int pageSize,
                                                                                                @RequestParam(value = "cabinet_number") List<String> cabinet_number
    ){
        PageHelper.startPage(pageNum,pageSize);
        List<DataMonitoring_tapWater> tdm= dataMonitoringService.AllDataByTzDataMonitoring_tapWater(cabinet_number);
        PageInfo<DataMonitoring_tapWater> pageResult = new PageInfo<DataMonitoring_tapWater>(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("自来水表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createByErrorMessage("自来水表监控实时查询失败");
        }
    }

    @RequestMapping("/DataMonitoring_fermentationAir")
    @ResponseBody
    public ServerResponse<PageInfo<DataMonitoring_fermentationAir>> AllDataByTzDataMonitoring_fermentationAir(@RequestParam(value = "pageNum") int pageNum,
                                                                                                              @RequestParam(value = "pageSize") int pageSize,
                                                                                                              @RequestParam(value = "cabinet_number") List<String> cabinet_number
    ){
        PageHelper.startPage(pageNum,pageSize);
        List<DataMonitoring_fermentationAir> tdm= dataMonitoringService.AllDataByTzDataMonitoring_fermentationAir(cabinet_number);
        PageInfo<DataMonitoring_fermentationAir> pageResult = new PageInfo<DataMonitoring_fermentationAir>(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("电表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createByErrorMessage("电表监控实时查询失败");
        }
    }

    @RequestMapping("/DataMonitoring_steamMeter")
    @ResponseBody
    public ServerResponse<PageInfo<DataMonitoring_steamMeter>> AllDataByTzDataMonitoring_steamMeter(@RequestParam(value = "pageNum") int pageNum,
                                                                                 @RequestParam(value = "pageSize") int pageSize,
                                                                                 @RequestParam(value = "cabinet_number") List<String> cabinet_number
    ){
        PageHelper.startPage(pageNum,pageSize);
        List<DataMonitoring_steamMeter> tdm= dataMonitoringService.AllDataByTzDataMonitoring_steamMeter(cabinet_number);
        PageInfo<DataMonitoring_steamMeter> pageResult = new PageInfo<DataMonitoring_steamMeter>(tdm);
        if(tdm.size()>0){
            return  ServerResponse.createBySuccess("电表监控实时查询成功",pageResult);
        }else{
            return ServerResponse.createByErrorMessage("电表监控实时查询失败");
        }
    }

}
