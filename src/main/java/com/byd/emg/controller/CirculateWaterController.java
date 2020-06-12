package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.CirculateWater;
import com.byd.emg.pojo.TapWater;
import com.byd.emg.service.ICirculateWater;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/circulateWater")
public class CirculateWaterController {

    @Autowired
    private ICirculateWater iCirculateWater;

    //根据位号查询该节点下面所有子节点的循环水用量
    @RequestMapping("/selectCirculateWaterByTag_name")
    public ServerResponse selectTapWaterByTag_name(String tag_name,
                                                   String device_name){
        CirculateWater circulateWaterDb=iCirculateWater.selectByTag_name(tag_name,device_name);
        List<CirculateWater> circulateWaterList=new ArrayList<CirculateWater>();
        if(circulateWaterDb!=null){
            circulateWaterList=iCirculateWater.selectByCirculateWaterBySuper_Id(circulateWaterDb.getId());
        }
        if(circulateWaterList.size()>0){
            return ServerResponse.createBySuccess("查询成功",circulateWaterList);
        }else if(circulateWaterDb!=null){
            circulateWaterList.add(circulateWaterDb);
            return ServerResponse.createBySuccess("查询成功",circulateWaterList);
        }else{
            /*CirculateWater circulateWater=new CirculateWater();
            circulateWaterList.add(circulateWater);*/
            return ServerResponse.createByErrorMessage("查询失败");
        }
    }

    //向各节点下面增加或修改循环水数据的方法
    @RequestMapping("/addOrUpdateCirculateWater")
    public ServerResponse addOrUpdateCirculateWater(String device_name_parent,String tag_name_parent,CirculateWater circulateWater){
        CirculateWater circulateWaterDb=iCirculateWater.selectByTag_name(tag_name_parent,device_name_parent);
        if(circulateWaterDb==null){
            return ServerResponse.createByErrorMessage("请先勾选父节点");
        }
        if(circulateWaterDb.getSuper_id()!=null){
            circulateWater.setSuper_id(circulateWaterDb.getSuper_id());
        }else{
            circulateWater.setSuper_id(circulateWaterDb.getId());
        }
        //circulateWater.setRoot_node_id(circulateWaterDb.getRoot_node_id());
        if(circulateWater.getId()!=null){//编辑
            int updateCount=0;
            updateCount=iCirculateWater.updateCirculateWaterById(circulateWater);
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("更新成功");
            }else{
                return ServerResponse.createByErrorMessage("更新失败");
            }
        }else{//新增
            List<CirculateWater> circulateWaterExists=iCirculateWater.selectByCirculateWater(circulateWater);
            if(circulateWaterExists.size()>0){
                return ServerResponse.createByErrorMessage("新增的数据已存在");
            }
            int insertCount=0;
            insertCount=iCirculateWater.insertCirculateWater(circulateWater);
            if(insertCount>0){
                return ServerResponse.createBySuccessMessage("新增成功");
            }else{
                return ServerResponse.createByErrorMessage("新增失败");
            }
        }
    }

}
