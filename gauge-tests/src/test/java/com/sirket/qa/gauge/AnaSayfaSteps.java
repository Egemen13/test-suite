package com.sirket.qa.gauge;

import com.microsoft.playwright.*;
import com.thoughtworks.gauge.Step;

import static org.assertj.core.api.Assertions.assertThat;

public class AnaSayfaSteps {

    @Step("Ana sayfaya git")
    public void anaSayfayaGit() {
        Tarayici.sayfa().navigate(Tarayici.BASE_URL);
    }

    @Step("<yol> adresine git")
    public void adreseGit(String yol) {
        Tarayici.sayfa().navigate(Tarayici.BASE_URL + yol);
    }

    @Step("Baslik <metin> icermeli")
    public void baslikIcermeli(String metin) {
        assertThat(Tarayici.sayfa().title()).contains(metin);
    }

    @Step("URL <metin> icermeli")
    public void urlIcermeli(String metin) {
        assertThat(Tarayici.sayfa().url()).contains(metin);
    }

    @Step("Sayfada <metin> metni gorunmeli")
    public void sayfadaGorunmeli(String metin) {
        assertThat(Tarayici.sayfa().getByText(metin, new Page.GetByTextOptions().setExact(true)).isVisible()).isTrue();
    }

    @Step("<metin> metnine tikla")
    public void metneTikla(String metin) {
        Tarayici.sayfa().getByText(metin, new Page.GetByTextOptions().setExact(true)).click();
    }

    @Step("<alan> alanina <deger> yaz")
    public void alanaYaz(String alan, String deger) {
        Tarayici.sayfa().locator(alan).fill(deger);
    }

    @Step("Formu gonder")
    public void formuGonder() {
        Tarayici.sayfa().locator("#submit").click();
    }

    @Step("Sonuc alaninda <metin> gorunmeli")
    public void sonucAlanindaGorunmeli(String metin) {
        assertThat(Tarayici.sayfa().locator("#output").textContent()).contains(metin);
    }

    @Step("<alan> alani gecersiz olmali")
    public void alanGecersizOlmali(String alan) {
        boolean gecerli = (Boolean) Tarayici.sayfa().locator(alan)
                .evaluate("element => element.checkValidity()");
        assertThat(gecerli).isFalse();
    }
}
