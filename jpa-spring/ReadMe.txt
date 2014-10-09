Beispielprojekt für JPA und Spring 4

Technologie
	Derby als Embedded Datanebank (Datenbankname "testDB")
	Spring 4
	JPA für Spring 4
	BeanValidation 1.0
Siehe http://docs.spring.io/spring-data/jpa/docs/current/reference/html/

Projekt bauen:
	1) mvn flyway:clean
	2) mvn flyway:migrate verify
	3) mvn clean install