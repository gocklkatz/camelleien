package io.gocklkatz.camelaws;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws2.s3.AWS2S3Component;
import org.apache.camel.component.aws2.s3.AWS2S3Configuration;
import org.apache.camel.impl.DefaultCamelContext;

public class LocalStackCamel {

    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

        AWS2S3Configuration configuration = new AWS2S3Configuration();
        //configuration.setAmazonS3Client(new LocalStackS3Client());

        AWS2S3Component s3Component = new AWS2S3Component(context);
        s3Component.setConfiguration(configuration);
        s3Component.setLazyStartProducer(true);
        context.addComponent("aws2-s3", s3Component);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {

                from("aws2-s3://mycamelbucket?delay=5000&deleteAfterRead=false")
                        .log("Received message from S3: ${body}");

            }
        });

        context.start();
        Thread.sleep(10_000);
        context.stop();
    }
}
