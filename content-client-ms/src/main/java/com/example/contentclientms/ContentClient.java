package com.example.contentclientms;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.function.Function;

@RestController
public class ContentClient {

    private final RestClient restClient;

    public ContentClient() {
        restClient = RestClient.builder().build();
    }

    @GetMapping("/call-client")
    @CircuitBreaker(name = "contentClientCall", fallbackMethod = "fallbackMethodContent")
    public List<Content> get(@RequestParam String tag) {
        return restClient
                .get()
                .uri("http://localhost:10881/api/content/{tag}", tag)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public List<Content> fallbackMethodContent(String tag, Throwable throwable) {
        System.out.println("FALLBACK działa: " + throwable.getClass().getName());
        Content content = new Content();
        content.setId(1L);
        content.setTag("java");
        content.setTitle("title");
        return List.of(content);
    }
}

