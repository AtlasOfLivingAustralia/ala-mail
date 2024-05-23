# ala-mail

Java libraries and Grails plugins for handling mail delivery

Usage
-----

The current version of these libraries is: `1.0.0-SNAPSHOT`.

To ensure that various plugins and libraries and self-consistent, a project should use the same version for
each of the plugins and libraries that it consumes, eg. for a Grails project:

`gradle.properties`:
```gradle.properties
alaMailLibsVersion=1.0.0-SNAPSHOT
```

`build.gradle`:
```build.gradle
dependencies {
  implementation "au.org.ala:ala-mail:${alaMailLibsVersion}"
}
```

Components
----------

This project contains all the following previously separate ALA Grails plugins and libs:

- [ala-mail](ala-mail) - provide an implementation of the spring mail sender at integrates with AWS SES. Can also be 
used as a grails plugin.  