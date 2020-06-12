package com.byd.emg.mapper;

import com.byd.emg.pojo.CirculateWater;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CirculateWaterMapper {

    public CirculateWater selectByTag_name(@Param("query_criteria") String query_criteria);

    public List<CirculateWater> selectByCirculateWater(CirculateWater circulateWater);

    public List<CirculateWater> selectByCirculateWaterBySuper_Id(@Param("id") Long id);

    public int insertCirculateWater(CirculateWater circulateWater);

    public int updateCirculateWaterById(CirculateWater circulateWater);



}
