package com.byd.emg.resultData;

//单位成本分析数据的包装类
public class UnitCostData {

    private String yields;

    private String productType;

    private String powerCost;

    private String tapwaterCost;

    private String circulateWaterCost;

    private String freezeWaterCost;

    private String steamCost;

    private String faJiaoAirCost;

    private String unitCost;

    private String startDate;


    private String endDate;

    public UnitCostData(String yields,String productType,String powerCost, String tapwaterCost, String steamCost, String circulateWaterCost, String freezeWaterCost, String faJiaoAirCost, String unitCost, String startDate, String endDate) {
        this.yields=yields;
        this.productType=productType;
        this.powerCost=powerCost;
        this.tapwaterCost=tapwaterCost;
        this.steamCost=steamCost;
        this.circulateWaterCost=circulateWaterCost;
        this.freezeWaterCost=freezeWaterCost;
        this.faJiaoAirCost=faJiaoAirCost;
        this.unitCost=unitCost;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public String getYields() {
        return yields;
    }

    public void setYields(String yields) {
        this.yields = yields;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPowerCost() {
        return powerCost;
    }

    public void setPowerCost(String powerCost) {
        this.powerCost = powerCost;
    }

    public String getTapwaterCost() {
        return tapwaterCost;
    }

    public void setTapwaterCost(String tapwaterCost) {
        this.tapwaterCost = tapwaterCost;
    }

    public String getCirculateWaterCost() {
        return circulateWaterCost;
    }

    public void setCirculateWaterCost(String circulateWaterCost) {
        this.circulateWaterCost = circulateWaterCost;
    }

    public String getFreezeWaterCost() {
        return freezeWaterCost;
    }

    public void setFreezeWaterCost(String freezeWaterCost) {
        this.freezeWaterCost = freezeWaterCost;
    }

    public String getSteamCost() {
        return steamCost;
    }

    public void setSteamCost(String steamCost) {
        this.steamCost = steamCost;
    }

    public String getFaJiaoAirCost() {
        return faJiaoAirCost;
    }

    public void setFaJiaoAirCost(String faJiaoAirCost) {
        this.faJiaoAirCost = faJiaoAirCost;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
