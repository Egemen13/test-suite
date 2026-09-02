package com.sirket.qa.web;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WebSteps {

    private static final Map<String, String> ELEMENTLER = yukle();

    private static Map<String, String> yukle() {
        Map<String, String> harita = new HashMap<>();
        try (InputStreamReader r = new InputStreamReader(
                WebSteps.class.getClassLoader().getResourceAsStream("elementler.json"))) {

            JsonObject kok = JsonParser.parseReader(r).getAsJsonObject();
            for (String grup : kok.keySet()) {
                JsonObject elemanlar = kok.getAsJsonObject(grup);
                for (Map.Entry<String, JsonElement> e : elemanlar.entrySet()) {
                    if (harita.containsKey(e.getKey())) {
                        throw new IllegalStateException(
                                "Element adi birden fazla grupta tanimli: " + e.getKey());
                    }
                    harita.put(e.getKey(), e.getValue().getAsString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("elementler.json okunamadi", e);
        }
        return harita;
    }

    private static By locator(String ad, Object... parametreler) {
        String tanim = ELEMENTLER.get(ad);
        if (tanim == null) {
            throw new IllegalArgumentException("Element deposunda yok: " + ad);
        }
        if (parametreler.length > 0) {
            tanim = String.format(tanim, parametreler);
        }
        if (tanim.startsWith("xpath=")) {
            return By.xpath(tanim.substring(6));
        }
        return By.cssSelector(tanim.replaceFirst("^css=", ""));
    }

    private WebElement gorunen(String ad, Object... p) {
        return Tarayici.bekle().until(
                ExpectedConditions.visibilityOfElementLocated(locator(ad, p)));
    }

    @Step("<yol> adresine git")
    public void adreseGit(String yol) {
        Tarayici.surucu().get(Tarayici.BASE_URL + yol);
    }

    @Step("<ad> elementine tikla")
    public void elementeTikla(String ad) {
        Tarayici.bekle().until(
                ExpectedConditions.elementToBeClickable(locator(ad))).click();
    }

    @Step("<metin> textini <ad> elemente yaz")
    public void elementeYaz(String metin, String ad) {
        WebElement e = gorunen(ad);
        e.clear();
        e.sendKeys(metin);
    }

    @Step("<ad> elementini kontrol et")
    public void elementiKontrolEt(String ad) {
        assertThat(gorunen(ad).isDisplayed()).isTrue();
    }

    @Step("<metin> texti <ad> elementinde gorunuyor mu kontrol et")
    public void textGorunuyorMu(String metin, String ad) {
        assertThat(gorunen(ad).getText()).contains(metin);
    }

    @Step("<ad> elementi gorunmemeli")
    public void elementGorunmemeli(String ad) {
        assertThat(Tarayici.surucu().findElements(locator(ad))
                .stream().anyMatch(WebElement::isDisplayed)).isFalse();
    }

    @Step("<ad> elementine <deger> degeri ile tikla")
    public void parametreliTikla(String ad, String deger) {
        Tarayici.bekle().until(
                ExpectedConditions.elementToBeClickable(locator(ad, deger))).click();
    }

    @Step("<ad> elementi <deger> degeri ile bulunmali")
    public void parametreliBulunmali(String ad, String deger) {
        assertThat(gorunen(ad, deger).isDisplayed()).isTrue();
    }

    @Step("<ad> elementi <deger> degeri ile bulunmamali")
    public void parametreliBulunmamali(String ad, String deger) {
        assertThat(Tarayici.surucu().findElements(locator(ad, deger))).isEmpty();
    }
}
