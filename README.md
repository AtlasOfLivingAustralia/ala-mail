# ala-mail [![Build Status](https://app.travis-ci.com/AtlasOfLivingAustralia/ala-mail.svg?branch=develop)](https://app.travis-ci.com/AtlasOfLivingAustralia/ala-mail)
## Usage
```
implementation 'au.org.ala:ala-mail:1.0.0-SNAPSHOT'
```

## Description
The `ala-mail` library provides an implementation of `org.springframework.mail.javamail.JavaMailSender` that delivery 
via AWS Simple Email Service (SES).

## Setup
To include the `ala-mail` library in your application.

### Spring Boot

By default, no spring beans will be added to the context. \
When the configuration `mail.ses.enabled` = `true` the following beans are available:

 - `mailSender`: `org.springframework.mail.javamail.JavaMailSender`
 - `awsEmailService`: `com.amazonaws.services.simpleemail.AmazonSimpleEmailService`

### Grails

As with Spring Boot the `mailSender` bean will be available if enables.

Using with the [grails-mail](https://github.com/grails/grails-mail) plugin. 

The `grails-mail` plugin uses the `mailSender` to send mail, when enabled it will deliver via AWS SES.

## Configuration

 - `mail.ses.enabled`: set to true to enable AWS SES mail sender
 - `mail.ses.configSet`: (optional) the name of an [AWS SES configuration set](https://docs.aws.amazon.com/ses/latest/dg/using-configuration-sets.html)
 - `mail.ses.region`: (optional) the AWS region one of [Regions](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/regions/Regions.html) enum

example:
```
mail:
  ses:
    enabled: true
    configSet: my-config-set
    region: AP-SOUTHEAST-2
```

## Changelog

- **Version 1.0.0**
  - Initial release 