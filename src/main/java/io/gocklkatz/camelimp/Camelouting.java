package io.gocklkatz.camelimp;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Camelouting {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {

                from("timer://foo?fixedRate=true&period=1000")
                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                System.out.println("Timer triggered.");
                            }
                        });

                /*
                from("ftp://localhost?username=ftpuser&password=sososecret")
                        .process(exchange -> {
                            System.out.println(
                                    "We just downloaded: " + exchange.getIn().getHeader("CamelFileName")
                            );
                        })
                        .to("jms:queue:incomingOrders");
                 */
            }
        });

        context.start();
        Thread.sleep(5_000);
        context.stop();
    }
}
