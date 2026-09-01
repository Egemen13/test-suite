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
}
