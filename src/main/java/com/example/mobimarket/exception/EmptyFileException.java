package com.example.mobimarket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmptyFileException extends  RuntimeException{
    private final String msg;
}
