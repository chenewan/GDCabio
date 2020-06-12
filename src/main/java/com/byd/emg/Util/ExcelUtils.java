package com.byd.emg.Util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


import java.io.*;

//通过Excel模板自动填充数据，并生成新的excel文件的工具类
public class ExcelUtils {

    private static final Integer EXCEL_TO_PDF_OPERAND =0;

    private static final String RESOURCE_PATH=ExcelUtils.class.getClassLoader().getResource("").getPath();


    public static void excel2Pdf(String inFilePath, String outFilePath) throws Exception {
        ActiveXComponent ax = null;
        Dispatch excel = null;
        File inFile=null;
        try {
            ComThread.InitSTA();
            ax = new ActiveXComponent("Excel.Application");
            ax.setProperty("Visible", new Variant(false));
            ax.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
            Dispatch excels = ax.getProperty("Workbooks").toDispatch();
            inFile=new File(inFilePath);
            Object[] obj = new Object[]{
                    inFilePath,
                    new Variant(false),
                    new Variant(false)
            };
            excel = Dispatch.invoke(excels, "Open", Dispatch.Method, obj, new int[9]).toDispatch();
            Dispatch currentSheet = Dispatch.get((Dispatch) excel,
                    "ActiveSheet").toDispatch();
            Dispatch pageSetup = Dispatch.get(currentSheet, "PageSetup")
                    .toDispatch();
            Dispatch.put(pageSetup, "Zoom", false); // 值为100或false
            Dispatch.put(pageSetup, "FitToPagesWide", 1); // 所有列为一页(1或false)
            //Dispatch.put(pageSetup, "Orientation", new Variant(2));
            // 转换格式
            Object[] obj2 = new Object[]{
                    new Variant(EXCEL_TO_PDF_OPERAND), // PDF格式=0
                    outFilePath,
                    new Variant(0)  //0=标准 (生成的PDF图片不会变模糊) ; 1=最小文件
            };
            Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method,obj2, new int[1]);
        } catch (Exception es) {
            es.printStackTrace();
            throw es;
        } finally {
            if (excel != null) {
                Dispatch.call(excel, "Close", new Variant(false));
            }
            if (ax != null) {
                ax.invoke("Quit", new Variant[] {});
                ax = null;
            }
            if(inFile!=null) inFile.delete();
            ComThread.Release();
        }
    }
}
