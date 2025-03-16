package com.example.demo.repository;


import com.example.demo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductIdAndChosenColorAndChosenSize(Long cartId, Long productId, String chosenColor, String chosenSize);


    @Query("SELECT c FROM CartItem c JOIN FETCH c.product WHERE c.cart.id = :cartId")
    List<CartItem> findByCartIdWithProduct(@Param("cartId") Long cartId);
}
