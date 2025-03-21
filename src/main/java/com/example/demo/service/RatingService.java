package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RatingService {


    @Autowired
    private RatingRepo ratingRepo;


    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;


//    public Page<Rating> getRatingListByProductId(String token, Long productId ,int page, int size) {
//        Pageable pageable =  PageRequest.of(page, size);
//
//        return ratingRepo.findByProductId(productId, pageable);
//    }


    public ApiSuccessResponse createRating(String token, Long transactionId, Long productId, Float rating, String review){


        Long userId = jwtService.extractUserId(token);

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Transaction transaction = transactionRepo.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));

                CartItem cartItem = cartItemRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

                Rating reviewData = new Rating();


            reviewData.setRating(rating);
            reviewData.setUser(user);
            reviewData.setReview(review);
            reviewData.setAddedAt(LocalDateTime.now());
            reviewData.setCartItem(cartItem);
            reviewData.setTransaction(transaction);
            ratingRepo.save(reviewData);


        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse();
        apiSuccessResponse.setStatus("SUCCESS");
        apiSuccessResponse.setMessage("Review Submitted");

        return apiSuccessResponse;

    }


}
