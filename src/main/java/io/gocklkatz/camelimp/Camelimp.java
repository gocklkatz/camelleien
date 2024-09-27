package io.gocklkatz.camelimp;

import org.apache.camel.CamelContext;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

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
                from("file:///Users/katzi/Downloads/ain")
                        .filter().xpath("/order[not(@test)]")
                        .to("file:///Users/katzi/Downloads/aout");
            }
        });

        camelContext.start();
        Thread.sleep(30_000);

        camelContext.stop();
    }
}
