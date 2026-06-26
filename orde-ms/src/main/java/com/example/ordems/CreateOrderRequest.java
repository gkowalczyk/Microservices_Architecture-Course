package com.example.ordems;

public record CreateOrderRequest(
        Long customerId,
        String productName,
        Integer quantity
) {
}
