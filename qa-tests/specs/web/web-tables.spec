DemoQA Web Tables
=================

Kayit tablosunun ekleme, arama, duzenleme ve silme davranislari.

TST_TABLO_0001 Yeni kayit eklenir
----------------------------------
Tags: web, smoke, TST_TABLO_0001

* Web Tables sayfasi acilir
* Yeni kayit formu acilir
* Kayit bilgileri "Ahmet" "Yilmaz" "ahmet.yilmaz@ornek.com" "34" "12000" "Test" girilir
* Kayit formu gonderilir
* "kayitSatiri" elementi "ahmet.yilmaz@ornek.com" degeri ile bulunmali

TST_TABLO_0002 Kayit aranabilir
--------------------------------
Tags: web, TST_TABLO_0002

* Web Tables sayfasi acilir
* "Cierra" icin arama yapilir
* "kayitSatiri" elementi "Cierra" degeri ile bulunmali

TST_TABLO_0003 Olmayan kayit icin sonuc donmez
-----------------------------------------------
Tags: web, dogrulama, TST_TABLO_0003

* Web Tables sayfasi acilir
* "kesinlikleboylebirkayityok" icin arama yapilir
* "tabloSatirlari" elementi bulunmamali

TST_TABLO_0004 Kayit silinir
-----------------------------
Tags: web, TST_TABLO_0004

* Web Tables sayfasi acilir
* Yeni kayit formu acilir
* Kayit bilgileri "Silinecek" "Kayit" "silinecek@ornek.com" "30" "9000" "Gecici" girilir
* Kayit formu gonderilir
* "kayitSatiri" elementi "silinecek@ornek.com" degeri ile bulunmali
* "satirSilButon" elementine "silinecek@ornek.com" degeri ile tikla
* "kayitSatiri" elementi "silinecek@ornek.com" degeri ile bulunmamali
