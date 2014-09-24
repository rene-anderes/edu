ReadMe zum Projekt JEE Employee

JDK 8 
 - Installieren

Glassfish 4
 - Installieren
 - Im pom.xml '<glassfish.home>[Installationspfad Glassfish]</glassfish.home>' entsprechend anpassen.
 - Alternative: Im [user.home]\.m2\settings.xml die Variable setzen
 - Glassfish starten ([Installationspfad Glassfish]\glassfish\bin\asadmin.bat start-domain domain1)
 - Im Browser auf die Glassfish-Administration zugreifen: http://localhost:4848
 - JDBC Resource einrichten, Name: jdbc/empl (Connection Pool: DerbyPool)
 
Derby: 
 - Derby wird mit Glassfish und/oder Java JDK mitinstalliert
 - Derby starten
    a) [Installationspfad Glassfish]\glassfish\bin\asadmin.bat start-database --dbhome javadb
    b) [Installationspfad JDK]\db\bin\startNetworkServer.bat 
	
Datenbank mit Testdaten anlegen:
 Im Verzeichnis:  ..\server\assembly\database 
 mvn flyway:clean
 und
 mvn flyway:migrate verify -P testdata 
 aufrufen

Projekt bauen:
 Im Stammverzeichnis 'mvn clean install -Djavax.xml.accessExternalSchema=all' aufrufen
 Sämtliche IT-Test's die via Arquillian ablaufen werden auf dem Glassfish deployed und via Glassfish getestet.

Projekt deploy
 Im Verzeichnis ..\server\assembly\ear
 mvn package
 aufrufen. Dadurch wird das entsprechende EAR-File gebildet.
 Wird 'mvn clean package -P deploy-glassfish' augerufen, wird das EAR-File im Glassfish-Container deployed.
	