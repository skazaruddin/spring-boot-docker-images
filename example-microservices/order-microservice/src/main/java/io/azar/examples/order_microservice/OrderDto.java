package io.azar.examples.order_microservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {
    private Order order;
    private Product product;
}
