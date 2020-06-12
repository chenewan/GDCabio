package com.byd.emg.service;

import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReportFormService {

    public int insert_Contractual_capacity (List<Contractual_capacity> contractual_capacity,String time);

    public int tax(Tax tax);

    public int updateTax(Tax tax);

    public int insertAddition(List<TotalElectricityAddition> additionList,String type);

    public ServerResponse power_price(String Applicable_time,String method);

    public ServerResponse power_price_pdf(String Applicable_time,String method);

    public ServerResponse energy_consumption(String Start_t,String End_t,String method);

    public ServerResponse energy_consumption_pdf(String start_t, String end_t,String method);

    public ServerResponse finally_report(String Start_t,String End_t,String type,String method);

    public ServerResponse finally_report_pdf(String start_t, String end_t, String type,String method);

    public ServerResponse powerCost(String start_t, String end_t,String mthod);

    public ServerResponse powerCost_pdf(String start_t, String end_t);

    public ServerResponse totalCost(String start_t, String end_t);

    public ServerResponse unitCostSteam(String start_t, String end_t);

    public ServerResponse totalCost_pdf(String start_t, String end_t);

    public ServerResponse unitCostSteam_pdf(String start_t, String end_t);

    public int delTaxById(Long id);

    public List<Tax> taxRecords(String start_time);

    public int Total_Electricity_Update(TotalElectricityAddition totalElectricityAddition);

    public List<TotalElectricityAddition> Total_Electricity_Select(String searchTime, String type);

    public int Total_Electricity_Delete(Long id);

    public int electricityFeesUpdate(Contractual_capacity contractualCapacity);

    public List<Contractual_capacity> electricityFeesSelect();

    public int electricityFeesDelete(Long id);

    public void getAllData(String startDate,String endDate,int sheetNum,int column,int tagColum);

    public String selectLastTime(String tableName,String field,String type);

    public int addElectricityFees(Contractual_capacity contractualCapacity);
}
