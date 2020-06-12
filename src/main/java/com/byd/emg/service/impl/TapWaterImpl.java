package com.byd.emg.service.impl;

import com.byd.emg.mapper.TapWaterMapper;
import com.byd.emg.pojo.TapWater;
import com.byd.emg.service.ITapWater;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iTapWater")
public class TapWaterImpl implements ITapWater {

    @Autowired
    private TapWaterMapper tapWaterMapper;

    @Override
    public List<TapWater> selectByTapWaterBySuper_Id(Long super_id) {
        return tapWaterMapper.selectByTapWaterBySuper_Id(super_id);
    }

    @Override
    public TapWater selectByTag_name(String tag_name,String device_name) {
        String query_criteria="";
        if(!StringUtils.isEmpty(tag_name)){
            query_criteria="tag_name="+"\'"+tag_name+"\'";
            if(!StringUtils.isEmpty(device_name)){
                query_criteria+=" and device_name="+"\'"+device_name+"\'";
            }
        }else{
            if(!StringUtils.isEmpty(device_name)){
                query_criteria="device_name="+"\'"+device_name+"\'";
            }else{
                return null;
            }
        }
        return tapWaterMapper.selectByTag_name(query_criteria);
    }

    @Override
    public List<TapWater> selectByTapWater(TapWater tapWater) {
        return tapWaterMapper.selectByTapWater(tapWater);
    }

    @Override
    public int insertTapWater(TapWater tapWater) {
        return tapWaterMapper.insertTapWater(tapWater);
    }

    @Override
    public int updateTapWaterById(TapWater tapWater) {
        return tapWaterMapper.updateTapWaterById(tapWater);
    }
}
