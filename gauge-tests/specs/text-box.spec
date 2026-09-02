DemoQA Text Box
===============

Text Box formunun kullanici bilgilerini dogru islemesi.

TST_TEXTBOX_0001 Gecerli bilgilerle form gonderilir
----------------------------------------------------
Tags: web, smoke, TST_TEXTBOX_0001

* Text Box sayfasi acilir
* Formu "Test Kullanici" ve "test.kullanici@ornek.com" ile doldur
* Formu gonder
* "sonucAlani" elementini kontrol et
* "Test Kullanici" texti "sonucAd" elementinde gorunuyor mu kontrol et

TST_TEXTBOX_0002 Gecersiz e-posta ile form gonderilmez
-------------------------------------------------------
Tags: web, dogrulama, TST_TEXTBOX_0002

* Text Box sayfasi acilir
* Formu "Test Kullanici" ve "gecersiz-eposta" ile doldur
* Formu gonder
* "sonucAlani" elementi gorunmemeli
