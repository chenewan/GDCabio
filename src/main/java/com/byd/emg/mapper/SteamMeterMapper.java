package com.byd.emg.mapper;

import com.byd.emg.pojo.SteamMeter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SteamMeterMapper {

    public SteamMeter selectByTag_name(@Param("query_criteria") String query_criteria);

    public List<SteamMeter> selectSteamMeterBySuper_Id(@Param("super_id") Long super_id);
}
