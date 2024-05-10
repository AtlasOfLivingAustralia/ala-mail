package au.org.ala.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailResult;
import org.springframework.lang.Nullable;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class WrappedJavaMailSenderImpl extends JavaMailSenderImpl {

    private static final String HEADER_MESSAGE_ID = "Message-ID";

    private AmazonSimpleEmailService emailService;

    @Nullable
    private String configSet;

    public AmazonSimpleEmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(AmazonSimpleEmailService emailService) {
        this.emailService = emailService;
    }

    void setConfigSet(@Nullable String configSet) {
        this.configSet = configSet;
    }

    @Nullable
    String getConfigSet() {
        return configSet;
    }

    @Override
    protected void doSend(MimeMessage[] mimeMessages, @Nullable Object[] originalMessages) throws MailException {


        Map<Object, Exception> failedMessages = new LinkedHashMap<>();

        for (int i = 0; i < mimeMessages.length; i++) {

            // Send message via current transport...
            MimeMessage mimeMessage = mimeMessages[i];
            try {
                if (mimeMessage.getSentDate() == null) {
                    mimeMessage.setSentDate(new Date());
                }
                String messageId = mimeMessage.getMessageID();
                mimeMessage.saveChanges();
                if (messageId != null) {
                    // Preserve explicitly specified message id...
                    mimeMessage.setHeader(HEADER_MESSAGE_ID, messageId);
                }

                // Send the email.
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                mimeMessage.writeTo(outputStream);

                RawMessage rawMessage =
                        new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

                SendRawEmailRequest rawEmailRequest =
                        new SendRawEmailRequest(rawMessage)
                                .withConfigurationSetName(configSet);

                SendRawEmailResult result = emailService.sendRawEmail(rawEmailRequest);

            } catch (Exception ex) {
                Object original = (originalMessages != null ? originalMessages[i] : mimeMessage);
                failedMessages.put(original, ex);
            }
        }

        if (!failedMessages.isEmpty()) {
            throw new MailSendException(failedMessages);
        }
    }
}
