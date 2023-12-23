package com.example.mobimarket.dto.response;

import com.example.mobimarket.entity.Image;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    Double price;
    List<Image> images;
    int likes;
}
