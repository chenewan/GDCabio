package com.byd.emg.service.impl;

import com.byd.emg.mapper.EquipmentMapper;
import com.byd.emg.mapper.RealTimeValueMapper;
import com.byd.emg.pojo.EquipmentStates;
import com.byd.emg.pojo.RealValue;
import com.byd.emg.resultData.EquipmentData;
import com.byd.emg.resultData.EquipmentParameterData;
import com.byd.emg.resultData.IceboxParameterData;
import com.byd.emg.service.EquipmentService;
import com.byd.emg.service.RatedGasProductionService;
import com.sun.org.apache.bcel.internal.generic.SWITCH;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("equipmentService")
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private RealTimeValueMapper realTimeValueMapper;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private RatedGasProductionService ratedGasProductionService;

    @Override
    public Object getParameterData(List<String> tagnames,String media) {
        List<RealValue> resultList=new ArrayList<>();
        List<RealValue> powerList=realTimeValueMapper.selectByTagnameList(tagnames,"realValue_power_table");
        List<RealValue> freezeWaterList=realTimeValueMapper.selectByTagnameList(tagnames,"realValue_freezeWater_table");
        List<RealValue> fermentationAirList=realTimeValueMapper.selectByTagnameList(tagnames,"realValue_fermentationAir_table");
        if(powerList.size()>0) resultList.addAll(powerList);
        if(freezeWaterList.size()>0) resultList.addAll(freezeWaterList);
        if(fermentationAirList.size()>0) resultList.addAll(fermentationAirList);
        String tagNameElectricConsumption = "";
        String tagNameCumulativeCooling = "";

        if(media.contains("k")){
            EquipmentParameterData equipmentParameterData = new EquipmentParameterData();
            for(RealValue realValue:resultList){
                String tagname=realValue.getRealValue_tagname();
                int startIndex=tagname.lastIndexOf("-")+1;
                if(tagname.contains("JT")&&tagname.substring(startIndex).equals("Ia")){
                    equipmentParameterData.setRunningCurrent(realValue.getRealValue());
                }else if (tagname.contains("JT")&&tagname.substring(startIndex).equals("P")){
                    equipmentParameterData.setActivePower(realValue.getRealValue());
                }else if(tagname.contains("JT")&&tagname.substring(startIndex).equals("PF")){
                    equipmentParameterData.setPowerFactor(realValue.getRealValue());
                }else if(tagname.contains("JT")&&tagname.substring(startIndex).equals("EP")){
                    equipmentParameterData.setActiveEnergy(realValue.getRealValue());
                }else if(tagname.contains("FIT")&&tagname.substring(startIndex).equals("V")){
                    equipmentParameterData.setInstantaneousFlow(realValue.getRealValue());
                }else if(tagname.contains("FIT")&&tagname.substring(startIndex).equals("P")){
                    equipmentParameterData.setSystemPressure(realValue.getRealValue());
                }else if(tagname.contains("FIT")&&tagname.substring(startIndex).equals("T1")){
                    equipmentParameterData.setSystemTemperature(realValue.getRealValue());
                }
            }

            Map<String,String> ratedGasMap=ratedGasProductionService.getratedGas();
            Double powerRatio=0d;
            //计算额定功率比
            for(RealValue realValue:powerList){
                String tagname=realValue.getRealValue_tagname();
                int startIndex=tagname.lastIndexOf("-")+1;
                if(tagname.substring(startIndex).equals("P")){
                    Double p=0d;
                    if(!StringUtils.isEmpty(realValue.getRealValue())) p=Double.valueOf(realValue.getRealValue());
                    System.out.println(Double.valueOf(ratedGasMap.get(tagname)));
                    powerRatio=p/Double.valueOf(ratedGasMap.get(tagname));
                    break;
                }
            }
            //计算实时效率和产气效率
            for(RealValue realValue:fermentationAirList){
                String tagname=realValue.getRealValue_tagname();
                int startIndex=tagname.lastIndexOf("-")+1;
                if(tagname.substring(startIndex).equals("V")){
                    //实时效率
                    Double gasProduction=0d;
                    if(!StringUtils.isEmpty(realValue.getRealValue())) gasProduction=Double.valueOf(realValue.getRealValue());
                    Double realTimeEfficiency=gasProduction/Double.valueOf(ratedGasMap.get(tagname));
                    equipmentParameterData.setRealTimeEfficiency(realTimeEfficiency+"");
                    //产气效率
                    Double biogasProductionEfficiency=0d;
                    if(powerRatio!=0d) biogasProductionEfficiency=realTimeEfficiency/powerRatio;
                    equipmentParameterData.setGasProductionEfficiency(biogasProductionEfficiency+"");
                    break;
                }
            }
            EquipmentStates equipmentStates = equipmentMapper.getEquipmentStates(media);
            equipmentParameterData.setEquipmentStates(equipmentStates);
            return equipmentParameterData;
        }else{
            IceboxParameterData iceboxParameterData = new IceboxParameterData();
            for(RealValue realValue:resultList){
                String tagname=realValue.getRealValue_tagname();
                int startIndex=tagname.lastIndexOf("-")+1;
                if(tagname.contains("JT")&&tagname.substring(startIndex).equals("Ib")){
                    iceboxParameterData.setRunningCurrent(realValue.getRealValue());
                }else if (tagname.contains("JT")&&tagname.substring(startIndex).equals("P")){
                    iceboxParameterData.setActivePower(realValue.getRealValue());
                }else if(tagname.contains("JT")&&tagname.substring(startIndex).equals("PF")){
                    iceboxParameterData.setPowerFactor(realValue.getRealValue());
                }else if(tagname.contains("JT")&&tagname.substring(startIndex).equals("EP")){
                    iceboxParameterData.setActiveEnergy(realValue.getRealValue());
                    tagNameElectricConsumption=tagname;
                }else if(tagname.contains("FIT")&&tagname.substring(startIndex).equals("T1")){
                    iceboxParameterData.setInletTemperature(realValue.getRealValue());
                }else if(tagname.contains("FIT")&&tagname.substring(startIndex).equals("T2")){
                    iceboxParameterData.setReturnTemperature(realValue.getRealValue());
                }else if(tagname.contains("FIT")&&tagname.substring(startIndex).equals("Q")){
                    iceboxParameterData.setInstantaneousFlow(realValue.getRealValue());
                }else if(tagname.contains("FIT")&&tagname.substring(startIndex).equals("EQ")){
                    iceboxParameterData.setCumulativeFlow(realValue.getRealValue());
                }else if(tagname.contains("FIT")&&tagname.substring(startIndex).equals("TE")){
                    iceboxParameterData.setCumulativeCooling(realValue.getRealValue());
                    tagNameCumulativeCooling=tagname;
                }
            }

            Map<String,String> ratedGasMap=ratedGasProductionService.getratedGas();
            Double powerRatio=0d;
            //计算额定功率比
            for(RealValue realValue:powerList){
                String tagname=realValue.getRealValue_tagname();
                int startIndex=tagname.lastIndexOf("-")+1;
                if(tagname.substring(startIndex).equals("P")){
                    Double p=0d;
                    if(!StringUtils.isEmpty(realValue.getRealValue())) p=Double.valueOf(realValue.getRealValue());
                    powerRatio=p/Double.valueOf(ratedGasMap.get(tagname));
                    break;
                }
            }

            //计算实时效率和产气效率
            for(RealValue realValue:freezeWaterList){
                String tagname=realValue.getRealValue_tagname();
                int startIndex=tagname.lastIndexOf("-")+1;
                if(tagname.substring(startIndex).equals("FE")){
                    //实时效率
                    Double gasProduction=0d;
                    if(!StringUtils.isEmpty(realValue.getRealValue())) gasProduction=Double.valueOf(realValue.getRealValue());
                    Double realTimeEfficiency=gasProduction/Double.valueOf(ratedGasMap.get(tagname));
                    //效率
                    Double biogasProductionEfficiency=0d;
                    if(powerRatio!=0d) biogasProductionEfficiency=realTimeEfficiency/powerRatio;
                    iceboxParameterData.setEffectiveness(biogasProductionEfficiency+"");
                    break;
                }
            }
            EquipmentStates equipmentStates = equipmentMapper.getEquipmentStates(media);
            iceboxParameterData.SetIceboxParameterData(equipmentStates);
            List<Double> electricConsumption = equipmentMapper.getActiveEnergy("min_power",tagNameElectricConsumption);
            List<Double> cumulativeCooling = equipmentMapper.getActiveEnergy("min_freezeWater",tagNameCumulativeCooling);
            Double effectiveness =0d;
            if((cumulativeCooling.size()>1)&&(electricConsumption.size()>1)&&(cumulativeCooling.get(0)-cumulativeCooling.get(1)!=0)){
                effectiveness=(electricConsumption.get(0)-electricConsumption.get(1))/(cumulativeCooling.get(0)-cumulativeCooling.get(1));
            }
            iceboxParameterData.setElectricConsumption(effectiveness.toString());
            return iceboxParameterData;
        }
    }

    @Override
    public List<EquipmentData> equipmentAnalysis(List<String> tankNumbers, String media, String start_time_1, String end_time_1, String start_time_2, String end_time_2,String maxDateDb,String minDateDb) {
        if(this.compareToDate(minDateDb,end_time_1)&&this.compareToDate(minDateDb,end_time_2)){
            return null;
        }
        List<EquipmentData> resultList=new ArrayList<EquipmentData>();
        List<EquipmentData> list1=new ArrayList<EquipmentData>();
        List<EquipmentData> list2=new ArrayList<EquipmentData>();
        String condition="";
        if(media.equals("空气")){
            condition="airCostValue";
        }else if(media.equals("蒸汽")){
            condition="steamCostValue";
        }
        list1=equipmentMapper.getDataByTimes(tankNumbers,condition,start_time_1,end_time_1);
        list2=equipmentMapper.getDataByTimes(tankNumbers,condition,start_time_2,end_time_2);
        resultList.addAll(list1);
        resultList.addAll(list2);
        for(EquipmentData equip:resultList){
            equip.setMedia(media);
        }
        return resultList;
    }

    @Override
    public Map<String,List<String[]>> equipmentAnalysisBar(List<String> tankNumbers, String media, String start_time_1, String end_time_1, String start_time_2, String end_time_2, String maxDateDb, String minDateDb) {
        if(this.compareToDate(minDateDb,end_time_1)||this.compareToDate(minDateDb,end_time_2)){
            return null;
        }
        Map<String,List<String[]>> resultMap=new HashMap<>();
        List<EquipmentData> list1=new ArrayList<EquipmentData>();
        List<EquipmentData> list2=new ArrayList<EquipmentData>();
        String condition="";
        if(media.equals("空气")){
            condition="airCostValue";
        }else if(media.equals("蒸汽")){
            condition="steamCostValue";
        }
        list1=equipmentMapper.getDataByTimes(tankNumbers,condition,start_time_1,end_time_1);
        List<String[]> time_1List=new ArrayList<String[]>();
        for(String tankNumber:tankNumbers){
            String[] str_1=new String[2];
            str_1[0]=start_time_1+"至"+end_time_1;
            str_1[1]="0";
            for(int index=0;index<list1.size();index++){
                EquipmentData equip=list1.get(index);
                if(tankNumber.equals(equip.getEquipmentName())){
                    str_1[1]=equip.getCostValue();
                    if(StringUtils.isEmpty(str_1[1])) str_1[1]="0";
                    list1.remove(index);
                    index--;
                    break;
                }
            }
            time_1List.add(str_1);
        }
        resultMap.put("time_1",time_1List);
        list2=equipmentMapper.getDataByTimes(tankNumbers,condition,start_time_2,end_time_2);
        List<String[]> time_2List=new ArrayList<String[]>();
        for(String tankNumber:tankNumbers){
            String[] str_2=new String[2];
            str_2[0]=start_time_2+"至"+end_time_2;
            str_2[1]="0";
            for(int index=0;index<list2.size();index++){
                EquipmentData equip=list2.get(index);
                if(tankNumber.equals(equip.getEquipmentName())){
                    str_2[1]=equip.getCostValue();
                    if(StringUtils.isEmpty(str_2[1])) str_2[1]="0";
                    list2.remove(index);
                    index--;
                    break;
                }
            }
            time_2List.add(str_2);
        }
        resultMap.put("time_2",time_2List);
        return resultMap;
    }

    @Override
    public String selectEquipmentRangeDate(String method) {
        return equipmentMapper.selectEquipmentRangeDate(method);
    }

    @Override
    public List<EquipmentStates> getEquipmentStatesAll() {
        return equipmentMapper.getEquipmentStatesAll();
    }

    @Override
    public void batchUpdataEquipmentStates(List<EquipmentStates> equipmentStatesList,String time) {
        equipmentMapper.batchUpdataEquipmentStates(equipmentStatesList,time);
    }

    //比较两个时间的前后(queryDate比dateDb小，就返回true)
    public boolean compareToDate(String dateDb, String queryDate){
        Date date_Db=null;
        Date paraDate=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date_Db = sdf.parse(dateDb);
            paraDate = sdf.parse(queryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(paraDate.compareTo(date_Db)<0){
            return true;
        }else{
            return false;
        }
    }
}
