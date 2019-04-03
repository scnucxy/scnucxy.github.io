package com.ycyl.TestFTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**excel读写工具类
 * @author ChenXiaoYu
 * @date 2018年5月21日 上午10:15:02
 */
public class POIUtil {

        private static Logger logger  = Logger.getLogger(POIUtil.class);
        private final static String xls = "xls";
        private final static String xlsx = "xlsx";

        /**
         * 读入excel文件，解析后返回
         * @param file
         * @throws IOException
         */
        public static List<String[]> readExcel(File file) throws IOException{
            //检查文件
            checkFile(file);
            //获得Workbook工作薄对象
            Workbook workbook = getWorkBook(file);
            //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
            List<String[]> list = new ArrayList<String[]>();
            if(workbook != null){
                for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                	//为了只取得第一个工作表
                	
                    //获得当前sheet工作表
                    Sheet sheet = workbook.getSheetAt(sheetNum);
                    if(sheet == null){
                        continue;
                    }
                    //获得当前sheet的开始行
                    int firstRowNum  = sheet.getFirstRowNum();
                    //获得当前sheet的结束行
                    int lastRowNum = sheet.getLastRowNum();
                    //循环除了第一行的所有行
                    for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                        //获得当前行
                    	System.out.println("解析第"+rowNum+"行");
                        Row row = sheet.getRow(rowNum);
                        if(row == null){
                            continue;
                        }
                        //获得当前行的开始列
                        int firstCellNum = row.getFirstCellNum();
                        //获得当前行的列数
                        int lastCellNum = row.getPhysicalNumberOfCells();
                        
                        String[] cells = new String[lastCellNum];
                        //循环当前行
                        for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                            Cell cell = row.getCell(cellNum);
                            cells[cellNum] = getCellValue(cell);
                        }
                        list.add(cells);
                    }
                }
//                workbook.close();
            }
            return list;
        }
        
        
        /**判断文件是否存在和文件类型是否正确
         * @param file
         * @throws IOException
         */
        public static void checkFile(File file) throws IOException{
            //判断文件是否存在
            if(null == file){
                logger.error("文件不存在！");
                throw new FileNotFoundException("文件不存在！");
            }
            //获得文件名
            String fileName = file.getName();
            //判断文件是否是excel文件
            if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
                logger.error(fileName + "不是excel文件");
                throw new IOException(fileName + "不是excel文件");
            }
        }
        
        
        
        
        /**获取workbook对象
         * @param file
         * @return
         */
        public static Workbook getWorkBook(File file) {
            //获得文件名
            String fileName = file.getName();
            //创建Workbook工作薄对象，表示整个excel
            Workbook workbook = null;
            try {
                //获取excel文件的io流
                InputStream is = new FileInputStream(file);
                //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
                if(fileName.endsWith(xls)){
                    //2003
                    workbook = new HSSFWorkbook(is);
                }else if(fileName.endsWith(xlsx)){
                    //2007 及2007以上
                    workbook = new XSSFWorkbook(is);
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
            return workbook;
        }
        public static String getCellValue(Cell cell){
            String cellValue = "";
            if(cell == null){
                return cellValue;
            }
            //把数字当成String来读，避免出现1读成1.0的情况
            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            //判断数据的类型
            switch (cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC: //数字
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING: //字符串
                    cellValue = String.valueOf(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN: //Boolean
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA: //公式
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_BLANK: //空值
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR: //故障
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
                    break;
            }
            return cellValue;
        }
        
        
        
        
        /** 
         * 设置某些列的值只能输入预制的数据,显示下拉框. 
         * @param sheet 要设置的sheet. 
         * @param textlist 下拉框显示的内容 
         * @param firstRow 开始行 
         * @param endRow 结束行 
         * @param firstCol   开始列 
         * @param endCol  结束列 
         * @return 设置好的sheet. 
         */  
        public static HSSFSheet setHSSFValidation(HSSFSheet sheet,  
                String[] textlist, int firstRow, int endRow, int firstCol,  
                int endCol) {  
            // 加载下拉列表内容  
            DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);  
            // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列  
            CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow, firstCol, endCol);  
            // 数据有效性对象  
            HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);  
            sheet.addValidationData(data_validation_list);  
            return sheet;  
        }  
      
        
        
        /** 
         * 设置单元格上提示 
         * @param sheet  要设置的sheet. 
         * @param promptTitle 标题 
         * @param promptContent 内容 
         * @param firstRow 开始行 
         * @param endRow  结束行 
         * @param firstCol  开始列 
         * @param endCol  结束列 
         * @return 设置好的sheet. 
         */  
        public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,  
                String promptContent, int firstRow, int endRow ,int firstCol,int endCol) {  
            // 构造constraint对象  
            DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("BB1");  
            // 四个参数分别是：起始行、终止行、起始列、终止列  
            CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow,firstCol, endCol);  
            // 数据有效性对象  
            HSSFDataValidation data_validation_view = new HSSFDataValidation(regions,constraint);  
            data_validation_view.createPromptBox(promptTitle, promptContent);  
            sheet.addValidationData(data_validation_view);  
            return sheet;  
        }  
    }
