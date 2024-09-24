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
                from("timer:foo")
                        .log("Hello Camel");
            }
        };
    }
}
