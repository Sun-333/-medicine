package Util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtil {
	public static List<Map<String, Object>> readExcelFileToDB(File file, String sheetName) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {

			FileInputStream fis = new FileInputStream(file);

			POIFSFileSystem fs = new POIFSFileSystem(fis);

			HSSFWorkbook workbook = new HSSFWorkbook(fs);// 创建Excel工作簿对象

			// HSSFSheet sheet = workbook.getSheetAt(0);//获取第1个工作表
			HSSFSheet sheet = workbook.getSheet(sheetName);

			HSSFRow row_tableNames = sheet.getRow(0);

			int number = row_tableNames.getPhysicalNumberOfCells();
			System.out.println("列" + number);
			String[] tableNames = new String[number];
			for (int i = 0; i < number; i++) {
				tableNames[i] = row_tableNames.getCell(i).getStringCellValue().trim();
			}
			System.out.println(Arrays.asList(tableNames).toString());
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {// 循环Excel文件的每一行
				System.out.println(sheet.getLastRowNum() + "行数");
				HSSFRow row = sheet.getRow(i);// 获取第i行

				Map<String, Object> map = new HashMap<>();
				for (int j = 0; j < number; j++) {
					if (row.getCell(j) != null) {
						// row.getCell(j).setCellType(HSSFCell.CELL_TYPE_STRING);

						int cellType = row.getCell(j).getCellType();

						if (cellType == HSSFCell.CELL_TYPE_STRING) {

							map.put(tableNames[j], row.getCell(j).getStringCellValue());

						} else if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {

							if (HSSFDateUtil.isCellDateFormatted(row.getCell(j))) {
								map.put(tableNames[j], row.getCell(j).getDateCellValue());

							} else {
								double date = row.getCell(j).getNumericCellValue();
								
								map.put(tableNames[j], String.valueOf(new Double(date).intValue()));
							}
						}
					}

					/*
					 * if(cellType==HSSFCell.CELL_TYPE_STRING) {
					 * map.put(tableNames[j],row.getCell(j).getStringCellValue()); }else
					 * if(cellType==HSSFCell.CELL_TYPE_NUMERIC) {
					 * if(HSSFDateUtil.isCellDateFormatted(row.getCell(j))) { map.put(tableNames[j],
					 * row.getCell(j).getDateCellValue()); } else {
					 * 
					 * row.getCell(j).setCellType(HSSFCell.CELL_TYPE_STRING); map.put(tableNames[j],
					 * row.getCell(j).getStringCellValue().trim()); } }
					 */

				}

				list.add(map);
			}

			fis.close();
			System.out.println(list.toString());
			System.out.println("现在表格数据");
			return list;

		} catch (

		Exception e) {

			e.printStackTrace();

			return null;

		}

	}

}
