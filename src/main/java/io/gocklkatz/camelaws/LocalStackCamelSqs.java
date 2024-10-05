package io.gocklkatz.camelaws;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

public class LocalStackCamelSqs {
    public static void main(String[] args) throws Exception {

        final String ACCESS_KEY = "test";
        final String SECRET_KEY = "test";

        Region region = Region.US_EAST_1;

        SqsClient sqs = SqsClient.builder()
                .endpointOverride(URI.create("https://sqs.localhost.localstack.cloud:4566"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                .region(region)
                .build();

        CamelContext context = new DefaultCamelContext();
        context.getRegistry().bind("myClient", sqs);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {

                from("aws2-sqs://test-queue?amazonSQSClient=#myClient&deleteAfterRead=false&delay=5000&maxMessagesPerPoll=1")
                        .process(exchange -> {
                            System.out.println(exchange.getIn().getBody());
                        });

            }
        });

        context.start();
        Thread.sleep(10_000);
        context.stop();
    }
}
