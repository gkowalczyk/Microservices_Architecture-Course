package com.example.clienthttp;

import io.netty.handler.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.time.Duration;

@RestController
@RequestMapping("/webtemplate")
public class WebTempController {

    WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                    .protocol(HttpProtocol.HTTP11, HttpProtocol.H2)
                   .wiretap("reactor.netty.http.client.HttpClient",
                           LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                    .responseTimeout(Duration.ofSeconds(10))))
                    .filter(new ProtocolLoggingFilter())
            .build();

    @GetMapping("/users")
    public Mono<ResponseEntity<String>> get() {

        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/users")
                .retrieve()

                .bodyToMono(String.class)
                .map(response -> {
                    long start = System.currentTimeMillis();
                    long total = System.currentTimeMillis() - start;
                    return ResponseEntity.ok("time=" + total + " " + response);
                });
    }
}
