package com.example.demo.repository;

import com.example.demo.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistItemRepo extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByUserId (Long userId);
}
