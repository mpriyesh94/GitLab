package PageObject.Support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import org.testng.Assert;

public class DatabaseActions {

	static final String url = PropertyReader.readProperty("testlink_mysql_url");
	static final String username = PropertyReader.readProperty("testlink_mysql_username");
	static final String password = PropertyReader.readProperty("testlink_mysql_password");

	Connection conn = null;
	public Statement stmt = null;
	public ResultSet getData = null;

	/**
	 * This method is used to establish Db Connections.
	 * 
	 * @param schema
	 * @return
	 */
	public DatabaseActions() {
		try {
			// Load the Connector/J driver
			Class.forName("com.mysql.jdbc.Driver");
			// Establish connection to MySQL
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
		} catch (Exception e) {
			Assert.fail("Database Connection Failed");
		}
	}

	/**
	 * This method is used to fetch data from database.
	 * 
	 * @param query
	 * @param columnName
	 * @return
	 */
	public String[] fetchQuery(String query, String list) {
		String value[] = new String[5];
		try {
			ResultSet getData = null;
			ArrayList<String> filename = new ArrayList<String>(Arrays.asList(list.split(",")));
			String status[] = new String[10];
				getData = stmt.executeQuery(query);
				while (getData.next()) {
					for (int col = 0; col < filename.size(); col++) {
					String data = filename.get(col);
					// Retrieve all the data by column name
					status[col] = getData.getString(data);
				}
			}
			for (int val = 0; val < status.length; val++) {
				if (status[val] != null) {
					value[val] = status[val];
				} else {
					break;
				}
			}
			getData.close();

		} catch (Exception e) {
			Assert.fail("Could not Fetch " + query);
		}
		return value;
	}
	/**
	 * It is used for insert,edit,update data into database.
	 * 
	 * @param query
	 * @return
	 */
	public boolean executeQuery(String query) {
		boolean returnValue = false;
		try {
			stmt.executeUpdate(query);
			returnValue = true;
		} catch (Exception e) {
			Assert.fail("Could not Execute query " + query);
		}
		return returnValue;
	}
	
	
	/**
	 * It is used to select data into database.
	 * 
	 * @param query
	 * @return
	 */
	public boolean selectQuery(String query) {
		boolean returnValue = false;
		try {
			stmt.executeQuery(query);
			returnValue = true;
		} catch (Exception e) {
			Assert.fail("Could not Execute query " + query);
		}
		return returnValue;
	}
	
	



}
