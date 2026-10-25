package com.voc.stockapi.service;

import com.voc.stockapi.dto.ProductRequest;
import com.voc.stockapi.domain.Product;
import com.voc.stockapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;

    public Product createProduct(ProductRequest req) {
        if (repository.existsBySku(req.getSku())) {
            throw new IllegalArgumentException("SKU already exists: " + req.getSku());
        }
        Product product = Product.builder()
                .name(req.getName())
                .sku(req.getSku())
                .price(req.getPrice())
                .stock(req.getStock())
                .category(req.getCategory())
                .build();
        return repository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    public Product updateStock(Long id, Long quantityChange) {
        Product product = getProductById(id);
        product.setStock(product.getStock().add(java.math.BigDecimal.valueOf(quantityChange)));
        if (product.getStock().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }
        return repository.save(product);
    }
}