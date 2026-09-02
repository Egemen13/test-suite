package com.sirket.qa.gauge;

import com.thoughtworks.gauge.*;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class Tarayici {

    public static final String BASE_URL =
            System.getenv().getOrDefault("BASE_URL", "https://demoqa.com");

    private static WebDriver driver;
    private static WebDriverWait bekleyici;
    private static String uuid;

    public static WebDriver surucu() {
        return driver;
    }

    public static WebDriverWait bekle() {
        return bekleyici;
    }

    @BeforeScenario
    public void senaryoBaslat(ExecutionContext ctx) {
        ChromeOptions secenekler = new ChromeOptions();
        if (Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", "true"))) {
            secenekler.addArguments("--headless=new");
        }
        secenekler.addArguments("--window-size=1440,900");

        driver = new ChromeDriver(secenekler);
        bekleyici = new WebDriverWait(driver, Duration.ofSeconds(10));

        String specAdi = ctx.getCurrentSpecification().getName();
        uuid = UUID.randomUUID().toString();

        TestResult sonuc = new TestResult()
                .setUuid(uuid)
                .setName(ctx.getCurrentScenario().getName())
                .setLabels(List.of(
                        new Label().setName("suite").setValue(specAdi),
                        new Label().setName("feature").setValue(specAdi)));

        Allure.getLifecycle().scheduleTestCase(sonuc);
        Allure.getLifecycle().startTestCase(uuid);
    }

    @AfterScenario
    public void senaryoBitir(ExecutionContext ctx) {
        boolean basarisiz = ctx.getCurrentScenario().getIsFailing();

        if (driver != null) {
            Allure.getLifecycle().addAttachment(
                    basarisiz ? "Hata ekrani" : "Son ekran",
                    "image/png", "png",
                    new ByteArrayInputStream(
                            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        }

        Allure.getLifecycle().updateTestCase(uuid,
                r -> r.setStatus(basarisiz ? Status.FAILED : Status.PASSED));
        Allure.getLifecycle().stopTestCase(uuid);
        Allure.getLifecycle().writeTestCase(uuid);

        if (driver != null) driver.quit();
    }
}
