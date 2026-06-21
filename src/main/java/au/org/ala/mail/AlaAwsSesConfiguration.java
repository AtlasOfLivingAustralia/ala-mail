package au.org.ala.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.SesClientBuilder;

@Configuration
@EnableConfigurationProperties({ AlaAwsSesConfigurationProperties.class })
public class AlaAwsSesConfiguration {

    @Autowired
    AlaAwsSesConfigurationProperties properties;

    @Bean
    @ConditionalOnProperty("mail.ses.enabled")
    SesClient awsEmailService() {

        SesClientBuilder builder = SesClient.builder();

        if (properties.getRegion() != null) {
            builder = builder.region(properties.getRegion());
        }

        SesClient sesClient = builder.build();

        return sesClient;
    }

    @Bean
    @ConditionalOnProperty("mail.ses.enabled")
    JavaMailSender mailSender(SesClient awsEmailService) {

        AlaAwsSesMailSender mailSender = new AlaAwsSesMailSender();
        mailSender.setEmailService(awsEmailService);

        if (properties.getConfigSet() != null && !properties.getConfigSet().isBlank()) {
            mailSender.setConfigSet(properties.getConfigSet());
        }

        return mailSender;
    }
}
