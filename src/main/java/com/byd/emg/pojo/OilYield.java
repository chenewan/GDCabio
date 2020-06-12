package com.byd.emg.pojo;

public class OilYield {

    private Long id;

    private String yields;

    private String productType;

    private String produceDays;

    private String unitPricePower;

    private String unitPriceWater;

    private String unitPriceCircuWater;

    private String unitPriceFreezeWater;

    private String unitPriceSteam;

    private String unitPriceAir;

    private String startDate;

    private String endDate;

    public OilYield() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getYields() {
        return yields;
    }

    public void setYields(String yields) {
        this.yields = yields;
    }

    public String getProduceDays() {
        return produceDays;
    }

    public void setProduceDays(String produceDays) {
        this.produceDays = produceDays;
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

    public String getUnitPricePower() {
        return unitPricePower;
    }

    public void setUnitPricePower(String unitPricePower) {
        this.unitPricePower = unitPricePower;
    }

    public String getUnitPriceWater() {
        return unitPriceWater;
    }

    public void setUnitPriceWater(String unitPriceWater) {
        this.unitPriceWater = unitPriceWater;
    }

    public String getUnitPriceCircuWater() {
        return unitPriceCircuWater;
    }

    public void setUnitPriceCircuWater(String unitPriceCircuWater) {
        this.unitPriceCircuWater = unitPriceCircuWater;
    }

    public String getUnitPriceFreezeWater() {
        return unitPriceFreezeWater;
    }

    public void setUnitPriceFreezeWater(String unitPriceFreezeWater) {
        this.unitPriceFreezeWater = unitPriceFreezeWater;
    }

    public String getUnitPriceSteam() {
        return unitPriceSteam;
    }

    public void setUnitPriceSteam(String unitPriceSteam) {
        this.unitPriceSteam = unitPriceSteam;
    }

    public String getUnitPriceAir() {
        return unitPriceAir;
    }

    public void setUnitPriceAir(String unitPriceAir) {
        this.unitPriceAir = unitPriceAir;
    }
}
