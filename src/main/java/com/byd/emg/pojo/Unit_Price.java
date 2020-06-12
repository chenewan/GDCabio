package com.byd.emg.pojo;

public class Unit_Price {
    private Long id;

    private String power_price; //电单价

    private String peak_electricity_price; //峰电价

    private String peacetime_electricity_price;//平电价

    private String troughtime_electricity_price;//谷电价

    private String price; //水和蒸汽单价

    private String times; //时间段

    private String start_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unit_Price() {
    }
    public Unit_Price(String power_price) {
        this.power_price = power_price;
    }
    public Unit_Price(Unit_Price price, String start_time) {
        this.power_price = price.getPower_price();
        this.price = price.getPrice();
        this.peak_electricity_price=price.getPeak_electricity_price();
        this.peacetime_electricity_price=price.getPeacetime_electricity_price();
        this.troughtime_electricity_price=price.getTroughtime_electricity_price();
        this.start_time = start_time;
    }

    public String getPeak_electricity_price() {
        return peak_electricity_price;
    }

    public void setPeak_electricity_price(String peak_electricity_price) {
        this.peak_electricity_price = peak_electricity_price;
    }

    public String getPeacetime_electricity_price() {
        return peacetime_electricity_price;
    }

    public void setPeacetime_electricity_price(String peacetime_electricity_price) {
        this.peacetime_electricity_price = peacetime_electricity_price;
    }

    public String getTroughtime_electricity_price() {
        return troughtime_electricity_price;
    }

    public void setTroughtime_electricity_price(String troughtime_electricity_price) {
        this.troughtime_electricity_price = troughtime_electricity_price;
    }

    public String getPower_price() {
        return power_price;
    }

    public void setPower_price(String power_price) {
        this.power_price = power_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
