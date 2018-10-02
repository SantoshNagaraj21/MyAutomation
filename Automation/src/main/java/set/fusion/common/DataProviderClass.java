package set.fusion.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataProviderClass {

	static XSSFWorkbook WB;
	static XSSFSheet sheet;
	private static XSSFCell Cell;

	public static HashMap[][] testData(String sheetName) throws Exception {

		String filepath = ReadProperties.getPropertyValue("TestDataPath", "\\src\\main\\resources\\common\\Config.Properties");
		String fileName = ReadProperties.getPropertyValue("TestDataFile", "\\src\\main\\resources\\common\\Config.Properties");

		File file = new File("./" + filepath + "\\" + fileName);

		FileInputStream testdatafile = new FileInputStream(file);

		WB = new XSSFWorkbook(testdatafile);
		sheet = WB.getSheet(sheetName);

		ArrayList<LinkedHashMap<String, String>> arraylist = new ArrayList<LinkedHashMap<String, String>>();

		int frow = 1;
		int hrow = 0;

		int totalRows = sheet.getLastRowNum();
		int totalcols = sheet.getRow(frow).getLastCellNum();

		for (int i = frow; i <= totalRows; i++) {

			DataFormatter getrunvalue = new DataFormatter();

			XSSFRow actualrow = sheet.getRow(i);
			XSSFCell runcell = actualrow.getCell(0);

			int runvalue = Integer.parseInt(getrunvalue.formatCellValue(runcell));
			if (runvalue == 1) {

				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

				for (int j = 0; j < totalcols; j++) {

					DataFormatter formatter = new DataFormatter();

					XSSFCell colcell = actualrow.getCell(j);
					String fieldvalue = formatter.formatCellValue(colcell);

					XSSFRow headrow = sheet.getRow(0);
					XSSFCell headcell = headrow.getCell(j);
					String fieldname = formatter.formatCellValue(headcell);

					map.put(fieldname, fieldvalue);

				}

				arraylist.add(map);

			}
		}

		HashMap[][] mydata = new HashMap[arraylist.size()][1];
		for (int i = 0; i < arraylist.size(); i++) {

			mydata[i][0] = arraylist.get(i);

		}

		return mydata;

	}
	
	@DataProvider(name = "GmailLoginPage")
	public static Object[][] GmailLoginPage() throws Exception {

		Object[][] data = testData("GmailLoginPage");
		return data;
	}
	
	@DataProvider(name = "APITest")
	public static Object[][] APITests() throws Exception {

		Object[][] data = testData("APITest");
		return data;
	}

}
