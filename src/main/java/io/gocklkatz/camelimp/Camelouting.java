package io.gocklkatz.camelimp;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class Camelouting {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("vm://localhost");
        context.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("ftp://localhost?username=ftpuser&password=sososecret")
                        .to("jms:queue:incomingOrders");
            }
        });

        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}
