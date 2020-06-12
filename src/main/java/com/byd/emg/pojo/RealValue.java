package com.byd.emg.pojo;

public class RealValue {

    private long realValue_id;

    private String cabinet_name;

    private String cabinet_number;

    private String realValue_tagname;

    private String realValue;

    //实时效率
    private String realTimeEfficiency;

    //产气效率
    private String biogasProductionEfficiency;

    private String totalRunningTime;

    private String loadingTime;

    private String noLoadTime;

    //开机次数
    private String openingTimes;

    private String date;

    private String time;

    public long getRealValue_id() {
        return realValue_id;
    }

    public void setRealValue_id(long realValue_id) {
        this.realValue_id = realValue_id;
    }

    public String getCabinet_name() {
        return cabinet_name;
    }

    public void setCabinet_name(String cabinet_name) {
        this.cabinet_name = cabinet_name;
    }

    public String getCabinet_number() {
        return cabinet_number;
    }

    public void setCabinet_number(String cabinet_number) {
        this.cabinet_number = cabinet_number;
    }

    public String getRealValue_tagname() {
        return realValue_tagname;
    }

    public void setRealValue_tagname(String realValue_tagname) {
        this.realValue_tagname = realValue_tagname;
    }

    public String getRealValue() {
        return realValue;
    }

    public void setRealValue(String realValue) {
        this.realValue = realValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRealTimeEfficiency() {
        return realTimeEfficiency;
    }

    public void setRealTimeEfficiency(String realTimeEfficiency) {
        this.realTimeEfficiency = realTimeEfficiency;
    }

    public String getBiogasProductionEfficiency() {
        return biogasProductionEfficiency;
    }

    public void setBiogasProductionEfficiency(String biogasProductionEfficiency) {
        this.biogasProductionEfficiency = biogasProductionEfficiency;
    }

    public String getTotalRunningTime() {
        return totalRunningTime;
    }

    public void setTotalRunningTime(String totalRunningTime) {
        this.totalRunningTime = totalRunningTime;
    }

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    public String getNoLoadTime() {
        return noLoadTime;
    }

    public void setNoLoadTime(String noLoadTime) {
        this.noLoadTime = noLoadTime;
    }

    public String getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(String openingTimes) {
        this.openingTimes = openingTimes;
    }
}
