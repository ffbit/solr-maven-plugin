# Release Notes

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
