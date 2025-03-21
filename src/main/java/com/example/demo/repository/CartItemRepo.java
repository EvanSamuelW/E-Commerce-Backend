package com.example.demo.repository;


import com.example.demo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCart_CartIdAndProductIdAndChosenColorAndChosenSize(Long cartId, Long productId, String chosenColor, String chosenSize);

    List<CartItem> findByCart_CartId(Long cartId);
}
