package io.gocklkatz.camelmail;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

//https://greenmail-mail-test.github.io/greenmail
public class Greenmail {

    public static void main(String[] args) throws Exception {
        GreenMail greenMail = new GreenMail(ServerSetupTest.ALL);
        greenMail.start();

        final String subject = "Hello World";
        final String body = "My name is";
        MimeMessage message = createMimeMessage(subject, body, greenMail);

        GreenMailUser user = greenMail.setUser("bbb@localhost", "bbb", "soooosecret");
        user.deliver(message);

        //MimeMessage msgReceived = greenMail.getReceivedMessagesForDomain("bbb@localhost")[0];
        //System.out.println("Subject: " + msgReceived.getSubject());
        //System.out.println("Body: " + GreenMailUtil.getBody(msgReceived));
    }

    private static MimeMessage createMimeMessage(String subject, String body, GreenMail greenMail) throws Exception{
        MimeMessage message = new MimeMessage(greenMail.getSmtp().createSession());
        message.setFrom(new InternetAddress("aaa@localhost"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("bbb@localhost"));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/html; charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);

        return message;
    }
}
