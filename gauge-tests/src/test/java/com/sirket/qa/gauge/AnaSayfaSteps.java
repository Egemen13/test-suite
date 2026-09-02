package com.sirket.qa.gauge;

import com.thoughtworks.gauge.Step;

import static org.assertj.core.api.Assertions.assertThat;

public class AnaSayfaSteps {

    @Step("Ana sayfaya git")
    public void anaSayfayaGit() {
        Tarayici.surucu().get(Tarayici.BASE_URL);
    }

    @Step("<yol> adresine git")
    public void adreseGit(String yol) {
        Tarayici.surucu().get(Tarayici.BASE_URL + yol);
    }

    @Step("Baslik <metin> icermeli")
    public void baslikIcermeli(String metin) {
        assertThat(Tarayici.surucu().getTitle()).contains(metin);
    }

    @Step("URL <metin> icermeli")
    public void urlIcermeli(String metin) {
        assertThat(Tarayici.surucu().getCurrentUrl()).contains(metin);
    }
}
