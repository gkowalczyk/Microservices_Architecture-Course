package com.example.ordems;

public record OrderResponse(
        Long id,
        Long customerId,
        String customerEmail,
        String productName,
        Integer quantity,
        OrderStatus status) {

    static OrderResponse from(OrderEntity order) {
                return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getCustomerEmail(),
                order.getProductName(),
                order.getQuantity(),
                order.getStatus()
        );
    }
}