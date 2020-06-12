package com.byd.emg.mapper;

import com.byd.emg.pojo.TapWater;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TapWaterMapper {

    public List<TapWater> selectByTapWater(TapWater tapWater);

    public List<TapWater> selectByTapWaterBySuper_Id(@Param("super_id") Long super_id);

    public TapWater selectByTag_name(@Param("query_criteria") String query_criteria);

    public int insertTapWater(TapWater tapWater);

    public int updateTapWaterById(TapWater tapWater);


}
