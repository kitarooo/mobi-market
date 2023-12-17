package com.example.mobimarket.repository;

import com.example.mobimarket.entity.Product;
import com.example.mobimarket.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    /*@Query("select * from products inner join users_liked_products USING(productId) where user_id= userId")
            Product findByIdAndFindUserLikedById(Long userId, Long productId);*/

    @Query(value = "SELECT COUNT(*) FROM users_liked_products WHERE liked_users_id= :userId AND liked_products_id= :productId", nativeQuery = true)
    Long findProductByIdAndFindUserById(@Param("userId") Long userId,@Param("productId") Long productId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM users_liked_products WHERE liked_users_id= :userId AND liked_products_id= :productId", nativeQuery = true)
    void dislikeProductByIdAndUserId(@Param("userId") Long userId,@Param("productId") Long productId);
}
