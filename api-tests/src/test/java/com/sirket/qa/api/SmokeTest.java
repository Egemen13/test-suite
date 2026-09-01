package com.sirket.qa.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Epic("Smoke")
@Feature("Kullanici servisi")
class SmokeTest {

    private static final String BASE_URL =
            System.getProperty("base.url", "https://jsonplaceholder.typicode.com");

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kullanici listesi 200 donmeli")
    void kullaniciListesiDoner() {
        given()
                .filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }

    @Test
    @Description("Tek kullanici 200 donmeli")
    void tekKullaniciDoner() {
        given()
                .filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .when()
                .get("/users/1")
                .then()
                .statusCode(200);
    }
}