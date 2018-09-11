# apiJerseyV2
# Project: apiJerseyV2
# Version: 0.1.1
mvn archetype:generate -DgroupId=apiJerseyV2 -DartifactId=apiJerseyV2 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
mvn clean install
mvn jetty:run

# En un navegador, abrir el archivo test.html
# Desde jetty las URL son distintas. Eg: http://localhost:8080/rest/otherHTML


