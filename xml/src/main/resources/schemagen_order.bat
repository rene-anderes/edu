@echo off
set JAVA_HOME=c:\Java-Entwicklung\jdk1.8.0_20
%JAVA_HOME%\bin\schemagen -cp "%JAVA_HOME%\lib\tools.jar;..\..\..\target\classes" org.anderes.edu.xml.jaxb.modeldriven.Customer
