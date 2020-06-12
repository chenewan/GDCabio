package com.byd.emg.pojo;

import java.io.Serializable;

//电表、水表、气表的集合(real_tz_general_table原始数据表的实体类)
public class ResourceMeter implements Serializable {

    private long id;

    private long num;

    private String tagname;

    private String maxValue;

    private String minValue;

    private String describe;

    private String area;

    private String workShop;

    private String unitSymbol;

    private String type;
    private String Name_display_settings;

    public String getName_display_settings() {
        return Name_display_settings;
    }

    public void setName_display_settings(String name_display_settings) {
        Name_display_settings = name_display_settings;
    }

    private DataMonitoringDashboard DataMonitoringDashboard;

    public com.byd.emg.pojo.DataMonitoringDashboard getDataMonitoringDashboard() {
        return DataMonitoringDashboard;
    }

    public void setDataMonitoringDashboard(com.byd.emg.pojo.DataMonitoringDashboard dataMonitoringDashboard) {
        DataMonitoringDashboard = dataMonitoringDashboard;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getNum() { return num; }

    public void setNum(long num) { this.num = num; }

    public String getTagname() { return tagname; }

    public void setTagname(String tagname) { this.tagname = tagname; }

    public String getDescribe() { return describe; }

    public void setDescribe(String describe) { this.describe = describe; }

    public String getMaxValue() { return maxValue; }

    public void setMaxValue(String maxValue) { this.maxValue = maxValue; }

    public String getMinValue() { return minValue; }

    public void setMinValue(String minValue) { this.minValue = minValue; }

    public String getArea() { return area; }

    public void setArea(String area) { this.area = area; }

    public String getWorkShop() { return workShop; }

    public void setWorkShop(String workShop) { this.workShop = workShop; }

    public String getUnitSymbol() { return unitSymbol; }

    public void setUnitSymbol(String unitSymbol) { this.unitSymbol = unitSymbol; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
