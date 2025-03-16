package com.example.demo.model;

public class AddItemRequest {
    private Long productId;
    private String choosenColor;
    private String choosenSize;

    // Getters and setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getChoosenColor() {
        return choosenColor;
    }

    public void setChoosenColor(String choosenColor) {
        this.choosenColor = choosenColor;
    }

    public String getChoosenSize() {
        return choosenSize;
    }

    public void setChoosenSize(String choosenSize) {
        this.choosenSize = choosenSize;
    }
}

