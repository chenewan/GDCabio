package com.byd.emg.mapper;

import com.byd.emg.pojo.Instrument;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InstrumentMapper {

    public List<Instrument> instrumentAll(@Param("key") String key,@Param("way") String way);

    public int updateInstrumentById(Instrument instrument);

    public List<Instrument> getTagnameOrDescribe(@Param("key") String key);

    public int insertInstrument(Instrument instrument);

    public int deleteById(@Param("id") int id);

    public List<Instrument> selectByTagnameList(@Param("tagnameList") List<String> tagnameList,@Param("startDate") String startDate,@Param("endDate")  String endDate);

    public Instrument selectByInstrument(Instrument instrument);
}
