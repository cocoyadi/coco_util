package com.coco.util;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangxiaoxun
 * excel工具类
 * @date 2019/3/27  16:11
 **/
public class ExcelUtil {
    private static Logger errorLogger = Logger.getLogger(ExcelUtil.class);

    /**
     * 将list数据写入到excel中，并对excel进行输出
     * @param titleList
     * @param dataLlist
     * @param fileName
     */
    public static void generateExcelFile(List<String> titleList, List<List<String>> dataLlist, String fileName, OutputStream outputStream){
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();
            /**
             * 在Excel工作簿中建一工作表，其名为缺省值
             * 如要新建一名为"效益指标"的工作表，其语句为：
             * HSSFSheet sheet = workbook.createSheet("效益指标");
             */
            HSSFSheet sheet = workbook.createSheet();
            //写入表头
            HSSFRow titleRow = sheet.createRow((short)0);
            for(int i = 0; i < titleList.size(); i ++){
                //在索引i的位置创建单元格（从左上端开始算起）
                HSSFCell cell = titleRow.createCell(i);
                // 定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(titleList.get(i));
            }
            //写入数据　　
            for(int i = 1; i <= dataLlist.size(); i ++){
                HSSFRow dataRow = sheet.createRow(i);
                List<String> data = dataLlist.get(i-1);
                int j = 0;
                for(String s: data){
                    HSSFCell cell = dataRow.createCell(j++);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(s);
                }
            }
            // 把相应的Excel 工作簿存盘 　　
            workbook.write(outputStream);
            outputStream.flush();
            // 操作结束，关闭文件 　　
            outputStream.close();
            System.out.println(fileName + "文件生成...");
        }catch(Exception e){
            errorLogger.error("generateExcelFile error:",e);
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 将list数据写入到excel中
     * @param titleList
     * @param dataLlist
     * @param fileName
     */
    public static void downloadLocalExcelFile(List<String> titleList, List<List<String>> dataLlist, String fileName){
        try{
            // 新建一输出文件流(excel文件命名：2003为xls，2007为xlsx) 　　
            FileOutputStream fOut = new FileOutputStream(fileName + "_" + System.currentTimeMillis() + ".xls");
            generateExcelFile(titleList, dataLlist, fileName, fOut);
        }catch(Exception e){
            errorLogger.error("generateExcelFile error:" ,e);;
        }
    }

    /**
     * 导入excel文件
     * @param path
     * @throws IOException
     */
    public static List<Cell>  importExcelFile(String path) throws IOException, InvalidFormatException {
        if(path != null){

            if(path.endsWith("xlsx")){
                HSSFWorkbook wb = (HSSFWorkbook)WorkbookFactory.create(new File(path));
                return importXlsDoc(wb);
            }else if(path.endsWith("xls")){
                XSSFWorkbook wb = (XSSFWorkbook)WorkbookFactory.create(new File(path));
                return importXlsxDoc(wb);
            }
        }
        return null;
    }


    /**
     * 导入xls文件(97-03)
     * @param hssfWorkbook
     * @throws IOException
     */
    public static List<Cell> importXlsDoc(HSSFWorkbook hssfWorkbook) throws IOException {
        List<Cell> list = new ArrayList<>();
        //默认只取第一张表
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        //循环遍历
        for(int row_num = 0; row_num < (hssfSheet.getLastRowNum()+1);row_num++){
            HSSFRow hssfRow = hssfSheet.getRow(row_num);
            if(hssfRow != null){
                if(hssfRow.getCell(0) != null || !"".equals(hssfRow.getCell(0))){
                    list.add( hssfRow.getCell(0) );
                }
            }
        }
        return list;
    }


    /**
     * 导入xlsx文件(03+)
     * @param xssfWorkbook
     * @throws IOException
     */
    public static  List<Cell> importXlsxDoc(XSSFWorkbook xssfWorkbook) throws IOException {
        List<Cell> list = new ArrayList<>();
        //默认只取第一张表
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        //循环遍历
        for(int row_num = 0; row_num < (xssfSheet.getLastRowNum()+1);row_num++){
            XSSFRow hssfRow = xssfSheet.getRow(row_num);
            list.add( hssfRow.getCell(0) );
        }
        return list;
    }


    public static int getIntCellValue(Cell cell) {
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return Integer
                    .parseInt(new java.text.DecimalFormat("0").format(cell.getNumericCellValue()));
        }

        return -1000;
    }

    public static long getLongCellValue(Cell cell) {
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return Long
                    .parseLong(new java.text.DecimalFormat("0").format(cell.getNumericCellValue()));
        }

        return -1000;
    }

    public static double getNumericCellValue(Cell cell) {
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return cell.getNumericCellValue();
        }

        return 0d;
    }

    public static String getDateCellValue(Cell cell, String format) {

        if (cell != null) {
            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC
                    && DateUtil.isCellDateFormatted(cell)) {
                return CommonUtils.formateDateToStr(cell.getDateCellValue(), format);
            }

            if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
                String val = cell.getStringCellValue();
                Date date = null;
                if(val.length() > 0) {
                    try {
                        date = CommonUtils.parseDateFormStr(val);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if(date!= null) {
                    return CommonUtils.formateDateToStr(date, format);
                }
            }

        }

        return "";
    }

    public static boolean getBooleanCellValue(Cell cell) {
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue();
        }

        return false;
    }

    public static String getStringCellValue(Cell cell) {
        if (cell != null
            // && cell.getCellType() == Cell.CELL_TYPE_STRING
                ) {
            return cell.getStringCellValue();
        }

        return "";
    }

    public static void main(String args[]){
        List<List<String>> list = new ArrayList<List<String>>();
        for(int i=0; i < 10; i++ ){
            List<String>  data = new ArrayList<String>();
            data.add("张小娴");
            data.add("12");
            data.add("女");
            data.add("160");
            list.add(data);
        }
        List<String> titleList = new ArrayList<String >();
        titleList.add("姓名");
        titleList.add("年龄");
        titleList.add("性别");
        titleList.add("身高");
        downloadLocalExcelFile(titleList, list,"file");
    }




}
