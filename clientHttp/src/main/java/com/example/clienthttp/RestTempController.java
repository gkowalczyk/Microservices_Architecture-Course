package com.example.clienthttp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/resttemplate")
public class RestTempController {

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/users")
    public ResponseEntity<String> get() {
        restTemplate.setInterceptors(List.of(new ProtocolLoggingInterceptor()));
        long start = System.currentTimeMillis();
        String result = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users",
                String.class);
        long total = System.currentTimeMillis() - start;
        return ResponseEntity.ok("time=" + total + " " + result);
    }
}
