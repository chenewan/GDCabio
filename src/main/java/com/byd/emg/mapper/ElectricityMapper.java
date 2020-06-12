package com.byd.emg.mapper;

import com.byd.emg.pojo.Electricity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElectricityMapper {

    public Electricity selectByTag_name(@Param("query_criteria") String query_criteria);

    public List<Electricity> selectElectricityBySuper_Id(@Param("super_id") Long super_id);
}
