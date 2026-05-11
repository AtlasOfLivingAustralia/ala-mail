package au.org.ala.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@EnableConfigurationProperties({ AlaAwsSesConfigurationProperties.class })
public class AlaAwsSesConfiguration {

    @Autowired
    AlaAwsSesConfigurationProperties properties;

    @Bean
    @ConditionalOnProperty("mail.ses.enabled")
    AmazonSimpleEmailService awsEmailService() {

        AmazonSimpleEmailServiceClientBuilder builder =
                AmazonSimpleEmailServiceClientBuilder.standard();

        // TODO: configure alternate AWS region
        if (properties.getRegion() != null) {
            builder.withRegion(properties.getRegion());
        }

        return builder.build();
    }

    @Bean
    @ConditionalOnProperty("mail.ses.enabled")
    JavaMailSender mailSender(AmazonSimpleEmailService awsEmailService) {

        AlaAwsSesMailSender mailSender = new AlaAwsSesMailSender();
        mailSender.setEmailService(awsEmailService);

        if (properties.getConfigSet() != null && !properties.getConfigSet().isBlank()) {
            mailSender.setConfigSet(properties.getConfigSet());
        }

        return mailSender;
    }
}
