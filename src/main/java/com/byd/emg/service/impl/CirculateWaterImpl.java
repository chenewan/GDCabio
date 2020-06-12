package com.byd.emg.service.impl;

import com.byd.emg.mapper.AclMapper;
import com.byd.emg.mapper.CirculateWaterMapper;
import com.byd.emg.pojo.CirculateWater;
import com.byd.emg.service.ICirculateWater;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("iCirculateWater")

@Transactional
public class CirculateWaterImpl implements ICirculateWater {

    @Autowired
    private CirculateWaterMapper circulateWaterMapper;
    @Autowired
    private AclMapper aclMapper;

    @Override
    public CirculateWater selectByTag_name(String tag_name,String device_name) {
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
        return circulateWaterMapper.selectByTag_name(query_criteria);
    }

    @Override
    public List<CirculateWater> selectByCirculateWaterBySuper_Id(Long id) {
        return circulateWaterMapper.selectByCirculateWaterBySuper_Id(id);
    }

    @Override
    public List<CirculateWater> selectByCirculateWater(CirculateWater circulateWater) {
        return circulateWaterMapper.selectByCirculateWater(circulateWater);
    }

    @Override
    public int insertCirculateWater(CirculateWater circulateWater) {
        return circulateWaterMapper.insertCirculateWater(circulateWater);
    }

    @Override
    public int updateCirculateWaterById(CirculateWater circulateWater) {
        return circulateWaterMapper.updateCirculateWaterById(circulateWater);
    }
}
