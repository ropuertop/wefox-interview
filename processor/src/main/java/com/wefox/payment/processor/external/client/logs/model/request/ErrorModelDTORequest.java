package com.wefox.payment.processor.external.client.logs.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Builder
@Jacksonized
@AllArgsConstructor
public final class ErrorModelDTORequest {

    @JsonProperty("payment_id")
    private final String paymentId;
    @JsonProperty("error_type")
    private final String errorText;
    @JsonProperty("error_description")
    private final String errorDescription;

}
