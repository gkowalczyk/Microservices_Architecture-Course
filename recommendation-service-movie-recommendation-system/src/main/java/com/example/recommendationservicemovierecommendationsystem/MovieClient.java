package com.example.recommendationservicemovierecommendationsystem;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component

public class MovieClient {

    private final RestClient restClient;

    public MovieClient(@Qualifier
                               ("loadBalancedRestClientBuilder")
                               RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://movie-service-movie-recommendation-system")
                .build();
    }

    public List<MovieResponse> findCandidates(
            String genre, Double minimumRating) {

        return restClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/api/movies")
                                .queryParam("genre", genre)
                                .queryParam("minimumRating", minimumRating)
                                .build()
                )
                .retrieve()
                .body(new ParameterizedTypeReference<List<MovieResponse>>() {
                });
    }
}
