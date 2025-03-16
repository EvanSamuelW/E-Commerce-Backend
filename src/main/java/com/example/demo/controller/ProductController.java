package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.ProductCategory;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Value("${image.upload.dir}")  // To specify where images should be stored
    private String imageUploadDir;

    @PostMapping("/create")
    public ResponseEntity<Product> createProductWithImage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") float price,
            @RequestParam("availableSizes") String[] availableSizes,
            @RequestParam("availableColors") String[] availableColors,
            @RequestParam("image") MultipartFile imageFile) {

        try {
            // Step 1: Upload the image and get the URL
            String imageUrl = saveImage(imageFile);

            // Step 2: Create a new Product object with the data
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setProductSizes(availableSizes);
            product.setProductColors(availableColors);
            product.setImage(imageUrl);  // Store the image URL in the database

            // Step 3: Save the product to the database
            Product savedProduct = productService.createProduct(
                    product.getName(),
                    product.getDescription(),
                    product.getImage(),
                    product.getPrice(),
                    product.getProductSizes(),
                    product.getProductColors()
            );

            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
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
        return "/images/" + fileName;  // Adjust as per your needs (e.g., include domain if necessary)
    }

    @GetMapping
    public Page<Product> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {


        return productService.getAllProducts(page,size);
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/categories")
    public List<ProductCategory> getProductCategory() {
        return productService.getAllProductCategory();
    }

    @PostMapping("/categories/create")
    public ProductCategory createProductCategory( @RequestParam("name") String name,
                                                  @RequestParam("description") String description) {
        return productService.createProductCategory(name, description);
    }

    @GetMapping("/recommended-item")
    public List<Product> getRecommendedProduct() {
        return productService.getTop4Product();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
