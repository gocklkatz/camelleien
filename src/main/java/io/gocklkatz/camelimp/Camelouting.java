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
                .choice()
                    .when(x -> ((String) x.getIn().getHeader("CamelFileName")).endsWith(".xml"))
                        .log("Choice 1")
                    .when(header("CamelFileName").endsWith(".csv"))
                        .log("Choice 2")
                    .otherwise()
                        .log("Choice 3");
            }
        });

        context.start();
        Thread.sleep(5_000);
        context.stop();
    }
}
