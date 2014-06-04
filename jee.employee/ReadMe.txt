ReadMe zum Projekt Employee

Domäne für WebLogic aufsetzen:
	..\domain_setup_scripts\bin\create_domain.cmd

Weblogic starten: 
	..\domains\edu_base_domain\startWebLogic.cmd
	
Sämtliche IT-Test's die via Arquillian ablaufen werden auf den WebLogic deployed und via WebLogic getestet.

Es muss eine locale Oracle-XE Installation vorhanden sein. 
Darin sollte der User developer mit Passwort developer angelegt sein.

Datenbank mit Testdaten anlegen:
	Im Verzeichnis:  ..\server\assembly\database 
	mvn flyway:migrate verify -P testdata aufrufen

Projekt bauen:
	Im Stammverzeichnis mvn clean install aufrufen