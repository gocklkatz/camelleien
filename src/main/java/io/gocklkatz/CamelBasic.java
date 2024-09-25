package io.gocklkatz;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

//https://github.com/apache/camel-examples
public final class CamelBasic {

    public static void main(String[] args) throws Exception {
        try (CamelContext camel = new DefaultCamelContext()) {
            camel.addRoutes(createBasicRoute());
            camel.start();

            Thread.sleep(10_000);
        }
    }

    static RouteBuilder createBasicRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("imap://127.0.0.1:3143?username=bbb&password=soooosecret&delete=false&unseen=true")
                        .log("Email subject: ${header.subject}")
                        .log("Email body: ${body}")
                        .bean(AttachmentProcessor.class);
            }
        };
    }
}
