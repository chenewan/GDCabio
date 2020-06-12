package com.byd.emg.resultData;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.pojo.EquipmentStates;

//空压机设备参数返回值
public class EquipmentParameterData {
    //运行参数
    //运行电流
    private String runningCurrent;
    //瞬时流量
    private String instantaneousFlow;
    //有功功率
    private String activePower;
    //功率因素
    private String powerFactor;
    //实时效率
    private String realTimeEfficiency;
    //系统压力
    private String systemPressure;
    //系统温度
    private String systemTemperature;

    //运行统计

    //产气效率
    private String gasProductionEfficiency;
    //总运行时间
    private String totalRunningTime;
    //加载时间
    private String loadTime;
    //空载时间
    private String deadTime;
    //开启次数
    private String openTimes;
    //有功电能
    private String activeEnergy;

    public EquipmentParameterData() {
    }

    public void setEquipmentStates(EquipmentStates equipmentStates) {
        this.totalRunningTime = DateTimeUtil.formatMinute(equipmentStates.getTotalRunningTime());
        this.loadTime = DateTimeUtil.formatMinute(equipmentStates.getLoadTime());
        this.deadTime = DateTimeUtil.formatMinute(equipmentStates.getDeadTime());
        this.openTimes = DateTimeUtil.formatMinute(equipmentStates.getOpenTimes());
    }

    public EquipmentParameterData(String runningCurrent, String instantaneousFlow, String activePower, String powerFactor, String realTimeEfficiency, String systemPressure, String systemTemperature, String gasProductionEfficiency, String totalRunningTime, String loadTime, String deadTime, String openTimes, String activeEnergy) {
        this.runningCurrent = runningCurrent;
        this.instantaneousFlow = instantaneousFlow;
        this.activePower = activePower;
        this.powerFactor = powerFactor;
        this.realTimeEfficiency = realTimeEfficiency;
        this.systemPressure = systemPressure;
        this.systemTemperature = systemTemperature;
        this.gasProductionEfficiency = gasProductionEfficiency;
        this.totalRunningTime = totalRunningTime;
        this.loadTime = loadTime;
        this.deadTime = deadTime;
        this.openTimes = openTimes;
        this.activeEnergy = activeEnergy;
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

    public String getRealTimeEfficiency() {
        return realTimeEfficiency;
    }

    public void setRealTimeEfficiency(String realTimeEfficiency) {
        this.realTimeEfficiency = realTimeEfficiency;
    }

    public String getSystemPressure() {
        return systemPressure;
    }

    public void setSystemPressure(String systemPressure) {
        this.systemPressure = systemPressure;
    }

    public String getSystemTemperature() {
        return systemTemperature;
    }

    public void setSystemTemperature(String systemTemperature) {
        this.systemTemperature = systemTemperature;
    }

    public String getGasProductionEfficiency() {
        return gasProductionEfficiency;
    }

    public void setGasProductionEfficiency(String gasProductionEfficiency) {
        this.gasProductionEfficiency = gasProductionEfficiency;
    }

    public String getTotalRunningTime() {
        return totalRunningTime;
    }

    public void setTotalRunningTime(String totalRunningTime) {
        this.totalRunningTime = totalRunningTime;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public String getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(String deadTime) {
        this.deadTime = deadTime;
    }

    public String getOpenTimes() {
        return openTimes;
    }

    public void setOpenTimes(String openTimes) {
        this.openTimes = openTimes;
    }

    public String getActiveEnergy() {
        return activeEnergy;
    }

    public void setActiveEnergy(String activeEnergy) {
        this.activeEnergy = activeEnergy;
    }
}
