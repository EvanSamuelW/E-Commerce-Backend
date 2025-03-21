package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // Ignore the Hibernate lazy loader properties
@Setter
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Assuming a User entity exists in your system

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_id", nullable = false)
    private CartItem cartItem;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "review")
    private String review;

    @Column(name = "added_at")
    private LocalDateTime addedAt;




}
