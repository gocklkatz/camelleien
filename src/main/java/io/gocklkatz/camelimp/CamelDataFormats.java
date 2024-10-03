package io.gocklkatz.camelimp;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelDataFormats {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {

                from("file://C:\\Users\\Stefan Katzensteiner\\Downloads\\ain?noop=true")
                        .marshal().base64()
                        .process(exchange -> {
                            String custom = exchange.getIn().getBody(String.class);
                            System.out.println(custom);
                        });

                /*
                from("file://C:\\Users\\Stefan Katzensteiner\\Downloads\\ain?noop=true")
                        .marshal().base64()
                        .process(exchange -> {
                            String custom = exchange.getIn().getBody(String.class);
                            System.out.println(custom);
                        })
                        .unmarshal().base64()
                        .process(exchange -> {
                            String custom = exchange.getIn().getBody(String.class);
                            System.out.println(custom);
                        });
                 */

            }
        });

        context.start();
        Thread.sleep(10_000);
        context.stop();
    }
}
