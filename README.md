# Simple Calculator Framework
============

[![Build Status](https://travis-ci.org/glorund/calculator.svg?branch=master)](https://travis-ci.org/glorund/calculator)

============
build 
mvn clean build

============
run
mvn spring-boot:run

============
Configuration
File src/main/resources/application.properties currently contains following formula:
    formula=X1+X2/(X3*6.1)-5

Fill free to change it.
Supported operators +-/* ^(power operator)
You can use brackets ()
Unary - is unsupported. Nevertheless, you can use negative numbers as paramaters.
You can use constants and variables as arguments. Currently variables will be substituted by position in REST call.
So, X1+X2/(X3*6.1)-5  and X1+X1/(X1*6.1)-5 and a+b/(b*6.1)-5 and c+c/(c*6.1)-5 will produce same results.
 
REST example:
 POST http://localhost:8080/calc
 JSON: 
 [12.4,0.2,98.765e-1]

 Result will be: 7.403319686654777
 