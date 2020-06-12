package com.byd.emg.mapper;

import com.byd.emg.pojo.PowerCompensation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PowerCompensationMapper {

    public void batchIsert(@Param("powerCompensationList") List<PowerCompensation> powerCompensationList,@Param("his_date")  String his_date,@Param("his_time")  String his_time);


}
