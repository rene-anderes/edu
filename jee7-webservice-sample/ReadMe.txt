JEE 6 WebService Example

Technologien:
- Java 1.8
- JEE 7
- Application Server WebLogic 12.2.1

Mittels dem Befehl 'mvn' wird die Applikation gebildet (clean install ist default).

Der Deploy mittels 'mvn -Pdeploy-weblogic' deployed das war auf einen WebLogic-Server

Mittels mvn clean generate-source -Pwsimport werden die Klassen des 'mitarbeiter.wsdl' generiert.

Mittels mvn clean install -Pdeveloper,wsimport,it wird das war-File auf den WebLogic deployed und Integrationstest gemacht.

WebServices
URL ?WSDL:
	http://localhost:7001/ws-sample/webservice/employees/1/employee?WSDL
	http://localhost:7001/ws-sample/MitarbeiterService?WSDL

REST-Service (das Verzeichnis /resources/* ist mit Basic Autorization geschützt)
	URL /application.wadl: http://localhost:7001/ws-sample/resources/application.wadl

HTML
	http://localhost:7001/ws-sample zeigt eine Liste aller registrierten Servlets / Services
