package com.byd.emg.service.impl;

import com.byd.emg.mapper.ElectricityMapper;
import com.byd.emg.pojo.Electricity;
import com.byd.emg.service.IElectricity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iElectricity")
public class ElectricityImpl implements IElectricity {

    @Autowired
    private ElectricityMapper electricityMapper;

    @Override
    public Electricity selectByTag_name(String tag_name, String device_name) {
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
        return electricityMapper.selectByTag_name(query_criteria);
    }

    @Override
    public List<Electricity> selectElectricityBySuper_Id(Long super_id) {
        return electricityMapper.selectElectricityBySuper_Id(super_id);
    }
}
