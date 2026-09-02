package com.sirket.qa.gaugeapi;

import com.thoughtworks.gauge.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class KitapListesiSteps {

    private static final String BASE_URL =
            System.getenv().getOrDefault("BASE_URL", "https://demoqa.com");

    private Response yanit;

    @Step("Kitap listesini getir")
    public void kitapListesiniGetir() {
        yanit = given()
                .filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .when()
                .get("/BookStore/v1/Books");
    }

    @Step("Yanit durum kodu <kod> olmali")
    public void durumKoduOlmali(String kod) {
        assertThat(yanit.statusCode()).isEqualTo(Integer.parseInt(kod));
    }

    @Step("Kitap listesi bos olmamali")
    public void kitapListesiBosOlmamali() {
        assertThat(yanit.jsonPath().getList("books")).isNotEmpty();
    }

    @Step("Ilk kitabin ISBN ve basligi dolu olmali")
    public void ilkKitapZorunluAlanlariIcerir() {
        assertThat(yanit.jsonPath().getString("books[0].isbn")).isNotBlank();
        assertThat(yanit.jsonPath().getString("books[0].title")).isNotBlank();
    }
}
