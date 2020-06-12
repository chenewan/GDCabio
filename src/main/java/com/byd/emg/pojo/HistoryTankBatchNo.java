package com.byd.emg.pojo;


public class HistoryTankBatchNo {

    private Long id;

    private String tankNumber;  //灌号

    private String batchNumber; //批次号

    private String steamTotalFlowStart;   //蒸汽开始累计流量

    private String steamTotalFlowEnd;       //蒸汽结束累计流量

    private String airTotalFlowStart;    //空气开始累计流量

    private String airTotalFlowEnd;      //空气结束累计流量

    private String productType;         //产品类型

    private String media;               //介质

    private String steamCostValue;                //蒸汽耗值

    private String airCostValue;         //空气耗值

    private String state;               //状态

    private String startTime;

    private String endTime;

    public HistoryTankBatchNo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTankNumber() {
        return tankNumber;
    }

    public void setTankNumber(String tankNumber) {
        this.tankNumber = tankNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getSteamTotalFlowStart() {
        return steamTotalFlowStart;
    }

    public void setSteamTotalFlowStart(String steamTotalFlowStart) {
        this.steamTotalFlowStart = steamTotalFlowStart;
    }

    public String getSteamTotalFlowEnd() {
        return steamTotalFlowEnd;
    }

    public void setSteamTotalFlowEnd(String steamTotalFlowEnd) {
        this.steamTotalFlowEnd = steamTotalFlowEnd;
    }

    public String getAirTotalFlowStart() {
        return airTotalFlowStart;
    }

    public void setAirTotalFlowStart(String airTotalFlowStart) {
        this.airTotalFlowStart = airTotalFlowStart;
    }

    public String getAirTotalFlowEnd() {
        return airTotalFlowEnd;
    }

    public void setAirTotalFlowEnd(String airTotalFlowEnd) {
        this.airTotalFlowEnd = airTotalFlowEnd;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getSteamCostValue() {
        return steamCostValue;
    }

    public void setSteamCostValue(String steamCostValue) {
        this.steamCostValue = steamCostValue;
    }

    public String getAirCostValue() {
        return airCostValue;
    }

    public void setAirCostValue(String airCostValue) {
        this.airCostValue = airCostValue;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
