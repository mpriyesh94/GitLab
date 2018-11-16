package PageObject.Support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertyReader {

	/**
	 * This method is used to read data from config properties.
	 * 
	 * @param key
	 * @return
	 */
	public static String readProperty(String key) {

		String returnText = "";
		try {
			File file = new File("config.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			returnText = properties.getProperty(key);
			fileInput.close();
		} catch (Exception e) {
		}
		return returnText;
	}

	/**
	 * This method is used to write data from config properties.
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String writeProperty(String key, String value) throws IOException {

		String returnText = "";
		FileOutputStream fileOut = null;
		try {
			Properties configProperty = new Properties();
			File dirl = new File(".");
			String file = dirl.getCanonicalPath() + File.separator + "src" + File.separator + "data" + File.separator
					+ "properties" + File.separator + "user" + ".properties";
			FileInputStream fileIn = new FileInputStream(file);
			configProperty.load(fileIn);
			configProperty.setProperty(key, value);
			fileOut = new FileOutputStream(file);
			configProperty.store(fileOut, "User properties");

		} catch (Exception e) {
		} finally {
			fileOut.close();
		}
		return returnText;
	}

	/**
	 * This method used to get property elements from \src\data\properties\ path.
	 * 
	 * @param propertyName
	 * @param key
	 * @return
	 */
	public static String getPropertyFile(String propertyName, String key) {

		String returnText=null;
		try {

			File dirl = new File(".");
			String file = dirl.getCanonicalPath() + File.separator + "src" + File.separator + "data" + File.separator
					+ "properties" + File.separator + propertyName + ".properties";
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			returnText = properties.getProperty(key);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnText;

	}
}
