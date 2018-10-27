package aut.framework.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DataProvider {

	static XSSFWorkbook WB;
	static XSSFSheet sheet;

	@SuppressWarnings("rawtypes")
	public static HashMap[][] testData(String sheetName) throws Exception {

		String filepath = ReadProperties.getPropertyValue("TestDataPath",
				"\\src\\main\\resources\\common\\Config.Properties");
		String fileName = ReadProperties.getPropertyValue("TestDataFile",
				"\\src\\main\\resources\\common\\Config.Properties");

		File file = new File("./" + filepath + "\\" + fileName);

		FileInputStream testdatafile = new FileInputStream(file);

		WB = new XSSFWorkbook(testdatafile);
		sheet = WB.getSheet(sheetName);

		ArrayList<LinkedHashMap<String, String>> arraylist = new ArrayList<LinkedHashMap<String, String>>();

		int frow = 1;

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

	@SuppressWarnings("rawtypes")
	public static HashMap[][] testDataXML(String xmlName)
			throws IOException, ParserConfigurationException, SAXException {

		String filepath = ReadProperties.getPropertyValue("TestDataPath",
				"\\src\\main\\resources\\common\\Config.Properties");

		File file = new File("./" + filepath + "\\" + xmlName + ".xml");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList dataList = doc.getElementsByTagName("Dataset");

		System.out.println("DataSet Length: " + dataList.getLength());

		ArrayList<LinkedHashMap<String, String>> arraylist = new ArrayList<LinkedHashMap<String, String>>();

		for (int i = 0; i < dataList.getLength(); i++) {

			Node data = dataList.item(i);

			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

			if (data.getNodeType() == data.ELEMENT_NODE) {

				Element dataId = (Element) data;

				String id = dataId.getAttribute("id");

				map.put("id", id);

				NodeList childDataList = data.getChildNodes();

				for (int j = 0; j < childDataList.getLength(); j++) {

					Node child = childDataList.item(j);

					if (child.getNodeType() == child.ELEMENT_NODE) {

						Element childData = (Element) child;

						String attriName = childData.getTagName();

						String attriValue = child.getTextContent();

						map.put(attriName, attriValue);
					}

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

}
