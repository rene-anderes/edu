ReadMe zum Projekt Employee

Glassfish 4
 - Installieren
 - Im pom.xml '<glassfish.home>[Installationspfad Glassfish]</glassfish.home>' entsprechend anpassen.
 - via settings.xml auch möglich
 - Glassfish starten
 
Derby: 
 - Derby wird mit Glassfish und/oder Java JDK mitinstalliert
 - Derby starten
    a) [Installationspfad Glassfish]\glassfish\bin\asadmin.bat start-database --dbhome javadb
    b) [Installationspfad JDK]\db\bin\startNetworkServer.bat 
	
Datenbank mit Testdaten anlegen:
	Im Verzeichnis:  ..\server\assembly\database 
	mvn flyway:migrate verify -P testdata aufrufen

Projekt bauen:
	Im Stammverzeichnis mvn clean install aufrufen
	
Sämtliche IT-Test's die via Arquillian ablaufen werden auf dem Glassfish deployed und via Glassfish getestet.