package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.Electricity;
import com.byd.emg.pojo.TapWater;
import com.byd.emg.service.IElectricity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/electricity")
public class ElectricityController {

    @Autowired
    private IElectricity iElectricity;

    //根据位号和设备名查询该节点下面所有子节点的用电量
    @RequestMapping("/selectElectricityByTag_name")
    public ServerResponse selectElectricityByTag_name(String tag_name,
                                                   String device_name){
        Electricity electricityDb=iElectricity.selectByTag_name(tag_name,device_name);
        List<Electricity> electricityList=new ArrayList<Electricity>();
        if(electricityDb!=null){
            electricityList=iElectricity.selectElectricityBySuper_Id(electricityDb.getId());
        }
        if(electricityList.size()>0){
            return ServerResponse.createBySuccess("查询成功",electricityList);
        }else if(electricityDb!=null){
            electricityList.add(electricityDb);
            return ServerResponse.createBySuccess("查询成功",electricityList);
        }else{
            /*TapWater tapWater=new TapWater();
            tapWaterList.add(tapWater);*/
            return ServerResponse.createByErrorMessage("查询失败");
        }
    }


}
