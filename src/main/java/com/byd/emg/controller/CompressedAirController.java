package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.CompressedAir;
import com.byd.emg.pojo.TapWater;
import com.byd.emg.service.ICompressedAir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/compressedAir")
public class CompressedAirController {

    @Autowired
    private ICompressedAir iCompressedAir;

    //根据位号查询该节点下面所有子节点的压缩空气用量
    @RequestMapping("/selectCompressedAirByTag_name")
    public ServerResponse selectCompressedAirByTag_name(String tag_name,
                                                   String device_name){
        CompressedAir compressedAirDb=iCompressedAir.selectByTag_name(tag_name,device_name);
        List<CompressedAir> compressedAirList=new ArrayList<CompressedAir>();
        if(compressedAirDb!=null){
            compressedAirList=iCompressedAir.selectByTapWaterBySuper_Id(compressedAirDb.getId());
        }
        if(compressedAirList.size()>0){
            return ServerResponse.createBySuccess("查询成功",compressedAirList);
        }else if(compressedAirDb!=null){
            compressedAirList.add(compressedAirDb);
            return ServerResponse.createBySuccess("查询成功",compressedAirList);
        }else{
           /* CompressedAir compressedAir=new CompressedAir();
            compressedAirList.add(compressedAir);*/
            return ServerResponse.createByErrorMessage("查询失败");
        }
    }

}
