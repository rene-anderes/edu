Lösung für die Aufgabe "Kochbuch"

Es können unterschiedliche Datenbanken angebunden werden.
Profile:

 derby-embedded 
 	-> Datenbankfiles werden im Root des Projektordners abgelegt (Verzeichnis: cookbookDatabase)
 	-> Default-Profil
 	
 derby-inmemory
	-> Optional, wird zur Zeit nicht verwendet. 
	 
 mysql-local 
 	-> Es wird eine MySQL Datenbank auf Standardport 3306 verwendet
 	-> mittels mvn clean install -P mysql-local,create-schema-user wird das entsprechende Datenbankschema erstellt und der User für das Schema angelegt

Testing
Mittels Maven laufen alle Tests (wie es sein sollte) duch.
Um Tests mittels Eclipse auszuführen kann mittels mvn clean install -P derby-embedded,create-tables -DskipTests=true die entsprechende Datenbank mit Testdaten gefüllt (nach dem Löschen und der Neuerstellung)