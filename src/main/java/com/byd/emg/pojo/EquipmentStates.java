package com.byd.emg.pojo;

public class EquipmentStates {

    private Integer id;

    private String tagName;

    private String totalRunningTime;

    private String loadTime;

    private String deadTime;

    private String openTimes;

    private String updateTime;

    private String realTagName;

    private String tagNameValue;

    public EquipmentStates() {
    }

    public EquipmentStates(Integer id, String tagName, String totalRunningTime, String loadTime, String deadTime, String openTimes, String updateTime, String realTagName) {
        this.id = id;
        this.tagName = tagName;
        this.totalRunningTime = totalRunningTime;
        this.loadTime = loadTime;
        this.deadTime = deadTime;
        this.openTimes = openTimes;
        this.updateTime = updateTime;
        this.realTagName = realTagName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTotalRunningTime() {
        return totalRunningTime;
    }

    public void setTotalRunningTime(String totalRunningTime) {
        this.totalRunningTime = totalRunningTime;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public String getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(String deadTime) {
        this.deadTime = deadTime;
    }

    public String getOpenTimes() {
        return openTimes;
    }

    public void setOpenTimes(String openTimes) {
        this.openTimes = openTimes;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRealTagName() {
        return realTagName;
    }

    public void setRealTagName(String realTagName) {
        this.realTagName = realTagName;
    }

    public String getTagNameValue() {
        return tagNameValue;
    }

    public void setTagNameValue(String tagNameValue) {
        this.tagNameValue = tagNameValue;
    }
}
