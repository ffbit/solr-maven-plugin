# Release Notes

## v0.0.4

* Solr 4.3.1 support [#17](https://github.com/ffbit/solr-maven-plugin/pull/17)
* Support for Apache Solr 4.3.1 [#15](https://github.com/ffbit/solr-maven-plugin/pull/15)
* Refactored tests [#16](https://github.com/ffbit/solr-maven-plugin/pull/16) as a part of the Unit tests [#6](https://github.com/ffbit/solr-maven-plugin/pull/6) issue

## v0.0.3

* Site documentation without usage examples [#14](https://github.com/ffbit/solr-maven-plugin/pull/14) as a part of the Documentation [#13](https://github.com/ffbit/solr-maven-plugin/issues/13) issue
* Stop Jetty properly [#12](https://github.com/ffbit/solr-maven-plugin/issues/12)
* Help mojo test [#11](https://github.com/ffbit/solr-maven-plugin/pull/11)
* Fixed the annotation for the remote repositories list [#10](https://github.com/ffbit/solr-maven-plugin/pull/10)
* First approximation of Unit tests [#9](https://github.com/ffbit/solr-maven-plugin/pull/9) as a part of the Unit tests [#6](https://github.com/ffbit/solr-maven-plugin/pull/6) issue
* Make use of Maven Mojo Annotations [#8](https://github.com/ffbit/solr-maven-plugin/pull/8)

## v0.0.2

* External libraries support for Apache Solr 4.3.0 ([#3](https://github.com/ffbit/solr-maven-plugin/pull/3) and [#7](https://github.com/ffbit/solr-maven-plugin/pull/7))
* Travic CI build script ([#4](https://github.com/ffbit/solr-maven-plugin/pull/4) and [#5](https://github.com/ffbit/solr-maven-plugin/pull/5))

## v0.0.1

* Support for Apache Solr **only** of version 4.3.0 without external libraries
* Configuration properties
  * Solr version by &lt;solrVersion&gt;, default is **4.3.0**
  * Solr home by &lt;solrHome&gt;, default is **${project.build.directory}/solr**
  * Jetty context path by &lt;contextPath&gt;, default is **/**
  * Jetty port by &lt;port&gt;, default is **8983**
