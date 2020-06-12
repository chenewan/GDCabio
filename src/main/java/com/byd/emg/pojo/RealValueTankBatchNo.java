package com.byd.emg.pojo;

import java.util.Objects;

public class RealValueTankBatchNo {

    private Long id;

    private String tankNumber;  //灌号

    private String batchNumber; //批次号

    private String steamTotalFlow;   //蒸汽累计流量

    private String airTotalFlow;       //空气累计流量

    private String steamShunShiFlow;    //蒸汽瞬时流量

    private String airShunShiFlow;      //空气瞬时流量

    private String productType;         //产品类型

    private String state;               //状态

    private String time;                //时间

//    private Long aid;                //子ID

    public RealValueTankBatchNo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTankNumber() {
        return tankNumber;
    }

    public void setTankNumber(String tankNumber) {
        this.tankNumber = tankNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getSteamTotalFlow() {
        return steamTotalFlow;
    }

    public void setSteamTotalFlow(String steamTotalFlow) {
        this.steamTotalFlow = steamTotalFlow;
    }

    public String getAirTotalFlow() {
        return airTotalFlow;
    }

    public void setAirTotalFlow(String airTotalFlow) {
        this.airTotalFlow = airTotalFlow;
    }

    public String getSteamShunShiFlow() {
        return steamShunShiFlow;
    }

    public void setSteamShunShiFlow(String steamShunShiFlow) {
        this.steamShunShiFlow = steamShunShiFlow;
    }

    public String getAirShunShiFlow() {
        return airShunShiFlow;
    }

    public void setAirShunShiFlow(String airShunShiFlow) {
        this.airShunShiFlow = airShunShiFlow;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

//    public Long getAid() {
//        return aid;
//    }
//
//    public void setAid(Long aid) {
//        this.aid = aid;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealValueTankBatchNo that = (RealValueTankBatchNo) o;
        return Objects.equals(tankNumber, that.tankNumber) &&
                Objects.equals(batchNumber, that.batchNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tankNumber, batchNumber);
    }
}
