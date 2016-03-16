Lösung für die Aufgabe "Kochbuch"

Es können unterschiedliche Datenbanken angebunden werden.
Profile:

 derby-embedded 
 	-> Datenbankfile werden im Projektordner abgelegt (testDB)
 	
 derby-inmemory
 	-> Default-Profil
 
 mysql-local 
 	-> Es wird eine MySQL Datenbank auf Standardport 3306 verwendet
 	-> mittels mvn clean install -P mysql-local,create-schema-user wird das entsprechende Datenbankschema erstellt und der User für das Schema angelegt


Die Unit-Test's laufen mittels maven nicht durch. Dazu müssten die Test's mittels Maven Failsafe Plugin als Integrationstest gestartet werden (eigen JVM pro Test).