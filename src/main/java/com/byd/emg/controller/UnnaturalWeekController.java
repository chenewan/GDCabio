package com.byd.emg.controller;

import com.byd.emg.Util.UnnaturalWeekDbToExcel;
import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.Util.ExcelWriteOutUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.UnnaturalWeek;
import com.byd.emg.service.UnnaturalWeekService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/unnaturalWeek")
public class UnnaturalWeekController {

    @Autowired
    private UnnaturalWeekService iUnnaturalWeek;

    //查询非自然周数据的方法
    @RequestMapping("/selectByTime")
    public ServerResponse selectByTime(@RequestParam(value="Start_time_1",defaultValue ="") String Start_time_1,
                                       @RequestParam(value="End_time_1",defaultValue ="") String End_time_1){
        List<UnnaturalWeek> unnaturalWeekList=iUnnaturalWeek.selectByTime(Start_time_1,End_time_1);
        if (unnaturalWeekList.size() > 0) {
            return ServerResponse.createBySuccess("查询成功", unnaturalWeekList);
        }
        return ServerResponse.createByErrorMessage("查询失败");
    }

    //导出Excel
    @RequestMapping("/unnaturalDbToExcel")
    public ServerResponse unnaturalDbToExcel(@RequestParam(value="Start_time_1",defaultValue ="") String Start_time_1,
                                             @RequestParam(value="End_time_1",defaultValue ="") String End_time_1){
        List<UnnaturalWeek> unnaturalWeekList=iUnnaturalWeek.selectByTime(Start_time_1,End_time_1);
        String[] headers={"序号","产品类型","用水量","用电量","用气","时间"};
        HSSFWorkbook wb= UnnaturalWeekDbToExcel.bugrecordsToExcel(headers,unnaturalWeekList);
        String timeStamp= DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        String outFile="非自然周报表"+timeStamp+".xls";
        ExcelWriteOutUtil.excelToOutputstrame(wb,outFile);
        return ServerResponse.createBySuccessMessage("/export/excel/feiZiRanZhou/"+outFile);
    }

}
