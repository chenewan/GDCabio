package com.byd.emg.service;

import com.byd.emg.pojo.SteamMeter;

import java.util.List;

public interface ISteamMeter {

    public SteamMeter selectByTag_name(String tag_name, String device_name);

    public List<SteamMeter> selectSteamMeterBySuper_Id(Long super_id);
}
