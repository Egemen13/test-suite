package com.sirket.qa.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Epic("API")
@Feature("DemoQA Book Store")
class SmokeTest {

    private static final String BASE_URL =
            System.getProperty("base.url", "https://demoqa.com");

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kitap listesi 200 donmeli ve en az bir kitap icermeli")
    void kitapListesiDoner() {
        given()
                .filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .statusCode(200)
                .body("books.size()", org.hamcrest.Matchers.greaterThan(0));
    }

    @Test
    @Description("Kitap listesindeki kayitlar ISBN ve baslik icermeli")
    void kitapKayitlariZorunluAlanlariIcerir() {
        given()
                .filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .statusCode(200)
                .body("books[0].isbn", org.hamcrest.Matchers.not(org.hamcrest.Matchers.isEmptyOrNullString()))
                .body("books[0].title", org.hamcrest.Matchers.not(org.hamcrest.Matchers.isEmptyOrNullString()));
    }
}
