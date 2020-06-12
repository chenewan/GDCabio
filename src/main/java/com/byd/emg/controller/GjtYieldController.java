package com.byd.emg.controller;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.mapper.SectionAnalysisMapper;
import com.byd.emg.pojo.GjtYield;
import com.byd.emg.pojo.OilYield;
import com.byd.emg.resultData.GjtEnergyData;
import com.byd.emg.resultData.TankNumberData;
import com.byd.emg.resultData.UnitCostData;
import com.byd.emg.resultData.UnitEnergyData;
import com.byd.emg.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gjtYield")
public class GjtYieldController {

    @Autowired
    private GjtYieldService gjtYieldService;

    @Autowired
    private ProductInfoService iProductInfo;

    @Autowired
    private HistoryTankBatchNoService historyTankBatchNoService;

    @Autowired
    private OilYieldService oilYieldService;

    @Autowired
    private Unit_PriceService unit_priceService;

    @Autowired
    private SectionAnalysisMapper sectionAnalysisMapper;

    //计算大罐数、中罐数、小罐数的方法
    @RequestMapping("/countTankNumbers")
    public ServerResponse countTankNumbers(String startDate,String endDate,String productCode){
        //编号和产品的映射集合
        Map<String,String> productMap=iProductInfo.productInfoMap();
        String productType="";
        if(!StringUtils.isEmpty(productCode)){
            productType=productMap.get(productCode);
        }
        if(StringUtils.isEmpty(productType))  return ServerResponse.createByErrorMessage("产品类型为空");
        TankNumberData tankNumberData=new TankNumberData();
        //小罐罐号的集合
        List<String> smallList=new ArrayList<String>();
        smallList.add("201");smallList.add("202");smallList.add("203");smallList.add("204");
        String smallPotNumbers=historyTankBatchNoService.countTankNumbers(startDate,endDate,smallList,productType);
        if(StringUtils.isEmpty(smallPotNumbers))  smallPotNumbers="0";
        //中罐罐号的集合
        List<String> mediumList=new ArrayList<String>();
        mediumList.add("301");mediumList.add("302");mediumList.add("303");
        String middlePotNumbers=historyTankBatchNoService.countTankNumbers(startDate,endDate,mediumList,productType);
        if(StringUtils.isEmpty(middlePotNumbers))  middlePotNumbers="0";

        //大罐罐号的集合
        List<String> lagreList=new ArrayList<String>();
        lagreList.add("401");lagreList.add("402");lagreList.add("403");lagreList.add("404");lagreList.add("405");lagreList.add("406");
        String bigPotNumbers=historyTankBatchNoService.countTankNumbers(startDate,endDate,lagreList,productType);
        if(StringUtils.isEmpty(bigPotNumbers))  bigPotNumbers="0";
        tankNumberData.setSmallPotNumbers(smallPotNumbers);
        tankNumberData.setMiddlePotNumbers(middlePotNumbers);
        tankNumberData.setBigPotNumbers(bigPotNumbers);
        return ServerResponse.createBySuccess("查询设备参数失败",tankNumberData);
    }

    //新增及修改产量(手动输入干菌体产量)
    @RequestMapping("/insertYield")
    public ServerResponse insertYield(GjtYield gjtYield,String productCode){
        String strtDate=gjtYield.getStartDate();
        String endDate=gjtYield.getEndDate();
        if(StringUtils.isEmpty(gjtYield.getYields())) gjtYield.setYields("0");
        //大罐跨罐数
        List<String> lagreList=new ArrayList<String>();
        lagreList.add("401");lagreList.add("402");lagreList.add("403");lagreList.add("404");lagreList.add("405");lagreList.add("406");
        String bigSpanNumbers=historyTankBatchNoService.countSpanNumbers(gjtYield.getStartDate(),gjtYield.getEndDate(),lagreList,productCode);
        if(StringUtils.isEmpty(bigSpanNumbers))  bigSpanNumbers="0";
        gjtYield.setBigSpanNumbers(bigSpanNumbers);

        //中罐跨灌数
        List<String> mediumList=new ArrayList<String>();
        mediumList.add("301");mediumList.add("302");mediumList.add("303");
        String middleSpanNumbers=historyTankBatchNoService.countSpanNumbers(gjtYield.getStartDate(),gjtYield.getEndDate(),mediumList,productCode);
        if(StringUtils.isEmpty(middleSpanNumbers))  middleSpanNumbers="0";
        gjtYield.setMiddleSpanNumbers(middleSpanNumbers);

        //小罐跨罐数
        List<String> smallList=new ArrayList<String>();
        smallList.add("201");smallList.add("202");smallList.add("203");smallList.add("204");
        String smallSpanNumbers=historyTankBatchNoService.countSpanNumbers(gjtYield.getStartDate(),gjtYield.getEndDate(),smallList,productCode);
        if(StringUtils.isEmpty(smallSpanNumbers))  smallSpanNumbers="0";
        gjtYield.setSmallSpanNumbers(smallSpanNumbers);

        //查询单价
        Double unitPricePower=unit_priceService.selectUnitPrice(strtDate,endDate,"Comprehensive_Price_power");  //综合电价不含税
        if(unitPricePower==null)  unitPricePower=0d;
        Double unitPriceWater=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_water");
        if(unitPriceWater==null)  unitPriceWater=0d;
        Double unitPriceCircuWater=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_CirculatingWater");
        if(unitPriceCircuWater==null)  unitPriceCircuWater=0d;
        Double unitPriceFreezeWater=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_freezeWater");
        if(unitPriceFreezeWater==null)  unitPriceFreezeWater=0d;
        Double unitPriceSteam=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_steam");
        if(unitPriceSteam==null)  unitPriceSteam=0d;
        Double unitPriceAir=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price__fermentationAir");
        if(unitPriceAir==null)  unitPriceAir=0d;
        gjtYield.setUnitPricePower(unitPricePower+"");
        gjtYield.setUnitPriceWater(unitPriceWater+"");
        gjtYield.setUnitPriceCircuWater(unitPriceCircuWater+"");
        gjtYield.setUnitPriceFreezeWater(unitPriceFreezeWater+"");
        gjtYield.setUnitPriceSteam(unitPriceSteam+"");
        gjtYield.setUnitPriceAir(unitPriceAir+"");
        //编号和产品的映射集合
        Map<String,String> productMap=iProductInfo.productInfoMap();
        gjtYield.setProductType(productMap.get(productCode));

        if(gjtYield.getId()==null){//新增
            int insertCount=gjtYieldService.insertGjtYield(gjtYield);
            if(insertCount>0){
                return ServerResponse.createBySuccessMessage("新增成功");
            }else{
                return ServerResponse.createByErrorMessage("新增失败");
            }
        }else{//编辑
            int updateCount=gjtYieldService.updateGjtYield(gjtYield);
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("更新成功");
            }else{
                return ServerResponse.createByErrorMessage("更新失败");
            }
        }
    }

    //干菌体历史记录(默认显示最新的5条记录)
    @RequestMapping("/gjtYieldRecords")
    public ServerResponse gjtYieldRecords(String date,String productCode){
        if(!StringUtils.isEmpty(date)) date= DateTimeUtil.strToStr(date,"yyyy-MM-dd","yyyy-MM");
        //编号和产品的映射集合
        Map<String,String> productMap=iProductInfo.productInfoMap();
        List<GjtYield> gjtYieldList=gjtYieldService.selectByProductType(date,productMap.get(productCode));
        if(gjtYieldList.size()>0){
            return ServerResponse.createBySuccess("查询成功",gjtYieldList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //删除干菌体(根据id)
    @RequestMapping("/deleteGjtById")
    public ServerResponse deleteGjtById(Long id){
        int deleteCount=gjtYieldService.deleteGjtById(id);
        if(deleteCount>0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    //毛油产量新增及修改
    @RequestMapping("/insertMyYield")
    public ServerResponse insertMyYield(OilYield oilYield){
        String strtDate=oilYield.getStartDate();
        String endDate=oilYield.getEndDate();
        //查询单价
        Double unitPricePower=unit_priceService.selectUnitPrice(strtDate,endDate,"Comprehensive_Price_power");
        if(unitPricePower==null)  unitPricePower=0d;
        Double unitPriceWater=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_water");
        if(unitPriceWater==null)  unitPriceWater=0d;
        Double unitPriceCircuWater=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_CirculatingWater");
        if(unitPriceCircuWater==null)  unitPriceCircuWater=0d;
        Double unitPriceFreezeWater=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_freezeWater");
        if(unitPriceFreezeWater==null)  unitPriceFreezeWater=0d;
        Double unitPriceSteam=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_steam");
        if(unitPriceSteam==null)  unitPriceSteam=0d;
        Double unitPriceAir=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price__fermentationAir");
        if(unitPriceAir==null)  unitPriceAir=0d;
        oilYield.setUnitPricePower(unitPricePower+"");
        oilYield.setUnitPriceWater(unitPriceWater+"");
        oilYield.setUnitPriceCircuWater(unitPriceCircuWater+"");
        oilYield.setUnitPriceFreezeWater(unitPriceFreezeWater+"");
        oilYield.setUnitPriceSteam(unitPriceSteam+"");
        oilYield.setUnitPriceAir(unitPriceAir+"");
        if(oilYield.getId()==null){//新增
            int insertCount= oilYieldService.insertOilYield(oilYield,"my_yield");
            if(insertCount>0){
                return ServerResponse.createBySuccessMessage("新增成功");
            }
            return ServerResponse.createByErrorMessage("新增失败");
        }else{//编辑
            int updateCount=oilYieldService.updateOilYield(oilYield,"my_yield");
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("更新成功");
            }
            return ServerResponse.createByErrorMessage("更新失败");
        }
    }

    //毛油历史记录(默认显示最新的5条记录)
    @RequestMapping("/myOilYieldRecords")
    public ServerResponse myOilYieldRecords(String date,String type){
        if(!StringUtils.isEmpty(date)) date= DateTimeUtil.strToStr(date,"yyyy-MM-dd","yyyy-MM");
        List<OilYield> oilYieldList=oilYieldService.selectByDate(date,type,"my_yield");
        if(oilYieldList.size()>0){
            return ServerResponse.createBySuccess("查询成功",oilYieldList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //删除毛油(根据id)
    @RequestMapping("/deleteMyById")
    public ServerResponse deleteMyById(Long id){
        int deleteCount=oilYieldService.deleteOilById(id,"my_yield");
        if(deleteCount>0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    //成品油历史记录(默认显示最新的5条记录)
    @RequestMapping("/cpyOilYieldRecords")
    public ServerResponse cpyOilYieldRecords(String date,String type){
        if(!StringUtils.isEmpty(date)) date= DateTimeUtil.strToStr(date,"yyyy-MM-dd","yyyy-MM");
        List<OilYield> oilYieldList=oilYieldService.selectByDate(date,type,"cpy_yield");
        if(oilYieldList.size()>0){
            return ServerResponse.createBySuccess("查询成功",oilYieldList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //删除成品油(根据id)
    @RequestMapping("/deleteCpyById")
    public ServerResponse deleteCpyById(Long id){
        int deleteCount=oilYieldService.deleteOilById(id,"cpy_yield");
        if(deleteCount>0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    //成品油产量新增及更新
    @RequestMapping("/insertCpyYield")
    public ServerResponse insertCpyYield(OilYield oilYield){
        String strtDate=oilYield.getStartDate();
        String endDate=oilYield.getEndDate();
        //查询单价
        Double unitPricePower=unit_priceService.selectUnitPrice(strtDate,endDate,"Comprehensive_Price_power");
        if(unitPricePower==null)  unitPricePower=0d;
        Double unitPriceWater=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_water");
        if(unitPriceWater==null)  unitPriceWater=0d;
        Double unitPriceCircuWater=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_CirculatingWater");
        if(unitPriceCircuWater==null)  unitPriceCircuWater=0d;
        Double unitPriceFreezeWater=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_freezeWater");
        if(unitPriceFreezeWater==null)  unitPriceFreezeWater=0d;
        Double unitPriceSteam=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price_steam");
        if(unitPriceSteam==null)  unitPriceSteam=0d;
        Double unitPriceAir=unit_priceService.selectUnitPrice(strtDate,endDate,"Unit_Price__fermentationAir");
        if(unitPriceAir==null)  unitPriceAir=0d;
        oilYield.setUnitPricePower(unitPricePower+"");
        oilYield.setUnitPriceWater(unitPriceWater+"");
        oilYield.setUnitPriceCircuWater(unitPriceCircuWater+"");
        oilYield.setUnitPriceFreezeWater(unitPriceFreezeWater+"");
        oilYield.setUnitPriceSteam(unitPriceSteam+"");
        oilYield.setUnitPriceAir(unitPriceAir+"");
        if(oilYield.getId()==null){//新增
            int insertCount= oilYieldService.insertOilYield(oilYield,"cpy_yield");
            if(insertCount>0){
                return ServerResponse.createBySuccessMessage("新增成功");
            }
            return ServerResponse.createByErrorMessage("新增失败");
        }else{//编辑
            int updateCount=oilYieldService.updateOilYield(oilYield,"cpy_yield");
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("更新成功");
            }
            return ServerResponse.createByErrorMessage("更新失败");
        }
    }

    //能耗分析
    @RequestMapping("/energyData")
    public ServerResponse energyData(String startDate,String endDate,String productType,String media){
        List<GjtEnergyData> gjtEnergyDataList=gjtYieldService.energyData(startDate,endDate,productType,media);
        if(gjtEnergyDataList.size()>0){
            return ServerResponse.createBySuccess("查询成功",gjtEnergyDataList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //单耗分析
    @RequestMapping("/unitEnergyData")
    public ServerResponse unitEnergyData(String media){
        List<UnitEnergyData> unitEnergyDataList=new ArrayList<>();
        if(!StringUtils.isEmpty(media)) unitEnergyDataList=gjtYieldService.unitEnergyData(media);
        if(unitEnergyDataList.size()>0){
            return ServerResponse.createBySuccess("查询成功",unitEnergyDataList);
        }
        return ServerResponse.createBySuccess("查询失败",unitEnergyDataList);
    }

    //单位成本
    @RequestMapping("/unitCost")
    public ServerResponse unitCost(String media){
        List<UnitCostData> unitCostDataList=new ArrayList<>();
        if(!StringUtils.isEmpty(media)) unitCostDataList=gjtYieldService.unitCost(media);
        if(unitCostDataList.size()>0){
            return ServerResponse.createBySuccess("查询成功",unitCostDataList);
        }
        return ServerResponse.createBySuccess("查询失败",unitCostDataList);
    }






}
