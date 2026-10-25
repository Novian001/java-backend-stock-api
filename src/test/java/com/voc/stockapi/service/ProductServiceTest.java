package com.voc.stockapi.service;

import com.voc.stockapi.dto.ProductRequest;
import com.voc.stockapi.domain.Product;
import com.voc.stockapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@Transactional
class ProductServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository repository;

    @Test
    void createProduct_success() {
        ProductRequest req = ProductRequest.builder()
                .name("Bearing 6205")
                .sku("BRG-6205")
                .price(new BigDecimal("120000"))
                .stock(new BigDecimal("100"))
                .category(" bearings")
                .build();

        Product saved = productService.createProduct(req);

        assertNotNull(saved.getId());
        assertEquals("Bearing 6205", saved.getName());
        assertEquals("BRG-6205", saved.getSku());
        assertEquals(0, saved.getCreatedAt().compareTo(saved.getUpdatedAt()));
    }

    @Test
    void duplicateSku_throws() {
        ProductRequest req = ProductRequest.builder()
                .name("Bearing 6205")
                .sku("BRG-DUP")
                .price(new BigDecimal("100000"))
                .stock(new BigDecimal("50"))
                .category("bearings")
                .build();

        productService.createProduct(req);
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(req));
    }

    @Test
    void updateStock_positiveAndNegative() {
        Product p = repository.save(Product.builder()
                .name("Item A")
                .sku("ITEM-A")
                .price(new BigDecimal("10000"))
                .stock(new BigDecimal("10"))
                .category("demo")
                .build());

        Product updated = productService.updateStock(p.getId(), -3L);
        assertEquals(7, updated.getStock().compareTo(BigDecimal.ZERO));
        assertEquals(new BigDecimal("7"), updated.getStock());
    }
}