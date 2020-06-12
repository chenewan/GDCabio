package com.byd.emg.service;

import com.byd.emg.pojo.TapWater;

import java.util.List;

public interface ITapWater {

    public List<TapWater> selectByTapWaterBySuper_Id(Long super_id);

    public TapWater selectByTag_name(String tag_name,String device_name);

    public List<TapWater> selectByTapWater(TapWater tapWater);

    public int insertTapWater(TapWater tapWater);

    public int updateTapWaterById(TapWater tapWater);


}
