package com.byd.emg.mapper;

import com.byd.emg.pojo.TimeTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimeTableMapper {

    public List<TimeTable> selectCurrData(@Param("para") String para);

    public void deleteByList(@Param("tables") List<String> tables);

    public void batchInsert(@Param("tables") List<String> tables, @Param("currDate") String currDate);

    public void deleteByTableName(@Param("tableName") String tableName);

    public void insert(@Param("tableName") String tableName, @Param("currDate") String currDate);
}
