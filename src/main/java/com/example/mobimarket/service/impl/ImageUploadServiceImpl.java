package com.example.mobimarket.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.mobimarket.exception.EmptyFileException;
import com.example.mobimarket.service.ImageUploadService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    @SneakyThrows
    public String saveImage(MultipartFile multipartfile) {
        if (multipartfile.isEmpty()) {
            throw new EmptyFileException("File is empty");
        }

        final String urlKey = "cloudinary://429488699555469:ikzaa8wUnIvSZJr7h917mJPTdKU@dovfdzzuz";


        Cloudinary cloudinary = new Cloudinary((urlKey));

        File saveFile = Files.createTempFile(
                        System.currentTimeMillis() + "",
                        Objects.requireNonNull
                                (multipartfile.getOriginalFilename(), "File must have an extension")
                )
                .toFile();

        multipartfile.transferTo(saveFile);


        Map upload = cloudinary.uploader().upload(saveFile, ObjectUtils.emptyMap());

        return (String) upload.get("url");
    }
}
