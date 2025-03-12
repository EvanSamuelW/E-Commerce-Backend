package com.example.demo.service;

import com.example.demo.model.ApiSuccessResponse;
import com.example.demo.model.Product;
import com.example.demo.model.Rating;
import com.example.demo.model.User;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class RatingService {


    @Autowired
    private RatingRepo ratingRepo;


    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;


    public Page<Rating> getRatingListByProductId(String token, Long productId ,int page, int size) {
        Pageable pageable =  PageRequest.of(page, size);

        return ratingRepo.findByProductId(productId, pageable);
    }


    public ApiSuccessResponse createRating(String token, Long transactionId, Long[] productIdList, Float[] rating, String[] review){


        Long userId = jwtService.extractUserId(token);
        System.out.println("Checking cart table");

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));


        Arrays.stream(productIdList).forEach((n)->{
                Product product = productRepository.findById(n)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + n));

                Rating reviewData = new Rating();

                List<Long> productIdListAsList = Arrays.stream(productIdList).toList();
            int index = productIdListAsList.indexOf(n);

            List<Float> ratingList = Arrays.stream(rating).toList();
            reviewData.setRating(ratingList.get(index));
            reviewData.setUser(user);

            List<String> reviewList = Arrays.stream(review).toList();
            reviewData.setReview(reviewList.get(index));
            reviewData.setAddedAt(LocalDateTime.now());

            ratingRepo.save(reviewData);
        });

        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse();
        apiSuccessResponse.setStatus("SUCCESS");
        apiSuccessResponse.setMessage("Review Submitted");

        return apiSuccessResponse;

    }


}
