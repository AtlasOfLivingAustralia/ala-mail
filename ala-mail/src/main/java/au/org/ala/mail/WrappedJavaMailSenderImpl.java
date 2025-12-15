package au.org.ala.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.SendRawEmailResponse;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class WrappedJavaMailSenderImpl extends JavaMailSenderImpl {
    private static final Logger logger = LoggerFactory.getLogger(WrappedJavaMailSenderImpl.class);

    private static final String HEADER_MESSAGE_ID = "Message-ID";

    private SesClient sesClient;

    @Nullable
    private String configSet;

    public SesClient getSesClient() {
        return sesClient;
    }

    public void setEmailService(SesClient sesClient) {
        this.sesClient = sesClient;
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

                // Prepare RawMessage
                RawMessage rawMessage = RawMessage.builder()
                        .data(SdkBytes.fromByteArray(outputStream.toByteArray()))
                        .build();

                SendRawEmailRequest request = SendRawEmailRequest.builder()
                        .rawMessage(rawMessage)
                        .configurationSetName(configSet)
                        .build();

                SendRawEmailResponse response = sesClient.sendRawEmail(request);

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
