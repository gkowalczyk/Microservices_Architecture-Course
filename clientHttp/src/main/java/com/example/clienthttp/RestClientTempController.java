package com.example.clienthttp;

import io.netty.handler.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.time.Duration;

@RestController
@RequestMapping("/restclient")

public class RestClientTempController {

    private final RestClient restClient;



    public RestClientTempController() {
        this.restClient = RestClient.builder()
                .uriBuilderFactory(new DefaultUriBuilderFactory("https://jsonplaceholder.typicode.com"))
                .build();
    }

    @GetMapping("/users")
    public String get() {

        return restClient.get()
                .uri("/users")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RuntimeException(response.getStatusCode().toString() + "" +
                             response.getHeaders().toString());
                })
                .body(String.class);

    }
}
