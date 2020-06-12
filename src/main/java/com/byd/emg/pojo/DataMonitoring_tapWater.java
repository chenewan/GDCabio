package com.byd.emg.pojo;

public class DataMonitoring_tapWater {
    //自来水实体类

    private long monitoring_id;

    private String cabinet_name;

    private String q;
    private String v;
    private String eq;
    private String req;

    private String cabinet_number;
    private String time;

    public long getMonitoring_id() {
        return monitoring_id;
    }

    public void setMonitoring_id(long monitoring_id) {
        this.monitoring_id = monitoring_id;
    }

    public String getCabinet_name() {
        return cabinet_name;
    }

    public void setCabinet_name(String cabinet_name) {
        this.cabinet_name = cabinet_name;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getEq() {
        return eq;
    }

    public void setEq(String eq) {
        this.eq = eq;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
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
