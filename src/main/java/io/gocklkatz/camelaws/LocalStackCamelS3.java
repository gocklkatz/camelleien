package io.gocklkatz.camelaws;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws2.s3.AWS2S3Component;
import org.apache.camel.component.aws2.s3.AWS2S3Configuration;
import org.apache.camel.impl.DefaultCamelContext;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

public class LocalStackCamelS3 {

    public static void main(String[] args) throws Exception {

        final String ACCESS_KEY = "test";
        final String SECRET_KEY = "test";

        Region region = Region.US_EAST_1;

        S3Client s3Client = S3Client.builder()
                .endpointOverride(URI.create("https://s3.localhost.localstack.cloud:4566"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                .region(region)
                .build();

        //---

        CamelContext context = new DefaultCamelContext();

        AWS2S3Configuration configuration = new AWS2S3Configuration();
        configuration.setAmazonS3Client(s3Client);

        AWS2S3Component s3Component = new AWS2S3Component(context);
        s3Component.setConfiguration(configuration);
        s3Component.setLazyStartProducer(true);
        context.addComponent("aws2-s3", s3Component);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {

                from("aws2-s3://test-bucket?delay=5000&deleteAfterRead=false")
                        .process(exchange -> {
                            System.out.println("Received message: " + exchange.getIn().getBody(String.class));
                        });

            }
        });

        context.start();
        Thread.sleep(10_000);
        context.stop();
    }
}
