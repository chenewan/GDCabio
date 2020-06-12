package com.byd.emg.service.impl;

import com.byd.emg.mapper.InstrumentMapper;
import com.byd.emg.pojo.Instrument;
import com.byd.emg.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iInstrument")
public class InstrumentImpl implements InstrumentService {

    @Autowired
    private InstrumentMapper instrumentMapper;

    @Override
    public List<Instrument> instrumentAll(String key,String way) {

        return instrumentMapper.instrumentAll(key,way);
    }

    @Override
    public int updateInstrumentById(Instrument instrument) {
        return instrumentMapper.updateInstrumentById(instrument);
    }

    @Override
    public Instrument selectByInstrument(Instrument instrument) {
        return instrumentMapper.selectByInstrument(instrument);
    }

    @Override
    public List<Instrument> getTagnameOrDescribe(String key) {
        //List<Instrument>
        return instrumentMapper.getTagnameOrDescribe(key);
    }

    @Override
    public int insertInstrument(Instrument instrument) {
        return instrumentMapper.insertInstrument(instrument);
    }

    @Override
    public int deleteById(int id) {
        return instrumentMapper.deleteById(id);
    }
}
