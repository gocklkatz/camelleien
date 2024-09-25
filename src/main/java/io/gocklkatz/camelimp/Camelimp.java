package io.gocklkatz.camelimp;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Camelimp {

    public static void main(String[] args) {
        try (CamelContext camel = new DefaultCamelContext()) {
            camel.addRoutes(createBasicRoute());
            camel.start();

            Thread.sleep(10_000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static RouteBuilder createBasicRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("timer://foo?fixedRate=true&period=1000")
                        .log("Loggit!");
            }
        };
    }
}
