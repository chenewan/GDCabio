package com.byd.emg.pojo;

public class Contractual_capacity {

   private Integer id;

   private int num;

    private String Name;

    private String Contractual_capacity;

    private String Price;

    private String Applicable_time;

    private String monthTime;

    private String realTime;

    private String Rated_Capacity;

    public Contractual_capacity() {}

    public Contractual_capacity(String name, String contractual_capacity, String price, String applicable_time) {
        Name = name;
        Contractual_capacity = contractual_capacity;
        Price = price;
        Applicable_time = applicable_time;
    }

    public String getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(String monthTime) {
        this.monthTime = monthTime;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public String getRated_Capacity() {
        return Rated_Capacity;
    }

    public void setRated_Capacity(String rated_Capacity) {
        Rated_Capacity = rated_Capacity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContractual_capacity() {
        return Contractual_capacity;
    }

    public void setContractual_capacity(String contractual_capacity) {
        Contractual_capacity = contractual_capacity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getApplicable_time() {
        return Applicable_time;
    }

    public void setApplicable_time(String applicable_time) {
        Applicable_time = applicable_time;
    }
}
