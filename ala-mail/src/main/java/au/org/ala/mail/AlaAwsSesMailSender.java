package au.org.ala.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.springframework.lang.Nullable;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.io.InputStream;

import javax.mail.internet.MimeMessage;

public class AlaAwsSesMailSender implements JavaMailSender {

    static final WrappedJavaMailSenderImpl delegate = new WrappedJavaMailSenderImpl();

    public AmazonSimpleEmailService getEmailService() {
        return delegate.getEmailService();
    }

    public void setEmailService(AmazonSimpleEmailService emailService) {
        delegate.setEmailService(emailService);
    }

    void setConfigSet(@Nullable String configSet) {
        delegate.setConfigSet(configSet);
    }

    @Nullable
    String getConfigSet() {
        return delegate.getConfigSet();
    }

    @Override
    public MimeMessage createMimeMessage() {
        return delegate.createMimeMessage();
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        return delegate.createMimeMessage(contentStream);
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        delegate.send(mimeMessage);
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        delegate.send(mimeMessages);
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        delegate.send(mimeMessagePreparator);
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        delegate.send(mimeMessagePreparators);
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        delegate.send(simpleMessage);
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        delegate.send(simpleMessages);
    }
}
