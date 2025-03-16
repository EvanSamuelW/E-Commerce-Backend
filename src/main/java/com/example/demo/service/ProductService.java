package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.ProductCategory;
import com.example.demo.repository.ProductCategoryRepo;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    public Product createProduct(String name, String description, String image, float price, String[] availableSizes, String[] availableColors) {
        // Create a new Product object
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setImage(image);
        product.setPrice(price);
        product.setProductSizes(availableSizes);
        product.setProductColors(availableColors);

        // Save the product to the database
        return productRepository.save(product);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> getAllProducts(int page, int size) {
       Pageable pageable =  PageRequest.of(page, size);

       return productRepository.findAll(pageable);
    }


    public List<ProductCategory> getAllProductCategory() {
        return productCategoryRepo.findAll();
    }

    public ProductCategory createProductCategory(String name, String description)
    {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(name);
        productCategory.setDescription(description);
        productCategory.setCreatedAt(LocalDateTime.now());

        return productCategoryRepo.save(productCategory);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getTop4Product() {
        return productRepository.findTop4ByOrderByIdAsc();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
