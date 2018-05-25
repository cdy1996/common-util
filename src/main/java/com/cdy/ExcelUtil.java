package com.cdy;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * todo
 * Created by 陈东一
 * 2018/5/24 23:14
 */
public class ExcelUtil {
    
    public static List<Map<String, String>> readXlsx(InputStream fileInputStream) throws IOException {
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(fileInputStream);
            Map<String, String> map;
            List<Map<String, String>> list = new ArrayList<>();
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
                        int i = 0;
                        while (iterator.hasNext()) {
                            map.put(getValue(title.getCell(i++)), getValue(iterator.next()));
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
    
    private static List<Map<String, String>> readXls(InputStream inputStream) throws IOException {
        HSSFWorkbook hssfWorkbook = null;
        try {
            hssfWorkbook = new HSSFWorkbook(inputStream);
            Map<String, String> map;
            List<Map<String, String>> list = new ArrayList<>();
            // Read the Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // Read the Row
                HSSFRow title = hssfSheet.getRow(0);
                for (int rowNum = 1; rowNum < hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        map = new HashMap<>();
                        Iterator<Cell> iterator = hssfRow.iterator();
                        int i = 0;
                        while (iterator.hasNext()) {
                            map.put(getValue(title.getCell(i++)), getValue(iterator.next()));
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
                value = NumberUtil.format(cell.getNumericCellValue()).toString();
                break;
            case HSSFCell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                value = cell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                value = cell.getCellFormula() + "";
                break;
            case HSSFCell.CELL_TYPE_BLANK: // 空值
                value = "";
                break;
            case HSSFCell.CELL_TYPE_ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = "未知类型";
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
    
    
    public static List<Map<String, String>> readExcel(InputStream inputStream, String path) throws IOException {
        if (StringUtil.isNotBlank(path)) {
            String ext = getExt(path);
            if (StringUtil.isNotBlank(ext)) {
                if ("xls".equals(ext)) {
                    return readXls(inputStream);
                } else if ("xlsx".equals(ext)) {
                    return readXlsx(inputStream);
                }
            }
        }
        return new ArrayList<>();
    }
}
