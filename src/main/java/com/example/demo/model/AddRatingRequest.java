package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRatingRequest {

    private Long transactionId;
    private String review;
    private Float rating;
    private Long cartItemId;
    private Float totalAmount;
}
