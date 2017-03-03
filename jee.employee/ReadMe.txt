ReadMe zum Projekt JEE Employee

JDK 8 
 - Installieren

Glassfish 4
 - Installieren
 - EclipseLink auf Version 2.6 aktualisieren (siehe https://eclipse.org/eclipselink/releases/2.6.php - OSGI Bundle)
 	(Achtung, mit Version 2.6.0 funktioniert die REST-Schnittstelle nicht mehr richtig --> Version 2.6.x abwarten
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

Maven
Im settings.xml die Variable 'glassfish.home' einrichten: Zeigt auf das Installationsverzeichnis von Glassfish 4

Projekt bauen:
 Im Stammverzeichnis 'mvn clean install -Djavax.xml.accessExternalSchema=all' aufrufen
 Sämtliche IT-Test's die via Arquillian ablaufen werden auf dem Glassfish-Server deployed und via Glassfish getestet.
 Dazu muss die Datenbank eingerichtet und gestartet und der Glassfish-Server gestartet sein.

Projekt deploy
 Im Verzeichnis ..\server\assembly\ear
 mvn package
 aufrufen. Dadurch wird das entsprechende EAR-File gebildet.
 Wird 'mvn clean package -P deploy-glassfish' augerufen, wird das EAR-File im Glassfish-Container deployed.

In Eclipse sollte für das Module "Domain" das JPA Facet eingerichtet werden 
und das statische weaving eingeschaltet werden. Nur so werde alle Test's korrekt durchgeführt.
