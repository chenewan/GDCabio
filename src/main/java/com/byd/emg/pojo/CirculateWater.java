package com.byd.emg.pojo;

public class CirculateWater {

    private Long id;

    private Long super_id;

    private Long root_node_id;

    private String tag_name;

    private String device_name;

    private String water_consumption;

    private String cost;

    public CirculateWater() {}

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

    public Long getRoot_node_id() {
        return root_node_id;
    }

    public void setRoot_node_id(Long root_node_id) {
        this.root_node_id = root_node_id;
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

    public String getWater_consumption() {
        return water_consumption;
    }

    public void setWater_consumption(String water_consumption) {
        this.water_consumption = water_consumption;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
