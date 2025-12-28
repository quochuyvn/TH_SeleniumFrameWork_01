package testCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSetup_NoTestNG {

	public static void main(String[] args) throws InterruptedException {
		// Create Driver and open a web browser
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.opencart.com/index.php?route=common/home");
		Thread.sleep(2000);
		driver.close();

	}

}
