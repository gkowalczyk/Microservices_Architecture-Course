package com.example.recomendationms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.GET;

@SpringBootTest(classes = {RecommendationService.class, Config.class} )
class RecommendationServiceIntegrationTest {

    @Autowired
    private RecommendationService recommendationService;
    @Autowired
    private RestClient.Builder builder;

    private MockRestServiceServer mockServerRestServer;

    @BeforeEach
    void setUp() {
        mockServerRestServer = MockRestServiceServer.bindTo(builder).build();
    }

    @Test
    void getRecommendationByTag() {

        String mockResponse =
                "[{\"id\":\"1\",\"title\":\"ArchitektIT.pl\",\"tag\":\"java\"}]";

        this.mockServerRestServer
                .expect(requestToUriTemplate("http://localhost:8080/api/content/{tag}", "java"))
                .andExpect(method(GET))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));

        List<Map<String, String>> result = recommendationService.getRecommendationByTag("java");
        assertEquals(1, result.size());
        assertEquals("java", result.get(0).get("tag"));

        mockServerRestServer.verify();
    }
}


