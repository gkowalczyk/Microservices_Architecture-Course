package com.example.recomendationms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RecommendationServiceTest {

    @Mock
    private RestClient.Builder builder;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        recommendationService = new RecommendationService(builder);

        //builder.build()
        when(builder.build()).thenReturn(restClient);

        //restClient.get()
        when(restClient.get()).thenReturn(requestHeadersUriSpec);

        //. uri(...)
        when(requestHeadersUriSpec.uri(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.<Object[]>any()
        )).thenReturn(requestHeadersUriSpec);

        //retrieve()
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void getRecommendationByTag() {

        List<Map<String, String>> mockResponse = List.of(
                Map.of(
                        "id", "1",
                        "title", "ArchitektIT.pl",
                        "tag", "java"
                )
        );
        when(responseSpec.body(List.class)).thenReturn(mockResponse);
        List<Map<String, String>> result = recommendationService.getRecommendationByTag("java");
        assertEquals(1, result.size());
        assertEquals("java", result.get(0).get("tag"));
    }
}
