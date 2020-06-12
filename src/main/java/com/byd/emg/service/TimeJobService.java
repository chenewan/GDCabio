package com.byd.emg.service;

import cn.hutool.core.date.DateTime;
import com.byd.emg.pojo.PowerCompensation;
import com.byd.emg.pojo.Steam;
import com.byd.emg.pojo.TimeJob;

import java.util.List;
import java.util.Map;

public interface TimeJobService {

    public void equipmentJob();

    public int addSteam(Steam steam);

    public Map<String, List<TimeJob>> selectEletric(String startDate,String endDate);

    public List<String> insertHistoryData(String dataSourceTableName,String historyTableName,String condition,String date,String time);

    public void TimeJobByMonthHistoryData(String dataSourceTableName,String realValueTableName,String condition,String historyTableName, String date, String time);

    public void insert5minConsumeData(String dataSourceTableName, String historyTableNameConsume,String lastTimeTableName, String condition);

    public void updateOpenTimes();

    public String selectTopXHLDWater();

    public Double selectRealTemp(String realValue_table, String tagName);

    public void insertXHLDWater(String stateNow, String time);

    public void insertConsumeData(String datasourceTable, String hisTable, String queryDate, DateTime datime);
}
