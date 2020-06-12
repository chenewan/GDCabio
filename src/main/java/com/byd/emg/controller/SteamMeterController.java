package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.Electricity;
import com.byd.emg.pojo.SteamMeter;
import com.byd.emg.service.ISteamMeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/steamMeter")
public class SteamMeterController {

    @Autowired
    private ISteamMeter iSteamMeter;

    //根据位号和设备名查询该节点下面所有子节点的蒸汽用量
    @RequestMapping("/selectElectricityByTag_name")
    public ServerResponse selectSteamMeterByTag_name(String tag_name,
                                                      String device_name){
        SteamMeter steamMeterDb=iSteamMeter.selectByTag_name(tag_name,device_name);
        List<SteamMeter> steamMeterList=new ArrayList<SteamMeter>();
        if(steamMeterDb!=null){
            steamMeterList=iSteamMeter.selectSteamMeterBySuper_Id(steamMeterDb.getId());
        }
        if(steamMeterList.size()>0){
            return ServerResponse.createBySuccess("查询成功",steamMeterList);
        }else if(steamMeterDb!=null){
            steamMeterList.add(steamMeterDb);
            return ServerResponse.createBySuccess("查询成功",steamMeterList);
        }else{
            /*TapWater tapWater=new TapWater();
            tapWaterList.add(tapWater);*/
            return ServerResponse.createByErrorMessage("查询失败");
        }
    }


}
