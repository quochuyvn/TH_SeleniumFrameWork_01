package pageObject;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage_Teacher {
	public WebDriver driver;
	public WebDriverWait wait;
    public Actions actions;
    public JavascriptExecutor js;


	public BasePage_Teacher(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}
	
	 /* ============================================================
    HIGHLIGHT ELEMENT
 ============================================================ */

 public void highlight(WebElement element) throws InterruptedException {
     js.executeScript("arguments[0].style.border='2px solid red'", element);
     Thread.sleep(1000);
 }
	
	
}
