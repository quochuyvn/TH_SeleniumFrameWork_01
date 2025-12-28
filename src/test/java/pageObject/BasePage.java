package pageObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	public WebDriver driver;
	public WebDriverWait wait;
    public Actions actions;
    public JavascriptExecutor js;


	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}
	
	/* ============================================================
    WAIT UTILITIES (WebElement)
    - Chờ element sẵn sàng để thao tác
    - Fail là chụp screenshot
 ============================================================ */

 protected WebElement waitForVisible(WebElement element) {
     try {
         return wait.until(ExpectedConditions.visibilityOf(element));
     } catch (Exception e) {
         captureScreenshotAuto("wait_visible_fail");
         throw e;
     }
 }

 protected WebElement waitForClickable(WebElement element) {
     try {
         return wait.until(ExpectedConditions.elementToBeClickable(element));
     } catch (Exception e) {
         captureScreenshotAuto("wait_clickable_fail");
         throw e;
     }
 }

 /* ============================================================
    SAFE ACTIONS
    - Highlight màu xanh trước khi thao tác
    - Delay 500ms để dễ quan sát / quay video
    - Thao tác xong thì trả về style ban đầu
 ============================================================ */

 public void click(WebElement element) {
	    String originalStyle = null;

	    try {
	        waitForClickable(element);
	        originalStyle = highlight(element);
	        element.click();
	        unHighlight(element, originalStyle);
	    } catch (Exception e) {
	        captureScreenshotAuto("click_fail");
	        throw e;
	    }
	}
 
 public void clickAndNavigate(WebElement element) {
	    try {
	        waitForClickable(element);
	        highlight(element);
	        element.click();
	    } catch (Exception e) {
	        captureScreenshotAuto("click_navigate_fail");
	        throw e;
	    }
	}


 public void sendKeys(WebElement element, String text) {
     String originalStyle = null;

     try {
         waitForVisible(element);
         originalStyle = highlight(element);
         element.clear();
         element.sendKeys(text);
     } catch (Exception e) {
         captureScreenshotAuto("sendkeys_fail");
         throw e;
     } finally {
         unHighlight(element, originalStyle);
     }
 }

 public String getText(WebElement element) {
     String originalStyle = null;

     try {
         waitForVisible(element);
         originalStyle = highlight(element);
         return element.getText();
     } catch (Exception e) {
         captureScreenshotAuto("gettext_fail");
         throw e;
     } finally {
         unHighlight(element, originalStyle);
     }
 }

 public boolean isDisplayed(WebElement element) {
     try {
         return waitForVisible(element).isDisplayed();
     } catch (Exception e) {
         captureScreenshotAuto("isDisplayed_fail");
         return false;
     }
 }

 /* ============================================================
    JS UTILITIES
 ============================================================ */

 public void jsClick(WebElement element) {
     String originalStyle = null;

     try {
         waitForVisible(element);
         originalStyle = highlight(element);
         js.executeScript("arguments[0].click();", element);
     } catch (Exception e) {
         captureScreenshotAuto("js_click_fail");
         throw e;
     } finally {
         unHighlight(element, originalStyle);
     }
 }

 public void scrollToElement(WebElement element) {
     String originalStyle = null;

     try {
         waitForVisible(element);
         originalStyle = highlight(element);
         js.executeScript("arguments[0].scrollIntoView(true);", element);
     } catch (Exception e) {
         captureScreenshotAuto("scrollTo_fail");
         throw e;
     } finally {
         unHighlight(element, originalStyle);
     }
 }

 /* ============================================================
    HIGHLIGHT ELEMENT
    - Highlight luôn bật
    - Màu xanh để debug
 ============================================================ */

 /**
  * Highlight element bằng viền xanh + nền xanh nhạt
  * @return style gốc để restore
  */
 private String highlight(WebElement element) {
     if (element == null) return null;

     // Lưu lại style gốc
     String originalStyle = element.getAttribute("style");

     // Highlight màu xanh
     js.executeScript(
             "arguments[0].setAttribute('style', arguments[1]);",
             element,
             originalStyle + " border: 2px solid #00FF00; background-color: #E8FFE8;"
     );

     // Delay cố định 500ms để dễ quan sát
     sleep(500);

     return originalStyle;
 }

 /**
  * Trả element về style ban đầu
  */
 private void unHighlight(WebElement element, String originalStyle) {
	    if (element == null || originalStyle == null) return;

	    try {
	        js.executeScript(
	                "arguments[0].setAttribute('style', arguments[1]);",
	                element,
	                originalStyle
	        );
	    } catch (StaleElementReferenceException ignored) {
	        // Element đã bị stale do chuyển trang → bỏ qua là đúng
	    }
	}

 /* ============================================================
    COMMON UTILITY
 ============================================================ */

 protected void sleep(long millis) {
     try {
         Thread.sleep(millis);
     } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
     }
 }

 /* ============================================================
    SCREENSHOT
 ============================================================ */

 public void captureScreenshotAuto(String reason) {
     try {
         String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
         String filename = reason + "_" + timestamp + ".png";
         String folderPath = System.getProperty("user.dir") + "/screenshots/";

         File folder = new File(folderPath);
         if (!folder.exists()) folder.mkdirs();

         File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
         File dest = new File(folderPath + filename);

         FileUtils.copyFile(src, dest);

//         Allure.addAttachment(
//                 "Screenshot - " + reason,
//                 new ByteArrayInputStream(
//                         ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
//                 )
//         );

     } catch (Exception e) {
         System.out.println("[SCREENSHOT ERROR] " + e.getMessage());
     }
 }
	
	
}
