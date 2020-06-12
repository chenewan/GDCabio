package com.byd.emg.service.impl;

import com.byd.emg.mapper.UnnaturalWeekMapper;
import com.byd.emg.pojo.UnnaturalWeek;
import com.byd.emg.service.UnnaturalWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iUnnaturalWeek")
public class IUnnaturalWeek implements UnnaturalWeekService {

    @Autowired
    private UnnaturalWeekMapper unnaturalWeekMapper;

    @Override
    public void batchInsert(List<UnnaturalWeek> unnaturalWeekList) {
        List<UnnaturalWeek> unnaturalWeekDbList=unnaturalWeekMapper.selectByList(unnaturalWeekList);
        if(unnaturalWeekDbList.size()>0) unnaturalWeekList.removeAll(unnaturalWeekDbList);
        if(unnaturalWeekList.size()>0) unnaturalWeekMapper.batchInsert(unnaturalWeekList);
    }

    @Override
    public List<UnnaturalWeek> selectByTime(String start_time_1, String end_time_1) {
        return unnaturalWeekMapper.selectByTime(start_time_1,end_time_1);
    }
}
