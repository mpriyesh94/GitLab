package PageObject.Support;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ReadFromExcel {
	
	@SuppressWarnings("resource")
	public HSSFSheet initiateExcelConnection(String moduleName, String workSheetName, String workBookName){
		
		String strBasePath = null;
		String file = null;
		HSSFSheet sheet = null;
			    					
		try {
			File dir1 = new File (".");
			strBasePath=dir1.getCanonicalPath();
			file=strBasePath+File.separator+"src"+File.separator+"data"+File.separator+moduleName+File.separator+workBookName;
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			sheet = wb.getSheet(workSheetName);			
		} catch (RuntimeException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sheet;
		
	}
	
public Hashtable<String, Integer> findRowColumnCount(HSSFSheet sheet, Hashtable<String, Integer> rowColumnCount){
		
		HSSFRow row = null;				
		int rows; 		
		rows = sheet.getPhysicalNumberOfRows();
		int cols = 0;
		int tmp = 0;
		int counter = 0;
		String temp = null;
		
		for(int i = 0; i < 10 || i < rows; i++) {
			row = sheet.getRow(i);
			if(row != null) {
				temp = convertHSSFCellToString(row.getCell(0));
				if(!temp.equals("")){
				counter++;
				}
				tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				if(tmp > cols) cols = tmp;
			}
		}		
		
		rowColumnCount.put("RowCount", counter);
		rowColumnCount.put("ColumnCount", cols);		
		
		
		return rowColumnCount;
	}

	public Hashtable<String, Integer> readExcelHeaders(HSSFSheet sheet,Hashtable<String, Integer> excelHeaders,Hashtable<String, Integer> rowColumnCount){
		
		HSSFRow row = null;
		HSSFCell cell = null;
		for(int r = 0; r < rowColumnCount.get("RowCount"); r++) {
			row = sheet.getRow(r);    	        

			if(row != null) {
				for(int c = 0; c < rowColumnCount.get("ColumnCount"); c++) {	                
					cell = row.getCell(c);             
					if(cell != null) {						
						excelHeaders.put(cell.toString(), c);
					}
				}
				break;
			}	        
		}			
		return excelHeaders; 
	}
	

	public String convertHSSFCellToString(HSSFCell cell){
		String cellValue = null;	
		if(cell != null){
			cellValue = cell.toString();	
			cellValue = cellValue.trim();
		}else {
			cellValue = "";
		}		
		return cellValue;
	}
}

