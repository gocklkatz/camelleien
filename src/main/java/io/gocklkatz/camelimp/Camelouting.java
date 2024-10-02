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

                from("timer:mytimer?period=30000")
                    .process(exchange -> {
                        exchange.getIn().setBody("Hello World");
                    })
                    .transform(body().regexReplaceAll(" ", " - "))
                    .process(exchange -> {
                        String custom = exchange.getIn().getBody(String.class);
                        System.out.println(custom);
                    });

                /*
                from("timer:mytimer?period=2000")
                        .to("http://www.google.com")
                        .bean(new DoSomeThing())
                        .process(exchange -> {
                            String custom = exchange.getIn().getBody(String.class);
                            System.out.println(custom);
                        });
                 */

                /*
                from("timer:mytimer?period=2000")
                    .to("http://www.google.com")
                    .process(exchange -> {
                        String custom = exchange.getIn().getBody(String.class);
                        System.out.println(custom);
                    });
                 */

                /*
                from("file:///Users/katzi/Downloads/ain?noop=true")
                    .process(exchange -> {
                        String custom = exchange.getIn().getBody(String.class);
                        System.out.println(exchange.getIn().getHeader("CamelFileName"));
                        exchange.getIn().setBody(custom + " - Customized");
                    })
                    .process(exchange -> {
                        System.out.println(exchange.getIn().getHeader("CamelFileName"));
                    });
                 */

                /*
                from("file:///Users/katzi/Downloads/ain?noop=true")
                    .choice()
                        .when(x -> ((String) x.getIn().getHeader("CamelFileName")).endsWith(".xml"))
                            .log("Choice 1")
                        .when(header("CamelFileName").endsWith(".csv"))
                            .log("Choice 2")
                        .otherwise()
                            .log("Choice 3");
                 */
            }
        });

        context.start();
        Thread.sleep(10_000);
        context.stop();
    }
}
