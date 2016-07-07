Dokumentation

Damit dieses Beispiel funktioniert, sollte folgendermassen vorgegangen werden
1. Glassfish 4 installiert sein
2. JavaDB/Derby aus der Glassfish-Distribution gestartet werden, Beispiel: as-install/bin/asadmin start-database --dbhome as-install-parent/javadb
3. Glassfish 4 gestartet werden, Beispiel: as-install /bin/asadmin start-domain
4. Beispiel deployen
5. REST-Url: http://localhost:8080/jee7-sample/rest/persons
 
Glassfish Quick-Start: https://glassfish.java.net/docs/4.0/quick-start-guide.pdf
Glassfish Administration-Guide: https://glassfish.java.net/docs/4.0/administration-guide.pdf

Beschreibung
Als Datenbank wird die mit JEE7 vorgegebene Default-Datasource verwendet (siehe persistence.xml --> java:comp/DefaultDataSource).
Bei Glassfish 4 wird dazu eine JDBC-Connection zu der Derby Datanbank "sun-appserv-samples" aufgebaut (allenfalls zuerst erzeugt).