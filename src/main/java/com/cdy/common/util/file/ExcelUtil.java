package com.cdy.common.util.file;

import com.cdy.common.util.field.NumberUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.joining;

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
     * @throws InvalidFormatException
     */
    public static List<Map<String, Object>> read(String path) throws IOException, InvalidFormatException {
        return read(FileUtils.openInputStream(new File(path)));
    }
    
    
    /**
     * 读取excel
     *
     * @param inputStream 文件输入流
     * @return List list对应列，map对应单元格，key是title，value是值
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<Map<String, Object>> read(FileInputStream inputStream) throws IOException, InvalidFormatException {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            return doRead(workbook);
        } finally {
            workbook.close();
            inputStream.close();
        }
    }
    
    private static List<Map<String, Object>> doRead(Workbook workbook) {
        Map<String, Object> map;
        Row title;
        int i;
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
            case Cell.CELL_TYPE_NUMERIC: // 数字
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = com.cdy.common.util.field.DateUtil.toString(cell.getDateCellValue()); //日期型
                } else {
                    value = NumberUtil.format(cell.getNumericCellValue()).toString(); //数字型
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                try {
                    value = String.valueOf(cell.getStringCellValue());
                } catch (IllegalStateException e) {
                    value = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                value = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = cell.getRichStringCellValue() == null ? null : cell.getRichStringCellValue().toString();
                break;
        }
        return value;
    }
    
    private static void testRead() throws IOException, InvalidFormatException {
        List<Map<String, Object>> maps = read("D:\\迅雷下载\\我的文件\\大学\\陈东一校友卡申请信息.xls");
        print(maps);
    }
    
    private static void print(List<Map<String, Object>> maps){
        maps.forEach(e -> {
            Set<Map.Entry<String, Object>> entries = e.entrySet();
            System.out.println(entries.stream().map(Object::toString).collect(joining(",")));
        });
    }
    
    public static void main(String[] args) throws IOException, InvalidFormatException {
        
        Workbook workbook = testWrite();
        List<Map<String, Object>> list = doRead(workbook);
        print(list);
        workbook.close();
//        testRead();
    }
    
    
    private static Workbook testWrite() throws IOException {
        String path = "C:\\Users\\cdy1996\\Desktop\\testWrite.xls";
        String[] title = {"1", "2", "3"};
        Map<String, Object> map = new HashMap<>();
        map.put("1", "111");
        map.put("2", "222");
        map.put("3", "333");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        return write(title, list, true);
    
    }
    
    
    private static Workbook write(String[] title, List<Map<String, Object>> list, boolean isXls) {
        Workbook wb = null;
        if (isXls) {  //Excel 2003
            wb = new HSSFWorkbook();
        } else {  // Excel 2007/2010
            wb = new XSSFWorkbook();
        }
        return doWrite(title, list, wb);
    }
    

    
    /**
     * 用于写入excel文件
     *
     * @param title  标题
     * @param list   内容
     * @param path   文件路径
     * @throws IOException
     */
    public static void write(String[] title, List<Map<String, Object>> list, String path)
            throws IOException {
        Workbook wb;
        if (EXCEL_XLS.equalsIgnoreCase(getFileExt(path))) {  //Excel 2003
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }
        doWrite(title, list, wb);
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
        try(FileOutputStream outputStream = FileUtils.openOutputStream(file)) {
            wb.write(outputStream);
        }
    }
    
    private static String getFileExt(String path) {
        if (path == null || "".equals(path) || !path.contains(".")) {
            throw new RuntimeException("The file is not excel.");
        } else {
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
    }
    
    private static Workbook doWrite(String[] title, List<Map<String, Object>> list, Workbook workBook) {
        if (workBook.getNumberOfSheets() == 0) {
            workBook.createSheet();
        }
        Sheet sheet = workBook.getSheetAt(0);
        
        // 写入title
        Row row;
        row = sheet.createRow(sheet.getLastRowNum());
        for (int i = 0; i < title.length; i++) {
            row.createCell(i).setCellValue(title[i]);
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
        return workBook;
    }
}
