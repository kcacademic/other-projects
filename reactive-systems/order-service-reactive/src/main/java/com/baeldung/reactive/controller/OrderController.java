package com.baeldung.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.domain.Order;
import com.baeldung.reactive.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost")
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Mono<Order> create(@RequestBody Order order) {
        return orderService.createOrder(order)
            .map(o -> {
                if ("FAILURE".equals(o.getOrderStatus()))
                    throw new RuntimeException("Order processing failed, please try again later. " + o.getResponseMessage());
                else
                    return o;
            });
    }

    @GetMapping
    public Flux<Order> getAll() {
        return orderService.getOrders();
    }

}