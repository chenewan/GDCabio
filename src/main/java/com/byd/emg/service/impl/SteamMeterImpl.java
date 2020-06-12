package com.byd.emg.service.impl;

import com.byd.emg.mapper.SteamMeterMapper;
import com.byd.emg.pojo.SteamMeter;
import com.byd.emg.service.ISteamMeter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iSteamMeter")
public class SteamMeterImpl implements ISteamMeter {

    @Autowired
    private SteamMeterMapper steamMeterMapper;

    @Override
    public SteamMeter selectByTag_name(String tag_name, String device_name) {
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
        return steamMeterMapper.selectByTag_name(query_criteria);
    }

    @Override
    public List<SteamMeter> selectSteamMeterBySuper_Id(Long super_id) {
        return steamMeterMapper.selectSteamMeterBySuper_Id(super_id);
    }
}
