package com.byd.emg.mapper;

import com.byd.emg.pojo.UnnaturalWeek;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnnaturalWeekMapper {

    public void batchInsert(@Param("unnaturalWeekList") List<UnnaturalWeek> unnaturalWeekList);

    public List<UnnaturalWeek> selectByTime(@Param("start_time_1") String start_time_1,@Param("end_time_1")  String end_time_1);

    public List<UnnaturalWeek> selectByList(@Param("unnaturalWeekList") List<UnnaturalWeek> unnaturalWeekList);
}
