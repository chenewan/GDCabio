package com.byd.emg.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.common.ParameterUtil;
import com.byd.emg.mapper.InstrumentMapper;
import com.byd.emg.mapper.SectionAnalysisMapper;
import com.byd.emg.pojo.CycleValue;
import com.byd.emg.pojo.Instrument;
import com.byd.emg.pojo.SectionAnalysis;
import com.byd.emg.pojo.TimeJob;
import com.byd.emg.resultData.SankeyData;
import com.byd.emg.service.SectionAnalysisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("SectionAnalysisService")
public class SectionAnalysisServiceImpl implements SectionAnalysisService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final String END_TIME = "-1";
    private static final String FAJIAO = "fajiao";
    private static final String YUCHULI = "yuchuli";
    private static final String JINCHU = "jinchu";
    private static final String JINGLIAN = "jinglian";
    private static final String DONGLI = "dongli";
    private static final String GUOLU = "guolu";
    private static final String WUSHUI = "wushui";
    private static final String QC = "qc";
    private static final String YUNXING = "yunxing";
    private static final String XINGZHENG = "xingzheng";
    private static final String NULL_STRING = "";

    private static final Double PREVALUE = 0d;

    @Autowired
    private  SectionAnalysisMapper sectionAnalysisMapper;

    @Autowired
    private InstrumentMapper instrumentMapper;

    @Override
    public List< List<SectionAnalysis>> getsectionAnalysis(List<String> Section, String Start_time_1, String End_time_1,String Start_time_2,String End_time_2,String state,String maxDateDb,String minDateDb) {
        String Section_name;
        List<SectionAnalysis> resultList=new ArrayList();
        List<SectionAnalysis> resultList2=new ArrayList();
        //同比百分比
        List<SectionAnalysis> resultList3=new ArrayList();
        if(this.compareToDate(minDateDb,End_time_1)||this.compareToDate(minDateDb,End_time_2)){
            return null;
        }
        List<List<SectionAnalysis>>resultListAll  = new ArrayList();
        resultList=this.getHomePageData_3(Section,Start_time_1,End_time_1);
        SectionAnalysis sectionAnalysis_1=new SectionAnalysis();
        for(SectionAnalysis sectionAnalysis:resultList){
            sectionAnalysis_1=sectionAnalysis;
            if (state.equals("1")){
                sectionAnalysis.setDate_time(Start_time_1.substring(0,7));
            }else if (state.equals("2")){
                sectionAnalysis.setDate_time(Start_time_1+"至"+End_time_1);
            }else if (state.equals("3")){
                sectionAnalysis.setDate_time(Start_time_1.substring(0,7));
            }else if (state.equals("4")){
                sectionAnalysis.setDate_time(Start_time_1+"至"+End_time_1);
            }
        }
        resultListAll.add(resultList);
        resultList2=this.getHomePageData_3(Section,Start_time_2,End_time_2);
        SectionAnalysis sectionAnalysis_2=new SectionAnalysis();
        for(SectionAnalysis sectionAnalysis:resultList2){
            sectionAnalysis_2=sectionAnalysis;
            if (state.equals("1")){
                sectionAnalysis.setDate_time(Start_time_2.substring(0,7));
            }else if (state.equals("2")){
                sectionAnalysis.setDate_time(Start_time_2+"至"+End_time_2);
            }else if (state.equals("3")){
                sectionAnalysis.setDate_time(Start_time_2.substring(0,7));
            }else if (state.equals("4")){
                sectionAnalysis.setDate_time(Start_time_2+"至"+End_time_2);
            }
        }
        resultListAll.add(resultList2);
        SectionAnalysis sectionAnalysis_per=new SectionAnalysis();
        sectionAnalysis_per.setActive_electric_energy(getPercentage(sectionAnalysis_2.getActive_electric_energy(),sectionAnalysis_1.getActive_electric_energy()));
        sectionAnalysis_per.setCumulative_flow_of_tap_water(getPercentage(sectionAnalysis_2.getCumulative_flow_of_tap_water(),sectionAnalysis_1.getCumulative_flow_of_tap_water()));
        sectionAnalysis_per.setCumulative_flow_of_circulating_water(getPercentage(sectionAnalysis_2.getCumulative_flow_of_circulating_water(),sectionAnalysis_1.getCumulative_flow_of_circulating_water()));
        sectionAnalysis_per.setCumulative_Cooling_Value_of_Circulating_Water(getPercentage(sectionAnalysis_2.getCumulative_Cooling_Value_of_Circulating_Water(),sectionAnalysis_1.getCumulative_Cooling_Value_of_Circulating_Water()));
        sectionAnalysis_per.setCumulative_flow_of_frozen_water(getPercentage(sectionAnalysis_2.getCumulative_flow_of_frozen_water(),sectionAnalysis_1.getCumulative_flow_of_frozen_water()));
        sectionAnalysis_per.setCumulative_cold_value_of_frozen_water(getPercentage(sectionAnalysis_2.getCumulative_cold_value_of_frozen_water(),sectionAnalysis_1.getCumulative_cold_value_of_frozen_water()));
        sectionAnalysis_per.setCumulative_steam_flow(getPercentage(sectionAnalysis_2.getCumulative_steam_flow(),sectionAnalysis_1.getCumulative_steam_flow()));
        sectionAnalysis_per.setCumulative_air_flow(getPercentage(sectionAnalysis_2.getCumulative_air_flow(),sectionAnalysis_1.getCumulative_air_flow()));
        resultList3.add(sectionAnalysis_per);
        resultListAll.add(resultList3);
        return resultListAll;
    }

    public String getPercentage(String data_1,String data_2){
        String result="0.00%";
        if(StringUtils.isEmpty(data_1)&&StringUtils.isEmpty(data_2)) return result;
        NumberFormat percentage= NumberFormat.getPercentInstance();
        //设置百分数保留两位小数
        percentage.setMinimumFractionDigits(2);
        SectionAnalysis sectionAnalysisPer=new SectionAnalysis();
        if(Double.valueOf(data_1)==0) return result;
        Double res=(Double.valueOf(data_2)-Double.valueOf(data_1))/Double.valueOf(data_1);
        result=percentage.format(res);
        return result;
    }

    @Override
    public List<String[]> getsectionAnalysis_Histogram(List<String> Section, String Start_time_1, String End_time_1, String Start_time_2, String End_time_2, String medium,String maxDateDb,String minDateDb) {
        String Section_name;
        if(this.compareToDate(minDateDb,End_time_1)||this.compareToDate(minDateDb,End_time_2)){
            return null;
        }
        List<SectionAnalysis> resultList=new ArrayList();
        List<SectionAnalysis> resultList2=new ArrayList();
        List<String[]> resultListAll  = new ArrayList();
        resultList=this.getHomePageData_3(Section,Start_time_1,End_time_1);
        resultList2=this.getHomePageData_3(Section,Start_time_2,End_time_2);
        for(SectionAnalysis sectionAnalysis:resultList){
            String[] str_1=new String[2];
            str_1[0]=Start_time_1+"至"+End_time_1;
            if(medium.equals("Active_electric_energy")){
                str_1[1]=sectionAnalysis.getActive_electric_energy();
            }else if(medium.equals("Cumulative_flow_of_tap_water")){
                str_1[1]=sectionAnalysis.getCumulative_flow_of_tap_water();
            }else if(medium.equals("Cumulative_flow_of_circulating_water")){
                str_1[1]=sectionAnalysis.getCumulative_flow_of_circulating_water();
            }else if(medium.equals("Cumulative_Cooling_Value_of_Circulating_Water")){
                str_1[1]=sectionAnalysis.getCumulative_Cooling_Value_of_Circulating_Water();
            }else if(medium.equals("Cumulative_flow_of_frozen_water")){
                str_1[1]=sectionAnalysis.getCumulative_flow_of_frozen_water();
            }else if(medium.equals("Cumulative_cold_value_of_frozen_water")){
                str_1[1]=sectionAnalysis.getCumulative_cold_value_of_frozen_water();
            }else if(medium.equals("Cumulative_steam_flow")){
                str_1[1]=sectionAnalysis.getCumulative_steam_flow();
            }else if(medium.equals("Cumulative_air_flow")){
                str_1[1]=sectionAnalysis.getCumulative_air_flow();
            }
            resultListAll.add(str_1);
        }

        for(SectionAnalysis sectionAnalysis:resultList2){
            String[] str_2=new String[2];
            str_2[0]=Start_time_1+"至"+End_time_1;
            if(medium.equals("Active_electric_energy")){
                str_2[1]=sectionAnalysis.getActive_electric_energy();
            }else if(medium.equals("Cumulative_flow_of_tap_water")){
                str_2[1]=sectionAnalysis.getCumulative_flow_of_tap_water();
            }else if(medium.equals("Cumulative_flow_of_circulating_water")){
                str_2[1]=sectionAnalysis.getCumulative_flow_of_circulating_water();
            }else if(medium.equals("Cumulative_Cooling_Value_of_Circulating_Water")){
                str_2[1]=sectionAnalysis.getCumulative_Cooling_Value_of_Circulating_Water();
            }else if(medium.equals("Cumulative_flow_of_frozen_water")){
                str_2[1]=sectionAnalysis.getCumulative_flow_of_frozen_water();
            }else if(medium.equals("Cumulative_cold_value_of_frozen_water")){
                str_2[1]=sectionAnalysis.getCumulative_cold_value_of_frozen_water();
            }else if(medium.equals("Cumulative_steam_flow")){
                str_2[1]=sectionAnalysis.getCumulative_steam_flow();
            }else if(medium.equals("Cumulative_air_flow")){
                str_2[1]=sectionAnalysis.getCumulative_air_flow();
            }
            resultListAll.add(str_2);
        }
        return resultListAll;
    }

    private List<SectionAnalysis> getHomePageData_3(List<String> sectionList, String Start_t, String End_t) {
        if (sdf.format(new Date()).equals(End_t.trim())) {
            End_t = END_TIME;
        }
        List<SectionAnalysis> resultList=new ArrayList<SectionAnalysis>();
        DecimalFormat df = new DecimalFormat("0.00");
        for(String section:sectionList){
            SectionAnalysis sec=new SectionAnalysis();
            sec.setSection(section);
            if(section.equals("fajiao")){
                Double dlllg=sectionAnalysisMapper.dlllg(Start_t, End_t);
                Double fajiao_power=sectionAnalysisMapper.fajiao_power(Start_t, End_t);
                Double fajiao=0d;
                if(dlllg!=null&&fajiao_power!=null) fajiao =dlllg - fajiao_power;
                sec.setActive_electric_energy(df.format(fajiao));
                Double fajiao_tapwater=sectionAnalysisMapper.fajiao_tapwater(Start_t, End_t);
                if(fajiao_tapwater==null) fajiao_tapwater=0d;
                sec.setCumulative_flow_of_tap_water(df.format(fajiao_tapwater));
                Double fajiao_circulatingWater=sectionAnalysisMapper.fajiao_circulatingWate_EP(Start_t, End_t);
                if(fajiao_circulatingWater==null) fajiao_circulatingWater=0d;
                sec.setCumulative_flow_of_circulating_water(df.format(fajiao_circulatingWater));
                Double fajiao_circulatingWate_TE=sectionAnalysisMapper.fajiao_circulatingWate_TE(Start_t, End_t);
                if(fajiao_circulatingWate_TE==null) fajiao_circulatingWate_TE=0d;
                sec.setCumulative_Cooling_Value_of_Circulating_Water(df.format(fajiao_circulatingWate_TE));
                Double fajiao_freezeWater_EP=sectionAnalysisMapper.fajiao_freezeWater_EP(Start_t, End_t);
                if(fajiao_freezeWater_EP==null) fajiao_freezeWater_EP=0d;
                sec.setCumulative_flow_of_frozen_water(df.format(fajiao_freezeWater_EP));
                Double fajiao_freezeWater_TE=sectionAnalysisMapper.fajiao_freezeWater_TE(Start_t, End_t);
                if(fajiao_freezeWater_TE==null) fajiao_freezeWater_TE=0d;
                sec.setCumulative_cold_value_of_frozen_water(df.format(fajiao_freezeWater_TE));
                Double fajiao_steamMeter=sectionAnalysisMapper.fajiao_steamMeter(Start_t, End_t);
                if(fajiao_steamMeter==null) fajiao_steamMeter=0d;
                sec.setCumulative_steam_flow(df.format(fajiao_steamMeter));
                Double fajiao_fermentationAir=sectionAnalysisMapper.fajiao_fermentationAir(Start_t, End_t);
                if(fajiao_fermentationAir==null) fajiao_fermentationAir=0d;
                sec.setCumulative_air_flow(df.format(fajiao_fermentationAir));
            }else if(section.equals("yuchuli")){
                Double yuchuli=sectionAnalysisMapper.yuchuli_power(Start_t, End_t);
                if(yuchuli==null) yuchuli=0d;
                sec.setActive_electric_energy(df.format(yuchuli));
                Double yuchuli_tapwater=0d;
                sec.setCumulative_flow_of_tap_water(df.format(yuchuli_tapwater));
                Double yuchuli_circulatingWater=0d;
                sec.setCumulative_flow_of_circulating_water(df.format(yuchuli_circulatingWater));
                Double yuchuli_circulatingWate_TE=0d;
                sec.setCumulative_Cooling_Value_of_Circulating_Water(df.format(yuchuli_circulatingWate_TE));
                Double yuchuli_freezeWater_EP=0d;
                sec.setCumulative_flow_of_frozen_water(df.format(yuchuli_freezeWater_EP));
                Double yuchuli_freezeWater_TE=0d;
                sec.setCumulative_cold_value_of_frozen_water(df.format(yuchuli_freezeWater_TE));
                Double yuchuli_steamMeter=0d;
                sec.setCumulative_steam_flow(df.format(yuchuli_steamMeter));
                Double yuchuli_fermentationAir=0d;
                sec.setCumulative_air_flow(df.format(yuchuli_fermentationAir));
            }else if(section.equals("jinchu")){
                Double jinchu=sectionAnalysisMapper.jinchu_power(Start_t, End_t);
                if(jinchu==null) jinchu=0d;
                sec.setActive_electric_energy(df.format(jinchu));
                Double jinchu_tapwater=0d;
                sec.setCumulative_flow_of_tap_water(df.format(jinchu_tapwater));
                Double jinchu_circulatingWater=0d;
                sec.setCumulative_flow_of_circulating_water(df.format(jinchu_circulatingWater));
                Double jinchu_circulatingWate_TE=0d;
                sec.setCumulative_Cooling_Value_of_Circulating_Water(df.format(jinchu_circulatingWate_TE));
                Double jinchu_freezeWater_EP=0d;
                sec.setCumulative_flow_of_frozen_water(df.format(jinchu_freezeWater_EP));
                Double jinchu_freezeWater_TE=0d;
                sec.setCumulative_cold_value_of_frozen_water(df.format(jinchu_freezeWater_TE));
                Double jinchu_steamMeter=0d;
                sec.setCumulative_steam_flow(df.format(jinchu_steamMeter));
                Double jinchu_fermentationAir=0d;
                sec.setCumulative_air_flow(df.format(jinchu_fermentationAir));

            }else if(section.equals("jinglian")){
                Double jinglian=sectionAnalysisMapper.jinglian_power(Start_t, End_t);
                if(jinglian==null) jinglian=0d;
                sec.setActive_electric_energy(df.format(jinglian));
                Double jinglian_tapwater=sectionAnalysisMapper.jinglian_tapwater(Start_t, End_t);
                if(jinglian_tapwater==null)  jinglian_tapwater=0d;
                sec.setCumulative_flow_of_tap_water(df.format(jinglian_tapwater));
                Double jinglian_circulatingWater=sectionAnalysisMapper.jinglian_circulatingWate_EP(Start_t, End_t);
                if(jinglian_circulatingWater==null)  jinglian_circulatingWater=0d;
                sec.setCumulative_flow_of_circulating_water(df.format(jinglian_circulatingWater));
                Double jinglian_circulatingWate_TE=sectionAnalysisMapper.jinglian_circulatingWate_TE(Start_t, End_t);
                if(jinglian_circulatingWate_TE==null)  jinglian_circulatingWate_TE=0d;
                sec.setCumulative_Cooling_Value_of_Circulating_Water(df.format(jinglian_circulatingWate_TE));
                Double jianglian_freezeWater_EP=sectionAnalysisMapper.jianglian_freezeWater_EP(Start_t, End_t);
                if(jianglian_freezeWater_EP==null)  jianglian_freezeWater_EP=0d;
                sec.setCumulative_flow_of_frozen_water(df.format(jianglian_freezeWater_EP));
                Double jianglian_freezeWater_TE=sectionAnalysisMapper.jianglian_freezeWater_TE(Start_t, End_t);
                if(jianglian_freezeWater_TE==null)  jianglian_freezeWater_TE=0d;
                sec.setCumulative_cold_value_of_frozen_water(df.format(jianglian_freezeWater_TE));
                Double jinglian_steamMeter=sectionAnalysisMapper.jinglian_steamMeter(Start_t, End_t);
                if(jinglian_steamMeter==null)  jinglian_steamMeter=0d;
                sec.setCumulative_steam_flow(df.format(jinglian_steamMeter));
                Double jinglian_fermentationAir=0d;
                sec.setCumulative_air_flow(df.format(jinglian_fermentationAir));
            }else if(section.equals("dongli")){
                Double guolu =sectionAnalysisMapper.guolu_power(Start_t, End_t);
                if(guolu==null) guolu=0d;
                Double wushui = sectionAnalysisMapper.wushui_power(Start_t, End_t);
                if(wushui==null) wushui=0d;
                Double dongli_power=sectionAnalysisMapper.dongli_power(Start_t, End_t);
                Double jzxlk=sectionAnalysisMapper.jzxlk(Start_t, End_t);
                Double dongli=0d;
                if(dongli_power!=null&&guolu!=null&&wushui!=null&&jzxlk!=null){
                    dongli=dongli_power-guolu-wushui-jzxlk;
                }
                sec.setActive_electric_energy(df.format(dongli));
                Double dongli_tapwater=sectionAnalysisMapper.dongli_tapwater(Start_t, End_t);
                if(dongli_tapwater==null)  dongli_tapwater=0d;
                sec.setCumulative_flow_of_tap_water(df.format(dongli_tapwater));
                Double dongli_circulatingWater=sectionAnalysisMapper.dongli_circulatingWate_EP(Start_t, End_t);
                if(dongli_circulatingWater==null)  dongli_circulatingWater=0d;
                sec.setCumulative_flow_of_circulating_water(df.format(dongli_circulatingWater));
                Double dongli_circulatingWate_TE=sectionAnalysisMapper.dongli_circulatingWate_TE(Start_t, End_t);
                if(dongli_circulatingWate_TE==null)  dongli_circulatingWate_TE=0d;
                sec.setCumulative_Cooling_Value_of_Circulating_Water(df.format(dongli_circulatingWate_TE));
                Double dongli_freezeWater_EP=sectionAnalysisMapper.dongli_freezeWater_EP(Start_t, End_t);
                if(dongli_freezeWater_EP==null)  dongli_freezeWater_EP=0d;
                sec.setCumulative_flow_of_frozen_water(df.format(dongli_freezeWater_EP));
                Double dongli_freezeWater_TE=sectionAnalysisMapper.dongli_freezeWater_TE(Start_t, End_t);
                if(dongli_freezeWater_TE==null)  dongli_freezeWater_TE=0d;
                sec.setCumulative_cold_value_of_frozen_water(df.format(dongli_freezeWater_TE));
                Double dongli_steamMeter=0d;
                sec.setCumulative_steam_flow(df.format(dongli_steamMeter));
                Double dongli_fermentationAir=0d;
                sec.setCumulative_air_flow(df.format(dongli_fermentationAir));

            }else if(section.equals("guolu")){
                Double guolu =sectionAnalysisMapper.guolu_power(Start_t, End_t);
                if(guolu==null) guolu=0d;
                sec.setActive_electric_energy(df.format(guolu));
                Double guolu_tapwater=sectionAnalysisMapper.guolu_tapwater(Start_t, End_t);
                if(guolu_tapwater==null)  guolu_tapwater=0d;
                sec.setCumulative_flow_of_tap_water(df.format(guolu_tapwater));
                Double guolu_circulatingWater=0d;
                sec.setCumulative_flow_of_circulating_water(df.format(guolu_circulatingWater));
                Double guolu_circulatingWate_TE=0d;
                sec.setCumulative_Cooling_Value_of_Circulating_Water(df.format(guolu_circulatingWate_TE));
                Double guolu_freezeWater_EP=0d;
                sec.setCumulative_flow_of_frozen_water(df.format(guolu_freezeWater_EP));
                Double guolu_freezeWater_TE=0d;
                sec.setCumulative_cold_value_of_frozen_water(df.format(guolu_freezeWater_TE));
                Double guolu_steamMeter=sectionAnalysisMapper.guolu_steamMeter(Start_t, End_t);
                if(guolu_steamMeter==null)  guolu_steamMeter=0d;
                sec.setCumulative_steam_flow(df.format(guolu_steamMeter));
                Double guolu_fermentationAir=0d;
                sec.setCumulative_air_flow(df.format(guolu_fermentationAir));

            }else if(section.equals("wushui")){
                Double wushui = sectionAnalysisMapper.wushui_power(Start_t, End_t);
                if(wushui==null) wushui=0d;
                sec.setActive_electric_energy(df.format(wushui));
                Double wushui_tapwater=sectionAnalysisMapper.wushui_tapwater(Start_t, End_t);
                if(wushui_tapwater==null)  wushui_tapwater=0d;
                sec.setCumulative_flow_of_tap_water(df.format(wushui_tapwater));
                Double wushui_circulatingWater=0d;
                sec.setCumulative_flow_of_circulating_water(df.format(wushui_circulatingWater));
                Double wushui_circulatingWate_TE=0d;
                sec.setCumulative_Cooling_Value_of_Circulating_Water(df.format(wushui_circulatingWate_TE));
                Double wushui_freezeWater_EP=0d;
                sec.setCumulative_flow_of_frozen_water(df.format(wushui_freezeWater_EP));
                Double wushui_freezeWater_TE=0d;
                sec.setCumulative_cold_value_of_frozen_water(df.format(wushui_freezeWater_TE));
                Double wushui_steamMeter=sectionAnalysisMapper.wushui_steamMeter(Start_t, End_t);
                if(wushui_steamMeter==null)  wushui_steamMeter=0d;
                sec.setCumulative_steam_flow(df.format(wushui_steamMeter));
                Double wushui_fermentationAir=0d;
                sec.setCumulative_air_flow(df.format(wushui_fermentationAir));
            }else if(section.equals("qc")){
                Double qc=sectionAnalysisMapper.qc_power(Start_t, End_t);
                if(qc==null) qc=0d;
                sec.setActive_electric_energy(df.format(qc));
                Double qc_tapwater=0d;
                sec.setCumulative_flow_of_tap_water(df.format(qc_tapwater));
                Double qc_circulatingWater=0d;
                sec.setCumulative_flow_of_circulating_water(df.format(qc_circulatingWater));
                Double qc_circulatingWate_TE=0d;
                sec.setCumulative_Cooling_Value_of_Circulating_Water(df.format(qc_circulatingWate_TE));
                Double qc_freezeWater_EP=0d;
                sec.setCumulative_flow_of_frozen_water(df.format(qc_freezeWater_EP));
                Double qc_freezeWater_TE=0d;
                sec.setCumulative_cold_value_of_frozen_water(df.format(qc_freezeWater_TE));
                Double qc_steamMeter=0d;
                sec.setCumulative_steam_flow(df.format(qc_steamMeter));
                Double qc_fermentationAir=0d;
                sec.setCumulative_air_flow(df.format(qc_fermentationAir));
            }else if(section.equals("yunxing")){
                Double yunxing=sectionAnalysisMapper.yunxing_power(Start_t, End_t);
                if(yunxing==null) yunxing=0d;
                sec.setActive_electric_energy(df.format(yunxing));
                Double yunxing_tapwater=0d;
                sec.setCumulative_flow_of_tap_water(df.format(yunxing_tapwater));
                Double yunxing_circulatingWater=0d;
                sec.setCumulative_flow_of_circulating_water(df.format(yunxing_circulatingWater));
                Double yunxing_circulatingWate_TE=0d;
                sec.setCumulative_Cooling_Value_of_Circulating_Water(df.format(yunxing_circulatingWate_TE));
                Double yunxing_freezeWater_EP=0d;
                sec.setCumulative_flow_of_frozen_water(df.format(yunxing_freezeWater_EP));
                Double yunxing_freezeWater_TE=0d;
                sec.setCumulative_cold_value_of_frozen_water(df.format(yunxing_freezeWater_TE));
                Double yunxing_steamMeter=0d;
                sec.setCumulative_steam_flow(df.format(yunxing_steamMeter));
                Double yunxing_fermentationAir=0d;
                sec.setCumulative_air_flow(df.format(yunxing_fermentationAir));
            }else if(section.equals("xingzheng")){
                Double xingzheng=sectionAnalysisMapper.xingzheng_power(Start_t, End_t);
                if(xingzheng==null) xingzheng=0d;
                sec.setActive_electric_energy(df.format(xingzheng));
                Double xingzheng_tapwater=sectionAnalysisMapper.xingzheng_tapwater(Start_t, End_t);
                if(xingzheng_tapwater==null) xingzheng_tapwater=0d;
                sec.setCumulative_flow_of_tap_water(df.format(xingzheng_tapwater));
                Double xingzheng_circulatingWater=0d;
                sec.setCumulative_flow_of_circulating_water(df.format(xingzheng_circulatingWater));
                Double xingzheng_circulatingWate_TE=0d;
                sec.setCumulative_Cooling_Value_of_Circulating_Water(df.format(xingzheng_circulatingWate_TE));
                Double xingzheng_freezeWater_EP=0d;
                sec.setCumulative_flow_of_frozen_water(df.format(xingzheng_freezeWater_EP));
                Double xingzheng_freezeWater_TE=0d;
                sec.setCumulative_cold_value_of_frozen_water(df.format(xingzheng_freezeWater_TE));
                Double xingzheng_steamMeter=sectionAnalysisMapper.xingzheng_steamMeter(Start_t, End_t);
                if(xingzheng_steamMeter==null)  xingzheng_steamMeter=0d;
                sec.setCumulative_steam_flow(df.format(xingzheng_steamMeter));
                Double xingzheng_fermentationAir=0d;
                sec.setCumulative_air_flow(df.format(xingzheng_fermentationAir));
            }
            resultList.add(sec);
        }
        return resultList;
    }

    @Override
    public double wushui_power(String Start_t, String End_t) {
        if (sdf.format(new Date()).equals(End_t)) {
            End_t = END_TIME;
        }
        return sectionAnalysisMapper.wushui_power(Start_t, End_t);
    }

    @Override
    public List<Map<String,String>> gongduan_power(String Start_t,String End_t) {
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        Map<String,String> map = new HashMap<>();
        if (sdf.format(new Date()).equals(End_t)) {
            End_t = END_TIME;
        }
        map.put(FAJIAO,sectionAnalysisMapper.wushui_power(Start_t, End_t)+NULL_STRING);
        map.put(YUCHULI,sectionAnalysisMapper.yuchuli_power(Start_t, End_t)+NULL_STRING);
        map.put(JINCHU,sectionAnalysisMapper.jinchu_power(Start_t, End_t)+NULL_STRING);
        map.put(JINGLIAN,sectionAnalysisMapper.jinglian_power(Start_t, End_t)+NULL_STRING);
        map.put(DONGLI,sectionAnalysisMapper.dongli_power(Start_t, End_t)+NULL_STRING);
        map.put(GUOLU,sectionAnalysisMapper.guolu_power(Start_t, End_t)+NULL_STRING);
        map.put(WUSHUI,sectionAnalysisMapper.wushui_power(Start_t, End_t)+NULL_STRING);
        map.put(QC,sectionAnalysisMapper.qc_power(Start_t, End_t)+NULL_STRING);
        map.put(YUNXING,sectionAnalysisMapper.yunxing_power(Start_t, End_t)+NULL_STRING);
        map.put(XINGZHENG,sectionAnalysisMapper.xingzheng_power(Start_t, End_t)+NULL_STRING);
        list.add(map);
        return list;
    }

    @Override
    public List<SectionAnalysis> sectionAnalysis(String Start_t, String End_t) {
        List<SectionAnalysis> list = new ArrayList<SectionAnalysis>();
        String realMark="0";
        if (sdf.format(new Date()).equals(End_t.trim())) {
            realMark = END_TIME;
        }
        Integer index = sectionAnalysisMapper.serialn_numberEnd();
        if(index==null) index=0;
        index++;
       //有功电能
        ParameterUtil.setEleList();
        Map<String,Double> eleMap=this.getConsumptionValue(ParameterUtil.getEleList(),Start_t,End_t,realMark,"day_power","realValue_power_table");
        Double guolu =eleMap.get("JT-110910-05-EP")-eleMap.get("JT-110910-06-EP");
        if(guolu==null) guolu=0d;
        Double wushui = eleMap.get("JT-110910-08-EP");
        if(wushui==null) wushui=0d;
        //发酵变压器用电量
        Double zz=eleMap.get("JT-110910-01-EP");
        Double fajiao_power=eleMap.get("JT-110907-01-EP")+eleMap.get("JT-110907-02-EP")+eleMap.get("JT-110907-03-EP")+eleMap.get("JT-110907-05-EP")
                +eleMap.get("JT-110905-08-EP")+eleMap.get("JT-110905-01-EP")+eleMap.get("JT-110905-05-EP")+eleMap.get("JT-110905-04-EP")
                +eleMap.get("JT-110905-02-EP")+eleMap.get("JT-110905-06-EP")+eleMap.get("JT-110905-07-EP")+eleMap.get("JT-110904-05-EP")
                +eleMap.get("JT-110904-04-EP")+eleMap.get("JT-110904-03-EP")+eleMap.get("JT-110904-02-EP")+eleMap.get("JT-110904-01-EP")
                +eleMap.get("JT-110906-02-EP")+eleMap.get("JT-110906-01-EP")+eleMap.get("JT-110903-00-EP")+eleMap.get("JT-110904-06-EP")
                +eleMap.get("JT-110914-02-EP")+eleMap.get("JT-110914-03-EP")+eleMap.get("JT-110914-04-EP")
                +eleMap.get("JT-110902-01-EP")+eleMap.get("JT-110902-02-EP")+eleMap.get("JT-110902-03-EP")+eleMap.get("JT-110902-04-EP")
                +eleMap.get("JT-110902-05-EP")+eleMap.get("JT-110902-06-EP")+eleMap.get("JT-110902-07-EP")+eleMap.get("JT-110902-08-EP")
                +eleMap.get("JT-110902-09-EP")+eleMap.get("JT-110910-10-EP")+eleMap.get("JT-110910-12-EP");
        Double fajiao=0d;
        if(zz!=null&&fajiao_power!=null) fajiao =zz - fajiao_power;
        Double dongli_power=eleMap.get("JT-110909-01-EP")+eleMap.get("JT-110910-04-EP")+eleMap.get("JT-110910-02-EP")
                +eleMap.get("JT-110910-10-EP")+eleMap.get("JT-110909-03-EP")+eleMap.get("JT-110909-04-EP")
                +eleMap.get("JT-110910-03-EP")+eleMap.get("JT-110909-02-EP");
        Double jzxlk=eleMap.get("JT-110905-08-EP");
        Double yuchuli=eleMap.get("JT-110902-01-EP")+eleMap.get("JT-110902-02-EP")+eleMap.get("JT-110902-03-EP")
                +eleMap.get("JT-110902-04-EP")+eleMap.get("JT-110902-05-EP")+eleMap.get("JT-110902-06-EP")
                +eleMap.get("JT-110902-07-EP")+eleMap.get("JT-110902-08-EP")+eleMap.get("JT-110902-09-EP");
        if(yuchuli==null) yuchuli=0d;
        Double jinchu=eleMap.get("JT-110903-00-EP")-eleMap.get("JT-110903-01-EP")-eleMap.get("JT-110903-02-EP")-eleMap.get("JT-110903-03-EP")
                -eleMap.get("JT-110903-04-EP")-eleMap.get("JT-110903-05-EP")-eleMap.get("JT-110903-06-EP")-eleMap.get("JT-110903-07-EP")
                -eleMap.get("JT-110903-08-EP")-eleMap.get("JT-110903-09-EP")-eleMap.get("JT-110903-10-EP")-eleMap.get("JT-110903-11-EP")
                -eleMap.get("JT-110903-12-EP")-eleMap.get("JT-110903-13-EP");
        if(jinchu==null) jinchu=0d;
        Double jinglian=eleMap.get("JT-110903-01-EP")+eleMap.get("JT-110903-02-EP")+eleMap.get("JT-110903-03-EP")+eleMap.get("JT-110903-04-EP")
                +eleMap.get("JT-110903-05-EP")+eleMap.get("JT-110903-06-EP")+eleMap.get("JT-110903-07-EP")+eleMap.get("JT-110903-08-EP")
                +eleMap.get("JT-110903-09-EP")+eleMap.get("JT-110903-10-EP")+eleMap.get("JT-110903-11-EP")+eleMap.get("JT-110903-12-EP")
                +eleMap.get("JT-110903-13-EP")+eleMap.get("JT-110909-05-EP");
        if(jinglian==null) jinglian=0d;
        Double qc=eleMap.get("JT-110907-01-EP")+eleMap.get("JT-110907-02-EP")+eleMap.get("JT-110907-03-EP");
        if(qc==null) qc=0d;
        Double yunxing=eleMap.get("JT-110907-05-EP")+eleMap.get("JT-110905-08-EP")+eleMap.get("JT-110905-01-EP")
                +eleMap.get("JT-110905-05-EP")+eleMap.get("JT-110905-04-EP")+eleMap.get("JT-110905-02-EP")
                +eleMap.get("JT-110905-06-EP")+eleMap.get("JT-110905-07-EP");
        if(yunxing==null) yunxing=0d;
        Double xingzheng=eleMap.get("JT-110904-01-EP")+eleMap.get("JT-110904-02-EP")+eleMap.get("JT-110904-03-EP")+eleMap.get("JT-110904-04-EP");
        if(xingzheng==null) xingzheng=0d;
        Double dongli=0d;
        if(dongli_power!=null&&guolu!=null&&wushui!=null&&jzxlk!=null){
            dongli=dongli_power-guolu-wushui-jzxlk;
        }
        /*Double guolu =sectionAnalysisMapper.guolu_power(Start_t, End_t);
        if(guolu==null) guolu=0d;*/
        /*Double wushui = sectionAnalysisMapper.wushui_power(Start_t, End_t);
        if(wushui==null) wushui=0d;*/
        /*Double dlllg=sectionAnalysisMapper.dlllg(Start_t, End_t);*/
        /*Double fajiao_power=sectionAnalysisMapper.fajiao_power(Start_t, End_t);*/
        /*Double dongli_power=sectionAnalysisMapper.dongli_power(Start_t, End_t);*/
        /*Double jzxlk=sectionAnalysisMapper.jzxlk(Start_t, End_t);*/
        /*Double fajiao=0d;
        if(dlllg!=null&&fajiao_power!=null) fajiao =dlllg - fajiao_power;*/
        /*Double yuchuli=sectionAnalysisMapper.yuchuli_power(Start_t, End_t);
        if(yuchuli==null) yuchuli=0d;*/
        /*Double jinchu=sectionAnalysisMapper.jinchu_power(Start_t, End_t);
        if(jinchu==null) jinchu=0d;*/
        /*Double jinglian=sectionAnalysisMapper.jinglian_power(Start_t, End_t);
        if(jinglian==null) jinglian=0d;*/
        /*Double qc=sectionAnalysisMapper.qc_power(Start_t, End_t);
        if(qc==null) qc=0d;*/
        /*Double yunxing=sectionAnalysisMapper.yunxing_power(Start_t, End_t);
        if(yunxing==null) yunxing=0d;*/
        /*Double xingzheng=sectionAnalysisMapper.xingzheng_power(Start_t, End_t);
        if(xingzheng==null) xingzheng=0d;*/
       /* Double dongli=0d;
        if(dongli_power!=null&&guolu!=null&&wushui!=null&&jzxlk!=null){
            dongli=dongli_power-guolu-wushui-jzxlk;
        }*/

        //自来水总流量
        ParameterUtil.setTapWaterList();
        Map<String,Double> waterMap=this.getConsumptionValue(ParameterUtil.getTapWaterList(),Start_t,End_t,realMark,"day_tapWater","realValue_tapWater_table");
        Double fajiao_tapwater=waterMap.get("FIT-092501-08-EQ");
        if(fajiao_tapwater==null) fajiao_tapwater=0d;
        Double yuchuli_tapwater=0d;
        Double jinchu_tapwater=0d;
        Double jinglian_tapwater=waterMap.get("FIT-092501-06-EQ");
        if(jinglian_tapwater==null)  jinglian_tapwater=0d;
        Double dongli_tapwater=waterMap.get("FIT-092501-02-EQ");
        //Double dongli_tapwater=0d;
        if(dongli_tapwater==null)  dongli_tapwater=0d;
        Double guolu_tapwater=waterMap.get("FIT-092501-03-EQ");
        if(guolu_tapwater==null)  guolu_tapwater=0d;
        Double wushui_tapwater=waterMap.get("FIT-092501-05-EQ");
        if(wushui_tapwater==null)  wushui_tapwater=0d;
        Double qc_tapwater=0d;
        Double yunxing_tapwater=0d;
        //自来水总表值
        Double zongWater=0d;
        zongWater=waterMap.get("FIT-092501-01-EQ");
        Double total_fenbiao=0d;
        total_fenbiao=waterMap.get("FIT-092501-02-EQ")+waterMap.get("FIT-092501-03-EQ")+waterMap.get("FIT-092501-05-EQ")
                +waterMap.get("FIT-092501-06-EQ")+waterMap.get("FIT-092501-08-EQ")+waterMap.get("FIT-092501-07-EQ");
        if(total_fenbiao>zongWater) zongWater=total_fenbiao;
        Double xingzheng_tapwater=zongWater-waterMap.get("FIT-092501-02-EQ")-waterMap.get("FIT-092501-03-EQ")
                -waterMap.get("FIT-092501-05-EQ")-waterMap.get("FIT-092501-06-EQ")-waterMap.get("FIT-092501-08-EQ");
        if(xingzheng_tapwater==null) xingzheng_tapwater=0d;
        //Double fajiao_tapwater=sectionAnalysisMapper.fajiao_tapwater(Start_t, End_t);
        /*Double jinglian_tapwater=sectionAnalysisMapper.jinglian_tapwater(Start_t, End_t);
        if(jinglian_tapwater==null)  jinglian_tapwater=0d;*/
        /*Double dongli_tapwater=sectionAnalysisMapper.dongli_tapwater(Start_t, End_t);
        if(dongli_tapwater==null)  dongli_tapwater=0d;*/
        /*Double guolu_tapwater=sectionAnalysisMapper.guolu_tapwater(Start_t, End_t);
        if(guolu_tapwater==null)  guolu_tapwater=0d;*/
        /*Double wushui_tapwater=sectionAnalysisMapper.wushui_tapwater(Start_t, End_t);
        if(wushui_tapwater==null)  wushui_tapwater=0d;*/
       /* Double qc_tapwater=0d;
        Double yunxing_tapwater=0d;*/
       /* Double xingzheng_tapwater=sectionAnalysisMapper.xingzheng_tapwater(Start_t, End_t);
        if(xingzheng_tapwater==null) xingzheng_tapwater=0d;*/

        //循环水总流量
        List<String> circulatingWaterList=new ArrayList<>();
        circulatingWaterList.add("FIT-092101-01-EQ");circulatingWaterList.add("FIT-092101-02-EQ");circulatingWaterList.add("FIT-092202-02-EQ");
        circulatingWaterList.add("FIT-092202-03-EQ");circulatingWaterList.add("FIT-092202-02-EQ");
        Map<String,Double> circulatingWaterMap=this.getConsumptionValue(circulatingWaterList,Start_t,End_t,realMark,"day_circulatingWate","realValue_circulatingWater_table");
        Double fajiao_circulatingWater=circulatingWaterMap.get("FIT-092101-01-EQ")+circulatingWaterMap.get("FIT-092101-02-EQ")
                -circulatingWaterMap.get("FIT-092202-02-EQ");
        if(fajiao_circulatingWater==null) fajiao_circulatingWater=0d;
        Double yuchuli_circulatingWater=0d;
        Double jinchu_circulatingWater=0d;
        Double jinglian_circulatingWater=circulatingWaterMap.get("FIT-092202-02-EQ")+circulatingWaterMap.get("FIT-092202-03-EQ");
        if(jinglian_circulatingWater==null)  jinglian_circulatingWater=0d;
        Double dongli_circulatingWater=circulatingWaterMap.get("FIT-092101-01-EQ")+circulatingWaterMap.get("FIT-092101-02-EQ");
        if(dongli_circulatingWater==null)  dongli_circulatingWater=0d;
        /*Double fajiao_circulatingWater=sectionAnalysisMapper.fajiao_circulatingWate_EP(Start_t,End_t);
        if(fajiao_circulatingWater==null) fajiao_circulatingWater=0d;*/
       /* Double yuchuli_circulatingWater=0d;
        Double jinchu_circulatingWater=0d;*/
        /*Double jinglian_circulatingWater=sectionAnalysisMapper.jinglian_circulatingWate_EP(Start_t, End_t);
        if(jinglian_circulatingWater==null)  jinglian_circulatingWater=0d;*/
        /*Double dongli_circulatingWater=sectionAnalysisMapper.dongli_circulatingWate_EP(Start_t, End_t);
        if(dongli_circulatingWater==null)  dongli_circulatingWater=0d;*/
        Double guolu_circulatingWater=0d;
        Double wushui_circulatingWater=0d;
        Double qc_circulatingWater=0d;
        Double yunxing_circulatingWater=0d;
        Double xingzheng_circulatingWater=0d;

        //循环水冷值
       ParameterUtil.setCirculatingTEList();
        Map<String,Double> circulatingTEMap=this.getConsumptionValue(ParameterUtil.getCirculatingTEList(),Start_t,End_t,realMark,"day_circulatingWate","realValue_circulatingWater_table");
        Double fajiao_circulatingWate_TE=circulatingTEMap.get("FIT-092101-01-TE")+circulatingTEMap.get("FIT-092101-02-TE")
                -circulatingTEMap.get("FIT-092202-02-TE");
        if(fajiao_circulatingWate_TE==null) fajiao_circulatingWate_TE=0d;
        /*Double fajiao_circulatingWate_TE=sectionAnalysisMapper.fajiao_circulatingWate_TE(Start_t, End_t);
        if(fajiao_circulatingWate_TE==null) fajiao_circulatingWate_TE=0d;*/
        Double yuchuli_circulatingWate_TE=0d;
        Double jinchu_circulatingWate_TE=0d;
        Double jinglian_circulatingWate_TE=circulatingTEMap.get("FIT-092202-02-TE")+circulatingTEMap.get("FIT-092202-03-TE");
        if(jinglian_circulatingWate_TE==null)  jinglian_circulatingWate_TE=0d;
        Double dongli_circulatingWate_TE=circulatingTEMap.get("FIT-092101-01-TE")+circulatingTEMap.get("FIT-092101-02-TE");
        if(dongli_circulatingWate_TE==null)  dongli_circulatingWate_TE=0d;
        /*Double jinglian_circulatingWate_TE=sectionAnalysisMapper.jinglian_circulatingWate_TE(Start_t, End_t);
        if(jinglian_circulatingWate_TE==null)  jinglian_circulatingWate_TE=0d;*/
        /*Double dongli_circulatingWate_TE=sectionAnalysisMapper.dongli_circulatingWate_TE(Start_t, End_t);
        if(dongli_circulatingWate_TE==null)  dongli_circulatingWate_TE=0d;*/
        Double guolu_circulatingWate_TE=0d;
        Double wushui_circulatingWate_TE=0d;
        Double qc_circulatingWate_TE=0d;
        Double yunxing_circulatingWate_TE=0d;
        Double xingzheng_circulatingWate_TE=0d;

        //冷冻水流量
        ParameterUtil.setFreezeWaterList();
        Map<String,Double> freezeWaterMap=this.getConsumptionValue(ParameterUtil.getFreezeWaterList(),Start_t,End_t,realMark,"day_freezeWater","realValue_freezeWater_table");
        Double fajiao_freezeWater_EP=freezeWaterMap.get("FIT-092202-01-EQ")-freezeWaterMap.get("FIT-092202-02-EQ");
        if(fajiao_freezeWater_EP==null) fajiao_freezeWater_EP=0d;
        Double yuchuli_freezeWater_EP=0d;
        Double jinchu_freezeWater_EP=0d;
        Double jianglian_freezeWater_EP=freezeWaterMap.get("FIT-092202-02-EQ");
        if(jianglian_freezeWater_EP==null)  jianglian_freezeWater_EP=0d;
        Double dongli_freezeWater_EP=freezeWaterMap.get("FIT-092202-01-EQ");
        if(dongli_freezeWater_EP==null)  dongli_freezeWater_EP=0d;
        /*Double fajiao_freezeWater_EP=sectionAnalysisMapper.fajiao_freezeWater_EP(Start_t, End_t);
        if(fajiao_freezeWater_EP==null) fajiao_freezeWater_EP=0d;*/
        /*Double yuchuli_freezeWater_EP=0d;
        Double jinchu_freezeWater_EP=0d;*/
       /* Double jianglian_freezeWater_EP=sectionAnalysisMapper.jianglian_freezeWater_EP(Start_t, End_t);
        if(jianglian_freezeWater_EP==null)  jianglian_freezeWater_EP=0d;*/
        /*Double dongli_freezeWater_EP=sectionAnalysisMapper.dongli_freezeWater_EP(Start_t, End_t);
        if(dongli_freezeWater_EP==null)  dongli_freezeWater_EP=0d;*/
        Double guolu_freezeWater_EP=0d;
        Double wushui_freezeWater_EP=0d;
        Double qc_freezeWater_EP=0d;
        Double yunxing_freezeWater_EP=0d;
        Double xingzheng_freezeWater_EP=0d;

        //冷冻水冷值
        ParameterUtil.setFreezeWaterTEList();
        Map<String,Double> freezeWaterTEMap=this.getConsumptionValue(ParameterUtil.getFreezeWaterTEList(),Start_t,End_t,realMark,"day_freezeWater","realValue_freezeWater_table");
        Double fajiao_freezeWater_TE=freezeWaterTEMap.get("FIT-092202-01-TE")-freezeWaterTEMap.get("FIT-092202-02-TE");
        if(fajiao_freezeWater_TE==null) fajiao_freezeWater_TE=0d;
        Double yuchuli_freezeWater_TE=0d;
        Double jinchu_freezeWater_TE=0d;
        Double jianglian_freezeWater_TE=freezeWaterTEMap.get("FIT-092202-02-TE");
        if(jianglian_freezeWater_TE==null)  jianglian_freezeWater_TE=0d;
        Double dongli_freezeWater_TE=freezeWaterTEMap.get("FIT-092202-01-TE");
        if(dongli_freezeWater_TE==null)  dongli_freezeWater_TE=0d;
        /*Double fajiao_freezeWater_TE=sectionAnalysisMapper.fajiao_freezeWater_TE(Start_t, End_t);
        if(fajiao_freezeWater_TE==null) fajiao_freezeWater_TE=0d;*/
        /*Double yuchuli_freezeWater_TE=0d;
        Double jinchu_freezeWater_TE=0d;*/
        /*Double jianglian_freezeWater_TE=sectionAnalysisMapper.jianglian_freezeWater_TE(Start_t, End_t);
        if(jianglian_freezeWater_TE==null)  jianglian_freezeWater_TE=0d;*/
        /*Double dongli_freezeWater_TE=sectionAnalysisMapper.dongli_freezeWater_TE(Start_t, End_t);
        if(dongli_freezeWater_TE==null)  dongli_freezeWater_TE=0d;*/
        Double guolu_freezeWater_TE=0d;
        Double wushui_freezeWater_TE=0d;
        Double qc_freezeWater_TE=0d;
        Double yunxing_freezeWater_TE=0d;
        Double xingzheng_freezeWater_TE=0d;

        //蒸汽流量
        ParameterUtil.setSteamList();
        Map<String,Double> steamMap=this.getConsumptionValue(ParameterUtil.getSteamList(),Start_t,End_t,realMark,"day_steamMeter","realValue_steamMeter_table");
        Double fajiao_steamMeter=steamMap.get("NG_LX_ALL")+steamMap.get("NG_FJ_ALL")+steamMap.get("NG_HCL_ALL");
        if(fajiao_steamMeter==null) fajiao_steamMeter=0d;
        Double yuchuli_steamMeter=0d;
        Double jinchu_steamMeter=0d;
        Double jinglian_steamMeter=steamMap.get("FIT-031001-01-EQ");
        if(jinglian_steamMeter==null)  jinglian_steamMeter=0d;
        Double dongli_steamMeter=0d;
        Double guolu_steamMeter=steamMap.get("FIT-100601-04-EQ");
        if(guolu_steamMeter==null)  guolu_steamMeter=0d;
        Double wushui_steamMeter=steamMap.get("FIT-092501-09-EQ");
        if(wushui_steamMeter==null)  wushui_steamMeter=0d;
        Double qc_steamMeter=0d;
        Double yunxing_steamMeter=0d;
        Double xingzheng_steamMeter=steamMap.get("FIT-100601-02-EQ")+steamMap.get("FIT-100601-03-EQ")-steamMap.get("FIT-100601-04-EQ")
                -steamMap.get("FIT-012001-10-EQ")-steamMap.get("FIT-092501-09-EQ")-steamMap.get("FIT-031001-01-EQ");
        if(xingzheng_steamMeter==null)  xingzheng_steamMeter=0d;
        /*Double fajiao_steamMeter=sectionAnalysisMapper.fajiao_steamMeter(Start_t, End_t);
        if(fajiao_steamMeter==null) fajiao_steamMeter=0d;*/
        /*Double yuchuli_steamMeter=0d;
        Double jinchu_steamMeter=0d;*/
        /*Double jinglian_steamMeter=sectionAnalysisMapper.jinglian_steamMeter(Start_t, End_t);
        if(jinglian_steamMeter==null)  jinglian_steamMeter=0d;*/
        /*Double dongli_steamMeter=0d;*/
        /*Double guolu_steamMeter=sectionAnalysisMapper.guolu_steamMeter(Start_t, End_t);
        if(guolu_steamMeter==null)  guolu_steamMeter=0d;*/
        /*Double wushui_steamMeter=sectionAnalysisMapper.wushui_steamMeter(Start_t, End_t);
        if(wushui_steamMeter==null)  wushui_steamMeter=0d;*/
        /*Double qc_steamMeter=0d;
        Double yunxing_steamMeter=0d;*/
        /*Double xingzheng_steamMeter=sectionAnalysisMapper.xingzheng_steamMeter(Start_t, End_t);
        if(xingzheng_steamMeter==null)  xingzheng_steamMeter=0d;*/

        //空气流量
        List<String> ferAirList=new ArrayList<>();
        ferAirList.add("FIT-012801-02-EQ");ferAirList.add("FIT-012801-03-EQ");ferAirList.add("FIT-012801-04-EQ");
        ferAirList.add("FIT-012801-05-EQ");ferAirList.add("FIT-012801-06-EQ");ferAirList.add("FIT-012801-07-EQ");
        Map<String,Double> ferAirMap=this.getConsumptionValue(ferAirList,Start_t,End_t,realMark,"day_fermentationAir","realValue_fermentationAir_table");
        Double fajiao_fermentationAir=ferAirMap.get("FIT-012801-02-EQ")+ferAirMap.get("FIT-012801-03-EQ")+ferAirMap.get("FIT-012801-04-EQ")
                +ferAirMap.get("FIT-012801-05-EQ")+ferAirMap.get("FIT-012801-06-EQ")+ferAirMap.get("FIT-012801-07-EQ");
        if(fajiao_fermentationAir==null) fajiao_fermentationAir=0d;
        Double yuchuli_fermentationAir=0d;
        Double jinchu_fermentationAir=0d;
        Double jinglian_fermentationAir=0d;
        Double dongli_fermentationAir=0d;
        Double guolu_fermentationAir=0d;
        Double wushui_fermentationAir=0d;
        Double qc_fermentationAir=0d;
        Double yunxing_fermentationAir=0d;
        Double xingzheng_fermentationAir=0d;

        String datetime= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        list.add(new SectionAnalysis("fajiao",fajiao+NULL_STRING,fajiao_tapwater+"",fajiao_circulatingWater+"",fajiao_circulatingWate_TE+"",fajiao_freezeWater_EP+"",fajiao_freezeWater_TE+"",fajiao_steamMeter+"",fajiao_fermentationAir+"",End_t,index++));
        list.add(new SectionAnalysis("yuchuli",yuchuli+NULL_STRING,yuchuli_tapwater+"",yuchuli_circulatingWater+"",yuchuli_circulatingWate_TE+"",yuchuli_freezeWater_EP+"",yuchuli_freezeWater_TE+"",yuchuli_steamMeter+"",yuchuli_fermentationAir+"",End_t,index++));
        list.add(new SectionAnalysis("jinchu",jinchu+NULL_STRING,jinchu_tapwater+"",jinchu_circulatingWater+"",jinchu_circulatingWate_TE+"",jinchu_freezeWater_EP+"",jinchu_freezeWater_TE+"",jinchu_steamMeter+"",jinchu_fermentationAir+"",End_t,index++));
        list.add(new SectionAnalysis("jinglian",jinglian+NULL_STRING,jinglian_tapwater+"",jinglian_circulatingWater+"",jinglian_circulatingWate_TE+"",jianglian_freezeWater_EP+"",jianglian_freezeWater_TE+"",jinglian_steamMeter+"",jinglian_fermentationAir+"",End_t,index++));
        list.add(new SectionAnalysis("dongli",dongli+NULL_STRING,dongli_tapwater+"",dongli_circulatingWater+"",dongli_circulatingWate_TE+"",dongli_freezeWater_EP+"",dongli_freezeWater_TE+"",dongli_steamMeter+"",dongli_fermentationAir+"",End_t,index++));
        list.add(new SectionAnalysis("guolu",guolu+NULL_STRING,guolu_tapwater+"",guolu_circulatingWater+"",guolu_circulatingWate_TE+"",guolu_freezeWater_EP+"",guolu_freezeWater_TE+"",guolu_steamMeter+"",guolu_fermentationAir+"",End_t,index++));
        list.add(new SectionAnalysis("wushui",wushui+NULL_STRING,wushui_tapwater+"",wushui_circulatingWater+"",wushui_circulatingWate_TE+"",wushui_freezeWater_EP+"",wushui_freezeWater_TE+"",wushui_steamMeter+"",wushui_fermentationAir+"",End_t,index++));
        list.add(new SectionAnalysis("qc",qc+NULL_STRING,qc_tapwater+"",qc_circulatingWater+"",qc_circulatingWate_TE+"",qc_freezeWater_EP+"",qc_freezeWater_EP+"",qc_steamMeter+"",qc_fermentationAir+"",End_t,index++));
        list.add(new SectionAnalysis("yunxing",yunxing+NULL_STRING,yunxing_tapwater+"",yunxing_circulatingWater+"",yunxing_circulatingWate_TE+"",yunxing_freezeWater_EP+"",yunxing_freezeWater_TE+"",yunxing_steamMeter+"",yunxing_fermentationAir+"",End_t,index++));
        list.add(new SectionAnalysis("xingzheng",xingzheng+NULL_STRING,xingzheng_tapwater+"",xingzheng_circulatingWater+"",xingzheng_circulatingWate_TE+"",xingzheng_freezeWater_EP+"",xingzheng_freezeWater_TE+"",xingzheng_steamMeter+"",xingzheng_fermentationAir+"",End_t,index++));

        //sectionAnalysisMapper.insert_sectionpower(list);
        return list;
    }

    @Override
    public List<SectionAnalysis> getHomePageData(List<String> sections) {
        String dayCurr= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        //String beforeOneDay=this.subtractDay(dayCurr);   //前一天
        String startDate=dayCurr.substring(0,5)+"01-01";
        //获取数据库中的最小时间
        String minDateDb=sectionAnalysisMapper.selectDate_time("min(his_date)");
        if(this.compareToDate(minDateDb,startDate)) startDate=minDateDb;
        List<SectionAnalysis> sectionAnalysisList=this.getHomePageData_2(sections,startDate,dayCurr);
        return sectionAnalysisList;
    }

    @Override
    public List<SankeyData> getSankeyData(List<String> sections, String medium) {
        List<SankeyData> resultList=new ArrayList<>();
        String sqlField="";
        String dayCurr= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");
        String startDate=dayCurr.substring(0,5)+"01-01";
        String realMark="0";
        if (sdf.format(new Date()).equals(dayCurr.trim())) {
            realMark = END_TIME;
        }
        //String beforeOneDay=this.subtractDay(dayCurr);   //前一天

        //获取数据库中的最小时间
        String minDateDb=sectionAnalysisMapper.selectDate_time("min(his_date)");
        if(this.compareToDate(minDateDb,startDate)) startDate=minDateDb;
        if(medium.equals("自来水")){
            sqlField="Cumulative_flow_of_tap_water";
            //resultList=sectionAnalysisMapper.getSankeyData(sections,sqlField,startDate,beforeOneDay);
            //消耗量
            List<String> waterList=new ArrayList<>();
            waterList.add("FIT-092501-08-EQ");waterList.add("FIT-092501-06-EQ");waterList.add("FIT-092501-02-EQ");
            waterList.add("FIT-092501-03-EQ");waterList.add("FIT-092501-05-EQ");waterList.add("FIT-092501-01-EQ");
            waterList.add("FIT-092501-02-EQ");waterList.add("FIT-092501-03-EQ");waterList.add("FIT-092501-05-EQ");
            waterList.add("FIT-092501-06-EQ");
            Map<String,Double> waterMap=this.getConsumptionValue(waterList,startDate,dayCurr,realMark,"day_tapWater","realValue_tapWater_table");
            Double fajiao_tapwater=waterMap.get("FIT-092501-08-EQ");
            if(fajiao_tapwater==null) fajiao_tapwater=0d;
            Double yuchuli_tapwater=0d;
            Double jinchu_tapwater=0d;
            Double jinglian_tapwater=waterMap.get("FIT-092501-06-EQ");
            if(jinglian_tapwater==null)  jinglian_tapwater=0d;
            Double dongli_tapwater=waterMap.get("FIT-092501-02-EQ");
            if(dongli_tapwater==null)  dongli_tapwater=0d;
            Double guolu_tapwater=waterMap.get("FIT-092501-03-EQ");
            if(guolu_tapwater==null)  guolu_tapwater=0d;
            Double wushui_tapwater=waterMap.get("FIT-092501-05-EQ");
            if(wushui_tapwater==null)  wushui_tapwater=0d;
            Double qc_tapwater=0d;
            Double yunxing_tapwater=0d;
            Double xingzheng_tapwater=waterMap.get("FIT-092501-01-EQ")-waterMap.get("FIT-092501-02-EQ")-waterMap.get("FIT-092501-03-EQ")
                    -waterMap.get("FIT-092501-05-EQ")-waterMap.get("FIT-092501-06-EQ")-waterMap.get("FIT-092501-08-EQ");
            if(xingzheng_tapwater==null) xingzheng_tapwater=0d;
            addCostValue(sections,resultList,fajiao_tapwater,yuchuli_tapwater,jinchu_tapwater,jinglian_tapwater,dongli_tapwater,guolu_tapwater,wushui_tapwater,qc_tapwater,yunxing_tapwater,xingzheng_tapwater);
        }else if(medium.equals("电")){
            //sqlField="Active_electric_energy";
            //resultList=sectionAnalysisMapper.getSankeyData(sections,sqlField,startDate,beforeOneDay);
            //消耗量
            ParameterUtil.setEleList();
            Map<String,Double> eleMap=this.getConsumptionValue(ParameterUtil.getEleList(),startDate,dayCurr,realMark,"day_power","realValue_power_table");
            Double guolu =eleMap.get("JT-110910-05-EP")-eleMap.get("JT-110910-06-EP");
            if(guolu==null) guolu=0d;
            Double wushui = eleMap.get("JT-110910-08-EP");
            if(wushui==null) wushui=0d;
            Double zz=eleMap.get("JT-110910-01-EP");
            Double fajiao_power=eleMap.get("JT-110907-01-EP")+eleMap.get("JT-110907-02-EP")+eleMap.get("JT-110907-03-EP")+eleMap.get("JT-110907-05-EP")
                    +eleMap.get("JT-110905-08-EP")+eleMap.get("JT-110905-01-EP")+eleMap.get("JT-110905-05-EP")+eleMap.get("JT-110905-04-EP")
                    +eleMap.get("JT-110905-02-EP")+eleMap.get("JT-110905-06-EP")+eleMap.get("JT-110905-07-EP")+eleMap.get("JT-110904-05-EP")
                    +eleMap.get("JT-110904-04-EP")+eleMap.get("JT-110904-03-EP")+eleMap.get("JT-110904-02-EP")+eleMap.get("JT-110904-01-EP")
                    +eleMap.get("JT-110906-02-EP")+eleMap.get("JT-110906-01-EP")+eleMap.get("JT-110903-00-EP")+eleMap.get("JT-110904-06-EP")
                    +eleMap.get("JT-110904-01-EP")+eleMap.get("JT-110914-02-EP")+eleMap.get("JT-110914-03-EP")+eleMap.get("JT-110914-04-EP")
                    +eleMap.get("JT-110902-01-EP")+eleMap.get("JT-110902-02-EP")+eleMap.get("JT-110902-03-EP")+eleMap.get("JT-110902-04-EP")
                    +eleMap.get("JT-110902-05-EP")+eleMap.get("JT-110902-06-EP")+eleMap.get("JT-110902-07-EP")+eleMap.get("JT-110902-08-EP")
                    +eleMap.get("JT-110902-09-EP")+eleMap.get("JT-110910-10-EP")+eleMap.get("JT-110910-12-EP");
            Double fajiao=0d;
            if(zz!=null&&fajiao_power!=null) fajiao =zz - fajiao_power;
            Double dongli_power=eleMap.get("JT-110909-01-EP")+eleMap.get("JT-110910-04-EP")+eleMap.get("JT-110910-02-EP")
                    +eleMap.get("JT-110910-10-EP")+eleMap.get("JT-110909-03-EP")+eleMap.get("JT-110909-04-EP")
                    +eleMap.get("JT-110910-03-EP")+eleMap.get("JT-110909-02-EP");
            Double jzxlk=eleMap.get("JT-110905-08-EP");
            Double yuchuli=eleMap.get("JT-110902-01-EP")+eleMap.get("JT-110902-02-EP")+eleMap.get("JT-110902-03-EP")
                    +eleMap.get("JT-110902-04-EP")+eleMap.get("JT-110902-05-EP")+eleMap.get("JT-110902-06-EP")
                    +eleMap.get("JT-110902-07-EP")+eleMap.get("JT-110902-08-EP");
            if(yuchuli==null) yuchuli=0d;
            Double jinchu=eleMap.get("JT-110903-00-EP")-eleMap.get("JT-110903-01-EP")-eleMap.get("JT-110903-02-EP")-eleMap.get("JT-110903-03-EP")
                    -eleMap.get("JT-110903-04-EP")-eleMap.get("JT-110903-05-EP")-eleMap.get("JT-110903-06-EP")-eleMap.get("JT-110903-07-EP")
                    -eleMap.get("JT-110903-08-EP")-eleMap.get("JT-110903-09-EP")-eleMap.get("JT-110903-10-EP")-eleMap.get("JT-110903-11-EP")
                    -eleMap.get("JT-110903-12-EP")-eleMap.get("JT-110903-13-EP");
            if(jinchu==null) jinchu=0d;
            Double jinglian=eleMap.get("JT-110903-01-EP")+eleMap.get("JT-110903-02-EP")+eleMap.get("JT-110903-03-EP")+eleMap.get("JT-110903-04-EP")
                    +eleMap.get("JT-110903-05-EP")+eleMap.get("JT-110903-06-EP")+eleMap.get("JT-110903-07-EP")+eleMap.get("JT-110903-08-EP")
                    +eleMap.get("JT-110903-09-EP")+eleMap.get("JT-110903-10-EP")+eleMap.get("JT-110903-11-EP")+eleMap.get("JT-110903-12-EP")
                    +eleMap.get("JT-110903-13-EP")+eleMap.get("JT-110909-05-EP");
            if(jinglian==null) jinglian=0d;
            Double qc=eleMap.get("JT-110907-01-EP")+eleMap.get("JT-110907-02-EP")+eleMap.get("JT-110907-03-EP");
            if(qc==null) qc=0d;
            Double yunxing=eleMap.get("JT-110907-05-EP")+eleMap.get("JT-110905-08-EP")+eleMap.get("JT-110905-01-EP")
                    +eleMap.get("JT-110905-05-EP")+eleMap.get("JT-110905-04-EP")+eleMap.get("JT-110905-02-EP")
                    +eleMap.get("JT-110905-06-EP")+eleMap.get("JT-110905-07-EP");
            if(yunxing==null) yunxing=0d;
            Double xingzheng=eleMap.get("JT-110904-01-EP")+eleMap.get("JT-110904-02-EP")+eleMap.get("JT-110904-03-EP")+eleMap.get("JT-110904-04-EP");
            if(xingzheng==null) xingzheng=0d;
            Double dongli=0d;
            if(dongli_power!=null&&guolu!=null&&wushui!=null&&jzxlk!=null){
                dongli=dongli_power-guolu-wushui-jzxlk;
            }
            addCostValue(sections,resultList,fajiao,yuchuli,jinchu,jinglian,dongli,guolu,wushui,qc,yunxing,xingzheng);
        }else if(medium.equals("循环水")){
            //sqlField="Cumulative_flow_of_circulating_water";
            //resultList=sectionAnalysisMapper.getSankeyData(sections,sqlField,startDate,beforeOneDay);
            //消耗量
            ParameterUtil.setCirculateWaterList();
            Map<String,Double> circulatingWaterMap=this.getConsumptionValue(ParameterUtil.getCirculateWaterList(),startDate,dayCurr,realMark,"day_circulatingWate","realValue_circulatingWater_table");
            Double fajiao_circulatingWater=circulatingWaterMap.get("FIT-092101-01-EQ")+circulatingWaterMap.get("FIT-092101-02-EQ")
                    -circulatingWaterMap.get("FIT-092202-02-EQ");
            if(fajiao_circulatingWater==null) fajiao_circulatingWater=0d;
            Double yuchuli_circulatingWater=0d;
            Double jinchu_circulatingWater=0d;
            Double jinglian_circulatingWater=circulatingWaterMap.get("FIT-092202-02-EQ")+circulatingWaterMap.get("FIT-092202-03-EQ");
            if(jinglian_circulatingWater==null)  jinglian_circulatingWater=0d;
            Double dongli_circulatingWater=circulatingWaterMap.get("FIT-092101-01-EQ")+circulatingWaterMap.get("FIT-092101-02-EQ");
            if(dongli_circulatingWater==null)  dongli_circulatingWater=0d;
            Double guolu_circulatingWater=0d;
            Double wushui_circulatingWater=0d;
            Double qc_circulatingWater=0d;
            Double yunxing_circulatingWater=0d;
            Double xingzheng_circulatingWater=0d;
            addCostValue(sections,resultList,fajiao_circulatingWater,yuchuli_circulatingWater,jinchu_circulatingWater,jinglian_circulatingWater,dongli_circulatingWater,guolu_circulatingWater,wushui_circulatingWater,qc_circulatingWater,yunxing_circulatingWater,xingzheng_circulatingWater);
        }else if(medium.equals("冷冻水")){
            //sqlField="Cumulative_flow_of_frozen_water";
            //resultList=sectionAnalysisMapper.getSankeyData(sections,sqlField,startDate,beforeOneDay);
            //消耗量
            ParameterUtil.setFreezeWaterList();
            Map<String,Double> freezeWaterMap=this.getConsumptionValue(ParameterUtil.getFreezeWaterList(),startDate,dayCurr,realMark,"day_freezeWater","realValue_freezeWater_table");
            Double fajiao_freezeWater_EP=freezeWaterMap.get("FIT-092202-01-EQ")-freezeWaterMap.get("FIT-092202-02-EQ");
            if(fajiao_freezeWater_EP==null) fajiao_freezeWater_EP=0d;
            Double yuchuli_freezeWater_EP=0d;
            Double jinchu_freezeWater_EP=0d;
            Double jianglian_freezeWater_EP=freezeWaterMap.get("FIT-092202-02-EQ");
            if(jianglian_freezeWater_EP==null)  jianglian_freezeWater_EP=0d;
            Double dongli_freezeWater_EP=freezeWaterMap.get("FIT-092202-01-EQ");
            if(dongli_freezeWater_EP==null)  dongli_freezeWater_EP=0d;
            Double guolu_freezeWater_EP=0d;
            Double wushui_freezeWater_EP=0d;
            Double qc_freezeWater_EP=0d;
            Double yunxing_freezeWater_EP=0d;
            Double xingzheng_freezeWater_EP=0d;
            addCostValue(sections,resultList,fajiao_freezeWater_EP,yuchuli_freezeWater_EP,jinchu_freezeWater_EP,jianglian_freezeWater_EP,dongli_freezeWater_EP,guolu_freezeWater_EP,wushui_freezeWater_EP,qc_freezeWater_EP,yunxing_freezeWater_EP,xingzheng_freezeWater_EP);
        }else if(medium.equals("蒸汽")){
            //sqlField="Cumulative_steam_flow";
           // resultList=sectionAnalysisMapper.getSankeyData(sections,sqlField,startDate,beforeOneDay);
            //消耗量
            ParameterUtil.setSteamList();
            Map<String,Double> steamMap=this.getConsumptionValue(ParameterUtil.getSteamList(),startDate,dayCurr,realMark,"day_steamMeter","realValue_steamMeter_table");
            Double fajiao_steamMeter=steamMap.get("NG_LX_ALL")+steamMap.get("NG_FJ_ALL")+steamMap.get("NG_HCL_ALL");
            if(fajiao_steamMeter==null) fajiao_steamMeter=0d;
            Double yuchuli_steamMeter=0d;
            Double jinchu_steamMeter=0d;
            Double jinglian_steamMeter=steamMap.get("FIT-031001-01-EQ");
            if(jinglian_steamMeter==null)  jinglian_steamMeter=0d;
            Double dongli_steamMeter=0d;
            Double guolu_steamMeter=steamMap.get("FIT-100601-04-EQ");
            if(guolu_steamMeter==null)  guolu_steamMeter=0d;
            Double wushui_steamMeter=steamMap.get("FIT-092501-09-EQ");
            if(wushui_steamMeter==null)  wushui_steamMeter=0d;
            Double qc_steamMeter=0d;
            Double yunxing_steamMeter=0d;
            Double xingzheng_steamMeter=steamMap.get("FIT-100601-02-EQ")+steamMap.get("FIT-100601-03-EQ")-steamMap.get("FIT-100601-04-EQ")
                    -steamMap.get("FIT-012001-10-EQ")-steamMap.get("FIT-092501-09-EQ")-steamMap.get("FIT-031001-01-EQ");
            if(xingzheng_steamMeter==null)  xingzheng_steamMeter=0d;
            addCostValue(sections,resultList,fajiao_steamMeter,yuchuli_steamMeter,jinchu_steamMeter,jinglian_steamMeter,dongli_steamMeter,guolu_steamMeter,wushui_steamMeter,qc_steamMeter,yunxing_steamMeter,xingzheng_steamMeter);
        }else if(medium.equals("空气")){
            sqlField="Cumulative_air_flow";
            //resultList=sectionAnalysisMapper.getSankeyData(sections,sqlField,startDate,beforeOneDay);
            //消耗量
            ParameterUtil.setFerAirList();
            Map<String,Double> ferAirMap=this.getConsumptionValue(ParameterUtil.getFerAirList(),startDate,dayCurr,realMark,"day_fermentationAir","realValue_fermentationAir_table");
            Double fajiao_fermentationAir=ferAirMap.get("FIT-012801-02-EQ")+ferAirMap.get("FIT-012801-03-EQ")+ferAirMap.get("FIT-012801-04-EQ")
                    +ferAirMap.get("FIT-012801-05-EQ")+ferAirMap.get("FIT-012801-06-EQ")+ferAirMap.get("FIT-012801-07-EQ");
            if(fajiao_fermentationAir==null) fajiao_fermentationAir=0d;
            Double yuchuli_fermentationAir=0d;
            Double jinchu_fermentationAir=0d;
            Double jinglian_fermentationAir=0d;
            Double dongli_fermentationAir=0d;
            Double guolu_fermentationAir=0d;
            Double wushui_fermentationAir=0d;
            Double qc_fermentationAir=0d;
            Double yunxing_fermentationAir=0d;
            Double xingzheng_fermentationAir=0d;
            addCostValue(sections,resultList,fajiao_fermentationAir,yuchuli_fermentationAir,jinchu_fermentationAir,jinglian_fermentationAir,dongli_fermentationAir,guolu_fermentationAir,wushui_fermentationAir,qc_fermentationAir,yunxing_fermentationAir,xingzheng_fermentationAir);
        }
        List<SankeyData> screenList=new ArrayList<>();
        //和传入的工段顺序一致
        for(String se:sections){
            for(int index=0;index<resultList.size();index++){
                SankeyData sankeyData=resultList.get(index);
                if(sankeyData.getSection().equals(se)){
                    screenList.add(sankeyData);
                    resultList.remove(index);
                    index--;
                    break;
                }
            }
        }
        return screenList;
    }

    @Override
    public void calculateSankeyData() {

    }

    @Override
    public String selectSankeyRangeDate(String method) {
        return sectionAnalysisMapper.selectSankeyRangeDate(method);
    }

    private void addCostValue(List<String> sections,List<SankeyData> resultList,Double fajiao_v,Double yuchuli_v,Double jinchu_v,
                                  Double jinglian_v,Double dongli_v,Double guolu_v,Double wushui_v,Double qc_v,
                                  Double yunxing_v,Double xingzheng_v) {
        DecimalFormat df = new DecimalFormat("0.00");
        for(String ss:sections){
            SankeyData sankeyData=new SankeyData();
            sankeyData.setSection(ss);
            if(ss.equals("fajiao")) {
                sankeyData.setSecValue(df.format(fajiao_v));
            }
            if(ss.equals("yuchuli")){
                sankeyData.setSecValue(df.format(yuchuli_v));
            }
            if(ss.equals("jinchu")){
                sankeyData.setSecValue(df.format(jinchu_v));
            }
            if(ss.equals("jinglian")){
                sankeyData.setSecValue(df.format(jinglian_v));
            }
            if(ss.equals("dongli")){
                sankeyData.setSecValue(df.format(dongli_v));
            }
            if(ss.equals("guolu")){
                sankeyData.setSecValue(df.format(guolu_v));
            }
            if(ss.equals("wushui")){
                sankeyData.setSecValue(df.format(wushui_v));
            }
            if(ss.equals("qc")){
                sankeyData.setSecValue(df.format(qc_v));
            }
            if(ss.equals("yunxing")){
                sankeyData.setSecValue(df.format(yunxing_v));
            }
            if(ss.equals("xingzheng")){
                sankeyData.setSecValue(df.format(xingzheng_v));
            }
            resultList.add(sankeyData);
        }
    }

    private List<SectionAnalysis> getHomePageData_2(List<String> sections, String startDate, String dayCurr) {
        List<SectionAnalysis> resultList=new ArrayList<SectionAnalysis>();
        String realMark="0";
        if (sdf.format(new Date()).equals(dayCurr.trim())) {
            realMark = END_TIME;
        }
        //电
        ParameterUtil.setEleList();
        Map<String,Double> eleMap=this.getConsumptionValue(ParameterUtil.getEleList(),startDate,dayCurr,realMark,"day_power","realValue_power_table");
        //水
        ParameterUtil.setTapWaterList();
        Map<String,Double> waterMap=this.getConsumptionValue(ParameterUtil.getTapWaterList(),startDate,dayCurr,realMark,"day_tapWater","realValue_tapWater_table");
        //蒸汽
        ParameterUtil.setSteamList();
        Map<String,Double> steamMap=this.getConsumptionValue(ParameterUtil.getSteamList(),startDate,dayCurr,realMark,"day_steamMeter","realValue_steamMeter_table");

        DecimalFormat df = new DecimalFormat("0.00");
        for(String section:sections){
            SectionAnalysis sectionAnalysis=new SectionAnalysis();
            sectionAnalysis.setSection(section);
            if(section.equals("fajiao")){
                Double zz=eleMap.get("JT-110910-01-EP");
                Double fajiao_power=eleMap.get("JT-110907-01-EP")+eleMap.get("JT-110907-02-EP")+eleMap.get("JT-110907-03-EP")+eleMap.get("JT-110907-05-EP")
                        +eleMap.get("JT-110905-08-EP")+eleMap.get("JT-110905-01-EP")+eleMap.get("JT-110905-05-EP")+eleMap.get("JT-110905-04-EP")
                        +eleMap.get("JT-110905-02-EP")+eleMap.get("JT-110905-06-EP")+eleMap.get("JT-110905-07-EP")+eleMap.get("JT-110904-05-EP")
                        +eleMap.get("JT-110904-04-EP")+eleMap.get("JT-110904-03-EP")+eleMap.get("JT-110904-02-EP")+eleMap.get("JT-110904-01-EP")
                        +eleMap.get("JT-110906-02-EP")+eleMap.get("JT-110906-01-EP")+eleMap.get("JT-110903-00-EP")+eleMap.get("JT-110904-06-EP")
                        +eleMap.get("JT-110904-01-EP")+eleMap.get("JT-110914-02-EP")+eleMap.get("JT-110914-03-EP")+eleMap.get("JT-110914-04-EP")
                        +eleMap.get("JT-110902-01-EP")+eleMap.get("JT-110902-02-EP")+eleMap.get("JT-110902-03-EP")+eleMap.get("JT-110902-04-EP")
                        +eleMap.get("JT-110902-05-EP")+eleMap.get("JT-110902-06-EP")+eleMap.get("JT-110902-07-EP")+eleMap.get("JT-110902-08-EP")
                        +eleMap.get("JT-110902-09-EP")+eleMap.get("JT-110910-10-EP")+eleMap.get("JT-110910-12-EP");
                Double fajiao=0d;
                if(zz!=null&&fajiao_power!=null) fajiao =zz - fajiao_power;
                sectionAnalysis.setActive_electric_energy(df.format(fajiao));
                Double fajiao_tapwater=waterMap.get("FIT-092501-08-EQ");
                if(fajiao_tapwater==null) fajiao_tapwater=0d;
                sectionAnalysis.setCumulative_flow_of_tap_water(df.format(fajiao_tapwater));
                Double fajiao_steamMeter=steamMap.get("NG_LX_ALL")+steamMap.get("NG_FJ_ALL")+steamMap.get("NG_HCL_ALL");
                if(fajiao_steamMeter==null) fajiao_steamMeter=0d;
                sectionAnalysis.setCumulative_steam_flow(df.format(fajiao_steamMeter));
            }else if(section.equals("jinglian")){
                Double jinglian=eleMap.get("JT-110903-01-EP")+eleMap.get("JT-110903-02-EP")+eleMap.get("JT-110903-03-EP")+eleMap.get("JT-110903-04-EP")
                        +eleMap.get("JT-110903-05-EP")+eleMap.get("JT-110903-06-EP")+eleMap.get("JT-110903-07-EP")+eleMap.get("JT-110903-08-EP")
                        +eleMap.get("JT-110903-09-EP")+eleMap.get("JT-110903-10-EP")+eleMap.get("JT-110903-11-EP")+eleMap.get("JT-110903-12-EP")
                        +eleMap.get("JT-110903-13-EP")+eleMap.get("JT-110909-05-EP");
                if(jinglian==null) jinglian=0d;
                sectionAnalysis.setActive_electric_energy(df.format(jinglian));
                Double jinglian_tapwater=waterMap.get("FIT-092501-06-EQ");
                if(jinglian_tapwater==null)  jinglian_tapwater=0d;
                sectionAnalysis.setCumulative_flow_of_tap_water(df.format(jinglian_tapwater));
                Double jinglian_steamMeter=sectionAnalysisMapper.jinglian_steamMeter(startDate, dayCurr);
                if(jinglian_steamMeter==null)  jinglian_steamMeter=0d;
                sectionAnalysis.setCumulative_steam_flow(df.format(jinglian_steamMeter));
            }else if(section.equals("dongli")){
                Double guolu =eleMap.get("JT-110910-05-EP")-eleMap.get("JT-110910-06-EP");
                if(guolu==null) guolu=0d;
                Double wushui = eleMap.get("JT-110910-08-EP");
                if(wushui==null) wushui=0d;
                Double dongli_power=eleMap.get("JT-110909-01-EP")+eleMap.get("JT-110910-04-EP")+eleMap.get("JT-110910-02-EP")
                        +eleMap.get("JT-110910-10-EP")+eleMap.get("JT-110909-03-EP")+eleMap.get("JT-110909-04-EP")
                        +eleMap.get("JT-110910-03-EP")+eleMap.get("JT-110909-02-EP");
                Double jzxlk=eleMap.get("JT-110905-08-EP");
                Double dongli=0d;
                if(dongli_power!=null&&guolu!=null&&wushui!=null&&jzxlk!=null){
                    dongli=dongli_power-guolu-wushui-jzxlk;
                }
                sectionAnalysis.setActive_electric_energy(df.format(dongli));
                Double dongli_tapwater=waterMap.get("FIT-092501-02-EQ");
                if(dongli_tapwater==null)  dongli_tapwater=0d;
                sectionAnalysis.setCumulative_flow_of_tap_water(df.format(dongli_tapwater));
                Double dongli_steamMeter=0d;
                sectionAnalysis.setCumulative_steam_flow(df.format(dongli_steamMeter));
            }else if(section.equals("guolu")){
                Double guolu =eleMap.get("JT-110910-05-EP")-eleMap.get("JT-110910-06-EP");
                if(guolu==null) guolu=0d;
                sectionAnalysis.setActive_electric_energy(df.format(guolu));
                Double guolu_tapwater=waterMap.get("FIT-092501-03-EQ");
                if(guolu_tapwater==null)  guolu_tapwater=0d;
                sectionAnalysis.setCumulative_flow_of_tap_water(df.format(guolu_tapwater));
                Double guolu_steamMeter=sectionAnalysisMapper.guolu_steamMeter(startDate, dayCurr);
                if(guolu_steamMeter==null)  guolu_steamMeter=0d;
                sectionAnalysis.setCumulative_steam_flow(df.format(guolu_steamMeter));
            }else if(section.equals("wushui")){
                Double wushui = eleMap.get("JT-110910-08-EP");
                if(wushui==null) wushui=0d;
                sectionAnalysis.setActive_electric_energy(df.format(wushui));
                Double wushui_tapwater=waterMap.get("FIT-092501-05-EQ");
                if(wushui_tapwater==null)  wushui_tapwater=0d;
                sectionAnalysis.setCumulative_flow_of_tap_water(df.format(wushui_tapwater));
                Double wushui_steamMeter=steamMap.get("FIT-092501-09-EQ");
                if(wushui_steamMeter==null)  wushui_steamMeter=0d;
                sectionAnalysis.setCumulative_steam_flow(df.format(wushui_steamMeter));
            }
            resultList.add(sectionAnalysis);
        }
        return resultList;
    }

    //根据传入的日期减去一天
    public String subtractDay(String date) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String returnDate=date;
        try {
            Calendar rightNow = Calendar.getInstance();
            Date begin_d1=df.parse(date);
            rightNow.setTime(begin_d1);
            rightNow.add(Calendar.DAY_OF_MONTH,-1);
            returnDate=df.format(rightNow.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
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

    //根据tagnameList的集合计算对应的耗值
    @Override
    public Map<String,Double> getConsumptionValue(List<String> tagnameList, String startDate, String endDate,String realMark,String hisTablename,String realTablename) {
       DateTime datime= DateUtil.date();
        String currMonth=datime.toString("yyyy-MM");
        Double dd=0d;
        Map<String,Double> resultMap=new HashMap<String,Double>();
        List<Instrument> instrumentList=new ArrayList<>();
        //查出手输的记录的集合
        if(tagnameList.size()>0){
            instrumentList=instrumentMapper.selectByTagnameList(tagnameList,startDate,endDate);
        }
        List<String> tagnameDbList=new ArrayList<>();
        for(Instrument instrument:instrumentList){
            //将字母转换为大写
            if(instrument.getTagname().equals("Fit-100601-02-EQ")){
                System.out.println(instrument.getTagname());
                System.out.println("<<=====>>"+instrument.getTagname().toUpperCase());
            }
            tagnameDbList.add(instrument.getTagname().toUpperCase());
        }
        if(tagnameDbList.size()>0) tagnameList.removeAll(tagnameDbList);
        List<TimeJob>  timeJobList=new ArrayList<>();
        if(tagnameList.size()>0) timeJobList=sectionAnalysisMapper.getConsumptionValue(tagnameList,startDate,endDate,realMark,hisTablename,realTablename);
        for(String tagname:tagnameList){
            boolean flag=true;
            for(int index=0;index<timeJobList.size();index++){
                TimeJob timeJob=timeJobList.get(index);
                /*System.out.println(timeJob.getTagname());
                System.out.println("=====>>"+timeJob.getTagname().toUpperCase());*/
                if(tagname.equals(timeJob.getTagname().toUpperCase())){
                    Double costValue=0d;
                    if(!StringUtils.isEmpty(timeJob.getTime_value())) costValue=Double.valueOf(timeJob.getTime_value());
                    //if(costValue<0) costValue=0d;
                    //张三三和华三三的表需要乘上倍率(12000)
                    if(tagname.equals("JT-110900-02-EP")||tagname.equals("JT-110900-01-EP")){
                        resultMap.put(tagname,costValue*ParameterUtil.MAGNIFICATION);
                    }else{
                        //2019年12月份K3空压机电表值清零，需要做处理
                        if(tagname.equals("JT-110910-04-EP")){
                            List<String> tagnameList_2=new ArrayList<>();tagnameList_2.add("JT-110910-04-EP");
                            Double preValue=0d;
                            Double laterValue=0d;
                            if(this.compareToDate_2(startDate,"2019-12-17",endDate)>0){
                                String timeValue=sectionAnalysisMapper.getConsumptionValue(tagnameList_2,startDate,"2019-12-17",realMark,hisTablename,realTablename).get(0).getTime_value();
                                preValue=Double.valueOf(timeValue);
                                if(this.compareToDate_2(startDate,"2019-12-19",endDate)>0){
                                    String timeValue_2=sectionAnalysisMapper.getConsumptionValue(tagnameList_2,"2019-12-19",endDate,realMark,hisTablename,realTablename).get(0).getTime_value();
                                    preValue=Double.valueOf(timeValue_2);
                                }
                                resultMap.put(tagname,preValue+laterValue);
                            }else{
                                resultMap.put(tagname,costValue);
                            }
                        }else{
                            resultMap.put(tagname,costValue);
                        }

                    }
                    flag=false;
                    timeJobList.remove(index);
                    index--;
                    break;
                }
            }
            if(flag){
                resultMap.put(tagname,0d);
            }
        }
        for(Instrument instrument:instrumentList){
            Double costValue=0d;
            if(!StringUtils.isEmpty(instrument.getRealValue())) costValue=Double.valueOf(instrument.getRealValue());
            //张三三和华三三的表需要乘上倍率(12000)
            String tagname=instrument.getTagname().toUpperCase();
            if(tagname.equals("JT-110900-02-EP")||tagname.equals("JT-110900-01-EP")){
                resultMap.put(tagname,costValue*ParameterUtil.MAGNIFICATION);
            }else{
                resultMap.put(tagname,costValue);
            }
        }
        return resultMap;
    }

    public int compareToDate_2(String start_t, String queryDate, String end_t) {
        if(StringUtils.isEmpty(start_t)||StringUtils.isEmpty(end_t)) return -1;
        Date startDate=null;
        Date endDate=null;
        Date compareDate=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            startDate = sdf.parse(start_t);
            endDate= sdf.parse(end_t);
            compareDate=sdf.parse(queryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(compareDate.compareTo(endDate)<0&&compareDate.compareTo(startDate)>=0){
            return 1;
        }else{
            return -1;
        }
    }

}
