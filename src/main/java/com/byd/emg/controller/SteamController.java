package com.byd.emg.controller;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.SectionAnalysis;
import com.byd.emg.pojo.Steam;
import com.byd.emg.service.TimeJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/steam")
public class SteamController {

    @Autowired
    private TimeJobService timeJobService;

    @RequestMapping("/addSteam")
    public ServerResponse addSteam(Steam steam){
        int insertCount=timeJobService.addSteam(steam);
        if(insertCount>0){
            return ServerResponse.createBySuccessMessage("新增成功");
        }
        return ServerResponse.createByErrorMessage("新增失败");
    }


}
