# Apache Solr Maven Plugin

[![Build Status](https://travis-ci.org/ffbit/solr-maven-plugin.png)](https://travis-ci.org/ffbit/solr-maven-plugin)

## Goal

Apache Solr Maven Plugin runs an Apache Solr instance during integration tests
or as a standalone instance for other test purposes.

Current version of the plugin supports only the following versions of
Apache Solr:

  * 4.2.0
  * 4.2.1
  * 4.3.0
  * 4.3.1

## Documentation

[Apache Solr Maven Plugin Documentation](http://ffbit.github.io/solr-maven-plugin/)

## Change log

[RELEASE-NOTES.md](RELEASE-NOTES.md)

## Usage

### Standalone Apache Solr server

Add plugin to your pom.xml

    <plugin>
        <groupId>com.ffbit.maven.plugins</groupId>
        <artifactId>solr-maven-plugin</artifactId>
        <version>0.0.7.1</version>
    </plugin>

Run

    mvn clean solr:run

Open <http://localhost:8983/> in your favourite browser

Or

### Integration tests

Add plugin to your pom.xml

    <plugin>
        <groupId>com.ffbit.maven.plugins</groupId>
        <artifactId>solr-maven-plugin</artifactId>
        <version>0.0.7.1</version>
        <executions>
            <execution>
                <id>Apache Solr Start</id>
                <phase>pre-integration-test</phase>
                <goals>
                    <goal>start</goal>
                </goals>
            </execution>
        </executions>
    </plugin>

Run

    mvn clean integration-test


## Status

Supports Apache Solr of version 4.2.0, 4.2.1, 4.3.0 and 4.3.1 **only**
