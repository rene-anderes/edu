Dokumentation

Damit dieses Beispiel funktioniert, sollte folgendermassen vorgegangen werden
1. Glassfish 4 installiert sein
2. [glassfish_installation]\glassfish\modules\org.eclipse.persistence.moxy.jar ersetzen (sonst Fehlermeldung "BeanValidationHelper not found)
3. JavaDB/Derby aus der Glassfish-Distribution gestartet werden
	a) Möglichkeit 1: as-install/bin/asadmin start-database --dbhome as-install-parent/javadb
	b) Möglichkeit 2: %JAVA_HOME%\db\bin\startNetworkServer
4. Glassfish 4 starten, Beispiel: as-install /bin/asadmin start-domain
5. Beispiel deployen
6. REST-Url: http://localhost:8080/jee7-sample/rest/persons
 
Glassfish Quick-Start: https://glassfish.java.net/docs/4.0/quick-start-guide.pdf
Glassfish Administration-Guide: https://glassfish.java.net/docs/4.0/administration-guide.pdf

Beschreibung
Als Datenbank wird die mit JEE7 vorgegebene Default-Datasource verwendet (siehe persistence.xml --> java:comp/DefaultDataSource).
Bei Glassfish 4 wird dazu eine JDBC-Connection zu der Derby Datanbank "sun-appserv-samples" aufgebaut (wenn diese nicht existiert, wird sie durch Glassfish zuerst erzeugt).
