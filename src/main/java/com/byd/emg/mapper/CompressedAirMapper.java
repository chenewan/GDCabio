package com.byd.emg.mapper;

import com.byd.emg.pojo.CompressedAir;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompressedAirMapper {

    public CompressedAir selectByTag_name(@Param("query_criteria") String query_criteria);

    public List<CompressedAir> selectByTapWaterBySuper_Id(@Param("super_id") Long super_id);
}
