package com.example.mobimarket.entity;

import com.example.mobimarket.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity {
    String name;

    String shortDescription;

    String fullDescription;

    double price;

    String photo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    User user;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "likedProducts", cascade = CascadeType.MERGE)
    List<User> likedUsers;
}
