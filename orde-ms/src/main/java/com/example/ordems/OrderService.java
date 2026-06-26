package com.example.ordems;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final CustomerClient customerClient;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        CustomerResponse customerResponse = customerClient.getCustomer(createOrderRequest.customerId());
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerId(customerResponse.id());
        orderEntity.setCustomerEmail(customerResponse.email());
        orderEntity.setProductName(createOrderRequest.productName());
        orderEntity.setQuantity(createOrderRequest.quantity());
        orderEntity.setStatus(OrderStatus.CREATED);
        return OrderResponse.from(orderRepo.save(orderEntity));
    }
    public List<OrderResponse> getOrderByCustomerID(Long customerId) {
        return orderRepo.findByCustomerId(customerId)
                .stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }
}
