# apiJerseyV2
Project: apiJerseyV2

Version: 0.1.1

How to run using maven?

1. Generar el proyecto:

mvn archetype:generate -DgroupId=apiJerseyV2 -DartifactId=apiJerseyV2 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

2. Copiar las clases dentro de la carpeta src/main ( java y webapp )

3. Compilar

mvn clean install

4. Correr utilizando jetty.

mvn jetty:run

// En un navegador, abrir el archivo test.html
// Desde jetty las URL son distintas. Eg: http://localhost:8080/rest/otherHTML


