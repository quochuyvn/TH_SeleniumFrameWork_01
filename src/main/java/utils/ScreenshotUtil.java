package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler; // [UPDATE] Dùng thư viện này của Selenium quản lý file ảnh tốt hơn

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    // Constructor Private để ngăn việc khởi tạo class này (Best practice cho Utility class)
    private ScreenshotUtil() {
    }

    /**
     * [UPDATE] Hàm chụp ảnh màn hình lưu ra File vật lý (.png)
     * Dùng khi muốn lưu trữ bằng chứng lâu dài trong folder.
     */
    public static String captureViewport(WebDriver driver, String testName) {
        try {
            // Kiểm tra driver null để tránh NullPointerException
            if (driver == null) {
                System.out.println("Driver is null, cannot capture screenshot.");
                return null;
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            
            // [UPDATE] Đường dẫn thư mục
            String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
            File dir = new File(screenshotDir);
            
            // [UPDATE] Tạo thư mục nếu chưa tồn tại
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Chụp ảnh
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            // Đường dẫn file đích
            String destinationPath = screenshotDir + testName + "_" + timestamp + ".png";
            File dest = new File(destinationPath);

            // [UPDATE] Dùng FileHandler của Selenium thay cho Files.copy của Java IO
            // FileHandler tự động xử lý việc ghi đè hoặc lỗi file tốt hơn trong ngữ cảnh test
            FileHandler.copy(src, dest);

            return destinationPath;

        } catch (Exception e) {
            // [UPDATE] Log ra console để biết lỗi nhưng không throw RuntimeException làm dừng hẳn luồng test (tuỳ nhu cầu)
            System.err.println("Exception while taking screenshot: " + e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * [NEW] Hàm chụp ảnh trả về chuỗi Base64
     * Rất hữu ích cho ExtentReport 5+. 
     * Ưu điểm: Ảnh được nhúng thẳng vào file HTML, không cần folder 'screenshots' đi kèm khi gửi báo cáo.
     */
    public static String captureBase64(WebDriver driver) {
        try {
            if (driver == null) return null;
            // Trả về chuỗi mã hoá ảnh thay vì tạo file
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("Exception while taking Base64 screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * [NEW] Hàm chụp ảnh riêng một Element cụ thể (Selenium 4 Feature)
     * Ví dụ: Chỉ chụp cái thông báo lỗi màu đỏ, hoặc cái form login.
     */
    public static String captureElement(WebElement element, String elementName) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotDir = System.getProperty("user.dir") + "/screenshots/elements/"; // Lưu folder riêng cho gọn
            
            File dir = new File(screenshotDir);
            if (!dir.exists()) dir.mkdirs();

            // Selenium 4 cho phép gọi getScreenshotAs trực tiếp trên WebElement
            File src = element.getScreenshotAs(OutputType.FILE);
            
            String destPath = screenshotDir + elementName + "_" + timestamp + ".png";
            FileHandler.copy(src, new File(destPath));

            return destPath;
        } catch (Exception e) {
            System.err.println("Exception while taking Element screenshot: " + e.getMessage());
            return null;
        }
    }
}