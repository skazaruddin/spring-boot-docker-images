package io.azar.examples.order_microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ProductServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${productms.host}")
    private String productServiceHost;

    public Product getProduct(Long productId) {
        String url = productServiceHost+ "/products/" + productId; // Adjust URL for your product service
        return restTemplate.getForObject(url, Product.class);
    }
}
