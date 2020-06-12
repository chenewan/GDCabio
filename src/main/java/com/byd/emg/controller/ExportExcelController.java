package com.byd.emg.controller;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.Util.ExcelWriteOutUtil;
import com.byd.emg.Util.TimeJobDbToExcelUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.service.HistoryRecordService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

//导出excel表格的控制类
@RestController
@RequestMapping("/exportExcel")
public class ExportExcelController {

    @Autowired
    private HistoryRecordService historyRecordService;


    /**
     * @param changing:介质(水、电、汽)变换，默认为”电“
     * @return
     */
    //导出Sampling_period_5min数据
    @RequestMapping("/sampling_period_5minDbToExcel")
    public ServerResponse sampling_period_5minDbToExcel(@RequestParam(value = "cycle") String cycle, //周期
                                                        @RequestParam(value = "startTime") String startTime,
                                                        @RequestParam(value = "endTime") String endTime,
                                                        @RequestParam(value = "startDate") String startDate,
                                                        @RequestParam(value = "endDate") String endDate,
                                                        @RequestParam(value = "type") String type, //类型
                                                        @RequestParam(value = "cabinet_number") List<String> cabinet_number,
                                                        @RequestParam(value = "showValue")String showValue,
                                                        @RequestParam(value = "changing",defaultValue = "电") String changing){
        String excelFileName="";
        String tableName="";
        if(changing.equals("电")){
            excelFileName="查询分析_电表";
            tableName="realValue_power_table";
        }else if(changing.equals("自来水")){
            excelFileName="查询分析_自来水表";
            tableName="realValue_tapWater_table";
        }else if(changing.equals("空气")){
            excelFileName="查询分析_压缩空气表";
            tableName="realValue_fermentationAir_table";
        }else if(changing.equals("蒸汽")){
            excelFileName="查询分析_蒸汽表";
            tableName="realValue_steamMeter_table";
        }else if(changing.equals("循环水")){
            excelFileName="查询分析_循环水表";
            tableName="realValue_circulatingWater_table";
        }else if(changing.equals("冷冻水")){
            excelFileName="查询分析_冷冻水表";
            tableName="realValue_freezeWater_table";
        }
        Map<String, Object> resultMap=historyRecordService.exportExcelData(cabinet_number, type, startTime, endTime, startDate, endDate, cycle,showValue,changing,tableName);
        HSSFWorkbook wb= TimeJobDbToExcelUtil.timeJobDbToExcel(cabinet_number,resultMap,excelFileName);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile=excelFileName+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccess("导出成功","/export/excel/"+outFile);
    }

}
