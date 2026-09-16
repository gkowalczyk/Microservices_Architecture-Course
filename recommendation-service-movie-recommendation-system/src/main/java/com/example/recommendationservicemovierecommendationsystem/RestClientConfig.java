package com.example.recommendationservicemovierecommendationsystem;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.boot.restclient.autoconfigure.RestClientBuilderConfigurer;

@Configuration
public class RestClientConfig {

    @LoadBalanced
    @Bean
    public RestClient.Builder loadBalancedRestClientBuilder( RestClientBuilderConfigurer configurer) {

        return configurer.configure(RestClient.builder());
    }


    @Primary
    @Bean
    public RestClient.Builder standardRestClientBuilder(RestClientBuilderConfigurer configurer) {

        return configurer.configure(RestClient.builder());
    }
}
