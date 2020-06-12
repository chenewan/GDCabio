package com.byd.emg.pojo;

public class DataMonitoringDashboard {
    private long monitoring_id;

    private String dash_tagname;

    private String upper_limit;

    private String lower_limit;

    private String customname;

    private String name_display;

    public long getMonitoring_id() {
        return monitoring_id;
    }

    public void setMonitoring_id(long monitoring_id) {
        this.monitoring_id = monitoring_id;
    }

    public String getDash_tagname() {
        return dash_tagname;
    }

    public void setDash_tagname(String dash_tagname) {
        this.dash_tagname = dash_tagname;
    }

    public String getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(String upper_limit) {
        this.upper_limit = upper_limit;
    }

    public String getLower_limit() {
        return lower_limit;
    }

    public void setLower_limit(String lower_limit) {
        this.lower_limit = lower_limit;
    }

    public String getCustomname() {
        return customname;
    }

    public void setCustomname(String customname) {
        this.customname = customname;
    }

    public String getName_display() {
        return name_display;
    }

    public void setName_display(String name_display) {
        this.name_display = name_display;
    }
}
