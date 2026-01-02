package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

    // === 1. Report objects ===
    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;

    // === 2. ThreadLocal để chạy parallel an toàn ===
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private String reportName;

    // ======================================================
    // onStart – chạy 1 lần trước khi toàn bộ suite bắt đầu
    // ======================================================
    @Override
    public void onStart(ITestContext context) {

        // Tạo timestamp cho report
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                .format(new Date());

        reportName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(
                System.getProperty("user.dir") + "/reports/" + reportName);

        // ===== Config giao diện report =====
        sparkReporter.config().setDocumentTitle("Opencart Automation Report");
        sparkReporter.config().setReportName("Functional Test Execution");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // ===== System Info =====
        extent.setSystemInfo("Application", "Opencart");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", System.getProperty("user.name"));

        // ===== Lấy parameter từ testng.xml =====
        String os = context.getCurrentXmlTest().getParameter("os");
        String browser = context.getCurrentXmlTest().getParameter("browser");

        if (os != null)
            extent.setSystemInfo("OS", os);

        if (browser != null)
            extent.setSystemInfo("Browser", browser);

        // ===== Hiển thị group nếu có =====
        List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
        if (!groups.isEmpty()) {
            extent.setSystemInfo("Groups", groups.toString());
        }
    }

    // ======================================================
    // onTestStart – chạy trước MỖI test method
    // ======================================================
    @Override
    public void onTestStart(ITestResult result) {

        // Tạo ExtentTest cho từng test method
        ExtentTest test = extent.createTest(
        		result.getTestClass().getRealClass().getSimpleName() + " :: "
                        + result.getMethod().getMethodName());

        // Gán group vào report
        test.assignCategory(result.getMethod().getGroups());

        // Set vào ThreadLocal
        extentTest.set(test);
    }

    // ======================================================
    // onTestSuccess
    // ======================================================
    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS,
                result.getMethod().getMethodName() + " PASSED");
    }

    // ======================================================
    // onTestFailure
    // ======================================================
    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTest test = extentTest.get();

        test.log(Status.FAIL,
                result.getMethod().getMethodName() + " FAILED");

        // Log exception
        test.log(Status.FAIL, result.getThrowable());

        try {
            // === Lấy driver từ BaseClass của test hiện tại ===
            BaseClass base = (BaseClass) result.getInstance();

            String screenshotPath = ScreenshotUtil.captureViewport(
                    base.getDriver(),
                    result.getMethod().getMethodName());

            test.addScreenCaptureFromPath(screenshotPath);

        } catch (Exception e) {
            test.log(Status.WARNING,
                    "Failed to attach screenshot: " + e.getMessage());
        }
    }

    // ======================================================
    // onTestSkipped
    // ======================================================
    @Override
    public void onTestSkipped(ITestResult result) {

        extentTest.get().log(Status.SKIP,
                result.getMethod().getMethodName() + " SKIPPED");

        if (result.getThrowable() != null) {
            extentTest.get().log(Status.SKIP, result.getThrowable());
        }
    }

    // ======================================================
    // onFinish – chạy sau khi suite kết thúc
    // ======================================================
    @Override
    public void onFinish(ITestContext context) {

        // Ghi report ra file
        extent.flush();

        // === Auto open report (chỉ dùng local) ===
        try {
            if (Desktop.isDesktopSupported()) {
                File reportFile = new File(
                        System.getProperty("user.dir") + "/reports/" + reportName);
                Desktop.getDesktop().browse(reportFile.toURI());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear ThreadLocal (best practice)
        extentTest.remove();
    }
}
