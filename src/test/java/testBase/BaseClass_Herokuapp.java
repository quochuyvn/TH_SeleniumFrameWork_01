package testBase;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass_Herokuapp {
	public WebDriver driver;
	public Logger logger; 
	
	public WebDriver getDriver() {
        return driver;
    }
	
	@BeforeClass
	public void setup() {
		
		logger = LogManager.getLogger(this.getClass()); //Log4j
		
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://the-internet.herokuapp.com/");
		driver.manage().window().maximize();
	}
	
	@AfterClass
	public void tearDown() {
		driver.close();
	}
	
	
}
