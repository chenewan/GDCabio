package com.byd.emg.pojo;

public class ResultPageData {

    //ResourceMeter
    private long num;

    private String tagname;

    private String maxValue;

    private String minValue;

    private String describe;

    private String area;

    private String workShop;

    private String unitSymbol;

    private String type;

    //EnergyMeterPage
    private Long monitoring_id;

    private String upperLimit;

    private String lowerLimit;

    //RealTimeValue
    private Long realValue_id;

    private  String realValue;

    private String time;



    public String getUpper_color() {
        return upper_color;
    }

    public void setUpper_color(String upper_color) {
        this.upper_color = upper_color;
    }

    public String getLower_color() {
        return lower_color;
    }

    public void setLower_color(String lower_color) {
        this.lower_color = lower_color;
    }

    private String upper_color;

    private String lower_color;

    private String custom_name;

    private String  name_display;

    public String getName_display() {
        return name_display;
    }

    public void setName_display(String name_display) {
        this.name_display = name_display;
    }

    public String getCustom_name() {
        return custom_name;
    }

    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWorkShop() {
        return workShop;
    }

    public void setWorkShop(String workShop) {
        this.workShop = workShop;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    public void setUnitSymbol(String unitSymbol) {
        this.unitSymbol = unitSymbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getRealValue() {
        return realValue;
    }

    public void setRealValue(String realValue) {
        this.realValue = realValue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getMonitoring_id() {
        return monitoring_id;
    }

    public void setMonitoring_id(Long monitoring_id) {
        this.monitoring_id = monitoring_id;
    }

    public Long getRealValue_id() {
        return realValue_id;
    }

    public void setRealValue_id(Long realValue_id) {
        this.realValue_id = realValue_id;
    }
}
