package com.wefox.payment.processor.external.client.logs.connection;

import com.wefox.payment.processor.external.client.logs.model.request.ErrorModelDTORequest;
import com.wefox.payment.processor.external.client.logs.model.response.ErrorModelDTOResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ILogSystemConnection {

    ErrorModelDTOResponse registerNewError(final ErrorModelDTORequest request) throws IOException, URISyntaxException, InterruptedException;
}
