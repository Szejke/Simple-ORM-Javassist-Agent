# Simple-ORM-Javassist-Agent
ENG:
Instructions for starting the application:
open the project in the compiler from the java-agent-javassist
Run the following commands in the terminal
- mvn package
- java -javaagent:agent/target/agent-0.1-SNAPSHOT.jar -jar other/target/other-0.1-SNAPSHOT.jar
Method-Javassist project
-The side project contains a prototype of how the mapping method works.


Helpful link:
http://tomsquest.com/blog/2014/01/intro-java-agent-and-bytecode-manipulation/

Requirements:
Maven
Java version 8




PL:
Instrukcja uruchomienia aplikacji:
Po otworzeniu w kompilatorze projektu z folderu java-agent-javassist
Wywołaj następujące polecenia w terminalu 
- mvn package
- java -javaagent:agent/target/agent-0.1-SNAPSHOT.jar -jar other/target/other-0.1-SNAPSHOT.jar


Projekt Method-Javassist
-Projekt poboczny zawiera prototyp działania metody mapowania.

Pomocny Link:
http://tomsquest.com/blog/2014/01/intro-java-agent-and-bytecode-manipulation/


Wymagania:
Maven
Java w wersji 8