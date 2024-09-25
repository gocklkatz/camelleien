package io.gocklkatz;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMultipart;
import org.apache.camel.Body;
import org.apache.camel.Handler;

public class AttachmentProcessor {

    @Handler
    public void processAttachment(@Body MimeMultipart mimeMultipart) throws MessagingException {
        System.out.println("Processing attachment");
        System.out.println("Attachment count: " + mimeMultipart.getCount());
    }
}
