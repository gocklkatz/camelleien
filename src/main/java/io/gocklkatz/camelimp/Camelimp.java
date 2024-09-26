package io.gocklkatz.camelimp;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Camelimp {

    public static void main(String[] args) {
        try (CamelContext camelContext = new DefaultCamelContext()) {

            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("timer://foo?fixedRate=true&period=1000")
                            .log("Loggit!");
                }
            });
            camelContext.start();

            Thread.sleep(10_000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
