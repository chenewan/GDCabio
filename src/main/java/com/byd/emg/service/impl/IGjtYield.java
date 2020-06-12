package com.byd.emg.service.impl;

import com.byd.emg.mapper.GjtYieldMapper;
import com.byd.emg.mapper.SectionAnalysisMapper;
import com.byd.emg.pojo.GjtYield;
import com.byd.emg.resultData.GjtEnergyData;
import com.byd.emg.resultData.UnitCostData;
import com.byd.emg.resultData.UnitEnergyData;
import com.byd.emg.service.GjtYieldService;
import com.byd.emg.service.SectionAnalysisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("iGjtYield")
public class IGjtYield implements GjtYieldService {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private GjtYieldMapper gjtYieldMapper;

    @Autowired
    private SectionAnalysisMapper sectionAnalysisMapper;

    @Autowired
    private SectionAnalysisService SectionAnalysisService;

    @Override
    public int insertGjtYield(GjtYield gjtYield) {
        int resultCount=0;
        List<Integer> ids=gjtYieldMapper.selectByTimes(gjtYield);
        if(ids.size()>0){
            resultCount=gjtYieldMapper.batchUpdateByIds(ids,gjtYield);
        }else{
            resultCount=gjtYieldMapper.insertGjtYield(gjtYield);
        }
        return resultCount;
    }

    @Override
    public List<GjtEnergyData> energyData(String startDate, String endDate,String productType,String media) {
        List<GjtEnergyData> gjtEnergyDataList=new ArrayList<GjtEnergyData>();
        String tableName="";
        String fields="";
        if(media.equals("干菌体")){
            tableName="gjt_yield";
            fields="id,productType,yields,startDate,endDate,produceDays,bigPotNumbers,middlePotNumbers,smallPotNumbers,bigSpanNumbers,middleSpanNumbers,smallSpanNumbers";
        }else if(media.equals("毛油")){
            tableName="my_yield";
            fields="id,productType,yields,produceDays,startDate,endDate";
        }else if(media.equals("成品油")){
            tableName="cpy_yield";
            fields="id,productType,yields,produceDays,startDate,endDate";
        }
        gjtEnergyDataList=gjtYieldMapper.selectEnergyDataConditon(startDate,endDate,productType,tableName,fields);
        //计算总产量
        Double totalYields=gjtYieldMapper.selectTotalYieldsCondition(startDate,endDate,productType,tableName);
        if(totalYields==null)  totalYields=0d;
        //计算总用电、蒸汽、自来水
        Map<String,Double> resultMap=this.totalConsumption(media, gjtEnergyDataList);
        Double totalElectrity=resultMap.get("totalElectrity");
        Double totalTapwater=resultMap.get("totalTapwater");
        Double totalSteam=resultMap.get("totalSteam");
        DecimalFormat df = new DecimalFormat("0.00");
        for(GjtEnergyData gjtEnergyData:gjtEnergyDataList){
            Double yiedlds=0d;
            if(!StringUtils.isEmpty(gjtEnergyData.getYields())) yiedlds=Double.valueOf(gjtEnergyData.getYields());
            Double electricEnergy=0d;
            Double tapwater=0d;
            Double steam=0d;
            if(totalYields!=0){
                electricEnergy=totalElectrity*(yiedlds/totalYields);
                tapwater=totalTapwater*(yiedlds/totalYields);
                steam=totalSteam*(yiedlds/totalYields);
            }
            gjtEnergyData.setElectricEnergy(df.format(electricEnergy));
            gjtEnergyData.setTapwater(df.format(tapwater));
            gjtEnergyData.setSteam(df.format(steam));
        }
        return gjtEnergyDataList;
    }

    @Override
    public List<UnitEnergyData> unitEnergyData(String media) {
        List<GjtEnergyData> gjtEnergyDataList=new ArrayList<GjtEnergyData>();
        List<UnitEnergyData> resultList=new ArrayList<>();
        String tableName="";
        String fields="";
        if(media.equals("干菌体")){
            tableName="gjt_yield";
            fields="id,productType,yields,startDate,endDate,produceDays,bigPotNumbers,middlePotNumbers,smallPotNumbers,bigSpanNumbers,middleSpanNumbers,smallSpanNumbers";
        }else if(media.equals("毛油")){
            tableName="my_yield";
            fields="id,productType,yields,produceDays,startDate,endDate";
        }else if(media.equals("成品油")){
            tableName="cpy_yield";
            fields="id,productType,yields,produceDays,startDate,endDate";
        }
        gjtEnergyDataList=gjtYieldMapper.selectEnergyData(tableName,fields);
        //计算总产量
        Double totalYields=gjtYieldMapper.selectTotalYields(tableName);
        if(totalYields==null)  totalYields=0d;
        //计算总用电、蒸汽、自来水
        Map<String,Double> resultMap=this.totalConsumption(media, gjtEnergyDataList);
        Double totalElectrity=resultMap.get("totalElectrity");
        Double totalTapwater=resultMap.get("totalTapwater");
        Double totalSteam=resultMap.get("totalSteam");
        //计算总用循环水、冷冻水、发酵空气(未提供公式)
        Map<String,Double> totalMap=this.totalConsumption_2(media, gjtEnergyDataList);
        Double totalCirculateWater=totalMap.get("totalCirculateWater");
        Double totalFreezeWater=totalMap.get("totalFreezeWater");
        Double totalFaJiaoAir=totalMap.get("totalFaJiaoAir");
        DecimalFormat df = new DecimalFormat("0.00");
        for(GjtEnergyData gjtEnergyData:gjtEnergyDataList){
            Double yiedlds=0d;
            if(!StringUtils.isEmpty(gjtEnergyData.getYields())) yiedlds=Double.valueOf(gjtEnergyData.getYields());
            Double unitPower=0d;
            Double unitTapwater=0d;
            Double unitSteam=0d;
            Double unitCirculateWater=0d;
            Double unitFreezeWater=0d;
            Double unitFaJiaoAir=0d;
            if(totalYields!=0&&yiedlds!=0){
                unitPower=totalElectrity*(yiedlds/totalYields)/yiedlds;
                unitTapwater=totalTapwater*(yiedlds/totalYields)/yiedlds;
                unitSteam=totalSteam*(yiedlds/totalYields)/yiedlds;
                unitCirculateWater=totalCirculateWater*(yiedlds/totalYields)/yiedlds;
                unitFreezeWater=totalFreezeWater*(yiedlds/totalYields)/yiedlds;
                unitFaJiaoAir=totalFaJiaoAir*(yiedlds/totalYields)/yiedlds;
            }
            resultList.add(new UnitEnergyData(gjtEnergyData.getYields(),gjtEnergyData.getProductType(),df.format(unitPower),df.format(unitTapwater),df.format(unitSteam),df.format(unitCirculateWater),df.format(unitFreezeWater),df.format(unitFaJiaoAir),gjtEnergyData.getStartDate(),gjtEnergyData.getEndDate()));
        }
        return resultList;
    }

    @Override
    public List<UnitCostData> unitCost(String media) {
        List<GjtEnergyData> gjtEnergyDataList=new ArrayList<GjtEnergyData>();
        List<UnitCostData> resultList=new ArrayList<UnitCostData>();
        String tableName="";
        String fields="";
        if(media.equals("干菌体")){
            tableName="gjt_yield";
            fields="id,productType,yields,startDate,endDate,produceDays,bigPotNumbers,middlePotNumbers,smallPotNumbers,bigSpanNumbers,middleSpanNumbers,smallSpanNumbers,unitPricePower,unitPriceWater,unitPriceCircuWater,unitPriceFreezeWater,unitPriceSteam,unitPriceAir";
        }else if(media.equals("毛油")){
            tableName="my_yield";
            fields="id,productType,yields,produceDays,unitPricePower,unitPriceWater,unitPriceCircuWater,unitPriceFreezeWater,unitPriceSteam,unitPriceAir,startDate,endDate";
        }else if(media.equals("成品油")){
            tableName="cpy_yield";
            fields="id,productType,yields,produceDays,unitPricePower,unitPriceWater,unitPriceCircuWater,unitPriceFreezeWater,unitPriceSteam,unitPriceAir,startDate,endDate";
        }
        gjtEnergyDataList=gjtYieldMapper.selectEnergyData(tableName,fields);
        //计算总产量
        Double totalYields=gjtYieldMapper.selectTotalYields(tableName);
        if(totalYields==null)  totalYields=0d;
        //计算总用电、蒸汽、自来水
        Map<String,Double> resultMap=this.totalConsumption(media, gjtEnergyDataList);
        Double totalElectrity=resultMap.get("totalElectrity");
        Double totalTapwater=resultMap.get("totalTapwater");
        Double totalSteam=resultMap.get("totalSteam");
        //计算总用循环水、冷冻水、发酵空气(未提供公式)
        Map<String,Double> totalMap=this.totalConsumption_2(media, gjtEnergyDataList);
        Double totalCirculateWater=totalMap.get("totalCirculateWater");
        Double totalFreezeWater=totalMap.get("totalFreezeWater");
        Double totalFaJiaoAir=totalMap.get("totalFaJiaoAir");
        DecimalFormat df = new DecimalFormat("0.00");
        for(GjtEnergyData gjtEnergyData:gjtEnergyDataList){
            Double yiedlds=0d;
            if(!StringUtils.isEmpty(gjtEnergyData.getYields())) yiedlds=Double.valueOf(gjtEnergyData.getYields());
            Double unitPricePower=0d;
            if(!StringUtils.isEmpty(gjtEnergyData.getUnitPricePower())) unitPricePower=Double.valueOf(gjtEnergyData.getUnitPricePower());
            Double unitPriceWater=0d;
            if(!StringUtils.isEmpty(gjtEnergyData.getUnitPriceWater())) unitPriceWater=Double.valueOf(gjtEnergyData.getUnitPriceWater());
            Double unitPriceCircuWater=0d;
            if(!StringUtils.isEmpty(gjtEnergyData.getUnitPriceCircuWater())) unitPriceCircuWater=Double.valueOf(gjtEnergyData.getUnitPriceCircuWater());
            Double unitPriceFreezeWater=0d;
            if(!StringUtils.isEmpty(gjtEnergyData.getUnitPriceFreezeWater())) unitPriceFreezeWater=Double.valueOf(gjtEnergyData.getUnitPriceFreezeWater());
            Double unitPriceSteam=0d;
            if(!StringUtils.isEmpty(gjtEnergyData.getUnitPriceSteam())) unitPriceSteam=Double.valueOf(gjtEnergyData.getUnitPriceSteam());
            Double unitPriceAir=0d;
            if(!StringUtils.isEmpty(gjtEnergyData.getUnitPriceAir())) unitPriceAir=Double.valueOf(gjtEnergyData.getUnitPriceAir());
            Double powerCost=0d;
            Double tapwaterCost=0d;
            Double steamCost=0d;
            Double circulateWaterCost=0d;
            Double freezeWaterCost=0d;
            Double unitCost=0d;
            Double faJiaoAirCost=totalFaJiaoAir*(yiedlds/totalYields)*unitPriceAir;
            if(totalYields!=0d){
                powerCost=totalElectrity*(yiedlds/totalYields)*unitPricePower;
                tapwaterCost=totalTapwater*(yiedlds/totalYields)*unitPriceWater;
                steamCost=totalSteam*(yiedlds/totalYields)*unitPriceSteam;
                circulateWaterCost=totalCirculateWater*(yiedlds/totalYields)*unitPriceCircuWater;
                freezeWaterCost=totalFreezeWater*(yiedlds/totalYields)*unitPriceFreezeWater;
            }
            if(yiedlds!=0d){
                unitCost=(powerCost+tapwaterCost+steamCost+circulateWaterCost+freezeWaterCost+faJiaoAirCost)/yiedlds;
            }
            resultList.add(new UnitCostData(gjtEnergyData.getYields(),gjtEnergyData.getProductType(),df.format(powerCost),df.format(tapwaterCost),df.format(steamCost),df.format(circulateWaterCost),df.format(freezeWaterCost),df.format(faJiaoAirCost),df.format(unitCost),gjtEnergyData.getStartDate(),gjtEnergyData.getEndDate()));
        }
        return resultList;
    }

    @Override
    public List<GjtYield> selectByProductType(String date, String productType) {
        //默认显示前5条
        String topMethod="top 5";
        if(!StringUtils.isEmpty(date)) topMethod="";
        return gjtYieldMapper.selectByProductType(date,productType,topMethod);
    }

    @Override
    public int updateGjtYield(GjtYield gjtYield) {
        return gjtYieldMapper.updateGjtYield(gjtYield);
    }

    @Override
    public int deleteGjtById(Long id) {
        return gjtYieldMapper.deleteGjtById(id);
    }

    @Override
    public Double getProductionDays(String startDate,String endDate, String type, String tableName) {
        List<GjtYield> gjtYieldList=gjtYieldMapper.selectGjtYields(startDate,endDate,type,tableName);
        Double totalDays=0d;
        for(GjtYield data:gjtYieldList){
            if(StringUtils.isEmpty(data.getProduceDays())) data.setProduceDays("0");
            totalDays+=Integer.valueOf(data.getProduceDays());
        }
        return totalDays;
    }

    //计算总用电、蒸汽、自来水
    public Map<String,Double> totalConsumption(String media, List<GjtEnergyData> gjtEnergyDataList) {
        Map<String,Double> resultMap=new HashMap<String,Double>();
        Double totalElectrity=0d;
        Double totalTapwater=0d;
        Double totalSteam=0d;
        int lastIndex=gjtEnergyDataList.size();
        if(media.equals("干菌体")){
            if(gjtEnergyDataList.size()>0){
                String start_1=gjtEnergyDataList.get(0).getStartDate();
                String end_1=gjtEnergyDataList.get(lastIndex-1).getEndDate();
                if (sdf.format(new Date()).equals(end_1.trim())) {
                    end_1 = "-1";
                }
                Double fajiao_power=0d;
                fajiao_power = sectionAnalysisMapper.fajiao_power(start_1,end_1);
                if(fajiao_power==null) fajiao_power=0d;
                Double yuchuli_power=0d;
                yuchuli_power=sectionAnalysisMapper.yuchuli_power(start_1,end_1);
                Double fjkqydl=0d;
                fjkqydl=sectionAnalysisMapper.yasuo_power(start_1,end_1);
                Double fajiao_circulatingWate_TE=0d;
                fajiao_circulatingWate_TE =sectionAnalysisMapper.fajiao_circulatingWate_TE(start_1,end_1);
                if(fajiao_circulatingWate_TE==null) fajiao_circulatingWate_TE=0d;
                if(fjkqydl==null) fjkqydl=0d;
                Double zxhs=0d;
                zxhs=sectionAnalysisMapper.zongxunhuanshui_TE(start_1,end_1);
                if(zxhs==null) zxhs=0d;
                Double xhsydl=0d;
                xhsydl=sectionAnalysisMapper.xunhuan_power(start_1,end_1);
                if(xhsydl==null) xhsydl=0d;
                Double fajiao_freezeWater_TE=0d;
                fajiao_freezeWater_TE =sectionAnalysisMapper.fajiao_freezeWater_TE(start_1,end_1);
                if(fajiao_freezeWater_TE==null) fajiao_freezeWater_TE=0d;
                Double dongli_freezeWater_TE=0d;
                dongli_freezeWater_TE = sectionAnalysisMapper.dongli_freezeWater_TE(start_1,end_1);
                if(dongli_freezeWater_TE==null) dongli_freezeWater_TE=0d;
                Double ldsydl=0d;
                ldsydl=sectionAnalysisMapper.lengdong_power(start_1,end_1);
                if(ldsydl==null) ldsydl=0d;
                Double ybkqydl=0d;
                ybkqydl=sectionAnalysisMapper.yibiao_power(start_1,end_1);
                if(ybkqydl==null) ybkqydl=0d;
                Double guolu_power = sectionAnalysisMapper.guolu_power(start_1,end_1);
                if(guolu_power==null) guolu_power=0d;
                Double wushui_power = sectionAnalysisMapper.wushui_power(start_1,end_1);
                if(wushui_power==null) wushui_power=0d;
                Double dongli_power =sectionAnalysisMapper.dongli_power(start_1,end_1);
                if(dongli_power==null) dongli_power=0d;
                Double ldsxhtxt=sectionAnalysisMapper.ldsxhtxt(start_1,end_1);
                if(ldsxhtxt==null) ldsxhtxt=0d;
                Double xhsbtxt=sectionAnalysisMapper.xhsbtxt(start_1,end_1);
                if(xhsbtxt==null) xhsbtxt=0d;
                Double qykyj=sectionAnalysisMapper.qykyj(start_1,end_1);
                if(qykyj==null) qykyj=0d;
                Double kyjbtxt=sectionAnalysisMapper.kyjbtxt(start_1,end_1);
                if(kyjbtxt==null) kyjbtxt=0d;
                Double dlgybf_power = dongli_power-ldsxhtxt-xhsbtxt-qykyj-kyjbtxt-wushui_power-guolu_power;
                Double fajiao_steamMeter = sectionAnalysisMapper.fajiao_steamMeter(start_1,end_1);
                if(fajiao_steamMeter==null) fajiao_steamMeter=0d;
                Double zzq =sectionAnalysisMapper.zong_steamMeter(start_1,end_1);
                if(zzq==null) zzq=0d;
                if(zxhs*xhsydl!=0&&dongli_freezeWater_TE*ldsydl!=0&&zzq!=0) totalElectrity = fajiao_power+ yuchuli_power+fjkqydl+(fajiao_circulatingWate_TE/zxhs*xhsydl)+(fajiao_freezeWater_TE/dongli_freezeWater_TE*ldsydl)+0.2*ybkqydl + 0.4*dlgybf_power+(fajiao_steamMeter/zzq*1)+/* 蒸汽/锅炉用电量不明确*/0.6*wushui_power;

                /*  干菌体用水*/
                Double fajiao_tapwater = sectionAnalysisMapper.fajiao_tapwater(start_1,end_1);
                if(fajiao_tapwater==null) fajiao_tapwater=0d;
                Double dongli_tapwater =sectionAnalysisMapper.dongli_tapwater(start_1,end_1);
                if(dongli_tapwater==null) dongli_tapwater=0d;
                Double guolu_tapwater = sectionAnalysisMapper.guolu_tapwater(start_1,end_1);
                if(guolu_tapwater==null) guolu_tapwater=0d;
                Double wushui_tapwater = sectionAnalysisMapper.wushui_tapwater(start_1,end_1);
                if(wushui_tapwater==null) wushui_tapwater=0d;
                Double jianglian_tapwater = sectionAnalysisMapper.jinglian_tapwater(start_1,end_1);
                if(jianglian_tapwater==null) jianglian_tapwater=0d;
                if(fajiao_tapwater+jianglian_tapwater!=0) totalTapwater=fajiao_tapwater+dongli_tapwater+guolu_tapwater+wushui_tapwater*(fajiao_tapwater/(fajiao_tapwater+jianglian_tapwater));

                /*  干菌体用蒸汽*/
                List<String> tagnemeList=new ArrayList<>();
                //连消
                tagnemeList.add("FIT-012001-02-EQ");tagnemeList.add("FIT-010506-17-EQ");tagnemeList.add("FIT-010505-17-EQ");
                //发酵
                tagnemeList.add("Fit-012001-02-EQ");tagnemeList.add("Fit-012001-07-EQ");tagnemeList.add("Fit-012001-09-EQ");
                //烘干
                tagnemeList.add("FIT-012001-09-EQ");
                //污水
                tagnemeList.add("Fit-092501-09-EQ");
                //精炼
                tagnemeList.add("Fit-031001-01-EQ");
                //自用蒸汽(锅炉自用蒸汽)
                tagnemeList.add("Fit-100601-04-EQ");
                Map<String,Double> consumptionMap=SectionAnalysisService.getConsumptionValue(tagnemeList,start_1,end_1,"0","day_steamMeter","realValue_steamMeter_table");
                Double lianxiao_steamMeter=consumptionMap.get("FIT-012001-02-EQ")+consumptionMap.get("FIT-010506-17-EQ")+consumptionMap.get("FIT-010505-17-EQ");
                Double fajiao_steamMeter_2=consumptionMap.get("Fit-012001-02-EQ")+consumptionMap.get("Fit-012001-07-EQ")+consumptionMap.get("Fit-012001-09-EQ");
                Double honggan_steamMeter=consumptionMap.get("FIT-012001-09-EQ");
                Double wushui_steamMeter=consumptionMap.get("Fit-092501-09-EQ");
                Double jinglian_steamMeter=consumptionMap.get("Fit-031001-01-EQ");
                Double ziyong_steamMeter=consumptionMap.get("Fit-100601-04-EQ");
                if(fajiao_steamMeter_2+jinglian_steamMeter!=0){
                    totalSteam=lianxiao_steamMeter+fajiao_steamMeter_2+honggan_steamMeter+wushui_steamMeter*fajiao_steamMeter_2/(fajiao_steamMeter_2+jinglian_steamMeter)+ziyong_steamMeter*fajiao_steamMeter_2/(fajiao_steamMeter_2+jinglian_steamMeter);
                }
            }

        }else if(media.equals("毛油")){
            if(gjtEnergyDataList.size()>0){
                String start_1=gjtEnergyDataList.get(0).getStartDate();
                String end_1=gjtEnergyDataList.get(lastIndex-1).getEndDate();
                if (sdf.format(new Date()).equals(end_1.trim())) {
                    end_1 = "-1";
                }
                /*  毛油用电*/
                Double ybkqydl=sectionAnalysisMapper.yibiao_power(start_1,end_1);
                if(ybkqydl==null) ybkqydl=0d;
                Double jinchu_power = sectionAnalysisMapper.jinchu_power(start_1,end_1);
                if(jinchu_power==null) jinchu_power=0d;
                Double dongli_power =sectionAnalysisMapper.dongli_power(start_1,end_1);
                if(dongli_power==null) dongli_power=0d;
                Double ldsxhtxt=sectionAnalysisMapper.ldsxhtxt(start_1,end_1);
                if(ldsxhtxt==null) ldsxhtxt=0d;
                Double xhsbtxt=sectionAnalysisMapper.xhsbtxt(start_1,end_1);
                if(xhsbtxt==null) xhsbtxt=0d;
                Double qykyj=sectionAnalysisMapper.qykyj(start_1,end_1);
                if(qykyj==null) qykyj=0d;
                Double kyjbtxt=sectionAnalysisMapper.kyjbtxt(start_1,end_1);
                if(kyjbtxt==null) kyjbtxt=0d;
                Double wushui_power = sectionAnalysisMapper.wushui_power(start_1,end_1);
                if(wushui_power==null) wushui_power=0d;
                Double guolu_power = sectionAnalysisMapper.guolu_power(start_1,end_1);
                if(guolu_power==null) guolu_power=0d;
                Double dlgybf_power = dongli_power-ldsxhtxt-xhsbtxt-qykyj-kyjbtxt-wushui_power-guolu_power;
                //锅炉比例分摊
                Double fajiaoSteamMeter=sectionAnalysisMapper.fajiao_steamMeter(start_1,end_1);
                if(fajiaoSteamMeter==null)   fajiaoSteamMeter=0d;
                //蒸汽总用量的tagname的集合(FIT-100601-03-EQ、FIT-100601-02-EQ  外来)
                List<String> zongSteamList=new ArrayList<>();
                zongSteamList.add("FIT-100601-03-EQ");zongSteamList.add("FIT-100601-02-EQ");
                Map<String,Double> zongSteamMap=SectionAnalysisService.getConsumptionValue(zongSteamList,start_1,end_1,"0","day_steamMeter","realValue_steamMeter_table");
                Double zong_steamMeter=zongSteamMap.get("FIT-100601-03-EQ")+zongSteamMap.get("FIT-100601-02-EQ");
                if(zong_steamMeter==null)   zong_steamMeter=0d;
                Double guoluProportion=0d;
                if(zong_steamMeter!=0) guoluProportion=guolu_power*(fajiaoSteamMeter/zong_steamMeter);
                totalElectrity=jinchu_power+0.15*ybkqydl+0.3*dlgybf_power+1+guoluProportion+0.1*wushui_power;

                /*  毛油用水*/
                totalTapwater=0d;

                /*  毛油用蒸汽*/
                Double jinchu=0d;
                Double wushui_steamMeter=sectionAnalysisMapper.wushui_steamMeter(start_1,end_1);
                if(wushui_steamMeter==null) wushui_steamMeter=0d;
                Double jinglian_steamMeter=sectionAnalysisMapper.jinglian_steamMeter(start_1,end_1);
                if(jinglian_steamMeter==null) jinglian_steamMeter=0d;
                Double guolu_steamMeter=sectionAnalysisMapper.guolu_steamMeter(start_1,end_1);
                if(guolu_steamMeter==null) guolu_steamMeter=0d;
                if(fajiaoSteamMeter+jinglian_steamMeter!=0) totalSteam=jinchu+wushui_steamMeter*jinchu/(fajiaoSteamMeter+jinglian_steamMeter)+guolu_steamMeter*(fajiaoSteamMeter+jinglian_steamMeter);

            }
        }else if(media.equals("成品油")){
            if(gjtEnergyDataList.size()>0){
                String start_1=gjtEnergyDataList.get(0).getStartDate();
                String end_1=gjtEnergyDataList.get(lastIndex-1).getEndDate();
                if (sdf.format(new Date()).equals(end_1.trim())) {
                    end_1 = "-1";
                }
                /*  成品油用电*/
                Double jianglian_power = sectionAnalysisMapper.jinglian_power(start_1,end_1);
                if(jianglian_power==null) jianglian_power=0d;
                Double jianglian_circulatingWate_TE = sectionAnalysisMapper.jinglian_circulatingWate_TE(start_1,end_1);
                if(jianglian_circulatingWate_TE==null) jianglian_circulatingWate_TE=0d;
                Double zxhs=sectionAnalysisMapper.zongxunhuanshui_TE(start_1,end_1);
                if(zxhs==null) zxhs=0d;
                Double xhsydl=sectionAnalysisMapper.xunhuan_power(start_1,end_1);
                if(xhsydl==null) xhsydl=0d;
                Double wushui_power = sectionAnalysisMapper.wushui_power(start_1,end_1);
                if(wushui_power==null) wushui_power=0d;
                //锅炉比例分摊
                Double fajiaoSteamMeter=sectionAnalysisMapper.fajiao_steamMeter(start_1,end_1);
                if(fajiaoSteamMeter==null)   fajiaoSteamMeter=0d;
                //蒸汽总用量的tagname的集合(FIT-100601-03-EQ、FIT-100601-02-EQ  外来)
                List<String> zongSteamList=new ArrayList<>();
                zongSteamList.add("FIT-100601-03-EQ");zongSteamList.add("FIT-100601-02-EQ");
                Map<String,Double> zongSteamMap=SectionAnalysisService.getConsumptionValue(zongSteamList,start_1,end_1,"0","day_steamMeter","realValue_steamMeter_table");
                Double zong_steamMeter=zongSteamMap.get("FIT-100601-03-EQ")+zongSteamMap.get("FIT-100601-02-EQ");
                if(zong_steamMeter==null)   zong_steamMeter=0d;
                Double guoluProportion=0d;
                Double guolu_power = sectionAnalysisMapper.guolu_power(start_1,end_1);
                if(guolu_power==null) guolu_power=0d;
                if(zong_steamMeter!=0) guoluProportion=guolu_power*(fajiaoSteamMeter/zong_steamMeter);
                if(zxhs*xhsydl!=0) totalElectrity =jianglian_power+(jianglian_circulatingWate_TE/zxhs*xhsydl)+guoluProportion+0.3*wushui_power;

                /*  成品油用水*/
                Double jianglian_tapwater = sectionAnalysisMapper.jinglian_tapwater(start_1,end_1);
                if(jianglian_tapwater==null) jianglian_tapwater=0d;
                Double dongli_tapwater =sectionAnalysisMapper.dongli_tapwater(start_1,end_1);
                if(dongli_tapwater==null) dongli_tapwater=0d;
                Double guolu_tapwater = sectionAnalysisMapper.guolu_tapwater(start_1,end_1);
                if(guolu_tapwater==null) guolu_tapwater=0d;
                Double wushui_tapwater = sectionAnalysisMapper.wushui_tapwater(start_1,end_1);
                if(wushui_tapwater==null) wushui_tapwater=0d;
                Double fajiao_tapwater = sectionAnalysisMapper.fajiao_tapwater(start_1,end_1);
                if(fajiao_tapwater==null) fajiao_tapwater=0d;
                if(fajiao_tapwater+jianglian_tapwater!=0) totalTapwater=jianglian_tapwater+(dongli_tapwater+guolu_tapwater+wushui_tapwater)*(jianglian_tapwater/(fajiao_tapwater+jianglian_tapwater));

                /*  成品油用蒸汽*/
                Double jinchu=0d;
                Double wushui_steamMeter=sectionAnalysisMapper.wushui_steamMeter(start_1,end_1);
                if(wushui_steamMeter==null) wushui_steamMeter=0d;
                Double jinglian_steamMeter=sectionAnalysisMapper.jinglian_steamMeter(start_1,end_1);
                if(jinglian_steamMeter==null) jinglian_steamMeter=0d;
                Double guolu_steamMeter=sectionAnalysisMapper.guolu_steamMeter(start_1,end_1);
                if(guolu_steamMeter==null) guolu_steamMeter=0d;
                if(fajiaoSteamMeter+jinglian_steamMeter!=0) totalSteam =(jinglian_steamMeter-jinchu)+wushui_steamMeter*(jinglian_steamMeter-jinchu)/(fajiaoSteamMeter+jinglian_steamMeter)+guolu_steamMeter*(jinglian_steamMeter-jinchu)/(fajiaoSteamMeter+jinglian_steamMeter);
            }
        }
        resultMap.put("totalElectrity",totalElectrity);
        resultMap.put("totalTapwater",totalTapwater);
        resultMap.put("totalSteam",totalSteam);
        return resultMap;
    }

    //计算总用循环水、冷冻水、发酵空气(只有干菌体才会用到发酵空气)
    private Map<String, Double> totalConsumption_2(String media, List<GjtEnergyData> gjtEnergyDataList) {
        Map<String,Double> resultMap=new HashMap<String,Double>();
        Double totalCirculateWater=0d;
        Double totalFreezeWater=0d;
        Double totalFaJiaoAir=0d;
        int lastIndex=gjtEnergyDataList.size();
        if(media.equals("干菌体")){
            if(gjtEnergyDataList.size()>0) {
                String start_1=gjtEnergyDataList.get(0).getStartDate();
                String end_1=gjtEnergyDataList.get(lastIndex-1).getEndDate();
                if (sdf.format(new Date()).equals(end_1.trim())) {
                    end_1 = "-1";
                }
                //干菌体用循环水
                List<String> tagnameList_1=new ArrayList<>();tagnameList_1.add("FIT-092101-05-EQ");
                Double gjtCirculateWater=SectionAnalysisService.getConsumptionValue(tagnameList_1,start_1,end_1,"","day_circulatingWate","realValue_circulatingWater_table").get("FIT-092101-05-EQ");
                Double zongxunhuanshui_EP=sectionAnalysisMapper.zongxunhuanshui_EP(start_1,end_1);
                if(zongxunhuanshui_EP==null) zongxunhuanshui_EP=0d;
                List<String> tagnameList_2=new ArrayList<>();tagnameList_2.add("FIT-092202-02-EQ");
                Double tuochouCirculateWater=SectionAnalysisService.getConsumptionValue(tagnameList_2,start_1,end_1,"0","day_circulatingWate","realValue_circulatingWater_table").get("FIT-092202-02-EQ");
                if(zongxunhuanshui_EP!=0d) totalCirculateWater=gjtCirculateWater*(zongxunhuanshui_EP-tuochouCirculateWater)/zongxunhuanshui_EP;

                //干菌体用冷冻水 (总冷量-原料库用冷量-精炼车间冷量)
                List<String> tagnameList_3=new ArrayList<>();tagnameList_3.add("FIT-092202-04-TE");tagnameList_3.add("FIT-092202-01-TE");
                Map<String,Double> teWaterMap=SectionAnalysisService.getConsumptionValue(tagnameList_3,start_1,end_1,"0","day_freezeWater","realValue_freezeWater_table");
                Double jingLianFreeWater=sectionAnalysisMapper.jianglian_freezeWater_TE(start_1,end_1);
                if(jingLianFreeWater==null) jingLianFreeWater=0d;
                totalFreezeWater=teWaterMap.get("FIT-092202-01-TE")-teWaterMap.get("FIT-092202-04-TE")-jingLianFreeWater;

                //干菌体用发酵空气
                Double fajiao_fermentationAir=sectionAnalysisMapper.fajiao_fermentationAir(start_1,end_1);
                if(fajiao_fermentationAir==null) fajiao_fermentationAir=0d;
                totalFaJiaoAir=fajiao_fermentationAir;
            }
        }else if(media.equals("毛油")){
            if(gjtEnergyDataList.size()>0){
                String start_1=gjtEnergyDataList.get(0).getStartDate();
                String end_1=gjtEnergyDataList.get(lastIndex-1).getEndDate();
                if (sdf.format(new Date()).equals(end_1.trim())) {
                    end_1 = "-1";
                }
                //毛油用循环水
                totalCirculateWater=0d;

                //毛油用冷冻水
                totalFreezeWater=0d;

            }
        }else if(media.equals("成品油")){
            if(gjtEnergyDataList.size()>0){
                String start_1=gjtEnergyDataList.get(0).getStartDate();
                String end_1=gjtEnergyDataList.get(lastIndex-1).getEndDate();
                if (sdf.format(new Date()).equals(end_1.trim())) {
                    end_1 = "-1";
                }
                //成品油用循环水
                List<String> tagnameList=new ArrayList<>(); tagnameList.add("FIT-092202-02-EQ");
                totalCirculateWater=SectionAnalysisService.getConsumptionValue(tagnameList,start_1,end_1,"0","day_circulatingWate","realValue_circulatingWater_table").get("FIT-092202-02-EQ");

                //成品油用冷冻水
                Double jingLianFreeWater=sectionAnalysisMapper.jianglian_freezeWater_TE(start_1,end_1);
                if(jingLianFreeWater==null) jingLianFreeWater=0d;
                totalFreezeWater=jingLianFreeWater;
            }
        }
        resultMap.put("totalCirculateWater",totalCirculateWater);
        resultMap.put("totalFreezeWater",totalFreezeWater);
        resultMap.put("totalFaJiaoAir",totalFaJiaoAir);
        return resultMap;
    }
}
