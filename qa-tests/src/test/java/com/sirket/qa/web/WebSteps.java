package com.sirket.qa.web;

import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.InputStream;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class WebSteps {

    private static final Properties ELEMENTLER = yukle();

    private static Properties yukle() {
        Properties p = new Properties();
        try (InputStream in = WebSteps.class
                .getClassLoader().getResourceAsStream("elementler.properties")) {
            if (in == null) throw new IllegalStateException("elementler.properties bulunamadi");
            p.load(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    private static By locator(String ad) {
        String secici = ELEMENTLER.getProperty(ad);
        if (secici == null) {
            throw new IllegalArgumentException("Element deposunda yok: " + ad);
        }
        return By.cssSelector(secici);
    }

    private WebElement gorunenElement(String ad) {
        return Tarayici.bekle().until(
                ExpectedConditions.visibilityOfElementLocated(locator(ad)));
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
        WebElement e = gorunenElement(ad);
        e.clear();
        e.sendKeys(metin);
    }

    @Step("<ad> elementini kontrol et")
    public void elementiKontrolEt(String ad) {
        assertThat(gorunenElement(ad).isDisplayed()).isTrue();
    }

    @Step("<metin> texti <ad> elementinde gorunuyor mu kontrol et")
    public void textGorunuyorMu(String metin, String ad) {
        assertThat(gorunenElement(ad).getText()).contains(metin);
    }

    @Step("<ad> elementi gorunmemeli")
    public void elementGorunmemeli(String ad) {
        assertThat(Tarayici.surucu().findElements(locator(ad))
                .stream().anyMatch(WebElement::isDisplayed)).isFalse();
    }
}
