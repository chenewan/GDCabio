package com.byd.emg.service;

import com.byd.emg.pojo.UnnaturalWeek;

import java.util.List;

public interface UnnaturalWeekService {

    public void batchInsert(List<UnnaturalWeek> unnaturalWeekList);

    public List<UnnaturalWeek> selectByTime(String start_time_1, String end_time_1);
}
