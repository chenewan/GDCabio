package com.byd.emg.mapper;

import com.byd.emg.pojo.OilYield;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OilYieldMapper {

    public int insertOilYield(@Param("oilYield") OilYield oilYield,@Param("tableName") String tableName);

    public List<Integer> selectByTimes(@Param("oilYield") OilYield oilYield,@Param("tableName") String tableName);

    public int batchUpdateByIds(@Param("idList") List<Integer> idList,@Param("oilYield") OilYield oilYield,@Param("tableName") String tableName);

    public List<OilYield> selectByDate(@Param("date") String date,@Param("type") String type,@Param("topMethod") String topMethod,@Param("tableName") String tableName);

    public int updateOilYield(@Param("oilYield") OilYield oilYield,@Param("tableName") String tableName);

    public int deleteOilById(@Param("id") Long id,@Param("tableName") String tableName);
}
