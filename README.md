# apiJerseyV2
Project: apiJerseyV2

Version: 1.0.1

How to run using maven?

1. Clonar esta branch

git clone --single-branch -b herokuVersion https://github.com/ccantero/apiJerseyV2.git

2. Compilar

mvn clean install

3. Correr utilizando jetty.

mvn jetty:run

// En un navegador, abrir el archivo test.html
// Desde jetty las URL son distintas. Eg: http://localhost:8080/otherHTML

// Usando Heroku

https://apieventos-g5.herokuapp.com/myresource

https://apieventos-g5.herokuapp.com/otherHTML


