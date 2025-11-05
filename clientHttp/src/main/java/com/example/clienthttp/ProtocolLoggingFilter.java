package com.example.clienthttp;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

public class ProtocolLoggingFilter implements ExchangeFilterFunction {
    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        System.out.println("Request URI" + request.url());
        System.out.println("Request method" + request.method());

        return next.exchange(request)
                .doOnNext(response -> {
                    System.out.println("Response Status Code" + response.statusCode());
                    System.out.println("Response Headers" + response.headers().asHttpHeaders());
                });
    }
}
