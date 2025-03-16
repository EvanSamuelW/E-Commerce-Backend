package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.CartItemRepo;
import com.example.demo.repository.CartRepo;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;


    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    public List<CartItem> getCartListByUserId(String token) {
        Long userId = jwtService.extractUserId(token);
        System.out.println("Checking cart table");
        Optional<Cart> cart = cartRepo.findCartByUserIdAndStatus(userId,"active");

        if(cart.isEmpty()){
            System.out.println("Cart not found");
            return new ArrayList<>();
        }else{
            return cartItemRepo.findByCartIdWithProduct(cart.get().getId());
        }
    }


    @Transactional
    public ApiSuccessResponse addItemToCart(String token, Long productId, String chosenColor, String chosenSize) {
        // Fetch the user by their ID
        Long userId = jwtService.extractUserId(token);

        // Fetch the User entity from the database
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        Optional<Cart> cart =  cartRepo.findCartByUserIdAndStatus(userId, "active");
        Cart createdCart = new Cart();
        if(cart.isEmpty()) {
            Cart newCart = new Cart();
            newCart.setUser(user); // Set the user (which will set the user_id in the Cart entity)

            // Optionally, set other fields
            newCart.setStatus("active"); // For example, set the status
            newCart.setCreatedAt(LocalDateTime.now());
            newCart.setUpdatedAt(LocalDateTime.now());

            createdCart = cartRepo.save(newCart);

        }else{
            createdCart = cart.get();
        }


        Optional<CartItem> cartItem = cartItemRepo.findByCartIdAndProductIdAndChosenColorAndChosenSize(createdCart.getId(), productId, chosenColor, chosenSize);

        if (cartItem.isPresent())  throw new RuntimeException("Product Already inside cart");

        CartItem cartItem1 = new CartItem();
        cartItem1.setProduct(product);
        cartItem1.setCart(createdCart);
        cartItem1.setQuantity(1);
        cartItem1.setAddedAt(LocalDateTime.now());
        cartItem1.setChosenColor(chosenColor);
        cartItem1.setChosenSize(chosenSize);

        cartItemRepo.save(cartItem1);

        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse();
        apiSuccessResponse.setStatus("SUCCESS");
        apiSuccessResponse.setMessage("Item Successfully added to cart");

        return apiSuccessResponse;

    }


    @Transactional
    public ApiSuccessResponse updateCart( Long cartItemId, int quantity){

        CartItem cartItem = cartItemRepo.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart Item not found with ID: " + cartItemRepo));

        cartItem.setQuantity(quantity);

        cartItemRepo.save(cartItem);

        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse();
        apiSuccessResponse.setStatus("SUCCESS");
        apiSuccessResponse.setMessage("Quantity Updated");

        return apiSuccessResponse;
    }


    @Transactional
    public ApiSuccessResponse deleteCartItem( Long cartItemId){

        CartItem cartItem = cartItemRepo.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart Item not found with ID: " + cartItemRepo));


        cartItemRepo.delete(cartItem);

        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse();
        apiSuccessResponse.setStatus("SUCCESS");
        apiSuccessResponse.setMessage("Item Removed From Cart");

        return apiSuccessResponse;
    }
}
