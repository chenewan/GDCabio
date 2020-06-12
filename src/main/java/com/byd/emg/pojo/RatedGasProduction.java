package com.byd.emg.pojo;

public class RatedGasProduction {
    private Integer id;

    private String tagName;

    private String ratedValue;

    public RatedGasProduction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getRatedValue() {
        return ratedValue;
    }

    public void setRatedValue(String ratedValue) {
        this.ratedValue = ratedValue;
    }
}
