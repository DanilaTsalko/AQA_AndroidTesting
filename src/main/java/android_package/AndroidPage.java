package android_package;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class AndroidPage {
    private final AppiumDriver driver;
    private final WebDriverWait wait;
    private final Duration duration = Duration.ofSeconds(10);
    private final long time = duration.getSeconds();

    public AndroidPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, time);
    }

    private static final List<By> viewElements = List.of(
            MobileBy.AccessibilityId("Animation"),
            MobileBy.AccessibilityId("Auto Complete"),
            MobileBy.AccessibilityId("Buttons"),
            MobileBy.AccessibilityId("Chronometer"),
            MobileBy.AccessibilityId("Controls"),
            MobileBy.AccessibilityId("Custom"),
            MobileBy.AccessibilityId("Date Widgets"),
            MobileBy.AccessibilityId("Drag and Drop"),
            MobileBy.AccessibilityId("Expandable Lists"),
            MobileBy.AccessibilityId("Focus"),
            MobileBy.AccessibilityId("Gallery"),
            MobileBy.AccessibilityId("Game Controller Input"),
            MobileBy.AccessibilityId("Grid"),
            MobileBy.AccessibilityId("Hover Events"),
            MobileBy.AccessibilityId("ImageButton"),
            MobileBy.AccessibilityId("ImageSwitcher"),
            MobileBy.AccessibilityId("ImageView"),
            MobileBy.AccessibilityId("Layout Animation"),
            MobileBy.AccessibilityId("Layouts"),
            MobileBy.AccessibilityId("Lists"),
            MobileBy.AccessibilityId("Picker"),
            MobileBy.AccessibilityId("Popup Menu"),
            MobileBy.AccessibilityId("Progress Bar"),
            MobileBy.AccessibilityId("Radio Group"),
            MobileBy.AccessibilityId("Rating Bar"),
            MobileBy.AccessibilityId("Rotating Button"),
            MobileBy.AccessibilityId("ScrollBars"),
            MobileBy.AccessibilityId("Search View"),
            MobileBy.AccessibilityId("Secure View"),
            MobileBy.AccessibilityId("Seek Bar"),
            MobileBy.AccessibilityId("Spinner"),
            MobileBy.AccessibilityId("Splitting Touches across Views"),
            MobileBy.AccessibilityId("Switches"),
            MobileBy.AccessibilityId("System UI Visibility"),
            MobileBy.AccessibilityId("Tabs"),
            MobileBy.AccessibilityId("TextClock"),
            MobileBy.AccessibilityId("TextFields"),
            MobileBy.AccessibilityId("TextSwitcher"),
            MobileBy.AccessibilityId("Visibility"),
            MobileBy.AccessibilityId("WebView"),
            MobileBy.AccessibilityId("WebView2"),
            MobileBy.AccessibilityId("WebView3")
    );

    public void clickOnViews() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Views"))).click();
    }

    public void scrollToElement(By locator) {
        boolean elementFound = false;
        int attempts = 0;

        while (!elementFound && attempts < 5) {
            try {
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                element.click();
                elementFound = true;
            } catch (Exception e) {
                attempts++;
                System.out.println("Попытка " + attempts + ": Элемент не найден, прокручиваем экран...");
                driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
                System.out.println("Прокрутка выполнена.");

                if (attempts >= 5) {
                    System.out.println("Не удалось найти элемент после 5 попыток.");
                    throw e;
                }
            }
        }
    }

    public void clickAllViewElements() {
        int openedTabsCount = 0;

        for (By locator : viewElements) {
            try {
                scrollToElement(locator);
                System.out.println("Клик по элементу: " + locator);
                openedTabsCount++;
                new WebDriverWait(driver, 5);
                driver.navigate().back();
            } catch (Exception e) {
                System.out.println("Не удалось кликнуть по элементу: " + locator);
            }
        }

        if (openedTabsCount == 42) {
            System.out.println("Тест прошел успешно. Количество открытых вкладок: " + openedTabsCount);
        } else {
            System.out.println("Ошибка: Ожидалось 42 вкладки, но открыто: " + openedTabsCount);
            throw new AssertionError("Неверное количество вкладок. Ожидалось 42, а открыто " + openedTabsCount);
        }
    }

    public void openDateTimeDialog() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Views"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Date Widgets"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("1. Dialog"))).click();
    }

    public void setDateTime() {
        openDateTimeDialog();

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        int day = tomorrow.getDayOfMonth();

        wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("change the date"))).click();

        MobileElement dayElement = (MobileElement) wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//android.view.View[contains(@content-desc, '" + day + "')]"))
        );
        dayElement.click();
        driver.findElement(MobileBy.id("android:id/button1")).click();

        wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("change the time (spinner)"))).click();

        MobileElement hourSpinner = (MobileElement) wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.NumberPicker[1]//android.widget.EditText"))
        );
        hourSpinner.clear();
        hourSpinner.sendKeys("11");

        MobileElement minuteSpinner = (MobileElement) wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.NumberPicker[2]//android.widget.EditText"))
        );
        minuteSpinner.clear();
        minuteSpinner.sendKeys("11");

        MobileElement pmButton = (MobileElement) wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@text=\"PM\"]"))
        );
        pmButton.click();
        driver.findElement(MobileBy.id("android:id/button1")).click();

        System.out.println("Дата и время успешно установлены: " + tomorrow + " 11:11 PM");
    }

    public void openTextSwitcherDialog() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Views"))).click();
        By locator = MobileBy.AccessibilityId("TextSwitcher");
        scrollToElement(locator);
    }

    public int incrementIfMatch(MobileElement textElement, int clickCount) {
        String numberText = textElement.getText();
        int numberValue = Integer.parseInt(numberText);

        if (clickCount == numberValue) {
            System.out.println("Нажатие: " + clickCount);
            clickCount++;
        }

        return clickCount;
    }

    public void letsClick() {
        int clickCount = 0;
        int startNum = 0;

        openTextSwitcherDialog();

        for (int i = 0; i < 10; i++) {
            MobileElement textElement = (MobileElement) wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//android.widget.TextView[@text=\"" + startNum + "\"]"))
            );

            clickCount = incrementIfMatch(textElement, clickCount);

            wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Next"))).click();

            startNum++;
        }
    }
}
