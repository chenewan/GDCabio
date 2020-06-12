package com.byd.emg.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//参数设定的工具类
public class ParameterUtil {

    private static List<String> eleList;

    private static List<String> tapWaterList;

    private static List<String> circulateWaterList;

    private static List<String> circulatingTEList;

    private static List<String> freezeWaterList;

    private static List<String> freezeWaterTEList;

    private static List<String> steamList;

    private static List<String> ferAirList;

    public static final int MAGNIFICATION=12000;

    public static List<String> getEleList() {
        return eleList;
    }
    public static void setEleList() {
        Set<String> eleSet=new HashSet<>();
        eleSet.add("JT-110909-06-EP");eleSet.add("JT-110907-01-EP");eleSet.add("JT-110907-02-EP");
        eleSet.add("JT-110907-03-EP");eleSet.add("JT-110907-05-EP");eleSet.add("JT-110905-08-EP");
        eleSet.add("JT-110905-01-EP");eleSet.add("JT-110905-05-EP");eleSet.add("JT-110905-04-EP");
        eleSet.add("JT-110905-02-EP");eleSet.add("JT-110905-06-EP");eleSet.add("JT-110905-07-EP");
        eleSet.add("JT-110904-05-EP");eleSet.add("JT-110904-04-EP");eleSet.add("JT-110904-03-EP");
        eleSet.add("JT-110904-02-EP");eleSet.add("JT-110906-02-EP");eleSet.add("JT-110904-03-EP");
        eleSet.add("JT-110906-01-EP");eleSet.add("JT-110904-06-EP");eleSet.add("JT-110910-01-EP");
        eleSet.add("JT-110914-02-EP");eleSet.add("JT-110914-03-EP");eleSet.add("JT-110904-01-EP");
        eleSet.add("JT-110914-04-EP");eleSet.add("JT-110902-01-EP");eleSet.add("JT-110902-02-EP");
        eleSet.add("JT-110902-03-EP");eleSet.add("JT-110902-04-EP");eleSet.add("JT-110902-05-EP");
        eleSet.add("JT-110902-06-EP");eleSet.add("JT-110902-07-EP");eleSet.add("JT-110902-08-EP");
        eleSet.add("JT-110902-09-EP");eleSet.add("JT-110909-05-EP");eleSet.add("JT-110903-00-EP");
        eleSet.add("JT-110903-01-EP");eleSet.add("JT-110903-02-EP");eleSet.add("JT-110903-03-EP");
        eleSet.add("JT-110903-04-EP");eleSet.add("JT-110903-05-EP");eleSet.add("JT-110903-06-EP");
        eleSet.add("JT-110903-07-EP");eleSet.add("JT-110903-08-EP");eleSet.add("JT-110903-09-EP");
        eleSet.add("JT-110903-10-EP");eleSet.add("JT-110903-11-EP");eleSet.add("JT-110903-12-EP");
        eleSet.add("JT-110903-13-EP");eleSet.add("JT-110903-01-EP");eleSet.add("JT-110903-02-EP");
        eleSet.add("JT-110903-03-EP");eleSet.add("JT-110903-04-EP");eleSet.add("JT-110903-05-EP");
        eleSet.add("JT-110903-06-EP");eleSet.add("JT-110903-07-EP");eleSet.add("JT-110903-08-EP");
        eleSet.add("JT-110903-09-EP");eleSet.add("JT-110903-10-EP");eleSet.add("JT-110903-11-EP");
        eleSet.add("JT-110903-10-EP");eleSet.add("JT-110903-11-EP");eleSet.add("JT-110903-12-EP");
        eleSet.add("JT-110903-13-EP");eleSet.add("JT-110903-01-EP");eleSet.add("JT-110903-02-EP");
        eleSet.add("JT-110903-03-EP");eleSet.add("JT-110903-04-EP");eleSet.add("JT-110903-05-EP");
        eleSet.add("JT-110903-06-EP");eleSet.add("JT-110903-07-EP");eleSet.add("JT-110903-08-EP");
        eleSet.add("JT-110903-09-EP");eleSet.add("JT-110903-10-EP");eleSet.add("JT-110903-11-EP");
        eleSet.add("JT-110903-12-EP");eleSet.add("JT-110903-13-EP");eleSet.add("JT-110909-05-EP");
        eleSet.add("JT-110909-01-EP");eleSet.add("JT-110910-04-EP");eleSet.add("JT-110910-02-EP");
        eleSet.add("JT-110910-10-EP");eleSet.add("JT-110909-03-EP");eleSet.add("JT-110909-04-EP");
        eleSet.add("JT-110910-03-EP");eleSet.add("JT-110909-02-EP");eleSet.add("JT-110910-05-EP");
        eleSet.add("JT-110910-06-EP");eleSet.add("JT-110910-08-EP");eleSet.add("JT-110917-01-EP");
        eleSet.add("JT-110917-02-EP");eleSet.add("JT-110917-03-EP");eleSet.add("JT-110917-04-EP");
        eleSet.add("JT-110917-05-EP");eleSet.add("JT-110917-06-EP");eleSet.add("JT-110917-07-EP");
        eleSet.add("JT-110917-08-EP");eleSet.add("JT-110917-09-EP");eleSet.add("JT-110917-10-EP");
        eleSet.add("JT-110917-11-EP");eleSet.add("JT-110917-12-EP");eleSet.add("JT-110917-13-EP");
        eleSet.add("JT-110916-01-EP");eleSet.add("JT-110916-02-EP");eleSet.add("JT-110916-03-EP");
        eleSet.add("JT-110916-04-EP");eleSet.add("JT-110916-05-EP");eleSet.add("JT-110916-06-EP");
        eleSet.add("JT-110916-07-EP");eleSet.add("JT-110919-01-EP");eleSet.add("JT-110919-02-EP");
        eleSet.add("JT-110919-03-EP");eleSet.add("JT-110919-04-EP");eleSet.add("JT-110919-05-EP");
        eleSet.add("JT-110918-01-EP");eleSet.add("JT-110918-02-EP");eleSet.add("JT-110918-03-EP");
        eleSet.add("JT-110918-04-EP");eleSet.add("JT-110918-05-EP");eleSet.add("JT-110918-06-EP");
        eleSet.add("JT-110904-10-EP");eleSet.add("JT-110904-11-EP");eleSet.add("JT-110904-12-EP");
        eleSet.add("JT-110904-13-EP");eleSet.add("JT-110910-03-EP");eleSet.add("JT-110910-11-EP");
        eleSet.add("JT-110918-07-EP");eleSet.add("JT-110908-04-EP");eleSet.add("JT-110908-05-EP");
        eleSet.add("JT-110908-06-EP");eleSet.add("JT-110910-09-EP");eleSet.add("JT-110910-12-EP");
        ParameterUtil.eleList =new ArrayList<String>(eleSet);
    }

    public static List<String> getCirculateWaterList() {
        return circulateWaterList;
    }

    public static void setCirculateWaterList() {
        Set<String> circulateWaterSet=new HashSet<>();
        circulateWaterSet.add("FIT-092101-01-EQ");circulateWaterSet.add("FIT-092101-02-EQ");
        circulateWaterSet.add("FIT-092202-03-EQ");circulateWaterSet.add("FIT-092202-02-EQ");
        circulateWaterSet.add("FIT-092202-02-EQ");
        ParameterUtil.circulateWaterList=new ArrayList<String>(circulateWaterSet);
    }

    public static List<String> getCirculatingTEList() {
        return circulatingTEList;
    }

    public static void setCirculatingTEList() {
        Set<String> circulatingTESet=new HashSet<>();
        circulatingTESet.add("FIT-092101-01-TE");circulatingTESet.add("FIT-092101-02-TE");
        circulatingTESet.add("FIT-092202-03-TE");circulatingTESet.add("FIT-092202-02-TE");
        ParameterUtil.circulatingTEList=new ArrayList<String>(circulatingTESet);
    }

    public static List<String> getFreezeWaterList() {
        return freezeWaterList;
    }

    public static void setFreezeWaterList() {
        Set<String> freezeWaterSet=new HashSet<>();
        freezeWaterSet.add("FIT-092202-01-EQ");freezeWaterSet.add("FIT-092202-02-EQ");
        ParameterUtil.freezeWaterList=new ArrayList<String>(freezeWaterSet);
    }

    public static List<String> getFreezeWaterTEList() {
        return freezeWaterTEList;
    }

    public static void setFreezeWaterTEList() {
        Set<String> freezeWaterTESet=new HashSet<>();
        freezeWaterTESet.add("FIT-092202-01-TE");freezeWaterTESet.add("FIT-092202-02-TE");
        ParameterUtil.freezeWaterTEList=new ArrayList<String>(freezeWaterTESet);
    }

    public static List<String> getSteamList() {
        return steamList;
    }

    public static void setSteamList() {
        Set<String> steamSet=new HashSet<>();
        steamSet.add("NG_LX_ALL");steamSet.add("NG_FJ_ALL");steamSet.add("NG_HCL_ALL");
        steamSet.add("FIT-031001-01-EQ");steamSet.add("FIT-100601-04-EQ");steamSet.add("FIT-092501-09-EQ");
        steamSet.add("FIT-100601-02-EQ");steamSet.add("FIT-100601-03-EQ");steamSet.add("FIT-100601-04-EQ");
        steamSet.add("FIT-012001-10-EQ");steamSet.add("FIT-092501-09-EQ");steamSet.add("FIT-031001-01-EQ");
        steamSet.add("FIT-100601-02-EQ");steamSet.add("FIT-100601-02-EQ");
        ParameterUtil.steamList=new ArrayList<String>(steamSet);
    }

    public static List<String> getFerAirList() {
        return ferAirList;
    }

    public static void setFerAirList() {
        Set<String> ferAirSet=new HashSet<>();
        ferAirSet.add("FIT-012801-02-EQ");ferAirSet.add("FIT-012801-03-EQ");ferAirSet.add("FIT-012801-04-EQ");
        ferAirSet.add("FIT-012801-05-EQ");ferAirSet.add("FIT-012801-06-EQ");ferAirSet.add("FIT-012801-07-EQ");
        ParameterUtil.ferAirList=new ArrayList<String>(ferAirSet);
    }

    public static List<String> getTapWaterList() {
        return tapWaterList;
    }

    public static void setTapWaterList() {
        Set<String> tapWaterSet=new HashSet<>();
        tapWaterSet.add("FIT-092501-08-EQ");tapWaterSet.add("FIT-092501-02-EQ");tapWaterSet.add("FIT-092501-03-EQ");
        tapWaterSet.add("FIT-092501-05-EQ");tapWaterSet.add("FIT-092501-06-EQ");tapWaterSet.add("FIT-092501-01-EQ");
        //行政用自来水(计算值)
        tapWaterSet.add("FIT-092501-07-EQ");
        ParameterUtil.tapWaterList=new ArrayList<String>(tapWaterSet);
    }
}
