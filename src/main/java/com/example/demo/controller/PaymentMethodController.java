package com.example.demo.controller;


import com.example.demo.model.PaymentMethod;
import com.example.demo.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment-method")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Value("${image.upload.dir.payment}")  // To specify where images should be stored
    private String imageUploadDir;


    @GetMapping("/")
    public List<PaymentMethod> getAllPaymentMethod() {
        return paymentMethodService.findAllPaymentMethod();
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentMethod> createProductWithImage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile imageFile) {

        try {
            String imageUrl = saveImage(imageFile);

           PaymentMethod paymentMethod1 = paymentMethodService.createPaymentMethod(name,description,imageUrl);

            return new ResponseEntity<>(paymentMethod1, HttpStatus.CREATED);
        } catch (IOException e) {
            System.out.println("Exception: "+ e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        // Generate a unique name for the image file (e.g., UUID) to avoid name conflicts
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(imageUploadDir, fileName);

        // Create directories if they do not exist
        File directory = new File(imageUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the file to the specified directory
        Files.copy(imageFile.getInputStream(), imagePath);

        // Return the URL (this could be a relative URL or a full URL depending on your setup)
        return "/images/payment-method/" + fileName;  // Adjust as per your needs (e.g., include domain if necessary)
    }

}
