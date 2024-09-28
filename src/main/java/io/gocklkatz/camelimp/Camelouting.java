package io.gocklkatz.camelimp;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Camelouting {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("ftp://localhost?username=ftpuser&password=sososecret")
                        .log("xxx");
            }
        });

        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}
