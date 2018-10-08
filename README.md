# apiJerseyV2

How to test

Go to: [logon_V1](http://localhost:8080/jsp/logon_V1.jsp "link title")


Test with usuario:password

**Issues**

* No pide authenticacion luego del primer login ( Queda en la cookie )
* No se puede forzar Authentication Header 
* [Logon.html](http://localhost:8080/jsp/logon.html "link title") No funciona el js
* Passwords guardadas como plain / text src/resources/
* Siempre hay que borrar las cookies para que deje de mandar el Authentication Header


------------------------------------------------------------------

## Installacion

### Desde linea de comandos [ Ubuntu ]

```sh
$ git clone https://github.com/ccantero/apiJerseyV2.git
$ mvn clean install
$ export EVENTBRITE_KEY=TACSYYYY2CGRUPO#
$ mvn jetty:run
```

### Open your web browser:

[Login Page](http://localhost:8080/ "link title")


### Run on eclipse

Run using Maven Build

Colocar como goal: **jetty:run**

![](https://image.ibb.co/bvu29K/jetty_run_1.jpg)

Setear la variable de entorno  **EVENTBRITE_KEY**

![](https://image.ibb.co/jc5W3e/jetty_run_2.jpg)

## Heroku UAT Version

[Login Page](https://apieventos-g5.herokuapp.com/ "link title")
