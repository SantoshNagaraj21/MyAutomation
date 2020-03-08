package aut.framework.common;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {

  // This class contains Methods for Reading and Writing Data to File or Properties

  static Properties properties;

  //This Method loads the property file
  public static void loadProperties(String filepath) throws IOException {

    properties = new Properties();

    File file = new File(System.getProperty("user.dir") + filepath);
    FileReader freader = new FileReader(file);

    properties.load(freader);
  }

  //This Method is used to get data from property file
  public static String getPropertyValue(String property, String propertypath) throws IOException {

    loadProperties(propertypath);
    return properties.getProperty(property);

  }

}
