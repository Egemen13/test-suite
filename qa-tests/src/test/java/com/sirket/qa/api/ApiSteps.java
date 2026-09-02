package com.sirket.qa.api;

import com.thoughtworks.gauge.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiSteps {

    public static final String BASE_URL =
            System.getenv().getOrDefault("API_BASE_URL", "https://demoqa.com");

    private static Response yanit;

    @Step("<yol> adresine GET istegi at")
    public void getIstegiAt(String yol) {
        yanit = given().when().get(BASE_URL + yol);
    }

    @Step("Yanit kodu <kod> olmali")
    public void yanitKodu(int kod) {
        assertThat(yanit.statusCode()).isEqualTo(kod);
    }

    @Step("Yanitta <alan> alani bulunmali")
    public void alanBulunmali(String alan) {
        Object deger = yanit.jsonPath().get(alan);
        assertThat(deger).isNotNull();
    }

    @Step("<alan> listesi bos olmamali")
    public void listeBosOlmamali(String alan) {
        assertThat(yanit.jsonPath().getList(alan)).isNotEmpty();
    }

    @Step("<alan> alani <deger> olmali")
    public void alanDegeri(String alan, String deger) {
        assertThat(yanit.jsonPath().getString(alan)).isEqualTo(deger);
    }
}
