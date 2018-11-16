package PageObject.Support;

import org.apache.log4j.Logger;
import com.relevantcodes.extentreports.LogStatus;


public class Log  {
	
	// Initialize Log4j logs
	public static Logger Log = Logger.getLogger(Log.class.getName());
	//public static ExtentTest test;

public static void getReport(String message) {
		Log.info(message + "- PASS");
		WebActions.test.log(LogStatus.PASS, message + " -- executed successfully");

	}
	// This is to print log for the beginning of the test case, as we usually run so
	// many test cases as a test suite

	public static void startTestCase(String CaseID,String logTestCaseName) {
		WebActions.test =ExtentReport.extent.startTest(CaseID);

		Log.info("****************************************************************************************");

		Log.info("$$$$$$$$$$$$$$$$$$$$$                 " + logTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");

		Log.info("****************************************************************************************");

	}

	// This is to print log for the ending of the test case

	public static void endTestCase(String sTestCaseName) {
		ExtentReport.extent.endTest(WebActions.test);
		Log.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");

		Log.info("X");

		Log.info("X");

	}

	// Need to create these methods, so that they can be called

	public static void info(String message) {

		Log.info(message);

	}

	public static void warn(String message) {

		Log.warn(message);

	}

	public static void error(String message) {

		Log.error(message);

	}

	public static void fatal(String message) {

		Log.fatal(message);

	}

	public static void debug(String message) {

		Log.debug(message);

	}

	public static void error(Throwable throwable) {
	Log.error(throwable);
	}

}