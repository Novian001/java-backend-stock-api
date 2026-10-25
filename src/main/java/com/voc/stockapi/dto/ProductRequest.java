package com.voc.stockapi.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductRequest {

    @NotBlank(message = "Nama produk wajib diisi")
    @Size(max = 120)
    private String name;

    @NotBlank(message = "SKU wajib diisi")
    @Size(max = 40)
    private String sku;

    @NotNull(message = "Harga wajib diisi")
    @DecimalMin(value = "0.0", inclusive = true, message = "Harga harus >= 0")
    private BigDecimal price;

    @NotNull(message = "Stok wajib diisi")
    @DecimalMin(value = "0.0", inclusive = true, message = "Stok harus >= 0")
    private BigDecimal stock;

    @NotBlank(message = "Kategori wajib diisi")
    @Size(max = 60)
    private String category;
}