package com.byd.emg.service.impl;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.Util.ExcelToPdf;
import com.byd.emg.Util.ExcelUtils;
import com.byd.emg.common.*;
import com.byd.emg.mapper.ElectricityPriceStatementMapper;
import com.byd.emg.mapper.GjtYieldMapper;
import com.byd.emg.mapper.SectionAnalysisMapper;
import com.byd.emg.mapper.Unit_PriceMapper;
import com.byd.emg.pojo.*;
import com.byd.emg.service.*;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Calendar.MONTH;

@Service("ReportFormService")
@Log4j
public class ReportFormServiceImp implements ReportFormService {

    private static final String PATH_PREFIX="D:\\Program\\web\\baoBiaoModel\\";

    private static final String pdf_PATH_PREFIX="D:\\Program\\web\\baoBiaoModel\\pdfModel\\";

    //private static final String CREATE_FILE_PATH="D:/dist/cabio";

    private static final String CREATE_FILE_PATH="D:/dist";

    //private static final String CREATE_pdf_PATH="D:/dist/cabio/export/pdf/";

    private static final String CREATE_pdf_PATH="D:/dist/export/pdf/";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private ElectricityPriceStatementMapper electricityPriceStatementMapper;

    @Autowired
    private SectionAnalysisMapper sectionAnalysisMapper;

    @Autowired
    private Unit_PriceService unit_PriceService;

    @Autowired
    private UnnaturalWeekService iUnnaturalWeek;

    @Autowired
    private GjtYieldMapper gjtYieldMapper;

    @Autowired
    private GjtYieldService iGjtYield;

    @Autowired
    private TimeJobService timeJobService;

    @Autowired
    private SectionAnalysisService SectionAnalysisService;

    @Autowired
    private Unit_PriceService unit_priceService;

    @Autowired
    private IPowerCompensation iPowerCompensation;

    @Autowired
    private HistoryTankBatchNoService historyTankBatchNoService;


    public int insert_Contractual_capacity(List<Contractual_capacity> contractual_capacity,String time) {
        int insertCount=0;
        int updateCount=0;
        List<Contractual_capacity> updateList=new ArrayList<Contractual_capacity>();
        //查询数据库的立约容量的集合
        List<Contractual_capacity> contractual_capacityDbList=electricityPriceStatementMapper.selectContractual_capacityByList(contractual_capacity);
        for(int index=0;index<contractual_capacity.size();index++){
            Contractual_capacity contractual=contractual_capacity.get(index);
            for(int kk=0;kk<contractual_capacityDbList.size();kk++){
                Contractual_capacity contractual_capacityDb=contractual_capacityDbList.get(kk);
                if(contractual.getApplicable_time().equals(contractual_capacityDb.getApplicable_time())){
                    contractual.setId(contractual_capacityDb.getId());
                    updateList.add(contractual);
                    contractual_capacityDbList.remove(kk);
                    kk--;
                    contractual_capacity.remove(index);
                    index--;
                    break;
                }
            }
        }
        if(contractual_capacity.size()>0) insertCount=electricityPriceStatementMapper.insert_Contractual_capacity(contractual_capacity,time);
        if(updateList.size()>0) updateCount=electricityPriceStatementMapper.updateContractualCapacity(updateList,time);
        return insertCount+updateCount;
    }

    @Override
    public int tax(Tax tax) {
        int b=0;
        int a=0;
        List<Tax> taxList=electricityPriceStatementMapper.selectByTime(tax.getStart_time());
        if(taxList.size()>0){
            for(Tax taxDb:taxList){
                taxDb.setTax(tax.getTax());
            }
            b=electricityPriceStatementMapper.batchUpdateTax(taxList);
        }else{
            //b =electricityPriceStatementMapper.update_tax(start_time);
            a = electricityPriceStatementMapper.insert_tax(tax.getTax(),tax.getStart_time());
        }
        return a+b;
    }

    @Override
    public int updateTax(Tax tax) {
        return electricityPriceStatementMapper.updateTax(tax);
    }

    public List<Tax> taxRecords(String start_time){
        return electricityPriceStatementMapper.taxRecords(start_time);
    }

    @Override
    public int Total_Electricity_Update(TotalElectricityAddition totalElectricityAddition) {
        return electricityPriceStatementMapper.Total_Electricity_Update(totalElectricityAddition);
    }

    @Override
    public List<TotalElectricityAddition> Total_Electricity_Select(String searchTime, String type) {
//        if(StringUtils.isEmpty(searchTime)){
//            searchTime = electricityPriceStatementMapper.selectLatestTime(type);
//        }
        return electricityPriceStatementMapper.Total_Electricity_Select(searchTime,type);
    }

    @Override
    public int Total_Electricity_Delete(Long id) {
        return electricityPriceStatementMapper.Total_Electricity_Delete(id);
    }

    @Override
    public int electricityFeesUpdate(Contractual_capacity contractualCapacity) {
        return electricityPriceStatementMapper.electricityFeesUpdate(contractualCapacity);
    }

    @Override
    public String selectLastTime(String tableName,String field,String type) {
        return electricityPriceStatementMapper.selectLastTime(tableName,field,type);
    }

    @Override
    public int addElectricityFees(Contractual_capacity contractualCapacity) {
        return electricityPriceStatementMapper.addElectricityFees(contractualCapacity);
    }

    @Override
    public List<Contractual_capacity> electricityFeesSelect() {
//        if(StringUtils.isEmpty(searchTime)){
//            searchTime = electricityPriceStatementMapper.selectElectricityFeesLatestTime();
//        }
        return electricityPriceStatementMapper.electricityFeesSelect();
    }

    @Override
    public int electricityFeesDelete(Long id) {
        return electricityPriceStatementMapper.electricityFeesDelete(id);
    }

    /**获取所有电表的补偿值
     * @param     startDate       开始时间
     * @param     endDate         结束时间
     * @param     sheetNum        设置内容的sheet页
     * @param      colNum          设置消耗值开始的列数
     * @param      tagColum        变量所在的列号
     */
    @Override
    public void getAllData(String startDate,String endDate,int sheetNum,int colNum,int tagColum) {
        String address=PATH_PREFIX+"葛店工段能耗报表.xlsx";
        String ext = address.substring(address.lastIndexOf("."));
        File teampFile=null;
        InputStream is=null;
        try {
            Workbook rwb=null;
            File insertFile=new File(address);
            is = new FileInputStream(insertFile);
            if(".xls".equals(ext)){
                rwb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext)){
                rwb = new XSSFWorkbook(is);
            }

            Sheet sheet = rwb.getSheetAt(sheetNum);
            //int clos=sheet.getRow(0).getPhysicalNumberOfCells();//得到所有的列
            int totalRows=sheet.getPhysicalNumberOfRows();//得到所有的行
            //从模板中获取所有的tagname的集合
            Map<Integer,String> rowMap=new HashMap<Integer,String>();
            for(int rowNum=2;rowNum<totalRows;rowNum++){
                Row row=sheet.getRow(rowNum);
                String tagname=DateTimeUtil.getCellFormatValue(row.getCell(tagColum));
                if(!StringUtils.isEmpty(tagname)){
                    rowMap.put(rowNum,tagname);
                }
            }
            List<String> tagnameList=new ArrayList<String>(rowMap.values());
            Map<String,Double> eleMap=SectionAnalysisService.getConsumptionValue(tagnameList,startDate,endDate,"0","day_power","realValue_power_table");
            Map<Integer[],Double> dataMap=new HashMap<Integer[],Double>();
            for(Map.Entry<Integer,String> en:rowMap.entrySet()){
                Integer rowNum=en.getKey();
                String tagname=rowMap.get(rowNum);
                dataMap.put(new Integer[]{rowNum,colNum},eleMap.get(tagname));
            }
            Date date=new Date();
            String timeStamp=DateTimeUtil.dateToStr(date,"yyMMddHHmmss");
            String currMonth=DateTimeUtil.dateToStr(date,"yyyy-MM");
            String outFileName="葛店工段能耗报表"+timeStamp+".xlsx";
            PoiUtil.setValueToPoi(null,address,dataMap,"",null,outFileName,"dianBuChang/"+currMonth,"",2,null,"");
            //从生成的excel文件中读取数据并存到数据库中
            String filePath=CREATE_FILE_PATH+"/export/excel/dianBuChang/"+currMonth+"/"+outFileName;
            teampFile=new File(filePath);
            is = new FileInputStream(teampFile);
            rwb = new XSSFWorkbook(is);
            sheet = rwb.getSheetAt(sheetNum);
            totalRows=sheet.getPhysicalNumberOfRows();//得到所有的行
            List<PowerCompensation> powerCompensationList=new ArrayList<PowerCompensation>();
            for(int rowNum=2;rowNum<totalRows;rowNum++){
                Row row=sheet.getRow(rowNum);
                int j=0;
                String pid=DateTimeUtil.getCellFormatValue(row.getCell(j++));
                pid=pid.substring(0,pid.indexOf("."));
                String tagname=DateTimeUtil.getCellFormatValue(row.getCell(j++));
                String name=DateTimeUtil.getCellFormatValue(row.getCell(j++));
                String costValue=DateTimeUtil.getCellFormatValue(row.getCell(j++));
                String compensateValue=DateTimeUtil.getCellFormatValue(row.getCell(j++));
                String calculateValue=DateTimeUtil.getCellFormatValue(row.getCell(j++));
                powerCompensationList.add(new PowerCompensation(pid,tagname,costValue,compensateValue,calculateValue));
            }
            /*String his_date=DateTimeUtil.dateToStr(date,"yyyy-MM-dd");
            String his_time=DateTimeUtil.dateToStr(date,"HH:mm:ss");*/
            String his_date=startDate;
            String his_time="00:00:00";
            if(powerCompensationList.size()>0) iPowerCompensation.batchIsert(powerCompensationList,his_date,his_time);
            //if(teampFile!=null) teampFile.delete();
        }catch (Exception e) {
            System.out.println("获取所有电表补偿值失败");
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("关流失败");
                }
                ;
            }
        }
    }

    @Override
    public int delTaxById(Long id) {
        return electricityPriceStatementMapper.delTaxById(id);
    }

    @Override
    public int insertAddition(List<TotalElectricityAddition> additionList,String type){
        List<TotalElectricityAddition> updateAddtionList=new ArrayList<TotalElectricityAddition>();
        List<TotalElectricityAddition> additionDbList=electricityPriceStatementMapper.selectAddtionByList(additionList,type);
        for(int index=0;index<additionList.size();index++){
            TotalElectricityAddition addition=additionList.get(index);
            for(TotalElectricityAddition additionDb:additionDbList){
                if(addition.getNumber()==(additionDb.getNumber())){
                    if(addition.getStart_time().trim().equals(additionDb.getStart_time().trim())){
                        addition.setId(additionDb.getId());
                        updateAddtionList.add(addition);
                        additionList.remove(index);
                        index--;
                        break;
                    }
                }
            }
        }
        int insertCount=0;
        if(updateAddtionList.size()>0) insertCount = electricityPriceStatementMapper.batchUpdateAddtion(updateAddtionList);
        if(additionList.size()>0) insertCount=electricityPriceStatementMapper.batchInsertAddtion(additionList,type);
        return insertCount;
    }

    /*变动电价电单价报表*/
    @Override
    public ServerResponse power_price(String Applicable_time,String method){
        String timeStamp=DateTimeUtil.dateToStr(new Date(),"yyMMddHHmmss");
        String startDate=Applicable_time+"-01";
        String endDate=this.addMonth(startDate);
        Map<Integer[],Double> map = new HashMap<Integer[],Double>();
        List<Contractual_capacity> list1 = electricityPriceStatementMapper.select_Contractual_capacity(Applicable_time);
        Map<String,List<TimeJob>> powerMap = timeJobService.selectEletric(startDate,endDate);
        List<Unit_Price> list3 = electricityPriceStatementMapper.select_Unit_Price(Applicable_time);
        List<TotalElectricityAddition> list4 = electricityPriceStatementMapper.select_TotalElectricityAddition(Applicable_time);
        List<Tax> list5 = electricityPriceStatementMapper.select_tax(Applicable_time);
        String date_1=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Applicable_time,"yyyy-MM"),"yyyy年MM月");
        String sheetName="葛店"+date_1;
        String msg="";
        //计算张三三的功率因素
        List<String> tagnameList=new ArrayList<>();
        tagnameList.add("JT-110900-02-EP");tagnameList.add("JT-110900-02-EQ");
        Map<String,Double> zssPfMap=SectionAnalysisService.getConsumptionValue(tagnameList,startDate,endDate,"0","day_power","realValue_power_table");
        Double zssEP=zssPfMap.get("JT-110900-02-EP");
        Double zssEQ=zssPfMap.get("JT-110900-02-EQ");
        Double zssPF=0d;
        if(zssEP!=0&&zssEQ!=0) zssPF=zssEP/Math.sqrt(zssEP*zssEP+zssEQ*zssEQ);
        //计算华三三的功率因素
        List<String> tagnameList_2=new ArrayList<>();
        tagnameList_2.add("JT-110900-01-EP");tagnameList_2.add("JT-110900-01-EQ");
        Map<String,Double> hssPfMap=SectionAnalysisService.getConsumptionValue(tagnameList_2,startDate,endDate,"0","day_power","realValue_power_table");
        Double hssEP=hssPfMap.get("JT-110900-01-EP");
        Double hssEQ=hssPfMap.get("JT-110900-01-EQ");
        Double hssPF=0d;
        if(hssEP!=0&&zssEQ!=0) hssPF=hssEP/Math.sqrt(hssEP*hssEP+hssEQ*hssEQ);
        if(zssPF<0.91&&hssPF<0.91){
            map.put(new Integer[]{29,1},Double.parseDouble("0.0000"));
        }else if(zssPF<0.91&&hssPF>=0.91){
            if(hssPF>=0.91&&hssPF<0.92){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0015"));
            }else if(hssPF>=0.92&&hssPF<0.93){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0030"));
            }else if(hssPF>=0.93&&hssPF<0.94){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0045"));
            }else if(hssPF>=0.94&&hssPF<0.95){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0060"));
            }else if(hssPF>=0.95&&hssPF<1.0){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0075"));
            }
        }else if(hssPF<0.91&&zssPF>=0.91){
            if(zssPF>=0.91&&zssPF<0.92){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0015"));
            }else if(zssPF>=0.92&&zssPF<0.93){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0030"));
            }else if(zssPF>=0.93&&zssPF<0.94){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0045"));
            }else if(zssPF>=0.94&&zssPF<0.95){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0060"));
            }else if(zssPF>=0.95&&zssPF<1.0){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0075"));
            }
        }else{
            Double avg=(zssPF+hssPF)/2;
            if(avg>=0.91&&avg<0.92){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0015"));
            }else if(avg>=0.92&&avg<0.93){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0030"));
            }else if(avg>=0.93&&avg<0.94){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0045"));
            }else if(avg>=0.94&&avg<0.95){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0060"));
            }else if(avg>=0.95&&avg<1.0){
                map.put(new Integer[]{29,1},Double.parseDouble("0.0075"));
            }
        }
        Double allContractual_capacity=0d;
        Double Contractual_capacityPrice=0d;
        for (Contractual_capacity contractual_capacity : list1){

//            if(StringUtils.isEmpty(contractual_capacity.getApplicable_time().trim())){
//                msg="没有当前月的立约容量";
//            }else if(!contractual_capacity.getApplicable_time().trim().equals(Applicable_time.trim())){
//                msg="没有当前月的立约容量，当前显示的是："+contractual_capacity.getApplicable_time().trim()+"的立约容量";
//            }
            allContractual_capacity+=Double.parseDouble(contractual_capacity.getContractual_capacity());
            Contractual_capacityPrice=Double.parseDouble(contractual_capacity.getPrice());
//            if ("1#变压器".equals(contractual_capacity.getName())){
//                map.put(new Integer[]{3,0},Double.parseDouble(contractual_capacity.getContractual_capacity()));
//                map.put(new Integer[]{2,1},Double.parseDouble(contractual_capacity.getPrice()));
//                map.put(new Integer[]{3,1},Double.parseDouble(contractual_capacity.getPrice()));
//            }
//            if ("2#变压器".equals(contractual_capacity.getName())){
//                map.put(new Integer[]{4,0},Double.parseDouble(contractual_capacity.getContractual_capacity()));
//                map.put(new Integer[]{4,1},Double.parseDouble(contractual_capacity.getPrice()));
//            }
//            if ("3#变压器".equals(contractual_capacity.getName())){
//                map.put(new Integer[]{5,0},Double.parseDouble(contractual_capacity.getContractual_capacity()));
//                map.put(new Integer[]{5,1},Double.parseDouble(contractual_capacity.getPrice()));
//            }
//            if ("1#高压电机".equals(contractual_capacity.getName())){
//                map.put(new Integer[]{6,0},Double.parseDouble(contractual_capacity.getContractual_capacity()));
//                map.put(new Integer[]{6,1},Double.parseDouble(contractual_capacity.getPrice()));
//            }
//            if ("2#高压电机".equals(contractual_capacity.getName())){
//                map.put(new Integer[]{7,0},Double.parseDouble(contractual_capacity.getContractual_capacity()));
//                map.put(new Integer[]{7,1},Double.parseDouble(contractual_capacity.getPrice()));
//            }
//            if ("3#高压电机".equals(contractual_capacity.getName())){
//                map.put(new Integer[]{8,0},Double.parseDouble(contractual_capacity.getContractual_capacity()));
//                map.put(new Integer[]{8,1},Double.parseDouble(contractual_capacity.getPrice()));
//            }
//            if ("4#高压电机".equals(contractual_capacity.getName())){
//                map.put(new Integer[]{9,0},Double.parseDouble(contractual_capacity.getContractual_capacity()));
//                map.put(new Integer[]{9,1},Double.parseDouble(contractual_capacity.getPrice()));
//            }
//            if ("5#高压电机".equals(contractual_capacity.getName())){
//                map.put(new Integer[]{10,0},Double.parseDouble(contractual_capacity.getContractual_capacity()));
//                map.put(new Integer[]{10,1},Double.parseDouble(contractual_capacity.getPrice()));
//            }
//            if ("6#高压电机".equals(contractual_capacity.getName())){
//                map.put(new Integer[]{11,0},Double.parseDouble(contractual_capacity.getContractual_capacity()));
//                map.put(new Integer[]{11,1},Double.parseDouble(contractual_capacity.getPrice()));
//            }
        }
        map.put(new Integer[]{2,0},allContractual_capacity);
        map.put(new Integer[]{2,1},Contractual_capacityPrice);
        for(Map.Entry<String,List<TimeJob>> en:powerMap.entrySet()){
            if(en.getKey().contains("zss")){
                //张三三上次示数
                for(TimeJob timeJob:powerMap.get("zssStartShowDatas")){
                    Double powerValue=0d;
                    String time_value=timeJob.getTime_value();
                    System.out.println("张三三上次示数");
                    if(!StringUtils.isEmpty(time_value)) powerValue=Double.valueOf(time_value);
                    String tagname=timeJob.getTagname();
                    int startIndex=tagname.lastIndexOf("-")+1;
                    String tagnameSuffix=tagname.substring(startIndex);
                    if(tagnameSuffix.equals("EP")){
                        map.put(new Integer[]{18,2},powerValue);
                    }else if(tagnameSuffix.equals("EP2")){
                        map.put(new Integer[]{19,2},powerValue);
                    }else if(tagnameSuffix.equals("EP4")){
                        map.put(new Integer[]{20,2},powerValue);
                    }else if(tagnameSuffix.equals("EP3")){
                        map.put(new Integer[]{21,2},powerValue);
                    }else if(tagnameSuffix.equals("EQ")){
                        map.put(new Integer[]{22,2},powerValue);
                    }
                }
                //张三三本次示数
                for(TimeJob timeJob:powerMap.get("zssCurrShowDatas")){
                    Double powerValue=0d;
                    System.out.println("张三三本次示数");
                    String time_value=timeJob.getTime_value();
                    if(!StringUtils.isEmpty(time_value)) powerValue=Double.valueOf(time_value);
                    String tagname=timeJob.getTagname();
                    int startIndex=tagname.lastIndexOf("-")+1;
                    String tagnameSuffix=tagname.substring(startIndex);
                    if(tagnameSuffix.equals("EP")){
                        map.put(new Integer[]{18,3},powerValue);
                    }else if(tagnameSuffix.equals("EP2")){
                        map.put(new Integer[]{19,3},powerValue);
                    }else if(tagnameSuffix.equals("EP4")){
                        map.put(new Integer[]{20,3},powerValue);
                    }else if(tagnameSuffix.equals("EP3")){
                        map.put(new Integer[]{21,3},powerValue);
                    }else if(tagnameSuffix.equals("EQ")){
                        map.put(new Integer[]{22,3},powerValue);
                    }
                }
            }else if(en.getKey().contains("hss")){
                //华三三上次示数
                for(TimeJob timeJob:powerMap.get("hssStartShowDatas")){
                    System.out.println("华三三上次示数");
                    Double powerValue=0d;
                    String time_value=timeJob.getTime_value();
                    if(!StringUtils.isEmpty(time_value)) powerValue=Double.valueOf(time_value);
                    String tagname=timeJob.getTagname();
                    int startIndex=tagname.lastIndexOf("-")+1;
                    String tagnameSuffix=tagname.substring(startIndex);
                    if(tagnameSuffix.equals("EP")){
                        map.put(new Integer[]{13,2},powerValue);
                    }else if(tagnameSuffix.equals("EP2")){
                        map.put(new Integer[]{14,2},powerValue);
                    }else if(tagnameSuffix.equals("EP4")){
                        map.put(new Integer[]{15,2},powerValue);
                    }else if(tagnameSuffix.equals("EP3")){
                        map.put(new Integer[]{16,2},powerValue);
                    }else if(tagnameSuffix.equals("EQ")){
                        map.put(new Integer[]{17,2},powerValue);
                    }
                }
                //华三三本次示数
                for(TimeJob timeJob:powerMap.get("hssCurrShowDatas")){
                    System.out.println("华三三本次示数");
                    Double powerValue=0d;
                    String time_value=timeJob.getTime_value();
                    if(!StringUtils.isEmpty(time_value)) powerValue=Double.valueOf(time_value);
                    String tagname=timeJob.getTagname();
                    int startIndex=tagname.lastIndexOf("-")+1;
                    String tagnameSuffix=tagname.substring(startIndex);
                    if(tagnameSuffix.equals("EP")){
                        map.put(new Integer[]{13,3},powerValue);
                    }else if(tagnameSuffix.equals("EP2")){
                        map.put(new Integer[]{14,3},powerValue);
                    }else if(tagnameSuffix.equals("EP4")){
                        map.put(new Integer[]{15,3},powerValue);
                    }else if(tagnameSuffix.equals("EP3")){
                        map.put(new Integer[]{16,3},powerValue);
                    }else if(tagnameSuffix.equals("EQ")){
                        map.put(new Integer[]{17,3},powerValue);
                    }
                }
            }
        }
        if(list3.size()>0){
            Unit_Price unit_price=list3.get(0);
            if(!StringUtils.isEmpty(unit_price.getStart_time())){
                if(!unit_price.getStart_time().trim().equals(Applicable_time.trim())){
                    msg+="没有该当前月的峰、平、谷电单价,当前显示的是 "+unit_price.getStart_time().trim()+"的电单价";
                }else{
                    if(StringUtils.isEmpty(unit_price.getPeak_electricity_price())){
                        msg+="峰电单价为空,";
                    }
                    if(StringUtils.isEmpty(unit_price.getPeacetime_electricity_price())){
                        msg+="平电单价为空,";
                    }
                    if(StringUtils.isEmpty(unit_price.getTroughtime_electricity_price())){
                        msg+="谷电单价为空,";
                    }
                }
            }
        }else{
            msg+="没有当前月的峰、平、谷电单价";
        }
        for (Unit_Price unit_price : list3){
            map.put(new Integer[]{25,2},Double.parseDouble(unit_price.getPeak_electricity_price()));
            map.put(new Integer[]{26,2},Double.parseDouble(unit_price.getPeacetime_electricity_price()));
            map.put(new Integer[]{27,2},Double.parseDouble(unit_price.getTroughtime_electricity_price()));
        }
        //工业电占比
        String eleAddtion="0";
        //平目录占比
        String pmlAddtion="0";
        if(list4.size()>0){
            TotalElectricityAddition totalElectricityAddition=list4.get(0);
            if(!StringUtils.isEmpty(totalElectricityAddition.getStart_time())){
                if(!totalElectricityAddition.getStart_time().trim().equals(Applicable_time.trim())){
                    msg+="没有当前月的平目录贷征、工业贷征,当前显示的是 "+totalElectricityAddition.getStart_time().trim();
                }
            }
        }else{
            msg+="没有当前月的平目录贷征、工业贷征";
        }
        for (TotalElectricityAddition totalElectricityAddition : list4){
            if ("工业电".equals(totalElectricityAddition.getType())){
                eleAddtion=totalElectricityAddition.getProportion();
                if ("城市附加".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{32,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("农网还贷".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{33,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("可再生能源附加".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{34,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("农村低压电网维护费".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{35,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("小型水库移民扶持".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{36,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("大中型水库移民扶持".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{37,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
            }
            if ("平目录".equals(totalElectricityAddition.getType())){
                pmlAddtion=totalElectricityAddition.getProportion();
                if ("平目录电费".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{39,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("城市附加".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{41,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("农网还贷".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{42,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("可再生能源附加".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{43,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("农村低压电网维护费".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{44,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("小型水库移民扶持".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{45,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
                if ("大中型水库移民扶持".equals(totalElectricityAddition.getName())){
                    map.put(new Integer[]{46,2},Double.parseDouble(totalElectricityAddition.getUnit_Price()));
                }
            }
        }
        map.put(new Integer[]{32,13},Double.parseDouble(eleAddtion));
        map.put(new Integer[]{39,13},Double.parseDouble(pmlAddtion));
        String taxStr = "";
        for (Tax tax : list5){
            taxStr=tax.getTax();
            if(!StringUtils.isEmpty(taxStr)){
                break;
            }
        }
        Integer[] integers = new Integer[]{2,13};
        if(StringUtils.isEmpty(taxStr)){
            return ServerResponse.createByErrorMessage("请输入当前月份的税率");
        }
        map.put(integers,Double.parseDouble(taxStr));
        //葛店工厂变动电价20190128
        String outFileName=date_1+"葛店工厂变动电价_"+timeStamp+".xlsx";
        String []columnName=new String[2];
        File ff=null;
        String filePath=PATH_PREFIX+"葛店工厂变动电价.xlsx";
        PoiUtil.setValueToPoi(ff,filePath,map,sheetName,columnName,outFileName,"bianDongDianJia","",0,null,method);
        String power_price = ZongHeDianJiaUtil.getPricePowerByExcel("D:/dist/export/excel/bianDongDianJia/"+outFileName);
        electricityPriceStatementMapper.UpdateSmTable("Unit_Price_Power","power_price",power_price,"id","12");
        return ServerResponse.createBySuccess(msg,"/export/excel/bianDongDianJia/"+outFileName);
    }

    //变动电价报表预览
    @Override
    public ServerResponse power_price_pdf(String Applicable_time,String method) {
        ServerResponse response=this.power_price(Applicable_time,method);
        if(!response.getStatus().equals("SUCCESS")) return response;
        //生成后的变动电价的报表的路径
        String reportPath=CREATE_FILE_PATH+response.getData();
        String outFileName="";
        try {
            //获取文件名
            int startIndex=reportPath.lastIndexOf("/");
            int endIndex=reportPath.lastIndexOf(".");
            outFileName=reportPath.substring(startIndex+1,endIndex);
            //生成的pdf路径
            String pdf_path=CREATE_pdf_PATH+outFileName+".pdf";
            ExcelUtils.excel2Pdf(reportPath,pdf_path);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("pdf转换失败");
        }
        return ServerResponse.createBySuccess(response.getMsg(),"/export/pdf/"+outFileName+".pdf");
    }

    //工段
    @Override
    public ServerResponse energy_consumption(String Start_t, String End_t,String method) {
        //End_t=this.addMonth(Start_t);
        String realMark="0";
        if (sdf.format(new Date()).equals(End_t.trim())) {
            realMark = "-1";
        }
        List<SectionAnalysis> energy_ = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String start_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
        String end_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(End_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
        String timeStamp=DateTimeUtil.dateToStr(new Date(),"yyMMddHHmmss");
        String date=DateTimeUtil.dateToStr(new Date(),"yyyy");
        String outFileName="";
        outFileName="葛店工段能耗报表_"+start_date+"_"+end_date+"_"+timeStamp+".xlsx";
        String modelFileName="";
        File modelFile=new File(PATH_PREFIX+"葛店工段能耗报表"+date+".xlsx");
        if(modelFile.exists()){
            //当前年的模板
            modelFileName=PATH_PREFIX+"葛店工段能耗报表"+date+".xlsx";
        }else{
            //使用空白模板
            modelFileName=PATH_PREFIX+"葛店工段能耗报表.xlsx";
        }
        //获取数据库中的最大时间和最小时间
        String maxDate=sectionAnalysisMapper.selectDate_time("max(his_date)");
        String minDate=sectionAnalysisMapper.selectDate_time("min(his_date)");
        //开始时间比数据库的最大时间大或者结束时间比数据库最小时间小
        if(!this.compareToDate(maxDate,Start_t)||this.compareToDate(minDate,End_t)) return ServerResponse.createByErrorMessage("没有该时间段的数据，该系统存储的数据时间范围："+minDate+"至"+maxDate);
        //开始时间比数据库的最小时间小
        /*if(this.compareToDate(minDate,Start_t)) Start_t=minDate;
        //结束时间比数据库的最大时间大
        if(!this.compareToDate(maxDate,End_t)) End_t=maxDate;*/
        try {
            //energy_=SectionAnalysisService.sectionAnalysis(Start_t,End_t);
            Map<Integer[],Double> dataMap = new HashMap<>();
            //水单价
            String errcode="请输入该月份的：";
            Map<String,Double> productMap=new HashMap<>();
            String startDate=date= DateTimeUtil.strToStr(Start_t,"yyyy-MM-dd","yyyy-MM");
            String endDate=date= DateTimeUtil.strToStr(End_t,"yyyy-MM-dd","yyyy-MM");
            Double unit_water=unit_PriceService.getUnitPrice(startDate,endDate,"Unit_Price_water");
            if(unit_water==null) {
                errcode+="水单价、";
                unit_water=0d;
            }
            productMap.put("unit_water",unit_water);
            //电单价
            Double unit_power=0d;
            List<Unit_Price> unit_Price = unit_PriceService.selectPowerUnit_Price();
            if(unit_Price.size()>0) unit_power= Double.valueOf(unit_Price.get(0).getPower_price());
            productMap.put("unit_power",unit_power);
            //蒸汽单价
            Double unit_steam=unit_PriceService.getUnitPrice(startDate,endDate,"Unit_Price_steam");
            if(unit_steam==null){
                errcode+="蒸汽单价、";
                unit_steam=0d;
            }
            productMap.put("unit_steam",unit_steam);

            Map<String,Map<Integer, String>> resultMap=setDataMap(Start_t, End_t, realMark, modelFileName,dataMap);
            //单价变量的集合
            Map<Integer, String> productMediaMap=resultMap.get("product");
            List<String> procuctTagnameList=new ArrayList<>(productMediaMap.values());
            if(procuctTagnameList.size()>0){
                for(Map.Entry<Integer,String> en:productMediaMap.entrySet()){
                    Integer rowNum=en.getKey();
                    String tagname=productMediaMap.get(rowNum);
                    dataMap.put(new Integer[]{rowNum,7+2},productMap.get(tagname));
                }
            }
            //将耗值填入到对应的模板里，并刷新对应月分的宏
            String dateStr = sdf.format(simpleDateFormat.parse(Start_t));
            Integer month = Integer.parseInt(dateStr);
            PoiUtil.setValueToPoi(modelFile,modelFileName,dataMap,"",null,outFileName,"gongDuanNengHao","多列",2,month,method);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return ServerResponse.createBySuccess("/export/excel/gongDuanNengHao/"+outFileName);
    }

    /*工厂能耗*/
    @Override
    public ServerResponse finally_report(String Start_t,String End_t,String type,String method) {
        String start_date=null;
        String yearAndMonth=null;
        String end_date=null;
        String timeStamp=DateTimeUtil.dateToStr(new Date(),"yyMMddHHmmss");
        String date=DateTimeUtil.dateToStr(new Date(),"yyyy");
        String outFileName="";
        String modelFileName="";
        Integer column=null;
        String [] columnName=new String[2];
        File modelFile=null;
        String weekMark="";
        if(type.equals("周报")){
            start_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
            yearAndMonth=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyyMM");
            end_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(End_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
            String location=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"dd");
            //if(!location.equals("01")||!location.equals("08")||!location.equals("15")||!location.equals("22")) return "1";
            List<String> weekList=new ArrayList<String>();
            weekList.add("01");weekList.add("08");weekList.add("15");weekList.add("22");
            if(!weekList.contains(location)) weekMark= "1";
            column=Integer.parseInt(location)/7+1;
            columnName[0]=start_date.substring(5)+"到"+end_date.substring(5);
            columnName[1]=start_date.substring(0,5)+"葛店 产品能耗周报（用量）";
            outFileName="葛店工厂能耗周报"+start_date+"_"+end_date+"_"+timeStamp+".xlsx";
            modelFile=new File(PATH_PREFIX+"葛店工厂能耗周报"+yearAndMonth+".xlsx");
            if(modelFile.exists()){
                //当前年当前月的模板
                modelFileName=PATH_PREFIX+"葛店工厂能耗周报"+yearAndMonth+".xlsx";

            }else{
                //使用空白模板
                modelFileName=PATH_PREFIX+"葛店工厂能耗周报"+".xlsx";
            }
        }else if(type.equals("月报")){
            //End_t=this.addMonth(Start_t);
            start_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
            String year=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
            end_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(End_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
            String month=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"MM");
            column=Integer.parseInt(month);
            columnName[1]=start_date.substring(0,5)+"葛店 产品能耗月报（用量）";
            outFileName="葛店工厂能耗月报"+start_date+"_"+end_date+"_"+timeStamp+".xlsx";
            modelFile=new File(PATH_PREFIX+"葛店工厂能耗月报"+year+".xlsx");
            if(modelFile.exists()){
                //当前年的模板
                modelFileName=PATH_PREFIX+"葛店工厂能耗月报"+year+".xlsx";
            }else{
                //使用空白模板
                modelFileName=PATH_PREFIX+"葛店工厂能耗月报.xlsx";
            }
        }else if(type.equals("年报")){
            //End_t=this.addYear(Start_t);
            start_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy年");
            String year=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
            column=Integer.parseInt(year)-2019+1;
            columnName[0]=start_date.substring(0,4);
            columnName[1]=start_date.substring(0,5)+"葛店 产品能耗年报（用量）";
            outFileName=start_date+"葛店工厂能耗年报"+"_"+timeStamp+".xlsx";
            modelFile=new File(PATH_PREFIX+"葛店工厂能耗年报"+year+".xlsx");
            if(modelFile.exists()){
                //当前年的模板
                if(method.equals("pdf")){
                    modelFileName=pdf_PATH_PREFIX+"葛店工厂能耗年报"+year+".xlsx";
                }else{
                    modelFileName=PATH_PREFIX+"葛店工厂能耗年报"+year+".xlsx";
                }
            }else{
                //使用空白模板
                modelFileName=PATH_PREFIX+"葛店工厂能耗年报.xlsx";
            }
        }
        //获取数据库中的最大时间和最小时间
        String maxDate=sectionAnalysisMapper.selectDate_time("max(his_date)");
        String minDate=sectionAnalysisMapper.selectDate_time("min(his_date)");
        //开始时间比数据库的最大时间大或者结束时间比数据库最小时间小
        if(!this.compareToDate(maxDate,Start_t)||this.compareToDate(minDate,End_t)) return ServerResponse.createByErrorMessage("没有该时间段的数据，该系统存储的数据时间范围："+minDate+"至"+maxDate);
        //开始时间比数据库的最小时间小
        /*if(this.compareToDate(minDate,Start_t)) Start_t=minDate;
        //结束时间比数据库的最大时间大
        if(!this.compareToDate(maxDate,End_t)) End_t=maxDate;*/

        //产品产量
        Map<String,Double> productMap=new HashMap<>();
        String msg="请输入该月份的：";
        //String queryDate=start_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy-MM");
        Double gjt_ARA_yield=gjtYieldMapper.selectTotalYieldsCondition(Start_t,End_t,"ARA","gjt_yield");
        if(gjt_ARA_yield==null) {
            msg+="ARA干菌体产量、";
            gjt_ARA_yield=0d;
        }
        productMap.put("gjt_ARA_yield",gjt_ARA_yield);
        Double gjt_BC_yield=gjtYieldMapper.selectTotalYieldsCondition(Start_t,End_t,"BC","gjt_yield");;
        if(gjt_BC_yield==null){
            msg+="BC干菌体产量、";
            gjt_BC_yield=0d;
        }
        productMap.put("gjt_BC_yield",gjt_BC_yield);
        Double my_ARA_yield=gjtYieldMapper.selectTotalYieldsCondition(Start_t,End_t,"ARA","my_yield");;
        if(my_ARA_yield==null){
            msg+="ARA毛油产量、";
            my_ARA_yield=0d;
        }
        productMap.put("my_ARA_yield",my_ARA_yield);
        Double my_DHA_yield=gjtYieldMapper.selectTotalYieldsCondition(Start_t,End_t,"DHA","my_yield");;
        if(my_DHA_yield==null){
            msg+="DHA毛油产量、";
            my_DHA_yield=0d;
        }
        productMap.put("my_DHA_yield",my_DHA_yield);
        Double cpy_ARA_yield=gjtYieldMapper.selectTotalYieldsCondition(Start_t,End_t,"ARA","cpy_yield");;
        if(cpy_ARA_yield==null){
            msg+="ARA成品油产量、";
            cpy_ARA_yield=0d;
        }
        productMap.put("cpy_ARA_yield",cpy_ARA_yield);
        Double cpy_DHA_yield=gjtYieldMapper.selectTotalYieldsCondition(Start_t,End_t,"DHA","cpy_yield");;
        if(cpy_DHA_yield==null) {
            msg+="DHA成品油产量、";
            cpy_DHA_yield=0d;
        }
        productMap.put("cpy_DHA_yield",cpy_DHA_yield);
        String realMark="0";
        if (sdf.format(new Date()).equals(End_t.trim())) {
            realMark = "-1";
        }
        //均摊比例部分
        //总压缩空气通气量
        String queryStartDate=Start_t+" "+"00:00:00";
        String queryEndDate=End_t+" "+"00:00:00";
        Double zong_air=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"","airCostValue");
        zong_air+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"","");
        productMap.put("zong_air",zong_air);
        //ARA干菌体通气量
        Double gjt_ARA_air=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"ARA干菌体","airCostValue");
        gjt_ARA_air+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"","ARA干菌体");
        productMap.put("gjt_ARA_air",gjt_ARA_air);
        //BC干菌体通气量
        Double gjt_BC_air=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"BC干菌体","airCostValue");
        gjt_BC_air+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"","BC干菌体");
        productMap.put("gjt_BC_air",gjt_BC_air);
        //DHA干菌体通气量
        Double git_DHA_air=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"DHA干菌体","airCostValue");
        git_DHA_air+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"","DHA干菌体");
        productMap.put("git_DHA_air",git_DHA_air);

        //总蒸汽通气量
        Double zong_steam=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"","steamCostValue");
        zong_steam+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"蒸汽","");
        productMap.put("zong_steam",zong_steam);
        //ARA干菌体通气量
        Double gjt_ARA_steam=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"ARA干菌体","steamCostValue");
        gjt_ARA_steam+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"蒸汽","ARA干菌体");
        productMap.put("gjt_ARA_steam",gjt_ARA_steam);
        //BC干菌体通气量
        Double gjt_BC_steam=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"BC干菌体","steamCostValue");
        gjt_BC_steam+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"蒸汽","BC干菌体");
        productMap.put("gjt_BC_steam",gjt_BC_steam);
        //DHA干菌体通气量
        Double git_DHA_steam=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"DHA干菌体","steamCostValue");
        git_DHA_steam+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"蒸汽","DHA干菌体");
        productMap.put("git_DHA_steam",git_DHA_steam);

        //ARA成品油生产天数
        Double cpy_ARA_days=iGjtYield.getProductionDays(Start_t,End_t,"ARA","cpy_yield");
        productMap.put("cpy_ARA_days",cpy_ARA_days);
        //DHA成品油生产天数
        Double cpy_DHA_days=iGjtYield.getProductionDays(Start_t,End_t,"DHA","cpy_yield");
        productMap.put("cpy_DHA_days",cpy_DHA_days);

        Map<Integer[],Double> dataMap = new HashMap<>();
        Map<String,Map<Integer, String>> resultMap=setDataMap(Start_t, End_t, realMark, modelFileName,dataMap);
        //将产量数据设置到数据源里
        //产量变量的集合
        Map<Integer, String> productMediaMap=resultMap.get("product");
        List<String> procuctTagnameList=new ArrayList<>(productMediaMap.values());
        if(procuctTagnameList.size()>0){
            for(Map.Entry<Integer,String> en:productMediaMap.entrySet()){
                Integer rowNum=en.getKey();
                String tagname=productMediaMap.get(rowNum);
                dataMap.put(new Integer[]{rowNum,11+2},productMap.get(tagname));
            }
        }
        //将耗值填入到对应的模板里，并刷新对应月分的宏
        PoiUtil.setValueToPoi(modelFile,modelFileName,dataMap,"",columnName,outFileName,"gongChangNengHao","多列",2,column,method);
        return ServerResponse.createBySuccess(msg,"/export/excel/gongChangNengHao/"+outFileName);
    }

    @Override
    public ServerResponse powerCost(String start_t, String end_t,String method) {
        //end_t=this.addMonth(start_t);
        List<SectionAnalysis> energy_ = new ArrayList<>();
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String start_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(start_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
        String end_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(end_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
        String timeStamp=DateTimeUtil.dateToStr(new Date(),"yyMMddHHmmss");
        String date=DateTimeUtil.dateToStr(new Date(),"yyyy");
        String outFileName="";
        outFileName="葛店工厂能耗动力成本_"+start_date+"_"+end_date+"_"+timeStamp+".xlsx";
        String modelFileName="";
        File modelFile=new File(PATH_PREFIX+"葛店工厂能耗动力成本"+date+".xlsx");
        if(modelFile.exists()){
            //当前年的模板
            modelFileName=PATH_PREFIX+"葛店工厂能耗动力成本"+date+".xlsx";
        }else{
            //使用空白模板
            modelFileName=PATH_PREFIX+"葛店工厂能耗动力成本.xlsx";
        }
        //获取数据库中的最大时间和最小时间
        String maxDate=sectionAnalysisMapper.selectDate_time("max(his_date)");
        String minDate=sectionAnalysisMapper.selectDate_time("min(his_date)");
        //开始时间比数据库的最大时间大或者结束时间比数据库最小时间小
        if(!this.compareToDate(maxDate,start_t)||this.compareToDate(minDate,end_t)) return ServerResponse.createByErrorMessage("没有该时间段的数据，该系统存储的数据时间范围："+minDate+"至"+maxDate);
        //开始时间比数据库的最小时间小
        if(this.compareToDate(minDate,start_t)) start_t=minDate;
        //结束时间比数据库的最大时间大
        if(!this.compareToDate(maxDate,end_t)) end_t=maxDate;
        /*产品计算=================================================*/
        String errcode="请输入该月份的：";
        //水单价
        Map<String,Double> productMap=new HashMap<>();
        String startDate=date= DateTimeUtil.strToStr(start_t,"yyyy-MM-dd","yyyy-MM");
        String endDate=date= DateTimeUtil.strToStr(end_t,"yyyy-MM-dd","yyyy-MM");
        Double unit_water=unit_PriceService.getUnitPrice(startDate,endDate,"Unit_Price_water");
        if(unit_water==null) {
            errcode+="水单价、";
            unit_water=0d;
        }
        productMap.put("unit_water",unit_water);
        //电单价
        Double unit_power=0d;
        List<Unit_Price> unit_Price = unit_PriceService.selectPowerUnit_Price();
        if(unit_Price.size()>0) unit_power= Double.valueOf(unit_Price.get(0).getPower_price());

//        String Applicable_time=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(start_t,"yyyy-MM-dd"),"yyyy-MM");
//        ServerResponse response=this.power_price(Applicable_time,"");
//        String pathUrl="";
//        if(response.getStatus().equals("SUCCESS")&&StringUtils.isEmpty(response.getMsg())){
//            pathUrl=response.getData().toString();
//        }else{
//            return ServerResponse.createByErrorMessage(response.getMsg());
//        }
//        //生成后的变动电价的报表的路径
//        String reportPath=CREATE_FILE_PATH+pathUrl;
//        //获取综合电价的方法
//        String unitPricePower= ZongHeDianJiaUtil.getPricePowerByExcel(reportPath);
//        if(!StringUtils.isEmpty(unitPricePower)){
//            try{
//                unit_power=Double.valueOf(unitPricePower);
//            }catch (Exception e){
//                unit_power=0d;
//            }
//        }else{
//            errcode+="电单价、";
//        }
        productMap.put("unit_power",unit_power);
        //蒸汽单价
        Double unit_steam=unit_PriceService.getUnitPrice(startDate,endDate,"Unit_Price_steam");
        if(unit_steam==null){
            errcode+="蒸汽单价、";
            unit_steam=0d;
        }
        productMap.put("unit_steam",unit_steam);
        //产量
        //String queryDate=start_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(start_t,"yyyy-MM-dd"),"yyyy-MM");
        Double gjt_ARA_yield=gjtYieldMapper.selectTotalYieldsCondition(start_t,end_t,"ARA","gjt_yield");
        if(gjt_ARA_yield==null) {
            errcode+="ARA干菌体产量、";
            gjt_ARA_yield=0d;
        }
        productMap.put("gjt_ARA_yield",gjt_ARA_yield);
        Double gjt_BC_yield=gjtYieldMapper.selectTotalYieldsCondition(start_t,end_t,"BC","gjt_yield");;
        if(gjt_BC_yield==null){
            errcode+="BC干菌体产量、";
            gjt_BC_yield=0d;
        }
        productMap.put("gjt_BC_yield",gjt_BC_yield);
        Double my_ARA_yield=gjtYieldMapper.selectTotalYieldsCondition(start_t,end_t,"ARA","my_yield");;
        if(my_ARA_yield==null){
            errcode+="ARA毛油产量、";
            my_ARA_yield=0d;
        }
        productMap.put("my_ARA_yield",my_ARA_yield);
        Double my_DHA_yield=gjtYieldMapper.selectTotalYieldsCondition(start_t,end_t,"DHA","my_yield");;
        if(my_DHA_yield==null){
            errcode+="DHA毛油产量、";
            my_DHA_yield=0d;
        }
        productMap.put("my_DHA_yield",my_DHA_yield);
        Double cpy_ARA_yield=gjtYieldMapper.selectTotalYieldsCondition(start_t,end_t,"ARA","cpy_yield");;
        if(cpy_ARA_yield==null){
            errcode+="ARA成品油产量、";
            cpy_ARA_yield=0d;
        }
        productMap.put("cpy_ARA_yield",cpy_ARA_yield);
        Double cpy_DHA_yield=gjtYieldMapper.selectTotalYieldsCondition(start_t,end_t,"DHA","cpy_yield");;
        if(cpy_DHA_yield==null) {
            errcode+="DHA成品油产量、";
            cpy_DHA_yield=0d;
        }
        productMap.put("cpy_DHA_yield",cpy_DHA_yield);

        //均摊比例部分
        //总压缩空气通气量
        String queryStartDate=start_t+" "+"00:00:00";
        String queryEndDate=end_t+" "+"00:00:00";
        Double zong_air=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"","airCostValue");
        zong_air+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"","");
        productMap.put("zong_air",zong_air);
        //ARA干菌体通气量
        Double gjt_ARA_air=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"ARA干菌体","airCostValue");
        gjt_ARA_air+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"","ARA干菌体");
        productMap.put("gjt_ARA_air",gjt_ARA_air);
        //BC干菌体通气量
        Double gjt_BC_air=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"BC干菌体","airCostValue");
        gjt_BC_air+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"","BC干菌体");
        productMap.put("gjt_BC_air",gjt_BC_air);
        //DHA干菌体通气量
        Double git_DHA_air=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"DHA干菌体","airCostValue");
        git_DHA_air+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"","DHA干菌体");
        productMap.put("git_DHA_air",git_DHA_air);

        //总蒸气通气量
        Double zong_steam=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"","steamCostValue");
        zong_steam+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"蒸汽","");
        productMap.put("zong_steam",zong_steam);
        //ARA干菌体通气量
        Double gjt_ARA_steam=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"ARA干菌体","steamCostValue");
        gjt_ARA_steam+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"蒸汽","ARA干菌体");
        productMap.put("gjt_ARA_steam",gjt_ARA_steam);
        //BC干菌体通气量
        Double gjt_BC_steam=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"BC干菌体","steamCostValue");
        gjt_BC_steam+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"蒸汽","BC干菌体");
        productMap.put("gjt_BC_steam",gjt_BC_steam);
        //DHA干菌体通气量
        Double git_DHA_steam=historyTankBatchNoService.getTotalAir(queryStartDate,queryEndDate,"DHA干菌体","steamCostValue");
        git_DHA_steam+=historyTankBatchNoService.getTotalAirCrossMonth(queryStartDate,queryEndDate,"蒸汽","DHA干菌体");
        productMap.put("git_DHA_steam",git_DHA_steam);

        //ARA成品油生产天数
        Double cpy_ARA_days=iGjtYield.getProductionDays(start_t,end_t,"ARA","cpy_yield");
        productMap.put("cpy_ARA_days",cpy_ARA_days);
        //DHA成品油生产天数
        Double cpy_DHA_days=iGjtYield.getProductionDays(start_t,end_t,"DHA","cpy_yield");
        productMap.put("cpy_DHA_days",cpy_DHA_days);


        String realMark="0";
        if (sdf.format(new Date()).equals(end_t.trim())) {
            realMark = "-1";
        }
        Map<Integer[],Double> dataMap = new HashMap<>();
        Map<String,Map<Integer, String>> resultMap=setDataMap(start_t, end_t, realMark, modelFileName,dataMap);
        //将产量数据设置到数据源里
        //产量变量及单价的集合
        Map<Integer, String> productMediaMap=resultMap.get("product");
        List<String> procuctTagnameList=new ArrayList<>(productMediaMap.values());
        if(procuctTagnameList.size()>0){
            for(Map.Entry<Integer,String> en:productMediaMap.entrySet()){
                Integer rowNum=en.getKey();
                String tagname=productMediaMap.get(rowNum);
                dataMap.put(new Integer[]{rowNum,11+2},productMap.get(tagname));
            }
        }
        String month=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(start_t,"yyyy-MM-dd"),"MM");
        Integer column=Integer.parseInt(month);
        PoiUtil.setValueToPoi(modelFile,modelFileName,dataMap,"",null,outFileName,"dongLiChengBen","多列",2,column,method);
        if(StringUtils.isEmpty(errcode)){
            return ServerResponse.createBySuccess("/export/excel/dongLiChengBen/"+outFileName);
        }else{
            //errcode+="请输入对应时间段内的单价";
            return  ServerResponse.createBySuccess(errcode,"/export/excel/dongLiChengBen/"+outFileName);
        }
    }



    /**
     * 获取报表数据源的方法
     * @param Start_t    报表开始时间
     * @param End_t      报表结束时间
     * @param realMark
     * @param modelFileName     模板路径
     * @return
     */
    public Map<String,Map<Integer, String>> setDataMap(String Start_t, String End_t, String realMark, String modelFileName,Map<Integer[],Double> dataMap) {
        //从模板中获取变量的集合
        int [] tagColums=new int[]{1,7,11};
        Map<String,Map<Integer, String>> mediaMap=this.getModelTagnameList(modelFileName,2,tagColums);
        //根据变量的集合查询对应时间段内的耗值
        //电的变量的集合
        Map<Integer, String> eleMediaMap=mediaMap.get("ele");
        List<String> eleList=new ArrayList<>(eleMediaMap.values());
        if(eleList.size()>0){
            Map<String,Double> eleMap=SectionAnalysisService.getConsumptionValue(eleList,Start_t,End_t,realMark,"day_power","realValue_power_table");
            for(Map.Entry<Integer,String> en:eleMediaMap.entrySet()){
                Integer rowNum=en.getKey();
                String tagname=eleMediaMap.get(rowNum);
                System.out.println("电介质行号："+rowNum+" ; "+tagname+" ; "+eleMap.get(tagname));
//                log.info("电介质行号："+rowNum+" ; "+tagname+" ; "+eleMap.get(tagname));
                dataMap.put(new Integer[]{rowNum,tagColums[0]+2},eleMap.get(tagname));
            }
        }

        //自来水的变量的集合
        Map<Integer, String> tapWaterMediaMap=mediaMap.get("tapWater");
        List<String> tapWaterList=new ArrayList<>(tapWaterMediaMap.values());
        if(tapWaterList.size()>0){
            Map<String,Double> waterMap=SectionAnalysisService.getConsumptionValue(tapWaterList,Start_t,End_t,realMark,"day_tapWater","realValue_tapWater_table");
            /*Double zongWater=0d;
            zongWater=waterMap.get("FIT-092501-01-EQ");
            Double total_fenbiao=0d;
            total_fenbiao=waterMap.get("FIT-092501-02-EQ")+waterMap.get("FIT-092501-03-EQ")+waterMap.get("FIT-092501-05-EQ")
                    +waterMap.get("FIT-092501-06-EQ")+waterMap.get("FIT-092501-08-EQ")+waterMap.get("FIT-092501-07-EQ");
            if(total_fenbiao>zongWater) zongWater=total_fenbiao;*/
            for(Map.Entry<Integer,String> en:tapWaterMediaMap.entrySet()){
                Integer rowNum=en.getKey();
                String tagname=tapWaterMediaMap.get(rowNum);
                /*if(tagname.equals("FIT-092501-01-EQ")){
                    dataMap.put(new Integer[]{rowNum,tagColums[1]+2},zongWater);
                }else{*/
                System.out.println(waterMap.get(tagname));
                    dataMap.put(new Integer[]{rowNum,tagColums[1]+2},waterMap.get(tagname));
                //}
            }
        }

        //循环水的变量的集合
        Map<Integer, String> circulateWaterMediaMap=mediaMap.get("circulateWater");
        List<String> circulateWaterList=new ArrayList<>(circulateWaterMediaMap.values());
        if(circulateWaterList.size()>0){
            Map<String,Double> circulateWaterMap=SectionAnalysisService.getConsumptionValue(circulateWaterList,Start_t,End_t,"0","day_circulatingWate","realValue_circulatingWater_table");
            for(Map.Entry<Integer,String> en:circulateWaterMediaMap.entrySet()){
                Integer rowNum=en.getKey();
                String tagname=circulateWaterMediaMap.get(rowNum);
                dataMap.put(new Integer[]{rowNum,tagColums[1]+2},circulateWaterMap.get(tagname));
            }
        }

        //冷冻水的变量的集合
        Map<Integer, String> freezeWaterMediaMap=mediaMap.get("freezeWater");
        List<String> freezeWaterList=new ArrayList<>(freezeWaterMediaMap.values());
        if(freezeWaterList.size()>0){
            Map<String,Double> freezeWaterMap=SectionAnalysisService.getConsumptionValue(freezeWaterList,Start_t,End_t,"0","day_freezeWater","realValue_freezeWater_table");
            for(Map.Entry<Integer,String> en:freezeWaterMediaMap.entrySet()){
                Integer rowNum=en.getKey();
                String tagname=freezeWaterMediaMap.get(rowNum);
                dataMap.put(new Integer[]{rowNum,tagColums[1]+2},freezeWaterMap.get(tagname));
            }
        }

        //蒸汽的变量的集合
        Map<Integer, String> steamMediaMap=mediaMap.get("steam");
        List<String> steamList=new ArrayList<>(steamMediaMap.values());
        if(steamList.size()>0){
            Map<String,Double> steamMap=SectionAnalysisService.getConsumptionValue(steamList,Start_t,End_t,"0","day_steamMeter","realValue_steamMeter_table");
            for(Map.Entry<Integer,String> en:steamMediaMap.entrySet()){
                Integer rowNum=en.getKey();
                String tagname=steamMediaMap.get(rowNum);
                dataMap.put(new Integer[]{rowNum,tagColums[2]+2},steamMap.get(tagname));
            }
        }
        //发酵压缩空气的变量的集合
        Map<Integer, String> fermentationAirMap=mediaMap.get("fermentationAir");
        List<String> fermentationAirList=new ArrayList<>(fermentationAirMap.values());
        if(steamList.size()>0){
            Map<String,Double> fermentationMap=SectionAnalysisService.getConsumptionValue(fermentationAirList,Start_t,End_t,"0","day_fermentationAir","realValue_fermentationAir_table");
            for(Map.Entry<Integer,String> en:fermentationAirMap.entrySet()){
                Integer rowNum=en.getKey();
                String tagname=fermentationAirMap.get(rowNum);
                dataMap.put(new Integer[]{rowNum,tagColums[1]+2},fermentationMap.get(tagname));
            }
        }
        return mediaMap;
    }

    /**
     * 从模板中获取所有介质的tagname的集合
     * @param address      模板的路径
     * @param sheetNum      数据源所在的sheet页
     * @param tagColNums    不同介质的变量(tagname)所在列号的集合
     * @return
     */
    public Map<String,Map<Integer, String>> getModelTagnameList(String address,int sheetNum,int[] tagColNums) {
        String ext = address.substring(address.lastIndexOf("."));
        Map<String,Map<Integer, String>> resultMap=new HashMap<>();
        //电的集合
        Map<Integer, String> eleMap=new HashMap<>();
        //自来水的集合
        Map<Integer, String> tapWaterMap=new HashMap<>();
        //循环水的集合
        Map<Integer, String> circulateWaterMap=new HashMap<>();
        //冷冻水的集合
        Map<Integer, String> freezeWaterMap=new HashMap<>();
        //蒸汽的集合
        Map<Integer, String> steamMap=new HashMap<>();
        //产品产量的集合
        Map<Integer, String> productMap=new HashMap<>();
        //发酵压缩空气的集合
        Map<Integer, String> fermentationAirMap=new HashMap<>();
        InputStream is=null;
        try {
            Workbook rwb = null;
            File insertFile = new File(address);
            is = new FileInputStream(insertFile);
            if (".xls".equals(ext)) {
                rwb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(ext)) {
                rwb = new XSSFWorkbook(is);
            }
            Sheet sheet = rwb.getSheetAt(sheetNum);
            //int clos=sheet.getRow(0).getPhysicalNumberOfCells();//得到所有的列
            int totalRows = sheet.getPhysicalNumberOfRows();//得到所有的行
            //从模板中获取所有介质的tagname的集合,
            String media="";
            String tagname ="";
            for(int startColum:tagColNums){
                for (int rowNum =0; rowNum < totalRows; rowNum++) {
                    tagname ="";
                    Row row = sheet.getRow(rowNum);
                    //判断是否是合并单元格
                    if(PoiUtil.isMergedRegion(sheet,rowNum,startColum)){
                        media=PoiUtil.getMergedRegionValue(sheet,rowNum,startColum);
                    }else{
                        tagname = DateTimeUtil.getCellFormatValue(row.getCell(startColum));
                    }
                    if (!StringUtils.isEmpty(tagname)) {
                        //用正则表达式匹配是否是汉字，如果是汉字，继续读下一行，因为变量不包含汉字
                        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]*");
                        Matcher m = pattern.matcher(tagname);
                        if(media.contains("电")&&!m.matches()){
                            eleMap.put(rowNum, tagname);
                        }else if(media.contains("自来水")&&!m.matches()){
                            tapWaterMap.put(rowNum, tagname);
                        }else if(media.contains("蒸汽")&&!m.matches()){
                            steamMap.put(rowNum, tagname);
                        }else if(media.contains("循环水")&&!m.matches()){
                            circulateWaterMap.put(rowNum, tagname);
                        }else if(media.contains("冷冻水")&&!m.matches()){
                            freezeWaterMap.put(rowNum, tagname);
                        }else if(media.contains("单价")||media.contains("产品产量")&&!m.matches()){
                            productMap.put(rowNum, tagname);
                        }else if(media.contains("发酵压缩空气")){
                            fermentationAirMap.put(rowNum, tagname);
                        }

                    }
                }
            }
            resultMap.put("ele",eleMap);
            resultMap.put("tapWater",tapWaterMap);
            resultMap.put("circulateWater",circulateWaterMap);
            resultMap.put("freezeWater",freezeWaterMap);
            resultMap.put("steam",steamMap);
            resultMap.put("product",productMap);
            resultMap.put("fermentationAir",fermentationAirMap);
        }catch (Exception e){
            System.out.println("获取所有介质的变量的集合失败");
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("关流失败");
                }
            }
        }
        return resultMap;
    }

    //蒸汽单位成本报表
    @Override
    public ServerResponse unitCostSteam(String start_t, String end_t) {
        //end_t=this.addMonth(start_t);
        String realMark="0";
        if (sdf.format(new Date()).equals(end_t.trim())) {
            realMark = "-1";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String start_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(start_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
        String end_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(end_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
        String timeStamp=DateTimeUtil.dateToStr(new Date(),"yyMMddHHmmss");
        String date=DateTimeUtil.dateToStr(new Date(),"yyyy");
        String outFileName="";
        outFileName="蒸汽单位成本报表_"+start_date+"_"+end_date+"_"+timeStamp+".xlsx";
        String modelFileName="";
        File modelFile=new File(PATH_PREFIX+"蒸汽单位成本报表"+date+".xlsx");
        if(modelFile.exists()){
            //当前年的模板
            modelFileName=PATH_PREFIX+"蒸汽单位成本报表"+date+".xlsx";
        }else{
            //使用空白模板
            modelFileName=PATH_PREFIX+"蒸汽单位成本报表.xlsx";
        }
        //获取数据库中的最大时间和最小时间
        String maxDate=sectionAnalysisMapper.selectDate_time("max(his_date)");
        String minDate=sectionAnalysisMapper.selectDate_time("min(his_date)");
        //开始时间比数据库的最大时间大或者结束时间比数据库最小时间小
        if(!this.compareToDate(maxDate,start_t)||this.compareToDate(minDate,end_t)) return ServerResponse.createByErrorMessage("没有该时间段的数据，该系统存储的数据时间范围："+minDate+"至"+maxDate);
        //开始时间比数据库的最小时间小
        if(this.compareToDate(minDate,start_t)) start_t=minDate;
        //结束时间比数据库的最大时间大
        if(!this.compareToDate(maxDate,end_t)) end_t=maxDate;

        Map<String,Double> productMap=new HashMap<>();
        //蒸汽单价
        Double unitPriceSteam=unit_priceService.selectUnitPrice(start_t,end_t,"Unit_Price_steam");
        if(unitPriceSteam==null)  unitPriceSteam=0d;
        productMap.put("unit_steam",unitPriceSteam);
        //水单价
        Double unitPriceWater=unit_priceService.selectUnitPrice(start_t,end_t,"Unit_Price_water");
        if(unitPriceWater==null)  unitPriceWater=0d;
        productMap.put("unit_water",unitPriceWater);
        //天然气单价
        Double unitPriceNaturalGas=unit_priceService.selectUnitPrice(start_t,end_t,"Unit_Price_NaturalGas");
        if(unitPriceNaturalGas==null)  unitPriceNaturalGas=0d;
        productMap.put("unit_naaturalgas",unitPriceNaturalGas);
       //获取综合电单价
        Double unit_power=0d;
        String Applicable_time=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(start_t,"yyyy-MM-dd"),"yyyy-MM");
        ServerResponse response=this.power_price(Applicable_time,"");
        String pathUrl="";
        String msg="报表生成成功";
        if(response.getStatus().equals("SUCCESS")&&StringUtils.isEmpty(response.getMsg())){
            pathUrl=response.getData().toString();
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
        productMap.put("unit_naturalgas",unitPriceNaturalGas);
        //生成后的变动电价的报表的路径
        String reportPath=CREATE_FILE_PATH+pathUrl;
        //获取综合电价的方法
        String unitPricePower= ZongHeDianJiaUtil.getPricePowerByExcel(reportPath);
        if(!StringUtils.isEmpty(unitPricePower)){
            try{
                unit_power=Double.valueOf(unitPricePower);
            }catch (Exception e){
                unit_power=0d;
            }
        }
        productMap.put("unit_power",unit_power);
        /**这个是计算蒸汽单价的报表，是直读锅炉房的蒸汽月度总量，用水量是锅炉房的用水总量，用电量是锅炉房的用电总量，天然气月度用量（或者煤用量）*/
        Map<Integer[],Double> dataMap = new HashMap<>();
        Map<String,Map<Integer, String>> resultMap=setDataMap(start_t, end_t, realMark, modelFileName,dataMap);
        //将产量数据设置到数据源里
        //产量变量的集合
        Map<Integer, String> productMediaMap=resultMap.get("product");
        List<String> procuctTagnameList=new ArrayList<>(productMediaMap.values());
        if(procuctTagnameList.size()>0){
            for(Map.Entry<Integer,String> en:productMediaMap.entrySet()){
                Integer rowNum=en.getKey();
                String tagname=productMediaMap.get(rowNum);
                dataMap.put(new Integer[]{rowNum,11+2},productMap.get(tagname));
            }
        }


        String dateStr = null;
        dateStr=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(start_t,"yyyy-MM-dd"),"MM");
        int month = Integer.parseInt(dateStr);
        String condition="多列";
        //String []columnName=new String[2];
        PoiUtil.setValueToPoi(modelFile,modelFileName,dataMap,"",null,outFileName,"zhengqiDanWeiChengBen",condition,2,month,"");
        return ServerResponse.createBySuccess(msg,"/export/excel/zhengqiDanWeiChengBen/"+outFileName);
    }

    @Override
    public ServerResponse totalCost_pdf(String start_t, String end_t) {
        ServerResponse response=this.totalCost(start_t,end_t);
        if(!response.getStatus().equals("SUCCESS")) return response;
        //生成后的变动电价的报表的路径
        String reportPath=CREATE_FILE_PATH+response.getData();
        String outFileName="";
        try {
            //获取文件名
            int startIndex=reportPath.lastIndexOf("/");
            int endIndex=reportPath.lastIndexOf(".");
            outFileName=reportPath.substring(startIndex+1,endIndex);
            //生成的pdf路径
            String pdf_path=CREATE_pdf_PATH+outFileName+".pdf";
            ExcelUtils.excel2Pdf(reportPath,pdf_path);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("pdf转换失败");
        }
        return ServerResponse.createBySuccess(response.getMsg(),"/export/pdf/"+outFileName+".pdf");
    }

    @Override
    public ServerResponse unitCostSteam_pdf(String start_t, String end_t) {
        ServerResponse response=this.unitCostSteam(start_t,end_t);
        if(!response.getStatus().equals("SUCCESS")) return response;
        //生成后的变动电价的报表的路径
        String reportPath=CREATE_FILE_PATH+response.getData();
        String outFileName="";
        try {
            //获取文件名
            int startIndex=reportPath.lastIndexOf("/");
            int endIndex=reportPath.lastIndexOf(".");
            outFileName=reportPath.substring(startIndex+1,endIndex);
            //生成的pdf路径
            String pdf_path=CREATE_pdf_PATH+outFileName+".pdf";
            ExcelUtils.excel2Pdf(reportPath,pdf_path);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("pdf转换失败");
        }
        return ServerResponse.createBySuccess(response.getMsg(),"/export/pdf/"+outFileName+".pdf");
    }

    //工段能耗报表预览
    @Override
    public ServerResponse energy_consumption_pdf(String start_t, String end_t,String method) {
        ServerResponse response=this.energy_consumption(start_t,end_t,method);
        if(!response.getStatus().equals("SUCCESS")) return response;
        //生成后的变动电价的报表的路径
        String reportPath=CREATE_FILE_PATH+response.getData();
        String outFileName="";
        try {
            //获取文件名
            int startIndex=reportPath.lastIndexOf("/");
            int endIndex=reportPath.lastIndexOf(".");
            outFileName=reportPath.substring(startIndex+1,endIndex);
            //生成的pdf路径
            String pdf_path=CREATE_pdf_PATH+outFileName+".pdf";
            ExcelUtils.excel2Pdf(reportPath,pdf_path);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("pdf转换失败");
        }
        return ServerResponse.createBySuccess(response.getMsg(),"/export/pdf/"+outFileName+".pdf");
    }

    @Override
    public ServerResponse finally_report_pdf(String start_t, String end_t, String type,String method) {
        ServerResponse response=this.finally_report(start_t,end_t,type,method);
        if(!response.getStatus().equals("SUCCESS")) return response;
        //生成后的变动电价的报表的路径
        String reportPath=CREATE_FILE_PATH+response.getData();
        String outFileName="";
        try {
            //获取文件名
            int startIndex=reportPath.lastIndexOf("/");
            int endIndex=reportPath.lastIndexOf(".");
            outFileName=reportPath.substring(startIndex+1,endIndex);
            //生成的pdf路径
            String pdf_path=CREATE_pdf_PATH+outFileName+".pdf";
           ExcelUtils.excel2Pdf(reportPath,pdf_path);
//             ExcelToPdf.excelToPdf(reportPath,pdf_path);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("pdf转换失败");
        }
        return ServerResponse.createBySuccess("/export/pdf/"+outFileName+".pdf");
    }

    @Override
    public ServerResponse powerCost_pdf(String start_t, String end_t) {
        ServerResponse response=this.powerCost(start_t,end_t,"pdf");
        if(!response.getStatus().equals("SUCCESS")) return response;
        //生成后的变动电价的报表的路径
        String reportPath=CREATE_FILE_PATH+response.getData();
        String outFileName="";
        try {
            //获取文件名
            int startIndex=reportPath.lastIndexOf("/");
            int endIndex=reportPath.lastIndexOf(".");
            outFileName=reportPath.substring(startIndex+1,endIndex);
            //生成的pdf路径
            String pdf_path=CREATE_pdf_PATH+outFileName+".pdf";
            ExcelUtils.excel2Pdf(reportPath,pdf_path);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("pdf转换失败");
        }
        return ServerResponse.createBySuccess("/export/pdf/"+outFileName+".pdf");
    }

    //动力总费用
    @Override
    public ServerResponse totalCost(String start_t, String end_t) {
        //end_t=this.addMonth(start_t);
        List<SectionAnalysis> energy_ = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String start_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(start_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
        String end_date=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(end_t,"yyyy-MM-dd"),"yyyy年MM月dd日");
        String timeStamp=DateTimeUtil.dateToStr(new Date(),"yyMMddHHmmss");
        String date=DateTimeUtil.dateToStr(new Date(),"yyyy");
        String outFileName="";
        outFileName="动力总费用报表_"+start_date+"_"+end_date+"_"+timeStamp+".xlsx";
        String modelFileName="";
        File modelFile=new File(PATH_PREFIX+"动力总费用报表"+date+".xlsx");
        if(modelFile.exists()){
            //当前年的模板
            modelFileName=PATH_PREFIX+"动力总费用报表"+date+".xlsx";
        }else{
            //使用空白模板
            modelFileName=PATH_PREFIX+"动力总费用报表.xlsx";
        }
        //获取数据库中的最大时间和最小时间
        String maxDate=sectionAnalysisMapper.selectDate_time("max(his_date)");
        String minDate=sectionAnalysisMapper.selectDate_time("min(his_date)");
        //开始时间比数据库的最大时间大或者结束时间比数据库最小时间小
        if(!this.compareToDate(maxDate,start_t)||this.compareToDate(minDate,end_t)) return ServerResponse.createByErrorMessage("没有该时间段的数据，该系统存储的数据时间范围："+minDate+"至"+maxDate);
        //开始时间比数据库的最小时间小
        if(this.compareToDate(minDate,start_t)) start_t=minDate;
        //结束时间比数据库的最大时间大
        if(!this.compareToDate(maxDate,end_t)) end_t=maxDate;

        //计算水月用量(FIT-092501-01-EQ) 水月用量 =自来水总表每月用量
        ParameterUtil.setTapWaterList();
        Map<String,Double> zongWaterMap=SectionAnalysisService.getConsumptionValue(ParameterUtil.getTapWaterList(),start_t,end_t,"0","day_tapWater","realValue_tapWater_table");
        Double zongWater=0d;
        zongWater=zongWaterMap.get("FIT-092501-01-EQ");
        Double total_fenbiao=0d;
        total_fenbiao=zongWaterMap.get("FIT-092501-02-EQ")+zongWaterMap.get("FIT-092501-03-EQ")+zongWaterMap.get("FIT-092501-05-EQ")
                +zongWaterMap.get("FIT-092501-06-EQ")+zongWaterMap.get("FIT-092501-08-EQ")+zongWaterMap.get("FIT-092501-07-EQ");
        if(total_fenbiao>zongWater) zongWater=total_fenbiao;
        Double unitPriceWater=unit_priceService.selectUnitPrice(start_t,end_t,"Unit_Price_water");
        if(unitPriceWater==null)  unitPriceWater=0d;
        //Double waterCost=zongWater*unitPriceWater;
        //计算电月用量(JT-110900-02-EP、JT-110900-01-EP) 电月用量 = 张三三月用电量 + 华三三月用电量；
        List<String> zongEleList=new ArrayList<>();
        zongEleList.add("JT-110900-02-EP");zongEleList.add("JT-110900-01-EP");
        Map<String,Double> zongEleMap=SectionAnalysisService.getConsumptionValue(zongEleList,start_t,end_t,"0","day_power","realValue_power_table");
        Double zongEle=zongEleMap.get("JT-110900-02-EP")+zongEleMap.get("JT-110900-01-EP");
        if(zongEle==null)   zongEle=0d;
        //电单价
        Double unit_power=0d;
        String Applicable_time=DateTimeUtil.dateToStr(DateTimeUtil.strToDate(start_t,"yyyy-MM-dd"),"yyyy-MM");
        ServerResponse response=this.power_price(Applicable_time,"");
        String pathUrl="";
        if(response.getStatus().equals("SUCCESS")&&StringUtils.isEmpty(response.getMsg())){
            pathUrl=response.getData().toString();
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
        //生成后的变动电价的报表的路径
        String reportPath=CREATE_FILE_PATH+pathUrl;
        //获取综合电价的方法
        String unitPricePower= ZongHeDianJiaUtil.getPricePowerByExcel(reportPath);
        if(!StringUtils.isEmpty(unitPricePower)){
            try{
                unit_power=Double.valueOf(unitPricePower);
            }catch (Exception e){
                unit_power=0d;
            }
        }
        //Double eleCost=zongEle*unitPricePower;
        //计算蒸汽月用量(FIT-100601-03-EQ、FIT-100601-02-EQ  外来) 蒸汽月用量 =  蒸汽总表每月用量
        List<String> zongSteamList=new ArrayList<>();
        zongSteamList.add("FIT-100601-03-EQ");zongSteamList.add("FIT-100601-02-EQ");
        Map<String,Double> zongSteamMap=SectionAnalysisService.getConsumptionValue(zongSteamList,start_t,end_t,"0","day_steamMeter","realValue_steamMeter_table");
        Double zong_steamMeter=zongSteamMap.get("FIT-100601-03-EQ")+zongSteamMap.get("FIT-100601-02-EQ");
        if(zong_steamMeter==null)   zong_steamMeter=0d;
        Double unitPriceSteam=unit_priceService.selectUnitPrice(start_t,end_t,"Unit_Price_steam");
        if(unitPriceSteam==null)  unitPriceSteam=0d;
        //Double steamCost=zong_steamMeter*unitPriceSteam;
        //向excel表中填充值
        String dateStr = null;
        Map<Integer[],Double> dataMap = new HashMap<>();
        try {
            dateStr = sdf.format(simpleDateFormat.parse(start_t));
            int month = Integer.parseInt(dateStr);
            dataMap.put(new Integer[]{2,month},unitPriceWater);
            dataMap.put(new Integer[]{3,month},unit_power);
            dataMap.put(new Integer[]{4,month},unitPriceSteam);
            //dataMap.put(new Integer[]{5,month},unitPriceWater);
            dataMap.put(new Integer[]{5,month},zongWater);
            dataMap.put(new Integer[]{8,month},zongEle);
            //dataMap.put(new Integer[]{7,month},eleCost);
            dataMap.put(new Integer[]{11,month},zong_steamMeter);
            //dataMap.put(new Integer[]{10,month},steamCost);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String condition="多列";
        String []columnName=new String[2];
        PoiUtil.setValueToPoi(modelFile,modelFileName,dataMap,"",columnName,outFileName,"dongLiZongFeiYong",condition,0,null,"");
        return ServerResponse.createBySuccess("/export/excel/dongLiZongFeiYong/"+outFileName);
    }

    //判断数据库查询出来的时间是否在周期时间段内
    public boolean compareToDate(String dateDb, String queryDate){
        Date date_Db=null;
        Date paraDate=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date_Db = sdf.parse(dateDb);
            paraDate = sdf.parse(queryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(paraDate.compareTo(date_Db)<=0){
            return true;
        }else{
            return false;
        }
    }

    //将传入的日期加上一个月
    public String addMonth(String dateDb){
        Date dateParameter=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateParameter = sdf.parse(dateDb);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dateParameter);
        rightNow.add(MONTH,1);
        return sdf.format(rightNow.getTime());
    }

    //将传入的日期加上一年
    public String addYear(String dateDb){
        Date dateParameter=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateParameter = sdf.parse(dateDb);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dateParameter);
        rightNow.add(Calendar.YEAR,1);
        System.out.println(sdf.format(rightNow.getTime()));
        return sdf.format(rightNow.getTime());
    }


}
