
package com.example.demo.controller;


import com.example.demo.model.AddItemRequest;
import com.example.demo.model.ApiSuccessResponse;
import com.example.demo.model.CartItem;
import com.example.demo.model.UpdateCartRequest;
import com.example.demo.service.CartService;
import com.example.demo.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {


    @Autowired
    private CartService cartService;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/")
    public List<CartItem> getCartListByUserId(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: "+token);

        return cartService.getCartListByUserId(token);
    }


    @PostMapping("/add-item")
    public ResponseEntity<ApiSuccessResponse> createCart(@RequestHeader("Authorization") String authorizationHeader,
                                                         @RequestBody AddItemRequest addItemRequest) {
        // Extract the token
        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: " + token);

        // Step 3: Save the product to the database
        ApiSuccessResponse savedItem = cartService.addItemToCart(token, addItemRequest.getProductId(),
                addItemRequest.getChoosenColor(), addItemRequest.getChoosenSize());

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }


    @PutMapping("/update")
    public ResponseEntity<ApiSuccessResponse> updateCart(@RequestHeader("Authorization") String authorizationHeader,
                                                         @RequestBody UpdateCartRequest updateCartRequest) {

        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: "+token);
        // Step 3: Save the product to the database
        ApiSuccessResponse savedItem = cartService.updateCart(updateCartRequest.getCartItemId(),updateCartRequest.getQuantity()
        );

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);


    }


    @DeleteMapping("/delete")
    public ResponseEntity<ApiSuccessResponse> updateCart(@RequestHeader("Authorization") String authorizationHeader,
                                                         @RequestParam("cartItemId") Long cartItemId) {

        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: "+token);
        // Step 3: Save the product to the database
        ApiSuccessResponse savedItem = cartService.deleteCartItem(cartItemId);

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);


    }
}
