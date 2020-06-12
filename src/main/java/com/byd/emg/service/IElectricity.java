package com.byd.emg.service;

import com.byd.emg.pojo.Electricity;

import java.util.List;

public interface IElectricity {

    public Electricity selectByTag_name(String tag_name, String device_name);

    public List<Electricity> selectElectricityBySuper_Id(Long super_id);
}
