package com.example.demo.service;


import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistItemRepo wishlistItemRepo;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    public List<WishlistItem> getWishlistByUserId(String token) {
        Long userId = jwtService.extractUserId(token);
        System.out.println("Checking wishlist table");
        List<WishlistItem> wishlistItems= wishlistItemRepo.findByUserId(userId);

        if(wishlistItems.isEmpty()){
            System.out.println("UserId not found");
            return new ArrayList<>();
        }else{
            return wishlistItems;
        }
    }


    @Transactional
    public ApiSuccessResponse addItemToWishlist(String token, Long productId) {
        // Fetch the user by their ID
        Long userId = jwtService.extractUserId(token);

        // Fetch the User entity from the database
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        WishlistItem wishlistItem = new WishlistItem();

        wishlistItem.setUser(user);
        wishlistItem.setProduct(product);
        wishlistItem.setAddedAt(LocalDateTime.now());
        wishlistItemRepo.save(wishlistItem);

        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse();
        apiSuccessResponse.setStatus("SUCCESS");
        apiSuccessResponse.setMessage("Item Successfully added to cart");

        return apiSuccessResponse;

    }



    @Transactional
    public ApiSuccessResponse deleteWishlistItem( Long wishlistItemId){

        WishlistItem wishlistItem = wishlistItemRepo.findById(wishlistItemId).orElseThrow(() -> new RuntimeException("Wishlist Item not found with ID: " + wishlistItemId));


        wishlistItemRepo.delete(wishlistItem);

        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse();
        apiSuccessResponse.setStatus("SUCCESS");
        apiSuccessResponse.setMessage("Item Removed From Cart");

        return apiSuccessResponse;
    }
}
