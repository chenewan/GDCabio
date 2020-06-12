package com.byd.emg.resultData;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.pojo.EquipmentStates;

//空压机设备参数返回值
public class IceboxParameterData {
    //运行参数

    //冷热水进水温度
    private String inletTemperature;
    //冷热水回水温度
    private String returnTemperature;
    //运行电流
    private String runningCurrent;
    //瞬时流量
    private String instantaneousFlow;
    //有功功率
    private String activePower;
    //功率因素
    private String powerFactor;

    //运行统计

    //累计流量
    private String cumulativeFlow;
    //累计冷量
    private String cumulativeCooling;
    //运行时间
    private String operationHours;
    //效率
    private String effectiveness;
    //有功电能
    private String activeEnergy;
    //电单耗
    private String electricConsumption;
    //开机次数
    private String bootTimes;

    public IceboxParameterData() {
    }
    public void SetIceboxParameterData(EquipmentStates equipmentStates) {
        this.operationHours = DateTimeUtil.formatMinute(equipmentStates.getTotalRunningTime());
        this.bootTimes = DateTimeUtil.formatMinute(equipmentStates.getOpenTimes());
    }
    public IceboxParameterData(String inletTemperature, String returnTemperature, String runningCurrent, String instantaneousFlow, String activePower, String powerFactor, String cumulativeFlow, String cumulativeCooling, String operationHours, String effectiveness, String activeEnergy, String electricConsumption, String bootTimes) {
        this.inletTemperature = inletTemperature;
        this.returnTemperature = returnTemperature;
        this.runningCurrent = runningCurrent;
        this.instantaneousFlow = instantaneousFlow;
        this.activePower = activePower;
        this.powerFactor = powerFactor;
        this.cumulativeFlow = cumulativeFlow;
        this.cumulativeCooling = cumulativeCooling;
        this.operationHours = operationHours;
        this.effectiveness = effectiveness;
        this.activeEnergy = activeEnergy;
        this.electricConsumption = electricConsumption;
        this.bootTimes = bootTimes;
    }

    public String getInletTemperature() {
        return inletTemperature;
    }

    public void setInletTemperature(String inletTemperature) {
        this.inletTemperature = inletTemperature;
    }

    public String getReturnTemperature() {
        return returnTemperature;
    }

    public void setReturnTemperature(String returnTemperature) {
        this.returnTemperature = returnTemperature;
    }

    public String getRunningCurrent() {
        return runningCurrent;
    }

    public void setRunningCurrent(String runningCurrent) {
        this.runningCurrent = runningCurrent;
    }

    public String getInstantaneousFlow() {
        return instantaneousFlow;
    }

    public void setInstantaneousFlow(String instantaneousFlow) {
        this.instantaneousFlow = instantaneousFlow;
    }

    public String getActivePower() {
        return activePower;
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower;
    }

    public String getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(String powerFactor) {
        this.powerFactor = powerFactor;
    }

    public String getCumulativeFlow() {
        return cumulativeFlow;
    }

    public void setCumulativeFlow(String cumulativeFlow) {
        this.cumulativeFlow = cumulativeFlow;
    }

    public String getCumulativeCooling() {
        return cumulativeCooling;
    }

    public void setCumulativeCooling(String cumulativeCooling) {
        this.cumulativeCooling = cumulativeCooling;
    }

    public String getOperationHours() {
        return operationHours;
    }

    public void setOperationHours(String operationHours) {
        this.operationHours = operationHours;
    }

    public String getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(String effectiveness) {
        this.effectiveness = effectiveness;
    }

    public String getActiveEnergy() {
        return activeEnergy;
    }

    public void setActiveEnergy(String activeEnergy) {
        this.activeEnergy = activeEnergy;
    }

    public String getElectricConsumption() {
        return electricConsumption;
    }

    public void setElectricConsumption(String electricConsumption) {
        this.electricConsumption = electricConsumption;
    }

    public String getBootTimes() {
        return bootTimes;
    }

    public void setBootTimes(String bootTimes) {
        this.bootTimes = bootTimes;
    }
}
