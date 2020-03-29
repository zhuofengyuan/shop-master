package com.zhuofengyuan.wechat.shop.util;

import com.zhuofengyuan.wechat.shop.entity.ProvinceTask;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 读取上篇中的xls文件的内容，并打印出来
 *
 * @author Administrator
 */
public class ExcelTest {

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
    public static /*List<Map<String, Object>>*/List<ProvinceTask> readTable(String path) throws Exception {
        InputStream ips = new FileInputStream(path);
        XSSFWorkbook wb = new XSSFWorkbook(ips);
        XSSFSheet sheet = wb.getSheetAt(0);
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        var list = new ArrayList<ProvinceTask>();
        for (Iterator ite = sheet.rowIterator(); ite.hasNext(); ) {
            XSSFRow row = (XSSFRow) ite.next();
//            if(!"综合管理中心".equals(row.getCell(0).getRichStringCellValue().toString())){
//                continue;
//            }
            String pro = null;
            if(row.getCell(0) != null){
                pro = row.getCell(0).getRichStringCellValue().toString();
            }
            for(int j = 1; j <= 12; j++){
//                Map<String, Object> rowm = new HashMap<String, Object>();
                var rowm = new ProvinceTask();
                rowm.setDept("商照事业部");
                rowm.setMonth(j);
                rowm.setYear(2020);
                rowm.setAmount(new BigDecimal(row.getCell(j).getNumericCellValue() * 10000));
                rowm.setProvince(pro);
//                rowm.put("dept", "家居事业部");
//                rowm.put("task", row.getCell(j));
//                rowm.put("year", 2020);
//                rowm.put("month", j);
//                rowm.put("province", pro);
                list.add(rowm);
                System.out.println(pro + "-" + row.getCell(j).getNumericCellValue());
            }
//            row.getCell(1).setCellType(CellType.STRING);
//            rowm.put("code", row.getCell(1).getRichStringCellValue().toString());
//            rowm.put("name", row.getCell(2).getRichStringCellValue().toString());
//            if(row.getCell(3).getCellTypeEnum() == CellType.NUMERIC){
//                rowm.put("id", row.getCell(3).getNumericCellValue());
//            } else {
//                rowm.put("id", row.getCell(3).getRichStringCellValue().toString());
//            }
//            row.getCell(4).setCellType(CellType.STRING);
//            rowm.put("d", row.getCell(4).getRichStringCellValue().toString());
//            list.add(rowm);
        }
        return list;
    }

}