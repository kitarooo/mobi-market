package com.example.mobimarket.dto.request;

import com.example.mobimarket.entity.Image;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.FilterDef;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;
    String shortDescription;
    String fullDescription;
    double price;
    List<Image> images;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    int likes;
}
