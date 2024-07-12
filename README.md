# A projekt célja

Felvételi feladata

# Kiírás:

Készítsen egy személyeket nyilvántartó, karbantartó Spring Boot alkalmazást. A feladathoz tartozó
adatbázis tartalmazzon 3 táblát. Személyek, címek, elérhetőségek. Egy személynek maximum kettő címe
lehet (állandó, ideiglenes), egy címhez több elérhetőség (email, telefon, stb.) tartozhat.

Az alkalmazás feladatai:

* Adatok lekérdezése,
* Adatok rögzítése, módosítása, törlése, hibakezeléssel,
* Tesztesetek írása. 

Kérem a következőket publikálja GitHubon:

* Az elkészült program forráskódját, a készítés folyamatának áttekinthetősége miatt ne egy blokkban feltöltve, 
* Az adatbázis létrehozásához szükséges DDL-t,
* A kiinduló adatok betöltéséhez szükséges DML-t.

A feladatot Java 17 és MS SQL 2019 környezetben fogjuk ellenőrizni.

# Telepítés

## Adatbázis

A config a `\src\main\resources\application.properties` pathon,
 illetve a teszt adatbázis a `\src\test\resources\application.properties` pathon érhető el és módosítható.

Az sql könyvtárban található a: 
 * `\sql\DDL.sql` az adatrbázis séma
 * `\sql\DML_Product.sql` az éles adatbázis tartalma
 * `\sql\DML_Test.sql` a test adatbázis tartalma

## Fordítás

A telepítés mavennel történik: `mvn clean install`

# Futtatás

Parancssorból: `java -jar target\personalregistry-1.0-SNAPSHOT.jar`, vagy IDE-ből.

Látogassa meg a futó alkalmazást böngészőböl: http://localhost:8080/