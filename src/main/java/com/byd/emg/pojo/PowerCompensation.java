package com.byd.emg.pojo;

import java.math.BigDecimal;

//电量的补偿值类
public class PowerCompensation {

    private Integer id;

    private String pid;

    //变量名
    private String tagname;

    private String describe;

    //消耗值
    private String costValue;

    //补偿值
    private String compensateValue;

    //计算值
    private String calculateValue;

    private String his_date;

    private String his_time;

    public PowerCompensation() {}

    public PowerCompensation(String pid,String tagname,String costValue,String compensateValue,String calculateValue){
        this.pid=pid;
        this.tagname=tagname;
        this.costValue=costValue;
        this.compensateValue=compensateValue;
        this.calculateValue=calculateValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCostValue() {
        return costValue;
    }

    public void setCostValue(String costValue) {
        this.costValue = costValue;
    }

    public String getCompensateValue() {
        return compensateValue;
    }

    public void setCompensateValue(String compensateValue) {
        this.compensateValue = compensateValue;
    }

    public String getCalculateValue() {
        return calculateValue;
    }

    public void setCalculateValue(String calculateValue) {
        this.calculateValue = calculateValue;
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
}
