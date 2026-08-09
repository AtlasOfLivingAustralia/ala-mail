# ala-mail [![Build Status](https://app.travis-ci.com/AtlasOfLivingAustralia/ala-mail.svg?branch=develop)](https://app.travis-ci.com/AtlasOfLivingAustralia/ala-mail)

## Usage

Current release version:

```groovy
implementation 'au.org.ala:ala-mail:3.0.0'
```

Current development version:

```groovy
implementation 'au.org.ala:ala-mail:3.0.0-SNAPSHOT'
```

This version targets Grails `7.1.1`.

## Description
The `ala-mail` library provides an implementation of `org.springframework.mail.javamail.JavaMailSender` that delivery 
via AWS Simple Email Service (SES).

## Setup
To include the `ala-mail` library in your application.

### Spring Boot

By default, no spring beans will be added to the context. 
When the configuration `mail.ses.enabled` = `true` the following beans are available:

 - `mailSender`: `org.springframework.mail.javamail.JavaMailSender`
 - `awsEmailService`: `com.amazonaws.services.simpleemail.AmazonSimpleEmailService`

### Grails

As with Spring Boot the `mailSender` bean will be available if enabled.

Using with the [grails-mail](https://github.com/grails/grails-mail) plugin. 

The `grails-mail` plugin uses the `mailSender` to send mail, when enabled it will deliver via AWS SES.

## Configuration

 - `mail.ses.enabled`: set to true to enable AWS SES mail sender
 - `mail.ses.configSet`: (optional) the name of an [AWS SES configuration set](https://docs.aws.amazon.com/ses/latest/dg/using-configuration-sets.html)
 - `mail.ses.region`: (optional) the AWS region
example:
```yaml
mail:
  ses:
    enabled: true
    configSet: my-config-set
    region: AP-SOUTHEAST-2
```

## Building

This repository is a single-project Gradle library build rooted at this directory.

```sh
./gradlew clean build
./gradlew publishToMavenLocal
```

## Changelog

- **Version 1.0.0**
  - Initial release 
- **Version 2.0.0**
  - Update to Grails 7.1.1
- **Version 3.0.0**
  - Restructure the Gradle build as a root-project library
