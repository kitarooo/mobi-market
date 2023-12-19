package com.example.mobimarket.repository;

import com.example.mobimarket.entity.Product;
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
    @Query(value = "SELECT COUNT(*) FROM users_liked_products WHERE liked_users_id= :userId AND liked_products_id= :productId", nativeQuery = true)
    Long findProductByIdAndFindUserById(@Param("userId") Long userId,@Param("productId") Long productId);

    @Query(value = "select p from Product p where p.id = :productId and p.user.id = :userId")
    Optional<Product> findByIdAndIdOfUser(@Param("productId") Long productId, @Param("userId") Long userId);



    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM users_liked_products WHERE liked_users_id= :userId AND liked_products_id= :productId", nativeQuery = true)
    void dislikeProductByIdAndUserId(@Param("userId") Long userId,@Param("productId") Long productId);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM users_my_products WHERE user_id= :userId AND my_products_id= :productId", nativeQuery = true)
    void deleteProductByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM users_liked_products WHERE liked_products_id= :productId", nativeQuery = true)
    void deleteProductFromUsersLikeProduct(@Param("productId") Long productId);
    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteProductById(Long id);
}
