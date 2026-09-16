package com.example.orderservicezipkin;


import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class OrderConfig {

    private final Tracer tracer;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8082")
                .requestInterceptor((request, body, execution) -> {
                    Span span = tracer.currentSpan();
                    if (span != null) {
                        request.getHeaders().add("X-B3-TraceId", span.context().traceId());
                        request.getHeaders().add("X-B3-SpanId", span.context().spanId());
                        request.getHeaders().add("X-B3-Sampled", "1");
                    }
                    return execution.execute(request, body);
                })
                .build();
    }
}
