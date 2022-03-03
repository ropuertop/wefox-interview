package com.wefox.payment.processor.external.client.logs.connection.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.payment.processor.external.client.logs.connection.ILogSystemConnection;
import com.wefox.payment.processor.external.client.logs.model.request.ErrorModelDTORequest;
import com.wefox.payment.processor.external.client.logs.model.response.ErrorModelDTOResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Log4j2
public class LogSystemConnectionImpl implements ILogSystemConnection {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.of(5, ChronoUnit.SECONDS))
            .build();

    @Override
    public ErrorModelDTOResponse registerNewError(final ErrorModelDTORequest requestDTO) throws IOException, URISyntaxException, InterruptedException {

        final var requestBody = this.objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(requestDTO);

        // creating the request
        final var logSystemUrl = "http://localhost:9000/logs";

        final var request = HttpRequest
                .newBuilder(new URI(logSystemUrl))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .timeout(Duration.of(5, ChronoUnit.SECONDS))
                .build();

        // sending the request
        final var response = this.client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200)
        {
            final var optionalBody = Optional.ofNullable(response.body());

            return optionalBody.isPresent()
                    ? this.objectMapper.readValue(optionalBody.get(), ErrorModelDTOResponse.class)
                    : null;
        }
        else
        {
            log.error("(LogSystemConnectionImpl) -> (registerNewError): there was an problem trying to register a new error: [{}]", response);
            // we could return the default behavior, we only need to define what we want to do on yellow paths
            return null;
        }
    }
}
