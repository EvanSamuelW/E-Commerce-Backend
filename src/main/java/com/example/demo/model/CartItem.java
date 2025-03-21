package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)  // Keep it LAZY to avoid loading the entire Cart
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonIgnore  // Prevent serialization of the Cart object
    private Cart cart;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private LocalDateTime addedAt = LocalDateTime.now();

    @Column
    private int quantity;

    @Column(name = "chosen_color")
    private String chosenColor;

    @Column(name = "chosen_size")
    private String chosenSize;

    @Transient
    private Long cartId;

    @Transient
    private boolean isReviewed;

    // Getter to initialize cartId only when needed
    public Long getCartId() {
        if (this.cart != null) {
            return this.cart.getCartId();
        }
        return null;
    }

    public void setIsReviewed(boolean isReviewed) {
        this.isReviewed = isReviewed; // You can manually set the value of the transient field
    }

}

