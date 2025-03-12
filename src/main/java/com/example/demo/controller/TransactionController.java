package com.example.demo.controller;

import com.example.demo.model.ApiSuccessResponse;
import com.example.demo.model.Transaction;
import com.example.demo.service.JWTService;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:3001")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/")
    public List<Transaction> getTransactionByUserId(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: "+token);

        return transactionService.getTransactionListByUserId(token);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiSuccessResponse> createTransaction(@RequestHeader("Authorization") String authorizationHeader,
                                                                @RequestParam("productId") Long productId ,
                                                                @RequestParam("cartId") Long cartId,
                                                                @RequestParam("shippingAddress") String shippingAddress,
                                                                @RequestParam("billingAddress") String billingAddress,
                                                                @RequestParam("totalAmount") Long totalAmount,
                                                                @RequestParam("paymentMethod") String paymentMethod)
    {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: "+token);
        // Step 3: Save the product to the database
        ApiSuccessResponse savedItem = transactionService.createTransaction(token, cartId, productId, shippingAddress, billingAddress, totalAmount, paymentMethod
        );

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);


    }


    @PutMapping("/update")
    public ResponseEntity<ApiSuccessResponse> updateTransaction(@RequestHeader("Authorization") String authorizationHeader,
                                                                @RequestParam("transactionId") Long transactionId)
    {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        System.out.println("Token: "+token);
        // Step 3: Save the product to the database
        ApiSuccessResponse savedItem = transactionService.updateTransactionStatus(transactionId);

        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);


    }

}
