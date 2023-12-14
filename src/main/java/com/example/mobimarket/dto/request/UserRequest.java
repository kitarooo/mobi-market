package com.example.mobimarket.dto.request;

import jdk.jfr.Name;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String firstname;
    String lastname;
    LocalDate birthday;
    String phoneNumber;
}
