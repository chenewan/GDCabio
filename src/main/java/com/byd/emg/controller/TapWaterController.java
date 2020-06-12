package com.byd.emg.controller;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.TapWater;
import com.byd.emg.service.ITapWater;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tapWater")
public class TapWaterController {

    @Autowired
    private ITapWater iTapWater;

    //根据位号查询该节点下面所有子节点的自来水用量
    @RequestMapping("/selectTapWaterByTag_name")
    public ServerResponse selectTapWaterByTag_name(String tag_name,
                                                   String device_name){
        TapWater tapWaterDb=iTapWater.selectByTag_name(tag_name,device_name);
        List<TapWater> tapWaterList=new ArrayList<TapWater>();
        if(tapWaterDb!=null){
            tapWaterList=iTapWater.selectByTapWaterBySuper_Id(tapWaterDb.getId());
        }
        if(tapWaterList.size()>0){
            return ServerResponse.createBySuccess("查询成功",tapWaterList);
        }else if(tapWaterDb!=null){
            tapWaterList.add(tapWaterDb);
            return ServerResponse.createBySuccess("查询成功",tapWaterList);
        }else{
            TapWater tapWater=new TapWater();
            tapWaterList.add(tapWater);
            return ServerResponse.createByError("查询失败",tapWaterList);
        }
    }

    //向前端返回树形结构的数据
    /*@RequestMapping("returnTreeData")
    public ServerResponse returnTreeData(){


    }*/

    //向各节点下面增加或修改自来水数据的方法
   @RequestMapping("/addOrUpdateTapWater")
    public ServerResponse addOrUpdateTapWater(String device_name_parent,String tag_name_parent,TapWater tapWater){
        TapWater tapWaterDb=iTapWater.selectByTag_name(tag_name_parent,device_name_parent);
        if(tapWaterDb==null){
            return ServerResponse.createByErrorMessage("请先勾选父节点");
        }
        if(tapWaterDb.getSuper_id()!=null){
            tapWater.setSuper_id(tapWaterDb.getSuper_id());
        }else{
            tapWater.setSuper_id(tapWaterDb.getId());
        }
       //tapWater.setRoot_node_id(tapWaterDb.getRoot_node_id());
       if(tapWater.getId()!=null){ //编辑
            int updateCount=0;
            updateCount=iTapWater.updateTapWaterById(tapWater);
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("更新成功");
            }else{
                return ServerResponse.createByErrorMessage("更新失败");
            }
       }else{ //新增
           List<TapWater> tapWaterExists=iTapWater.selectByTapWater(tapWater);
           if(tapWaterExists.size()>0){
               return ServerResponse.createByErrorMessage("新增的数据已存在");
           }
           int insertCount=0;
           insertCount=iTapWater.insertTapWater(tapWater);
           if(insertCount>0){
               return ServerResponse.createBySuccessMessage("新增成功");
           }else{
               return ServerResponse.createByErrorMessage("新增失败");
           }
       }
    }


}
