# Apache Solr Maven Plugin

[![Build Status](https://travis-ci.org/ffbit/solr-maven-plugin.png)](https://travis-ci.org/ffbit/solr-maven-plugin)

## Goal

Run Apache Solr Server for Integration Tests

## Usage

### Standalone Apache Solr server

Add plugin to your pom.xml

    <plugin>
        <groupId>com.ffbit.maven.plugins</groupId>
        <artifactId>solr-maven-plugin</artifactId>
        <version>0.0.1</version>
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
        <version>0.0.1</version>
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

Supports Apache Solr **only** of version 4.3.0
