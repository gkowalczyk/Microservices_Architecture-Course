package com.example.paymentservicezipkin;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final RestClient restClient;
    private final Tracer tracer;

    @GetMapping("/api/payment/status")
    public String payment(@RequestParam String paymentMethod) {

        Span span = tracer.nextSpan().name("order-processing").start();
        span.tag("item", paymentMethod);

        try {
        String shippingStatus = restClient.get()
                .uri("/api/shipping/status")
                .retrieve()
                .body(String.class);
                return "Payment confirmed" + shippingStatus;
    } finally {
            span.end();
        }
        }
}
/*

| Nagłówek          | Za co odpowiada                                                                                 |
        | ----------------- | ----------------------------------------------------------------------------------------------- |
        | `Host`            | Określa adres i port serwera docelowego, np. `localhost:8082`.                                  |
        | `User-Agent`      | Informuje, jaki klient wysłał żądanie, np. Java HTTP Client lub przeglądarka.                   |
        | `Content-Length`  | Podaje rozmiar body w bajtach. `0` oznacza brak body.                                           |
        | `Accept-Encoding` | Informuje, jakie formaty kompresji odpowiedzi obsługuje klient, np. `gzip`.                     |
        | `Connection`      | Przekazuje ustawienia dotyczące aktualnego połączenia HTTP.                                     |
        | `Upgrade`         | Prosi o przejście na inny protokół, np. `h2c`, czyli HTTP/2 bez HTTPS.                          |
        | `HTTP2-Settings`  | Zawiera techniczne ustawienia potrzebne do przejścia na HTTP/2.                                 |
        | `X-B3-TraceId`    | Identyfikuje całą operację przechodzącą przez wiele mikroserwisów.                              |
        | `X-B3-SpanId`     | Identyfikuje konkretny etap tej operacji.                                                       |
        | `X-B3-Sampled`    | Określa, czy dane tracingowe mają zostać zapisane i przesłane do Zipkina: `1` — tak, `0` — nie. |
*/
