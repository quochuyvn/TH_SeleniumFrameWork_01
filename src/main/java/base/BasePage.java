package base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import config.ConfigReader; // [NEW]
import utils.*;

/**
 * =====================================================
 * BasePage
 * =====================================================
 * - Chứa TẤT CẢ hành vi thao tác UI
 * - Explicit Wait đặt TẬP TRUNG tại đây
 * - KHÔNG quản lý WebDriver lifecycle
 * =====================================================
 */
public abstract class BasePage { // [UPDATE] abstract

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Actions actions;
	protected MouseUtil mouse;
	protected KeyboardUtil keyboard;
	protected SliderUtil sliderUtil;
	protected Logger logger;

	/* =======================
	 * CONSTRUCTOR
	 * ======================= */
	public BasePage(WebDriver driver) {
		this.driver = driver;

		// [UPDATE] Explicit wait lấy từ config (fallback = 10s)
		int explicitWait = ConfigReader.getInt("explicit.wait", 10);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));

		if (driver != null) {
			this.actions = new Actions(driver);
		}
		this.mouse = new MouseUtil(driver);
		this.keyboard = new KeyboardUtil(driver);
		this.sliderUtil = new SliderUtil(driver);
		this.logger = LogManager.getLogger(this.getClass());

		PageFactory.initElements(driver, this);
	}

	/* =======================
	 * [NEW] GETTERS (support Cucumber)
	 * ======================= */
	protected WebDriver getDriver() {
		return driver;
	}

	protected WebDriverWait getWait() {
		return wait;
	}

	/* =======================
	 * HELPER FOR LOG
	 * ======================= */
	protected String describe(WebElement element) {
		try {
			String tag = element.getTagName();
			String id = element.getAttribute("id");
			String text = element.getText();

			if (id != null && !id.isEmpty())
				return "<" + tag + ">[id=" + id + "]";

			if (text != null && !text.isEmpty())
				return "<" + tag + ">[text=" + text + "]";

			return "<" + tag + ">";
		} catch (Exception e) {
			return "<element>";
		}
	}

	/* ======================
	 * STEP LOGGER
	 * ====================== */
	protected void logStep(String message) {
		logger.info("");
		logger.info("========== [STEP] {} ==========", message);
	}

	/* =====================================================
	 * WAIT METHODS (GIỮ NGUYÊN)
	 * ===================================================== */

	protected void waitForElementVisible(WebElement element) {
		if (element == null) {
			throw new IllegalArgumentException("Element cannot be null");
		}
		logger.debug("[WAIT] Visible {}", describe(element));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	protected void waitForElementClickable(WebElement element) {
		if (element == null) {
			throw new IllegalArgumentException("Element cannot be null");
		}
		logger.debug("[WAIT] Clickable {}", describe(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	protected void waitForElementPresent(By locator) {
		if (locator == null) {
			throw new IllegalArgumentException("Locator cannot be null");
		}
		logger.debug("[WAIT] Presence of locator {}", locator);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	protected void waitForPageLoaded() {
		logger.info("[WAIT] Page loaded completely");
		wait.until(driver -> {
			if (driver == null) return false;
			Object readyState = ((JavascriptExecutor) driver)
					.executeScript("return document.readyState");
			return "complete".equals(readyState);
		});
	}

	protected void waitForElementInvisible(WebElement element) {
		if (element == null) {
			throw new IllegalArgumentException("Element cannot be null");
		}
		logger.debug("[WAIT] Invisible {}", describe(element));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	/* =====================================================
	 * BASIC ACTIONS (GIỮ NGUYÊN 100%)
	 * ===================================================== */

	protected void click(WebElement element) {
		logger.info("[ACTION] Click {}", describe(element));
		try {
			waitForElementClickable(element);
			HighlightUtil.highlight(driver, element);
			element.click();
		} catch (StaleElementReferenceException e) {
			logger.warn("[RETRY] StaleElement → Click again {}", describe(element));
			waitForElementClickable(element);
			element.click();
		}
	}

	protected void clickWithRetry(WebElement element) {
		logger.info("[ACTION] Click with retry {}", describe(element));
		RetryUtil.retry(() -> {
			waitForElementClickable(element);
			HighlightUtil.highlight(driver, element);
			element.click();
		}, "Click " + describe(element));
	}

	protected void jsClick(WebElement element) {
		logger.info("[ACTION] JS Click {}", describe(element));
		waitForElementClickable(element);
		HighlightUtil.highlight(driver, element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	protected void jsClickWithRetry(WebElement element) {
		logger.info("[ACTION] JS Click with retry {}", describe(element));
		RetryUtil.retry(() -> {
			waitForElementVisible(element);
			HighlightUtil.highlight(driver, element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}, "JS Click " + describe(element));
	}

	protected void type(WebElement element, String text) {
		logger.info("[ACTION] Type '{}' into {}", text, describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		element.clear();
		element.sendKeys(text);
	}

	// 🔥 GIỮ NGUYÊN
	protected void typeSlowly(WebElement element, String text, long delayMs) throws InterruptedException {
		logger.info("[ACTION] Type slowly '{}' into {}", text, describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		element.clear();
		for (char c : text.toCharArray()) {
			element.sendKeys(String.valueOf(c));
			Thread.sleep(delayMs);
		}
	}

	// 🔥 GIỮ NGUYÊN
	protected void typeWithRetry(WebElement element, String text) {
		logger.info("[ACTION] Type with retry '{}' into {}", text, describe(element));
		RetryUtil.retry(() -> {
			waitForElementVisible(element);
			HighlightUtil.highlight(driver, element);
			element.clear();
			element.sendKeys(text);
		}, "Type " + text + " into " + describe(element));
	}

	// 🔥 GIỮ NGUYÊN
	protected void appendText(WebElement element, String text) {
		logger.info("[ACTION] Append '{}' into {}", text, describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		element.sendKeys(text);
	}

	protected String getText(WebElement element) {
		logger.info("[ACTION] Get text from {}", describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		return element.getText();
	}

	/**
	 * Get attribute value from element
	 * @param element target element
	 * @param attribute attribute name
	 * @return attribute value (never null, returns empty string if attribute not found)
	 */
	@SuppressWarnings("null")
	protected String getAttribute(WebElement element, String attribute) {
		logger.info("[ACTION] Get attribute '{}' from {}", attribute, describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		String value = element.getAttribute(attribute);
		return java.util.Objects.requireNonNullElse(value, "");
	}

	/**
	 * [NOTE]
	 * - isDisplayed / isEnabled intentionally DO NOT wait
	 * - Used for quick state check (assert, conditional logic)
	 * - If need wait → call waitForElementVisible() explicitly
	 */
	protected boolean isDisplayed(WebElement element) {
		try {
			boolean result = element.isDisplayed();
			logger.info("[VERIFY] {} displayed = {}", describe(element), result);
			return result;
		} catch (Exception e) {
			logger.warn("[VERIFY] {} NOT displayed", describe(element));
			return false;
		}
	}

	protected boolean isEnabled(WebElement element) {
		try {
			boolean result = element.isEnabled();
			logger.info("[VERIFY] {} enabled = {}", describe(element), result);
			return result;
		} catch (Exception e) {
			logger.warn("[VERIFY] {} NOT enabled", describe(element));
			return false;
		}
	}

	/* =====================================================
	 * MOUSE ACTIONS
	 * ===================================================== */

	protected void hover(WebElement element) {
		logger.info("[ACTION] Hover {}", describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		mouse.hover(element);
	}

	protected void rightClick(WebElement element) {
		logger.info("[ACTION] Right click {}", describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		mouse.rightClick(element);
	}

	protected void doubleClick(WebElement element) {
		logger.info("[ACTION] Double click {}", describe(element));
		waitForElementClickable(element);
		HighlightUtil.highlight(driver, element);
		mouse.doubleClick(element);
	}

	protected void clickAndHold(WebElement element) {
		logger.info("[ACTION] Click & Hold {}", describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		mouse.clickAndHold(element);
	}

	protected void release() {
		logger.info("[ACTION] Release mouse");
		mouse.release();
	}

	protected void dragAndDrop(WebElement source, WebElement target) {
		logger.info("[ACTION] Drag {} → {}", describe(source), describe(target));
		waitForElementVisible(source);
		waitForElementVisible(target);
		HighlightUtil.highlight(driver, source);
		HighlightUtil.highlight(driver, target);
		mouse.dragAndDrop(source, target);
	}

	protected void dragAndDropCustom(WebElement source, WebElement target) {
		logger.info("[ACTION] Drag (custom) {} → {}", describe(source), describe(target));
		waitForElementVisible(source);
		waitForElementVisible(target);
		HighlightUtil.highlight(driver, source);
		HighlightUtil.highlight(driver, target);
		mouse.dragAndDropCustom(source, target);
	}

	protected void dragAndDropByOffset(WebElement source, int xOffset, int yOffset) {
		logger.info("[ACTION] Drag {} by offset x={}, y={}", describe(source), xOffset, yOffset);
		waitForElementVisible(source);
		HighlightUtil.highlight(driver, source);
		mouse.dragAndDropByOffset(source, xOffset, yOffset);
	}

	/* =====================================================
	 * ADVANCED ACTIONS
	 * ===================================================== */

	protected void scrollToElement(WebElement element) {
		logger.info("[ACTION] Scroll to {}", describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		((JavascriptExecutor) driver)
				.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	protected void scrollToTop() {
		logger.info("[ACTION] Scroll to top");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);");
		waitForPageLoaded();
	}

	protected void scrollToBottom() {
		logger.info("[ACTION] Scroll to bottom");
		((JavascriptExecutor) driver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		waitForPageLoaded();		
	}

	/* =====================================================
	 * KEYBOARD ACTIONS
	 * ===================================================== */

	protected void ctrlSelectAll(WebElement element) {
		logger.info("[ACTION] Ctrl + A {}", describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		keyboard.ctrlSelectAll(element);
	}

	protected void ctrlCopy(WebElement element) {
		logger.info("[ACTION] Ctrl + C {}", describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		keyboard.ctrlCopy(element);
	}

	protected void ctrlPaste(WebElement element) {
		logger.info("[ACTION] Ctrl + V {}", describe(element));
		waitForElementVisible(element);
		HighlightUtil.highlight(driver, element);
		keyboard.ctrlPaste(element);
	}

	protected void shiftClick(WebElement element) {
		logger.info("[ACTION] Shift + Click {}", describe(element));
		waitForElementClickable(element);
		HighlightUtil.highlight(driver, element);
		keyboard.shiftClick(element);
	}

	protected void altClick(WebElement element) {
		logger.info("[ACTION] Alt + Click {}", describe(element));
		waitForElementClickable(element);
		HighlightUtil.highlight(driver, element);
		keyboard.altClick(element);
	}

	protected void pressKey(Keys key) {
		logger.info("[ACTION] Press key {}", key.name());
		keyboard.pressKey(key);
	}

	/* =====================================================
	 * SLIDER
	 * ===================================================== */

	protected void moveSliderByPercentage(WebElement slider, int percentage) {
		logger.info("[ACTION] Move slider {} to {}%", describe(slider), percentage);
		waitForElementVisible(slider);
		HighlightUtil.highlight(driver, slider);
		sliderUtil.moveSliderByPercentage(slider, percentage);
	}

	protected void setSliderValue(WebElement slider, int value) {
		logger.info("[ACTION] Set slider {} value = {}", describe(slider), value);
		waitForElementVisible(slider);
		HighlightUtil.highlight(driver, slider);
		sliderUtil.setSliderValue(slider, value);
	}

	protected void setSliderValueByJS(WebElement slider, int value) {
		logger.info("[ACTION] Set slider (JS) {} value = {}", describe(slider), value);
		waitForElementVisible(slider);
		HighlightUtil.highlight(driver, slider);
		sliderUtil.setSliderValueByJS(slider, value);
	}

	protected void assertSliderValue(WebElement slider, int expected) {
		logger.info("[VERIFY] Slider {} value = {}", describe(slider), expected);
		waitForElementVisible(slider);
		HighlightUtil.highlight(driver, slider);
		sliderUtil.assertSliderValue(slider, expected);
	}

	protected void assertSliderPercentage(WebElement slider, int expectedPercentage) {
		logger.info("[VERIFY] Slider {} percentage = {}", describe(slider), expectedPercentage);
		waitForElementVisible(slider);
		HighlightUtil.highlight(driver, slider);
		sliderUtil.assertSliderPercentage(slider, expectedPercentage);
	}

	protected void assertSliderValue(WebElement slider, int expected, int tolerance) {
		logger.info("[VERIFY] Slider {} value = {} ±{}", describe(slider), expected, tolerance);
		waitForElementVisible(slider);
		HighlightUtil.highlight(driver, slider);
		sliderUtil.assertSliderValue(slider, expected, tolerance);
	}

	// ==========================================
    // CORE ENGINE: SMART FINDER (Trái tim của Framework)
    // ==========================================
    /**
     * Tìm element linh hoạt bằng nhiều chiến lược XPath
     * @param text Text hiển thị, label, hoặc attribute (id/name/placeholder)
     * @param elementType Loại element: button, input, dropdown, checkbox, radio
     * @return WebElement đầu tiên tìm thấy, displayed và enabled
     * @throws RuntimeException nếu không tìm thấy element nào
     */
    private WebElement findElementByStrategies(String text, String elementType) {
        List<String> xpathStrategies;
        String cleanText = text.trim();

        switch (elementType.toLowerCase()) {
            case "button":
            case "link":
                xpathStrategies = Arrays.asList(
                        // 1. Button với text chính xác
                        "//button[normalize-space()='%s']",
                        // 2. Link (thẻ a) với text chính xác
                        "//a[normalize-space()='%s']",
                        // 3. Input type=submit/button
                        "//input[@type='submit' and @value='%s']",
                        "//input[@type='button' and @value='%s']",
                        // 4. Button chứa text (phòng trường hợp icon/span bên trong)
                        "//button[contains(normalize-space(), '%s')]",
                        "//a[contains(normalize-space(), '%s')]",
                        // 5. Tìm theo ID
                        "//*[@id='%s']",
                        // 6. Tìm theo aria-label
                        "//*[@aria-label='%s']"
                );
                break;

            case "input":
                xpathStrategies = Arrays.asList(
                        // 1. Input với placeholder chính xác
                        "//input[@placeholder='%s']",
                        // 2. Label nằm trước input (following)
                        "//label[normalize-space()='%s']/following::input[1]",
                        // 3. Label bao bọc input
                        "//label[normalize-space()='%s']//input",
                        // 4. Input theo name attribute
                        "//input[@name='%s']",
                        // 5. Input theo id attribute
                        "//input[@id='%s']",
                        // 6. Tìm input cùng container với label
                        "//label[normalize-space()='%s']/ancestor::div[1]//input"
                );
                break;

            case "dropdown":
                xpathStrategies = Arrays.asList(
                        // 1. Select theo ID
                        "//select[@id='%s']",
                        // 2. Select theo name
                        "//select[@name='%s']",
                        // 3. Label nằm trước select
                        "//label[normalize-space()='%s']/following::select[1]",
                        // 4. Label bao bọc select
                        "//label[normalize-space()='%s']//select"
                );
                break;

            case "checkbox":
            case "radio":
                xpathStrategies = Arrays.asList(
                        // 1. Input theo ID/name
                        "//input[@id='%s']",
                        "//input[@name='%s']",
                        // 2. Label bao bọc input
                        "//label[contains(normalize-space(),'%s')]//input[@type='checkbox' or @type='radio']",
                        // 3. Label nằm trước input
                        "//label[normalize-space()='%s']/following::input[@type='checkbox' or @type='radio'][1]",
                        // 4. Input nằm trước text
                        "//input[@type='checkbox' or @type='radio']/following-sibling::*[normalize-space()='%s']/preceding-sibling::input[1]"
                );
                break;

			// === MỚI 1: TEXT AREA (Ô nhập liệu nhiều dòng) ===
            case "textarea":
                xpathStrategies = Arrays.asList(
                    "//label[normalize-space()='%s']/following::textarea[1]", // Label ở trên
                    "//textarea[@placeholder='%s']",                          // Placeholder
                    "//textarea[@name='%s']",                                 // Name
                    "//textarea[@id='%s']"                                    // ID
                );
                break;

            // === MỚI 2: IMAGE (Ảnh/Icon) ===
            case "image":
                xpathStrategies = Arrays.asList(
                    "//img[@alt='%s']",       // Tìm theo Alt Text (Chuẩn Accessibility)
                    "//img[@title='%s']",     // Tìm theo Title
                    "//img[contains(@src, '%s')]" // Tìm theo tên file ảnh (nguy hiểm hơn nhưng đôi khi cần)
                );
                break;

            // === MỚI 3: TEXT VISIBILITY (Dùng để verify thông báo) ===
            case "text":
                xpathStrategies = Arrays.asList(
                    "//*[normalize-space()='%s']", // Tìm bất kỳ thẻ nào chứa text chính xác
                    "//*[contains(text(), '%s')]"  // Tìm thẻ chứa text (gần đúng)
                );
                break;

            default:
                throw new IllegalArgumentException("Chưa hỗ trợ loại element: " + elementType);
        }

        // Thử từng chiến lược
        for (String xpathFormat : xpathStrategies) {
            // String.format() never returns null, but explicitly assert for null-safety checker
            String actualXpath = Objects.requireNonNull(
                String.format(xpathFormat, cleanText),
                "XPath format should not be null"
            );
            try {
                List<WebElement> elements = driver.findElements(By.xpath(actualXpath));
                
                for (WebElement element : elements) {
                    try {
                        // Kiểm tra element có thể tương tác được
                        if (element.isDisplayed() && element.isEnabled()) {
                            logger.debug("✓ Found '{}' with text '{}' using: {}", elementType, cleanText, actualXpath);
                            return element;
                        }
                    } catch (StaleElementReferenceException e) {
                        // Element bị stale, bỏ qua và thử element tiếp theo
                        continue;
                    }
                }
            } catch (Exception ignored) {
                // XPath không match hoặc có lỗi khác, thử chiến lược tiếp theo
            }
        }

        // Không tìm thấy element nào
        logger.error("✗ Không tìm thấy {} với text/label: '{}'", elementType, cleanText);
        throw new RuntimeException("Không tìm thấy " + elementType + " có text/label là: " + cleanText);
    }

    // ==========================================
    // USER ACTIONS (Các hàm để Step gọi)
    // ==========================================

    /**
     * Click vào button/link theo text hiển thị hoặc ID
     */
    public void clickElement(String text) {
        logger.info("Clicking element: {}", text);
        WebElement element = wait.until(d -> {
            try {
                WebElement el = findElementByStrategies(text, "button");
                // Đảm bảo element vẫn clickable sau khi tìm thấy
                // Suppress warning: Selenium API không có @NonNull annotation
                @SuppressWarnings("null")
                WebElement clickable = ExpectedConditions.elementToBeClickable(el).apply(d);
                // Return directly - null-safety handled by wait.until() + requireNonNull below
                return clickable;
            } catch (Exception e) {
                return null; // Retry wait
            }
        });
        Objects.requireNonNull(element, "Element không tìm thấy sau timeout").click();
    }

    /**
     * Nhập text vào input field theo label hoặc placeholder
     */
    public void enterText(String label, String value) {
        logger.info("Enter text '{}' into field: {}", value, label);
        WebElement element = wait.until(d -> {
            try {
                WebElement el = findElementByStrategies(label, "input");
                if (el.isDisplayed() && el.isEnabled()) return el;
                return null;
            } catch (Exception e) {
                return null;
            }
        });
        Objects.requireNonNull(element, "Input field không tìm thấy sau timeout");
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Chọn giá trị trong dropdown theo label
     */
    public void selectDropdown(String label, String value) {
        logger.info("Select '{}' from dropdown: {}", value, label);
        WebElement element = wait.until(d -> {
            try {
                return findElementByStrategies(label, "dropdown");
            } catch (Exception e) {
                return null;
            }
        });
        Objects.requireNonNull(element, "Dropdown không tìm thấy sau timeout");
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    /**
     * Click vào checkbox/radio button theo label
     */
    public void clickRadioOrCheckbox(String label) {
        logger.info("Selecting checkbox/radio: {}", label);
        WebElement element = wait.until(d -> {
            try {
                WebElement el = findElementByStrategies(label, "checkbox");
                if (el.isDisplayed() && el.isEnabled()) return el;
                return null;
            } catch (Exception e) {
                return null;
            }
        });
        Objects.requireNonNull(element, "Checkbox/Radio không tìm thấy sau timeout");
        // Nếu chưa chọn thì mới click (để tránh toggle bỏ chọn)
        if (!element.isSelected()) {
            element.click();
        }
    }

	// ==========================================
    // ACTION ĐẶC BIỆT CHO TABLE (Xử lý lưới dữ liệu)
    // ==========================================
    
    /**
     * Tìm dòng chứa text (rowText) và click vào nút (actionButton) trên dòng đó
     * Ví dụ: Tìm dòng "Nguyen Van A" và click nút "Edit"
     */
    public void clickButtonInTableRecord(String rowText, String actionButtonText) {
        // Logic XPath:
        // 1. //tr[descendant::*[normalize-space()='Nguyen Van A']] -> Tìm dòng tr chứa text
        // 2. //button[normalize-space()='Edit'] -> Tìm nút Edit bên trong dòng tr đó
        String xpath = Objects.requireNonNull(
            String.format("//tr[descendant::*[normalize-space()='%s']]//button[normalize-space()='%s'] | //tr[descendant::*[normalize-space()='%s']]//a[normalize-space()='%s']", 
                         rowText, actionButtonText, rowText, actionButtonText),
            "XPath format should not be null"
        );
        
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            Objects.requireNonNull(btn, "Button không tìm thấy sau timeout").click();
            System.out.println("Clicked '" + actionButtonText + "' on row containing '" + rowText + "'");
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy nút '" + actionButtonText + "' trên dòng chứa '" + rowText + "'");
        }
    }

    // ==========================================
    // CÁC HÀM ACTION MỚI
    // ==========================================

    public void enterTextToTextArea(String label, String value) {
        WebElement element = wait.until(d -> findElementByStrategies(label, "textarea"));
        element.clear();
        element.sendKeys(value);
    }

    public void clickImage(String altText) {
        WebElement element = wait.until(d -> findElementByStrategies(altText, "image"));
        element.click();
    }
    
    public boolean isTextDisplayed(String text) {
        try {
            WebElement element = wait.until(d -> findElementByStrategies(text, "text"));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
