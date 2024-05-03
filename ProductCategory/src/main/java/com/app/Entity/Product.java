package com.app.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    
    private String productName;

    
    private String productDescription;

    @Column(nullable = false)
    private BigDecimal productPrice;

   
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
    
    @JsonIgnoreProperties(value = "products")
    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;
}
