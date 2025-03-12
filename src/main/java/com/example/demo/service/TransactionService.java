package com.example.demo.service;


import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {


    @Autowired
    private TransactionRepo transactionRepo;


    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    public List<Transaction> getTransactionListByUserId(String token) {
        Long userId = jwtService.extractUserId(token);
        System.out.println("Checking transaction table");
       return transactionRepo.findByUserId(userId);
    }


    @Transactional
    public ApiSuccessResponse createTransaction(String token, Long cartId, Long productId, String shippingAddress, String billingAddress, Long totalAmount, String paymentMethod ) {
        // Fetch the user by their ID
        Long userId = jwtService.extractUserId(token);

        // Fetch the User entity from the database
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));


        Cart cart =  cartRepo.findCartByUserIdAndStatus(userId, "active") .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));;

        Transaction transaction = new Transaction();

        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionStatus("In Process");
        transaction.setProducts(product);
        transaction.setUser(user);
        transaction.setPaymentMethod(paymentMethod);
        transaction.setTotalAmount(totalAmount);
        transaction.setBillingAddress(billingAddress);
        transaction.setShippingAddress(shippingAddress);
        transaction.setCart(cart);

        transactionRepo.save(transaction);

        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse();
        apiSuccessResponse.setStatus("SUCCESS");
        apiSuccessResponse.setMessage("Transaction successfully processed");

        return apiSuccessResponse;

    }


    @Transactional
    public ApiSuccessResponse updateTransactionStatus(Long transactionId) {



        Transaction transaction = transactionRepo.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));

        transaction.setTransactionStatus("Completed");

        transactionRepo.save(transaction);

        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse();
        apiSuccessResponse.setStatus("SUCCESS");
        apiSuccessResponse.setMessage("Transaction successfully updated");

        return apiSuccessResponse;

    }




}
