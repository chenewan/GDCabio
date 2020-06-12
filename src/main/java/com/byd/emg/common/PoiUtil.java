package com.byd.emg.common;

import com.byd.emg.Util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class PoiUtil {
    private  static final int NUMBERCOLMUN = 20; //核对公式列数
    public static void main(String[] args) throws Exception {
    // 操作03Excel
    // String filePath="D:\\test.xls";
    // 操作07Excel
    String filePath = "D:\\test1.xlsx";
    Map<Integer[], Double> map = new HashMap<Integer[], Double>();
		map.put(new Integer[] { 4, 0 }, new Double(5));

    Map<Integer[], Double> returnMap = new HashMap<Integer[], Double>();
    Integer[] integers = new Integer[] { 5, 0 };
		returnMap.put(integers, null);
		String [] col=null;
        File ff=null;
    //setValueToPoi(ff,filePath, map, "",col,"","","",0);

		System.out.println(returnMap.get(integers));

		//System.out.println(returnStatus.getMsg());
}

    /**
     * @param   filePath   模板路径
     * @param   modelFile      当前年、月模板的文件域
     * @param   map  需要设置值的map 内容格式例如：map.put(new Integer[]{1,1},1.0) //设置第二行，第二列的值为1.0
     * @param   sheetNum   需要设定内容的的sheet页
     * @param   sheetName   sheet页的名称
     * @param   columnName    列名称
     * @param   outFileName      生成的文件名
     * @param   type              报表类型
     * @param   condition
     * @param   sheetNum       数据源所在的sheet页
     * @param month             要更新的列(对应的月分)
     * @return
     */
    public static Workbook setValueToPoi(File modelFile, String filePath, Map<Integer[], Double> map, String sheetName, String[] columnName, String outFileName, String type, String condition, int sheetNum,Integer month,String method) {
        // 验证传入的参数
        if(month!=null){
            month += 1;
        }
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        String[] splits = filePath.split("\\.");
        if (splits.length > 1) {
            if (splits[1].equals("xls")) {
                return setPoi03(filePath, map);
            } else if (splits[1].equals("xlsx")) {
                return setPoi07(modelFile,filePath,map,sheetName,columnName,outFileName,type,condition,sheetNum,month,method);
            }
        } else {
            return null;
        }
        return null;
    }

    public static Workbook setValueToPoi_2(File modelFile,String filePath, Map<Integer[], Double> map,Map<Integer[], Double> testmap,String sheetName,String[] columnName,String outFileName,String type,String condition) {
        // 验证传入的参数
        if (filePath == null && !("").equals(filePath)) {
            return null;
        }
        String[] splits = filePath.split("\\.");
        if (splits.length > 1) {
            if (splits[1].equals("xls")) {
                return setPoi03(filePath, map);
            } else if (splits[1].equals("xlsx")) {
                return setPoi07_2(modelFile,filePath,map,testmap,sheetName,columnName,outFileName,type,condition);
            }
        } else {
            return null;
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    private static Workbook setPoi03(String filePath, Map<Integer[], Double> map) {
        FileOutputStream out = null;
        FileInputStream fs = null;
        POIFSFileSystem ps = null;
        Workbook wb = null;
        Sheet sheet = null;
        try {
            fs = new FileInputStream(filePath); // 获取.xls
            ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
            wb = new HSSFWorkbook(ps);
            out = new FileOutputStream(filePath);
            sheet = wb.getSheetAt(0); // 获取到工作表，因为一个excel可能有多个工作表
            for (Map.Entry<Integer[], Double> en : map.entrySet()) {
                Integer[] integers = en.getKey();
                if (null != integers && integers.length == 2) {
                    Row row = sheet.getRow(integers[0]);// 获取行,角标从0开始
                    Cell cell = row.getCell(integers[1]);// 从行中获取指定列的值
                    cell.setCellValue(en.getValue());// 设置值
                }
            }

            return wb;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                wb.write(out);
                out.close();
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                out = null;
                wb = null;
                fs = null;
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static  Workbook setPoi07(File modelFile,String filePath, Map<Integer[], Double> map,String sheetName,String[] columnName,String outFileName,String type,String condition,int sheetNum,Integer month,String method) {
        FileOutputStream out = null;
        FileOutputStream modelOut = null;
        FileInputStream fs = null;
        File outFile=null;
        Workbook wb = null;
        Sheet sheet = null;
        try {
            fs = new FileInputStream(filePath);
            wb = new XSSFWorkbook(fs);
            String outFilePath="D:/dist/export/excel/"+type;
            //String outFilePath="D:/dist/cabio/export/excel/"+type;
            File fileTemp=new File(outFilePath);
            //文件夹不存在就创建
            if(!fileTemp.exists()){
                fileTemp.mkdirs();
            }
            outFile=new File(outFilePath+"/"+outFileName);
            //文件不存在就创建
            if(!outFile.exists()) {
                outFile.createNewFile();
            }
            out = new FileOutputStream(outFile);
            //更新模板内容
            if(modelFile!=null&&!method.equals("pdf")){
                //文件不存在就创建
                if(!modelFile.exists()) {
                    modelFile.createNewFile();
                }
                if(condition.equals("多列")) modelOut=new FileOutputStream(modelFile);
            }

            sheet = wb.getSheetAt(sheetNum);// 获取到工作表，因为一个excel可能有多个工作表
            setContent(map, sheetName, columnName,wb, sheet,month,method);
            return wb;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(out!=null){
                    out.flush();
                    wb.write(out);
                    out.close();
                }
                if(modelOut!=null&&!method.equals("pdf")){
                    FileInputStream modelIn=new FileInputStream(outFile);
                    XSSFWorkbook wb_2=new XSSFWorkbook(modelIn);
                    modelOut.flush();
                    wb_2.write(modelOut);
                    modelOut.close();
                    modelIn.close();
                }else if(method.equals("pdf")){
                    FileInputStream modelIn=new FileInputStream(outFile);
                    XSSFWorkbook wb_2=new XSSFWorkbook(modelIn);
                    //预览时将其它的sheet隐藏
                    int aa=wb_2.getNumberOfSheets();
                    //将公式所在的列隐藏
                    wb_2.getSheetAt(0).setColumnHidden(15,true);
                    if(aa>1){
                        for(int index=1;index<aa;index++){
                            wb_2.setSheetHidden(index,true);
                        }
                    }
                    FileOutputStream pdfModel=new FileOutputStream(outFile);
                    wb_2.write(pdfModel);
                    pdfModel.close();
                    modelIn.close();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static  Workbook setPoi07_2(File modelFile,String filePath, Map<Integer[], Double> map,Map<Integer[], Double> testmap,String sheetName,String[] columnName,String outFileName,String type,String condition) {
        FileOutputStream out = null;
        FileOutputStream modelOut = null;
        FileInputStream fs = null;
        File outFile=null;
        Workbook wb = null;
        Sheet sheet = null;
        try {
            fs = new FileInputStream(filePath);
            wb = new XSSFWorkbook(fs);
            String outFilePath="D:/dist/export/excel/"+type;
            //String outFilePath="D:/dist/cabio/export/excel/"+type;
            File fileTemp=new File(outFilePath);
            //文件夹不存在就创建
            if(!fileTemp.exists()){
                fileTemp.mkdirs();
            }
            outFile=new File(outFilePath+"/"+outFileName);
            //文件不存在就创建
            if(!outFile.exists()) {
                outFile.createNewFile();
            }
            out = new FileOutputStream(outFile);
            //更新模板内容
            if(modelFile!=null){
                //文件不存在就创建
                if(!modelFile.exists()) {
                    modelFile.createNewFile();
                }
            }
            if(modelFile!=null){
                if(condition.equals("多列")) modelOut=new FileOutputStream(modelFile);
            }
            sheet = wb.getSheetAt(0);// 获取到工作表，因为一个excel可能有多个工作表
           /* setContent(map, sheetName, columnName,wb, sheet,);
            setContent(testmap, sheetName, columnName,wb, wb.getSheetAt(1));*/
            return wb;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(out!=null){
                    out.flush();
                    wb.write(out);
                    out.close();
                }
                if(modelOut!=null){
                    FileInputStream modelIn=new FileInputStream(outFile);
                    XSSFWorkbook wb_2=new XSSFWorkbook(modelIn);
                    modelOut.flush();
                    wb_2.write(modelOut);
                    modelOut.close();
                    modelIn.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setContent(Map<Integer[], Double> map, String sheetName, String[] columnName, Workbook wb, Sheet sheet,Integer month,String method) {

        if (!StringUtils.isEmpty(sheetName)) wb.setSheetName(0, sheetName);
        //设置列标题
        if(columnName!=null){
            Sheet firstSheet=wb.getSheetAt(0);
            if (!StringUtils.isEmpty(columnName[1])) {
                Row row_1 = firstSheet.getRow(0);// 获取行,角标从0开始
                Cell cell_1 = row_1.getCell(0);// 从行中获取指定列的值
                cell_1.setCellValue(columnName[1]);// 设置值
            }
            if (!StringUtils.isEmpty(columnName[0])) {
                Row row_2 = firstSheet.getRow(1);// 获取行,角标从0开始
                Cell cell_2 = row_2.getCell(month);// 从行中获取指定列的值
                cell_2.setCellValue(columnName[0]);// 设置值
            }
        }
        for (Map.Entry<Integer[], Double> en : map.entrySet()) {
            Integer[] integers = en.getKey();
            if (null != integers && integers.length == 2) {
                Row row = sheet.getRow(integers[0]);// 获取行,角标从0开始
                Cell cell = row.getCell(integers[1]);// 从行中获取指定列的值
                System.out.println("====>>" + en.getValue());
                if (cell == null) cell = row.createCell(integers[1]);
                if(en.getValue()==null){
                    cell.setCellFormula("0");// 设置值
                }else{
                    cell.setCellFormula(en.getValue().toString());// 设置值
                }
            }
        }
        //刷新对应月分的宏
        CellStyle cellStyle = wb.createCellStyle();
        //cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //cellStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());// 设置背景色
        cellStyle.setFont(wb.createFont());
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        if(month!=null){
            Sheet sheet1=wb.getSheetAt(0);
            int rows=sheet1.getPhysicalNumberOfRows();
            for(int rowNum=2;rowNum<rows;rowNum++){
                Row row = sheet1.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                //刷新公式所在列的宏   公式所在的列为15
                Cell gongshiCell=refreshMacro(row,NUMBERCOLMUN,wb);
                String setValue="";
                try{
                    setValue=DateTimeUtil.getCellFormatValue(gongshiCell).toString();
                }catch (Exception e){
                    System.out.println("setValue"+setValue);
                    System.out.println("rowNum===>>"+rowNum);
                }

                //设置当前月分的值
                Cell cell = row.getCell(month);// 从行中获取指定列的值
                if (cell == null) cell = row.createCell(month);
                if(!StringUtils.isEmpty(setValue)){
                    String regex="^[+-]?\\d+(\\.\\d+)?$";
                    if(setValue.matches(regex)==true){//说明是数字
                        if(Double.valueOf(setValue)==-1){
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue("0");
                        }else{
                            cell.setCellFormula(setValue);
                        }

                    }
                }
            }
        }
        //刷新所有单元格的宏
        int aa=wb.getNumberOfSheets();
        for(int sheetNum=0;sheetNum<aa;sheetNum++){
            Sheet allSheet=wb.getSheetAt(sheetNum);
            int num=allSheet.getPhysicalNumberOfRows();
            int col=allSheet.getRow(1).getPhysicalNumberOfCells();
            if(sheetNum==2){
                System.out.println("sheet页："+sheetNum+"===>>"+"总行数："+num+"===>>"+"总列数："+col);
            }

            for (int rowNumber = 0; rowNumber < num; rowNumber++) {
                for (int column = 0; column < col; column++) {
                    Row row = allSheet.getRow(rowNumber);// 获取行,角标从0开始
                    if (row == null) {
                        continue;
                    }
                    Cell cell = row.getCell(column);// 从行中获取指定列的值
                    if (cell == null) cell = row.createCell(column);
                    wb.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell);// 刷新宏
                }
            }
        }

    }

    //刷新单个单元格的宏
    public static Cell refreshMacro(Row row,int column,Workbook wb){
        Cell cell = row.getCell(column);// 从行中获取指定列的值
        if (cell == null) cell = row.createCell(column);
        wb.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell);// 刷新宏
        return cell;
    }

    public static void setTax07(String filePath,Map<Integer[], Double> reSetMap){
        FileOutputStream out = null;
        FileInputStream fs = null;
        XSSFWorkbook wb = null;
        XSSFSheet sheet = null;
        try{
            fs = new FileInputStream(filePath);
            wb = new XSSFWorkbook(fs);
            sheet = wb.getSheetAt(0);// 获取到工作表，因为一个excel可能有多个工作表
            for (Map.Entry<Integer[], Double> en : reSetMap.entrySet()) {
                Integer[] integers = en.getKey();
                if (null != integers && integers.length == 2) {
                    XSSFRow row = sheet.getRow(integers[0]);
                    XSSFCell cell = row.getCell(integers[1]);
                    if(cell==null) cell=row.createCell(integers[1]);
                    String formula = cell.getCellFormula();
                    formula = formula.substring(0,formula.indexOf("/")+1);
                    Double d = en.getValue();
                    System.out.println("*************************formula:"+formula);
                    System.out.println("*************************s:"+d);
//                    cell.setCellFormula();
                }
            }
            out = new FileOutputStream(filePath);
        }catch (IOException e) {

            return ;
        } catch (Exception e) {
            e.printStackTrace();
            return ;
        }finally {
            try {
                if(out!=null) out.flush();
                wb.write(out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                out = null;
                wb = null;
            }
        }
    }

    /**  
     * 判断指定的单元格是否是合并单元格  
     * @param sheet   
     * @param row 行下标  
     * @param column 列下标  
     * @return  
     */
    public static boolean isMergedRegion(Sheet sheet,int row,int column){
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i=0;i<sheetMergeCount;i++){
            CellRangeAddress range=sheet.getMergedRegion(i);
            int firstColumn=range.getFirstColumn();
            int lastColumn=range.getLastColumn();
            int firstRow=range.getFirstRow();
            int lastRow=range.getLastRow();
            if(row>=firstRow&&row<=lastRow){
                if(column>=firstColumn&&column<=lastColumn){
                    return true;
                }
            }
    }
    return false;
    }

    /**   
     * 获取合并单元格的值   
     * @param sheet   
     * @param row   
     * @param column   
     * @return   
     */
    public static String getMergedRegionValue(Sheet sheet,int row,int column){
        int sheetMergeCount=sheet.getNumMergedRegions();
        for(int i=0;i<sheetMergeCount;i++){
            CellRangeAddress ca=sheet.getMergedRegion(i);
            int firstColumn=ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }
        return null ;
    }

    /**   
     * 获取单元格的值   
     * @param cell   
     * @return   
     */
    public static String getCellValue(Cell cell){
        if(cell == null) return "";
        if(cell.getCellType() == Cell.CELL_TYPE_STRING){
            return cell.getStringCellValue();
        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
            return cell.getCellFormula();
        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

}
