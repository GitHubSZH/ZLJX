package com.zljx.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserReadExcelImpl{

	private static final String filePath = "D:\\hb\\介入放射学.xls";

	private static final String classPath = "com.zljx.pojo.CTAndMRI";


	public static List<Map<String, Object>> Bh() {
		try {
			List<Map<String, Object>> maps = readExecl(null);
			return maps;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static InputStream getInputStream(String filePath, MultipartFile file) throws IOException {
		InputStream is = null;
		if (file != null) {
			is = file.getInputStream();
		} else {
			is = new FileInputStream(filePath);
		}
		return is;
	}

	public static Workbook getWorkBook(InputStream is) throws IOException {
		Workbook wb = null;
		String suffixName = filePath.substring(filePath.indexOf("."));
		if (".xlsx".equals(suffixName)) {
			wb = new XSSFWorkbook(is);
		} else if (".xls".equals(suffixName)) {
			wb = new HSSFWorkbook(is);
		} else {
			return null;
		}
		return wb;
	}

	public static List<String> setMapKey() throws ClassNotFoundException {
		List<String> item = new ArrayList<>();
		Class<?> userClass = Class.forName(classPath);
		Field[] m = userClass.getDeclaredFields();
		for (int i = 0; i < m.length; i++) {
			Field method = m[i];
			/*if (i != 0) {
			}
			*/item.add(method.getName());
		}
//		Comparator<String> comparator = Collections.reverseOrder();
//		Collections.sort(item, comparator);
		return item;
	}

	public static List<Map<String, Object>> readExecl( MultipartFile file) throws IOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> keyList = null;
		try {
			keyList = setMapKey();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream is = getInputStream(filePath, file);
		Workbook wb = getWorkBook(is);
		if (wb == null) {
			return list;
		}
		int Sheets = wb.getNumberOfSheets(); // 获取总个数
		for (int i = 0; i < Sheets; i++) {
			Sheet sheet = wb.getSheetAt(i);
			int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
			System.out.println(physicalNumberOfRows);
			for (int j = 0; j < physicalNumberOfRows; j++) {
				if (j >= 1) {
					Row row = sheet.getRow(j); // 获取当前xlsx的行
					int cellNum = row.getPhysicalNumberOfCells(); // 获取总列数
					Map<String, Object> cmap = new HashMap<>();
					for (int k = 0; k < cellNum; k++) {
						Cell cell = row.getCell(k);
						Object item = getCellValue(cell);
						cmap.put(keyList.get(k), item);
					}
					list.add(cmap);
				}
			}
		}
		return list;
	}

	/*public int importExcel(MultipartFile file) {
		int result = 0;
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		try {

			List<Map<String, Object>> list = readExecl(file);
			JSON.toJSONString(list);
			for (Map<String, Object> map : list) {
				String json = gson.toJson(map);
				UserExcel user = gson.fromJson(json, UserExcel.class);
				System.out.println(user);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}*/

	@SuppressWarnings("deprecation")
	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 把数字当成String来读，避免出现1读成1.0的情况
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: // 数字
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING: // 字符串
				cellValue = String.valueOf(cell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN: // Boolean
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA: // 公式
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_BLANK: // 空值
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR: // 故障
				cellValue = "非法字符";
				break;
			default:
				cellValue = "未知类型";
				break;
		}
		return cellValue;
	}
}
