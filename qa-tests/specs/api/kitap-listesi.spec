DemoQA Book Store API
=====================

Book Store API'sinin kitap listeleme davranisi.

TST_API_0001 Kitap listesi doner
---------------------------------
Tags: api, smoke, TST_API_0001

* "/BookStore/v1/Books" adresine GET istegi at
* Yanit kodu "200" olmali
* Yanitta "books" alani bulunmali
* "books" listesi bos olmamali

TST_API_0002 ISBN ile tek kitap doner
--------------------------------------
Tags: api, TST_API_0002

* "/BookStore/v1/Book?ISBN=9781449325862" adresine GET istegi at
* Yanit kodu "200" olmali
* "isbn" alani "9781449325862" olmali
