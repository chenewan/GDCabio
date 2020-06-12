package com.byd.emg.resultData;

//单耗分析数据的包装类
public class UnitEnergyData {

    private String yields;

    private String productType;

    private String unitPower;

    private String unitTapwater;

    private String unitCirculateWater;

    private String unitFreezeWater;

    private String unitSteam;

    private String unitFaJiaoAir;

    private String startDate;

    private String endDate;

    public UnitEnergyData() {}

    public UnitEnergyData(String yields,String productType,String unitPower, String unitTapwater, String unitSteam, String unitCirculateWater, String unitFreezeWater, String unitFaJiaoAir, String startDate, String endDate) {
        this.yields=yields;
        this.productType=productType;
        this.unitPower=unitPower;
        this.unitTapwater=unitTapwater;
        this.unitSteam=unitSteam;
        this.unitCirculateWater=unitCirculateWater;
        this.unitFreezeWater=unitFreezeWater;
        this.unitFaJiaoAir=unitFaJiaoAir;
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

    public String getUnitPower() {
        return unitPower;
    }

    public void setUnitPower(String unitPower) {
        this.unitPower = unitPower;
    }

    public String getUnitTapwater() {
        return unitTapwater;
    }

    public void setUnitTapwater(String unitTapwater) {
        this.unitTapwater = unitTapwater;
    }

    public String getUnitCirculateWater() {
        return unitCirculateWater;
    }

    public void setUnitCirculateWater(String unitCirculateWater) {
        this.unitCirculateWater = unitCirculateWater;
    }

    public String getUnitFreezeWater() {
        return unitFreezeWater;
    }

    public void setUnitFreezeWater(String unitFreezeWater) {
        this.unitFreezeWater = unitFreezeWater;
    }

    public String getUnitSteam() {
        return unitSteam;
    }

    public void setUnitSteam(String unitSteam) {
        this.unitSteam = unitSteam;
    }

    public String getUnitFaJiaoAir() {
        return unitFaJiaoAir;
    }

    public void setUnitFaJiaoAir(String unitFaJiaoAir) {
        this.unitFaJiaoAir = unitFaJiaoAir;
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
