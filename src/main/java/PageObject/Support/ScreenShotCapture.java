package PageObject.Support;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShotCapture {
	// ScreenShot Switch
	public String SCREENSHOT_SWITCH = PropertyReader.readProperty("screenshot_switch");

	/**
	 * This method is used to Capturing screenshot.
	 * 
	 * @param driver
	 * @param screenShotName
	 * @return
	 * @throws IOException
	 */
	public String capture(WebDriver driver, String screenShotName) throws IOException {
		if (SCREENSHOT_SWITCH.trim().toLowerCase().equals("on")) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			if (System.getenv("ReportName") != null) {
				String dest = System.getProperty("user.dir") + File.separator + "src" + File.separator + "reports"
						+ File.separator + screenShotName + ".png";
				File destination = new File(dest);
				FileUtils.copyFile(source, destination);
			} else {
				String dest = System.getProperty("user.dir") + File.separator + "src" + File.separator + "reports"
						+ File.separator + screenShotName + ".png";
				File destination = new File(dest);
				FileUtils.copyFile(source, destination);
			}
		}
		return screenShotName;
	}
}
