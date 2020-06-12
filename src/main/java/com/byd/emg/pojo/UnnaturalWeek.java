package com.byd.emg.pojo;

import java.util.Objects;

public class UnnaturalWeek {

    private Long id;

    private String productType;

    private String water;

    private String electricity;

    private String steam;

    private String startDate;

    private String endDate;

    public UnnaturalWeek() {}

    public UnnaturalWeek(String productType, String water, String electricity, String steam, String startDate,String endDate) {
        this.productType = productType;
        this.water = water;
        this.electricity = electricity;
        this.steam = steam;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnnaturalWeek that = (UnnaturalWeek) o;
        return Objects.equals(productType, that.productType) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, startDate, endDate);
    }

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

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getSteam() {
        return steam;
    }

    public void setSteam(String steam) {
        this.steam = steam;
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
