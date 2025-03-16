package com.example.demo.controller;


import com.example.demo.model.ApiSuccessResponse;
import com.example.demo.model.WishlistItem;
import com.example.demo.service.JWTService;
import com.example.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/")
    public List<WishlistItem> getWishlistListByUser(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: "+token);

        return wishlistService.getWishlistByUserId(token);
    }


    @PostMapping("/add-item")
    public ResponseEntity<ApiSuccessResponse> createWishlist(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("productId") Long productId) {

        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: "+token);
        // Step 3: Save the product to the database
        ApiSuccessResponse savedItem = wishlistService.addItemToWishlist(token,productId);

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);


    }




    @DeleteMapping("/delete")
    public ResponseEntity<ApiSuccessResponse> deleteWishlist(@RequestHeader("Authorization") String authorizationHeader,
                                                         @RequestParam("wishlistItemId") Long wishlistItemId) {

        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: "+token);
        // Step 3: Save the product to the database
        ApiSuccessResponse savedItem = wishlistService.deleteWishlistItem(wishlistItemId);

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);


    }

}
