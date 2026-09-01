package com.sirket.qa.web;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Web")
@Feature("Ana sayfa")
class HomePageTest {

    private static final String BASE_URL =
            System.getProperty("base.url", "https://playwright.dev");

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeEach
    void createContext() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext(TestInfo info) {
        if (page != null) {
            Allure.addAttachment(info.getDisplayName(), "image/png",
                    new ByteArrayInputStream(page.screenshot()), "png");
        }
        if (context != null) context.close();
    }

    @Test
    @Description("Ana sayfa acilmali ve baslik dogru olmali")
    void anaSayfaAcilir() {
        page.navigate(BASE_URL);
        assertTrue(page.title().contains("Playwright"));
    }

    @Test
    @Description("Docs sayfasi acilmali")
    void dokumantasyonAcilir() {
        page.navigate(BASE_URL + "/docs/intro");
        assertTrue(page.url().contains("docs"));
        assertTrue(page.locator("h1").first().isVisible());
    }
}
