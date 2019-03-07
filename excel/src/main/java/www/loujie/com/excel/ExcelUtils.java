package www.loujie.com.excel;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import www.loujie.com.util.ArgsUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ExcelUtils {

    static Workbook createWorkbook(InputStream is) throws IOException, InvalidFormatException {
        if (!is.markSupported()) {
            is = new PushbackInputStream(is, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(is)) {
            return new HSSFWorkbook(is);
        }
        if (DocumentFactoryHelper.hasOOXMLHeader(is)) {
            return new XSSFWorkbook(OPCPackage.open(is));
        }
        return null;
    }

    /**
     * 设置表头的单元格样式
     */
    @SuppressWarnings("deprecation")
    static XSSFCellStyle getHeadStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格的背景颜色为淡蓝色
        cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    /**
     * 设置表体的单元格样式
     */
    @SuppressWarnings("deprecation")
    static XSSFCellStyle getBodyStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        // font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    public static <T> void writeListToExcel(List<T> datas, String[] titles, WriteCallback<T> callback,
                                            OutputStream os) {
        // 创建一个workbook 对应一个excel应用文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 在workbook中增加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = workbook.createSheet(ArgsUtils.formatDate(Calendar.getInstance().getTime(), "yyyyMMdd"));
        XSSFCellStyle headStyle = getHeadStyle(workbook);
        XSSFCellStyle bodyStyle = getBodyStyle(workbook);
        // 构建表头
        XSSFRow headRow = sheet.createRow(0);
        XSSFCell cell = null;
        for (int i = 0; i < titles.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(titles[i]);
        }
        // 构建表体数据
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                XSSFRow bodyRow = sheet.createRow(i + 1);
                T obj = datas.get(i);
                String[] cells = callback.getRowValue(obj);
                if (i == 12) {
                    System.err.println("cells: " + cells.length);
                }
                for (int j = 0; j < titles.length; j++) {
                    cell = bodyRow.createCell(j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(cells[j]);
                }
            }
        }

        try {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取流中的Excel内容到List中
     *
     * @param is
     * @param callback
     * @return
     */
    @SuppressWarnings("deprecation")
    public static <T> List<T> readExcelToList(InputStream is, ReadCallback<T> callback, int maxRow) {
        List<T> returnList = new ArrayList<>();
        try {
            // 1.获取Excel中的文档信息
            Workbook workbook = createWorkbook(is);
            // 2.获取第一页文档信息
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet != null) {
                // 略过第一行
                int columns = sheet.getRow(0).getPhysicalNumberOfCells();
                System.out.println(sheet.getPhysicalNumberOfRows());
                if (sheet.getPhysicalNumberOfRows() > (maxRow + 1)) {
                    throw new ExcelException("数据行数不能大于" + maxRow);
                }
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    // 3.读取行数据
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    // 3.1读取每个单元格的内容到数组中
                    String[] cells = new String[columns];
                    //	System.err.println("columns"+columns+",row.getPhysicalNumberOfCells(): "+row.getPhysicalNumberOfCells());
                    //	for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                    for (int j = 0; j < columns; j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            if (CellType.NUMERIC == cell.getCellTypeEnum() && DateUtil.isCellDateFormatted(cell)) {
                                cells[j] = ArgsUtils.formatDate(cell.getDateCellValue(), "yyyy-MM-dd HH:mm:ss");
                            } else {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                cells[j] = String.valueOf((cell.getStringCellValue()));
                            }
                        }
                    }
                    returnList.add(callback.setRowValue(row.getRowNum() + 1, cells));
                }
            }
        } catch (ExcelException e) {
            throw e;
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
            throw new ExcelException("文档存在问题");
        }
        return returnList;
    }

    public static void main(String[] args) {
        从Excel中读取数据变成List();
        try {
            listToExcel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void listToExcel() throws FileNotFoundException {
        WriteCallback<HashMap<String, Object>> callback = new WriteCallback<HashMap<String, Object>>() {
            @Override
            public String[] getRowValue(HashMap<String, Object> obj) {
                String[] returns = new String[2];
                returns[0] = String.valueOf(obj.get("name"));
                returns[1] = String.valueOf(obj.get("age"));
                return returns;
            }
        };
        List<HashMap<String, Object>> datas = new ArrayList<>();
        HashMap<String, Object> item1 = new HashMap<>();
        item1.put("name", "jiege");
        item1.put("age", "12");
        HashMap<String, Object> item2 = new HashMap<>();
        item2.put("name", "loue");
        item2.put("age", "112");
        datas.add(item1);
        datas.add(item2);
        FileOutputStream fStream = new FileOutputStream(new File("D://data//abc.xls"));
        writeListToExcel(datas, new String[]{"名称", "年龄"}, callback, fStream);
    }

    private static void 从Excel中读取数据变成List() {
        ReadCallback<HashMap<String, Object>> callback = new ReadCallback<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> setRowValue(int rowNO, String[] cells) {
                HashMap<String, Object> returnMap = new HashMap<>();
                returnMap.put("hanghao", rowNO);
                returnMap.put("name", cells[0]);
                returnMap.put("phone", cells[1]);
                returnMap.put("remark", cells[2]);
                return returnMap;
            }
        };
        try {
            List<HashMap<String, Object>> list = readExcelToList(
                    new BufferedInputStream(new FileInputStream(new File("D:\\data\\pbs_talk.xlsx"))), callback, 500);
            // System.err.println(JsonUtils.toJson(list));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}