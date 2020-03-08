package aut.framework.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
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
  static SetFusionDataMap Url;


  public static HashMap[][] testData(String sheetName) throws Exception {

    String filepath = ReadProperties.getPropertyValue("TestDataPath", "/src/main/resources/common/Config.Properties");
    String fileName = ReadProperties.getPropertyValue("TestDataFile", "/src/main/resources/common/Config.Properties");
    String timeZone = ReadProperties.getPropertyValue("timezone", "/src/main/resources/common/Config.Properties");

    File file = new File(System.getProperty("user.dir") + filepath + "/" + fileName);

    FileInputStream testdatafile = new FileInputStream(file);

    WB = new XSSFWorkbook(testdatafile);
    sheet = WB.getSheet(sheetName);

    FormulaEvaluator evaluator = WB.getCreationHelper().createFormulaEvaluator();

    ArrayList<LinkedHashMap<String, Object>> arraylist = new ArrayList<LinkedHashMap<String, Object>>();

    int firstRow = 1;

    int totalRows = sheet.getLastRowNum();
    int totalCols = sheet.getRow(firstRow).getLastCellNum();

    for (int i = firstRow; i <= totalRows; i++) {

      DataFormatter getRunValue = new DataFormatter();

      XSSFRow actualRow = sheet.getRow(i);
      WB.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
      XSSFCell runCell = actualRow.getCell(0);

      int runvalue = Integer.parseInt(getRunValue.formatCellValue(runCell));
      if (runvalue == 1) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        for (int j = 0; j < totalCols; j++) {

          DataFormatter formatter = new DataFormatter();

          short x = WB.createDataFormat().getFormat("YYYY-MM-DD;@");
          short y = WB.createDataFormat().getFormat("YYYY-MM-DD HH:MM:SS;@");

          SimpleDateFormat dateTimeISO;
          SimpleDateFormat dateTime;
          dateTimeISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
          dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          dateTimeISO.setTimeZone(TimeZone.getTimeZone(timeZone));

          List<String> datetimeList = Arrays.asList(Url.getDateTimeAttributes());

          CellStyle dateCellFormat = WB.createCellStyle();
          CellStyle datetimeCellFormat = WB.createCellStyle();
          dateCellFormat.setDataFormat(x);
          datetimeCellFormat.setDataFormat(y);

          String getColName = sheet.getRow(0).getCell(j).getStringCellValue();

          XSSFCell colCell = actualRow.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

          Object fieldValue;

          if (colCell == null) {
            fieldValue = null;
          } else if (colCell.getCellType() == CellType.NUMERIC) {

            if (DateUtil.isCellDateFormatted(colCell)) {

              if (datetimeList.contains(getColName)) {
                colCell.setCellStyle(datetimeCellFormat);
                fieldValue = dateTimeISO.format(dateTime.parse((String) formatter.formatCellValue(colCell, evaluator)));
              } else {
                colCell.setCellStyle(dateCellFormat);
                fieldValue = formatter.formatCellValue(colCell, evaluator);
              }

            } else {
              fieldValue = formatter.formatCellValue(colCell);
            }

          } else if (colCell.getCellType() == CellType.STRING) {
            fieldValue = formatter.formatCellValue(colCell);

          } else if (colCell.getCellType() == CellType.BOOLEAN) {
            fieldValue = colCell.getBooleanCellValue();

          } else if (colCell.getCellType() == CellType.FORMULA) {
            if (DateUtil.isCellDateFormatted(colCell)) {
              if (datetimeList.contains(getColName)) {
                colCell.setCellStyle(datetimeCellFormat);
                fieldValue = dateTimeISO.format(dateTime.parse((String) formatter.formatCellValue(colCell, evaluator)));
              } else {
                colCell.setCellStyle(dateCellFormat);
                fieldValue = formatter.formatCellValue(colCell, evaluator);
              }
            } else {
              fieldValue = formatter.formatCellValue(colCell, evaluator);
            }

          } else {
            fieldValue = null;
          }

          XSSFRow headrow = sheet.getRow(0);
          XSSFCell headcell = headrow.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
          String fieldname = formatter.formatCellValue(headcell);

          if (headcell == null) {
            fieldname = null;
          }

//          System.out.println("Field Name : " + fieldname + " " + fieldValue);

          map.put(fieldname, fieldValue);

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


  public static HashMap[][] testDataXML(String xmlName)
      throws IOException, ParserConfigurationException, SAXException {

    String filepath = ReadProperties.getPropertyValue("TestDataPath", "/src/main/resources/common/Config.Properties");

    File file = new File(System.getProperty("user.dir") + filepath + "/" + xmlName + ".xml");

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

  public static void updateDataInExcel(String sheetName, String[] attributes, String attributeValue, String replaceValue) throws Exception {

    String filepath = ReadProperties.getPropertyValue("TestDataPath", "/src/main/resources/common/Config.Properties");
    String fileName = ReadProperties.getPropertyValue("TestDataFile", "/src/main/resources/common/Config.Properties");

    File file = new File(System.getProperty("user.dir") + filepath + "/" + fileName);

    FileInputStream inputStream = new FileInputStream(file);

    WB = new XSSFWorkbook(inputStream);
    sheet = WB.getSheet(sheetName);

    List<String> list = Arrays.asList(attributes);

    int frow = 1;

    int totalRows = sheet.getLastRowNum();
    int totalcols = sheet.getRow(frow).getLastCellNum();

    for (int j = 0; j < totalcols; j++) {

      String getColName = sheet.getRow(0).getCell(j).getStringCellValue();

      if (list.contains(getColName)) {

        for (int i = frow; i <= totalRows; i++) {
          String getrowValue = sheet.getRow(i).getCell(j).getStringCellValue();
          if (getrowValue.equalsIgnoreCase(attributeValue)) {
            sheet.getRow(i).getCell(j).setCellValue(replaceValue);
          }
        }
      }
    }

    inputStream.close();

    FileOutputStream outputStream = new FileOutputStream(file);
    WB.write(outputStream);
    WB.close();
    outputStream.close();

  }

}
