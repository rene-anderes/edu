Projekt REST-Sample

Beschreibung
Ein einfaches Beispiel für eine Rest-Schnittstelle.
Es sind unterschiedliche Schnittstellen verfügbar:
../services/helloworld
../services/users/{username}  
../services/employees
../services/recipes
../services/recipes/{RecipeId}
../services/projects
../services/projects/{id}

Voraussetzungen
- JEE-Application-Server installiert und läuft (z.B. Glassfish)

Projekt bauen
Es handelt sich um ein Maven-Projekt
Mittels 'mvn clean package' wird ein WAR-File mit dem Namen 'rest-sample.war' erzeugt.
Dieses WAR-File befindet sich im 'target'-Verzeichnis des Maven-Projekts.

Deploy
Dieses WAR-File kann nun manuell auf dem Applikation Server deployed werden.
Beispiel Glassfish:
- http://localhost:4848 im Browser aufrufen
- "Deploy an Application" anwählen
- "Durchsuchen" anwählen und das obige WAR-File auswählen.
- Bitte "context root" merken
- mit "OK" wird das deployment abgeschlossen
- die Applications-List wird angezeigt, dort sollte das entsprechende Projekt ersichtlich sein.

Zugriff
Mittels z.B. SoapUI kann nun auch die REST-Schnittstelle zugriffen werde.
Bspw.: http://localhost:8080/rest-sample/services/helloworld

Für SoapUI existiert ein SoapUI-Projekt (Datei: REST-Project-soapui-project.xml) mit allen Requests die möglich sind.
Beachte: Server und Port überprüfen und allenfalls anpassen.

JUnit-Test
Mittels Jersey-Test-Framework werden die Schnittstellen getestet.

Integrationstest
Der Integrationstest "HelloWorldResourceTest" läuft, wenn die Applikation deployed ist.
Ist dieses Voraussetzung erfüllt, können die entsprechenden @Ignore-Annotations entfernt, die Variable 'uri' angepasst und der Test kann nun als JUnit-test gestartet werden. Dieser müsste nun Grün werden.


Entwicklungsumgebung Eclipse
Natürlich kann auch aus Eclipse heraus die Applikation deployed werden.
Dazu notwendig sind:
- Glassfish-Plugin im Eclipse
- mittels "Add and Remove" das Projekt Glassfish bekannt machen
- Publish des Projekts

