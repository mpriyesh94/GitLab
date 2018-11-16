package PageObject.Support;

import java.lang.reflect.Field;
import java.util.Hashtable;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class HumingoLib {

	public String workSheetName = null;
	public String workBookName = null;

	public String sNo = null;
	public String searchProduct = null;
	public String categoryList = null;
	public String selectQuery = null;
	public String expectedResult = null;
	

	public Hashtable<String, Integer> excelHeaders = new Hashtable<String, Integer>();
	public Hashtable<String, Integer> excelrRowColumnCount = new Hashtable<String, Integer>();

	public String toString() {
		StringBuffer listOfValues = new StringBuffer();
		@SuppressWarnings("rawtypes")
		Class cls = this.getClass();
		Field[] fields = cls.getDeclaredFields();
		Field field = null;
		try {
			for (int i = 0; i < fields.length; i++) {
				field = fields[i];
				Object subObj = field.get(this);
				if (subObj != null && !field.getName().equals("logger")) {
					listOfValues.append(":");
					listOfValues.append(field.getName());
					listOfValues.append("=");
					listOfValues.append(subObj.toString());
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return listOfValues.toString();
	}

	public boolean fetchTestData() {

		ReadFromExcel readTestData = new ReadFromExcel();
		boolean isDataFound = false;
		try {

			HSSFSheet sheet = null;
			// function call to initiate a connection to an excel sheet
			sheet = readTestData.initiateExcelConnection("ExcelData", workSheetName, workBookName);

			// function to find number of rows and columns
			excelrRowColumnCount = readTestData.findRowColumnCount(sheet, excelrRowColumnCount);

			// function call to find excel header fields
			excelHeaders = readTestData.readExcelHeaders(sheet, excelHeaders, excelrRowColumnCount);

			HSSFRow row = null;
			HSSFCell cell = null;
			String tempId = null;

			for (int r = 0; r < excelrRowColumnCount.get("RowCount"); r++) {
				row = sheet.getRow(r);
				if (row != null) {
					for (int c = 0; c < excelrRowColumnCount.get("ColumnCount");) {
						cell = row.getCell(excelHeaders.get("S_No"));
						if (cell != null) {
							tempId = readTestData.convertHSSFCellToString(row.getCell(excelHeaders.get("S_No")));
							if (tempId.equals(sNo)) {
								isDataFound = true;
								if (workSheetName.equalsIgnoreCase("Data")) {
									searchProduct = readTestData
											.convertHSSFCellToString(row.getCell(excelHeaders.get("SearchProduct")));
									categoryList = readTestData
											.convertHSSFCellToString(row.getCell(excelHeaders.get("Category")));
									selectQuery = readTestData
											.convertHSSFCellToString(row.getCell(excelHeaders.get("SelectQuery")));
									expectedResult = readTestData
											.convertHSSFCellToString(row.getCell(excelHeaders.get("ExpectedResult")));
								}
								break;
							} else {
								break;
							}
						} else {
							break;
						}
					}
				}
				if (isDataFound) {
					break;
				}
			}
			if (!isDataFound) {
				System.out.println("No more data to read");
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return isDataFound;
	}

}
