package com.example.demo.service;


import com.example.demo.model.PaymentMethod;
import com.example.demo.model.Product;
import com.example.demo.repository.PaymentMethodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepo paymentMethodRepo;

    public List<PaymentMethod> findAllPaymentMethod () {
        return paymentMethodRepo.findAll();
    }

    public PaymentMethod createPaymentMethod(String name, String description, String image) {
        // Create a new Product object
      PaymentMethod paymentMethod = new PaymentMethod();
      paymentMethod.setName(name);
      paymentMethod.setDescription(description);
      paymentMethod.setImage(image);
      paymentMethod.setCreatedAt(LocalDateTime.now());

      return paymentMethodRepo.save(paymentMethod);
    }

}
