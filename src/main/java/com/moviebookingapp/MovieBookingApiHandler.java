package com.moviebookingapp;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MovieBookingApiHandler implements RequestStreamHandler{

    public static final Logger logger = LoggerFactory.getLogger(MovieBookingApiHandler.class);

    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(MoviebookingappApplication.class);
            /* If you are using HTTP APIs with the version 2.0 of the proxy model, use the getHttpApiV2ProxyHandler
             method: handler = SpringBootLambdaContainerHandler.getHttpApiV2ProxyHandler(Application.class); */
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            logger.error("Could not initialize Spring Boot application ", e);
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        logger.info("handleRequest method called ");
        logger.info("handleRequest method called input stream size :: {}", inputStream.available() );
        handler.proxyStream(inputStream, outputStream, context);
    }
}
