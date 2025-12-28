package testCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class TestSetup_TestNG {
	
	@Test
	public void testSetUpTestNG() throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.opencart.com/index.php?route=common/home");
		Thread.sleep(2000);
		driver.close();
	}

}
