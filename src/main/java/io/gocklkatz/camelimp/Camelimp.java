package io.gocklkatz.camelimp;

import org.apache.camel.CamelContext;
import org.apache.camel.attachment.Attachment;
import org.apache.camel.attachment.AttachmentMessage;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.eclipse.angus.mail.util.BASE64DecoderStream;

import java.util.Map;

public class Camelimp {

    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure () {
                /*
                from("timer://foo?fixedRate=true&period=1000")
                        .log("Loggit!");
                 */
                /*
                from("file:///Users/katzi/Downloads/ain?noop=true")
                        .process(exchange -> {
                            Message m = exchange.getIn();
                            System.out.printf("m = %s%n", m);

                            Object o = exchange.getIn().getBody();
                            System.out.println("o = " + o);
                        })
                        .to("file:///Users/katzi/Downloads/aout");
                 */
                /*
                from("file:///Users/katzi/Downloads/ain")
                        .filter().xpath("/order[not(@test)]")
                        .to("file:///Users/katzi/Downloads/aout");
                 */
                from("file:///Users/katzi/Downloads/ain?noop=true")
                        .setHeader("Content-Type", constant("multipart/*"))
                        .unmarshal().mimeMultipart()
                        .process(exchange -> {
                            AttachmentMessage attachmentMessage = exchange.getIn(AttachmentMessage.class);
                            Map<String, Attachment> attachments = attachmentMessage.getAttachmentObjects();
                            if (attachments.isEmpty()) {
                                System.out.println("No attachments found.");
                            } else {
                                for (Map.Entry<String, org.apache.camel.attachment.Attachment> entry : attachments.entrySet()) {
                                    //String attachmentName = entry.getKey();
                                    org.apache.camel.attachment.Attachment attachment = entry.getValue();
                                    //System.out.println("Attachment name: " + attachmentName);
                                    BASE64DecoderStream stream = (BASE64DecoderStream) attachment.getDataHandler().getContent();
                                    byte[] bytes = stream.readAllBytes();
                                    System.out.println(new String(bytes));
                                }
                            }
                        });
            }
        });

        camelContext.start();
        Thread.sleep(30_000);

        camelContext.stop();
    }
}
