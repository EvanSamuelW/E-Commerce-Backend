package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTransactionRequest {

    private Long cartId;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;
    private Float totalAmount;
}
