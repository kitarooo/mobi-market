package com.example.mobimarket.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.FilterDef;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    int likes;
}
