package com.example.mobimarket.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String shortDescription;
    String fullDescription;
    double price;
    //photo

    @ManyToOne
    User user;
}
