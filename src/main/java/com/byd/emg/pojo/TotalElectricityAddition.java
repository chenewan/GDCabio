package com.byd.emg.pojo;

public class TotalElectricityAddition {
    private Long id;
    private String Name;
    private String Unit_Price;
    private String proportion;
    private String Start_time;
    private String end_time;
    private String type;
    private int number;
    public TotalElectricityAddition(){}


    public TotalElectricityAddition(String name, String unit_Price, String proportion, String start_time, int number) {
        Name = name;
        Unit_Price = unit_Price;
        this.proportion = proportion;
        Start_time = start_time;
        this.end_time = end_time;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUnit_Price() {
        return Unit_Price;
    }

    public void setUnit_Price(String unit_Price) {
        Unit_Price = unit_Price;
    }

    public String getStart_time() {
        return Start_time;
    }

    public void setStart_time(String start_time) {
        Start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
