package com.example.demo.repository;

import com.example.demo.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistItemRepo extends JpaRepository<WishlistItem, Long> {

    @Query("SELECT c FROM WishlistItem c JOIN FETCH c.product WHERE c.user.id= :userId")
    List<WishlistItem> findByUserId (Long userId);
}
