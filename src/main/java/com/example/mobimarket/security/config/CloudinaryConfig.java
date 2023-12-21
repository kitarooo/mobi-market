package com.example.mobimarket.security.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    /*@Value("${cloud_name}")*/
    private final String CLOUD_NAME = "dovfdzzuz";
   /* @Value("${api_key}")*/
    private String API_KEY = "429488699555469";
    /*@Value("${api_secret}")*/
    private String API_SECRET = "ikzaa8wUnIvSZJr7h917mJPTdKU";

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();

        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);

        return new Cloudinary(config);
    }
}
