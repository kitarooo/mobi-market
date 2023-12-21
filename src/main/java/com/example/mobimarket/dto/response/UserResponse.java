package com.example.mobimarket.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    private String imageUrl;
    String username;
    String firstname;
    String lastname;

    @JsonFormat(pattern="dd.MM.yyyy")
    LocalDate birthday;

    String phoneNumber;
    String email;
}
