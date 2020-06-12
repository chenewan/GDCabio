package com.byd.emg.controller;


import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.Contractual_capacity;
import com.byd.emg.pojo.Unit_Price;
import com.byd.emg.service.Unit_PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Unit_Price")
public class Unit_PriceController {
    @Autowired
    private Unit_PriceService unit_priceService;

    //新增立约容量
    @RequestMapping("/addCapacity")
    public ServerResponse addCapacity(Contractual_capacity capacity){
        int resultCount = unit_priceService.addCapacity(capacity);
        if (resultCount> 0) {
            return ServerResponse.createBySuccessMessage("新增成功");
        }
        return ServerResponse.createByErrorMessage("新增失败");
    }


    @RequestMapping("/getPowerUnit_Price")
    public ServerResponse getPowerUnit_Price(){
        List<Unit_Price> historyRecordsList = unit_priceService.getPowerUnit_Price();
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("查询电单价成功", historyRecordsList);
        }
        return ServerResponse.createByErrorMessage("查询电单价失败");
    }

    @RequestMapping("/getWaterUnit_Price")
    public ServerResponse getWaterUnit_Price(){
        List<Unit_Price> historyRecordsList = unit_priceService.getWaterUnit_Price();
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("查询水单价成功", historyRecordsList);
        }
        return ServerResponse.createByErrorMessage("查询水单价失败");
    }

    @RequestMapping("/getCirculatWaterUnit_Price")
    public ServerResponse getCirculatWaterUnit_Price(){
        List<Unit_Price> historyRecordsList = unit_priceService.getCirculatWaterUnit_Price();
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("查询循环水单价成功", historyRecordsList);
        }
        return ServerResponse.createByErrorMessage("查询循环水单价失败");
    }

    @RequestMapping("/getFreezeWaterUnit_Price")
    public ServerResponse getFreezeWaterUnit_Price(){
        List<Unit_Price> historyRecordsList = unit_priceService.getFreezeWaterUnit_Price();
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("查询冷冻水单价成功", historyRecordsList);
        }
        return ServerResponse.createByErrorMessage("查询冷冻水单价失败");
    }

    @RequestMapping("/getFermentationAirUnit_Price")
    public ServerResponse getFermentationAirUnit_Price(){
        List<Unit_Price> historyRecordsList = unit_priceService.getFermentationAirUnit_Price();
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("查询发酵空气单价成功", historyRecordsList);
        }
        return ServerResponse.createByErrorMessage("查询发酵空气单价失败");
    }

    @RequestMapping("/getSteamUnit_Price")
    public ServerResponse getSteamUnit_Price(){
        List<Unit_Price> historyRecordsList = unit_priceService.getSteamUnit_Price();
        if (historyRecordsList.size() > 0) {
            return ServerResponse.createBySuccess("查询蒸汽单价成功", historyRecordsList);
        }
        return ServerResponse.createByErrorMessage("查询蒸汽单价失败");
    }

    //新增及更新峰平谷电单价
    @RequestMapping("/addPowerUnit_Price")
    public ServerResponse addPowerUnit_Price(Unit_Price unit_price){
//        Long idDb=unit_priceService.getIDFromPowerUnit_Price(unit_price.getStart_time());//查询数据库中是否有相同日期的ID
//        unit_price.setId(idDb);
//        if(unit_price.getId()==null){//新增
//            int a = unit_priceService.addPowerUnit_Price(unit_price);
//            if (a>= 0) {
//                return ServerResponse.createBySuccessMessage("添加峰平谷电单价成功");
//            }
//            return ServerResponse.createByErrorMessage("添加峰平谷电单价失败");
//        }else{//编辑
        int updateCount=unit_priceService.updatePowerUnit_Price(unit_price);
        if(updateCount>0){
            return ServerResponse.createBySuccessMessage("修改峰平谷电单价成功");
        }
        return ServerResponse.createByErrorMessage("修改峰平谷电单价失败");
//        }
    }

    //查询峰平谷电单价
    @RequestMapping("/selectPowerUnit_Price")
    public ServerResponse selectPowerUnit_Price(){
        List<Unit_Price> unitPriceList = unit_priceService.selectPowerUnit_Price();
        if (unitPriceList.size()>0) {
            return ServerResponse.createBySuccess("查询峰平谷电单价成功",unitPriceList);
        }
        return ServerResponse.createByErrorMessage("查询峰平谷电单价失败");

    }


    //删除峰平谷电单价(根据id)
    @RequestMapping("/deletePowerUnit_Price")
    public ServerResponse deletePowerUnit_Price(Long id){
        int delCount=unit_priceService.deletePowerUnit_Price(id);
        if (delCount>0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }


    //电单价的历史记录(默认显示最新单价)
    @RequestMapping("/powerUnit_PriceRecords")
    public ServerResponse powerUnit_PriceRecords(@RequestParam(value = "start_time",defaultValue = "") String start_time){
        List<Unit_Price> unitPriceList=unit_priceService.powerUnit_PriceRecords(start_time,"Unit_Price_Power");
        if (unitPriceList.size()>0) {
            return ServerResponse.createBySuccess("查询成功",unitPriceList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //删除电单价(根据id)
    @RequestMapping("/delPowerUnit_PriceById")
    public ServerResponse delPowerUnit_PriceById(Long id){
        int delCount=unit_priceService.deleteUnit_PriceById(id,"Unit_Price_Power");
        if (delCount>0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }


    //蒸汽单价的新增及修改
    @RequestMapping("/addSteamUnit_Price")
    public ServerResponse addSteamUnit_Price(Unit_Price unit_price){
        if(unit_price.getId()==null){//新增
            int a = unit_priceService.addSteamUnit_Price(unit_price);
            if (a>= 0) {
                return ServerResponse.createBySuccessMessage("添加蒸汽单价成功");
            }
            return ServerResponse.createByErrorMessage("添加蒸汽单价失败");
        }else{//编辑
            int updateCount=unit_priceService.updateUnit_Price(unit_price,"Unit_Price_steam");
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("修改蒸汽单价成功");
            }
            return ServerResponse.createBySuccessMessage("修改蒸汽单价失败");
        }

    }

    //蒸汽的历史记录(默认显示最新单价)
    @RequestMapping("/steamUnit_PriceRecords")
    public ServerResponse steamUnit_PriceRecords(@RequestParam(value = "start_time") String start_time){
        List<Unit_Price> unitPriceList=unit_priceService.unitPriceRecords(start_time,"Unit_Price_steam");
        if (unitPriceList.size()>0) {
            return ServerResponse.createBySuccess("查询成功",unitPriceList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //删除蒸汽单价(根据id)
    @RequestMapping("/deleteSteamUnit_PriceById")
    public ServerResponse deleteSteamUnit_PriceById(Long id){
        int delCount=unit_priceService.deleteUnit_PriceById(id,"Unit_Price_steam");
        if (delCount>0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    //新增及更新水单价
    @RequestMapping("/addWaterUnit_Price")
    public ServerResponse addWaterUnit_Price(Unit_Price unit_price){
        if(unit_price.getId()==null){//新增
            int a = unit_priceService.addWaterUnit_Price(unit_price);
            if (a >= 0) {
                return ServerResponse.createBySuccessMessage("添加水单价成功");
            }
            return ServerResponse.createByErrorMessage("添加水单价失败");
        }else{//编辑
            int updateCount=unit_priceService.updateUnit_Price(unit_price,"Unit_Price_water");
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("修改水单价成功");
            }
            return ServerResponse.createByErrorMessage("修改水单价失败");
        }

    }

    //水单价的历史记录(默认显示最新单价)
    @RequestMapping("/waterUnit_PriceRecords")
    public ServerResponse waterUnit_PriceRecords(@RequestParam(value = "start_time") String start_time){
        List<Unit_Price> unitPriceList=unit_priceService.unitPriceRecords(start_time,"Unit_Price_water");
        if (unitPriceList.size()>0) {
            return ServerResponse.createBySuccess("查询成功",unitPriceList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //删除水单价记录(根据id)
    @RequestMapping("/deleteWaterUnit_PriceById")
    public ServerResponse deleteWaterUnit_PriceById(Long id){
        int delCount=unit_priceService.deleteUnit_PriceById(id,"Unit_Price_water");
        if (delCount>0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    //新增及更新天然气单价
    @RequestMapping("/addNaturalGasUnit_Price")
    public ServerResponse addNaturalGasUnit_Price(Unit_Price unit_price){
        if(unit_price.getId()==null){//新增
            int a = unit_priceService.addNaturalGasUnit_Price(unit_price);
            if (a >= 0) {
                return ServerResponse.createBySuccess("添加天然气单价成功", a);
            }
            return ServerResponse.createByErrorMessage("添加天然气单价失败");
        }else{//更新
            int updateCount=unit_priceService.updateUnit_Price(unit_price,"Unit_Price_NaturalGas");
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("修改天然气单价成功");
            }
            return ServerResponse.createByErrorMessage("修改天然气单价失败");
        }
    }

    //天然气单价的历史记录(默认显示最新单价)
    @RequestMapping("/naturalGasUnit_PriceRecords")
    public ServerResponse naturalGasUnit_PriceRecords(@RequestParam(value = "start_time") String start_time){
        List<Unit_Price> unitPriceList=unit_priceService.unitPriceRecords(start_time,"Unit_Price_NaturalGas");
        if (unitPriceList.size()>0) {
            return ServerResponse.createBySuccess("查询成功",unitPriceList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //删除天然气单价(根据id)
    @RequestMapping("/delNaturalGasUnit_PriceById")
    public ServerResponse delNaturalGasUnit_PriceById(Long id){
        int delCount=unit_priceService.deleteUnit_PriceById(id,"Unit_Price_NaturalGas");
        if (delCount>0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    //新增及更新循环水单价
    @RequestMapping("/addCirculatingWaterUnit_Price")
    public ServerResponse addCirculatingWaterUnit_Price(Unit_Price unit_price){
        if(unit_price.getId()==null){//新增
            int a = unit_priceService.addCirculatingWaterUnit_Price(unit_price);
            if (a >= 0) {
                return ServerResponse.createBySuccessMessage("添加循环水单价成功");
            }
            return ServerResponse.createByErrorMessage("添加循环水单价失败");
        }else{//编辑
            int updateCount=unit_priceService.updateUnit_Price(unit_price,"Unit_Price_CirculatingWater");
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("修改循环水单价成功");
            }
            return ServerResponse.createByErrorMessage("修改循环水单价失败");
        }
    }

    //循环水单价的历史记录(默认显示最新单价)
    @RequestMapping("/circulatingWaterUnit_PriceRecords")
    public ServerResponse circulatingWaterUnit_PriceRecords(@RequestParam(value = "start_time") String start_time){
        List<Unit_Price> unitPriceList=unit_priceService.unitPriceRecords(start_time,"Unit_Price_CirculatingWater");
        if (unitPriceList.size()>0) {
            return ServerResponse.createBySuccess("查询成功",unitPriceList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //删除循环水单价(根据id)
    @RequestMapping("/delCirculatingWaterUnit_PriceById")
    public ServerResponse delCirculatingWaterUnit_PriceById(Long id){
        int delCount=unit_priceService.deleteUnit_PriceById(id,"Unit_Price_CirculatingWater");
        if (delCount>0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @RequestMapping("/addFreezeWaterUnit_Price")
    public ServerResponse addFreezeWaterUnit_Price(Unit_Price unit_price){
        if(unit_price.getId()==null){//新增
            int a = unit_priceService.addFreezeWaterUnit_Price(unit_price);
            if (a >= 0) {
                return ServerResponse.createBySuccessMessage("添加冷冻水单价成功");
            }
            return ServerResponse.createByErrorMessage("添加冷冻水单价失败");
        }else{//编辑
            int updateCount=unit_priceService.updateUnit_Price(unit_price,"Unit_Price_freezeWater");
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("修改冷冻水单价成功");
            }
            return ServerResponse.createByErrorMessage("修改冷冻水单价失败");
        }
    }

    //冷冻水单价的历史记录(默认显示最新单价)
    @RequestMapping("/freezeWaterUnit_PriceRecords")
    public ServerResponse freezeWaterUnit_PriceRecords(@RequestParam(value = "start_time") String start_time){
        List<Unit_Price> unitPriceList=unit_priceService.unitPriceRecords(start_time,"Unit_Price_freezeWater");
        if (unitPriceList.size()>0) {
            return ServerResponse.createBySuccess("查询成功",unitPriceList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //删除冷冻水单价(根据id)
    @RequestMapping("/delFreezeWaterUnit_PriceById")
    public ServerResponse delFreezeWaterUnit_PriceById(Long id){
        int delCount=unit_priceService.deleteUnit_PriceById(id,"Unit_Price_freezeWater");
        if (delCount>0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    //新增及更新空气单价
    @RequestMapping("/addFermentationAirUnit_Price")
    public ServerResponse addFermentationAirUnit_Price(Unit_Price unit_price){
        if(unit_price.getId()==null){//新增
            int a = unit_priceService.addFermentationAirUnit_Price(unit_price);
            if (a >= 0) {
                return ServerResponse.createBySuccessMessage("添加空气单价成功");
            }
            return ServerResponse.createByErrorMessage("添加空气单价失败");
        }else{//编辑
            int updateCount=unit_priceService.updateUnit_Price(unit_price,"Unit_Price__fermentationAir");
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("修改空气单价成功");
            }
            return ServerResponse.createByErrorMessage("修改空气单价失败");
        }
    }

    //空气单价的历史记录(默认显示最新单价)
    @RequestMapping("/fermentationAirUnit_PriceRecords")
    public ServerResponse fermentationAirUnit_PriceRecords(@RequestParam(value = "start_time") String start_time){
        List<Unit_Price> unitPriceList=unit_priceService.unitPriceRecords(start_time,"Unit_Price__fermentationAir");
        if (unitPriceList.size()>0) {
            return ServerResponse.createBySuccess("查询成功",unitPriceList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //删除空气单价(根据id)
    @RequestMapping("/delFermentationAirUnit_PriceById")
    public ServerResponse delFermentationAirUnit_PriceById(Long id){
        int delCount=unit_priceService.deleteUnit_PriceById(id,"Unit_Price__fermentationAir");
        if (delCount>0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }



}
