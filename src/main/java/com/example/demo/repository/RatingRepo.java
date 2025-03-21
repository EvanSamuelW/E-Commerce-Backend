package com.example.demo.repository;


import com.example.demo.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepo  extends JpaRepository<Rating, Long> {

//    Page<Rating> findByProductId (Long productId, Pageable pageable);

    List<Rating> findByTransactionId(Long transactionId);

}
