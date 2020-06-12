package com.byd.emg.service;

import com.byd.emg.pojo.CompressedAir;

import java.util.List;

public interface ICompressedAir {

    public CompressedAir selectByTag_name(String tag_name, String device_name);

    public List<CompressedAir> selectByTapWaterBySuper_Id(Long super_id);


}
