package com.byd.emg.mapper;

import com.byd.emg.pojo.SectionAnalysis;
import com.byd.emg.pojo.TimeJob;
import com.byd.emg.resultData.SankeyData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SectionAnalysisMapper {



   /* List<SectionAnalysis> getsectionAnalysis_Start(@Param("Section_name")String Section_name, @Param("Start_time")String Start_time_1);


    List<SectionAnalysis> getsectionAnalysis_End(@Param("Section_name")String Section_name, @Param("End_time")String End_time);

    List<SectionAnalysis> getsectionAnalysis_Start_2(@Param("Section_name")String Section_name,@Param("Start_time")String Start_time_2);


    List<SectionAnalysis> getsectionAnalysis_End_2(@Param("Section_name")String Section_name,@Param("End_time")String End_time_2);



    String getsectionAnalysis_Histogram_Start(@Param("Section_name")String Section_name, @Param("Start_time")String Start_time_1,@Param("medium")String medium);


    String getsectionAnalysis_Histogram_End(@Param("Section_name")String Section_name, @Param("End_time")String End_time,@Param("medium")String medium);

    String getsectionAnalysis_Histogram_Start_2(@Param("Section_name")String Section_name,@Param("Start_time")String Start_time_2,@Param("medium")String medium);


    String getsectionAnalysis_Histogram_End_2(@Param("Section_name")String Section_name,@Param("End_time")String End_time_2,@Param("medium")String medium);
*/
    //1.1
    Double fajiao_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //1.2
    Double yuchuli_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //1.3
    Double jinchu_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //1.4
    Double jinglian_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //1.5
    Double dongli_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //1.6
    Double guolu_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //1.7
    Double wushui_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //1.8
    Double qc_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //1.9
    Double yunxing_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //1.10
    Double xingzheng_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //2.1
    Double xunhuan_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //2.2
    Double lengdong_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //2.3
    Double yasuo_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    //2.4
    Double yibiao_power(@Param("Start_t")String Start_t,@Param("End_t")String End_t);

    Double fjbyq(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double dlllg(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double jzxlk(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double ldsxhtxt(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double xhsbtxt(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double qykyj(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double kyjbtxt(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    void insert_sectionpower(@Param("list")List<SectionAnalysis> list);

    Double zongxunhuanshui_EP(@Param("Start_t")String Start_t,@Param("End_t")String End_t);

    Double zongxunhuanshui_TE(@Param("Start_t")String Start_t,@Param("End_t")String End_t);

    Double fajiao_tapwater(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double jinglian_tapwater(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double dongli_tapwater(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double guolu_tapwater(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double wushui_tapwater(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double xingzheng_tapwater(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double fajiao_circulatingWate_EP(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double jinglian_circulatingWate_EP(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double dongli_circulatingWate_EP(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double fajiao_circulatingWate_TE(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double jinglian_circulatingWate_TE(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double dongli_circulatingWate_TE(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double fajiao_freezeWater_EP(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double jianglian_freezeWater_EP(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double dongli_freezeWater_EP(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double fajiao_freezeWater_TE(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double jianglian_freezeWater_TE(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double dongli_freezeWater_TE(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double fajiao_steamMeter(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double jinglian_steamMeter(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double guolu_steamMeter(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double wushui_steamMeter(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double xingzheng_steamMeter(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double fajiao_fermentationAir(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Double zong_steamMeter(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    Integer serialn_numberEnd();
    List<SectionAnalysis> ReportForm_energy_consumption(@Param("Start_t")String Start_t,@Param("End_t")String End_t);
    String selectDate_time(@Param("method") String method);


    String selectSankeyRangeDate(@Param("method") String method);


    List<TimeJob> getConsumptionValue(@Param("tagnameList") List<String> tagnameList,@Param("startDate")  String startDate,@Param("endDate")  String endDate,@Param("realMark") String realMark,@Param("hisTablename") String hisTablename,@Param("realTablename")String realTablename);

    List<String> getTestDateList();

    Double getK3Value(@Param("tagname") String tagname);
}
