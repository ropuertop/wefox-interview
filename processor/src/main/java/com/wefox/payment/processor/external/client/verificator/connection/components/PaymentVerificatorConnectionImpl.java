package com.wefox.payment.processor.external.client.verificator.connection.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.payment.processor.core.model.Payment;
import com.wefox.payment.processor.external.client.verificator.connection.IPaymentVerificatorConnection;
import com.wefox.payment.processor.external.client.verificator.model.PaymentDTO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class PaymentVerificatorConnectionImpl implements IPaymentVerificatorConnection {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.of(5, ChronoUnit.SECONDS))
            .build();

    @Override
    public final Boolean checkIfValid(final PaymentDTO payment) throws URISyntaxException, IOException, InterruptedException {

        final var requestBody = this.objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(payment);

        // creating the request
        final var verificatorUrl = "http://localhost:9000/payment";

        final var request = HttpRequest
                .newBuilder(new URI(verificatorUrl))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .timeout(Duration.of(5, ChronoUnit.SECONDS))
                .build();

        // sending the request
        final var response = this.client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.statusCode() == 200;
    }
}
