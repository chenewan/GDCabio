package com.byd.emg.service.impl;

import com.byd.emg.mapper.PowerCompensationMapper;
import com.byd.emg.pojo.PowerCompensation;
import com.byd.emg.service.IPowerCompensation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iPowerCompensation")
public class PowerCompensationImpl implements IPowerCompensation {

    @Autowired
    private PowerCompensationMapper powerCompensationMapper;

    @Override
    public void batchIsert(List<PowerCompensation> powerCompensationList,String his_date,String his_time) {
        powerCompensationMapper.batchIsert(powerCompensationList,his_date,his_time);
    }
}
