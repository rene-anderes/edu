Beispielprojekt Log4J2 - JPA Logging

Die Log-Informationen werden in die Datenbank geschrieben.

MySQL Datenbank
Datenbank-Schema und User (logging) erstellen:
	mvn clean install -DskipTests=true -P mysql-local,mysql-createDatabaseAndUser
Datenbank Tables erstellen
	mvn clean install -DskipTests=true -P mysql-local,drop-create-table

Derby-Embedded Datenbank
Datenbank Tables erstellen
	mvn clean install -DskipTests=true -P derby-embedded,drop-create-table

	
Projekt kann nun gebildet werden (default ist dabei die Derby-Datenbank)
	mvn clean install
	
Mittels
	mvn clean install -P mysql-local
werden die gleichen Test's die Logg-Meldungen in die MySQL Datenbank schreiben.
	
Die Test's erzeugen entsprechende Log-Meldungen in der Datenbank


Log4j2 und JPAAppender
Diese Lösung erzeugt eine Tabelle und jede Log-Meldung wird in eine Zeile geschreiben. Dies ist aus Performance-Sicht die schnellste Lösung.

Will man die Daten mittels JPA auch wieder auslesen, so sind die JSON-Converter zu verwenden:

    @Convert(converter = ContextMapJsonAttributeConverter.class)
    public Map<String, String> getContextMap() {
        return this.getWrappedEvent().getContextMap();
    }
    
    @Convert(converter = ContextStackJsonAttributeConverter.class)
    public ThreadContext.ContextStack getContextStack() {
        return this.getWrappedEvent().getContextStack();
    }


Werden dieses Converter eingesetzt ist folgende Dependency ebenfalls notwendig:

	<dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.7.3</version>
    </dependency>
    
Informationen zum JPA-Appender: https://logging.apache.org/log4j/2.x/manual/appenders.html#JPAAppender

