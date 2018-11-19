package PageObject.Humingo_Demo;

import org.testng.annotations.Test;
import PageObject.Support.Log;
import PageObject.Support.WebActions;

public class TestHUM01VerifyLogin extends WebActions{
	@Test
	public void testVerifyLogin() {
		Log.startTestCase("1", "TestVerifyLogin");
	try {
		message = incrementSteps()+" Launch Browser";
		launchDriver();
		Log.getReport(message);
		
		message = incrementSteps()+" Login To Application";
		loginToAnApplication();
		Log.getReport(message);
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	Log.endTestCase("TestVerifyLogin");
}
	
}
