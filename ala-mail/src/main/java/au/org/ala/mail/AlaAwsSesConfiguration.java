package au.org.ala.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.mail.MailSender;

@Configuration
public class AlaAwsSesConfiguration {

    @Bean
    AmazonSimpleEmailService awsEmailService() {

        AmazonSimpleEmailServiceClientBuilder builder =
                AmazonSimpleEmailServiceClientBuilder.standard();

        // TODO: configure alternate AWS region
        // builder.withRegion(Regions.US_WEST_2);

        return builder.build();
    }

    @Bean("mailSender")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    MailSender mailSender(AmazonSimpleEmailService awsEmailService) {

        AlaAwsSesMailSender mailSender = new AlaAwsSesMailSender();
        mailSender.setEmailService(awsEmailService);

        return mailSender;
    }
}
