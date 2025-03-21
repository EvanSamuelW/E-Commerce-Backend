package com.example.demo.controller;


import com.example.demo.model.AddRatingRequest;
import com.example.demo.model.ApiSuccessResponse;
import com.example.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")

public class RatingController {

    @Autowired
    private RatingService ratingService;
    @PostMapping("/create")
    public ResponseEntity<ApiSuccessResponse> createRating(@RequestHeader("Authorization") String authorizationHeader,
                                                                @RequestBody AddRatingRequest addRatingRequest)
    {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: "+token);
        // Step 3: Save the product to the database
        ApiSuccessResponse savedItem = ratingService.createRating(token, addRatingRequest.getTransactionId(), addRatingRequest.getCartItemId(), addRatingRequest.getRating(), addRatingRequest.getReview());;

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);


    }
}
