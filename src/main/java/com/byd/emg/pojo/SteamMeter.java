package com.byd.emg.pojo;

public class SteamMeter {

    private Long id;

    private Long super_id;

    private String tag_name;

    private String device_name;

    private String steam_consumption;

    private String cost;

    public SteamMeter() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSuper_id() {
        return super_id;
    }

    public void setSuper_id(Long super_id) {
        this.super_id = super_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getSteam_consumption() {
        return steam_consumption;
    }

    public void setSteam_consumption(String steam_consumption) {
        this.steam_consumption = steam_consumption;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

}
