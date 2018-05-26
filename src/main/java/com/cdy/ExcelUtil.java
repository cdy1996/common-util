package com.cdy;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * todo
 * Created by 陈东一
 * 2018/5/24 23:14
 */
public class ExcelUtil {
    
    public static List<Map<String, Object>> readXlsx(InputStream fileInputStream) throws IOException {
        XSSFWorkbook xssfWorkbook = null;
        try {
            int row;
            xssfWorkbook = new XSSFWorkbook(fileInputStream);
            Map<String, Object> map;
            List<Map<String, Object>> list = new ArrayList<>();
            // Read the Sheet
            for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                // Read the Row
                XSSFRow title = xssfSheet.getRow(0);
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow != null) {
                        map = new HashMap<>();
                        Iterator<Cell> iterator = xssfRow.iterator();
                        row = 0;
                        while (iterator.hasNext()) {
                            map.put(getValue(title.getCell(row++)), getValue(iterator.next()));
                        }
                        list.add(map);
                    }
                }
            }
            return list;
        } finally {
            fileInputStream.close();
            if (xssfWorkbook != null) {
                xssfWorkbook.close();//最后记得关闭工作簿
            }
        }
        
    }
    
    private static List<Map<String, Object>> readXls(InputStream inputStream) throws IOException {
        HSSFWorkbook hssfWorkbook = null;
        try {
            hssfWorkbook = new HSSFWorkbook(inputStream);
            int row;
            HSSFRow title;
            HSSFRow hssfRow;
            Map<String, Object> map;
            List<Map<String, Object>> list = new ArrayList<>();
            // Read the Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                if (hssfSheet.getLastRowNum() <= 0) {
                    continue;
                }
                // Read the Row
                title = hssfSheet.getRow(0);
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        map = new HashMap<>();
                        Iterator<Cell> iterator = hssfRow.iterator();
                        row = 0;
                        while (iterator.hasNext()) {
                            map.put(getValue(title.getCell(row++)), getValue(iterator.next()));
                        }
                        list.add(map);
                    }
                }
            }
            return list;
        } finally {
            inputStream.close();
            if (hssfWorkbook != null) {
                hssfWorkbook.close();
            }
        }
    }
    
    private static String getValue(Cell cell) {
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
    
    /**
     * 获取文件扩展名
     *
     * @param path
     * @return String
     * @author zhang 2015-08-17 23:26
     */
    private static String getExt(String path) {
        if (path == null || "".equals(path) || !path.contains(".")) {
            return null;
        } else {
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
    }
    
    
    public static List<Map<String, Object>> readExcel(String path) throws IOException {
        if (StringUtil.isNotBlank(path)) {
            String ext = getExt(path);
            FileInputStream inputStream = FileUtil.openFileInputStream(path);
            if (StringUtil.isNotBlank(ext)) {
                if ("xls".equals(ext)) {
                    return readXls(inputStream);
                } else if ("xlsx".equals(ext)) {
                    return readXlsx(inputStream);
                } else {
                    return new ArrayList<>();
                }
            }
        }
        return new ArrayList<>();
    }
    
    
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    
    
    public static void deleteRow(String path) throws IOException {
        File file = new File(path);
        Workbook workBook = null;
        if (path.endsWith(EXCEL_XLS)) {  //Excel 2003
            workBook = new HSSFWorkbook(FileUtil.openFileInputStream(file));
        } else if (path.endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
            workBook = new XSSFWorkbook(FileUtil.openFileInputStream(file));
        }
        if (workBook.getNumberOfSheets() != 0) {
            Sheet sheet = workBook.getSheetAt(0);
            System.out.println("原始数据总行数，除属性列：" + sheet.getLastRowNum());
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i) == null) {
                    break;
                }
                sheet.removeRow(sheet.getRow(i));
            }
        }
        // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
        try (FileOutputStream out = new FileOutputStream(path)) {
            workBook.write(out);
        }
    }
    
    
    public static void writeExcel(String[] title, List<Map<String, Object>> list, String path) throws IOException {
        if (title.length == 0 || list == null) {
            return;
        }
        Workbook workBook = getWorkbook(path);
        if (workBook.getNumberOfSheets() == 0) {
            workBook.createSheet();
        }
        Sheet sheet = workBook.getSheetAt(0);
        // 写入title
        Row row = sheet.createRow(sheet.getLastRowNum());
        for (int i = 0; i < title.length; i++) {
            row.createCell(i).setCellValue(title[i]);
        }
        Map<String, Object> map;
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(++lastRowNum);
            map = list.get(i);
            for (int j = 0; j < title.length; j++) {
                row.createCell(j).setCellValue(map.get(title[j]).toString());
            }
        }
        try (OutputStream out = new FileOutputStream(path)) {
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            workBook.write(out);
        }
    }
    
    public static void writeExcelContinue(String[] title, List<Map<String, Object>> list, String path) throws IOException {
        if (title.length == 0 || list == null) {
            return;
        }
        Workbook workBook = getWorkbookContinue(path);
        if (workBook.getNumberOfSheets() == 0) {
            workBook.createSheet();
        }
        Sheet sheet = workBook.getSheetAt(0);
        // 写入title
        Row row;
        Map<String, Object> map;
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(++lastRowNum);
            map = list.get(i);
            for (int j = 0; j < title.length; j++) {
                row.createCell(j).setCellValue(map.get(title[j]).toString());
            }
        }
        try (OutputStream out = new FileOutputStream(path)) {
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            workBook.write(out);
        }
    }
    
    /**
     * 判断Excel的版本,获取Workbook
     */
    public static Workbook getWorkbook(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        Workbook wb = null;
        if (path.endsWith(EXCEL_XLS)) {  //Excel 2003
            wb = new HSSFWorkbook();
        } else if (path.endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
            wb = new XSSFWorkbook();
        }
        return wb;
    }
    
    public static Workbook getWorkbookContinue(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            throw new RuntimeException();
        }
        Workbook wb = null;
        if (path.endsWith(EXCEL_XLS)) {  //Excel 2003
            wb = new HSSFWorkbook(FileUtil.openFileInputStream(file));
        } else if (path.endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
            wb = new XSSFWorkbook(FileUtil.openFileInputStream(file));
        }
        return wb;
    }
    
    public static void main(String[] args) throws IOException {
//        testRead();
        testWrite();
    }
    
    private static void testRead() throws IOException {
        List<Map<String, Object>> maps = readExcel("C:\\Users\\cdy1996\\Desktop\\陈东一校友卡申请信息.xls");
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
        writeExcelContinue(title, list, path);
        
    }
}
