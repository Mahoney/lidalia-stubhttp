lidalia-http-2
=============

An experiment in modelling HTTP & creating an HTTP client in Scala.

Primarily this is a learning tool for me to consolidate my understanding
of the HTTP specification & Scala & experiment with some ideas I have about
the rich modelling of small types & composing HTTP request / response handling
using the decorator pattern.

To build you will need SNAPSHOT builds of lidalia-parent, lidalia-lang & scala-module:

for project in ( lidalia-parent lidalia-lang scala-module ) do git clone "git@github.com:Mahoney/$project.git"; cd "$project"; mvn clean install; cd ..; done
