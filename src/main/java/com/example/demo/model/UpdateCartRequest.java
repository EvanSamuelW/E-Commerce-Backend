package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartRequest {

    private Long cartItemId;
    private int quantity;

}
