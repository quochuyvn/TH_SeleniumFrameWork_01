package utilities;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/*
 * This is from AI - ChatGPT
 */

public class ScreenshotUtil {

    public static String captureScreen(WebDriver driver, String testName)
            throws IOException {

        String timeStamp = String.valueOf(System.currentTimeMillis());

        String screenshotPath = System.getProperty("user.dir")
                + "/screenshots/" + testName + "_" + timeStamp + ".png";

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(screenshotPath);

        FileUtils.copyFile(source, target);

        return screenshotPath;
    }
}

