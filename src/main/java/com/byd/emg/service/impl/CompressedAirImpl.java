package com.byd.emg.service.impl;

import com.byd.emg.mapper.CompressedAirMapper;
import com.byd.emg.pojo.CompressedAir;
import com.byd.emg.service.ICompressedAir;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("iCompressedAir")

@Transactional
public class CompressedAirImpl implements ICompressedAir {

    @Autowired
    private CompressedAirMapper compressedAirMapper;

    @Override
    public CompressedAir selectByTag_name(String tag_name, String device_name) {
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
        return compressedAirMapper.selectByTag_name(query_criteria);
    }

    @Override
    public List<CompressedAir> selectByTapWaterBySuper_Id(Long super_id) {
        return compressedAirMapper.selectByTapWaterBySuper_Id(super_id);
    }
}
