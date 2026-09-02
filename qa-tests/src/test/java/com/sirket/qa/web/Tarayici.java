package com.sirket.qa.web;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

public class Tarayici {

    public static final String BASE_URL =
            System.getenv().getOrDefault("BASE_URL", "https://demoqa.com");

    private static final String GRID_URL = System.getenv("GRID_URL");

    private static WebDriver driver;
    private static WebDriverWait bekleyici;

    public static WebDriver surucu() {
        return driver;
    }

    public static WebDriverWait bekle() {
        return bekleyici;
    }

    @BeforeScenario(tags = {"web"})
    public void tarayiciAc() {
        ChromeOptions secenekler = new ChromeOptions();
        if (Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", "true"))) {
            secenekler.addArguments("--headless=new");
        }
        secenekler.addArguments("--window-size=1440,900");

        if (GRID_URL != null && !GRID_URL.isBlank()) {
            try {
                driver = new RemoteWebDriver(new URL(GRID_URL), secenekler);
            } catch (Exception e) {
                throw new RuntimeException("Grid'e baglanilamadi: " + GRID_URL, e);
            }
        } else {
            driver = new ChromeDriver(secenekler);
        }

        bekleyici = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterScenario(tags = {"web"})
    public void tarayiciKapat() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
