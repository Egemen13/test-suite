# DemoQA - Text Box

Text Box formunun kullanici bilgilerini kaydetme ve zorunlu alan dogrulama davranislari.

## Gecerli kullanici bilgileriyle form gonderilir
* DemoQA Text Box formunu ac
* "#userName" alanina "Test Kullanici" yaz
* "#userEmail" alanina "test.kullanici@example.com" yaz
* "#currentAddress" alanina "Istanbul" yaz
* "#permanentAddress" alanina "Ankara" yaz
* Formu gonder
* Sonuc alaninda "Test Kullanici" gorunmeli
* Sonuc alaninda "test.kullanici@example.com" gorunmeli

## Gecersiz e-posta ile form gonderilemez
* DemoQA Text Box formunu ac
* "#userName" alanina "Test Kullanici" yaz
* "#userEmail" alanina "gecersiz-eposta" yaz
* Formu gonder
* "#userEmail" alani gecersiz olmali
