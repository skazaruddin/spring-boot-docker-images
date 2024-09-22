package io.azar.examples.order_microservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
class OrderRepositoryRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        orderRepository.deleteAll();
    }

    @Test
    void testGetAllOrders() throws Exception {
        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(10);
        order = orderRepository.save(order);

        // Stubbing WireMock for product service response
        stubFor(WireMock.get(urlEqualTo("/products/1")) // Adjust the URL as necessary
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "id": 1,
                                    "name": "Product A",
                                    "price": 100.0,
                                    "description": "Description of Product A"
                                }
                                """))); // Sample product response


        mockMvc.perform(get("/orders")
                        .accept(MediaType.APPLICATION_JSON)) // Changed to just JSON
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Use JSON
                .andExpect(jsonPath("$").isArray()) // Expect an array of orders
                .andExpect(jsonPath("$[0].order.productId").value(1)) // Verify the productId in the order
                .andExpect(jsonPath("$[0].order.quantity").value(10)) // Verify the quantity in the order
                .andExpect(jsonPath("$[0].order.productId").value(1)) // Verify the productId in the order
                .andExpect(jsonPath("$[0].product.id").value(1))
                .andExpect(jsonPath("$[0].product.name").value("Product A"))
                .andExpect(jsonPath("$[0].product.price").value(100.0));

    }

    @Test
    void testCreateOrder() throws Exception {
        String orderJson = "{\"productId\": 0, \"quantity\": 0}";

        mockMvc.perform(post("/orders")
                        .content(orderJson)
                        .accept(MediaType.APPLICATION_JSON) // Changed to just JSON
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check for JSON content type
                .andExpect(jsonPath("$.productId").value(0))
                .andExpect(jsonPath("$.quantity").value(0));
    }

    @Test
    void testUpdateOrder() throws Exception {
        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(10);
        order = orderRepository.save(order);

        String updatedOrderJson = "{\"productId\": 2, \"quantity\": 20}";

        mockMvc.perform(put("/orders/" + order.getId())
                        .content(updatedOrderJson)
                        .accept(MediaType.APPLICATION_JSON) // Changed to just JSON
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Ensure JSON content type
                .andExpect(jsonPath("$.productId").value(2))
                .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    void testDeleteOrder() throws Exception {
        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(10);
        order = orderRepository.save(order);

        Order finalOrder = order;
        mockMvc.perform(delete("/orders/" + order.getId()))
                .andExpect(status().isNoContent())
                .andExpect(result -> {
                    mockMvc.perform(get("/orders/" + finalOrder.getId()))
                            .andExpect(status().isNotFound());
                });


    }
}

