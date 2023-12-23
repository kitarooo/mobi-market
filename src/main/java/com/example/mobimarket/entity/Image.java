package com.example.mobimarket.entity;

import com.example.mobimarket.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "images")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image extends BaseEntity {
    String imageUrl;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE})
    Product product;
}
