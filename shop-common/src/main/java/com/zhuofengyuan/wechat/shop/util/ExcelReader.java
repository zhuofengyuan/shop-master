package com.zhuofengyuan.wechat.shop.util;

import com.zhuofengyuan.wechat.shop.entity.ProductCategory;
import com.zhuofengyuan.wechat.shop.entity.ProvinceTask;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 读取上篇中的xls文件的内容，并打印出来
 *
 * @author Administrator
 */
public class ExcelReader {

    /**
     * 读取一个excel文件的内容
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //extractor();
        readTable("F:\\fengtoos\\powerbi\\2020\\template.xlsx");
    }


    //通过对单元格遍历的形式来获取信息 ，这里要判断单元格的类型才可以取出值
    public static /*List<Map<String, Object>>*/List<ProductCategory> readTable(String path) throws Exception {
        InputStream ips = new FileInputStream(path);
        XSSFWorkbook wb = new XSSFWorkbook(ips);
        XSSFSheet sheet = wb.getSheetAt(0);
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        var list = new ArrayList<ProductCategory>();
        for (Iterator ite = sheet.rowIterator(); ite.hasNext(); ) {
            XSSFRow row = (XSSFRow) ite.next();
            ProductCategory rowm = new ProductCategory();
            if(row.getCell(0) != null){
                row.getCell(0).setCellType(CellType.STRING);
                rowm.setNumber(row.getCell(0).getRichStringCellValue().toString());
            }
            if(row.getCell(1) != null){
                row.getCell(1).setCellType(CellType.STRING);
                rowm.setName(row.getCell(1).getRichStringCellValue().toString());
            }
            if(row.getCell(2) != null){
                row.getCell(2).setCellType(CellType.STRING);
                rowm.setDescription(row.getCell(2).getRichStringCellValue().toString());
            }
            if(row.getCell(3) != null){
                row.getCell(3).setCellType(CellType.STRING);
                rowm.setParentName(row.getCell(3).getRichStringCellValue().toString());
            }
            if(row.getCell(4) != null){
                row.getCell(4).setCellType(CellType.STRING);
                rowm.setLevel(Integer.parseInt(row.getCell(4).getRichStringCellValue().toString()));
            }
            if(row.getCell(5) != null){
                row.getCell(5).setCellType(CellType.STRING);
                rowm.setParent(row.getCell(5).getRichStringCellValue().toString());
            }
            System.out.println(row.getCell(0) + "|" + row.getCell(1) + "|" + row.getCell(2) + "|" +
                    row.getCell(3) + "|" + row.getCell(4));
            list.add(rowm);
        }
        return list;
    }

}