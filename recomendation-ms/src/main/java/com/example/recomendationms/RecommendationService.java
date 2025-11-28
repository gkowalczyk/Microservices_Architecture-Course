package com.example.recomendationms;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

    @Autowired
    private RestClient.Builder builder;

    public List<Map<String, String>> getRecommendationByTag(String tag) {
        return builder.build()
                .get()
                .uri("http://localhost:8080/api/content/{tag}", tag)
                .retrieve()
                .body(List.class);
    }
}
