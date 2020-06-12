package com.byd.emg.service;

import com.byd.emg.pojo.Instrument;

import java.util.List;

public interface InstrumentService {

    public Instrument selectByInstrument(Instrument instrument);

    public List<Instrument> getTagnameOrDescribe(String key);

    public List<Instrument> instrumentAll(String key,String way);

    public int updateInstrumentById(Instrument instrument);

    public int insertInstrument(Instrument instrument);

    public int deleteById(int id);


}
