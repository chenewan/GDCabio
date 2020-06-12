package com.byd.emg.pojo;

import java.util.Objects;

public class TimeJob {

    private Long id;
    private String tagname;
    private String  time_value;
    private String his_date;
    private String his_time;
    private String  cabinet_number;
    private String time;

    public TimeJob() {}

    public TimeJob(TimeJob timeJob,String time_value,String date,String time) {
        this.tagname=timeJob.getTagname();
        this.time_value=time_value;
        this.his_date=date;
        this.his_time=time;
        this.cabinet_number=timeJob.getCabinet_number();
    }

    public TimeJob(String value,Instrument instrument) {
        this.tagname=instrument.getTagname();
        this.time_value=value;
        this.his_date=instrument.getStartDate();
        this.his_time=instrument.getEndDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeJob timeJob = (TimeJob) o;
        return Objects.equals(tagname, timeJob.tagname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagname);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getTime_value() {
        return time_value;
    }

    public void setTime_value(String time_value) {
        this.time_value = time_value;
    }

    public String getHis_date() {
        return his_date;
    }

    public void setHis_date(String his_date) {
        this.his_date = his_date;
    }

    public String getHis_time() {
        return his_time;
    }

    public void setHis_time(String his_time) {
        this.his_time = his_time;
    }

    public String getCabinet_number() {
        return cabinet_number;
    }

    public void setCabinet_number(String cabinet_number) {
        this.cabinet_number = cabinet_number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
