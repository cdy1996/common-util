package com.cdy;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * excel工具类
 * Created by 陈东一
 * 2018/5/24 23:14
 */
public class ExcelUtil {
    
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    
    /**
     * 读取excel
     *
     * @param path String
     * @return List list对应列，map对应单元格，key是title，value是值
     * @throws IOException
     */
    public static List<Map<String, Object>> readExcel(String path) throws IOException {
        Workbook workbook = null;
        FileInputStream inputStream = null;
        if (StringUtil.isNotBlank(path)) {
            String ext = getFileExt(path);
            if (StringUtil.isNotBlank(ext)) {
                return new ArrayList<>();
            }
            try {
                inputStream = FileUtil.openFileInputStream(path);
                if (EXCEL_XLS.equalsIgnoreCase(ext)) {
                    workbook = new HSSFWorkbook(inputStream);
                    return read(workbook);
                } else {
                    workbook = new XSSFWorkbook(inputStream);
                    return read(workbook);
                }
            } finally {
                if (workbook != null) {
                    workbook.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        return new ArrayList<>();
    }
    
    
    /**
     * 读取excel
     *
     * @param inputStream 文件输入流
     * @param isXLS       是否是xls类型
     * @return List list对应列，map对应单元格，key是title，value是值
     * @throws IOException
     */
    public static List<Map<String, Object>> readExcel(FileInputStream inputStream, boolean isXLS) throws IOException {
        Workbook workbook = null;
        try {
            if (isXLS) {
                workbook = new HSSFWorkbook(inputStream);
                return read(workbook);
            } else {
                workbook = new XSSFWorkbook(inputStream);
                return read(workbook);
            }
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            inputStream.close();
        }
    }
    
    
    /**
     * 用于写入excel文件
     *
     * @param title
     * @param list
     * @param path
     * @param isNew
     * @throws IOException
     */
    public static void writeExcel(String[] title, List<Map<String, Object>> list, String path, Boolean isNew) throws IOException {
        write(title, list, path, isNew);
    }
    
    /**
     * 用于导出excel文件
     *
     * @param title
     * @param list
     * @param outputStream
     * @param isXls
     * @throws IOException
     */
    public static void writeExcel(String[] title, List<Map<String, Object>> list, OutputStream outputStream, Boolean isXls) throws IOException {
        write(title, list, outputStream, true, isXls, null);
    }
    
    
    /**
     * 从头写入excel
     *
     * @param title String[]
     * @param list  List<Map<String, Object>> list对应列，map对应单元格，key是title，value是值
     */
    public static void write(String[] title, List<Map<String, Object>> list, Workbook workBook, boolean isNew) {
        if (workBook.getNumberOfSheets() == 0) {
            workBook.createSheet();
        }
        Sheet sheet = workBook.getSheetAt(0);
        
        // 写入title
        Row row;
        if (isNew) {
            row = sheet.createRow(sheet.getLastRowNum());
            for (int i = 0; i < title.length; i++) {
                row.createCell(i).setCellValue(title[i]);
            }
        }
        
        Map<String, Object> map;
        int lastRowNum = sheet.getLastRowNum();
        for (Map<String, Object> aList : list) {
            row = sheet.createRow(++lastRowNum);
            map = aList;
            for (int j = 0; j < title.length; j++) {
                row.createCell(j).setCellValue(map.get(title[j]).toString());
            }
        }
        
    }
    
    
    public static void main(String[] args) throws IOException {
//        testRead();
        testWrite();
    }
    
    private static void testRead() throws IOException {
        List<Map<String, Object>> maps = readExcel("D:\\迅雷下载\\我的文件\\大学\\陈东一校友卡申请信息.xls");
        maps.forEach(e -> {
            Set<Map.Entry<String, Object>> entries = e.entrySet();
            entries.forEach(System.out::println);
        });
    }
    
    private static void testWrite() throws IOException {
        String path = "C:\\Users\\cdy1996\\Desktop\\testWrite.xls";
        String[] title = {"1", "2", "3"};
        Map<String, Object> map = new HashMap<>();
        map.put("1", "111");
        map.put("2", "222");
        map.put("3", "333");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        writeExcel(title, list, path, true);
        
    }
    
    private static List<Map<String, Object>> read(Workbook workbook) throws IOException {
        Map<String, Object> map;
        Row title;
        int i = 0;
        List<Map<String, Object>> list = new ArrayList<>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            // Read the Row
            title = sheet.getRow(0);
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row != null) {
                    map = new HashMap<>();
                    Iterator<Cell> iterator = row.iterator();
                    i = 0;
                    while (iterator.hasNext()) {
                        map.put(getCellValue(title.getCell(i++)), getCellValue(iterator.next()));
                    }
                    list.add(map);
                }
            }
        }
        return list;
    }
    
    
    private static String getCellValue(Cell cell) {
        String value = null;
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                if (DateUtil.isCellDateFormatted(cell)) {
                    try {
                        value = com.cdy.DateUtil.format(cell.getDateCellValue()); //日期型
                    } catch (ParseException e) {
                        value = cell.getDateCellValue().toString();
                    }
                } else {
                    value = NumberUtil.format(cell.getNumericCellValue(), 0).toString(); //数字型
                }
                break;
            case HSSFCell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                try {
                    value = String.valueOf(cell.getStringCellValue());
                } catch (IllegalStateException e) {
                    value = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_BLANK: // 空值
                value = "";
                break;
            case HSSFCell.CELL_TYPE_ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = cell.getRichStringCellValue() == null ? null : cell.getRichStringCellValue().toString();
                break;
        }
        return value;
    }
    

    private static String getFileExt(String path) {
        if (path == null || "".equals(path) || !path.contains(".")) {
            throw new RuntimeException("The file is not excel.");
        } else {
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
    }
    
    private static void write(String[] title, List<Map<String, Object>> list, String path, boolean isNew) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            throw new RuntimeException("The file is exist.");
        }
        try (FileOutputStream out = new FileOutputStream(path)) {
            write(title, list, out, EXCEL_XLS.equalsIgnoreCase(getFileExt(path)), isNew, FileUtil.openFileInputStream(file));
        }
    }
    
    private static void write(String[] title, List<Map<String, Object>> list, OutputStream out, boolean isXls, boolean isNew, InputStream inputStream)
            throws IOException {
        Workbook wb = null;
        try {
            if (isNew) {
                if (isXls) {  //Excel 2003
                    wb = new HSSFWorkbook();
                } else {  // Excel 2007/2010
                    wb = new XSSFWorkbook();
                }
            } else {
                if (isXls) {  //Excel 2003
                    wb = new HSSFWorkbook(inputStream);
                } else {  // Excel 2007/2010
                    wb = new XSSFWorkbook(inputStream);
                }
            }
            write(title, list, wb, isNew);
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            wb.write(out);
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
    }
}
