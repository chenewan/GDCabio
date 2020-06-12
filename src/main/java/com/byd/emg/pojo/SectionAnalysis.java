package com.byd.emg.pojo;

public class SectionAnalysis {
    //工段分析

    private Long id ;

    private String Section; //工段

    private String Active_electric_energy;//有功电能

    private String Cumulative_flow_of_tap_water;//自来水总流量

    private String Cumulative_flow_of_circulating_water;//循环水总流量

    private String Cumulative_Cooling_Value_of_Circulating_Water;//循环水冷值

    private String Cumulative_flow_of_frozen_water;//冷冻水流量

    private String Cumulative_cold_value_of_frozen_water;//冷冻水冷值

    private String Cumulative_steam_flow;//蒸汽流量

    private String Cumulative_air_flow;//空气流量

    private String  Date_time;

    private int Serialn_number;



    @Override
    public String toString() {

        return Date_time;
    }

    public SectionAnalysis(){

    }

    public SectionAnalysis(String section, String active_electric_energy, String cumulative_flow_of_tap_water, String cumulative_flow_of_circulating_water, String cumulative_Cooling_Value_of_Circulating_Water, String cumulative_flow_of_frozen_water, String cumulative_cold_value_of_frozen_water, String cumulative_steam_flow, String cumulative_air_flow, String date_time, int serialn_number) {
        Section = section;
        Active_electric_energy = active_electric_energy;
        Cumulative_flow_of_tap_water = cumulative_flow_of_tap_water;
        Cumulative_flow_of_circulating_water = cumulative_flow_of_circulating_water;
        Cumulative_Cooling_Value_of_Circulating_Water = cumulative_Cooling_Value_of_Circulating_Water;
        Cumulative_flow_of_frozen_water = cumulative_flow_of_frozen_water;
        Cumulative_cold_value_of_frozen_water = cumulative_cold_value_of_frozen_water;
        Cumulative_steam_flow = cumulative_steam_flow;
        Cumulative_air_flow = cumulative_air_flow;
        Date_time = date_time;
        Serialn_number = serialn_number;
    }

    public String getDate_time() {
        return Date_time;
    }

    public void setDate_time(String date_time) {
        Date_time = date_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getActive_electric_energy() {
        return Active_electric_energy;
    }

    public void setActive_electric_energy(String active_electric_energy) {
        Active_electric_energy = active_electric_energy;
    }

    public String getCumulative_flow_of_tap_water() {
        return Cumulative_flow_of_tap_water;
    }

    public void setCumulative_flow_of_tap_water(String cumulative_flow_of_tap_water) {
        Cumulative_flow_of_tap_water = cumulative_flow_of_tap_water;
    }

    public String getCumulative_flow_of_circulating_water() {
        return Cumulative_flow_of_circulating_water;
    }

    public void setCumulative_flow_of_circulating_water(String cumulative_flow_of_circulating_water) {
        Cumulative_flow_of_circulating_water = cumulative_flow_of_circulating_water;
    }

    public String getCumulative_Cooling_Value_of_Circulating_Water() {
        return Cumulative_Cooling_Value_of_Circulating_Water;
    }

    public void setCumulative_Cooling_Value_of_Circulating_Water(String cumulative_Cooling_Value_of_Circulating_Water) {
        Cumulative_Cooling_Value_of_Circulating_Water = cumulative_Cooling_Value_of_Circulating_Water;
    }

    public String getCumulative_flow_of_frozen_water() {
        return Cumulative_flow_of_frozen_water;
    }

    public void setCumulative_flow_of_frozen_water(String cumulative_flow_of_frozen_water) {
        Cumulative_flow_of_frozen_water = cumulative_flow_of_frozen_water;
    }

    public String getCumulative_cold_value_of_frozen_water() {
        return Cumulative_cold_value_of_frozen_water;
    }

    public void setCumulative_cold_value_of_frozen_water(String cumulative_cold_value_of_frozen_water) {
        Cumulative_cold_value_of_frozen_water = cumulative_cold_value_of_frozen_water;
    }

    public String getCumulative_steam_flow() {
        return Cumulative_steam_flow;
    }

    public void setCumulative_steam_flow(String cumulative_steam_flow) {
        Cumulative_steam_flow = cumulative_steam_flow;
    }

    public String getCumulative_air_flow() {
        return Cumulative_air_flow;
    }

    public void setCumulative_air_flow(String cumulative_air_flow) {
        Cumulative_air_flow = cumulative_air_flow;
    }

    public int getSerialn_number() {
        return Serialn_number;
    }

    public void setSerialn_number(int serialn_number) {
        Serialn_number = serialn_number;
    }
}
