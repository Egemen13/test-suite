package com.sirket.qa.web;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Web")
@Feature("DemoQA Text Box")
class TextBoxTest {

    private static final String BASE_URL =
            System.getProperty("base.url", "https://demoqa.com");

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
    @Description("Kullanici Text Box formunu doldurup sonucu gorebilmeli")
    void kullaniciBilgileriniGonderebilir() {
        page.navigate(BASE_URL + "/text-box");

        page.locator("#userName").fill("Test Kullanici");
        page.locator("#userEmail").fill("test.kullanici@example.com");
        page.locator("#currentAddress").fill("Istanbul");
        page.locator("#permanentAddress").fill("Ankara");
        page.locator("#submit").click();

        assertTrue(page.locator("#output #name").textContent().contains("Test Kullanici"));
        assertTrue(page.locator("#output #email").textContent().contains("test.kullanici@example.com"));
    }
}
