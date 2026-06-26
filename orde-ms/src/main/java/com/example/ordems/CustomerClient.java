package com.example.ordems;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Component
public class CustomerClient {

    private static final String CUSTOMER_MS_BASE_URL = "http://customer-ms";

    private final RestClient customerLoadBalancedRestClient;
    private final RestClient directRestClient;
    private final String customerMsBaseUrlOverride;

    public CustomerClient(
            @Qualifier("customerLoadBalancedRestClientBuilder") RestClient.Builder
                    customerLoadBalancedRestClientBuilder,
            @Qualifier("restClientBuilder") RestClient.Builder restClientBuilder,
            @Value("${customer-ms.base-url:}") String customerMsBaseUrlOverride) {
        this.customerLoadBalancedRestClient = customerLoadBalancedRestClientBuilder.build();
        this.directRestClient = restClientBuilder.build();
        this.customerMsBaseUrlOverride = customerMsBaseUrlOverride;
    }

    public CustomerResponse getCustomer(Long customerId) {
        if (StringUtils.hasText(customerMsBaseUrlOverride)) {
            return directRestClient.get()
                    .uri(customerMsBaseUrlOverride + "/customers/{customerId}", customerId)
                    .retrieve()
                    .body(CustomerResponse.class);
        }

        return customerLoadBalancedRestClient.get()
                .uri(CUSTOMER_MS_BASE_URL + "/customers/{customerId}", customerId)
                .retrieve()
                .body(CustomerResponse.class);
    }
}

