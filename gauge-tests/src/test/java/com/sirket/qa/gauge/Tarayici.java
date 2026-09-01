package com.sirket.qa.gauge;

import com.microsoft.playwright.*;
import com.thoughtworks.gauge.*;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

public class Tarayici {

    public static final String BASE_URL =
            System.getenv().getOrDefault("BASE_URL", "https://playwright.dev");

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    private static String uuid;

    public static Page sayfa() {
        return page;
    }

    @BeforeSuite
    public void suiteBaslat() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterSuite
    public void suiteBitir() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeScenario
    public void senaryoBaslat(ExecutionContext ctx) {
        context = browser.newContext();
        page = context.newPage();

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

        if (page != null) {
            Allure.getLifecycle().addAttachment(
                    basarisiz ? "Hata ekrani" : "Son ekran",
                    "image/png", "png",
                    new ByteArrayInputStream(page.screenshot()));
        }

        Allure.getLifecycle().updateTestCase(uuid,
                r -> r.setStatus(basarisiz ? Status.FAILED : Status.PASSED));
        Allure.getLifecycle().stopTestCase(uuid);
        Allure.getLifecycle().writeTestCase(uuid);

        if (context != null) context.close();
    }
}
