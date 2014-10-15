Einfaches Beispiel mit Archillian

Basis für dieses Beipiel liefert ein Archetype:

mvn --batch-mode archetype:generate -DarchetypeGroupId=org.javaee-samples -DarchetypeArtifactId=javaee7-arquillian-archetype -DgroupId=org.samples.javaee7.arquillian -DartifactId=arquillian

Das erstellte Projekt wurde nur leicht angepasst, u.a. damit default mittels Glassfish-Embedded getestet wird.

Der entsprechende Artikel von Arun Gupta ist hier zu finden:
https://weblogs.java.net/blog/arungupta/archive/2014/07/01/testable-java-ee-7-maven-archetype-using-arquillian-tech-tip-34
