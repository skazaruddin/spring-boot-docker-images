package io.azar.examples.product_microservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductRepositoryRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
    }

    @Test
    void testCreateProduct() throws Exception {
        String productJson = "{\"name\": \"Product A\", \"price\": 100.0}";

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Product A"))
                .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    void testGetAllProducts() throws Exception {
        Product product = new Product();
        product.setName("Product A");
        product.setPrice(100.0);
        productRepository.save(product);

        mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Product A"))
                .andExpect(jsonPath("$[0].price").value(100.0));
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product();
        product.setName("Product A");
        product.setPrice(100.0);
        product = productRepository.save(product);

        mockMvc.perform(get("/products/" + product.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product A"))
                .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product product = new Product();
        product.setName("Product A");
        product.setPrice(100.0);
        product = productRepository.save(product);

        String updatedProductJson = "{\"name\": \"Updated Product A\", \"price\": 150.0}";

        mockMvc.perform(put("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product A"))
                .andExpect(jsonPath("$.price").value(150.0));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Product product = new Product();
        product.setName("Product A");
        product.setPrice(100.0);
        product = productRepository.save(product);

        mockMvc.perform(delete("/products/" + product.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/products/" + product.getId()))
                .andExpect(status().isNotFound());
    }
}

