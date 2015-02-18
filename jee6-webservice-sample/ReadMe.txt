JEE 6 WebService Example

Technologien:
- Java 1.7
- JEE 6
- JAX-WS - Metro als Implementation

Mittels dem Befehl 'mvn' wird die Applikation gebilder (clean install ist default).

Der Deploy mittels 'mvn -Pdeploy-weblogic' deployed das war auf einen WebLogic-Server

Mittels mvn clean generate-source -Pwsimport werden die Klassen des 'mitarbeiter.wsdl' generiert.

Mittels mvn clean install -Pdeveloper,wsimport,it wird das war auf den WebLogic deploed und Integrationstest gemacht.

Für den Deploy mittels Eclipse muss das beans.xml ins Verzeichnis [WebContent]/WEB-INF kopiert werden.