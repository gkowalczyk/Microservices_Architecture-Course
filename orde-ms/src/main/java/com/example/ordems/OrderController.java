package com.example.ordems;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(createOrderRequest);
    }
    @GetMapping("/customer/{customerId}")
    public List<OrderResponse> getOrderByCustomerId(@PathVariable Long customerId) {
        return orderService.getOrderByCustomerID(customerId);
    }
}
