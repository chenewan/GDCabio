package com.byd.emg.pojo;

public class SnakeyData {

    private int id;

    private String actuaValue;     //实际值

    private String magnification;  //倍率

    private String displayValue;   //显示值

    public SnakeyData() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActuaValue() {
        return actuaValue;
    }

    public void setActuaValue(String actuaValue) {
        this.actuaValue = actuaValue;
    }

    public String getMagnification() {
        return magnification;
    }

    public void setMagnification(String magnification) {
        this.magnification = magnification;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
}
