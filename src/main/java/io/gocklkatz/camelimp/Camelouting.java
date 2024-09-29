package io.gocklkatz.camelimp;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.impl.DefaultCamelContext;

public class Camelouting {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        /*
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("vm://localhost");
        context.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("ftp://localhost?username=ftpuser&password=sososecret")
                        .process(exchange -> {
                            System.out.println(
                                    "We just downloaded: " + exchange.getIn().getHeader("CamelFileName")
                            );
                        })
                        .to("jms:queue:incomingOrders");
            }
        });
         */

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://C:\\Users\\Stefan Katzensteiner\\Downloads\\ain?noop=true")
                        .convertBodyTo(String.class)
                        .log("Received message: ${body}");
            }
        });

        context.start();
        Thread.sleep(10_000);
        context.stop();
    }
}
