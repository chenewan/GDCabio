package com.byd.emg.service;

import com.byd.emg.pojo.CirculateWater;

import java.util.List;

public interface ICirculateWater {

    public CirculateWater selectByTag_name(String tag_name,String device_name);

    public List<CirculateWater> selectByCirculateWaterBySuper_Id(Long id);

    public List<CirculateWater> selectByCirculateWater(CirculateWater circulateWater);

    public int insertCirculateWater(CirculateWater circulateWater);

    public int updateCirculateWaterById(CirculateWater circulateWater);


}
