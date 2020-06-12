package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.ResourceMeter;
import com.byd.emg.pojo.ResultPageData;
import com.byd.emg.service.DataMonitoringDashboardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/energyMeterPage")
public class DataMonitoringDashboardController {

    @Autowired
    private DataMonitoringDashboardService dataMonitoringDashboardService;

    @RequestMapping("/energyMeterPageList")
    @ResponseBody
    public ServerResponse<PageInfo> EnergyMeterPagination(@RequestParam(value = "pageNum") int pageNum,
                                                          @RequestParam(value = "pageSize") int pageSize,
                                                          @RequestParam(value = "Search_parameters") String Search_parameters,
                                                          @RequestParam(value = "cabinet_number")List<String> cabinet_number
    ){


        System.out.print(pageNum+',');
        System.out.print(pageSize+',');

        //分页
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ResultPageData> pageResult = new PageInfo<ResultPageData>();
        //获取分页数据
        List<ResultPageData> energyMeterPageList= dataMonitoringDashboardService.getPageData(pageNum,pageSize,pageResult,Search_parameters,cabinet_number);


        if(energyMeterPageList.size()>0){
            pageResult.setList(energyMeterPageList);

            return  ServerResponse.createBySuccess("能源仪表分页成功",pageResult);
        }else{

            return ServerResponse.createByErrorMessage("能源仪表分页失败");
        }
    }


    @RequestMapping("/energyMeterFindTagName")
    @ResponseBody

    public ServerResponse<List<String>> findTagname(){
        List<String> tag =  dataMonitoringDashboardService.findTagname();
        return  ServerResponse.createBySuccess("返回所有tag成功",tag);
    }

    @RequestMapping("/energyMeterFindDescribe")
    @ResponseBody

    public ServerResponse< List<ResourceMeter>> findDescribe(@RequestParam(value = "tagname")String tagname ){
        List<ResourceMeter> Describe =  dataMonitoringDashboardService.findDescribe( tagname);
        return  ServerResponse.createBySuccess("返回所有中文描述成功",Describe);
    }
}
